package net.ben.action;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.ben.action.*;

@WebServlet("*.ben")
public class BenFrontController extends HttpServlet{

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		super.doGet(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		super.doPost(req, resp);
	}
	protected void ben(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String RequestURI=request.getRequestURI();
		System.out.println(RequestURI);
		//   /FunWeb2    8
		String contextPath=request.getContextPath();
		System.out.println(contextPath);
		System.out.println(contextPath.length());
		
		String command=RequestURI.substring(contextPath.length());
		System.out.println(command);
		//주소비교
		ActionForward forward=null;
		Action action=null;
		
		if(command.equals("endben.ben")){
			action = new endbenAction();
			try {
				forward = action.execute(request, response);
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		else if(command.equals("getben.ben")){
			action = new getbenAction();
			
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		else if(command.equals("startben.ben")){
			action = new startbenAction();
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
