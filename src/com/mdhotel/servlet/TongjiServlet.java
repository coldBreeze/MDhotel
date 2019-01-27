package com.mdhotel.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.mdhotel.common.DateUtils;
import com.mdhotel.common.StringUtil;
import com.mdhotel.dao.GoodsDao;
import com.mdhotel.dao.OrderDao;
import com.mdhotel.dao.PassengerDao;
import com.mdhotel.dao.StaffDao;

/**
 * Servlet implementation class TongjiServlet
 */
public class TongjiServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TongjiServlet() {
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
		System.out.println("-------TongjiServlet");
		PrintWriter pw=response.getWriter();
		PassengerDao passdao=new PassengerDao();
		OrderDao orderdao=new OrderDao();
		GoodsDao goodsdao=new GoodsDao();
		String path = request.getRequestURI();
		String action = path.substring(path.lastIndexOf("/") + 1, path.lastIndexOf("."));
		HttpSession session=request.getSession();
		String emplogin=(String)session.getAttribute("empInfo");
		String adminlogin=(String)session.getAttribute("adminInfo");
		String syslogin=(String)session.getAttribute("sysInfo");
		String type=request.getParameter("type");
		String login="";
		StaffDao staffdao=new StaffDao();
		
		if(StringUtil.isNotEmpty(type)){
			if(type.equals("0")){
				login=emplogin;
			}else if(type.equals("1")){
				login=adminlogin;
			}else if(type.equals("2")){
				login=syslogin;
			}
		}
		System.out.println(type+","+login);
		switch(action){
		case "pass":
			String shop=request.getParameter("shop");
			Date date=new Date();
			String[] dateArr=new String[7];
			int[] countArr=new int[7];
			for(int i=0;i<7;i++){
				String day=DateUtils.getPrevDay(date);
				date=DateUtils.getPrevDate(date);
				dateArr[i]=day.substring(0,2)+" / "+day.substring(2);
				countArr[i]=passdao.getDayCount(day,shop);
			}
			pw.print(Arrays.toString(dateArr)+"-"+Arrays.toString(countArr));
			break;
		case "good":
		    String gshop=request.getParameter("shop");
		    if(StringUtil.isEmpty(gshop)){
		    	
		    	gshop=staffdao.getShop(login);
		    }
			String names=goodsdao.getGoodsName();
			String[] nameArr=names.split(",");
			int[] buyArr=new int[8];
			int[] sellArr=new int[8];
			for(int i=0;i<nameArr.length;i++){
				System.out.println(nameArr[i]);
				buyArr[i]=orderdao.getBuyCount(nameArr[i],gshop);
				sellArr[i]=orderdao.getSellCount(nameArr[i],gshop);
			}
			pw.print(Arrays.toString(nameArr)+"-"+Arrays.toString(buyArr)+"-"+Arrays.toString(sellArr));	
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
