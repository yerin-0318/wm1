package net.manage.db;

import java.sql.Timestamp;

public class warningBean {
	private int num;
	private String warn_email; 
	private Timestamp date; 
	private int cause;
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	public String getWarn_email() {
		return warn_email;
	}
	public void setWarn_email(String warn_email) {
		this.warn_email = warn_email;
	}
	public Timestamp getDate() {
		return date;
	}
	public void setDate(Timestamp date) {
		this.date = date;
	}
	public int getCause() {
		return cause;
	}
	public void setCause(int cause) {
		this.cause = cause;
	}
}
