package CAMPS.Admin;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import CAMPS.Common.StaffStudentSearch;
import CAMPS.Common.UserInfo;
import CAMPS.Connect.DBConnect;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet(name = "role_allocation_controller", description = "role_allocation_controller", urlPatterns = { "/JSP/Admin/RoleAllocationController.do" })

public class RoleAllocationController extends HttpServlet {

	private static final long serialVersionUID = 1L;

	protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		DBConnect db = new DBConnect();
		try (PrintWriter out = response.getWriter()) {
			try {
				db.getConnection();
				HttpSession session = request.getSession();

				if (session.getAttribute("role_name").toString().contains("Role Management")) {
					if (request.getParameter("option") != null && request.getParameter("option").equalsIgnoreCase("role_select")) {
						String critical_role_condition = " ";
						if (session.getAttribute("ss_id").toString().equalsIgnoreCase("1"))
							critical_role_condition = " ";
						else
							critical_role_condition = " and rm.critical=0 ";
						out.print("<form action='RoleAllocationController.do' method='post'><ul id=\"listSelection\">");
						db.read("SELECT rm.role_id,rm.role_name,rm.display_name,IFNULL(rm.description,'')description,IF(um.staff_id IS NOT NULL,'Checked','') checked_data,ifnull(rm.help_text,'-') help_text FROM admin.role_master rm LEFT JOIN admin.user_master um ON FIND_IN_SET(rm.role_id,um.roles) AND um.staff_id='" + request.getParameter("staff_id") + "' where rm.status>0 " + critical_role_condition + " order by rm.display_name");
						while (db.rs.next()) {
							out.print("<li title=\"" + db.rs.getString("help_text") + "\"><strong>" + db.rs.getString("display_name") + "</strong></br><span   class=\"fcbkitem_text\">" + db.rs.getString("role_name") + "</span><input   type=\"checkbox\" style=\"visibility: hidden\" name=\"role\" id=\"role_" + db.rs.getString("role_id") + "\" value=\"" + db.rs.getString("role_id") + "\" " + db.rs.getString("checked_data") + " /></li>");
						}
						out.print("</ul><center><button class=\"btn btn-primary waves-effect\" type=\"submit\"  name=\"add_role\" id=\"add_role\" value='Save'>Save</button><script>function add_role(){alert($('[name=role]').val());}</script> </center><input type='hidden' name='staff_id' value='" + request.getParameter("staff_id") + "' /></form>");
					}

					else if (request.getParameter("add_role") != null && request.getParameter("add_role").equalsIgnoreCase("Save")) {
						if (session.getAttribute("role_name").toString().contains("Role Management")) {
							if (!session.getAttribute("ss_id").toString().equalsIgnoreCase("1") && request.getParameter("staff_id").equalsIgnoreCase("1"))
								response.sendRedirect("./role_allocation.jsp?am_c=2");
							else {
								out.print(String.join(",", request.getParameterValues("role")));
								db.update("UPDATE admin.`user_master` um INNER JOIN (SELECT GROUP_CONCAT(a.role_id order by a.role_id) roles, a.staff_id FROM (SELECT rm.role_id, um.`staff_id` FROM admin.`user_master` um INNER JOIN admin.`role_master` rm ON FIND_IN_SET(rm.`role_id`,um.`roles`) AND um.`staff_id`='" + request.getParameter("staff_id") + "' AND rm.`critical`>0 UNION SELECT rm.`role_id`, '" + request.getParameter("staff_id") + "' staff_id FROM admin.`role_master` rm WHERE rm.`role_id` IN (" + String.join(",", request.getParameterValues("role")) + ") AND rm.`critical`=0 )a)a ON a.staff_id=um.`staff_id` SET um.`roles`=" + (session.getAttribute("ss_id").toString().equalsIgnoreCase("1") ? "'" + String.join(",", request.getParameterValues("role")) + "'" : " a.roles ") + ", um.updated_by='" + session.getAttribute("ss_id") + "', um.updated_date=NOW()");
								response.sendRedirect("./role_allocation.jsp?am_c=1");
							}
						}
						response.sendRedirect("./role_allocation.jsp?am_c=2");
					}

					else if (request.getParameter("option") != null && request.getParameter("option").equalsIgnoreCase("loadUserDetails")) {
						UserInfo ui = new UserInfo();
						out.print(ui.userInfo("1", request.getParameter("staff_id").replaceAll("[^0-9]", "")));
					}

					else if (request.getParameter("option") != null && request.getParameter("option").equalsIgnoreCase("loadSearchDetails")) {
						StaffStudentSearch sss = new StaffStudentSearch();
						out.print(sss.loadStaffSearchResults(request.getParameter("search_string"), session.getAttribute("ss_id").toString()));
					}
				}

			} catch (Exception e) {
				out.print(e);
			} finally {
				try {
					db.closeConnection();
				} catch (SQLException ex) {

				}
			}
		}
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// processRequest(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}

	@Override
	public String getServletInfo() {
		return "Short description";
	}

}