package adminServer;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import database.UserData;

@WebServlet("/adminHome")
public class Home extends HttpServlet {
	boolean checkRegister=true;
	public void doGet(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
		try {
			UserData userData = new UserData();
			if(request.getParameter("adminname").equals(null));
			if(userData.addAdmin(request.getParameter("adminname"), request.getParameter("adminpass"))) {
				System.out.println("admin sign Up sucessfully failed ");
				response.sendRedirect("adminSignUp");
			}else {
				request.getRequestDispatcher("admin3/admin.html").forward(request, response);
				System.out.println("admin sign Up sucessfully completed ");
			}
		}
		catch(NullPointerException e) {
			// request.getRequestDispatcher("admin3/admin.html").forward(request, response);
			checkRegister=false;
		}catch( SQLException e) {
			System.out.println("admin sign up failed from adminHome class");
		}catch(ClassNotFoundException e) {
			System.out.println("class not found from adminHome class");
		}
		try{
			if(!checkRegister)
			if(request.getSession().getAttribute("admin").equals("signin")){
				request.getRequestDispatcher("admin3/admin.html").forward(request, response);
			}else{
				response.sendRedirect("authentication");
			}
		}catch(NullPointerException e){
			response.sendRedirect("authentication");
		}
	}
}
