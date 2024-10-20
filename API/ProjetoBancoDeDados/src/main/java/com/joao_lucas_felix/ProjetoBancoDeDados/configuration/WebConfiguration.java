package com.joao_lucas_felix.ProjetoBancoDeDados.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfiguration implements WebMvcConfigurer {
    @Value("${cors.originPatterns:default}")
    private String corsOriginPatterns = "";

    @Override
    public void addCorsMappings( CorsRegistry registry) {
        var allowedOrigins = corsOriginPatterns.split(","); // pega os valores lidos do resources
        registry.addMapping("/**")
                //.allowedMethods("GET", "POST", "PUT") // indica para quais verbos http o cors  vai funcionar.
                .allowedMethods("*")
                .allowedOrigins(allowedOrigins)// indica que as origins permitidas são as que nos lemos
                .allowCredentials(true); // para permitir autenticação
    }
}
