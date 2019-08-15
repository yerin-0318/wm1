package net.freeboard.db;

import java.sql.Timestamp;

public class free_boardBean {
	private int num;
	private String email;
	private String title;
	private String content;
	private String image;
	private Timestamp date;
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
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public Timestamp getDate() {
		return date;
	}
	public void setDate(Timestamp date) {
		this.date = date;
	}
	public int getMu_num() {
		return mu_num;
	}
	public void setMu_num(int mu_num) {
		this.mu_num = mu_num;
	}
}
