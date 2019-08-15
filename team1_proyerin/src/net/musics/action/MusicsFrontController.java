package net.musics.action;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.musics.action.Action;
import net.musics.action.ActionForward;
/**
 * Servlet implementation class ServletTest
 */
@WebServlet("*.mu")
public class MusicsFrontController extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doHandle(request, response);
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doHandle(request, response);
	}
	
	protected void doHandle(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String RequestURI=request.getRequestURI();
		System.out.println(RequestURI);

		String contextPath=request.getContextPath();

		String command=RequestURI.substring(contextPath.length());
		System.out.println(command);
					
		/*�ּ� ��*/	
		//������ �̵� ��� ���� ��,�̵������� ��� �� ���� �Ͽ� ���� ���ִ� ��ü�� ������ �������� ���� 
		ActionForward forward=null;
	
		//�ڽ� Action ��ü���� ���� �������̽� Ÿ���� �������� ����
		Action action=null;
			
		//Top.jsp����.. join��ũ�� ������ ȸ�������������� �̵��ϴ� ��û�� ��� ������..
		if(command.equals("/commentMuDel.mu")){
		
			action= new commentMuDelAction();
			try {
				

				// ������ �̵� ��� ���� �� true��...
				// �̵��������� �ּ� (???)�� ��� �ִ�..
				//new ActionForward()��ü�� ���� �޴´�.
				
				forward=action.execute(request, response);
						
			} catch (Exception e) {
				e.printStackTrace();
			}
		
		
			

		}else if(command.equals("/commentMuMod.mu")){ 
			
			action = new commentMuModAction();
			
			try {
				forward=action.execute(request, response);
			} catch (Exception e) {
				
			}

		}else if(command.equals("/commentMuWrt.mu")){
			

			action=new commentMuWrtAction();
			
			try {

				forward=action.execute(request, response);
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			
				
		}else if(command.equals("/deletemu.mu")){
			
			
			action=new deletemuAction();
			try {
				  

				forward=action.execute(request, response); 

			} catch (Exception e) {
				e.printStackTrace();
			}
		}	
		else if(command.equals("/modifymu.mu")){
			
				
			action= new modifymuAction();
		
			try {
				forward=action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		else if(command.equals("/writemu.mu")){
		
				
			action= new writemuAction();
		
			try {
				forward=action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		
		
		//�ּ� �̵�
		if(forward!=null){ //new ActionForward()��ü�� ���� �ϰ�..
			if(forward.isRedirect()){//true -> sendRedirect() ����ϋ�..
				//�����̷�Ʈ ������� ������ �̵�!  ������ �ּ� ��� ���� �� 

				//ȭ�� �̵��� session���� ����
				response.sendRedirect(forward.getPath());
				
			}else{//false -> forward() ����϶�...
				
				RequestDispatcher dispatcher=request.getRequestDispatcher(forward.getPath());
				dispatcher.forward(request, response);
			}
		}//if 
		
	//	doProcess �޼ҵ� ��
	//���� ��

		
	}
}
