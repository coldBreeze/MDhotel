package com.mdhotel.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mdhotel.common.StringUtil;
import com.mdhotel.dao.StaffDao;
import com.mdhotel.model.Staff;

/**
 * Servlet implementation class RegisterServlet
 */
public class RegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RegisterServlet() {
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
		PrintWriter pw=response.getWriter();
		String type=request.getParameter("type");
		int typen=Integer.parseInt(type);
		String login=request.getParameter("login");
		String name=request.getParameter("name");
		String pwd=request.getParameter("pwd");
		StaffDao staffdao=new StaffDao();
		if(StringUtil.isNotEmpty(login)&&StringUtil.isNotEmpty(name)&&StringUtil.isNotEmpty(pwd)){
			Staff staff=new Staff(login,name,pwd,typen);
			if(staffdao.isExistStaff(staff)){
				pw.print("exist");
			}
			else{
				 if(staffdao.registerStaff(staff)){
						pw.print("success");
					}else{
						pw.print("fail");
					}
			}
		}else{
			pw.print("error");
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
