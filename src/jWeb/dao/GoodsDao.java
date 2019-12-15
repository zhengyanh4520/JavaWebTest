package jWeb.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jWeb.jdbcContent.jdbcConnect;
import jWeb.pojo.Goods;
import jWeb.rand.randNo;

public class GoodsDao {

	//���ֲ���ĳһ��Ʒ(�����к�������gname����)
	public List<Goods> findGoods(String gName) {
		//����JDBC����
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		//�������ݴ洢����
		List<Goods> list=new ArrayList<Goods>();
		try {
			//�������ݿ�
			conn=jdbcConnect.getConnection();
			//����SQL����
			String sql="select gId,gName,gClass,gDescribe,gPrice,gNumber,gUrl,uName"
					+ " from goods,user"
					+ " where gName like ? and goods.gBelong=user.uId and gNumber!=-1";
			//����sql�������
			ps=conn.prepareStatement(sql);
			//��ռλ����ֵ
			gName="%"+gName+"%";
			ps.setString(1, gName);
			//ִ��
			rs=ps.executeQuery();
			//����ִ�н��
			while(rs.next()) 
			{
				Goods g=new Goods();
				g.setgId(rs.getString("gId"));
				g.setgName(rs.getString("gName"));
				g.setgBelong(rs.getString("uName"));
				g.setgClass(rs.getString("gClass"));
				g.setgDescribe(rs.getString("gDescribe"));
				g.setgPrice(rs.getInt("gPrice"));
				g.setgNumber(rs.getInt("gNumber"));
				g.setgUrl(rs.getString("gUrl"));
				list.add(g);
			}
			//�ر���Դ
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			jdbcConnect.delete(rs, ps, conn);
		}
		return list;
	}
	
	//�����Ʒ
	public int AddGoods(Goods g) {
		//����JDBC����
		Connection conn=null;
		PreparedStatement ps=null;
		//�������ݴ洢����
		int flag=0;
		try {
			//�������ݿ�
			conn=jdbcConnect.getConnection();
			//����SQL����
			String sql="insert into goods(gId,gName,gBelong,gClass,gDescribe,gPrice,gNumber,gUrl) values(?,?,?,?,?,?,?,?)";
			//����sql�������
			ps=conn.prepareStatement(sql);
			//��ռλ����ֵ
			ps.setString(1, g.getgId());
			ps.setString(2, g.getgName());
			ps.setString(3, g.getgBelong());
			ps.setString(4, g.getgClass());
			ps.setString(5, g.getgDescribe());
			ps.setInt(6, g.getgPrice());
			ps.setInt(7, g.getgNumber());
			ps.setString(8, g.getgUrl());
			//ִ��
			flag=ps.executeUpdate();
			//�ر���Դ
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			jdbcConnect.delete(ps, conn);
		}
		return flag;
	}


	
	//ɾ����Ʒ
	public int DeleteGoods(String gId) {
		//����JDBC����
		Connection conn=null;
		PreparedStatement ps=null;
		//�������ݴ洢����
		int flag=0;
		try {
			//�������ݿ�
			conn=jdbcConnect.getConnection();
			//����SQL����
			String sql="update goods set gNumber=-1 where gId=?";
			//����sql�������
			ps=conn.prepareStatement(sql);
			//��ռλ����ֵ
			ps.setString(1, gId);
			//ִ��
			flag=ps.executeUpdate();
			//�ر���Դ
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			jdbcConnect.delete(ps, conn);
		}
		return flag;
	}
	
	//�޸���Ʒ
	public int ChangeGoods(Goods g) {
		//����JDBC����
		Connection conn=null;
		PreparedStatement ps=null;
		//�������ݴ洢����
		int flag=0;
		try {
			//�������ݿ�
			conn=jdbcConnect.getConnection();
			//����SQL����
			String sql="update goods set gName=?,gBelong=?,gClass=?,gDescribe=?,gPrice=?,gNumber=?,gUrl=? where gId=?";
			//����sql�������
			ps=conn.prepareStatement(sql);
			//��ռλ����ֵ
			ps.setString(1, g.getgName());
			ps.setString(2, g.getgBelong());
			ps.setString(3, g.getgClass());
			ps.setString(4, g.getgDescribe());
			ps.setInt(5, g.getgPrice());
			ps.setInt(6, g.getgNumber());
			ps.setString(7, g.getgUrl());
			ps.setString(8, g.getgId());
			//ִ��
			flag=ps.executeUpdate();
			//�ر���Դ
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			jdbcConnect.delete(ps, conn);
		}
		return flag;
	}
	
