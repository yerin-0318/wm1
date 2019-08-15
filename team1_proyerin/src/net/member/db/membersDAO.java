package net.member.db;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.Properties;

import javax.sql.DataSource;

import org.json.simple.JSONObject;

import net.member.action.GoogleAuthentication;

import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.Message.RecipientType;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.naming.Context;
import javax.naming.InitialContext;

import java.sql.Connection;

public class membersDAO {
	
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

	public boolean insertMember(membersBean mb){
		
		int result = 0;
		
		try{
			
			con = getConnection();
			sql="insert into members(email,password,name,gender,birth,likegenre) values(?,?,?,?,?,?)";
			pstmt=con.prepareStatement(sql);
			pstmt.setString(1,mb.getEmail());
			pstmt.setString(2,mb.getPassword());
			pstmt.setString(3,mb.getName());
			pstmt.setString(4,mb.getGender());
			pstmt.setString(5,mb.getBirth());
			pstmt.setInt(6,mb.getLikegenre());
			
			result = pstmt.executeUpdate();
			
			if(result != 0){
				return true;
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			closeDB();
		}
		
		return false;
		
	}

	/* email 중복체크 메서드 */
	public boolean dupEmail(String email) {
		boolean result = true;

		try {
			
			con = getConnection();
			sql="SELECT email FROM members WHERE email=?";
			pstmt=con.prepareStatement(sql);
			
			pstmt.setString(1, email);
			
			rs=pstmt.executeQuery();
			
			if (rs.next()) {
				result = true;
			} else {
				result = false;
			}
		} catch (Exception e) {
			System.out.println("dupEmail()에서 에러" + e);
			e.printStackTrace();
		} finally {
			closeDB();
		}

		return result;
	}
	
	/* 6자리 인증번호 생성 메서드 */
	public String authNum() {
		StringBuffer authNum = new StringBuffer();

		for (int i = 0; i < 6; ++i) {
			int randNum = (int) (Math.random() * 10.0D);
			authNum.append(randNum);
		}

		return authNum.toString();
	}
	
	/* 인증메일 전송 메서드 */
	public boolean sendEmail(String email, String authNum) {
		boolean result = false;
		String sender = "yehamailtest@gamil.com";
		String subject = "watermelon인증번호입니다.";
		String content = "안녕하세요 " + email + "님, <br>" + "귀하의 인증번호는    [<b>" + authNum + "</b>]   입니다.";

		try {
			Properties properties = System.getProperties();
			properties.put("mail.smtp.starttls.enable", "true");
			properties.put("mail.smtp.host", "smtp.gmail.com");
			properties.put("mail.smtp.auth", "true");
			properties.put("mail.smtp.port", "587");
			Authenticator auth = new GoogleAuthentication();
			Session session = Session.getDefaultInstance(properties, auth);
			Message message = new MimeMessage(session);
			Address senderAd = new InternetAddress(sender);
			Address receiverAd = new InternetAddress(email);
			message.setHeader("content-type", "text/html;charset=UTF-8");
			message.setFrom(senderAd);
			message.addRecipient(RecipientType.TO, receiverAd);
			message.setSubject(subject);
			message.setContent(content, "text/html;charset=UTF-8");
			message.setSentDate(new Date());
			Transport.send(message);
			result = true;
		} catch (Exception e) {
			result = false;
			System.out.println("Error in SendEmail()");
			e.printStackTrace();
		} finally {
			closeDB();
		}

		return result;
	}
	

	
/*	public membersBean modifymembers(String email){
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "";
		membersBean mb = new membersBean();
		
		try{
			
			System.out.println("email" + email);
			con = getConnection();
			sql = "select * from members where email=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, email);
			rs = pstmt.executeQuery();
			
			if(rs.next()){
				
				mb.setEmail(rs.getString("email"));
				mb.setPassword(rs.getString("password"));
				mb.setName(rs.getString("name"));
				mb.setGender(rs.getString("gender"));
				mb.setBirth(rs.getString("birth"));
				mb.setLikegenre(rs.getInt("likegenre"));
				
			}
			
		}catch(Exception e){
			System.out.println("modifymember 오류" + e);
			e.printStackTrace();
		}finally{
			closeDB();
		}	
		return mb;
	}*/
	
	public JSONObject selectModify(String email){
		JSONObject Obj = new JSONObject();
		try{
			con=getConnection();
			sql = "select * from members where email=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, email);
			rs = pstmt.executeQuery();
			while(rs.next()){
				Obj.put("password", rs.getString("password"));
				Obj.put("name", rs.getString("name"));
				Obj.put("gender", rs.getString("gender"));
				Obj.put("birth", rs.getString("birth"));
				Obj.put("likegenre", rs.getInt("likegenre"));
				
				}
			} catch (Exception e){
				System.out.println("selectModify에러 : "+e);
			} finally{
				closeDB();
			}
		System.out.println("jsonObj:" +Obj);
		return Obj;
		
		
	}
	
	public void modifypro(membersBean mb){
		Connection con = null;
		PreparedStatement pstmt = null;
		String sql = "";
		membersBean dto = new membersBean();
		
		try{
			
			con = getConnection();
			sql = "update members set password=?, name=?, gender=?, birth=? where email=?";
			
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, mb.getPassword());
			pstmt.setString(2, mb.getName());
			pstmt.setString(3, mb.getGender());
			pstmt.setString(4, mb.getBirth());
		//	pstmt.setInt(5, mb.getLikegenre());
			pstmt.setString(5, mb.getEmail());
			
			pstmt.executeUpdate();
		}catch (Exception e){
			System.out.println("updatemodify 오류개빡쳐"+e);
			e.printStackTrace();
		}finally{
			closeDB();
		}
		
	}
	
	
	
