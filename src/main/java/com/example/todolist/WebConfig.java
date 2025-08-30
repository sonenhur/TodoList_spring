package com.example.todolist;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**") // 'api'로 시작하는 모든 요청에 대해
        .allowedOrigins("*") // 허용할 프론트엔드 주소(개발 환경에 맞게 수정)
        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // 허용할 HTTP 메서드
        .allowedHeaders("*"); // 모든 헤더 허용
    }
}
