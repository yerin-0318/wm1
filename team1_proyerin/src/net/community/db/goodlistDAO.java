package net.community.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class goodlistDAO {
	private Connection con=null;
	private PreparedStatement pstmt=null;
	private String sql="";
	private ResultSet rs=null;
	
	private Connection getConnection() throws Exception{
		Connection con=null;
		Context inti=new InitialContext();
		DataSource ds=(DataSource)inti.lookup("java:comp/env/jdbc/watermelon");
		con=ds.getConnection();
		return con;
	}
	
	private void closeDB(){
			try {
				if(rs!=null)rs.close();
				if(pstmt!=null)pstmt.close();
				if(con!=null)con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
	}

	/*내역 있는지 확인*/
	public boolean searchgood(int boardtype, int num, String email) {
		boolean check=false;
		try {
			con=getConnection();
			sql="select count(*) from goodlist where boardtype=? and post_num=? and email=?";
			pstmt=con.prepareStatement(sql);
			pstmt.setInt(1, boardtype);
			pstmt.setInt(2, num);
			pstmt.setString(3, email);
			rs=pstmt.executeQuery();
			if(rs.next()){
				if(rs.getInt(1)==1){//좋아요했음
					check=true;
				}
			}
		} catch (Exception e) {
			System.out.println("searchgood오류_"+e);
		} finally {
			closeDB();
		}
		
		return check;
	}

	public void setlist(goodlistBean goodBean) {
		try {
			con=getConnection();
			sql="insert into goodlist values(?,?,?)";
			pstmt=con.prepareStatement(sql);
			pstmt.setInt(1, goodBean.getBoardtype());
			pstmt.setString(2, goodBean.getEmail());
			pstmt.setInt(3, goodBean.getPost_num());
			pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeDB();
		}		
	}
}
