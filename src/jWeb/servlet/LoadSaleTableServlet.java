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

import jWeb.pojo.Goods;
import jWeb.service.BOBService;

/**
 * ���س����۱���ͳ�Ƴ�����������Ʒ����Ϣ
 */
@WebServlet("/LoadSaleTableServlet")
public class LoadSaleTableServlet extends HttpServlet {
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
			List<Goods> list=new ArrayList<Goods>();
			BOBService b=new BOBService();
			list=b.searchSaleTable();
		//��Ӧ������
			resp.getWriter().write(new Gson().toJson(list));
	}

}
