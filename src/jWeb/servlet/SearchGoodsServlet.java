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
 * ����ĳһ��Ʒ
 */
@WebServlet("/SearchGoodsServlet")
public class SearchGoodsServlet extends HttpServlet {
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
			String gName="";
			String gClass="";
			String gId="";
			Goods goods=new Goods();
			List<Goods> g=new ArrayList<Goods>();
			if(req.getParameter("gName")!=null)
			{
				gName=req.getParameter("gName");
				System.out.println("gName:"+gName);
				GoodsService u=new GoodsService();
				g=u.findGoods(gName);
			}else if(req.getParameter("gClass")!=null) {
				gClass=req.getParameter("gClass");
				System.out.println("gClass:"+gClass);
				GoodsService u=new GoodsService();
				g=u.findClassGoods(gClass);
			}else if(req.getParameter("gId")!=null) {
				gId=req.getParameter("gId");
				String uId=req.getParameter("uId");
				System.out.println("gId:"+gId +" uId:"+uId);
				GoodsService u=new GoodsService();
				goods=u.findIdGoods(uId,gId);
				req.getSession().setAttribute("goodsDetail", goods);
			}
		
		//��Ӧ������
			System.out.println(g);
			resp.getWriter().write(new Gson().toJson(g));
	}
       


}
