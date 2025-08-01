package com.playtodoo.modulith.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOriginPatterns("http://localhost:5500", "http://127.0.0.1:5500", "http://localhost:4200")
                .allowedMethods("*")
                .allowedHeaders("*");
        // ⚠️ no pongas `.allowCredentials(true)` si usas `"*"`
    }
}
