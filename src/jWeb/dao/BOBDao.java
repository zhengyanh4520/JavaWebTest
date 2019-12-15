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

	//插入购物车/购买记录，当bType为1时加入购物车，当bType为2时购买
	/*
	 * 当bType为0时该数据表示为浏览记录，在根据ID查找某一商品时已插入，
	 * 因此不在此处，处于GoodsDao的findIdGoods()中
	 */
	public int AddRecoding(BuyOrBrowse b) {
		//声明JDBC对象
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		//声明数据存储对象
		int flag=0;
		try {
			//连接数据库
			conn=jdbcConnect.getConnection();
			System.out.println("bType:"+b.getbType());
			if(b.getbType()==2) {
				//创建SQL命令
				String sql="insert into buybrowse(bId,uId,gId,bTime,bType,bPrice,bNumber) values(?,?,?,?,?,?,?)";
				//创建sql命令对象
				ps=conn.prepareStatement(sql);
				//给占位符赋值
				ps.setString(1, b.getbId());
				ps.setString(2, b.getuId());
				ps.setString(3, b.getgId());
				ps.setString(4, b.getbTime());
				ps.setInt(5, b.getbType());
				ps.setInt(6, b.getbPrice());
				ps.setInt(7, b.getbNumber());
				//执行
				flag=ps.executeUpdate();
			}else {
				//创建SQL命令
				String sql="select * from buybrowse where gId=? and uId=? and bType=1";
				//创建sql命令对象
				ps=conn.prepareStatement(sql);
				//给占位符赋值
				ps.setString(1, b.getgId());
				ps.setString(2, b.getuId());
				//执行
				rs=ps.executeQuery();
				int j=0;
				//遍历执行结果
				while(rs.next()) 
				{
					//该用户购物车已有此商品
					if(rs.getString("bId")!=null) {
						j=1;
					}
				}
				System.out.println("j:"+j);
				if(j==1) {
					sql="update buybrowse set bTime=?,bPrice=bPrice+?,bNumber=bNumber+? where gId=? and uId=? and bType=1";
					//创建sql命令对象
					ps.close();
					ps=conn.prepareStatement(sql);
					//给占位符赋值
					ps.setString(1, b.getbTime());
					ps.setInt(2, b.getbPrice());
					ps.setInt(3, b.getbNumber());
					ps.setString(4, b.getgId());
					ps.setString(5, b.getuId());
					//执行
					flag=ps.executeUpdate();
				}else {
					//创建SQL命令
					sql="insert into buybrowse(bId,uId,gId,bTime,bType,bPrice,bNumber) values(?,?,?,?,?,?,?)";
					//创建sql命令对象
					ps.close();
					ps=conn.prepareStatement(sql);
					//给占位符赋值
					ps.setString(1, b.getbId());
					ps.setString(2, b.getuId());
					ps.setString(3, b.getgId());
					ps.setString(4, b.getbTime());
					ps.setInt(5, b.getbType());
					ps.setInt(6, b.getbPrice());
					ps.setInt(7, b.getbNumber());
					//执行
					flag=ps.executeUpdate();
				}
			}
			//关闭资源
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			jdbcConnect.delete(rs, ps, conn);
		}
		return flag;
	}

	//查询购买记录,uId为admin时为管理员查询全部
	public List<BuyOrBrowse> searchBuy(String uId) {
		//声明JDBC对象
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		//声明数据存储对象
		List<BuyOrBrowse> list=new ArrayList<BuyOrBrowse>();
		try {
			//连接数据库
			conn=jdbcConnect.getConnection();
			//创建SQL命令
			String sql="";
			if("admin".equals(uId)) {
				sql="select bId,bTime,gName,uId,bPrice,bNumber,bType"
						+ " from buybrowse b,goods g"
						+ " where bType=2 and b.gId=g.gId"
						+ " order by bTime Desc";
				//创建sql命令对象
				ps=conn.prepareStatement(sql);
			}else {
				sql="select bId,bTime,gName,uId,bPrice,bNumber,bType"
						+ " from buybrowse b,goods g"
						+ " where b.uId=? and bType=2 and b.gId=g.gId"
						+ " order by bTime Desc";
				//创建sql命令对象
				ps=conn.prepareStatement(sql);
				//给占位符赋值
				ps.setString(1, uId);
			}
			//执行
			rs=ps.executeQuery();
			//遍历执行结果
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
			//关闭资源
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			jdbcConnect.delete(rs, ps, conn);
		}
		return list;
	}

	//查询浏览记录,uId为admin时为管理员查询全部
	public List<BuyOrBrowse> searchBrowse(String uId) {
		//声明JDBC对象
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		//声明数据存储对象
		List<BuyOrBrowse> list=new ArrayList<BuyOrBrowse>();
		try {
			//连接数据库
			conn=jdbcConnect.getConnection();
			//创建SQL命令
			String sql="";
			if("admin".equals(uId)) {
				sql="select bId,bTime,gName,uId,bPrice,bNumber,bType"
						+ " from buybrowse b,goods g"
						+ " where bType=0 and b.gId=g.gId"
						+ " order by bTime Desc";
				//创建sql命令对象
				ps=conn.prepareStatement(sql);
			}else {
				sql="select bId,bTime,gName,uId,bPrice,bNumber,bType"
						+ " from buybrowse b,goods g"
						+ " where b.uId=? and bType=0 and b.gId=g.gId"
						+ " order by bTime Desc";
				//创建sql命令对象
				ps=conn.prepareStatement(sql);
				//给占位符赋值
				ps.setString(1, uId);
			}
			//执行
			rs=ps.executeQuery();
			//遍历执行结果
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
			//关闭资源
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			jdbcConnect.delete(rs, ps, conn);
		}
		return list;
	}

	//查找购物车记录
	public List<BuyOrBrowse> searchCart(String uId) {
		//声明JDBC对象
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		//声明数据存储对象
		List<BuyOrBrowse> list=new ArrayList<BuyOrBrowse>();
		try {
			//连接数据库
			conn=jdbcConnect.getConnection();
			//创建SQL命令
			String sql="select bId,bTime,gName,uId,bPrice,bNumber,bType"
					+ " from buybrowse b,goods g"
					+ " where b.uId=? and bType=1 and b.gId=g.gId"
					+ " order by bTime Desc";
			//创建sql命令对象
			ps=conn.prepareStatement(sql);
			//给占位符赋值
			ps.setString(1, uId);
			//执行
			rs=ps.executeQuery();
			//遍历执行结果
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
			//关闭资源
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			jdbcConnect.delete(rs, ps, conn);
		}
		return list;
	}

	////主键查找某一记录
	public BuyOrBrowse findRecoding(String bId) {
		//声明JDBC对象
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		//声明数据存储对象
		BuyOrBrowse b=new BuyOrBrowse();
		try {
			//连接数据库
			conn=jdbcConnect.getConnection();
			//创建SQL命令
			String sql="select * from buybrowse where bId=?";
			//创建sql命令对象
			ps=conn.prepareStatement(sql);
			//给占位符赋值
			ps.setString(1, bId);
			//执行
			rs=ps.executeQuery();
			//遍历执行结果
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
			//关闭资源
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			jdbcConnect.delete(rs, ps, conn);
		}
		return b;
	}


	//删除记录
	public int deleteRecoding(String bId) {
		//声明JDBC对象
		Connection conn=null;
		PreparedStatement ps=null;
		//声明数据存储对象
		int flag=0;
		try {
			//连接数据库
			conn=jdbcConnect.getConnection();
			//创建SQL命令
			String sql="delete from buybrowse where bId=?";
			//创建sql命令对象
			ps=conn.prepareStatement(sql);
			//给占位符赋值
			ps.setString(1, bId);
			//执行
			flag=ps.executeUpdate();
			//关闭资源
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			jdbcConnect.delete(ps, conn);
		}
		return flag;
	}

	
	//加载出销售报表，统计出已售卖的商品的信息
	public List<Goods> searchSaleTable() {
		//声明JDBC对象
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		//声明数据存储对象
		List<Goods> list=new ArrayList<Goods>();
		try {
			//连接数据库
			conn=jdbcConnect.getConnection();
			//创建SQL命令
			String sql="select b.gId id,g.gName n,g.gBelong belong,g.gDescribe de,"
					+ "g.gClass class,sum(b.bPrice) price,sum(b.bNumber) number " + 
					"from buybrowse b left join goods g on  b.gId=g.gId " + 
					"where b.bType=2 " + 
					"group by b.gId;";
			//创建sql命令对象
			ps=conn.prepareStatement(sql);
			//执行
			rs=ps.executeQuery();
			//遍历执行结果
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
			//关闭资源
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			jdbcConnect.delete(rs, ps, conn);
		}
		return list;
	}

}
