package com.mdhotel.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import com.mdhotel.common.DBUtil;
import com.mdhotel.common.DateUtils;
import com.mdhotel.common.StringUtil;
import com.mdhotel.model.Goods;

public class GoodsDao {
	//获取总页数
			public int getTotalPage(String search){
				int pagenums=0;
				int count=0;
				Connection conn=null;
				PreparedStatement ps=null;
				ResultSet rs=null;
				try {
					conn=DBUtil.getConn();
					String sql="select count(*) from goods";
					if(StringUtil.isNotEmpty(search)){
						sql=sql+" where goods_code like '%"+search+"%' or goods_name like '%"+search+"%'";
					}
					ps=conn.prepareStatement(sql);
					rs=ps.executeQuery();
					while(rs.next()){
						count=rs.getInt(1);
					}
					pagenums=count%6==0?count/6:count/6+1;
					
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
			public ResultSet currentGoods(int currentPage,String search){
				Connection conn=null;
				PreparedStatement ps=null;
				ResultSet rs=null;
				try {
					conn=DBUtil.getConn();
					String sql="select goods_code, goods_name, goods_price,goods_count from goods"; 
					if(StringUtil.isNotEmpty(search)){
						sql=sql+" where goods_code like '%"+search+"%' or goods_name like '%"+search+"%'";
					}
					sql=sql+" order by goods_price ASC limit ?,?";
					ps=conn.prepareStatement(sql);
					int begin=(currentPage-1)*6;
					int end=begin+6;
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
			//修改
			public boolean updatePassenger(Goods good){
				Connection conn=null;
				PreparedStatement ps=null;
				int num=0;
				String sql="";
				try {
					conn=DBUtil.getConn();
					if(StringUtil.isNotEmpty(good.getCode())){			
						sql="update goods set goods_count= goods_count+"+good.getCount()+" where goods_code='"+good.getCode()+"'";
						ps=conn.prepareStatement(sql);
						num=ps.executeUpdate();
						if(num>0){
							return true;
						}else{
								return false;	
						}	
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
			//获取所有日用品名
			public String getGoodsName(){
				Connection conn=null;
				PreparedStatement ps=null;
				ResultSet rs=null;
				StringBuffer sb=new StringBuffer();
				String str="";
				try {
					conn=DBUtil.getConn();
					String sql="select goods_name from goods"; 
					ps=conn.prepareStatement(sql);
					rs=ps.executeQuery();
					while(rs.next()){
						sb.append(rs.getString(1)+",");
					}
					str=sb.toString();
					str=str.substring(0, str.length()-1);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				finally{
					//DBUtil.close(conn, ps, rs);
				}
				return str;
			}
			public static void main(String[] args) {
				GoodsDao g=new GoodsDao();
				System.out.println(g.getGoodsName().toString());
			}
}
