package jWeb.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import jWeb.pojo.BuyOrBrowse;
import jWeb.service.BOBService;

/**
 * �鿴�û����ﳵ
 */
@WebServlet("/LoadCartServlet")
public class LoadCartServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//������������ʽ
		req.setCharacterEncoding("utf-8");
		//������Ӧ�����ʽ
			resp.setCharacterEncoding("utf-8");
			resp.setContentType("text/html;charset=utf-8");
		//��ȡ������Ϣ
		//����������Ϣ
			List<BuyOrBrowse> list=new ArrayList<BuyOrBrowse>();
			String uId=req.getParameter("uId");
			BOBService b=new BOBService();
			list=b.searchCart(uId);
		//��Ӧ������
			resp.getWriter().write(new Gson().toJson(list));
	}

}
