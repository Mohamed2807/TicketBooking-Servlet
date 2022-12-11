package clientServer;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/trainList")
public class TrainList extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try{
			System.out.println("the before backUp attribute is "+request.getSession().getAttribute("backUp"));

			if(request.getSession().getAttribute("trainList").equals("")||request.getSession().getAttribute("from").equals("")||request.getSession().getAttribute("to").equals("")||request.getSession().getAttribute("onward").equals("")){
				response.sendRedirect("home");
			}else{
				request.getSession().setAttribute("backUp","trainList");
				System.out.println("<------------------------------------------------->");
				request.getRequestDispatcher("/client3/trainlist.html").forward(request, response);
			}
			System.out.println("the after backUp attribute is "+request.getSession().getAttribute("backUp"));

		}catch(NullPointerException e){
			response.sendRedirect("home");
		}
	}
}
