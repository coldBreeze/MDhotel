package com.mdhotel.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.mdhotel.common.DBUtil;
import com.mdhotel.common.StringUtil;

public class RoomDao {
	public ResultSet getRoom(String shop){
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		try {
			conn=DBUtil.getConn();
			String sql="select * from room where room_state=0 and room_shop=?"; 
			ps=conn.prepareStatement(sql);
			ps.setString(1,shop);
			rs=ps.executeQuery();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally{
			//DBUtil.close(conn, ps, rs);
		}
		return rs;
	}
}