	//������ĳһ��Ʒ
	public List<Goods> findClassGoods(String gClass) {
		//����JDBC����
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		//�������ݴ洢����
		List<Goods> list=new ArrayList<Goods>();
		try {
			//�������ݿ�
			conn=jdbcConnect.getConnection();
			//����SQL����
			String sql="select gId,gName,gClass,gDescribe,gPrice,gNumber,gUrl,uName"
					+ " from goods,user"
					+ " where gClass=? and goods.gBelong=user.uId and gNumber!=-1";
			//����sql�������
			ps=conn.prepareStatement(sql);
			//��ռλ����ֵ
			ps.setString(1, gClass);
			//ִ��
			rs=ps.executeQuery();
			//����ִ�н��
			while(rs.next()) 
			{
				Goods g=new Goods();
				g.setgId(rs.getString("gId"));
				g.setgName(rs.getString("gName"));
				g.setgBelong(rs.getString("uName"));
				g.setgClass(rs.getString("gClass"));
				g.setgDescribe(rs.getString("gDescribe"));
				g.setgPrice(rs.getInt("gPrice"));
				g.setgNumber(rs.getInt("gNumber"));
				g.setgUrl(rs.getString("gUrl"));
				list.add(g);
			}
			//�ر���Դ
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			jdbcConnect.delete(rs, ps, conn);
		}
		return list;
	}
	
	//����������Ʒ
	public List<Goods> LoadGoods() {
		//����JDBC����
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		//�������ݴ洢����
		List<Goods> list=new ArrayList<Goods>();
		try {
			//�������ݿ�
			conn=jdbcConnect.getConnection();
			//����SQL����
			String sql="select * from goods where gNumber!=-1";
			//����sql�������
			ps=conn.prepareStatement(sql);
			//��ռλ����ֵ
			//ִ��
			rs=ps.executeQuery();
			//����ִ�н��
			while(rs.next()) 
			{
				Goods g=new Goods();
				g.setgId(rs.getString("gId"));
				g.setgName(rs.getString("gName"));
				g.setgBelong(rs.getString("gBelong"));
				g.setgClass(rs.getString("gClass"));
				g.setgDescribe(rs.getString("gDescribe"));
				g.setgPrice(rs.getInt("gPrice"));
				g.setgNumber(rs.getInt("gNumber"));
				g.setgUrl(rs.getString("gUrl"));
				list.add(g);
			}
			//�ر���Դ
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			jdbcConnect.delete(rs, ps, conn);
		}
		return list;
	}

	//˫ID����ȷ����Ʒ,����������¼
	public Goods findIdGoods(String uId, String gId) {
		//����JDBC����
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		//�������ݴ洢����
		Goods g=new Goods();
		try {
			//�������ݿ�
			conn=jdbcConnect.getConnection();
			//����SQL����
			String sql="select * from goods where gId=?";
			//����sql�������
			ps=conn.prepareStatement(sql);
			//��ռλ����ֵ
			ps.setString(1, gId);
			//ִ��
			rs=ps.executeQuery();
			//����ִ�н��
			while(rs.next()) 
			{
				g.setgId(rs.getString("gId"));
				g.setgName(rs.getString("gName"));
				g.setgBelong(rs.getString("gBelong"));
				g.setgClass(rs.getString("gClass"));
				g.setgDescribe(rs.getString("gDescribe"));
				g.setgPrice(rs.getInt("gPrice"));
				g.setgNumber(rs.getInt("gNumber"));
				g.setgUrl(rs.getString("gUrl"));
			}
			sql="select * from user where uId=?";
			//����sql�������
			ps.close();
			ps=conn.prepareStatement(sql);
			//��ռλ����ֵ
			ps.setString(1, g.getgBelong());
			//ִ��
			rs.close();
			rs=ps.executeQuery();
			//����ִ�н��
			while(rs.next()) 
			{
				g.setgBelong(rs.getString("uName"));
			}
			
			//���������¼
			sql="insert into buybrowse(bId,uId,gId,bTime,bType) values(?,?,?,?,?)";
			//����sql�������
			ps.close();
			ps=conn.prepareStatement(sql);
			//����������
			randNo r=new randNo();
			String bId=r.createOtherNo();
			//��ȡ��ǰʱ��
	        Date date = new Date();
	    	SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	    	String time=dateFormat.format(date);
	    	//��ռλ����ֵ
	    	ps.setString(1, bId);
	    	ps.setString(2, uId);
	    	ps.setString(3, gId);
	    	ps.setString(4, time);
	    	ps.setInt(5, 0);
			//ִ��
			ps.executeUpdate();
			//�ر���Դ
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			jdbcConnect.delete(rs, ps, conn);
		}
		return g;
	}

