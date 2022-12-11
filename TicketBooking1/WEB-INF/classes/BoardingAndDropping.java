package clientServer;

import java.io.IOException;
import java.sql.SQLException;
import org.json.simple.JsonObject;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import service.AreaService;

@WebServlet(urlPatterns={"/boarding","/getRecord","/checkForRecord","/setRecord","/endRecord"})
public class BoardingAndDropping extends HttpServlet {
	public void doPost(HttpServletRequest request,HttpServletResponse response) throws IOException {
		try {
			if(request.getRequestURI().equals("/TicketBooking1/boarding")){
			response.getWriter().println(new AreaService().getPlacePointByID(request.getParameter("boarding")));
			}else if(request.getRequestURI().equals("/TicketBooking1/getRecord")){
				try{
					if(request.getSession().getAttribute("recordStatus").equals("true"))
						response.getWriter().print(request.getSession().getAttribute("record").toString());
						else{
							JsonObject json = new JsonObject();
						json.put("status","null");
						response.getWriter().print(json.toJson());
						}
						
					}catch(NullPointerException e){
						JsonObject json = new JsonObject();
						json.put("status","null");
						response.getWriter().print(json.toJson());
					}
			}
			else if(request.getRequestURI().equals("/TicketBooking1/setRecord")){
				request.getSession().setAttribute("record",request.getParameter("record"));
			}else if(request.getRequestURI().equals("/TicketBooking1/endRecord")){
				request.getSession().setAttribute("recordStatus","false");

			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
