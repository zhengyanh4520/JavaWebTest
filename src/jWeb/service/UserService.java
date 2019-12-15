package jWeb.service;

import jWeb.dao.UserDao;
import jWeb.pojo.User;

public class UserService {
	
	private UserDao u=new UserDao();
	
	//�û�ע��
	public int checkRegisterUser(String uId, String uName, String uIntroduce,
			String uPsw, int uType, String uSex, String uPhone, String uEmail) {
		return u.checkRegisterUser(uId, uName,uIntroduce,uPsw,uType,uSex,uPhone,uEmail);
	}
	
	//�û���¼
	public int checkLoginUser(String uId, String uPsw, int uType) {
		return u.checkLoginUser(uId,uPsw,uType);
	}

	//����ĳһ�û�������Ϣ
	public User findUser(String uId) {
		return u.findUser(uId);
	}

	//�޸���Ϣ
	public int changeInfo(User u2) {
		return u.changeInfo(u2);
	}

	//�û���ֵ
	public User recharge(String uId, int money) {
		return u.recharge(uId,money);
	}

	//��������û����
	public User reduceMoney(String uId, int bp) {
		return u.reduceMoney(uId,bp);
	}

}
