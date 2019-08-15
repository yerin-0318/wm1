package net.streamer.action;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.streamer.action.Action;
import net.streamer.action.ActionForward;
@WebServlet("*.st")
public class StreamerFrontController extends HttpServlet{

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	Streamer(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	Streamer(request, response);
	}
	
	protected void Streamer(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String RequestURI = request.getRequestURI();
		System.out.println(RequestURI);
		// /FunWeb2 8
		String contextPath = request.getContextPath();
		System.out.println(contextPath);
		System.out.println(contextPath.length());
		// /BoardList.bo
		String command = RequestURI.substring(contextPath.length());
		System.out.println(command);
		// 주소비교
		ActionForward forward = null;
		Action action = null;
		
		if(command.equals("getPaid.st")){
			action = new getPaid_strAction();
			
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		else if(command.equals("report.st")){
			action = new reportAction();
			
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
