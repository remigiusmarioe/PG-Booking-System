package com.pgbooking.user.config;



import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;



@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {

        return new OpenAPI()
                .info(
                        new Info()
                                .title("PG Booking User Service API")
                                .version("v1.0")
                                .description("User Service APIs for PG Booking System")
                                .contact(
                                        new Contact()
                                                .name("Remigius Marioe")
                                                .email("remigiusmarioe.07@gmail.com")
                                )
                );
    }
}
