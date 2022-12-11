package clientServer;

import java.io.IOException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import service.AreaService;

@WebServlet(urlPatterns={"/getArea"})
public class Place extends HttpServlet{

	public void doPost(HttpServletRequest request,HttpServletResponse response) throws IOException {
		if(request.getRequestURI().equals("/TicketBooking1/getArea")){
		response.getWriter().write(new AreaService().getdata(request.getSession().getAttribute("mail").toString()));
		}
	}
}
