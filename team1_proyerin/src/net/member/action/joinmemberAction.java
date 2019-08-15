package net.member.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.member.db.membersBean;
import net.member.db.membersDAO;
import net.member.db.userlikeBean;
import net.member.db.userlikeDAO;

public class joinmemberAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("MemberJoinAction execute()");

		//회원가입 폼(join.jsp)에서 입력한 한글 정보들이 깨지지 않도록 인코딩 설정
		request.setCharacterEncoding("utf-8");
		
		/*1,2클라이언트 요청 정보 받기*/
		/*3. 자바빈 DTO에 저장*/
		/*회원가입폼(join.jsp)에서 입력한 정보들을 MemberBean객체(자바빈)에 저장한다.*/
		membersBean mb = new membersBean();
		mb.setEmail(request.getParameter("email"));
		mb.setPassword(request.getParameter("password"));
		mb.setName(request.getParameter("name"));
		mb.setGender(request.getParameter("gender"));
		mb.setBirth(request.getParameter("birth"));

		//회원가입 성공 여부를 담을 변수 선언
		boolean result = false;

		//MemberBean객체를 매개변수로 DAO클래스의 insertMember()메소드에 전달하여 회원가입 처리
		membersDAO mdao = new membersDAO();
		
		String[] likeArr = request.getParameterValues("like");
		if(likeArr.length==0){
			mb.setLikegenre(0);
			//회원가입 내용을 담고 있는 mb객체를 전달하여 가입에 성공하면 true 실패 false
			result = mdao.insertMember(mb);
		}else{
			mb.setLikegenre(1);
			userlikeBean userlike = new userlikeBean();
			String likelist="";
			for(String s : likeArr){
				likelist+=(s+"/");
			}
			System.out.println(likelist);
			userlike.setEmail(request.getParameter("email"));
			userlike.setGenre(likelist);
			
			//회원가입 내용을 담고 있는 mb객체를 전달하여 가입에 성공하면 true 실패 false
			result = mdao.insertMember(mb);
			
			userlikeDAO likedao = new userlikeDAO();
			likedao.insertLike(userlike); //선호장르 추가
		}
		
		
		
		
		//회원가입실패시 null반환
		if(result==false){
			System.out.println("회원가입 실패");
			return null;
		}
		
		//회원가입 성공시 로그인페이지로 이동/페이지 이동 방식 여부 값, 이동페이지 경로 값 저장하여 리턴해주는 객체 생성
		ActionForward forward = new ActionForward();
		//페이지 이동 방식 여부 값 true로 저장
		//sendRedirect() 방식은 이동할 페이지 주소 경로 노출
		forward.setRedirect(true);
		// ./member/login.jsp이동할 페이지 주소 저장
		forward.setPath("./login.me");
		System.out.println("여기");
		return forward;
		
	}
	
	

	
	
	
}
