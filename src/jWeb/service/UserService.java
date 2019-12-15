package jWeb.service;

import jWeb.dao.UserDao;
import jWeb.pojo.User;

public class UserService {
	
	private UserDao u=new UserDao();
	
	//用户注册
	public int checkRegisterUser(String uId, String uName, String uIntroduce,
			String uPsw, int uType, String uSex, String uPhone, String uEmail) {
		return u.checkRegisterUser(uId, uName,uIntroduce,uPsw,uType,uSex,uPhone,uEmail);
	}
	
	//用户登录
	public int checkLoginUser(String uId, String uPsw, int uType) {
		return u.checkLoginUser(uId,uPsw,uType);
	}

	//返回某一用户所有信息
	public User findUser(String uId) {
		return u.findUser(uId);
	}

	//修改信息
	public int changeInfo(User u2) {
		return u.changeInfo(u2);
	}

	//用户充值
	public User recharge(String uId, int money) {
		return u.recharge(uId,money);
	}

	//购买减少用户余额
	public User reduceMoney(String uId, int bp) {
		return u.reduceMoney(uId,bp);
	}

}
