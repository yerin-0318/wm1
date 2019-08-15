package net.musics.db;

import java.sql.Timestamp;

public class musicsBean {
	private String email;
	private int num;
	private String music_title;
	private String album;
	private Timestamp date;
	private String music_file;
	private String genre;
	private int cnt;
	private int good;
	private String content;
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	public String getMusic_title() {
		return music_title;
	}
	public void setMusic_title(String music_title) {
		this.music_title = music_title;
	}
	public String getAlbum() {
		return album;
	}
	public void setAlbum(String album) {
		this.album = album;
	}
	public Timestamp getDate() {
		return date;
	}
	public void setDate(Timestamp date) {
		this.date = date;
	}
	public String getMusic_file() {
		return music_file;
	}
	public void setMusic_file(String music_file) {
		this.music_file = music_file;
	}
	public String getGenre() {
		return genre;
	}
	public void setGenre(String genre) {
		this.genre = genre;
	}
	public int getCnt() {
		return cnt;
	}
	public void setCnt(int cnt) {
		this.cnt = cnt;
	}
	public int getGood() {
		return good;
	}
	public void setGood(int good) {
		this.good = good;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
	
}
