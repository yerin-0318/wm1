package net.community.action;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import net.community.db.communityBean;
import net.community.db.communityDAO;

public class writecomAction implements Action{

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("writecomAction");
		
		//request.setCharacterEncoding("utf-8");
		
		ServletContext context = request.getSession().getServletContext();
		String realFolder = context.getRealPath("communityphoto");
		System.out.println(realFolder);
		
		int max = 1024 * 1024 * 10;
			 MultipartRequest multi = new MultipartRequest(request, realFolder,  max, "UTF-8",  new DefaultFileRenamePolicy());
			 ArrayList saveFiles = new ArrayList(); //저장된 이름
			 ArrayList originFiles = new ArrayList();
			 	
			 
			 Enumeration e = multi.getFileNames();
			 String email = multi.getParameter("email");
			 String content = multi.getParameter("content");
			 
			 String photo ="";

			 	while(e.hasMoreElements()){
			 		String filename = (String)e.nextElement();			 		
			 		saveFiles.add(multi.getFilesystemName(filename));			 		
			 		originFiles.add(multi.getOriginalFileName(filename));	
			 		
			 	}
			 	
			 	for(int i=0;i<saveFiles.size();i++){
			 		photo += (String)saveFiles.get(i)+"";
			 	}

		 		communityBean comm = new communityBean();
		 		comm.setEmail(email);
		 		comm.setContent(content);
			 	comm.setPhoto(photo);
				
			 	communityDAO dao = new communityDAO();
			 	
				dao.insertcontent(comm);
				
				ActionForward forward = new ActionForward();
				forward.setRedirect(true);
				forward.setPath("./mainfromcommu.co");
				
				
				return forward;
	}
	
}