	//���������Ʒ����
	public Goods reduceNumber(String gId, int n) {
		//����JDBC����
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		//�������ݴ洢����
		Goods g=new Goods();
		try {
			//�������ݿ�
			conn=jdbcConnect.getConnection();
			//����SQL����
			String sql="update goods set gNumber=gNumber-? where gId=?";
			//����sql�������
			ps=conn.prepareStatement(sql);
			//��ռλ����ֵ
			ps.setInt(1, n);
			ps.setString(2, gId);
			//ִ��
			ps.executeUpdate();
			//����ȡ��
			sql="select * from goods where gId=?";
			ps.close();
			ps=conn.prepareStatement(sql);
			//��ռλ����ֵ
			ps.setString(1, gId);
			//ִ��
			rs=ps.executeQuery();
			//����ִ�н��
			while(rs.next()) 
			{
				g.setgId(rs.getString("gId"));
				g.setgName(rs.getString("gName"));
				g.setgBelong(rs.getString("gBelong"));
				g.setgClass(rs.getString("gClass"));
				g.setgDescribe(rs.getString("gDescribe"));
				g.setgPrice(rs.getInt("gPrice"));
				g.setgNumber(rs.getInt("gNumber"));
				g.setgUrl(rs.getString("gUrl"));
			}
			//�ر���Դ
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			jdbcConnect.delete(ps, conn);
		}
		return g;
	}

	//����ĳһ�̼ҵ�������Ʒ
	public List<Goods> uidLoadGoods(String uId) {
		//����JDBC����
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		//�������ݴ洢����
		List<Goods> list=new ArrayList<Goods>();
		try {
			//�������ݿ�
			conn=jdbcConnect.getConnection();
			//����SQL����
			String sql="select * from goods where gBelong=? and gNumber!=-1";
			//����sql�������
			ps=conn.prepareStatement(sql);
			//��ռλ����ֵ
			ps.setString(1, uId);
			//ִ��
			rs=ps.executeQuery();
			//����ִ�н��
			while(rs.next()) 
			{
				Goods g=new Goods();
				g.setgId(rs.getString("gId"));
				g.setgName(rs.getString("gName"));
				g.setgBelong(rs.getString("gBelong"));
				g.setgClass(rs.getString("gClass"));
				g.setgDescribe(rs.getString("gDescribe"));
				g.setgPrice(rs.getInt("gPrice"));
				g.setgNumber(rs.getInt("gNumber"));
				g.setgUrl(rs.getString("gUrl"));
				list.add(g);
			}
			//�ر���Դ
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			jdbcConnect.delete(rs, ps, conn);
		}
		return list;
	}

	
	//gId������Ʒ
	public Goods findGoodsUseId(String gId) {
		//����JDBC����
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		//�������ݴ洢����
		Goods g=new Goods();
		try {
			//�������ݿ�
			conn=jdbcConnect.getConnection();
			//����SQL����
			String sql="select * from goods where gId=?";
			//����sql�������
			ps=conn.prepareStatement(sql);
			//��ռλ����ֵ
			ps.setString(1, gId);
			//ִ��
			rs=ps.executeQuery();
			//����ִ�н��
			while(rs.next()) 
			{
				g.setgId(rs.getString("gId"));
				g.setgName(rs.getString("gName"));
				g.setgBelong(rs.getString("gBelong"));
				g.setgClass(rs.getString("gClass"));
				g.setgDescribe(rs.getString("gDescribe"));
				g.setgPrice(rs.getInt("gPrice"));
				g.setgNumber(rs.getInt("gNumber"));
				g.setgUrl(rs.getString("gUrl"));
			}
			//�ر���Դ
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			jdbcConnect.delete(ps, conn);
		}
		return g;
	}

}
