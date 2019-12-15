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
import jWeb.service.GoodsService;

/**
 * ����������Ʒ
 */
@WebServlet("/LoadGoodsServlet")
public class LoadGoodsServlet extends HttpServlet {
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
			List<Goods> goods=new ArrayList<Goods>();
			if(req.getParameter("uId")!=null) {
				String uId=req.getParameter("uId");
				GoodsService g=new GoodsService();
				goods=g.uidLoadGoods(uId);
			}else {
				GoodsService g=new GoodsService();
				goods=g.LoadGoods();
			}
		//��Ӧ������
			System.out.println(goods);
			resp.getWriter().write(new Gson().toJson(goods));
	}

}
