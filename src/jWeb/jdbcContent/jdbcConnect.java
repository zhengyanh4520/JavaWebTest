package jWeb.jdbcContent;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class jdbcConnect {
	public static Connection conn=null;
	
	//�������ݿ�����
	public static Connection getConnection()
	{
		try
		{
			Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
			String url="jdbc:mysql://localhost:3306/javaWeb?user=root&password=root&useUnicode=true&characterEncoding=UTF-8&useSSL=false&serverTimezone=UTC";
			conn=DriverManager.getConnection(url);
			System.out.println("���ݿ����ӳɹ�");
		}
		catch(Exception e)
		{
			e.printStackTrace();
			System.out.println("���ݿ������쳣ʧ��");
		}
		return conn;
	}
	
	//�ر�����
	public static void delete(ResultSet rs, Statement stmt, Connection conn)
	{
		if(rs!=null)
		{
			try
			{
				rs.close();
			}
			catch(SQLException e)
			{
				e.printStackTrace();
			}
		}
		delete(stmt,conn);
	}

	public static void delete(Statement stmt, Connection conn)
	{
		if(stmt!=null)
		{
			try
			{
				stmt.close();
			}
			catch(SQLException e)
			{
				e.printStackTrace();
			}
		}
		if(conn!=null)
		{
			try
			{
				conn.close();
			}
			catch(SQLException e)
			{
				e.printStackTrace();
			}
		}
	}

}
