package com.java.config;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class MvcConfig implements  WebMvcConfigurer  {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
    	Path path = Paths.get("./static/images/");
    	String pathString = path.toFile().getAbsolutePath();
    	
        registry
          .addResourceHandler("/images/**")
          .addResourceLocations("file://"+pathString+"/");
    }
}
