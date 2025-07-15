package com.encora.ernesto.ramirez.todo_app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main entry point for the Todo App application.
 * This class initializes and starts the Spring Boot application.
 */
@SpringBootApplication
public class TodoAppApplication {

	/**
	 * Main method to launch the application.
	 * 
	 * @param args Command-line arguments
	 */
	public static void main(String[] args) {
		SpringApplication.run(TodoAppApplication.class, args);
	}

}
