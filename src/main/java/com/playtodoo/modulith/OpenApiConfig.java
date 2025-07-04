package com.playtodoo.modulith;

import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.OpenAPI;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Playtodoo API")
                        .version("1.0.0")
                        .description("API para la gestión de complejos deportivos y reservas.")
                        .contact(new Contact()
                                .name("Playtodoo")
                                .email("soporte@playtodoo.com")
                        )
                );
    }
}

