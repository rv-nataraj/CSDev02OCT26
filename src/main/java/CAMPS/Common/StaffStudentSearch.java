package CAMPS.Common;

import java.sql.SQLException;
import CAMPS.Connect.DBConnect;

public class StaffStudentSearch {

	public String loadStaffSearchResults(String search_string, String ss_id) { 
		String data = "", query = ""; int i=1;
		DBConnect db = new DBConnect();
		try {
			db.getConnection();
			query="SELECT 'Staff' ss_type, sm.staff_id AS ss_id,CONCAT('<a href=\"#\" data-dismiss=\"modal\" onclick=\"loadDivisions(',sm.staff_id,');\">','&#x261D;',TRIM(CONCAT(IFNULL(sm.first_name,''),' ',IFNULL(sm.middle_name,''),' ',IFNULL(sm.last_name,''),' ')),'</a>') ss_name,'Staff',md.dept_name FROM camps.staff_master sm INNER JOIN camps.master_department md ON sm.department_id=md.department_id "+(ss_id.equalsIgnoreCase("1")?"":" and sm.staff_id not in (1) ")+" left join camps.staff_alternate_id sai on sai.staff_id=sm.staff_id  WHERE sm.first_name LIKE '%"+search_string+"%' OR sm.staff_id like '%"+search_string+"%' OR sai.alternate_id like '%"+search_string+"%'  LIMIT 50";
			db.read(query);	
			data = "<div class=\"table-responsive text-nowrap\"><table class='table table-bordered table-striped' width='100%'>";
			data += "<thead><tr><th>S.No</th><th>Type</th><th>ID</th><th>Name</th><th>Dept / Programme</th></tr></thead>";
			while (db.rs.next()) {
				data += "<tr>";
				data += "<td>"+ i++ +"</td>";
				data += "<td>"+ db.rs.getString("ss_type") +"</td>";
				data += "<td>"+db.rs.getString("ss_id")+"</td>";
				data += "<td>"+db.rs.getString("ss_name")+"</td>";
				data += "<td>"+db.rs.getString("dept_name")+"</td>";
				data += "</tr>";
			}
			data += "</table></div>";
			if (i==1) {
				data = "No Matching Records Found";
			}
			return data;

		} catch (Exception e) {
			return "<b style=\"color:red\" title=\""+e.toString()+"\">Something Went Wrong</b>";
		} finally {
			try {
				db.closeConnection();
			} catch (SQLException ex) {

			}
		}
	}
	
