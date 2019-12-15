package jWeb.servlet;

import java.io.IOException;

import javax.mail.MessagingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import jWeb.sendMail.MailUtils;
import jWeb.service.UserService;

/**
 * �û�ע��
 */
@WebServlet("/RegisterServlet")
public class RegisterServlet extends HttpServlet {
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
			String uName=req.getParameter("uName");
			String uIntroduce=req.getParameter("uIntroduce");
			String uPsw=req.getParameter("uPsw");
			String uSex="";
			if("man".equals(req.getParameter("uSex"))) {
				uSex="��";
			}else {
				uSex="Ů";
			}
			String uPhone=req.getParameter("uPhone");
			String uEmail=req.getParameter("uEmail");
			int uType=0;
			if("user".equals(req.getParameter("uType"))){
				uType=0;
			}else {
				//����Ա
				uType=1;
			}
		//����������Ϣ
			UserService u=new UserService();
			int flag=u.checkRegisterUser(uId,uName,uIntroduce,uPsw,uType,uSex,uPhone,uEmail);
		//��Ӧ������
			if(flag==1)
			{
				String msg="��ӭע��ٻ��̵꣬��������Ե�¼�������ҳ�ˣ�";
				try {
					MailUtils.sendMail(uEmail, msg);
				} catch (MessagingException e) {
					e.printStackTrace();
				}
			}
			resp.getWriter().write(new Gson().toJson(flag));
	}
       


}
