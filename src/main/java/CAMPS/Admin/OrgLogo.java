
package CAMPS.Admin;

import java.io.IOException;
import java.sql.SQLException;

import CAMPS.Connect.DBConnect;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(name = "org_logo_3443", description = "org_logo_3443", urlPatterns = { "/getLogo" })

public class OrgLogo extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		resp.setContentType("application/json");
		resp.setCharacterEncoding("UTF-8");

		String bgcolor = "";
		String logo = "";

		DBConnect con = new DBConnect();
		try {
			con.getConnection();
			con.read("SELECT IFNULL(signin_page_color,'#2196F3') AS bgcolor, IFNULL(org_logo_cdn,'') AS logo FROM camps.master_organization LIMIT 1");

			if (con.rs.next()) {
				bgcolor = con.rs.getString("bgcolor");
				logo = con.rs.getString("logo");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				con.closeConnection();
			} catch (SQLException e) {

			}
		}

		String json = "{" + "\"status\":true," + "\"data\":{" + "\"bgcolor\":\"" + bgcolor + "\"," + "\"logo\":\"" + logo + "\"" + "}," + "\"message\":\"Organization branding loaded1\"," + "\"remarks\":\"\"," + "\"about\":\"Organization Branding API\"" + "}";

		resp.getWriter().print(json);
	}
}
