package jWeb.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import jWeb.jdbcContent.jdbcConnect;
import jWeb.pojo.BuyOrBrowse;
import jWeb.pojo.Goods;

public class BOBDao {

	//���빺�ﳵ/�����¼����bTypeΪ1ʱ���빺�ﳵ����bTypeΪ2ʱ����
	/*
	 * ��bTypeΪ0ʱ�����ݱ�ʾΪ�����¼���ڸ���ID����ĳһ��Ʒʱ�Ѳ��룬
	 * ��˲��ڴ˴�������GoodsDao��findIdGoods()��
	 */
	public int AddRecoding(BuyOrBrowse b) {
		//����JDBC����
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		//�������ݴ洢����
		int flag=0;
		try {
			//�������ݿ�
			conn=jdbcConnect.getConnection();
			System.out.println("bType:"+b.getbType());
			if(b.getbType()==2) {
				//����SQL����
				String sql="insert into buybrowse(bId,uId,gId,bTime,bType,bPrice,bNumber) values(?,?,?,?,?,?,?)";
				//����sql�������
				ps=conn.prepareStatement(sql);
				//��ռλ����ֵ
				ps.setString(1, b.getbId());
				ps.setString(2, b.getuId());
				ps.setString(3, b.getgId());
				ps.setString(4, b.getbTime());
				ps.setInt(5, b.getbType());
				ps.setInt(6, b.getbPrice());
				ps.setInt(7, b.getbNumber());
				//ִ��
				flag=ps.executeUpdate();
			}else {
				//����SQL����
				String sql="select * from buybrowse where gId=? and uId=? and bType=1";
				//����sql�������
				ps=conn.prepareStatement(sql);
				//��ռλ����ֵ
				ps.setString(1, b.getgId());
				ps.setString(2, b.getuId());
				//ִ��
				rs=ps.executeQuery();
				int j=0;
				//����ִ�н��
				while(rs.next()) 
				{
					//���û����ﳵ���д���Ʒ
					if(rs.getString("bId")!=null) {
						j=1;
					}
				}
				System.out.println("j:"+j);
				if(j==1) {
					sql="update buybrowse set bTime=?,bPrice=bPrice+?,bNumber=bNumber+? where gId=? and uId=? and bType=1";
					//����sql�������
					ps.close();
					ps=conn.prepareStatement(sql);
					//��ռλ����ֵ
					ps.setString(1, b.getbTime());
					ps.setInt(2, b.getbPrice());
					ps.setInt(3, b.getbNumber());
					ps.setString(4, b.getgId());
					ps.setString(5, b.getuId());
					//ִ��
					flag=ps.executeUpdate();
				}else {
					//����SQL����
					sql="insert into buybrowse(bId,uId,gId,bTime,bType,bPrice,bNumber) values(?,?,?,?,?,?,?)";
					//����sql�������
					ps.close();
					ps=conn.prepareStatement(sql);
					//��ռλ����ֵ
					ps.setString(1, b.getbId());
					ps.setString(2, b.getuId());
					ps.setString(3, b.getgId());
					ps.setString(4, b.getbTime());
					ps.setInt(5, b.getbType());
					ps.setInt(6, b.getbPrice());
					ps.setInt(7, b.getbNumber());
					//ִ��
					flag=ps.executeUpdate();
				}
			}
			//�ر���Դ
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			jdbcConnect.delete(rs, ps, conn);
		}
		return flag;
	}

	//��ѯ�����¼,uIdΪadminʱΪ����Ա��ѯȫ��
	public List<BuyOrBrowse> searchBuy(String uId) {
		//����JDBC����
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		//�������ݴ洢����
		List<BuyOrBrowse> list=new ArrayList<BuyOrBrowse>();
		try {
			//�������ݿ�
			conn=jdbcConnect.getConnection();
			//����SQL����
			String sql="";
			if("admin".equals(uId)) {
				sql="select bId,bTime,gName,uId,bPrice,bNumber,bType"
						+ " from buybrowse b,goods g"
						+ " where bType=2 and b.gId=g.gId"
						+ " order by bTime Desc";
				//����sql�������
				ps=conn.prepareStatement(sql);
			}else {
				sql="select bId,bTime,gName,uId,bPrice,bNumber,bType"
						+ " from buybrowse b,goods g"
						+ " where b.uId=? and bType=2 and b.gId=g.gId"
						+ " order by bTime Desc";
				//����sql�������
				ps=conn.prepareStatement(sql);
				//��ռλ����ֵ
				ps.setString(1, uId);
			}
			//ִ��
			rs=ps.executeQuery();
			//����ִ�н��
			while(rs.next()) 
			{
				BuyOrBrowse b=new BuyOrBrowse();
				b.setbId(rs.getString("bId"));
				b.setbTime(rs.getString("bTime"));
				b.setgId(rs.getString("gName"));
				b.setuId(rs.getString("uId"));
				b.setbPrice(rs.getInt("bPrice"));
				b.setbNumber(rs.getInt("bNumber"));
				b.setbType(rs.getInt("bType"));
				list.add(b);
			}
			//�ر���Դ
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			jdbcConnect.delete(rs, ps, conn);
		}
		return list;
	}

