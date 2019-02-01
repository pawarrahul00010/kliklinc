package com.technohertz.model;

import java.io.Serializable;

public class ExceptionHandle implements Serializable {
private String error_code;
private String status;
private Object object;
private String massage;



public String getError_code() {
	return error_code;
}
public void setError_code(String error_code) {
	this.error_code = error_code;
}
public String getStatus() {
	return status;
}
public void setStatus(String status) {
	this.status = status;
}
public Object getObject() {
	return object;
}
public void setObject(Object object) {
	this.object = object;
}
public String getMassage() {
	return massage;
}
public void setMassage(String massage) {
	this.massage = massage;
}

@Override
public String toString() {
	return "ExceptionHandle [error_code=" + error_code + ", status=" + status + ", object=" + object + ", massage="
			+ massage + "]";
}

}
