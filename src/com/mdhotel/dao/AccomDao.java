package com.mdhotel.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import com.mdhotel.common.DBUtil;
import com.mdhotel.common.DateUtils;
import com.mdhotel.common.StringUtil;
import com.mdhotel.model.Accom;
import com.mdhotel.model.Passenger;

public class AccomDao {
	public boolean addAccom(Accom accom){
		Connection conn=null;
		PreparedStatement ps=null;
		int num=0;
		String sql="";
		try {
			conn=DBUtil.getConn();
			if(StringUtil.isNotEmpty(accom.getCode())){
				System.out.println(accom.getCode()+","+accom.getRoom()+","+accom.getPass()+","+accom.getIntime()+","+accom.getOutime()+","+accom.getOrder()+","+accom.getPrice()+","+accom.getStaff()+","+accom.getRemark());
				if(StringUtil.isNotEmpty(accom.getOrder())){
				sql="insert into accom(accom_code,accom_room,accom_pass,accom_intime,accom_outime,accom_order,accom_price,accom_staff,accom_remark) values(?,?,?,?,?,?,?,?,?)";
				ps=conn.prepareStatement(sql);	
				ps.setString(1,accom.getCode());
				ps.setString(2,accom.getRoom());
				ps.setString(3,accom.getPass());
				ps.setString(4,DateUtils.parseDate(accom.getIntime()));
				ps.setString(5,DateUtils.parseDate(accom.getOutime()));
				ps.setString(6,accom.getOrder());
				ps.setInt(7,accom.getPrice());
				ps.setString(8,accom.getStaff());
				ps.setString(9,accom.getRemark());
				System.out.println("有订单");
				}else{
				sql="insert into accom(accom_code,accom_room,accom_pass,accom_intime,accom_outime,accom_price,accom_staff,accom_remark) values(?,?,?,?,?,?,?,?)";
				ps=conn.prepareStatement(sql);	
				ps.setString(1,accom.getCode());
				ps.setString(2,accom.getRoom());
				ps.setString(3,accom.getPass());
				ps.setString(4,DateUtils.parseDate(accom.getIntime()));
				ps.setString(5,DateUtils.parseDate(accom.getOutime()));
				ps.setInt(6,accom.getPrice());
				ps.setString(7,accom.getStaff());
				ps.setString(8,accom.getRemark());
				System.out.println("无订单");
			}
			}
			
			num=ps.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally{
			DBUtil.close(conn, ps, null);
		}
		if(num>0)
		return true;
		else
			return false;
	}
	//获取总页数
			public int getTotalPage(String search,String shop){
			
				int pagenums=0;
				int count=0;
				Connection conn=null;
				PreparedStatement ps=null;
				ResultSet rs=null;
				try {
					conn=DBUtil.getConn();
					String sql="select count(*) from(select accom.* from accom,room where accom_room=room_code and room_shop=?)t";
					if(StringUtil.isNotEmpty(search)){
						sql=sql+" where accom_code like '%"+search+"%' or accom_room like '%"+search+"%' or accom_pass like '%"+search+"%' or accom_staff like '%"+search+"%'";
					}
					System.out.println("page:"+sql);
					ps=conn.prepareStatement(sql);
					ps.setString(1,shop);
					rs=ps.executeQuery();
					while(rs.next()){
						count=rs.getInt(1);
					}
					pagenums=count%5==0?count/5:count/5+1;
					
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				finally{
					DBUtil.close(conn, ps, rs);
				}
				return pagenums;
			}
			
			//获取当前页数据
			public ResultSet currentAccom(int currentPage,String search,String shop){
				Connection conn=null;
				PreparedStatement ps=null;
				ResultSet rs=null;
				try {
					conn=DBUtil.getConn();
					String sql="select accom_code,accom_room,accom_pass,accom_intime,accom_outime,accom_price,accom_staff,accom_state from(select accom.* from accom,room where accom_room=room_code and room_shop=?)t"; 
					if(StringUtil.isNotEmpty(search)){
						sql=sql+" where accom_code like '%"+search+"%' or accom_room like '%"+search+"%' or accom_pass like '%"+search+"%' or accom_staff like '%"+search+"%'";
					}
					sql=sql+" order by accom_state ASC,accom_code DESC limit ?,?";
					System.out.println(sql);
					ps=conn.prepareStatement(sql);
					int begin=(currentPage-1)*5;
					int end=begin+5;
					ps.setString(1,shop);
					ps.setInt(2,begin);
					ps.setInt(3,end);
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
			//通过编码获取数据
			public ResultSet getByCode(String code){
				Connection conn=null;
				PreparedStatement ps=null;
				ResultSet rs=null;
				try {
					conn=DBUtil.getConn();
					System.out.println(code);
					String sql="select accom_code,accom_room,room_name,room_price,accom_pass,pass_name,accom_intime,accom_outime,accom_order,room_cash,accom_price,accom_remark"
+" from accom,room,passenger where accom_room=room_code and accom_pass=pass_code and accom_code='"+code+"'";
					System.out.println(sql);
					ps=conn.prepareStatement(sql);
					rs=ps.executeQuery();
					return rs;
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return rs;
		}
			//修改
			public boolean updateTime(Accom accom){
				Connection conn=null;
				PreparedStatement ps=null;
				int num=0;
				String sql="";
				try {
					conn=DBUtil.getConn();
					if(StringUtil.isNotEmpty(accom.getCode())){
							sql="update accom set accom_outime='"+DateUtils.parseDate(accom.getOutime())+"',accom_price="+accom.getPrice()+",accom_remark='"+accom.getRemark()+"' where accom_code='"+accom.getCode()+"'";
							ps=conn.prepareStatement(sql);
							num=ps.executeUpdate();
						}
						if(num>0){
							return true;
						}else{
								return false;	
						}	
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				finally{
					DBUtil.close(conn, ps, null);
				}
				return false;
			}
			//是否含有日用品订单
			public boolean isOrder(String code){
				Connection conn=null;
				PreparedStatement ps=null;
				ResultSet rs=null;
				boolean flag=false;
				try {
					conn=DBUtil.getConn();
					String sql="select accom_order from accom where accom_code='"+code+"'"; 
					ps=conn.prepareStatement(sql);
					rs=ps.executeQuery();
					while(rs.next()){
						String order=rs.getString("accom_order");
						if(StringUtil.isNotEmpty(order)){
							flag=true;
						}
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				finally{
					//DBUtil.close(conn, ps, rs);
				}
				return flag;
			}
			//修改订单
			public boolean updateOrder(Accom accom){
				Connection conn=null;
				PreparedStatement ps=null;
				int num=0;
				String sql="";
				AccomDao a=new AccomDao();
				try {
					conn=DBUtil.getConn();
					if(StringUtil.isNotEmpty(accom.getCode())){
						if(a.isOrder(accom.getCode())){
		
							sql="update accom set accom_order=CONCAT(accom_order,',','"+accom.getOrder()+"'),accom_price=accom_price+"+accom.getPrice()+" where accom_code='"+accom.getCode()+"'";
						}else{
							sql="update accom set accom_order='"+accom.getOrder()+"',accom_price=accom_price+"+accom.getPrice()+" where accom_code='"+accom.getCode()+"'";
						}
					System.out.println(sql);
					ps=conn.prepareStatement(sql);
					num=ps.executeUpdate();
						if(num>0){
							return true;
						}else{
								return false;	
						}
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				finally{
					DBUtil.close(conn, ps, null);
				}
				return false;
			}
			//结算
			public boolean updateState(String code){
				Connection conn=null;
				PreparedStatement ps=null;
				int num=0;
				String sql="";
				try {
					conn=DBUtil.getConn();
					if(StringUtil.isNotEmpty(code)){
							sql="update accom set accom_state=1 where accom_code='"+code+"'";
							ps=conn.prepareStatement(sql);
							num=ps.executeUpdate();
						}
						if(num>0){
							return true;
						}else{
								return false;	
						}	
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				finally{
					DBUtil.close(conn, ps, null);
				}
				return false;
			}
			//预订状态
			public boolean updateState2(String code){
				Connection conn=null;
				PreparedStatement ps=null;
				int num=0;
				String sql="";
				try {
					conn=DBUtil.getConn();
					if(StringUtil.isNotEmpty(code)){
							sql="update accom set accom_state=-1 where accom_code='"+code+"'";
							ps=conn.prepareStatement(sql);
							num=ps.executeUpdate();
						}
						if(num>0){
							return true;
						}else{
								return false;	
						}	
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				finally{
					DBUtil.close(conn, ps, null);
				}
				return false;
			}
			//预订状态
			public boolean updateState0(String code){
				Connection conn=null;
				PreparedStatement ps=null;
				int num=0;
				String sql="";
				try {
					conn=DBUtil.getConn();
					if(StringUtil.isNotEmpty(code)){
							sql="update accom set accom_state=0 where accom_code='"+code+"'";
							ps=conn.prepareStatement(sql);
							num=ps.executeUpdate();
						}
						if(num>0){
							return true;
						}else{
								return false;	
						}	
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				finally{
					DBUtil.close(conn, ps, null);
				}
				return false;
			}
//			public static void main(String[] args) {
//				AccomDao a=new AccomDao();
//				System.out.println(a.isOrder("A20170426181204"));
//			}
}
