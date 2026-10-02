package CAMPS.Masters;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet(name = "branch_master_controller_1234", description = "branch_master_controller_1234", urlPatterns = { "/JSP/masters/BranchMasterController.do" })

public class BranchMasterController extends HttpServlet {

	private static final long serialVersionUID = 1L;

	protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, Exception {
		response.setContentType("text/html;charset=UTF-8");
		HttpSession session = request.getSession();
		PrintWriter out = response.getWriter();
		String option = request.getParameter("option");

		try {
			if (session.getAttribute("role_name").toString().contains("MS102")) {
				if ("loadBranchData".equalsIgnoreCase(option)) {
					BranchMasterTransactions pt = new BranchMasterTransactions();
					out.print(pt.loadBranchData());
				} else if ("loadAddBranchData".equalsIgnoreCase(option)) {
					BranchMasterTransactions pt = new BranchMasterTransactions();
					out.print(pt.loadAddBranchData());
				}
				else if ("addBranchData".equalsIgnoreCase(option)) {
					BranchMasterTransactions pt = new BranchMasterTransactions();
					out.print(pt.addBranchData(request.getParameter("branch_code"), request.getParameter("branch_name"), session.getAttribute("ss_id").toString()));
				}
				else if ("updateBranchData".equalsIgnoreCase(option)) {
					BranchMasterTransactions pt = new BranchMasterTransactions();
					out.print(pt.updateBranchData(request.getParameter("branch_id").replaceAll("[^0-9]", ""), request.getParameter("fname"), request.getParameter("fvalue"), session.getAttribute("ss_id").toString()));
				}
				else if ("freezeBranchData".equalsIgnoreCase(option)) {
					BranchMasterTransactions pt = new BranchMasterTransactions();
					out.print(pt.freezeBranchData(request.getParameter("branch_id").replaceAll("[^0-9]", ""), session.getAttribute("ss_id").toString()));
				}
				else if ("deleteBranchData".equalsIgnoreCase(option)) {
					BranchMasterTransactions pt = new BranchMasterTransactions();
					out.print(pt.deleteBranchData(request.getParameter("branch_id").replaceAll("[^0-9]", ""), session.getAttribute("ss_id").toString()));
				}
				else if ("loadBranchAmountData".equalsIgnoreCase(option)) {
					BranchMasterTransactions pt = new BranchMasterTransactions();
					out.print(pt.loadBranchAmountData());
				}
			} else {
				out.print("Insufficient Role");
			}
		} catch (Exception e) {
			out.print(e);
		}
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			// processRequest(request, response);
		} catch (Exception ex) {
			Logger.getLogger(BranchMasterController.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			processRequest(request, response);
		} catch (Exception ex) {
			Logger.getLogger(BranchMasterController.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	@Override
	public String getServletInfo() {
		return "Short description";
	}
}