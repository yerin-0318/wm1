package net.member.db;

import java.sql.Timestamp;

public class membersBean {
	private String email;
	private String password;
	private String name;
	private String gender;
	private String birth;
	private int likegenre;
	private int streamer;
	private int point;
	private String profile_img;
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getBirth() {
		return birth;
	}
	public void setBirth(String birth) {
		this.birth = birth;
	}
	public int getLikegenre() {
		return likegenre;
	}
	public void setLikegenre(int likegenre) {
		this.likegenre = likegenre;
	}
	public int getStreamer() {
		return streamer;
	}
	public void setStreamer(int streamer) {
		this.streamer = streamer;
	}
	public int getPoint() {
		return point;
	}
	public void setPoint(int point) {
		this.point = point;
	}
	public String getProfile_img() {
		return profile_img;
	}
	public void setProfile_img(String profile_img) {
		this.profile_img = profile_img;
	}
}
