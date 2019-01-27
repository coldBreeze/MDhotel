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

import com.mdhotel.common.StringUtil;
import com.mdhotel.dao.PassengerDao;
import com.mdhotel.model.Passenger;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * Servlet implementation class PassengerServlet
 */
public class PassengerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PassengerServlet() {
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
		System.out.println("-------servlet");
		PrintWriter pw=response.getWriter();
		PassengerDao passdao=new PassengerDao();
		String path = request.getRequestURI();
		String action = path.substring(path.lastIndexOf("/") + 1, path.lastIndexOf("."));
		String name=request.getParameter("name");
		String age=request.getParameter("age");
		String sex=request.getParameter("sex");
		String idcard=request.getParameter("idcard");
		String tel=request.getParameter("tel");
		String remark=request.getParameter("remark");
		System.out.println("action:"+action);
		switch(action){
		case "add":
			int page=0;
			System.out.println(name+","+page+","+sex+","+idcard+","+tel+","+remark);
			if(StringUtil.isNotEmpty(name)&&StringUtil.isNotEmpty(idcard)&&StringUtil.isNotEmpty(tel)){
				if(StringUtil.isNotEmpty(age)){
					page=Integer.parseInt(age);	
				}	
				Passenger pass=new Passenger(name,page,sex,idcard,tel,remark);
				if(passdao.addPassenger(pass)){
					pw.print("success");
				}else{
					pw.print("fail");
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
			ResultSet rs=passdao.currentPassenger(cpage,search);
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
			int total=passdao.getTotalPage(psearch);
			if(total!=0){
				pw.print(total);
			}else{
				pw.print("empty");
			}
			break;
		case "read":
			String code=request.getParameter("code");
			if(StringUtil.isNotEmpty(code)){
				ResultSet rss=passdao.getByCode(code);
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
			String code1=request.getParameter("code");
			String name1=request.getParameter("name");
			String age1=request.getParameter("age");
			String sex1=request.getParameter("sex");
			String idcard1=request.getParameter("idcard");
			String tel1=request.getParameter("tel");
			String remark1=request.getParameter("remark");
			
			if(StringUtil.isNotEmpty(code1)){
				int ppage=0;
				if(StringUtil.isNotEmpty(age1)){
					ppage=Integer.parseInt(age1);
				}
				
				Passenger pass=new Passenger(code1,name1,ppage,sex1,idcard1,tel1,remark1);
				System.out.println(code1+","+name1+","+age1+","+sex1+","+idcard1+","+tel1+","+remark1);
				if(passdao.updatePassenger(pass)){
					System.out.println("success");
					pw.print("success");
				}else{
					pw.print("fail");
				}
			}else{
				pw.print("error");
			}
			break;
		case "delete":
			String dcode=request.getParameter("code");
			if(StringUtil.isNotEmpty(dcode)){
				if(passdao.deletePassenger(dcode)){
					pw.print("success");
				}else{
					pw.print("fail");
				}
			}else{
				pw.print("error");
			}
			break;
		case "info":
			ResultSet irs=passdao.getList();
			JSONArray ijsonarr=new JSONArray();
			try {
				ResultSetMetaData rsmd=irs.getMetaData();
				int count=rsmd.getColumnCount();
				while(irs.next()){	
					JSONObject json=new JSONObject();
					for(int i=1;i<=count;i++){	
						if(irs.getObject(i)==null){
							json.put(rsmd.getColumnName(i), "");	
						}else{
							json.put(rsmd.getColumnName(i), irs.getObject(i));	
						}
					}
					ijsonarr.add(json);
				}
				System.out.println(ijsonarr.size());
				pw.println(ijsonarr);
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
