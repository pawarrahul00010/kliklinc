package com.technohertz;

import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication(scanBasePackages = { "com.technohertz" })
public class Application {
	public static void main(String[] args) {
		HttpServletRequest httpRequest = null;
		String sessionId = httpRequest.getParameter("sessionId");
		
		if(sessionId == null){
			sessionId = getSessionId();
		}
		
		String message = " : Session ID = " + sessionId;
		System.out.println(message);
		SpringApplication.run(Application.class, args);
	}
	
	private static String getSessionId(){
		return UUID.randomUUID().toString()
				.replaceAll("-", "").toUpperCase();
	}
}
