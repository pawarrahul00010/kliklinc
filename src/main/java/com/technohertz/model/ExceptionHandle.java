package com.technohertz.model;

import java.io.Serializable;

public class ExceptionHandle implements Serializable {
private String error_code;
private String status;
private Object data;
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

public String getMassage() {
	return massage;
}
public void setMassage(String massage) {
	this.massage = massage;
}



/**
 * @return the data
 */
public Object getData() {
	return data;
}
/**
 * @param data the data to set
 */
public void setData(Object data) {
	this.data = data;
}
/* (non-Javadoc)
 * @see java.lang.Object#toString()
 */
@Override
public String toString() {
	return "ExceptionHandle [error_code=" + error_code + ", status=" + status + ", data=" + data + ", massage="
			+ massage + "]";
}

}
