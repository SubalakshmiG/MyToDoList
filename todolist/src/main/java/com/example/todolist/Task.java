package com.example.todolist;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

// This is our data model. It represents a single to-do item.
@Entity
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String description;
    private boolean completed;
    private LocalDate dueDate;

    @Enumerated(EnumType.STRING)
    private TaskCategory category;

    public Task() {
    }

    public Task(long id, String description, TaskCategory category, LocalDate dueDate) {
        this.description = description;
        this.completed = false; // Tasks are not completed by default
        this.category = category;
        this.dueDate = dueDate;
    }

    // Getters and Setters are needed for the framework to access the properties.
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public TaskCategory getCategory() {
        return category;
    }

    public void setCategory(TaskCategory category) {
        this.category = category;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }
}
