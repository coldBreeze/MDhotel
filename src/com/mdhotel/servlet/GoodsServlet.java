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
import com.mdhotel.dao.GoodsDao;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * Servlet implementation class GoodsServlet
 */
public class GoodsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GoodsServlet() {
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
		System.out.println("-------Goodsservlet");
		PrintWriter pw=response.getWriter();
		GoodsDao gooddao=new GoodsDao();
		String path = request.getRequestURI();
		String action = path.substring(path.lastIndexOf("/") + 1, path.lastIndexOf("."));
		switch(action){
		case "list":
			String currentPage=request.getParameter("currentPage");
			String search=request.getParameter("search");
			int cpage=1;
			if(StringUtil.isNotEmpty(currentPage)){
				cpage=Integer.parseInt(currentPage);
			}
			ResultSet rs=gooddao.currentGoods(cpage,search);
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
			int total=gooddao.getTotalPage(psearch);
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
