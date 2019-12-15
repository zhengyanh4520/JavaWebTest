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
 * ��ֵ
 */
@WebServlet("/AddMoneyServlet")
public class AddMoneyServlet extends HttpServlet {
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
			int m=(int) Float.parseFloat(req.getParameter("uMoney"));
			int money=Integer.parseInt(req.getParameter("rand"));
			int flag=0;
		//����������Ϣ
			UserService u=new UserService();
			User user=u.recharge(uId,money);
			if(m+money==user.getuMoney()) {
				flag=1;
			}
			System.out.println(m);
			System.out.println(money);
			System.out.println(user.getuMoney());
			System.out.println("��ֵ�����"+flag);
		    //��Ӧ������
			req.getSession().setAttribute("user", user);
			resp.getWriter().write(new Gson().toJson(flag));	
	}

}