	public int loginCheck(String email,String password){
	      
	      int checking=-1;//1 -> 아이디, 비밀번호 맞음
	      
	      try {
	         con=getConnection();
	         //sql : 이메일에 해당하는 password 가져오기
	         sql="select password from members where email=?";
	         pstmt=con.prepareStatement(sql);
	         pstmt.setString(1, email);
	         rs=pstmt.executeQuery();
	         
	         
	         if(rs.next()){
	            //로그인시 입력한 password와 db password 일치할시
	            if(password.equals(rs.getString("password"))){
	               checking=1;//아이디 맞음,비밀번호 맞음 
	            }else {
	               checking=0;//아이디 맞음, 비밀번호틀림 
	            }
	         
	         }else{
	            checking=-1; //아이디 자체가없음
	            
	         }
	      } catch (Exception e) {
	         e.printStackTrace();
	      }finally{
	         closeDB();
	      }
	      return checking;
	   }
	
	public boolean dropDB(String email){
		
		boolean result = false;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "";
		membersBean d = new membersBean();
		

		try {


			con = getConnection();
			sql = "delete from members where email=?";
			
		
				pstmt = con.prepareStatement(sql);
	
				pstmt.setString(1, email);
				

			pstmt.executeUpdate();

			

		} catch (Exception e) {
			System.out.println("drop오류 : "+e);
			e.printStackTrace();
			
		} finally {
			closeDB();
		}
		return result;
	}
	
	//중복아이디확인
	public boolean overlappedID(String id) {
		
		//아이디 중복 또는 중복이아니다 라는 판별값을 저장할 변수 
		boolean result = false;
		
		try {
			//커넥션풀로부터 커넥션 얻기
			con = getConnection();
			
			//오라클의 decode()함수를 이용하여 서블릿에서 전달된 ID에 해당하는 데이터를 검색하여
			//true또는 false반환하는데...
			//검색한 갯수가 1(검색한 레코드가 존재하면)이면 true를 반환,
			//존재 하지 않으면 false를 문자열로 반환하여 조회하는 SQL문을 작성
			String query = "select * from members where email = ?";
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, id);
			ResultSet rs = pstmt.executeQuery();
			result = rs.next();   
			pstmt.close();
			rs.close();
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
}
