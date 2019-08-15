package net.community.action;

import java.io.File;
import java.util.ArrayList;
import java.util.Enumeration;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import net.community.db.communityBean;
import net.community.db.communityDAO;

public class modifycomAction implements Action{

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("커뮤니티 글 수정Action");
		String enc = request.getParameter("enc");
		System.out.println("enc:"+enc);
		if(enc.equals("normal")){//파일변경없이 내용만 수정된 경우
			int num = Integer.parseInt(request.getParameter("num"));
			String content = request.getParameter("content");
			communityDAO dao = new communityDAO();
			communityBean bean = new communityBean();
			bean.setContent(content);
			bean.setNum(num);
			dao.modifycont(bean);
			
		}else{//파일변경이 된경우
			ServletContext context = request.getSession().getServletContext();
			String realFolder = context.getRealPath("communityphoto");
			System.out.println(realFolder); //저장경로
			
			int max = 1024 * 1024 * 10;
			 MultipartRequest multi = new MultipartRequest(request, realFolder,  max, "UTF-8",  new DefaultFileRenamePolicy());
			 //ArrayList saveFiles = new ArrayList(); //저장된 이름
			 //ArrayList originFiles = new ArrayList();
			
			 Enumeration e = multi.getFileNames();
			 String email = multi.getParameter("email");
			 String content = multi.getParameter("content");
			 int num = Integer.parseInt(multi.getParameter("num"));
			 
			 //String photo ="";

			 String filename = (String)e.nextElement();
			 String saveFile = multi.getFilesystemName(filename);
			 String originFile = multi.getOriginalFileName(filename);
			 
			 communityDAO dao = new communityDAO();
			 
			 if(saveFile==null || saveFile.equals("null")){ 
				 //파일이 있었다가 지워진 경우 => 실제 저장된 파일도 지워줘야한다.
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
			 }
			 
			 communityBean bean = new communityBean();
			 bean.setNum(num);
			 bean.setPhoto(saveFile);
			 bean.setContent(content);
			 
			 dao.modifycontfile(bean);
		}
		ActionForward forward = new ActionForward();
		forward.setRedirect(true);
		forward.setPath("./mainfromcommu.co");
		
		return forward;
	}
	
}
