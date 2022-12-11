package authentication;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns={"/logout","/adminlogout"})
public class LogOut extends HttpServlet {
	public  void doGet(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
	if(request.getRequestURI().equals("/TicketBooking1/logout")){
		Cookie cookie =new Cookie("TicketBooking","null");
	cookie.setMaxAge(2*24*60*60);
	response.addCookie(cookie);
	request.getSession().setAttribute("recordStatus","false");
	request.getSession().setAttribute("mail","null");
	request.getSession().setAttribute("backUp","home");

	System.out.println(	request.getSession().getAttribute("mail"));
	}else{
		request.getSession().setAttribute("admin","null");
		System.out.println(request.getSession().getAttribute("admin"));
	}
	
	response.sendRedirect("authentication");
	}
}
