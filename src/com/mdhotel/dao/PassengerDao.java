package com.mdhotel.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import com.mdhotel.common.DBUtil;
import com.mdhotel.common.DateUtils;
import com.mdhotel.common.StringUtil;
import com.mdhotel.model.Passenger;
import com.mdhotel.model.Staff;


public class PassengerDao {
	
	public boolean addPassenger(Passenger pass){
		Connection conn=null;
		PreparedStatement ps=null;
		int num=0;
		String sql="";
		Date date=new Date();
		try {
			conn=DBUtil.getConn();
			if(pass.getAge()==0){
				sql="insert into passenger(pass_code,pass_name,pass_age,pass_sex,pass_idcard,pass_tel,pass_remark) values(?,?,NULL,?,?,?,?)";
				ps=conn.prepareStatement(sql);	
				ps.setString(1,"p"+DateUtils.parseDateTime(date));
				ps.setString(2, pass.getName());
				ps.setString(3,pass.getSex());
				ps.setString(4,pass.getIdcard());
				ps.setString(5,pass.getTel());
				ps.setString(6,pass.getRemark());
			}
			else{
				sql="insert into passenger(pass_code,pass_name,pass_age,pass_sex,pass_idcard,pass_tel,pass_remark) values(?,?,?,?,?,?,?)";
				ps=conn.prepareStatement(sql);	
				ps.setString(1,"p"+DateUtils.parseDateTime(date));
				ps.setString(2, pass.getName());
				ps.setInt(3,pass.getAge());
				ps.setString(4,pass.getSex());
				ps.setString(5,pass.getIdcard());
				ps.setString(6,pass.getTel());
				ps.setString(7,pass.getRemark());
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
		public int getTotalPage(String search){
			int pagenums=0;
			int count=0;
			Connection conn=null;
			PreparedStatement ps=null;
			ResultSet rs=null;
			try {
				conn=DBUtil.getConn();
				String sql="select count(*) from passenger";
				if(StringUtil.isNotEmpty(search)){
					sql=sql+" where pass_code like '%"+search+"%' or pass_name like '%"+search+"%' or pass_idcard like '%"+search+"%'";
				}
				ps=conn.prepareStatement(sql);
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
		public ResultSet currentPassenger(int currentPage,String search){
			Connection conn=null;
			PreparedStatement ps=null;
			ResultSet rs=null;
			try {
				conn=DBUtil.getConn();
				String sql="select pass_code,pass_name,pass_age,pass_sex,pass_idcard,pass_tel,pass_state from passenger"; 
				if(StringUtil.isNotEmpty(search)){
					sql=sql+" where pass_code like '%"+search+"%' or pass_name like '%"+search+"%' or pass_idcard like '%"+search+"%'";
				}
				sql=sql+" order by pass_code DESC limit ?,?";
				ps=conn.prepareStatement(sql);
				int begin=(currentPage-1)*5;
				int end=begin+5;
				ps.setInt(1,begin);
				ps.setInt(2,end);
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
		public ResultSet getByCode(String code){
			Connection conn=null;
			PreparedStatement ps=null;
			ResultSet rs=null;
			try {
				conn=DBUtil.getConn();
				String sql="select * from passenger where pass_code=?";
				ps=conn.prepareStatement(sql);
				ps.setString(1, code);
				rs=ps.executeQuery();
				return rs;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return rs;
	}
		//获取编码和姓名
		public ResultSet getList(){
			Connection conn=null;
			PreparedStatement ps=null;
			ResultSet rs=null;
			try {
				conn=DBUtil.getConn();
				String sql="select pass_code,pass_name,pass_idcard from passenger order by pass_code desc";
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
		public boolean updatePassenger(Passenger pass){
			Connection conn=null;
			PreparedStatement ps=null;
			int num=0;
			String sql="";
			StringBuffer sb=new StringBuffer();
			String[] arr=null;
			try {
				conn=DBUtil.getConn();
				if(StringUtil.isNotEmpty(pass.getCode())){
					if(StringUtil.isNotEmpty(pass.getName())){
						sql="update passenger set pass_name='"+pass.getName()+"' where pass_code='"+pass.getCode()+"'";
						sb.append(sql+",");
						System.out.println(sql);
					}
					if(pass.getAge()==0){
						sql="update passenger set pass_age=NULL where pass_code='"+pass.getCode()+"'";
						sb.append(sql+",");
					}else{
						sql="update passenger set pass_age='"+pass.getAge()+"' where pass_code='"+pass.getCode()+"'";
						sb.append(sql+",");
					}		
					if(StringUtil.isNotEmpty(pass.getSex())){
						sql="update passenger set pass_sex='"+pass.getSex()+"' where pass_code='"+pass.getCode()+"'";
						sb.append(sql+",");
						System.out.println(sql);
					}
					if(StringUtil.isNotEmpty(pass.getIdcard())){
						sql="update passenger set pass_idcard='"+pass.getIdcard()+"' where pass_code='"+pass.getCode()+"'";
						sb.append(sql+",");
						System.out.println(sql);
					}
					if(StringUtil.isNotEmpty(pass.getTel())){
						sql="update passenger set pass_tel='"+pass.getTel()+"' where pass_code='"+pass.getCode()+"'";
						sb.append(sql+",");
						System.out.println(sql);
					}					
						sql="update passenger set pass_remark='"+pass.getRemark()+"' where pass_code='"+pass.getCode()+"'";
						sb.append(sql+",");
					if(sb.toString().equals("")){
						return false;
					}	
					sql=sb.toString();
					sql=sql.substring(0,sql.lastIndexOf(","));
					arr=sql.split(",");
					System.out.println(arr.length);
					for(int i=0;i<arr.length;i++){
						ps=conn.prepareStatement(arr[i]);
						num+=ps.executeUpdate();
					}
					System.out.println(num);
					if(num==arr.length){
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
		//删除
		public boolean deletePassenger(String code){
			Connection conn=null;
			PreparedStatement ps=null;
			int num=0;
			String sql="";
			try {
				conn=DBUtil.getConn();
				if(StringUtil.isNotEmpty(code)){
						sql="delete from passenger where pass_code='"+code+"'";
						ps=conn.prepareStatement(sql);
						num=ps.executeUpdate();
					System.out.println(num);
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
		//获取当日旅客数量
		public int getDayCount(String day,String shop){
			Connection conn=null;
			PreparedStatement ps=null;
			ResultSet rs=null;
			int count=0;
			try {
				conn=DBUtil.getConn();
				String sql="SELECT COUNT(*) FROM(select  REPLACE(SUBSTR(ACCOM_INTIME FROM 6 FOR 5),'-','')day from accom,room where accom_room=room_code and room_shop='"+shop+"')t where day like '%"+day+"%'";
				ps=conn.prepareStatement(sql);
				System.out.println(sql);
				rs=ps.executeQuery();
				while(rs.next()){
					count=rs.getInt(1);
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return count;
	}
//		public static void main(String[] args) {
//			PassengerDao d=new PassengerDao();
//			
//			Date date=new Date();
//			String[] dateArr=new String[7];
//			int[] countArr=new int[7];
//			for(int i=0;i<7;i++){
//				String day=DateUtils.getPrevDay(date);
//				date=DateUtils.getPrevDate(date);
//				dateArr[i]=day;
//				countArr[i]=d.getDayCount(day);
//			}
//			System.out.print(Arrays.toString(dateArr)+"-"+Arrays.toString(countArr));
//			
//		}
}