	//��ѯ�����¼,uIdΪadminʱΪ����Ա��ѯȫ��
	public List<BuyOrBrowse> searchBrowse(String uId) {
		//����JDBC����
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		//�������ݴ洢����
		List<BuyOrBrowse> list=new ArrayList<BuyOrBrowse>();
		try {
			//�������ݿ�
			conn=jdbcConnect.getConnection();
			//����SQL����
			String sql="";
			if("admin".equals(uId)) {
				sql="select bId,bTime,gName,uId,bPrice,bNumber,bType"
						+ " from buybrowse b,goods g"
						+ " where bType=0 and b.gId=g.gId"
						+ " order by bTime Desc";
				//����sql�������
				ps=conn.prepareStatement(sql);
			}else {
				sql="select bId,bTime,gName,uId,bPrice,bNumber,bType"
						+ " from buybrowse b,goods g"
						+ " where b.uId=? and bType=0 and b.gId=g.gId"
						+ " order by bTime Desc";
				//����sql�������
				ps=conn.prepareStatement(sql);
				//��ռλ����ֵ
				ps.setString(1, uId);
			}
			//ִ��
			rs=ps.executeQuery();
			//����ִ�н��
			while(rs.next()) 
			{
				BuyOrBrowse b=new BuyOrBrowse();
				b.setbId(rs.getString("bId"));
				b.setbTime(rs.getString("bTime"));
				b.setgId(rs.getString("gName"));
				b.setuId(rs.getString("uId"));
				b.setbPrice(rs.getInt("bPrice"));
				b.setbNumber(rs.getInt("bNumber"));
				b.setbType(rs.getInt("bType"));
				list.add(b);
			}
			//�ر���Դ
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			jdbcConnect.delete(rs, ps, conn);
		}
		return list;
	}

	//���ҹ��ﳵ��¼
	public List<BuyOrBrowse> searchCart(String uId) {
		//����JDBC����
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		//�������ݴ洢����
		List<BuyOrBrowse> list=new ArrayList<BuyOrBrowse>();
		try {
			//�������ݿ�
			conn=jdbcConnect.getConnection();
			//����SQL����
			String sql="select bId,bTime,gName,uId,bPrice,bNumber,bType"
					+ " from buybrowse b,goods g"
					+ " where b.uId=? and bType=1 and b.gId=g.gId"
					+ " order by bTime Desc";
			//����sql�������
			ps=conn.prepareStatement(sql);
			//��ռλ����ֵ
			ps.setString(1, uId);
			//ִ��
			rs=ps.executeQuery();
			//����ִ�н��
			while(rs.next()) 
			{
				BuyOrBrowse b=new BuyOrBrowse();
				b.setbId(rs.getString("bId"));
				b.setbTime(rs.getString("bTime"));
				b.setgId(rs.getString("gName"));
				b.setuId(rs.getString("uId"));
				b.setbPrice(rs.getInt("bPrice"));
				b.setbNumber(rs.getInt("bNumber"));
				b.setbType(rs.getInt("bType"));
				list.add(b);
			}
			//�ر���Դ
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			jdbcConnect.delete(rs, ps, conn);
		}
		return list;
	}

	////��������ĳһ��¼
	public BuyOrBrowse findRecoding(String bId) {
		//����JDBC����
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		//�������ݴ洢����
		BuyOrBrowse b=new BuyOrBrowse();
		try {
			//�������ݿ�
			conn=jdbcConnect.getConnection();
			//����SQL����
			String sql="select * from buybrowse where bId=?";
			//����sql�������
			ps=conn.prepareStatement(sql);
			//��ռλ����ֵ
			ps.setString(1, bId);
			//ִ��
			rs=ps.executeQuery();
			//����ִ�н��
			while(rs.next()) 
			{
				b.setbId(rs.getString("bId"));
				b.setbTime(rs.getString("bTime"));
				b.setgId(rs.getString("gId"));
				b.setuId(rs.getString("uId"));
				b.setbPrice(rs.getInt("bPrice"));
				b.setbNumber(rs.getInt("bNumber"));
				b.setbType(rs.getInt("bType"));
			}
			//�ر���Դ
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			jdbcConnect.delete(rs, ps, conn);
		}
		return b;
	}


	//ɾ����¼
	public int deleteRecoding(String bId) {
		//����JDBC����
		Connection conn=null;
		PreparedStatement ps=null;
		//�������ݴ洢����
		int flag=0;
		try {
			//�������ݿ�
			conn=jdbcConnect.getConnection();
			//����SQL����
			String sql="delete from buybrowse where bId=?";
			//����sql�������
			ps=conn.prepareStatement(sql);
			//��ռλ����ֵ
			ps.setString(1, bId);
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

	
	//���س����۱���ͳ�Ƴ�����������Ʒ����Ϣ
	public List<Goods> searchSaleTable() {
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
			String sql="select b.gId id,g.gName n,g.gBelong belong,g.gDescribe de,"
					+ "g.gClass class,sum(b.bPrice) price,sum(b.bNumber) number " + 
					"from buybrowse b left join goods g on  b.gId=g.gId " + 
					"where b.bType=2 " + 
					"group by b.gId;";
			//����sql�������
			ps=conn.prepareStatement(sql);
			//ִ��
			rs=ps.executeQuery();
			//����ִ�н��
			while(rs.next()) 
			{
				Goods g=new Goods();
				g.setgId(rs.getString("id"));
				g.setgName(rs.getString("n"));
				g.setgBelong(rs.getString("belong"));
				g.setgClass(rs.getString("class"));
				g.setgDescribe(rs.getString("de"));
				g.setgPrice(rs.getInt("price"));
				g.setgNumber(rs.getInt("number"));
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

}
