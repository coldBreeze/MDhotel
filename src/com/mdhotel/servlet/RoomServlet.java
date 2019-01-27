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
import com.mdhotel.dao.RoomDao;
import com.mdhotel.dao.StaffDao;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * Servlet implementation class RoomServlet
 */
public class RoomServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RoomServlet() {
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
		System.out.println("-------Roomservlet");
		PrintWriter pw=response.getWriter();
		RoomDao roomdao=new RoomDao();
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
		case "list":
			String shop=staffdao.getShop(login);
			ResultSet rs=roomdao.getRoom(shop);
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
