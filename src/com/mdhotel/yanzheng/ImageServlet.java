package com.mdhotel.yanzheng;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 验证码的servlet
 *
 */
public class ImageServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 构造方法
	 */
	public ImageServlet() {
		super();
	}

	/**
	 * 销毁servlet
	 */
	public void destroy() {
		super.destroy(); 
	}

	/**
	 * doGet（）方法 
	 *
	 * 当get请求到达时调用这个方法
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("image/jpeg"); 
        response.setHeader("Pragma", "No-cache"); 
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expire", 0);
        RandomValidateCode randomValidateCode = new RandomValidateCode();
        try {
            randomValidateCode.getRandcode(request, response); 
        } catch (Exception e) {
            e.printStackTrace();
        }
	}

	/**
	 * doPost（）方法 
	 *
	 * 当POST请求到达时调用这个方法
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		 doGet(request, response);
	}

	/**
	 * servlet的初始化方法
	 *
	 * @throws ServletException if an error occurs
	 */
	public void init() throws ServletException {
	}

}
