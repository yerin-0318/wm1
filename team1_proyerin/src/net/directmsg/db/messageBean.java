package net.directmsg.db;

import java.sql.Timestamp;

public class messageBean {
	private int num;
	private String receive_email;
	private String send_email;
	private String content;
	private Timestamp date;
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	public String getReceive_email() {
		return receive_email;
	}
	public void setReceive_email(String receive_email) {
		this.receive_email = receive_email;
	}
	public String getSend_email() {
		return send_email;
	}
	public void setSend_email(String send_email) {
		this.send_email = send_email;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Timestamp getDate() {
		return date;
	}
	public void setDate(Timestamp date) {
		this.date = date;
	}
	
}
