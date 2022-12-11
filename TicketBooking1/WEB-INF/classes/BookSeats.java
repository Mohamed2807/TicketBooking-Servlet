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


@WebServlet(urlPatterns={"/bookSeats"})
public class BookSeats extends HttpServlet{
	
	public  void doPost(HttpServletRequest request ,HttpServletResponse response) throws ServletException,IOException {
		try{
					System.out.println("the book seats url called ");
					request.getSession().setAttribute("recordStatus","false");
					request.getSession().setAttribute("backUp","home");
					PassengerDataService passengerData = new PassengerDataService();
					if(!request.getSession().getAttribute("mail").equals("null")){
						if(passengerData.registerSeats(request.getParameter("seatList"),request.getSession().getAttribute("mail").toString())){
							response.getWriter().print("true");
						}else{
							response.getWriter().print("false");
						
							}
							passengerData.closeSeatListDataBase();
						}
					else{
						response.getWriter().print("null");
					}
			
		}catch(DeserializationException | ClassNotFoundException |SQLException e){
			System.out.println("error from bookseats urls");
			}
		
	}
		

}
