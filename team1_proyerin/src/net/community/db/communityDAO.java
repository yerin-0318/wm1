package net.community.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class communityDAO {
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
	
	/*전체 글 개수*/
	public int getListSize(String streamer){
		int size=0;
		try {
			con=getConnection();
			sql="select count(*) from community where email=?";
			pstmt=con.prepareStatement(sql);
			pstmt.setString(1, streamer);
			rs=pstmt.executeQuery();
			if(rs.next()){
				size=rs.getInt(1);
			}
		} catch (Exception e) {
			System.out.println("getListSize에러_"+e);
		} finally {
			closeDB();
		}
		return size;
	}
	
	/*JSONArray로 내용 받아오기*/
	public JSONArray getList(String streamer, int num, int pageSize){
		JSONArray arr = new JSONArray();
		try {
			con=getConnection();
			sql="select name, num, photo, content, good, date, profile_img, email "
					+ "from community natural join members "
					+ "where email=? and num<=? "
					+ "order by num desc limit 0,?";
			pstmt=con.prepareStatement(sql);
			pstmt.setString(1, streamer);
			pstmt.setInt(2, num);
			pstmt.setInt(3, pageSize);
			rs=pstmt.executeQuery();
			while(rs.next()){
				System.out.println("테스트");
				JSONObject obj = new JSONObject();
				obj.put("num", rs.getInt("num"));
				obj.put("content", rs.getString("content"));
				obj.put("photo", rs.getString("photo"));
				obj.put("good", rs.getInt("good"));
				obj.put("date", rs.getTimestamp("date").toString());
				obj.put("name", rs.getString("name"));
				obj.put("profile_img", rs.getString("profile_img"));
				obj.put("email",rs.getString("email"));
				arr.add(obj);				
			}
		} catch (Exception e) {
			System.out.println("getList�삤瑜�_"+e);
		} finally {
			closeDB();
		}
		return arr;
	}
	
	/*JSON으로 댓글 가져오기*/
	public JSONArray getComments(int com_num, int startnum, String check) {
		JSONArray arr = new JSONArray();
		try {
			con = getConnection();
			if(check.equals("first")){ //처음 출력하는 경우 -> 핀 가장 위에
				sql = "select c.email email, date, content, name, num, pin "
						+ "from comment_commu c join "
						+ "(select email, name from members) m "
						+ "on (c.email=m.email) "
						+ "where com_num=? and num<? order by pin desc, num desc "
						+ "limit 0,5";
			}else{ //두번째 출력부터~ -> 핀 제외시켜야함
				sql = "select c.email email, date, content, name, num, pin "
						+ "from comment_commu c join "
						+ "(select email, name from members) m "
						+ "on (c.email=m.email) "
						+ "where com_num=? and num<? and pin=0 order by num desc "
						+ "limit 0,5";
			}
			pstmt=con.prepareStatement(sql);
			pstmt.setInt(1, com_num);
			pstmt.setInt(2, startnum);
			rs = pstmt.executeQuery();
			while(rs.next()){
				JSONObject obj = new JSONObject();
				obj.put("num",rs.getInt("num"));
				obj.put("date", rs.getTimestamp("date").toString());
				obj.put("content", rs.getString("content"));
				obj.put("name", rs.getString("name"));
				obj.put("email", rs.getString("email"));
				obj.put("pin", rs.getInt("pin"));
				int Totalcomments = getTotalcomments(rs.getInt("num"));
				obj.put("cnt", Totalcomments); //전체 댓글 수
				arr.add(obj);
			}
		} catch (Exception e) {
			System.out.println("getComments오류"+e);
		} finally {
			closeDB();
		}
		return arr;
	}

	/*전체 댓글 수*/
	private int getTotalcomments(int com_num) {
		int total=0;
		try {
			sql = "select count(*) from comment_commu where com_num=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, com_num);
			ResultSet tmp = pstmt.executeQuery();
			if(tmp.next()){
				total = tmp.getInt(1);
			}
		} catch (Exception e) {
			System.out.println("getTotalcomments오류_"+e);
		} 
		return total;
	}

	/*글쓰기*/
	public void insertcontent(communityBean comm) {
		try {
			con=getConnection();
			sql="insert into community(email,content,photo) values(?,?,?)";
			pstmt=con.prepareStatement(sql);
			pstmt.setString(1, comm.getEmail());
			pstmt.setString(2, comm.getContent());
			pstmt.setString(3, comm.getPhoto());
			pstmt.executeUpdate();
		} catch (Exception e) {
			System.out.println("communityDAO insetcontent에러_"+e);
		} finally {
			closeDB();
		}
	}
	
	/*글수정하기1.내용, 파일 모두 수정*/
	public void modifycontfile(communityBean comm) {
		try {
			con=getConnection();
			sql="update community set photo=?, content=? where num=?";
			pstmt=con.prepareStatement(sql);
			pstmt.setString(1, comm.getPhoto());
			pstmt.setString(2, comm.getContent());
			pstmt.setInt(3, comm.getNum());
			pstmt.executeUpdate();
		} catch (Exception e) {
			System.out.println("communityDAO modifycontfile에러_"+e);
		} finally {
			closeDB();
		}
	}
	
	/*글수정하기2.내용만 수정*/
	public void modifycont(communityBean comm) {
		try {
			con=getConnection();
			sql="update community set content=? where num=?";
			pstmt=con.prepareStatement(sql);
			pstmt.setString(1, comm.getContent());
			pstmt.setInt(2, comm.getNum());
			pstmt.executeUpdate();
		} catch (Exception e) {
			System.out.println("communityDAO modifycont에러_"+e);
		} finally {
			closeDB();
		}
	}

	/*마지막 번호*/
	public int getLastnum(String streamer) {
		int lastnum = 0;
		try {
			con=getConnection();
			sql="select max(num) from community where email=?";
			pstmt=con.prepareStatement(sql);
			pstmt.setString(1, streamer);
			rs = pstmt.executeQuery();
			if(rs.next()){
				lastnum = rs.getInt(1);
				System.out.println(lastnum);
			}
		} catch (Exception e) {
			System.out.println("而ㅻ�ㅻ땲�떚DAO insetcontent�뿉�윭_"+e);
		} finally {
			closeDB();
		}
		return lastnum;
	}
	
	/*JSONObject로 수정할 글의 내용 받아오기*/
	public JSONObject getCommcontent(int num) {
		JSONObject Obj = new JSONObject();
		try {
			con=getConnection();
			sql="select photo, content from community where num=?";
			pstmt=con.prepareStatement(sql);
			pstmt.setInt(1, num);
			rs = pstmt.executeQuery();
			while(rs.next()){
				Obj.put("photo", rs.getString("photo"));
				Obj.put("content", rs.getString("content"));
			}
		} catch (Exception e) {
			System.out.println("getCommcontent에러_"+e);
		} finally {
			closeDB();
		}
		return Obj;
	}
	
	/*이미 저장돼있던 첨부파일 값 가져옴*/
	public String getPhoto(int num) {
		String photo="";
		try {
			con=getConnection();
			sql="select photo from community where num=?";
			pstmt=con.prepareStatement(sql);
			pstmt.setInt(1, num);
			rs=pstmt.executeQuery();
			while(rs.next()){
				photo=rs.getString(1);
			}
		}catch(Exception e){
			System.out.println("getPhoto에러_"+e);
		}finally {
			closeDB();
		}
		return photo;
	}
	
	public void deletcont(int num){
		try {
			con=getConnection();
			sql="delete from community where num=?";
			pstmt=con.prepareStatement(sql);
			pstmt.setInt(1, num);
			pstmt.executeUpdate();
		}catch(Exception e){
			System.out.println("deletcont에러_"+e);
		}finally {
			closeDB();
		}
	}

	/*좋아요 추가하기*/
	public void plusgood(int num) {
		try {
			con=getConnection();
			sql="update community set good=good+1 where num=?";
			pstmt=con.prepareStatement(sql);
			pstmt.setInt(1, num);
			pstmt.executeUpdate();
		} catch (Exception e) {
			System.out.println("plusgood오류_"+e);
		} finally {
			closeDB();
		}
	}
	
	/*해당 게시글의 마지막 */
	public int getLastcommentnum(int num) {
		int lastnum = 0;
		try {
			con=getConnection();
			sql="select max(num) from comment_commu where com_num=?";
			pstmt=con.prepareStatement(sql);
			pstmt.setInt(1, num);
			rs = pstmt.executeQuery();
			if(rs.next()){
				lastnum = rs.getInt(1);
				System.out.println(lastnum);
			}
		} catch (Exception e) {
			System.out.println("getLastcommentnum에러_"+e);
		} finally {
			closeDB();
		}
		return lastnum;
	}

	public JSONObject getInfo(int num) {
		JSONObject jobj = new JSONObject();
		try {
			con = getConnection();
			sql = "select email, name "
					+ "from members "
					+ "where email = (select email from comment_commu where num=?)";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, num);
			if(rs.next()){
				jobj.put("email", rs.getString(1));
				jobj.put("name", rs.getString(2));
			}
		} catch (Exception e) {
			System.out.println("getInfo에러_"+e);
		} finally {
			closeDB();
		}
		
		return jobj;
	}
	
}
