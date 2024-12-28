package com.AntiqueAuto;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
@EnableScheduling
public class AntiqueAutoHubApplication {

	public static void main(String[] args) {
		SpringApplication.run(AntiqueAutoHubApplication.class, args);
	}
	@Configuration
	@EnableWebMvc
	public class MvcConfig implements WebMvcConfigurer {

	    @Override
	    public void addResourceHandlers(ResourceHandlerRegistry registry) {
	        // Serve static files (CSS, JS, images) from the default static folder
	        registry.addResourceHandler("/**")
	                .addResourceLocations("classpath:/static/");

	        // Serve uploaded files from the custom upload folder
	        registry.addResourceHandler("/upload/**")
	                .addResourceLocations("file:src/main/resources/upload/");
	    }
	}

}
