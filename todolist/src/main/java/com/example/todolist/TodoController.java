package com.example.todolist;

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

@RestController // Marks this class as a controller where every method returns a domain object instead of a view.
@RequestMapping("/api/tasks") // Maps all requests starting with /api/tasks to this controller.
public class TodoController {

    // Dependency Injection: Spring automatically provides the database repository here
    private final TaskRepository taskRepository;

    public TodoController(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @GetMapping // This method will handle GET requests to /api/tasks
    public List<Task> getAllTasks(@RequestParam(required = false) TaskCategory category) {
        if (category != null) {
            // If a category is provided in the URL, filter by it
            return taskRepository.findByCategory(category);
        }
        // Otherwise, return all tasks
        return taskRepository.findAll();
    }

    @PostMapping // Maps POST requests to /api/tasks
    @ResponseStatus(HttpStatus.CREATED) // Responds with
    public Task createTask(@RequestBody Task task) {
        return taskRepository.save(task); // Saves to Database
    }

    @PutMapping("/{id}") // Updates an existing task
    public Task updateTask(@PathVariable long id, @RequestBody Task taskDetails) {
        Task task = taskRepository.findById(id).orElseThrow(() -> new RuntimeException("Task not found"));
        
        task.setDescription(taskDetails.getDescription());
        task.setCompleted(taskDetails.isCompleted());
        task.setCategory(taskDetails.getCategory());
        task.setDueDate(taskDetails.getDueDate());
        
        return taskRepository.save(task);
    }

    @DeleteMapping("/{id}") // Deletes a task
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTask(@PathVariable long id) {
        taskRepository.deleteById(id);
    }
}
