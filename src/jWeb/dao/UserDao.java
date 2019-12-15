package jWeb.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import jWeb.jdbcContent.jdbcConnect;
import jWeb.pojo.User;

public class UserDao {

	 //注册用户uType=0/管理员uType=1，若可注册返回1，否则为0
	public int checkRegisterUser(String uId, String uName, String uIntroduce, String uPsw, int uType, String uSex, String uPhone, String uEmail) {
		//声明JDBC对象
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		//声明数据存储对象
		int judge=0;
		try {
			//连接数据库
			conn=jdbcConnect.getConnection();
			//创建SQL命令
			String sql="select * from user";
			//创建sql命令对象
			ps=conn.prepareStatement(sql);
			//给占位符赋值
			//执行
			rs=ps.executeQuery();
			//遍历执行结果,是否有重复uId
			while(rs.next()) 
			{
				if(uId.equals(rs.getString("uId"))) 
				{
					judge=0;
					jdbcConnect.delete(rs, ps, conn);
					return judge;
				}
			}
			//无重复，uId可用
			sql="insert into user(uId,uName,uPsw,uIntroduce,uType,uSex,uEmail,uPhone,uMoney) values(?,?,?,?,?,?,?,?,?)";
			ps.close();
			ps=conn.prepareStatement(sql);
			//给占位符赋值
			ps.setString(1, uId);
			ps.setString(2, uName);
			ps.setString(3, uPsw);
			ps.setString(4, uIntroduce);
			ps.setInt(5, uType);
			ps.setString(6, uSex);
			ps.setString(7, uEmail);
			ps.setString(8, uPhone);
			ps.setFloat(9, 0);
			//执行
			judge=ps.executeUpdate();
			//关闭资源
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			jdbcConnect.delete(rs, ps, conn);
		}
		return judge;
	}

	//用户/管理员登录，无错返回0，无此用户名返回1，密码错误返回2
	public int checkLoginUser(String uId, String uPsw, int uType) {
		//声明JDBC对象
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		//声明数据存储对象
		int judge=1;
		try {
			//连接数据库
			conn=jdbcConnect.getConnection();
			//创建SQL命令
			String sql="select * from user where uId=? and uType=?";
			//创建sql命令对象
			ps=conn.prepareStatement(sql);
			//给占位符赋值
			ps.setString(1, uId);
			ps.setInt(2, uType);
			//执行
			rs=ps.executeQuery();
			//遍历执行结果
			while(rs.next()) 
			{
				if(uPsw.equals(rs.getString("uPsw"))){
					judge=0;
					jdbcConnect.delete(rs, ps, conn);
					return judge;
				}else {
					judge=2;
				}
			}
			//关闭资源
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			jdbcConnect.delete(rs, ps, conn);
		}
		return judge;
	}

	//返回某一用户所有信息
	public User findUser(String uId) {
		//声明JDBC对象
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		//声明数据存储对象
		User user=new User();
		try {
			//连接数据库
			conn=jdbcConnect.getConnection();
			//创建SQL命令
			String sql="select * from user where uId=?";
			//创建sql命令对象
			ps=conn.prepareStatement(sql);
			//给占位符赋值
			ps.setString(1, uId);
			//执行
			rs=ps.executeQuery();
			//遍历执行结果
			while(rs.next()) 
			{
				user.setuId(rs.getString("uId"));
				user.setuName(rs.getString("uName"));
				user.setuIntroduce(rs.getString("uIntroduce"));
				user.setuSex(rs.getString("uSex"));
				user.setuPhone(rs.getString("uPhone"));
				user.setuEmail(rs.getString("uEmail"));
				user.setuPsw(rs.getString("uPsw"));
				user.setuType(rs.getInt("uType"));
				user.setuMoney(rs.getInt("uMoney"));
			}
			//关闭资源
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			jdbcConnect.delete(rs, ps, conn);
		}
		return user;
	}

