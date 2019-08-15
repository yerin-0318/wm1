package net.writelist.db;

import java.sql.Timestamp;

public class com_musicBean {
	private int num;
	private String email;
	private Timestamp date;
	private String content;
	private int mu_num;
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Timestamp getDate() {
		return date;
	}
	public void setDate(Timestamp date) {
		this.date = date;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public int getMu_num() {
		return mu_num;
	}
	public void setMu_num(int mu_num) {
		this.mu_num = mu_num;
	}


}
