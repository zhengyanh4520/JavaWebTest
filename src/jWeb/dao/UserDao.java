package jWeb.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import jWeb.jdbcContent.jdbcConnect;
import jWeb.pojo.User;

public class UserDao {

	 //ע���û�uType=0/����ԱuType=1������ע�᷵��1������Ϊ0
	public int checkRegisterUser(String uId, String uName, String uIntroduce, String uPsw, int uType, String uSex, String uPhone, String uEmail) {
		//����JDBC����
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		//�������ݴ洢����
		int judge=0;
		try {
			//�������ݿ�
			conn=jdbcConnect.getConnection();
			//����SQL����
			String sql="select * from user";
			//����sql�������
			ps=conn.prepareStatement(sql);
			//��ռλ����ֵ
			//ִ��
			rs=ps.executeQuery();
			//����ִ�н��,�Ƿ����ظ�uId
			while(rs.next()) 
			{
				if(uId.equals(rs.getString("uId"))) 
				{
					judge=0;
					jdbcConnect.delete(rs, ps, conn);
					return judge;
				}
			}
			//���ظ���uId����
			sql="insert into user(uId,uName,uPsw,uIntroduce,uType,uSex,uEmail,uPhone,uMoney) values(?,?,?,?,?,?,?,?,?)";
			ps.close();
			ps=conn.prepareStatement(sql);
			//��ռλ����ֵ
			ps.setString(1, uId);
			ps.setString(2, uName);
			ps.setString(3, uPsw);
			ps.setString(4, uIntroduce);
			ps.setInt(5, uType);
			ps.setString(6, uSex);
			ps.setString(7, uEmail);
			ps.setString(8, uPhone);
			ps.setFloat(9, 0);
			//ִ��
			judge=ps.executeUpdate();
			//�ر���Դ
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			jdbcConnect.delete(rs, ps, conn);
		}
		return judge;
	}

	//�û�/����Ա��¼���޴���0���޴��û�������1��������󷵻�2
	public int checkLoginUser(String uId, String uPsw, int uType) {
		//����JDBC����
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		//�������ݴ洢����
		int judge=1;
		try {
			//�������ݿ�
			conn=jdbcConnect.getConnection();
			//����SQL����
			String sql="select * from user where uId=? and uType=?";
			//����sql�������
			ps=conn.prepareStatement(sql);
			//��ռλ����ֵ
			ps.setString(1, uId);
			ps.setInt(2, uType);
			//ִ��
			rs=ps.executeQuery();
			//����ִ�н��
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
			//�ر���Դ
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			jdbcConnect.delete(rs, ps, conn);
		}
		return judge;
	}

	//����ĳһ�û�������Ϣ
	public User findUser(String uId) {
		//����JDBC����
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		//�������ݴ洢����
		User user=new User();
		try {
			//�������ݿ�
			conn=jdbcConnect.getConnection();
			//����SQL����
			String sql="select * from user where uId=?";
			//����sql�������
			ps=conn.prepareStatement(sql);
			//��ռλ����ֵ
			ps.setString(1, uId);
			//ִ��
			rs=ps.executeQuery();
			//����ִ�н��
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
			//�ر���Դ
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			jdbcConnect.delete(rs, ps, conn);
		}
		return user;
	}

	//�޸�ĳһ�û���Ϣ
	public int changeInfo(User u2) {
		//����JDBC����
		Connection conn=null;
		PreparedStatement ps=null;
		//�������ݴ洢����
		int judge=0;
		try {
			//�������ݿ�
			conn=jdbcConnect.getConnection();
			//����SQL����
			String sql="update user set uName=?,uPsw=?,uIntroduce=?,uType=?,uSex=?,uEmail=?,uPhone=?,uMoney=? where uId=?";
			//����sql�������
			ps=conn.prepareStatement(sql);
			//��ռλ����ֵ
			ps.setString(1, u2.getuName());
			ps.setString(2, u2.getuPsw());
			ps.setString(3, u2.getuIntroduce());
			ps.setInt(4, u2.getuType());
			ps.setString(5, u2.getuSex());
			ps.setString(6, u2.getuEmail());
			ps.setString(7, u2.getuPhone());
			ps.setInt(8, u2.getuMoney());
			ps.setString(9, u2.getuId());
			//ִ��
			judge=ps.executeUpdate();
			//�ر���Դ
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			jdbcConnect.delete(ps, conn);
		}
		return judge;
	}

	//�û���ֵ
	public User recharge(String uId, int money) {
		//����JDBC����
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		//�������ݴ洢����
		User u=new User();
		try {
			//�������ݿ�
			conn=jdbcConnect.getConnection();
			//����SQL����
			String sql="update user set uMoney=uMoney+? where uId=?";
			//����sql�������
			ps=conn.prepareStatement(sql);
			//��ռλ����ֵ
			ps.setInt(1, money);
			ps.setString(2, uId);
			//ִ��
			ps.executeUpdate();
			//����ȡ��
			sql="select * from user where uId=?";
			ps.close();
			ps=conn.prepareStatement(sql);
			//��ռλ����ֵ
			ps.setString(1, uId);
			//ִ��
			rs=ps.executeQuery();
			//����ִ�н��
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
			//�ر���Դ
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			jdbcConnect.delete(ps, conn);
		}
		return u;
	}

	//��������û����
	public User reduceMoney(String uId, int bp) {
		//����JDBC����
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		//�������ݴ洢����
		User u=new User();
		try {
			//�������ݿ�
			conn=jdbcConnect.getConnection();
			//����SQL����
			String sql="update user set uMoney=uMoney-? where uId=?";
			//����sql�������
			ps=conn.prepareStatement(sql);
			//��ռλ����ֵ
			ps.setInt(1, bp);
			ps.setString(2, uId);
			//ִ��
			ps.executeUpdate();
			//����ȡ��
			sql="select * from user where uId=?";
			ps.close();
			ps=conn.prepareStatement(sql);
			//��ռλ����ֵ
			ps.setString(1, uId);
			//ִ��
			rs=ps.executeQuery();
			//����ִ�н��
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
			//�ر���Դ
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			jdbcConnect.delete(ps, conn);
		}
		return u;
	}
	
}
