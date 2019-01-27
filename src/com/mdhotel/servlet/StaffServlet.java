package com.mdhotel.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.mdhotel.common.DateUtils;
import com.mdhotel.common.StringUtil;
import com.mdhotel.dao.ShopDao;
import com.mdhotel.dao.StaffDao;
import com.mdhotel.model.Staff;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;


/**
 * Servlet implementation class UserInfoServlet
 */
public class StaffServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public StaffServlet() {
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
		StaffDao staffdao=new StaffDao();
		PrintWriter pw=response.getWriter();
		HttpSession session=request.getSession();
		String emplogin=(String)session.getAttribute("empInfo");
		String adminlogin=(String)session.getAttribute("adminInfo");
		String syslogin=(String)session.getAttribute("sysInfo");
		String login="";
		String type=request.getParameter("type");
		System.out.println("type:"+type);
		if(StringUtil.isNotEmpty(type)){
			if(type.equals("0")){
				login=emplogin;
			}else if(type.equals("1")){
				login=adminlogin;
			}else if(type.equals("2")){
				login=syslogin;
			}
		}
		String path = request.getRequestURI();
		String action = path.substring(path.lastIndexOf("/") + 1, path.lastIndexOf("."));
		System.out.println("staff-action:"+action);
		switch (action) {
		case "info":			
			System.out.println("login:"+login);	
			if(StringUtil.isNotEmpty(login)){
				Staff staff=staffdao.getById(login);
				if(staff!=null){
					pw.print(staff.getLogin()+","+staff.getName()+","+staff.getType());
				}else{
					pw.print("fail");
				}
			}else{
				pw.print("fail");
			}	
			break;
		case "person":
			if(StringUtil.isNotEmpty(login)){
				ResultSet rs=staffdao.getRsById(login);
				JSONArray jsonarr=new JSONArray();
				try {
					ResultSetMetaData rsmd=rs.getMetaData();
					int count=rsmd.getColumnCount();
					System.out.println(count);
					while(rs.next()){	
						JSONObject json=new JSONObject();
						for(int i=1;i<=count;i++){	
							json.put(rsmd.getColumnName(i), rs.getObject(i));	
						}
						jsonarr.add(json);
					}
					System.out.println(jsonarr.size());
					pw.println(jsonarr);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			break;
		case "update":
			if(StringUtil.isNotEmpty(login)){
				String slogin=request.getParameter("login");
				String sname=request.getParameter("name");
				String sage=request.getParameter("age");
				String ssex=request.getParameter("sex");
				String stime=request.getParameter("time");
				String stel=request.getParameter("tel");
				String saddress=request.getParameter("address");
				String sremark=request.getParameter("remark");
				if(StringUtil.isNotEmpty(slogin)){
					int ssage=0;
					Date date=null;
					date=DateUtils.parseDate("0000-00-00");
					if(StringUtil.isNotEmpty(sage)){
						ssage=Integer.parseInt(sage);
					}
					if(StringUtil.isNotEmpty(stime)){
						date=DateUtils.parseDate(stime);
					}
					Staff staff=new Staff(slogin,sname,ssage,ssex,date,stel,saddress,sremark);
					System.out.println("----"+slogin+","+sname+","+ssage+","+ssex+","+date+","+stel+","+saddress+","+sremark);
					if(staffdao.updateStaff(staff)){
						System.out.println("success");
						pw.print("success");
					}else{
						pw.print("fail");
					}
				}	
			}
			break;
		case "pwd":
			if(StringUtil.isNotEmpty(login)){
				String spwd=request.getParameter("pwd");
				if(StringUtil.isNotEmpty(spwd)){
					Staff staff=new Staff(login,spwd);
					if(staffdao.pwdStaff(staff)){
						pw.print("success");
					}else{
						pw.print("fail");
					}
				}else{
					pw.println("error");
				}	
			}
			break;
		case "sessionDel":
			if(StringUtil.isNotEmpty(type)){
				if(type.equals("0")){
					session.removeAttribute("empInfo");
					if(session.getAttribute("empInfo") != null){
						pw.print("fail");
					}else{
						pw.print("success");
					}
				}else{
					session.removeAttribute("adminInfo");
					if(session.getAttribute("adminInfo") != null){
						pw.print("fail");
					}else{
						pw.print("success");
					}
				}
			}
			break;
		case "add":
			String alogin=request.getParameter("login");
			String pwd=request.getParameter("pwd");
			String name=request.getParameter("name");
			String age=request.getParameter("age");
			String sex=request.getParameter("sex");
			String time=request.getParameter("time");
			String tel=request.getParameter("tel");
			String address=request.getParameter("address");
			String remark=request.getParameter("remark");
			String shop=staffdao.getShop(login);
			System.out.println(shop);
			int aage=0;
			Date date=new Date();
			if(StringUtil.isNotEmpty(time)){
				date=DateUtils.parseDate(time);
			}	
			if(StringUtil.isNotEmpty(age)){
				aage=Integer.parseInt(age);
			}
			System.out.println("----"+alogin+","+name+","+aage+","+sex+","+date+","+tel+","+address+","+remark+","+shop);
			if(StringUtil.isNotEmpty(alogin)&&StringUtil.isNotEmpty(pwd)&&StringUtil.isNotEmpty(name)){
				Staff staff=new Staff(alogin,name,pwd,0,aage,sex,date,tel,address,remark,shop);
				if(staffdao.isExistStaff(staff)){
					pw.print("exist");
				}else{
					if(staffdao.addStaff(staff)){
						pw.print("success");
					}else{
						pw.print("fail");
					}
				}	
			}else{
				pw.print("error");
			}
			break;
		case "list":
			String currentPage=request.getParameter("currentPage");
			String search=request.getParameter("search");
			int cpage=1;
			if(StringUtil.isNotEmpty(currentPage)){
				cpage=Integer.parseInt(currentPage);
			}
			String lshop=staffdao.getShop(login);
			ResultSet rs=staffdao.currentStaff(cpage,search,lshop);
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
			int total=staffdao.getTotalPage(psearch,pshop);
			if(total!=0){
				pw.print(total);
			}else{
				pw.print("empty");
			}
			break;
		case "read":
			String rlogin=request.getParameter("login");
			if(StringUtil.isNotEmpty(rlogin)){
				ResultSet rss=staffdao.getRsById(rlogin);
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
			String dlogin=request.getParameter("login");
			if(StringUtil.isNotEmpty(dlogin)){
				if(staffdao.deleteStaff(dlogin)){
					pw.print("success");
				}else{
					pw.print("fail");
				}
			}else{
				pw.print("error");
			}
			break;
		case "shop":
			String sshop=staffdao.getShop(login);
			System.out.println(sshop);
			ShopDao shopdao=new ShopDao();
			if(StringUtil.isNotEmpty(sshop)){
				String sname=shopdao.getById(sshop);
				pw.print(sname);
			}else{
				pw.print("fail");
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
