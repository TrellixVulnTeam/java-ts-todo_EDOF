package com.api.server;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;


@ComponentScan(basePackageClasses = TodoController.class)
@SpringBootApplication
@EnableMongoRepositories
public class ServerApplication implements CommandLineRunner  {

	
	@Autowired
  TodoRepository repo;

	public static void main(String[] args) {
		SpringApplication.run(ServerApplication.class, args);

	}
 
	public void run(String... args) {
			
        System.out.println("SERVER RUNNING....");
        
    }

}