	public String loadStudentSearchResults(String search_string) { 
		String data = "", query = ""; int i=1;
		DBConnect db = new DBConnect();
		try {
			db.getConnection();
			query="SELECT ifnull(mbt.batch_name,'--') batch_name, mppd.period_name, 'Student' ss_type, sm.student_id AS ss_id,CONCAT('<a href=\"#\" data-dismiss=\"modal\" onclick=\"loadDivisions(',sm.student_id,');\">&#x261D;',TRIM(CONCAT(IFNULL(sm.first_name,''),' ',IFNULL(sm.middle_name,''),' ',IFNULL(sm.last_name,''),' ')),'</a>') ss_name,'Student',CONCAT(mp.programme_name,' ',mb.branch_name) dept_name FROM camps.student_master sm INNER JOIN camps.student_admission_master sam ON sam.student_id=sm.student_id INNER JOIN camps.student_promotion sp ON sp.sp_id=sam.sp_id left JOIN camps.`master_batch` mbt ON mbt.batch_id=sam.batch_id INNER JOIN camps.`master_programme_period_det` mppd ON mppd.prog_period_id=sp.prog_period_id  INNER JOIN camps.`master_branch` mb ON mb.branch_id=sam.branch_id INNER JOIN camps.`master_programme` mp ON mp.programme_id=mb.programme_id WHERE sm.first_name LIKE '%"+search_string+"%' OR sm.student_id='"+search_string+"' OR sm.emi_no like '%"+search_string+"%' or sp.roll_no like '%"+search_string+"%' LIMIT 60";
			db.read(query);	
			data = "<div class=\"table-responsive text-nowrap\"><table class='table table-bordered table-striped' width='100%'>";
			data += "<thead><tr><th>S.No</th><th>Type</th><th>ID</th><th>Name</th><th>Dept / Programme</th><th>Batch</th></tr></thead>";
			while (db.rs.next()) {
				data += "<tr>";
				data += "<td>"+ i++ +"</td>";
				data += "<td>"+ db.rs.getString("ss_type") +"</td>";
				data += "<td>"+db.rs.getString("ss_id")+"</td>";
				data += "<td>"+db.rs.getString("ss_name")+"</td>";
				data += "<td>"+db.rs.getString("dept_name")+" ("+db.rs.getString("period_name")+")"+"</td>";
				data += "<td>"+db.rs.getString("batch_name")+"</td>";
				data += "</tr>";
			}
			data += "</table></div>"+"<!--"+query+"-->";
			if (i==1) {
				data = "No Matching Records Found"+"<!--"+query+"-->";
			}
			return data;

		} catch (Exception e) {
			return "<b style=\"color:red\" title=\""+e.toString()+"\">Something Went Wrong</b>"+"<!--"+query+"-->";
		} finally {
			try {
				db.closeConnection();
			} catch (SQLException ex) {

			}
		}
		
	}

	
	public String loadStaffStudentSearchResults(String search_string) { 
		String data = "", query = ""; int i=1;
		DBConnect db = new DBConnect();
		try {
			db.getConnection();
			query="SELECT 'Staff' ss_type, sm.staff_id AS ss_id,CONCAT('<a href=\"#\" data-dismiss=\"modal\" onclick=\"loadDivisions(1,',sm.staff_id,');\">&#x261D;',TRIM(CONCAT(IFNULL(sm.first_name,''),' ',IFNULL(sm.middle_name,''),' ',IFNULL(sm.last_name,''),' ')),'</a>') ss_name,'Staff',md.dept_name FROM camps.staff_master sm INNER JOIN camps.master_department md ON sm.department_id=md.department_id  WHERE sm.first_name LIKE '%"+search_string+"%' OR sm.staff_id='"+search_string+"'  UNION SELECT 'Student' ss_type, sm.student_id AS ss_id,CONCAT('<a href=\"#\" data-dismiss=\"modal\" onclick=\"loadDivisions(2,',sm.student_id,');\">',TRIM(CONCAT(IFNULL(sm.first_name,''),' ',IFNULL(sm.middle_name,''),' ',IFNULL(sm.last_name,''),' ')),'</a>') ss_name,'Student',CONCAT(mp.programme_name,' ',mb.branch_name) FROM camps.student_master sm INNER JOIN camps.student_admission_master sam ON sam.student_id=sm.student_id INNER JOIN camps.`master_branch` mb ON mb.branch_id=sam.branch_id INNER JOIN camps.`master_programme` mp ON mp.programme_id=mb.programme_id WHERE sm.first_name LIKE '%"+search_string+"%' OR sm.student_id='"+search_string+"' LIMIT 50";
			db.read(query);	
			data = "<div class=\"table-responsive text-nowrap\"><table class='table table-bordered table-striped' width='100%'>";
			data += "<thead><tr><th>S.No</th><th>Type</th><th>ID</th><th>Name</th><th>Dept / Programme</th></tr></thead>";
			while (db.rs.next()) {
				data += "<tr>";
				data += "<td>"+ i++ +"</td>";
				data += "<td>"+ db.rs.getString("ss_type") +"</td>";
				data += "<td>"+db.rs.getString("ss_id")+"</td>";
				data += "<td>"+db.rs.getString("ss_name")+"</td>";
				data += "<td>"+db.rs.getString("dept_name")+"</td>";
				data += "</tr>";
			}
			data += "</table></div>";
			if (i==1) {
				data = "No Matching Records Found";
			}
			return data;
			
		} catch (Exception e) {
			return "<b style=\"color:red\" title=\""+e.toString()+"\">Something Went Wrong</b>";
		} finally {
			try {
				db.closeConnection();
			} catch (SQLException ex) {

			}
		}	
	}
}
