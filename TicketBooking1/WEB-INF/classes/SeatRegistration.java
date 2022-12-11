package clientServer;
import service.PassengerDataService;
import java.sql.SQLException;
import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.simple.DeserializationException;

import database.SeatsListData;


@WebServlet(urlPatterns={"/getAllHistory","/userHistory","/deleteSeats"})
public class SeatRegistration extends HttpServlet{
	public void doGet(HttpServletRequest request ,HttpServletResponse response) throws ServletException,IOException {
		 if(request.getRequestURI().equals("/TicketBooking1/userHistory")){
			request.getRequestDispatcher("client3/bookList.html").forward(request,response);
		}
	
	}
	public void doPost(HttpServletRequest request ,HttpServletResponse response) throws ServletException,IOException {
			if(request.getRequestURI().equals("/TicketBooking1/getAllHistory")){
				try {
					
					System.out.println(request.getSession().getAttribute("mail").toString());
					response.getWriter().println(new PassengerDataService().getPassengerByUser(request.getSession().getAttribute("mail").toString()));
				} catch (ClassNotFoundException |SQLException  e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}else if(request.getRequestURI().equals("/TicketBooking1/deleteSeats")){
			
			try{
				SeatsListData seatListdata = new SeatsListData();
			if(seatListdata.deleteSeats(request.getParameter("trainId"), request.getParameter("date"), request.getParameter("seat"), request.getParameter("coach"))){
				response.getWriter().print("true");
			}else{
				response.getWriter().print("false");
			}
		}catch(ClassNotFoundException | SQLException e){
			System.out.println("class not error from deleteSeats");
		}
		}
	}

}
