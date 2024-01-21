package org.example.configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfiguration {
    @Bean
    public OpenAPI docket(){
        return new OpenAPI()
                .info(new Info().title("API 서버 문서")
                        .description("API 서버 문서입니다.")
                        .version("v0.0.1"));

    }
}
