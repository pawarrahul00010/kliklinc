package com.technohertz.util;

import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

@Component
public class DateUtil {

public LocalDateTime getDate() {
		
	    LocalDateTime now = LocalDateTime.now();  
		   
		return now;
		
	}
}
