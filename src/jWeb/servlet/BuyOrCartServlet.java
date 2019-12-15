package jWeb.servlet;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.mail.MessagingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import jWeb.pojo.BuyOrBrowse;
import jWeb.pojo.Goods;
import jWeb.pojo.User;
import jWeb.rand.randNo;
import jWeb.sendMail.MailUtils;
import jWeb.service.BOBService;
import jWeb.service.GoodsService;
import jWeb.service.UserService;

/**
 * ���빺�ﳵ/�����¼����bTypeΪ1ʱ���빺�ﳵ����bTypeΪ2ʱ����
 * ���д���bIdʱ���ڹ��ﳵ�й���
 */
@WebServlet("/BuyOrCartServlet")
public class BuyOrCartServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//������������ʽ
		req.setCharacterEncoding("utf-8");
		//������Ӧ�����ʽ
			resp.setCharacterEncoding("utf-8");
			resp.setContentType("text/html;charset=utf-8");
		//��ȡ������Ϣ
			int flag=0;
			//�̳ǽ��м��빺�ﳵ������ֱ�ӹ������
			if(req.getParameter("bId")==null) {
				BuyOrBrowse b=new BuyOrBrowse();
				//��ȡbId����
				randNo r=new randNo();
				//��ȡ��ǰʱ��
		        Date date = new Date();
		    	SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		    	String time=dateFormat.format(date);
		    	//��Ҫʹ�õ�����
		    	String uId=req.getParameter("uId");
		    	String gId=req.getParameter("gId");
		    	int bType=Integer.parseInt(req.getParameter("bType"));
		    	int p=Integer.parseInt(req.getParameter("gPrice"));
		    	//����/��������
				int n=Integer.parseInt(req.getParameter("buyNumber"));
				//�ܼ�
				int bp=n*p;
				b.setbId(r.createOtherNo());
				b.setbTime(time);
				b.setuId(uId);
				b.setgId(gId);
				b.setbType(bType);
				b.setbPrice(bp);
				b.setbNumber(n);
			//����������Ϣ
				BOBService bob=new BOBService();
				flag=bob.AddRecoding(b);
				if(bType==2) {
					UserService u=new UserService();
					User user=u.reduceMoney(uId,bp);
					req.getSession().setAttribute("user", user);
					GoodsService g=new GoodsService();
					Goods goods=g.reduceNumber(gId,n);
					req.getSession().setAttribute("goodsDetail", goods);

					//�����ʼ�
					String msg="���������˺�Ϊ:"+uId+"���û���Ϊ��"+user.getuName()+"���˻���"
							+ "�ɹ�������"+n+"����Ʒ��"+"����Ʒ���Ϊ��"+gId+"��Ʒ��Ϊ��"+goods.getgName()
							+",�ܹ�����:"+bp+"Ԫ��";
					System.out.println(msg);
					try {
						MailUtils.sendMail(user.getuEmail(), msg);
					} catch (MessagingException e) {
						e.printStackTrace();
					}
				}else {
					System.out.println("uId:"+uId+"���빺�ﳵ"+n+"��"+"gId:"+gId+"��:"+bp+"Ԫ");
				}
			}
			//���ﳵ�й���
			else {
				String bId=req.getParameter("bId");
				String uId=req.getParameter("uId");
				//�ܼ�
				int bp=Integer.parseInt(req.getParameter("bPrice"));
				//����
				int n=Integer.parseInt(req.getParameter("bNumber"));
				//�ҵ�������¼����Ϣ
				BOBService bob=new BOBService();
				BuyOrBrowse b=bob.findRecoding(bId);
				System.out.println(b);
				
				String gId=b.getgId();
				//�ҵ������
				UserService u=new UserService();
				User user=u.findUser(uId);
				System.out.println(user);
				if(user.getuMoney()<bp) {
					//Ǯ����������
					flag=2;
				}else {
					//�ҵ��������
					GoodsService g=new GoodsService();
					Goods goods=g.findGoodsUseId(gId);
					System.out.println(goods);
					if(goods.getgNumber()<n) {
						//�������㣬����
						flag=1;
					}else {
						//������
						//ɾ��ԭ���ﳵ��¼
						flag=bob.deleteRecoding(bId);
						//��ȥ��Ʒ����
						g.reduceNumber(gId, n);
						//��Ϊ�����¼
						b.setbType(2);
						//��ȡ��bId����
						randNo r=new randNo();
						b.setbId(r.createOtherNo());
						//��ȡ��ǰʱ��
				        Date date = new Date();
				    	SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				    	String time=dateFormat.format(date);
				    	b.setbTime(time);
				    	//����
						bob.AddRecoding(b);
						System.out.println(b);
						//����
						flag=0;
						
						//�����ʼ�
						String msg="���������˺�Ϊ:"+uId+"���û���Ϊ��"+user.getuName()+"���˻���"
								+ "�ɹ�������"+n+"����Ʒ��"+"����Ʒ���Ϊ��"+gId+"��Ʒ��Ϊ��"+goods.getgName()
								+",�ܹ�����:"+bp+"Ԫ��";
						System.out.println(msg);
						try {
							MailUtils.sendMail(user.getuEmail(), msg);
						} catch (MessagingException e) {
							e.printStackTrace();
						}
					}
				}
			}
		//��Ӧ������
			resp.getWriter().write(new Gson().toJson(flag));
	}
       


}
