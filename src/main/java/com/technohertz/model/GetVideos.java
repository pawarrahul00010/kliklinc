package com.technohertz.model;

public class GetVideos {


private String url;
private String text;
/**
 * @return the url
 */
public String getUrl() {
	return url;
}
/**
 * @param url the url to set
 */
public void setUrl(String url) {
	this.url = url;
}
/**
 * @return the text
 */
public String getText() {
	return text;
}
/**
 * @param text the text to set
 */
public void setText(String text) {
	this.text = text;
}
/* (non-Javadoc)
 * @see java.lang.Object#toString()
 */
@Override
public String toString() {
	return "GetVideos [url=" + url + ", text=" + text + "]";
}






}
