package CAMPS.Admin;

import java.io.IOException;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;

import CAMPS.Connect.DBConnect;
import jakarta.servlet.DispatcherType;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.annotation.WebInitParam;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebFilter(filterName = "authorizationFilter", urlPatterns = { "/JSP/*" }, dispatcherTypes = { DispatcherType.REQUEST, DispatcherType.FORWARD, DispatcherType.INCLUDE, DispatcherType.ERROR }, initParams = { @WebInitParam(name = "avoid-urls", value = "welcomePage.jsp") })

public class authorizationFilter implements Filter {
	private FilterConfig filterConfig = null;

	public authorizationFilter() {
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		DBConnect db = null;
		try {
			HttpServletRequest req = (HttpServletRequest) request;
			HttpServletResponse res = (HttpServletResponse) response;
			String url = req.getServletPath(); // ignores query string
			if (request.getDispatcherType() != DispatcherType.REQUEST) {
				chain.doFilter(req, res);
				return;
			}

			HttpSession session = req.getSession(false);
			if (session == null || session.getAttribute("ss_id") == null) {
				res.sendRedirect(req.getContextPath() + "/CommonJSP/signin.jsp");
				return;
			}

			if (!url.contains("welcomePage.jsp")) {
				String roles = null;
				if (session.getAttribute("roles") != null) {
					roles = session.getAttribute("roles").toString();
				}
				db = new DBConnect();
				db.getConnection();
				// db.read("SELECT *, GROUP_CONCAT(rim.ipaddr) ips FROM admin.role_resource_mapping rrm INNER JOIN admin.resource_master rm ON FIND_IN_SET(rm.resource_id,rrm.resources) AND rrm.role_id IN (" + roles + ") AND rm.link ='" + url.substring(1) + "' LEFT JOIN admin.resource_ip_mapping rim ON rim.resource_id=rm.resource_id and rim.rstatus=1 where rm.rstatus>0 GROUP BY rm.resource_id");
				db.read("SELECT rm.*, GROUP_CONCAT(rim.ipaddr) ips FROM admin.role_resource_map rrm INNER JOIN admin.resource_master rm ON rm.resource_id=rrm.resource_id AND rrm.role_id IN (" + roles + ") AND rm.link='" + url.substring(1) + "' LEFT JOIN admin.resource_ip_mapping rim ON rim.resource_id=rm.resource_id AND rim.rstatus=1 WHERE rrm.record_status>0 and rm.rstatus>0 GROUP BY rm.resource_id");

				if (db.rs.first()) {

					if (session.getAttribute("role_name").toString().contains("Student(Portal)") && db.rs.getString("student_status").equalsIgnoreCase("0")) {
						res.sendRedirect(req.getContextPath() + "/" + "JSP/Welcome/welcomePage.jsp?error_code=0");
						return;
					}
					session.setAttribute("resource_id", db.rs.getString("resource_id"));

					// **************resource_list for controller security
					LinkedList<Integer> resourceList = getResourceList(session);

					if (resourceList == null) {
						resourceList = new LinkedList<>();
					}

					Integer resourceId = Integer.parseInt(db.rs.getString("resource_id"));

					resourceList.remove(resourceId); // avoid duplicates
					resourceList.addLast(resourceId);

					while (resourceList.size() > 6) {
						resourceList.removeFirst();
					}

					session.setAttribute("resource_list", resourceList);
					// *******************************************************
					if (db.rs.getString("critical").equalsIgnoreCase("1") && "0".equalsIgnoreCase(session.getAttribute("OTPstatus").toString())) {
						res.sendRedirect(req.getContextPath() + "/" + "JSP/home/otp_verification.jsp?rid=" + session.getAttribute("resource_id").toString());
						return;
					}
					if (db.rs.getString("ips") != null && !(session.getAttribute("ss_id").toString().equalsIgnoreCase("1333"))) {
						if (!db.rs.getString("ips").contains(session.getAttribute("ipaddr").toString().split(",")[0])) {
							db.insert("INSERT INTO admin.authorization_log (`resource`,`path`,`accesstime`,`status`,`user_id`,`roles`) VALUES('page','" + url.substring(1) + "',NOW(),'NA-IP RES','" + session.getAttribute("user_id").toString() + "','" + session.getAttribute("roles") + "');");
							res.sendRedirect(req.getContextPath() + "/" + "JSP/Welcome/welcomePage.jsp?error_code=2");
							return;
						}
					}
					if (session.getAttribute("adminlogin").toString().equalsIgnoreCase("false")) {
						if (session.getAttribute("uType").toString().equalsIgnoreCase("staff"))
							db.insert("INSERT INTO camps.pinned_recents (staff_id,  resource_id,`count`,access_date) VALUES ('" + session.getAttribute("ss_id").toString() + "','" + db.rs.getString("resource_id") + "','1',NOW()) ON DUPLICATE KEY UPDATE `access_date`=NOW(), `count`=`count`+1");
						else
							db.insert("INSERT INTO camps.pinned_recents (student_id,resource_id,`count`,access_date) VALUES ('" + session.getAttribute("ss_id").toString() + "','" + db.rs.getString("resource_id") + "','1',NOW()) ON DUPLICATE KEY UPDATE `access_date`=NOW(), `count`=`count`+1");
					}
					db.insert("INSERT INTO admin.authorization_log (`resource`,`path`,`accesstime`,`status`,`user_id`,`roles`,student_id,staff_id) VALUES('page','" + url.substring(1) + "',NOW(),'Authorized','" + session.getAttribute("user_id").toString() + "','" + session.getAttribute("roles") + "'," + (session.getAttribute("uType").toString().equalsIgnoreCase("student") ? session.getAttribute("ss_id").toString() : "null") + "," + (session.getAttribute("uType").toString().equalsIgnoreCase("staff") ? session.getAttribute("ss_id").toString() : "null") + ");");
				} else {
					if (req.getHeader("Referer") == null) {
						db.insert("INSERT INTO admin.authorization_log (`resource`,`path`,`accesstime`,`status`,`user_id`,`roles`,student_id,staff_id) VALUES('page','" + url.substring(1) + "',NOW(),'Not  Authorized','" + session.getAttribute("user_id").toString() + "','" + session.getAttribute("roles") + "'," + (session.getAttribute("uType").toString().equalsIgnoreCase("student") ? session.getAttribute("ss_id").toString() : "null") + "," + (session.getAttribute("uType").toString().equalsIgnoreCase("staff") ? session.getAttribute("ss_id").toString() : "null") + ");");
						res.sendRedirect(req.getContextPath() + "/" + "JSP/Welcome/welcomePage.jsp?error_code=0");
						return;
					} else {

						db.read("SELECT rm.resource_id FROM admin.resource_master rm WHERE rm.rstatus>0 AND rm.link='" + url.substring(1) + "'");
						if (db.rs.next()) {
							db.insert("INSERT INTO admin.authorization_log (`resource`,`path`,`accesstime`,`status`,`user_id`,`roles`,student_id,staff_id) VALUES('page','" + url.substring(1) + "',NOW(),'unauth jsp access','" + session.getAttribute("user_id").toString() + "','" + session.getAttribute("roles") + "'," + (session.getAttribute("uType").toString().equalsIgnoreCase("student") ? session.getAttribute("ss_id").toString() : "null") + "," + (session.getAttribute("uType").toString().equalsIgnoreCase("staff") ? session.getAttribute("ss_id").toString() : "null") + ");");
							res.sendRedirect(req.getContextPath() + "/" + "JSP/Welcome/welcomePage.jsp?error_code=0");
							return;
						}

						db.read("SELECT cm.controller_id FROM admin.controller_master cm WHERE cm.record_status>0 AND cm.url='" + url.substring(1) + "'");
						if (db.rs.next()) {
							String inClause = getResourceList(session).stream().map(String::valueOf).collect(java.util.stream.Collectors.joining(","));
							db.read("SELECT * FROM admin.controller_resource_mapping crm WHERE crm.record_status>0 AND crm.controller_id='" + db.rs.getString("controller_id") + "' AND crm.resource_id in (" + inClause + ")");
							if (db.rs.next()) {
							} else {
								db.insert("INSERT INTO admin.authorization_log (`resource`,`path`,`accesstime`,`status`,`user_id`,`roles`,student_id,staff_id) VALUES('page','" + url.substring(1) + "',NOW(),'Unauthorized AJAX','" + session.getAttribute("user_id").toString() + "','" + session.getAttribute("roles") + "'," + (session.getAttribute("uType").toString().equalsIgnoreCase("student") ? session.getAttribute("ss_id").toString() : "null") + "," + (session.getAttribute("uType").toString().equalsIgnoreCase("staff") ? session.getAttribute("ss_id").toString() : "null") + ");");
								res.sendRedirect(req.getContextPath() + "/" + "JSP/Welcome/welcomePage.jsp?error_code=0");
								return;
							}
						}
						db.insert("INSERT INTO admin.authorization_log (`resource`,`path`,`accesstime`,`status`,`user_id`,`roles`,student_id,staff_id) VALUES('" + req.getHeader("Referer") + "','" + url.substring(1) + "',NOW(),'AJAX','" + session.getAttribute("user_id").toString() + "','" + session.getAttribute("roles") + "'," + (session.getAttribute("uType").toString().equalsIgnoreCase("student") ? session.getAttribute("ss_id").toString() : "null") + "," + (session.getAttribute("uType").toString().equalsIgnoreCase("staff") ? session.getAttribute("ss_id").toString() : "null") + "  );");
					}
				}
			}
			chain.doFilter(new XSSRequestWrapper((HttpServletRequest) request), response);
		} catch (Exception ex) {
			// ex.printStackTrace();
		} finally {
			try {
				if (db != null) {
					db.closeConnection();
				}
			} catch (SQLException ex) {
				Logger.getLogger(authorizationFilter.class.getName()).log(Level.SEVERE, null, ex);
			}
		}
	}

	public FilterConfig getFilterConfig() {
		return (this.filterConfig);
	}

	public void setFilterConfig(FilterConfig filterConfig) {
		this.filterConfig = filterConfig;
	}

	public void destroy() {
	}

	public void init(FilterConfig filterConfig) {
		this.filterConfig = filterConfig;
	}

	public void log(String msg) {
		filterConfig.getServletContext().log(msg);
	}

	@SuppressWarnings("unchecked")
	private LinkedList<Integer> getResourceList(HttpSession session) {

		Object obj = session.getAttribute("resource_list");

		if (obj == null) {
			LinkedList<Integer> list = new LinkedList<>();
			session.setAttribute("resource_list", list);
			return list;
		}

		return (LinkedList<Integer>) obj;
	}

}
