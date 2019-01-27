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
import com.mdhotel.dao.GoodsDao;
import com.mdhotel.dao.OrderDao;
import com.mdhotel.dao.StaffDao;
import com.mdhotel.model.Gorder;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * Servlet implementation class OrderServlet
 */
public class OrderServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public OrderServlet() {
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
		System.out.println("-------Orderservlet");
		PrintWriter pw=response.getWriter();
		OrderDao orderdao=new OrderDao();
		String path = request.getRequestURI();
		String action = path.substring(path.lastIndexOf("/") + 1, path.lastIndexOf("."));
		HttpSession session=request.getSession();
		String emplogin=(String)session.getAttribute("empInfo");
		String adminlogin=(String)session.getAttribute("adminInfo");
		String syslogin=(String)session.getAttribute("sysInfo");
		String type=request.getParameter("type");
		String login="";
		StaffDao staffdao=new StaffDao();
		System.out.println(login);
		if(StringUtil.isNotEmpty(type)){
			if(type.equals("0")){
				login=emplogin;
			}else if(type.equals("1")){
				login=adminlogin;
			}else if(type.equals("2")){
				login=syslogin;
			}
		}
		switch(action){
		case "update":
			String goods=request.getParameter("goods");
			String num=request.getParameter("num");
			String price=request.getParameter("price");
			String shop=staffdao.getShop(login);
			int onum=0;
			int oprice=0;
			int otype=0;
			if(StringUtil.isNotEmpty(goods)){
				if(StringUtil.isNotEmpty(num)&&StringUtil.isNotEmpty(price)&&StringUtil.isNotEmpty(type)){
					onum=Integer.parseInt(num);
					oprice=Integer.parseInt(price);
					otype=Integer.parseInt(type);
					Gorder order=new Gorder(goods,onum,oprice,otype,shop);
					String code=orderdao.addOrder(order);
					if(code.equals("fail")){
						pw.print("fail");
					}else{
							pw.print(code+","+oprice);	
					}
				}
			}else{
				pw.print("error");
			}
			break;
		case "list":
			String currentPage=request.getParameter("currentPage");
			String search=request.getParameter("search");
			String lshop=staffdao.getShop(login);
			int cpage=1;
			if(StringUtil.isNotEmpty(currentPage)){
				cpage=Integer.parseInt(currentPage);
			}
			ResultSet rs=orderdao.currentOrder(cpage,search,lshop);
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
			String pshop=staffdao.getShop(login);
			int total=orderdao.getTotalPage(psearch,pshop);
			if(total!=0){
				pw.print(total);
			}else{
				pw.print("empty");
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
