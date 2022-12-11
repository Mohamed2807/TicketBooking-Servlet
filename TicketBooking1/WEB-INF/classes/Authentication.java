package authentication;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/authentication")
public  class Authentication extends HttpServlet{
	public  void doGet(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
		boolean check=false;
		System.out.println("the method is called ");
		try {
			Cookie cookie[]=request.getCookies();
			System.out.println("the length of the cookie is "+cookie.length);
			for(Cookie c:cookie) {
				if(c.getName().equals("TicketBooking")) {
					if(!c.getValue().equals("null")) {
						request.getSession().setAttribute("mail",c.getValue());
						check=true;
						break;
					}
					else {
						check=false;
						break;
					}
				}
			}
		}catch(NullPointerException e) {
			System.out.println(e);
			check=false;
		}
		if(check) {
			response.sendRedirect("home");
		}else {
			request.getRequestDispatcher("/authentication/Authentication.html").include(request, response);
		}
	}
}
