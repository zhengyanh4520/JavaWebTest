package jWeb.sendMail;

import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.Message.RecipientType;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;


public class MailUtils {
	public static void sendMail(String TO, String emailMsg) throws AddressException, MessagingException{
		//����Properties���������ʼ�������������Ϣ
		Properties props=new Properties();
		props.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
    	props.setProperty("mail.smtp.socketFactory.port", "465");
		//���ô���Э��SMTP
		props.setProperty("mail.transport.protocol", "SMPT");
		//����SMTP������
		props.setProperty("mail.host", "smtp.qq.com");
		//����smtp�Ƿ���Ҫ�û���֤
		props.setProperty("mail.smtp.auth", "true");
		props.setProperty("mail.smtp.ssl.enable","true");
		//���õ���
		props.setProperty("mail.debug", "true");
		//�������ӳ�ʱ
		props.setProperty("mail.smtp.timeout", "1000");
		//���ö˿�
        props.setProperty("mail.smtp.port", "465");
        props.setProperty("mail.smtp.socketFactory.fallback", "false");
        props.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		
		//������֤��
		Authenticator auth=new Authenticator() {
			public PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication("452056395@qq.com","awdxaegwhczlcadi");
			}
		};
		//ʵ�����ʼ��Ựsession
		Session session=Session.getInstance(props, auth);
		//����message,������
		Message message=new MimeMessage(session);
		//������
    	message.setFrom(new InternetAddress("452056395@qq.com"));
    	//���÷��ͷ�ʽ�ͽ����ߣ��������ڵ���sendMailʱͨ����������
    	message.setRecipient(RecipientType.TO, new InternetAddress(TO));
    	//�ʼ�����
    	message.setSubject("�ٻ��̵�");
    	message.setContent(emailMsg, "text/html;charset=utf-8");
    	//����
    	Transport.send(message);
	}
}
