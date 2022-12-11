package clientServer;


import java.time.LocalDate;
import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

import database.TrainsData;
import service.AreaService;

@WebServlet("/searchTrain")
public class SearchForm extends HttpServlet{

	public void doGet(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
		System.out.println(request.getParameter("from"));
		System.out.println(request.getParameter("to"));
		request.getSession().setAttribute("from", request.getParameter("from"));
		request.getSession().setAttribute("to", request.getParameter("to"));
		request.getSession().setAttribute("onward", request.getParameter("onward"));
		boolean checkDate=true;
		LocalDate date1 =java.time.LocalDate.now();
		try{
		LocalDate date2 =LocalDate.parse(request.getParameter("onward").toString());
		
		int todayYear=date1.getYear();
		int todayMonth=date1.getMonthValue();
	    int today=date1.getDayOfMonth();
	    int inputYear=date2.getYear();
		int inputMonth=date2.getMonthValue();
	    int inputDay=date2.getDayOfMonth();
	    
		if(todayYear<inputYear){

		}else if(todayYear==inputYear){
			if(todayMonth<inputMonth){

			}else if(todayMonth==inputMonth){
				if(today>inputDay){
					checkDate=false;
				}
			}else{
				checkDate=false;
			}
		}else{
			checkDate=false;
		}
		// int year1=date1.getYear();
		// int month1=date1.getMonthValue();
	    // int day1=date1.getDayOfMonth();
	    // int year=date2.getYear();
		// int month=date2.getMonthValue();
	    // int day=date2.getDayOfMonth();
	    // System.out.println(year1+""+month1+""+day1);
	    // System.out.println(year+""+month+""+day);
	    // if(year>=year1) {
	    // 	System.out.println("year1 is bigger");
	    // 	  if(month>=month1) {
	    // 		  System.out.println("year1 is bigger");
	    // 	  	if(day<day1){
	    // 	  		checkDate=false;
		// 		}
		// 	  }else{
		// 		checkDate=false;
		// 	  }
	    // }else {
	    // 	checkDate=false;
	    // }
		System.out.println("the date is "+java.time.LocalDate.now());
		System.out.println("the input date is "+request.getParameter("onward"));
		if(checkDate){
			if(new AreaService().searchTrains(request.getParameter("from"), request.getParameter("to"))) {
				System.out.println("its true");
				System.out.println("the backUp changed to trainList----------------------------------------------------------------------------------------------------------------------------------------------------------------");
				request.getSession().setAttribute("backUp","trainList");
				response.sendRedirect("trainList");
			}else {
				try{
				if(request.getParameter("from").equals("")&&request.getParameter("to").equals("")&&request.getParameter("onward").equals(""));
				
			System.out.println("its false");
			response.getWriter().print("<html><script>alert('no train is available in this Route');</script></html>");
			response.setContentType("text/html");
			System.out.println("done");
			request.getRequestDispatcher("/client3/home.html").include(request, response);
				}catch(NullPointerException e){
					System.out.println("its nulls");
					request.getRequestDispatcher("/client3/home.html").include(request, response);
				}
			}
				
		}else{
			response.getWriter().print("<html><script>alert('the date is invalid ');</script></html>");
			response.setContentType("text/html");
			System.out.println("done");
			request.getRequestDispatcher("/client3/home.html").include(request, response);
			
		}
	}catch(java.time.format.DateTimeParseException e){
		response.getWriter().print("<html><script>alert('the date is invalid ');</script></html>");
			response.setContentType("text/html");
			System.out.println("done");
			request.getRequestDispatcher("/client3/home.html").include(request, response);
			
	}
	}
}
