package com.codeosseum.ares.web;

import com.codeosseum.ares.web.Endpoints.Paths;
import com.codeosseum.ares.web.Endpoints.Views;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
public class MvcConfig implements WebMvcConfigurer {
    private static final String PUBLIC_RESOURCES_LOCATION = "classpath:/public/";

    private static final Map<String, String> viewToPathMapping = new LinkedHashMap<>();

    static {
        viewToPathMapping.put(Paths.HOME, Views.HOME);
        viewToPathMapping.put(Paths.LOGIN, Views.LOGIN);
        viewToPathMapping.put(Paths.Game.HOME, Views.Game.HOME);
    }

    @Override
    public void addViewControllers(final ViewControllerRegistry registry) {
        viewToPathMapping.forEach((path, view) -> registry.addViewController(path).setViewName(view));
    }

    @Override
    public void addResourceHandlers(final ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**").addResourceLocations(PUBLIC_RESOURCES_LOCATION);
    }
}
