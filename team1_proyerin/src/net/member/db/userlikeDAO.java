package net.member.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class userlikeDAO {
	
	Connection con = null;
	String sql = "";
	PreparedStatement pstmt=null;
	ResultSet rs=null;
	
	
	private Connection getConnection() throws Exception{
			
			Connection con = null;
			
			Context init = new InitialContext();
			DataSource ds =(DataSource)init.lookup("java:comp/env/jdbc/watermelon");
			con=  ds.getConnection();
			return con;
		}
	private void closeDB(){
		try {
			if(rs!=null) rs.close();
			if(pstmt!=null) pstmt.close();
			if(con!=null) con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void insertLike(userlikeBean userdto){
		
		try {
			con = getConnection();
			sql = "insert into userlike values(?,?)";
			pstmt=con.prepareStatement(sql);
			pstmt.setString(1, userdto.getEmail());
			pstmt.setString(2, userdto.getGenre());
			pstmt.executeUpdate();
		} catch (Exception e) {
			System.out.println("userlike_inserLike에러__"+e);
		} finally{
			closeDB();
		}
	}
	
}
