package com.ceng316.ceng316_oims_backend;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("https://oims-om4o.onrender.com", "http://localhost:3000")
                .allowedMethods("GET", "POST", "PUT", "DELETE")
                .exposedHeaders("Content-Disposition")
                .allowCredentials(true);
    }
}