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
 * 查询浏览记录，uId=admin时查找全部（此时为管理员查找）
 */
@WebServlet("/LoadBrowseServlet")
public class LoadBrowseServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//设置请求编码格式
		req.setCharacterEncoding("utf-8");
		//设置响应编码格式
			resp.setCharacterEncoding("utf-8");
			resp.setContentType("text/html;charset=utf-8");
		//获取请求信息
		//处理请求信息
			List<BuyOrBrowse> list=new ArrayList<BuyOrBrowse>();
			String uId=req.getParameter("uId");
			if("1".equals(req.getParameter("uType"))) {
				uId="admin";
			}
			BOBService b=new BOBService();
			list=b.searchBrowse(uId);
		//响应处理结果
			resp.getWriter().write(new Gson().toJson(list));
	}

}
