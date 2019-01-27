package com.mdhotel.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.mdhotel.common.DateUtils;
import com.mdhotel.common.StringUtil;
import com.mdhotel.dao.AccomDao;
import com.mdhotel.dao.StaffDao;
import com.mdhotel.model.Accom;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * Servlet implementation class AccomServlet
 */
public class AccomServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AccomServlet() {
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
		System.out.println("-------Accomservlet");
		PrintWriter pw=response.getWriter();
		AccomDao accomdao=new AccomDao();
		String path = request.getRequestURI();
		String action = path.substring(path.lastIndexOf("/") + 1, path.lastIndexOf("."));
		System.out.println(action);
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
		case "add":
			String code=request.getParameter("code");
			String room=request.getParameter("room");
			String pass=request.getParameter("pass");
			String intime=request.getParameter("intime");
			String outime=request.getParameter("outime");
			String order=request.getParameter("order");
			String price=request.getParameter("price");
			String staff=request.getParameter("staff");
			String remark=request.getParameter("remark");
			String state=request.getParameter("state");
			int aprice=0;
			if(StringUtil.isNotEmpty(price)){
				aprice=Integer.parseInt(price);
			}
			Accom accom=new Accom(code,room,pass,DateUtils.parseDate(intime),DateUtils.parseDate(outime),order,aprice,staff,remark);
			if(accomdao.addAccom(accom)){
				System.out.println("加进去了"+state);
				if(StringUtil.isNotEmpty(state)&&state.equals("-1")){
					if(accomdao.updateState2(code)){
						pw.print("success");
					}else{
						pw.print("fail");
					}
				}	else{
					pw.print("success");
				}
			}else{
				pw.print("fail");
			}
			break;
		case "list":
			String currentPage=request.getParameter("currentPage");
			String search=request.getParameter("search");
			String shop=staffdao.getShop(login);
			
			int cpage=1;
			if(StringUtil.isNotEmpty(currentPage)){
				cpage=Integer.parseInt(currentPage);
			}
			ResultSet rs=accomdao.currentAccom(cpage,search,shop);
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
			int total=accomdao.getTotalPage(psearch,pshop);
			
			if(total!=0){
				pw.print(total);
			}else{
				pw.print("empty");
			}
			break;
		case "read":
			String rcode=request.getParameter("code");
			System.out.println(rcode);
			if(StringUtil.isNotEmpty(rcode)){
			
				ResultSet rss=accomdao.getByCode(rcode);
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
		case "update":
			String ucode=request.getParameter("code");
			String utime=request.getParameter("outime");
			String uprice=request.getParameter("price");
			String uremark=request.getParameter("remark");
			if(StringUtil.isNotEmpty(uprice)){
				Accom uaccom=new Accom(ucode,DateUtils.parseDate(utime),Integer.parseInt(uprice),uremark);
				if(accomdao.updateTime(uaccom)){
					pw.print("success");
				}else{
					pw.print("fail");
				}
			}else{
				pw.print("error");
			}
			break;
		case "order":
			String ocode=request.getParameter("code");
			String oorder=request.getParameter("order");
			String oprice=request.getParameter("price");
			if(StringUtil.isNotEmpty(oprice)){
				Accom oaccom=new Accom(ocode,oorder,Integer.parseInt(oprice));
				if(accomdao.updateOrder(oaccom)){
					pw.print("success");
				}else{
					pw.print("fail");
				}
			}else{
				pw.print("error");
			}
			break;
		case "out":
			String outcode=request.getParameter("code");
			System.out.println(outcode);
			if(StringUtil.isNotEmpty(outcode)){
				if(accomdao.updateState(outcode)){
					pw.print("success");
				}else{
					pw.print("fail");
				}
			}else{
				pw.print("error");
			}
			break;
		case "in":
			String incode=request.getParameter("code");
			System.out.println(incode);
			if(StringUtil.isNotEmpty(incode)){
				if(accomdao.updateState0(incode)){
					pw.print("success");
				}else{
					pw.print("fail");
				}
			}else{
				pw.print("error");
			}
			
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
