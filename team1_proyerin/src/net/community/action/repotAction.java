package net.community.action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;

import net.community.db.communityDAO;

public class repotAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		int num = Integer.parseInt(request.getParameter("num"));
		System.out.println("num:"+num);
		communityDAO dao = new communityDAO();
		JSONObject obj = dao.getInfo(num);
		PrintWriter writer = response.getWriter();
		writer.print(obj);	
		return null;
	}
	
}
