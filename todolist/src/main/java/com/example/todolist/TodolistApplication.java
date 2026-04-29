package com.example.todolist;

import java.time.LocalDate;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class TodolistApplication {

	public static void main(String[] args) {
		SpringApplication.run(TodolistApplication.class, args);
	}

	// This bean runs automatically when the application starts
	@Bean
	public CommandLineRunner loadData(TaskRepository repository) {
		return (args) -> {
			// Add default tasks to the database so it isn't completely empty!
			repository.save(new Task(0L, "Build a dynamic UI frontend", TaskCategory.DAILY_TASK, LocalDate.now()));
			repository.save(new Task(0L, "Add calendar feature", TaskCategory.DAILY_TASK, LocalDate.now().plusDays(1)));
			repository.save(new Task(0L, "Master Spring Boot", TaskCategory.MONTH_GOAL, LocalDate.now().plusMonths(1)));
			repository.save(new Task(0L, "Get hired as a Java Developer!", TaskCategory.YEAR_GOAL, LocalDate.now().plusYears(1)));
		};
	}
}
