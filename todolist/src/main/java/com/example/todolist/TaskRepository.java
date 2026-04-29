package com.example.todolist;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Long> {
    // Spring writes all the database interaction code automatically!

    // By defining this method, Spring will automatically create a query
    // to "find all Tasks where the category matches the one provided".
    List<Task> findByCategory(TaskCategory category);
}