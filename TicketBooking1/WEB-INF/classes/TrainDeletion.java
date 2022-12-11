package adminServer;

import java.sql.SQLException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import database.TrainsData;

@WebServlet("/deleteTrain")
public class TrainDeletion extends  HttpServlet {
	public void doGet(HttpServletRequest request,HttpServletResponse response) {
		System.out.println("the method is called"+request.getParameter("trainID"));
		try {
			TrainsData trainsData= new TrainsData();
			trainsData.deleteTrain(Integer.parseInt(request.getParameter("trainID")));
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
