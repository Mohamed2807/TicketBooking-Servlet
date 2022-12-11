package adminServer;

import java.sql.SQLException;

import javax.servlet.Registration;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import service.TrainRegistrationService;

@WebServlet("/registerTrain")
public class TrainRegistration extends HttpServlet{
	public void doPost(HttpServletRequest request,HttpServletResponse response) {
		
		try {
			new TrainRegistrationService().registerTrain(request.getParameter("addTrain"));
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
