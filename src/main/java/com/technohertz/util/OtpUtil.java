package com.technohertz.util;

import org.springframework.stereotype.Component;

@Component
public class OtpUtil {
	
	
	public int getOTP() {
		
		int newOtp = (int)(Math.random()*9000)+1000;
		
		return newOtp;
	}

}
