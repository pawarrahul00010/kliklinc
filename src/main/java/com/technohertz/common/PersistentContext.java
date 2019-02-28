package com.technohertz.common;

import java.util.HashMap;

public class PersistentContext extends HashMap<Object, Object>{

	/**
	 * Serial Version Id.
	 */
	private static final long serialVersionUID = 718743726330085765L;

	/**
	 * Zero Argument Constructor.
	 */
	public PersistentContext() {
		super();
	}
	
	/**
	 * Method to set attribute.
	 * @param _key
	 * @param _object
	 */
	public void setAttribute(String _key, Object _object){
		this.put(_key, _object);
	}
	
	/**
	 * Method to retrieve attribute.
	 * @param _key
	 * @return
	 */
	public Object getAttribute(String _key){
		return this.get(_key);
	}
}
