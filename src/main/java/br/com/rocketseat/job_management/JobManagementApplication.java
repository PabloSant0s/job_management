package br.com.rocketseat.job_management;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "Job Management", description = "API for job management", version = "1.0.0"))
public class JobManagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(JobManagementApplication.class, args);
	}

}
