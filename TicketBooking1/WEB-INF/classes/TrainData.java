package adminServer;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import service.TrainRegistrationService;

@WebServlet(urlPatterns={"/trainData","/trainDataWithDate"})
public class TrainData extends HttpServlet{
	
	public void doPost(HttpServletRequest request,HttpServletResponse response)  {
		
		try {
			try {
				if(request.getRequestURI().equals("/TicketBooking1/trainData")){
				System.out.println("the json data are");
				System.out.println(new TrainRegistrationService().getData());
				response.getWriter().write(new TrainRegistrationService().getData());}
				else{
					response.getWriter().write(new TrainRegistrationService().getDataWithTrainDate(request.getParameter("getTrain").toString()));
				}
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
