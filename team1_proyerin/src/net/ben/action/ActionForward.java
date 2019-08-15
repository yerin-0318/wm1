package net.ben.action;

public class ActionForward {
	//이동 방식
	private boolean isRedirect=false; //true sendRedirect()
										//false  forward()
	//이동경로
	private String path=null;
	
	public boolean isRedirect() {
		return isRedirect;
	}
	public void setRedirect(boolean isRedirect) {
		this.isRedirect = isRedirect;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	
	
}
