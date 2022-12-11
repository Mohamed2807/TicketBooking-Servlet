
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
import org.json.simple.JsonArray;

import service.AreaService;
import service.TrainRegistrationService;

@WebServlet(urlPatterns= {"/getTrainListData","/getSeatList","/getBoardingData"})
public class FilterData2 extends HttpServlet {
	
	public void doPost(HttpServletRequest request,HttpServletResponse response) throws IOException {
		JsonObject obj= new JsonObject();
        String from=request.getSession().getAttribute("from").toString();
        String to=request.getSession().getAttribute("to").toString();
        String date=request.getSession().getAttribute("onward").toString();
        String userId=request.getSession().getAttribute("mail").toString();
        AreaService areaService = new AreaService();
		if(request.getRequestURI().equals("/TicketBooking1/getTrainListData")) {
		
			try {
                
				obj.put("trainData",areaService.getHomeTrainData(areaService.getFiltertrains1(from, to), date));
				obj.put("from",from);
				obj.put("to",to);
				obj.put("onward",date);
                obj.put("user",userId);
				response.getWriter().write(obj.toJson());
			} catch (ClassNotFoundException e1) {
				e1.printStackTrace();
			} catch (SQLException e1) {
				e1.printStackTrace();
			} 
		
		}else if(request.getRequestURI().equals("/TicketBooking1/getBoardingData")){
			
			try {
                JsonArray array =areaService.getBoardingData(areaService.getFiltertrains1(from, to));
				response.getWriter().write(array.toJson());
			} catch (ClassNotFoundException e1) {
				e1.printStackTrace();
			} catch (SQLException e1) {
				e1.printStackTrace();
			} 
			
		}else if(request.getRequestURI().equals("/TicketBooking1/getSeatList")){
            try {
                JsonArray array =areaService.getHomeSeatListData(areaService.getFiltertrains1(from, to));
				response.getWriter().write(array.toJson());
			} catch (ClassNotFoundException e1) {
				e1.printStackTrace();
			} catch (SQLException e1) {
				e1.printStackTrace();
			} catch (DeserializationException e) {
				e.printStackTrace();
			}
        }
	}
}
