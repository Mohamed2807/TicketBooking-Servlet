package authentication;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import database.UserData;
@WebServlet("/checkMail")
public class MailVerification extends HttpServlet {
	UserData storage ;
	public  void doGet(HttpServletRequest request,HttpServletResponse response) throws IOException{
		try {
			storage = new UserData();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			if(storage.verifyEmail(request.getParameter("mail"))) {
				response.getWriter().println("true");
			
			}else {
				if(storage.verifyAdminmail(request.getParameter("mail"))){
					response.getWriter().println("true");
				}else
				response.getWriter().println("false");
			
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
