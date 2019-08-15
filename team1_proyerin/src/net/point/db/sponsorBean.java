package net.point.db;

import java.sql.Timestamp;

public class sponsorBean {
	private int num;
	private String email;
	private String streamer;
	private int cost;
	private Timestamp date;
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
	public String getStreamer() {
		return streamer;
	}
	public void setStreamer(String streamer) {
		this.streamer = streamer;
	}
	public int getCost() {
		return cost;
	}
	public void setCost(int cost) {
		this.cost = cost;
	}
	public Timestamp getDate() {
		return date;
	}
	public void setDate(Timestamp date) {
		this.date = date;
	}
	
	
}
