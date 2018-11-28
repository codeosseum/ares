package com.codeosseum.ares.config.web;

import com.codeosseum.ares.config.web.Endpoints.Paths;
import com.codeosseum.ares.config.web.Endpoints.Views;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
public class MvcConfig implements WebMvcConfigurer {
    private static final Map<String, String> viewToPathMapping = new LinkedHashMap<>();

    static {
        viewToPathMapping.put(Paths.HOME, Views.HOME);
        viewToPathMapping.put(Paths.LOGIN, Views.LOGIN);
    }

    @Override
    public void addViewControllers(final ViewControllerRegistry registry) {
        viewToPathMapping.forEach((path, view) -> registry.addViewController(path).setViewName(view));
    }
}
