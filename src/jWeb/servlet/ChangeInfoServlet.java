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
 * 修改个人信息
 */
@WebServlet("/ChangeInfoServlet")
public class ChangeInfoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//设置请求编码格式
		req.setCharacterEncoding("utf-8");
		//设置响应编码格式
			resp.setCharacterEncoding("utf-8");
			resp.setContentType("text/html;charset=utf-8");
		//获取请求信息
			User u=(User) req.getSession().getAttribute("user");
			u.setuEmail(req.getParameter("uEmail"));
			u.setuName(req.getParameter("uName"));
			u.setuIntroduce(req.getParameter("uIntroduce"));
			u.setuPsw(req.getParameter("uPsw"));
			u.setuPhone(req.getParameter("uPhone"));
			u.setuType(Integer.parseInt(req.getParameter("uType")));
			if("man".equals(req.getParameter("uSex"))) {
				u.setuSex("男");
			}else {
				u.setuSex("女");
			}
		//处理请求信息
			UserService us=new UserService();
			int flag=us.changeInfo(u);
		//响应处理结果
			req.getSession().setAttribute("user", u);
			resp.getWriter().write(new Gson().toJson(flag));
	}
       


}
