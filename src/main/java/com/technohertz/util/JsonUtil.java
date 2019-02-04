package com.technohertz.util;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class JsonUtil {

	public String objToJson(Object obj){
		String json=null;
		try {
			ObjectMapper om=new ObjectMapper();
			json=om.writeValueAsString(obj);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return json;
	}
	
	public Object jsonToObj(String json,Class cls){
		Object ob=null;
		try {
			ObjectMapper om=new ObjectMapper();
			ob=om.readValue(json, cls);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ob;
	}
}
