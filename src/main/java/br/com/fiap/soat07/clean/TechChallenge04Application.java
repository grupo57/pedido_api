package br.com.fiap.soat07.clean;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.retry.annotation.EnableRetry;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

@EnableRetry
@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "Lanchonete Self Service API", version = "2.0", description = "Sistema de Self Service para Lanchonete"))
public class TechChallenge04Application {

	public static void main(String[] args) {
		SpringApplication.run(TechChallenge04Application.class, args);
	}

}