	//修改某一用户信息
	public int changeInfo(User u2) {
		//声明JDBC对象
		Connection conn=null;
		PreparedStatement ps=null;
		//声明数据存储对象
		int judge=0;
		try {
			//连接数据库
			conn=jdbcConnect.getConnection();
			//创建SQL命令
			String sql="update user set uName=?,uPsw=?,uIntroduce=?,uType=?,uSex=?,uEmail=?,uPhone=?,uMoney=? where uId=?";
			//创建sql命令对象
			ps=conn.prepareStatement(sql);
			//给占位符赋值
			ps.setString(1, u2.getuName());
			ps.setString(2, u2.getuPsw());
			ps.setString(3, u2.getuIntroduce());
			ps.setInt(4, u2.getuType());
			ps.setString(5, u2.getuSex());
			ps.setString(6, u2.getuEmail());
			ps.setString(7, u2.getuPhone());
			ps.setInt(8, u2.getuMoney());
			ps.setString(9, u2.getuId());
			//执行
			judge=ps.executeUpdate();
			//关闭资源
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			jdbcConnect.delete(ps, conn);
		}
		return judge;
	}

	//用户充值
	public User recharge(String uId, int money) {
		//声明JDBC对象
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		//声明数据存储对象
		User u=new User();
		try {
			//连接数据库
			conn=jdbcConnect.getConnection();
			//创建SQL命令
			String sql="update user set uMoney=uMoney+? where uId=?";
			//创建sql命令对象
			ps=conn.prepareStatement(sql);
			//给占位符赋值
			ps.setInt(1, money);
			ps.setString(2, uId);
			//执行
			ps.executeUpdate();
			//重新取除
			sql="select * from user where uId=?";
			ps.close();
			ps=conn.prepareStatement(sql);
			//给占位符赋值
			ps.setString(1, uId);
			//执行
			rs=ps.executeQuery();
			//遍历执行结果
			while(rs.next()) 
			{
				u.setuId(rs.getString("uId"));
				u.setuName(rs.getString("uName"));
				u.setuIntroduce(rs.getString("uIntroduce"));
				u.setuSex(rs.getString("uSex"));
				u.setuPhone(rs.getString("uPhone"));
				u.setuEmail(rs.getString("uEmail"));
				u.setuPsw(rs.getString("uPsw"));
				u.setuType(rs.getInt("uType"));
				u.setuMoney(rs.getInt("uMoney"));
			}
			//关闭资源
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			jdbcConnect.delete(ps, conn);
		}
		return u;
	}

	//购买减少用户余额
	public User reduceMoney(String uId, int bp) {
		//声明JDBC对象
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		//声明数据存储对象
		User u=new User();
		try {
			//连接数据库
			conn=jdbcConnect.getConnection();
			//创建SQL命令
			String sql="update user set uMoney=uMoney-? where uId=?";
			//创建sql命令对象
			ps=conn.prepareStatement(sql);
			//给占位符赋值
			ps.setInt(1, bp);
			ps.setString(2, uId);
			//执行
			ps.executeUpdate();
			//重新取除
			sql="select * from user where uId=?";
			ps.close();
			ps=conn.prepareStatement(sql);
			//给占位符赋值
			ps.setString(1, uId);
			//执行
			rs=ps.executeQuery();
			//遍历执行结果
			while(rs.next()) 
			{
				u.setuId(rs.getString("uId"));
				u.setuName(rs.getString("uName"));
				u.setuIntroduce(rs.getString("uIntroduce"));
				u.setuSex(rs.getString("uSex"));
				u.setuPhone(rs.getString("uPhone"));
				u.setuEmail(rs.getString("uEmail"));
				u.setuPsw(rs.getString("uPsw"));
				u.setuType(rs.getInt("uType"));
				u.setuMoney(rs.getInt("uMoney"));
			}
			//关闭资源
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			jdbcConnect.delete(ps, conn);
		}
		return u;
	}
	
}
