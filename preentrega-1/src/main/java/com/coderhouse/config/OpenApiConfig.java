package com.coderhouse.config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;

@Configuration
public class OpenApiConfig {
	
	@Bean
	OpenAPI custonOpenAPI() {
		return new OpenAPI()
				.info(new Info()
						.title("API REST Full | Java | CoderHouse")
						.version("3.0.0")
						.description("La API REST proporciona endpoints para un eCommerce de videojuegos."
								+ "Permite realizar operaciones CRUD tanto para clientes como para productos y facturas."
								+ "La API esta documentada utilizando Swagger, lo que facilita la comprension de los endpoints y su uso.")
						.contact(new Contact()
								.name("Alvaro Manterola")
								.email("alvaromanterola00@gmail.com"))
						.license(new License()
								.name("Licencia")
								.url("https://github.com/alvarom00/Java"))
						)
				.servers(List.of(new Server()
						.url("http://localhost:8080")
						.description("Servidor local")));
	}

}
