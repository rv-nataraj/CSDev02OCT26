package CAMPS.Masters;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;

import CAMPS.Connect.DBConnect;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet(name = "designation_master_controller_7234", description = "designation_master_controller_7234", urlPatterns = { "/JSP/masters/DesignationMasterController.do" })

public class DesignationMasterController extends HttpServlet {

	private static final long serialVersionUID = 1L;

	protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, Exception {
		response.setContentType("text/html;charset=UTF-8");
		HttpSession session = request.getSession();
		DBConnect db = new DBConnect();
		PrintWriter out = response.getWriter();
		try {
			db.getConnection();

			if (request.getParameter("option") != null && request.getParameter("option").equalsIgnoreCase("loadDesignationData")) {
				// if (session.getAttribute("role_name").toString().contains("Placement Officer")) {
				DesignationMasterTransactions pt = new DesignationMasterTransactions();
				out.print(pt.loadDesignationData());
				// }
				// else {
				// out.print("Insufficient Role to Access this");
				// }
			}

			else if (request.getParameter("option") != null && request.getParameter("option").equalsIgnoreCase("loadAddDesignationData")) {
				DesignationMasterTransactions pt = new DesignationMasterTransactions();
				out.print(pt.loadAddDesignationData());
			}

			else if (request.getParameter("option") != null && request.getParameter("option").equalsIgnoreCase("addDesignationData")) {
				DesignationMasterTransactions pt = new DesignationMasterTransactions();
				out.print(pt.addDesignationData(request.getParameter("desigination"), request.getParameter("order_no"), session.getAttribute("ss_id").toString()));
			}

			else if (request.getParameter("option") != null && request.getParameter("option").equalsIgnoreCase("updateDesignationData")) {
				DesignationMasterTransactions pt = new DesignationMasterTransactions();
				out.print(pt.updateDesignationData(request.getParameter("md_id").replaceAll("[^0-9]", ""), request.getParameter("fname"), request.getParameter("fvalue"), session.getAttribute("ss_id").toString()));
			}

			else if (request.getParameter("option") != null && request.getParameter("option").equalsIgnoreCase("freezeDesignationData")) {
				DesignationMasterTransactions pt = new DesignationMasterTransactions();
				out.print(pt.freezeDesignationData(request.getParameter("md_id").replaceAll("[^0-9]", ""), session.getAttribute("ss_id").toString()));
			}

			else if (request.getParameter("option") != null && request.getParameter("option").equalsIgnoreCase("deleteDesignationData")) {
				DesignationMasterTransactions pt = new DesignationMasterTransactions();
				out.print(pt.deleteDesignationData(request.getParameter("md_id").replaceAll("[^0-9]", ""), session.getAttribute("ss_id").toString()));
			}

		} catch (Exception e) {
			out.print(e);
		} finally {
			db.closeConnection();
		}
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			// processRequest(request, response);
		} catch (Exception ex) {
			Logger.getLogger(DesignationMasterController.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			processRequest(request, response);
		} catch (Exception ex) {
			Logger.getLogger(DesignationMasterController.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	@Override
	public String getServletInfo() {
		return "Short description";
	}
}
