package com.example.todolist;

import java.security.Principal;
import java.util.List; // Import PostMapping

import org.springframework.http.HttpStatus; // Import RequestBody for receiving JSON
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable; // Import ResponseStatus
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping; // For returning appropriate HTTP status
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController // Marks this class as a controller where every method returns a domain object instead of a view.
@RequestMapping("/api/tasks") // Maps all requests starting with /api/tasks to this controller.
public class TodoController {

    // Dependency Injection: Spring automatically provides the database repository here
    private final TaskRepository taskRepository;

    public TodoController(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @GetMapping // This method will handle GET requests to /api/tasks
    public List<Task> getAllTasks(Principal principal, @RequestParam(required = false) TaskCategory category) {
        String userId = principal.getName(); // Securely retrieved from the JWT token!
        if (category != null) {
            // If a category is provided in the URL, filter by it
            return taskRepository.findByUserIdAndCategory(userId, category);
        }
        // Otherwise, return all tasks
        return taskRepository.findByUserId(userId);
    }

    @PostMapping // Maps POST requests to /api/tasks
    @ResponseStatus(HttpStatus.CREATED) // Responds with
    public Task createTask(Principal principal, @RequestBody Task task) {
        String userId = principal.getName();
        task.setUserId(userId); // Assign the task to the user making the request
        return taskRepository.save(task); // Saves to Database
    }

    @PutMapping("/{id}") // Updates an existing task
    public Task updateTask(Principal principal, @PathVariable long id, @RequestBody Task taskDetails) {
        String userId = principal.getName();
        Task task = taskRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Task not found"));
        
        if (task.getUserId() != null && !task.getUserId().equals(userId)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Unauthorized: You do not own this task");
        }
        
        task.setDescription(taskDetails.getDescription());
        task.setCompleted(taskDetails.isCompleted());
        task.setCategory(taskDetails.getCategory());
        task.setDueDate(taskDetails.getDueDate());
        
        return taskRepository.save(task);
    }

    @DeleteMapping("/{id}") // Deletes a task
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTask(Principal principal, @PathVariable long id) {
        String userId = principal.getName();
        Task task = taskRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Task not found"));
        
        if (task.getUserId() != null && !task.getUserId().equals(userId)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Unauthorized: You do not own this task");
        }
        
        taskRepository.delete(task);
    }
}
