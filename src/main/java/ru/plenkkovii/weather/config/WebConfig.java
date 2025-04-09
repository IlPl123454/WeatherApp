package ru.plenkkovii.weather.config;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import ru.plenkkovii.weather.handler.AuthorizationInterceptor;
import ru.plenkkovii.weather.service.SessionService;

@AllArgsConstructor

@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final SessionService sessionService;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new AuthorizationInterceptor(sessionService)).addPathPatterns("/home");
    }
}

