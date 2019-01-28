package com.technohertz.common;

import java.util.HashMap;
import java.util.Map;

import com.technohertz.model.UserRegister;


public class Context{
	
	/**
	 * Key = Session Id.
	 * Persistent bean will store Attributes.
	 * Persistent<String,Object> {Key,Value}
	 */
	private Map<String, PersistentContext> applicationContext = new HashMap<String, PersistentContext>();
	
	
	/**
	 * User Store.
	 */
	private Map<String, UserRegister> userBucket = new HashMap<String, UserRegister>();
	
	/**
	 * @return the persistentContext
	 */
	public PersistentContext getPersistentContext(String sessionId) {
		
		PersistentContext persistentContext = this.applicationContext.get(sessionId);
		
		if(persistentContext == null){
			persistentContext = new PersistentContext();
			this.applicationContext.put(sessionId, persistentContext);
		}
		
		return persistentContext;
	}

	/**
	 * @return the userBucket
	 */
	public Map<String, UserRegister> getUserBucket() {
		return userBucket;
	}
	
	/*
	 * @return httpsession
	 */
	public boolean clearPersistentContext(String sessionId){
		
		this.applicationContext.remove(sessionId);
		
		return true;
	}
	
	
}