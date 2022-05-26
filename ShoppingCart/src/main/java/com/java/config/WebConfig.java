package com.java.config;


import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Component
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "app")
public class WebConfig implements WebMvcConfigurer{
	
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		Path uploadDir = Paths.get("./images");
		String stringUploadDir = uploadDir.toFile().getAbsolutePath();
		System.out.println("vao");
		registry.addResourceHandler("/images/**").addResourceLocations("file:/" + stringUploadDir + "/");
	}
	
	
}