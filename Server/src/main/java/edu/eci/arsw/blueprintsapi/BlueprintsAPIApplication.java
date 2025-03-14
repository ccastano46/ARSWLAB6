package edu.eci.arsw.blueprintsapi;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"edu.eci.arsw.blueprints"})
@OpenAPIDefinition(info = @Info(title = "API BLUEPRINTS", version = "1.0", description = "En Esta API se pueden registar cualquier tipo de plano con un autor y un nombre del plano junto con un grupo de puntos"))
public class BlueprintsAPIApplication {

	public static void main(String[] args) {
		SpringApplication.run(BlueprintsAPIApplication.class, args);
	}
}
