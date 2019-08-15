package net.playlist.action;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.playlist.action.Action;
import net.playlist.action.ActionForward;
@WebServlet("*.doplay")
public class PlaylistFrontController extends HttpServlet{

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doplayList(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doplayList(request, response);
	}
	protected void doplayList(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String RequestURI=request.getRequestURI();
		System.out.println(RequestURI);
		//   /FunWeb2    8
		String contextPath=request.getContextPath();
		System.out.println(contextPath);
		System.out.println(contextPath.length());
		//  /BoardList.bo
		String command=RequestURI.substring(contextPath.length());
		System.out.println(command);
		//주소비교
		ActionForward forward=null;
		Action action=null;
		if(command.equals("charge.doplay")){
			action = new chargeAction();
			
			try {
				forward = action.execute(request, response);
				
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		else if(command.equals("delpPlaylist.doplay")){
			action = new delPlaylistAction();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		else if(command.equals("getPlaylist.doplay")){
			action = new getPlaylistAction();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		else if (command.equals("play.doplay")) {
			action = new playAction();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	
		if(forward!=null){
			if(forward.isRedirect()){//true
				response.sendRedirect(forward.getPath());
			}else{//false
				RequestDispatcher dispatcher=request.getRequestDispatcher(forward.getPath());
				dispatcher.forward(request, response);
			}
		}
	}
	
}
