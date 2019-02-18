package com.technohertz;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;

import com.technohertz.util.FileStorageProperties;

@EnableConfigurationProperties({FileStorageProperties.class})
@SpringBootApplication(scanBasePackages = { "com.technohertz" })
@EnableCaching
public class Application {
	public static void main(String[] args) {
		
		SpringApplication.run(Application.class, args);
	}
	
}
