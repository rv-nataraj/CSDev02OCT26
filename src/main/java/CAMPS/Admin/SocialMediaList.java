
package CAMPS.Admin;

import java.io.IOException;
import java.sql.SQLException;

import CAMPS.Connect.DBConnect;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(name = "social_media_343", description = "social_media_343", urlPatterns = { "/getSM" })

public class SocialMediaList extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		resp.setContentType("application/json");
		resp.setCharacterEncoding("UTF-8");
		DBConnect con = new DBConnect();
		StringBuilder json = new StringBuilder();
		json.append("[");

		try {
			con.getConnection();
			con.read("SELECT sm_color, sm_icon, sm_link " + "FROM admin.social_media " + "WHERE rstatus > 0 ORDER BY order_no");

			boolean first = true;
			while (con.rs.next()) {
				if (!first)
					json.append(",");
				first = false;

				json.append("{").append("\"color\":\"").append(con.rs.getString("sm_color")).append("\",").append("\"icon\":\"").append(con.rs.getString("sm_icon")).append("\",").append("\"link\":\"").append(con.rs.getString("sm_link")).append("\"").append("}");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				con.closeConnection();
			} catch (SQLException e) {

			}
		}

		json.append("]");
		resp.getWriter().print(json.toString());
	}
}
