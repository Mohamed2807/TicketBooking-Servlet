package clientServer;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.DeserializationException;
import org.json.simple.JsonObject;

import service.AreaService;
import service.TrainRegistrationService;

@WebServlet(urlPatterns= {"/filterTrain","/previousFilter","/nextFilter","/givenDateFilter"})
public class FilterTrain extends HttpServlet {
	
	public void doPost(HttpServletRequest request,HttpServletResponse response) throws IOException {
		JsonObject obj;
		if(request.getRequestURI().equals("/TicketBooking1/filterTrain")) {
		System.out.println("the method is filter Train"+request.getSession().getAttribute("mail"));
		System.out.println("the method is filter Train"+request.getSession().getAttribute("from"));
		System.out.println("the method is filter Train"+request.getSession().getAttribute("to"));
		System.out.println("the method is filter Train"+request.getSession().getAttribute("onward"));
			
			try {
				obj = new AreaService().getFilterTrains(request.getSession().getAttribute("from").toString(),  request.getSession().getAttribute("to").toString(),request.getSession().getAttribute("mail").toString(),request.getSession().getAttribute("onward").toString());
				obj.put("from",request.getSession().getAttribute("from"));
				obj.put("to",request.getSession().getAttribute("to"));
				obj.put("onward",request.getSession().getAttribute("onward"));
				response.getWriter().write(obj.toJson());
			} catch (ClassNotFoundException e1) {
				e1.printStackTrace();
			} catch (SQLException e1) {
				e1.printStackTrace();
			} catch (DeserializationException e) {
				e.printStackTrace();
			}
			try {
				
			}catch(NullPointerException e) {
			
		
			}
		
		}else if(request.getRequestURI().equals("/TicketBooking1/givenDateFilter")){
			
			try {
			obj = new AreaService().getFilterTrains(request.getSession().getAttribute("from").toString(),  request.getSession().getAttribute("to").toString(),request.getSession().getAttribute("mail").toString(),request.getParameter("onward").toString());
				obj.put("from",request.getSession().getAttribute("from"));
				obj.put("to",request.getSession().getAttribute("to"));
				obj.put("onward",request.getParameter("onward"));
				response.getWriter().write(obj.toJson());
			} catch (ClassNotFoundException e1) {
				e1.printStackTrace();
			} catch (SQLException e1) {
				e1.printStackTrace();
			} catch (DeserializationException e) {
				e.printStackTrace();
			}
			try {
				
			}catch(NullPointerException e) {
			
		
			}
		}
	}
}
