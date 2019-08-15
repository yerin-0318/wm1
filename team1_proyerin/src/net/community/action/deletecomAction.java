package net.community.action;

import java.io.File;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.community.db.communityDAO;

public class deletecomAction implements Action{

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//System.out.println("커뮤니티 글 삭제Action");
		ServletContext context = request.getSession().getServletContext();
		String realFolder = context.getRealPath("communityphoto");
		//System.out.println(realFolder); //저장경로
		
		communityDAO dao = new communityDAO();
		int num = Integer.parseInt(request.getParameter("num"));
		
		//파일 삭제하기
		String befphoto = dao.getPhoto(num);
		File file = new File(realFolder+"/"+befphoto);
		//System.out.println("파일 위치:"+realFolder+"/"+befphoto);
		if(file.exists()){
			if(file.delete()){
				System.out.println("파일 삭제 성공");
			}else{
				System.out.println("파일 삭제 실패");
			}
		}else{
			System.out.println("파일이 존재하지 않습니다.");
		}
		
		
		dao.deletcont(num); //투플 지우기		
		
		ActionForward forward = new ActionForward();
		forward.setRedirect(true);
		forward.setPath("./mainfromcommu.co");
				
		return forward;
	}

}
