package net.writelist.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import org.apache.commons.collections4.iterators.ObjectGraphIterator;
import org.json.simple.JSONObject;

public class com_commuDAO {
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
	
	public void insertComm(com_commuBean bean){
		try {
			con=getConnection();
			sql="insert into comment_commu(content,com_num,email) "
					+ "values(?,?,?)";
			pstmt=con.prepareStatement(sql);
			pstmt.setString(1, bean.getContent());
			pstmt.setInt(2, bean.getCom_num());
			pstmt.setString(3, bean.getEmail());
			pstmt.executeUpdate();
		} catch (Exception e) {
			System.out.println("insert코멘트 에러_"+e);
		} finally {
			closeDB();
		}
	}
	
	//커뮤니티 댓글 관련 정보 얻어오기
	//해당글 전체 댓글 수 지금 출력된 댓글 수 출력
	public JSONObject getCommInf(int num, int nowlast, String total){
		JSONObject obj = new JSONObject();
		try {
			con = getConnection();		
			System.out.println("getCommInf_"+num+","+nowlast+","+total);
			System.out.println("여기1");
			if(total==""){ //처음으로 total구함
				System.out.println("여기");
				sql = "select count(*) from comment_commu where com_num=?";
				pstmt = con.prepareStatement(sql);
				pstmt.setInt(1, num);
				rs = pstmt.executeQuery();
				int tmp = 0;
				if(rs.next()){
					obj.put("total", rs.getInt(1));
					tmp = rs.getInt(1);
				}
				if(tmp==0){ //해당 글에 댓글이 없을 경우
					obj.put("prtCnt", -1);
					return obj;
				}
			}else{ //이전에도 total구한 경우
				obj.put("total", 0);
			}
			//해당 글에 댓글이 있는 경우
			sql = "SELECT count(*) "
					+ "FROM comment_commu "
					+ "where com_num=? and num>=? "
					+ "order by num desc";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, num);
			pstmt.setInt(2, nowlast);
			rs = pstmt.executeQuery();
			if(rs.next()){
				obj.put("prtCnt", rs.getInt(1));
			}
			
		} catch (Exception e) {
			System.out.println("getCommInf오류_"+e);
		} finally {
			closeDB();
		}
		return obj;
	}

	public void deleteComm(int num) {
		try {
			con = getConnection();
			sql = "delete from comment_commu "
					+ "where num=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, num);
			pstmt.executeUpdate();
		} catch (Exception e) {
			System.out.println("deleteComm_"+e);
		} finally {
			closeDB();
		}		
	}
}
