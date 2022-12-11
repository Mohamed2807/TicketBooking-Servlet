package adminServer;

import java.sql.SQLException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.DeserializationException;

import service.TrainRegistrationService;
import service.TrainUpdateService;

@WebServlet(urlPatterns={"/updateTrainData","/updateTrainID"})
public class UpdateTrains extends HttpServlet {

	public void doPost(HttpServletRequest request,HttpServletResponse response) {
		if(request.getRequestURI().equals("/TicketBooking1/updateTrainData")) {
				try {
					new TrainUpdateService().getAllDataInSameTrain(request.getParameter("newData"));
				} catch (DeserializationException | ClassNotFoundException | SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}		
		}else if(request.getRequestURI().equals("/TicketBooking1/updateTrainID")) {
			new TrainUpdateService().getAllDataInDiffTrain(request.getParameter("newData"), request.getParameter("trainID"));
		}
	}
}
