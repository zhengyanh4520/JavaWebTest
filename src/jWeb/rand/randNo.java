package jWeb.rand;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class randNo {
	public String createOtherNo() {
		String No="";
		String str="abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
		//�����
		Random r = new Random();
		//��ǰʱ��
		Date now = new Date();
		//��ǰʱ���д���46����ĸ�����������ĸ���16λ����
		SimpleDateFormat f = new SimpleDateFormat("yyyyMM'"+str.charAt(r.nextInt(str.length()))+"'dd'"+
		str.charAt(r.nextInt(str.length()))+"'HHmmss");
		No=f.format(now);
		return No;
	}
}
