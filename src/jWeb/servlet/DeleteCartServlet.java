package jWeb.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import jWeb.service.BOBService;

/**
 * �Ƴ����ﳵ�еļ�¼
 */
@WebServlet("/DeleteCartServlet")
public class DeleteCartServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//������������ʽ
		req.setCharacterEncoding("utf-8");
		//������Ӧ�����ʽ
			resp.setCharacterEncoding("utf-8");
			resp.setContentType("text/html;charset=utf-8");
		//��ȡ������Ϣ
			String bId=req.getParameter("bId");
		//����������Ϣ
			BOBService b=new BOBService();
			int flag=b.deleteRecoding(bId);
			System.out.println("ɾ����ƷgId��"+bId+"flag:"+flag+"(0ʧ��1�ɹ�)");
		//��Ӧ������
			resp.getWriter().write(new Gson().toJson(flag));
	}

}
