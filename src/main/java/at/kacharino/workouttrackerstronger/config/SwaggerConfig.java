package at.kacharino.workouttrackerstronger.config;

import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.OpenAPI;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Workout Tracker Stronger API")
                        .version("1.0")
                        .description("API documentation for your Workout Tracker Stronger project"));
    }
}