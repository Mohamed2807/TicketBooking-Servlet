package adminServer;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JsonArray;
import org.json.simple.JsonObject;

import database.AreaData;

@WebServlet(urlPatterns= {"/getAllTheStopsData"})
public class FilterBoarding extends HttpServlet {
	public void doPost(HttpServletRequest request,HttpServletResponse response) throws IOException {
		try {
			JsonObject obj = null;
			JsonArray array = new JsonArray();
			ResultSet result = new AreaData().getPlacesByID(request.getParameter("trainID"));
			while(result.next()) {
				obj = new JsonObject();
				obj.put(result.getString(2), result.getString(3));
				array.add(obj);
			}
			response.getWriter().write(array.toJson());
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
