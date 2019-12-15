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

	//名字查找某一商品(名字中含有输入gname即可)
	public List<Goods> findGoods(String gName) {
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
			String sql="select gId,gName,gClass,gDescribe,gPrice,gNumber,gUrl,uName"
					+ " from goods,user"
					+ " where gName like ? and goods.gBelong=user.uId and gNumber!=-1";
			//创建sql命令对象
			ps=conn.prepareStatement(sql);
			//给占位符赋值
			gName="%"+gName+"%";
			ps.setString(1, gName);
			//执行
			rs=ps.executeQuery();
			//遍历执行结果
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
			//关闭资源
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			jdbcConnect.delete(rs, ps, conn);
		}
		return list;
	}
	
	//添加商品
	public int AddGoods(Goods g) {
		//声明JDBC对象
		Connection conn=null;
		PreparedStatement ps=null;
		//声明数据存储对象
		int flag=0;
		try {
			//连接数据库
			conn=jdbcConnect.getConnection();
			//创建SQL命令
			String sql="insert into goods(gId,gName,gBelong,gClass,gDescribe,gPrice,gNumber,gUrl) values(?,?,?,?,?,?,?,?)";
			//创建sql命令对象
			ps=conn.prepareStatement(sql);
			//给占位符赋值
			ps.setString(1, g.getgId());
			ps.setString(2, g.getgName());
			ps.setString(3, g.getgBelong());
			ps.setString(4, g.getgClass());
			ps.setString(5, g.getgDescribe());
			ps.setInt(6, g.getgPrice());
			ps.setInt(7, g.getgNumber());
			ps.setString(8, g.getgUrl());
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


	
	//删除商品
	public int DeleteGoods(String gId) {
		//声明JDBC对象
		Connection conn=null;
		PreparedStatement ps=null;
		//声明数据存储对象
		int flag=0;
		try {
			//连接数据库
			conn=jdbcConnect.getConnection();
			//创建SQL命令
			String sql="update goods set gNumber=-1 where gId=?";
			//创建sql命令对象
			ps=conn.prepareStatement(sql);
			//给占位符赋值
			ps.setString(1, gId);
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
	
	//修改商品
	public int ChangeGoods(Goods g) {
		//声明JDBC对象
		Connection conn=null;
		PreparedStatement ps=null;
		//声明数据存储对象
		int flag=0;
		try {
			//连接数据库
			conn=jdbcConnect.getConnection();
			//创建SQL命令
			String sql="update goods set gName=?,gBelong=?,gClass=?,gDescribe=?,gPrice=?,gNumber=?,gUrl=? where gId=?";
			//创建sql命令对象
			ps=conn.prepareStatement(sql);
			//给占位符赋值
			ps.setString(1, g.getgName());
			ps.setString(2, g.getgBelong());
			ps.setString(3, g.getgClass());
			ps.setString(4, g.getgDescribe());
			ps.setInt(5, g.getgPrice());
			ps.setInt(6, g.getgNumber());
			ps.setString(7, g.getgUrl());
			ps.setString(8, g.getgId());
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
	
	//类别查找某一商品
	public List<Goods> findClassGoods(String gClass) {
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
			String sql="select gId,gName,gClass,gDescribe,gPrice,gNumber,gUrl,uName"
					+ " from goods,user"
					+ " where gClass=? and goods.gBelong=user.uId and gNumber!=-1";
			//创建sql命令对象
			ps=conn.prepareStatement(sql);
			//给占位符赋值
			ps.setString(1, gClass);
			//执行
			rs=ps.executeQuery();
			//遍历执行结果
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
			//关闭资源
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			jdbcConnect.delete(rs, ps, conn);
		}
		return list;
	}
	
	//加载所有商品
	public List<Goods> LoadGoods() {
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
			String sql="select * from goods where gNumber!=-1";
			//创建sql命令对象
			ps=conn.prepareStatement(sql);
			//给占位符赋值
			//执行
			rs=ps.executeQuery();
			//遍历执行结果
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
			//关闭资源
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			jdbcConnect.delete(rs, ps, conn);
		}
		return list;
	}

	//双ID查找确切商品,并添加浏览记录
	public Goods findIdGoods(String uId, String gId) {
		//声明JDBC对象
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		//声明数据存储对象
		Goods g=new Goods();
		try {
			//连接数据库
			conn=jdbcConnect.getConnection();
			//创建SQL命令
			String sql="select * from goods where gId=?";
			//创建sql命令对象
			ps=conn.prepareStatement(sql);
			//给占位符赋值
			ps.setString(1, gId);
			//执行
			rs=ps.executeQuery();
			//遍历执行结果
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
			//创建sql命令对象
			ps.close();
			ps=conn.prepareStatement(sql);
			//给占位符赋值
			ps.setString(1, g.getgBelong());
			//执行
			rs.close();
			rs=ps.executeQuery();
			//遍历执行结果
			while(rs.next()) 
			{
				g.setgBelong(rs.getString("uName"));
			}
			
			//插入浏览记录
			sql="insert into buybrowse(bId,uId,gId,bTime,bType) values(?,?,?,?,?)";
			//创建sql命令对象
			ps.close();
			ps=conn.prepareStatement(sql);
			//获得随机主键
			randNo r=new randNo();
			String bId=r.createOtherNo();
			//获取当前时间
	        Date date = new Date();
	    	SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	    	String time=dateFormat.format(date);
	    	//给占位符赋值
	    	ps.setString(1, bId);
	    	ps.setString(2, uId);
	    	ps.setString(3, gId);
	    	ps.setString(4, time);
	    	ps.setInt(5, 0);
			//执行
			ps.executeUpdate();
			//关闭资源
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			jdbcConnect.delete(rs, ps, conn);
		}
		return g;
	}

	//购买减少商品数量
	public Goods reduceNumber(String gId, int n) {
		//声明JDBC对象
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		//声明数据存储对象
		Goods g=new Goods();
		try {
			//连接数据库
			conn=jdbcConnect.getConnection();
			//创建SQL命令
			String sql="update goods set gNumber=gNumber-? where gId=?";
			//创建sql命令对象
			ps=conn.prepareStatement(sql);
			//给占位符赋值
			ps.setInt(1, n);
			ps.setString(2, gId);
			//执行
			ps.executeUpdate();
			//重新取除
			sql="select * from goods where gId=?";
			ps.close();
			ps=conn.prepareStatement(sql);
			//给占位符赋值
			ps.setString(1, gId);
			//执行
			rs=ps.executeQuery();
			//遍历执行结果
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
			//关闭资源
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			jdbcConnect.delete(ps, conn);
		}
		return g;
	}

	//加载某一商家的所有商品
	public List<Goods> uidLoadGoods(String uId) {
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
			String sql="select * from goods where gBelong=? and gNumber!=-1";
			//创建sql命令对象
			ps=conn.prepareStatement(sql);
			//给占位符赋值
			ps.setString(1, uId);
			//执行
			rs=ps.executeQuery();
			//遍历执行结果
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
			//关闭资源
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			jdbcConnect.delete(rs, ps, conn);
		}
		return list;
	}

	
	//gId查找商品
	public Goods findGoodsUseId(String gId) {
		//声明JDBC对象
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		//声明数据存储对象
		Goods g=new Goods();
		try {
			//连接数据库
			conn=jdbcConnect.getConnection();
			//创建SQL命令
			String sql="select * from goods where gId=?";
			//创建sql命令对象
			ps=conn.prepareStatement(sql);
			//给占位符赋值
			ps.setString(1, gId);
			//执行
			rs=ps.executeQuery();
			//遍历执行结果
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
			//关闭资源
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			jdbcConnect.delete(ps, conn);
		}
		return g;
	}

}
