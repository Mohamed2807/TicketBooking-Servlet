package clientServer;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import database.UserData;

@WebServlet("/home")
public class ClientHome extends HttpServlet {
	UserData storage;
	
	static boolean checkFirst=false;
	public  void doGet(HttpServletRequest request,HttpServletResponse response) throws IOException, ServletException  {
		response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1.
		response.setHeader("Pragma", "no-cache"); // HTTP 1.0.
		response.setDateHeader("Expires", 0); 
		System.out.println("home page called ");
		boolean check=true;
		try {
			storage= new UserData();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		boolean checkRecord=false;
		try{
			String var1=request.getSession().getAttribute("recordStatus");
			String var2="null";

			System.out.println("hey");
			if(checkFirst){
			if(request.getSession().getAttribute("recordStatus").equals("true")){
				request.getSession().setAttribute("trainList","pass");
				
				checkRecord=true;
				System.out.println("its true the backup is "+request.getSession().getAttribute("backUp")+"------------------------------------------------------------------------------");
			}
		}else{

		}
			
		}catch(NullPointerException e){

		}
		
		try {
			if(request.getParameter("role").equals("signIn")) {
				
				try {
					 if(storage.verifyAdmin(request.getParameter("mail"),  request.getParameter("pass"))) {
						request.getSession().setAttribute("admin","signin");
						response.sendRedirect("adminHome");
					}else {
						request.getSession().setAttribute("admin","null");
						if(storage.checkUser(request.getParameter("mail"), request.getParameter("pass"))) {
							System.out.println("the session id from home "+request.getSession().getId());
							Cookie cookie =new Cookie("TicketBooking",request.getParameter("mail"));
						    cookie.setMaxAge(2*24*60*60);
							response.addCookie(cookie);
							System.out.println("the coookie is added");
							request.getSession().setAttribute("mail",request.getParameter("mail"));
							request.getSession().setAttribute("admin","null");
							System.out.println("the check record is "+checkRecord);
							System.out.println("the backUp attribute sign in is "+request.getSession().getAttribute("backUp"));

							if(checkRecord){
								if(request.getSession().getAttribute("backUp").equals("home")){
									request.getRequestDispatcher("/client3/home.html").forward(request, response);
								}else{
									response.sendRedirect("trainList");
								}
							}else{
								request.getSession().setAttribute("trainList","pass");
								request.getRequestDispatcher("/client3/home.html").forward(request, response);
							}
						}else {
							response.getWriter().println("the given password is wrong "); 
							response.setContentType("text/html");
							request.getRequestDispatcher("/authentication/Authentication.html").include(request, response);
							System.out.println("hey5");
							//  response.sendRedirect("authentication");	
						}
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}else {
				try {
					if(request.getParameter("mail").equals("change")) {
						response.sendRedirect("adminSignUp");
					}
					else {
						System.out.println("sign up page is called ");
						if(storage.addUsers(request.getParameter("name"), request.getParameter("pass"), request.getParameter("mail"))) {
							Cookie cookie =new Cookie("TicketBooking",request.getParameter("mail"));
							request.getSession().setAttribute("mail",request.getParameter("mail"));
							cookie.setMaxAge(2*24*60*60);
							response.addCookie(cookie);
							//response.sendRedirect("home.html");
							System.out.println("the backUp attribute from sign Up is "+request.getSession().getAttribute("backUp"));

							if(checkRecord){
								if(request.getSession().getAttribute("backUp").equals("home")){
									request.getRequestDispatcher("/client3/home.html").forward(request, response);
								}else{
									response.sendRedirect("trainList");
								}
							}else{
								request.getSession().setAttribute("trainList","pass");
								request.getRequestDispatcher("/client3/home.html").forward(request, response);
							}
							
							request.getSession().setAttribute("admin","null");
							
						}else {
							response.sendRedirect("authentication");
		//				request.getRequestDispatcher("/authentication").forward(request, response);
						}
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}	
			}
		}catch(NullPointerException e) {
			System.out.println("exception occurs");
			check=false;
		}
		if(!check) {
			try {
				Cookie cookie =new Cookie("TicketBooking",request.getSession().getAttribute("mail").toString());
				cookie.setMaxAge(2*24*60*60);
				response.addCookie(cookie);
				System.out.println("the session is "+request.getSession().getAttribute("mail"));
				request.getSession().setAttribute("admin","null");
				if(request.getSession().getAttribute("mail").equals("null")){
					request.getSession().setAttribute("recordStatus","true");
				}
				if(request.getSession().getAttribute("recordStatus").equals("true")&& !request.getSession().getAttribute("mail").equals("null")){
					if(request.getSession().getAttribute("backUp").equals("home")){
						System.out.println("the backUp attribute is "+request.getSession().getAttribute("backUp"));
						request.getRequestDispatcher("/client3/home.html").forward(request, response);
					}else{
						response.sendRedirect("trainList");
					}
				}else{
					request.getRequestDispatcher("/client3/home.html").forward(request, response);
				}
				}catch(NullPointerException e) {
					request.getSession().setAttribute("admin","null");
					request.getSession().setAttribute("recordStatus","true");
					request.getSession().setAttribute("mail","null");
					request.getSession().setAttribute("backUp","home");
					request.getRequestDispatcher("/client3/home.html").forward(request, response);
				}
		}
		request.getSession().setAttribute("trainList","pass");

	}
}
