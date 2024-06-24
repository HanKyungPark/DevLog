package org.bitcamp.devlog.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI(){
        return new OpenAPI()
                .info(apiInfo());
    }


    private Info apiInfo() {
        return new Info()
                .title("Devlog Api 명세서")
                .description("각 컨틀롤러들의 RestApi 명세서")
                .version("1.0");
    }
}
