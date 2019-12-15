package jWeb.servlet;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.google.gson.Gson;

import jWeb.pojo.Goods;
import jWeb.rand.randNo;
import jWeb.service.GoodsService;

/**
 * �����Ʒ
 */
@WebServlet("/AddGoodsServlet")
public class AddGoodsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//������������ʽ
		req.setCharacterEncoding("utf-8");
	//������Ӧ�����ʽ
		resp.setCharacterEncoding("utf-8");
		resp.setContentType("text/html;charset=utf-8");
	
	//����DiskFileItemFactory��������
		DiskFileItemFactory factory=new DiskFileItemFactory();
	//�����ļ�����Ŀ¼�������������½�
		String webRoot=this.getServletContext().getRealPath("");
		String tempPath=webRoot+"tempFolder";
		Goods goods=new Goods();
		randNo r=new randNo();
		goods.setgId(r.createOtherNo());
		File f=new File(tempPath);
		System.out.println(tempPath);
		if(!f.exists()) {
			f.mkdirs();
		}
	//�����ļ�����·��
		factory.setRepository(f);
	//����servletFileUpload����
		ServletFileUpload fu=new ServletFileUpload(factory);
	//�����ַ�����
		fu.setHeaderEncoding("utf-8");
	try{
	//����request�õ��ϴ��ļ���FileItem����
		List<FileItem> fileItems=fu.parseRequest(req);
	//��������
		//���ֿ��ļ�������ͨ�ֶ�
		System.out.println(fileItems.size());
		
		for(int i=0;i<fileItems.size();i++) {
			if(fileItems.get(i).isFormField()) {
				//��ͨ�ֶ�FileItem		
				//��ȡ��������uNo tNo content
				String s=fileItems.get(i).getString();
				//�����������iso-8859-1 ��utf-8
				s=new String(s.getBytes("iso8859-1"),"utf-8");
				//��������
				if(i==0) {
					goods.setgName(s);
				}else if(i==1) {
					goods.setgDescribe(s);
				}else if(i==2) {
					goods.setgBelong(s);
				}else if(i==3) {
					goods.setgPrice(Integer.parseInt(s));
				}else if(i==4) {
					goods.setgNumber(Integer.parseInt(s));
				}else if(i==5) {
					goods.setgClass(s);
				}
				System.out.println(s);  	
			}else {
				//�ļ�
				//��ȡ�ϴ��ļ���
				String format=fileItems.get(i).getName();
				//��ȡ�ļ�����׺
				format=format.substring(format.lastIndexOf("."));
				//gUrl
				String url="pictures/goods/"+goods.getgId()+format;
				goods.setgUrl(url);
				//��������
				String webPath="";
				String fileName=goods.getgId()+format;
				webPath=webRoot+"pictures"+File.separator
								+"goods"+File.separator;
				//�ϲ�����·��
				String filePath=webPath+fileName;
				System.out.println(filePath);
				//����
				File file=new File(filePath);
				//��û����仰��ʾֱ�Ӵ���filePath�ļ��У��������ļ�	
				file.getParentFile().mkdirs();
				file.createNewFile();
				//����ļ���
				InputStream in=fileItems.get(i).getInputStream();
				//�򿪷������ϴ��ļ�
				FileOutputStream out=new FileOutputStream(file);
				//�Կ�
				byte[] buffer=new byte[1024];
				int len;
				//��ȡ
				while((len=in.read(buffer))>0) {
					out.write(buffer,0,len);
				}
				in.close();
				out.close();
				//ɾ����ʱ�ļ�
				fileItems.get(i).delete();	
				System.out.println("ͼƬ�ϴ��ɹ�!");
				GoodsService g=new GoodsService();
				int flag=g.AddGoodS(goods);
				resp.getWriter().write(new Gson().toJson(flag));	
			}
		}
	}catch(Exception e) {
			throw new RuntimeException(e);
	}
}
       


}
