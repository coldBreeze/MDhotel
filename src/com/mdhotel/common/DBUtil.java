package com.mdhotel.common;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBUtil {
	
	public static Connection getConn(){
		Connection conn=null;
		try {
					Class.forName("com.mysql.jdbc.Driver");
					String url="jdbc:mysql://localhost:3306/mdhotel?characterEncoding=utf-8";
					String loginUser="root";
					String loginPwd="123456";
			// emp为数据库名
			conn = DriverManager.getConnection(url,loginUser,loginPwd);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		return conn;
		
	}
	public static void close(Connection conn,PreparedStatement ps,ResultSet rs){
		try {
			if(conn!=null)
				conn.close();
			if(ps!=null)
				ps.close();
			if(rs!=null)
				rs.close();
	
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public static void main(String[] args) {
		System.out.println(getConn());
	}

}
