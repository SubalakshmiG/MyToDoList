package com.example.todolist;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserRepository userRepository;
    private final TaskRepository taskRepository;

    public UserController(UserRepository userRepository, TaskRepository taskRepository) {
        this.userRepository = userRepository;
        this.taskRepository = taskRepository;
    }

    // GDPR: Right to be Forgotten (Account Deletion)
    @DeleteMapping("/me")
    public ResponseEntity<String> deleteMyAccount(Principal principal) {
        String username = principal.getName(); // Securely pulled from the JWT token
        
        // 1. Find and delete all tasks owned by this user
        List<Task> userTasks = taskRepository.findByUserId(username);
        taskRepository.deleteAll(userTasks);
        
        // 2. Find and delete the actual user account
        Optional<User> user = userRepository.findByUsername(username);
        user.ifPresent(userRepository::delete);
        
        return ResponseEntity.ok("Account and all associated data successfully deleted.");
    }
}