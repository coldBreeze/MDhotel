package com.mdhotel.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.mdhotel.common.CookieDemo;
import com.mdhotel.common.StringUtil;
import com.mdhotel.dao.StaffDao;
import com.mdhotel.model.Staff;


/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
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
		HttpSession session=request.getSession();
		String radncode=session.getAttribute("RANDOMVALIDATECODEKEY").toString();//RANDOMVALIDATECODEKEY
		StaffDao staffDao=new StaffDao();
		String login=request.getParameter("login");
		String pwd=request.getParameter("pwd");
		String randomCode=request.getParameter("randomC");
		String remember=request.getParameter("remember");
		if(StringUtil.isNotEmpty(login)&&StringUtil.isNotEmpty(pwd)&&StringUtil.isNotEmpty(randomCode)){
				int typen=0;
				if(type.equals("emp")){
					typen=0;
				}
				else if(type.equals("admin")){
					typen=1;
				}else if(type.equals("sys")){
					typen=2;
				}
				Staff staff=new Staff(login,pwd,typen);
				if(staffDao.staffLogin(staff)){
					if(randomCode.toUpperCase().equals(radncode.toUpperCase())){
						if(StringUtil.isNotEmpty(remember)&&remember.equals("1")){
							if(StringUtil.isNotEmpty(type)){
								if(type.equals("emp")){			
										CookieDemo.addFormCookie(type,login, pwd, response);
										
								}else if(type.equals("admin")){
										CookieDemo.addFormCookie(type,login, pwd, response);
								}else if(type.equals("sys")){
									CookieDemo.addFormCookie(type,login, pwd, response);
							}
							}	
						}
						if(type.equals("emp")){
							session.setAttribute("empInfo",login);
							session.setMaxInactiveInterval(3600);
							System.out.println("addempSession");
						}else if(type.equals("admin")){
							session.setAttribute("adminInfo",login);
							session.setMaxInactiveInterval(3600);
							System.out.println("addadminSession");
						}else if(type.equals("sys")){
							session.setAttribute("sysInfo",login);
							session.setMaxInactiveInterval(3600);
							System.out.println("addsysSession");
						}
						pw.print("success");
					}else{
						pw.print("randomNo");
					}	
				}else{
					pw.print("fail");
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
