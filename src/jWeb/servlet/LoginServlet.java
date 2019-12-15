package jWeb.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import jWeb.pojo.User;
import jWeb.service.UserService;

/**
 * �û���¼
 */
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//������������ʽ
		req.setCharacterEncoding("utf-8");
		//������Ӧ�����ʽ
			resp.setCharacterEncoding("utf-8");
			resp.setContentType("text/html;charset=utf-8");
		//��ȡ������Ϣ
			String uId=req.getParameter("uId");
			String uPsw=req.getParameter("uPsw");
			int uType=0;
			if("user".equals(req.getParameter("uType"))){
				uType=0;
			}else {
				//����Ա
				uType=1;
			}
		//����������Ϣ
			UserService u=new UserService();
			int flag=u.checkLoginUser(uId,uPsw,uType);
		//��Ӧ������
			if(flag==0) {
				User user=u.findUser(uId);
				System.out.println("��¼�ɹ�");
				req.getSession().setAttribute("user", user);
			}else {
				System.out.println("�˺�/������� "+flag);
				resp.getWriter().write(new Gson().toJson(flag));
			}		
	}



}
