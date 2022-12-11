package clientServer;
import service.PassengerDataService;
import java.sql.SQLException;
import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;





@WebServlet(urlPatterns={"/checkSeats"})
public class CheckSeats extends HttpServlet{
	
	public synchronized void doPost(HttpServletRequest request ,HttpServletResponse response) throws ServletException,IOException {
		try{
			
					PassengerDataService passengerData = new PassengerDataService();
					response.getWriter().print(passengerData.checkSeats(request.getParameter("seatChecking")));
					passengerData.closeSeatListDataBase();
			
		}catch( ClassNotFoundException |SQLException e){
			System.out.println("error from bookseats urls");
			}
		
	}
		

}
