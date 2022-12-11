package adminServer;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import database.SeatsListData;
import service.PassengerDataService;

@WebServlet(urlPatterns= {"/queryBookings","/queryBookingByDate","/queryBookingByid","/queryAllBookings"})
public class PassengerData extends HttpServlet{
	public void doPost(HttpServletRequest request,HttpServletResponse response) {
		System.out.println("call from query");
	try {
		PassengerDataService passengerService=new PassengerDataService();
		if(request.getRequestURI().equals("/TicketBooking1/queryBookings")) {
			System.out.println(request.getRequestURI()+"=>"+request.getParameter("date")+"=>"+request.getParameter("trainID"));
		response.getWriter().println( passengerService.getDataWithDateAndTrain(request.getParameter("date"), request.getParameter("trainID")));
		}else if(request.getRequestURI().equals("/TicketBooking1/queryBookingByDate")) {
			System.out.println(request.getRequestURI()+"=>"+request.getParameter("date"));
			response.getWriter().println(passengerService.filterWithDate(request.getParameter("date")));
		}else if(request.getRequestURI().equals("/TicketBooking1/queryBookingByid")) {
			System.out.println(request.getRequestURI()+"=>"+request.getParameter("id"));
			response.getWriter().println(passengerService.filterWithtrainID(request.getParameter("id")));
		}else if(request.getRequestURI().equals("/TicketBooking1/queryAllBookings")) {
			System.out.println(request.getRequestURI());
			response.getWriter().println(passengerService.getAllPassengerData());
		}
		passengerService.closeSeatListDataBase();
		} catch (IOException | ClassNotFoundException | SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	}

}
