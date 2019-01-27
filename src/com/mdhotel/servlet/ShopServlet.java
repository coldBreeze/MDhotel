package com.mdhotel.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.mdhotel.common.StringUtil;
import com.mdhotel.dao.ShopDao;
import com.mdhotel.model.Shop;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * Servlet implementation class ShopServlet
 */
public class ShopServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ShopServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		ShopDao shopdao=new ShopDao();
		PrintWriter pw=response.getWriter();
		String path = request.getRequestURI();
		String action = path.substring(path.lastIndexOf("/") + 1, path.lastIndexOf("."));
		System.out.println("shop-action:"+action);
		switch (action) {
		case "add":		
			String code=request.getParameter("code");
			String name=request.getParameter("name");
			String address=request.getParameter("address");
			String time=request.getParameter("time");
			String remark=request.getParameter("remark");
			if(StringUtil.isNotEmpty(code)&&StringUtil.isNotEmpty(name)&&StringUtil.isNotEmpty(address)&&StringUtil.isNotEmpty(time)){
				Shop shop=new Shop(code,name,address,time,remark);
				if(shopdao.addShop(shop)){
					pw.print("success");
				}else{
					pw.print("fail");
				}
			}else{
				pw.print("fail");
			}	
			break;
			case "list":
				String currentPage=request.getParameter("currentPage");
				String search=request.getParameter("search");
				int cpage=1;
				if(StringUtil.isNotEmpty(currentPage)){
					cpage=Integer.parseInt(currentPage);
				}
				ResultSet rs=shopdao.currentShop(cpage,search);
				JSONArray jsonarr=new JSONArray();
				try {
					ResultSetMetaData rsmd=rs.getMetaData();
					int count=rsmd.getColumnCount();
					while(rs.next()){	
						JSONObject json=new JSONObject();
						for(int i=1;i<=count;i++){	
							if(rs.getObject(i)==null){
								json.put(rsmd.getColumnName(i), "");	
							}else{
								json.put(rsmd.getColumnName(i), rs.getObject(i));	
							}
						}
						jsonarr.add(json);
					}
					System.out.println(jsonarr.size());
					pw.println(jsonarr);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
			case "page":
				String psearch=request.getParameter("search");
				int total=shopdao.getTotalPage(psearch);
				if(total!=0){
					pw.print(total);
				}else{
					pw.print("empty");
				}
				break;
			case "read":
				String rcode=request.getParameter("code");
				if(StringUtil.isNotEmpty(rcode)){
					ResultSet rss=shopdao.getRsById(rcode);
					JSONArray jsonarrr=new JSONArray();
					try {
						ResultSetMetaData rsmd=rss.getMetaData();
						int count=rsmd.getColumnCount();
						while(rss.next()){	
							JSONObject json=new JSONObject();
							for(int i=1;i<=count;i++){	
								if(rss.getObject(i)==null){
									json.put(rsmd.getColumnName(i), "");	
								}else{
									json.put(rsmd.getColumnName(i), rss.getObject(i));	
								}
							}
							jsonarrr.add(json);
						}
						System.out.println(jsonarrr.size());
						pw.println(jsonarrr);
					}catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}	
				}else{
					pw.print("fail");
				}
				break;
			case "delete":
				String dcode=request.getParameter("code");
				if(StringUtil.isNotEmpty(dcode)){
					if(shopdao.deleteShop(dcode)){
						pw.print("success");
					}else{
						pw.print("fail");
					}
				}else{
					pw.print("error");
				}
				break;
			
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
