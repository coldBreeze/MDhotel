package com.mdhotel.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import com.mdhotel.common.DBUtil;
import com.mdhotel.common.DateUtils;
import com.mdhotel.common.StringUtil;
import com.mdhotel.model.Gorder;

public class OrderDao {
	public String addOrder(Gorder order){
		Connection conn=null;
		PreparedStatement ps=null;
		int num=0;
		String sql="";
		Date date=new Date();
		String code="O"+DateUtils.parseDateTime(date);
		try {
			conn=DBUtil.getConn();
			sql="insert into Gorder(order_code,order_goods,order_count,order_price,order_time,order_type,order_shop) values(?,?,?,?,?,?,?)";
			ps=conn.prepareStatement(sql);
			ps.setString(1,code);
			ps.setString(2, order.getGoods());
			if(order.getCount()<0){
				ps.setInt(3,-order.getCount());
			}else{
				ps.setInt(3,order.getCount());
			}
				ps.setInt(4,order.getPrice());
			ps.setString(5,DateUtils.parseDateTime2(date));
			ps.setInt(6,order.getType());
			ps.setString(7,order.getShop());
			System.out.println("O"+DateUtils.parseDateTime(date)+","+order.getGoods()+","+order.getCount()+","+order.getPrice()+","+DateUtils.parseDateTime2(date)+","+order.getType());
			num=ps.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally{
			DBUtil.close(conn, ps, null);
		}
		if(num>0)
		return code;
		else
			return "fail";
	}
	//获取总页数
			public int getTotalPage(String search,String shop){
				int pagenums=0;
				int count=0;
				Connection conn=null;
				PreparedStatement ps=null;
				ResultSet rs=null;
				int type;
				try {
					conn=DBUtil.getConn();
					String sql="select count(*) from(select order_code,order_goods,goods_name,order_count,order_price,order_time,order_type from Gorder,goods"
								+" where Gorder.order_goods=goods.goods_code and order_shop='"+shop+"')t";
					if(StringUtil.isNotEmpty(search)){
						if(search.equals("供应")||search.equals("供")||search.equals("应")){
							type=0;
							sql=sql+" where order_code like '%"+search+"%' or order_goods like '%"+search+"%' or goods_name like '%"+search+"%' or order_type = "+type;
						}else if(search.equals("购置")||search.equals("购")||search.equals("置")){
							type=1;
							sql=sql+" where order_code like '%"+search+"%' or order_goods like '%"+search+"%' or goods_name like '%"+search+"%' or order_type = "+type;
						}else{
						sql=sql+" where order_code like '%"+search+"%' or order_goods like '%"+search+"%' or goods_name like '%"+search+"%'";
						}
						}
					System.out.println(sql);
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
			public ResultSet currentOrder(int currentPage,String search,String shop){
				Connection conn=null;
				PreparedStatement ps=null;
				ResultSet rs=null;
				int type=0;
				try {
					conn=DBUtil.getConn();
					String sql="select order_code,order_goods,goods_name,order_count,order_price,order_time,order_type from(select order_code,order_goods,goods_name,order_count,order_price,order_time,order_type from Gorder o,goods g"
								+" where o.order_goods=g.goods_code and order_shop='"+shop+"')t"; 
					if(StringUtil.isNotEmpty(search)){
						if(search.equals("供应")||search.equals("供")||search.equals("应")){
							type=0;
							sql=sql+" where order_code like '%"+search+"%' or order_goods like '%"+search+"%' or goods_name like '%"+search+"%' or order_type = "+type;
						}else if(search.equals("购置")||search.equals("购")||search.equals("置")){
							type=1;
							sql=sql+" where order_code like '%"+search+"%' or order_goods like '%"+search+"%' or goods_name like '%"+search+"%' or order_type = "+type;
						}else{
						sql=sql+" where order_code like '%"+search+"%' or order_goods like '%"+search+"%' or goods_name like '%"+search+"%'";
						}
						}
					sql=sql+" order by order_code DESC limit ?,?";
					System.out.println(sql);
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
			//获取当日制定日用品购置数量
			public int getBuyCount(String goodsname,String shop){
				Connection conn=null;
				PreparedStatement ps=null;
				ResultSet rs=null;
				int count=0;
				try {
					conn=DBUtil.getConn();
					String sql="select sum(order_count) from(select goods_name,order_count from goods,gorder where order_goods=goods_code  and order_shop='"+shop+"'  and order_type=1)t where goods_name='"+goodsname+"' group by goods_name";
					System.out.println(sql);
					ps=conn.prepareStatement(sql);
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
			//获取当日制定日用品供应数量
			public int getSellCount(String goodsname,String shop){
				Connection conn=null;
				PreparedStatement ps=null;
				ResultSet rs=null;
				int count=0;
				try {
					conn=DBUtil.getConn();
					String sql="select sum(order_count) from(select goods_name,order_count from goods,gorder where order_goods=goods_code and order_shop='"+shop+"' and order_type=0)t where goods_name='"+goodsname+"' group by goods_name";
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
}
