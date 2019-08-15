package net.point.action;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.point.action.Action;
import net.point.action.ActionForward;
@WebServlet("*.point")
public class PointFrontController extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		point(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		point(request, response);
	}

	protected void point(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
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
		if (command.equals("getPoint.point")) {
			action = new getPointAction();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if (command.equals("getSponsor.point")) {
			action = new getSponsorAction();
		
		try {
			forward = action.execute(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
		}
		
		else if(command.equals("sendPointAction.point")){
			action = new sendPointAction();
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