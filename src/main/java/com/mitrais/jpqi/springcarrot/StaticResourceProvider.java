package com.mitrais.jpqi.springcarrot;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class StaticResourceProvider implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry
                .addResourceHandler("/css/**")
                .addResourceLocations("classpath:/static/css/");

        registry
                .addResourceHandler("/js/**")
                .addResourceLocations("classpath:/static/js/");

        registry
                .addResourceHandler("/images/**")
                //for Unix: file:/opt/files
                //TODO: use path here
                .addResourceLocations("classpath:/images/");
    }
}
