package com.example.todolist;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class TodolistApplication {

	public static void main(String[] args) {
		SpringApplication.run(TodolistApplication.class, args);
	}

	// This bean runs automatically when the application starts
	@Bean
	public CommandLineRunner loadData(TaskRepository repository, UserRepository userRepository, PasswordEncoder passwordEncoder, @Value("${ADMIN_PASSWORD:admin123}") String adminPassword) {
		return (args) -> {
			// Create default user if it doesn't exist
			if (userRepository.findByUsername("system-admin").isEmpty()) {
				// Uses environment variable for the admin password, falling back to admin123
				userRepository.save(new User("system-admin", passwordEncoder.encode(adminPassword)));
			}

			// Add default tasks to the database so it isn't completely empty!
			repository.save(new Task(0L, "Build a dynamic UI frontend", TaskCategory.DAILY_TASK, LocalDate.now(), "system-admin"));
			repository.save(new Task(0L, "Add calendar feature", TaskCategory.DAILY_TASK, LocalDate.now().plusDays(1), "system-admin"));
			repository.save(new Task(0L, "Master Spring Boot", TaskCategory.MONTH_GOAL, LocalDate.now().plusMonths(1), "system-admin"));
			repository.save(new Task(0L, "Get hired as a Java Developer!", TaskCategory.YEAR_GOAL, LocalDate.now().plusYears(1), "system-admin"));
		};
	}
}
