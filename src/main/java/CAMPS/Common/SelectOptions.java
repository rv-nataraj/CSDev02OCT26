package CAMPS.Common;

import java.sql.SQLException;

import CAMPS.Connect.DBConnect;

public class SelectOptions {

	public static String loadProgrammes() {
		String data = "";
		DBConnect db = new DBConnect();
		try {
			db.getConnection();
			data += "<option disabled value=\"\">--Select--</option>";
			db.read("SELECT bm.branch_id,CONCAT(IFNULL(mp.programme_code,''),' ',bm.branch_name) branch_name FROM camps.master_branch bm INNER JOIN camps.master_programme mp ON mp.programme_id=bm.programme_id  WHERE bm.status=1");
			while (db.rs.next()) {
				data += "<option   value=\"" + db.rs.getString("branch_id") + "\">" + db.rs.getString("branch_name") + "</option>";
			}
			return data;
		} catch (Exception e) {
			return "<b style=\"color:red\" title=\"" + e.toString() + "\">Something Went Wrong</b>";
		} finally {
			try {
				db.closeConnection();
			} catch (SQLException ex) {

			}
		}
	}

	public static String loadAY() {
		String data = "";
		DBConnect db = new DBConnect();
		try {
			db.getConnection();
			data += "<option selected value=\"\" disabled> -- AY -- </option>";

			String query = "SELECT CONCAT('<option value=\"',may.ay_id,'\" ',' >',CONCAT(may.acc_year),'</option>') val FROM   camps.`master_academic_year` may  ORDER BY may.ay_id DESC LIMIT 15";
			db.read(query);
			while (db.rs.next()) {
				data += db.rs.getString("val");
			}

			return data;
		} catch (Exception e) {
			return "<b style=\"color:red\" title=\"" + e.toString() + "\">Something Went Wrong</b>";
		} finally {
			try {
				db.closeConnection();
			} catch (SQLException ex) {

			}
		}
	}

	public static String loadTerm() {
		String data = "";
		DBConnect db = new DBConnect();
		try {
			db.getConnection();
			data += "<option value=\"\" selected disabled> -- Term -- </option>";

			String query = "SELECT CONCAT('<option data-ay_id=\"',may.ay_id,'\"  value=\"',mt.term_id,'\" ',' >',CONCAT(may.acc_year,' - ', mt.term_name),'</option>') val FROM camps.`master_term` mt INNER JOIN camps.`master_academic_year` may ON may.ay_id=mt.ay_id ORDER BY mt.term_id DESC LIMIT 12";
			db.read(query);
			while (db.rs.next()) {
				data += db.rs.getString("val");
			}

			return data;
		} catch (Exception e) {
			return "<b style=\"color:red\" title=\"" + e.toString() + "\">Something Went Wrong</b>";
		} finally {
			try {
				db.closeConnection();
			} catch (SQLException ex) {

			}
		}
	}

	public static String loadRegulation() {
		String data = "";
		DBConnect db = new DBConnect();
		try {
			db.getConnection();
			data += "<option value=\"\" disabled> -Regulation- </option>";

			String query = "SELECT CONCAT('<option  value=\"',rm.regulation_id,'\" ',' >',CONCAT(rm.regulation_name,' ', mp.`programme_name`,' ',mb.`branch_name`,' (',rm.`regulation_id`,') '),'</option>') val FROM curriculum.regulation_master rm INNER JOIN camps.`master_branch` mb ON mb.`branch_id`=rm.`branch_id` INNER JOIN camps.`master_programme` mp ON mp.`programme_id`=mb.`programme_id` where rm.live_status>0 and rm.status>0 ORDER BY mp.`programme_level` DESC ,mb.`branch_id`";
			db.read(query);
			while (db.rs.next()) {
				data += db.rs.getString("val");
			}

			return data;
		} catch (Exception e) {
			return "<b style=\"color:red\" title=\"" + e.toString() + "\">Something Went Wrong</b>";
		} finally {
			try {
				db.closeConnection();
			} catch (SQLException ex) {

			}
		}
	}

	public static String loadRegulation(String dept_id) {
		String data = "";
		DBConnect db = new DBConnect();
		try {
			db.getConnection();
			data += "<option value=\"\" disabled> -Regulation- </option>";

			String query = "SELECT CONCAT('<option  value=\"',rm.regulation_id,'\" ',' >',CONCAT(rm.regulation_name,' ', mp.`programme_name`,' ',mb.`branch_name`,' (',rm.`regulation_id`,') '),'</option>') val FROM curriculum.regulation_master rm INNER JOIN camps.`master_branch` mb ON mb.`branch_id`=rm.`branch_id` INNER JOIN camps.`master_programme` mp ON mp.`programme_id`=mb.`programme_id` and  mb.department_id=" + dept_id + " where rm.live_status>0 ORDER BY mp.`programme_level` DESC ,mb.`branch_id`";
			db.read(query);
			while (db.rs.next()) {
				data += db.rs.getString("val");
			}

			return data;
		} catch (Exception e) {
			return "<b style=\"color:red\" title=\"" + e.toString() + "\">Something Went Wrong</b>";
		} finally {
			try {
				db.closeConnection();
			} catch (SQLException ex) {

			}
		}
	}

	public static String loadRegulationOfBranch(String branch_id) {
		String data = "";
		DBConnect db = new DBConnect();
		try {
			db.getConnection();
			data += "<option value=\"\" disabled> -Regulation- </option>";

			String query = "SELECT CONCAT('<option  value=\"',rm.regulation_id,'\" ',' >',CONCAT(rm.regulation_name,' ', mp.`programme_name`,' ',mb.`branch_name`,' (',rm.`regulation_id`,') '),'</option>') val FROM curriculum.regulation_master rm INNER JOIN camps.`master_branch` mb ON mb.`branch_id`=rm.`branch_id` INNER JOIN camps.`master_programme` mp ON mp.`programme_id`=mb.`programme_id` and  mb.branch_id=" + branch_id + " where rm.live_status>0 ORDER BY mp.`programme_level` DESC ,mb.`branch_id`";
			db.read(query);
			while (db.rs.next()) {
				data += db.rs.getString("val");
			}

			return data;
		} catch (Exception e) {
			return "<b style=\"color:red\" title=\"" + e.toString() + "\">Something Went Wrong</b>";
		} finally {
			try {
				db.closeConnection();
			} catch (SQLException ex) {

			}
		}
	}

	public static String loadStudentStatusMultiSelect() {
		String data = "";
		DBConnect db = new DBConnect();
		try {
			db.getConnection();
			data += "<option value=\"\" disabled> -Student Status- </option>";

			String query = "SELECT a.status_name FROM camps.student_status_detail a ";
			db.read(query);
			while (db.rs.next()) {
				data += "<option " + ((db.rs.getString("status_name").equalsIgnoreCase("continuing") || db.rs.getString("status_name").equalsIgnoreCase("completed")) ? "selected" : "") + " value=\"" + db.rs.getString("status_name") + "\">" + db.rs.getString("status_name") + "</option>";
			}

			return data;
		} catch (Exception e) {
			return "<b style=\"color:red\" title=\"" + e.toString() + "\">Something Went Wrong</b>";
		} finally {
			try {
				db.closeConnection();
			} catch (SQLException ex) {

			}
		}
	}

	public static String loadDepartment() {
		String data = "", debuginfo = "";
		DBConnect db = new DBConnect();
		try {
			db.getConnection();
			data += "<option value=\"\" disabled>-- Department --</option>";

			String query = "SELECT GROUP_CONCAT('<option value=\"',md.`department_id`,'\">',md.`dept_name`,'(',md.`department_id`,')','</option>' SEPARATOR '') val FROM camps.`master_department` md  order by dept_order_no";
			debuginfo += "----" + query;
			db.read(query);
			while (db.rs.next()) {
				data += db.rs.getString("val");
			}
			return data;
		} catch (Exception e) {
			return "Error - <b style=\"color:red\" title=\"" + e.toString() + "\">Something Went Wrong</b>" + "<!---" + debuginfo + "--->";
		} finally {
			try {
				db.closeConnection();
			} catch (SQLException ex) {

			}
		}
	}

	public static String loadTeachingDepartment() {
		String data = "";
		DBConnect db = new DBConnect();
		try {
			db.getConnection();
			data += "<option value=\"\" disabled> -Dept Category- </option>";

			String query = "SELECT CONCAT('<option value=\"',md.department_id,'\">',CONCAT(md.`dept_code`,' - ',md.dept_name),'</option>') val FROM camps.`master_department` md WHERE md.sc_id=1 AND md.status>0 ORDER BY md.`dept_order_no`";

			db.read(query);
			while (db.rs.next()) {
				data += db.rs.getString("val");

			}

			return data;
		} catch (Exception e) {
			return "<b style=\"color:red\" title=\"" + e.toString() + "\">Something Went Wrong</b>";
		} finally {
			try {
				db.closeConnection();
			} catch (SQLException ex) {

			}
		}

	}

	public static String loadTeachingDepartment(String dept_filter) {
		String data = "";
		DBConnect db = new DBConnect();
		try {
			db.getConnection();
			data += "<option value=\"\" disabled selected>-- Dept Category --</option>";

			String query = "SELECT CONCAT('<option value=\"',md.department_id,'\">',CONCAT(md.`dept_code`,' - ',md.dept_name),'</option>') val FROM camps.`master_department` md WHERE md.sc_id=1 " + dept_filter + " AND md.status>0 ORDER BY md.`dept_order_no`";
			db.read(query);
			while (db.rs.next()) {
				data += db.rs.getString("val");

			}

			return data;
		} catch (Exception e) {
			return "<b style=\"color:red\" title=\"" + e.toString() + "\">Something Went Wrong</b>";
		} finally {
			try {
				db.closeConnection();
			} catch (SQLException ex) {

			}
		}

	}

	public static String loadStaffCategory() {
		String data = "";
		DBConnect db = new DBConnect();
		try {
			db.getConnection();
			data += "<option value=\"\" disabled> -Dept Category- </option>";
			String query = "SELECT CONCAT('<option ',if(sc.sc_id=1,'selected',''),' value=\"',sc.sc_id,'\">',sc.category_name,'</option>') val FROM camps.`master_staff_category` sc WHERE sc.`status`>0 ORDER BY sc.`sc_id`";
			db.read(query);
			while (db.rs.next()) {
				data += db.rs.getString("val");

			}
			return data;
		} catch (Exception e) {
			return "<b style=\"color:red\" title=\"" + e.toString() + "\">Something Went Wrong</b>";
		} finally {
			try {
				db.closeConnection();
			} catch (SQLException ex) {

			}
		}

	}

	public static String loadMaxMark() {
		String data = "";
		DBConnect db = new DBConnect();
		try {
			db.getConnection();
			data += "<option disabled value=\"\">--- Max Mark ---</option>";
			String query = "SELECT cemm.mark,cemm.default  FROM curriculum.`coe_examiners_max_mark` cemm WHERE cemm.`record_status`>0 ORDER BY cemm.order_no";
			db.read(query);
			while (db.rs.next()) {
				data += ("<option " + (db.rs.getString("default").equalsIgnoreCase("1") ? "selected" : "") + " value='" + db.rs.getString("mark") + "'>" + db.rs.getString("mark") + "</option>");

			}
			return data;
		} catch (Exception e) {
			return "<b style=\"color:red\" title=\"" + e.toString() + "\">Something Went Wrong</b>";
		} finally {
			try {
				db.closeConnection();
			} catch (SQLException ex) {

			}
		}
	}

	public static String loadLibCategory() {
		String data = "";
		DBConnect db = new DBConnect();
		try {
			db.getConnection();
			data += "<option value=\"\" disabled> -Lib Category- </option>";
			String query = "SELECT cm.cat_id,cm.cat_name FROM library.category_master cm WHERE cm.status>0 ORDER BY cm.cat_name";
			db.read(query);
			while (db.rs.next()) {
				data += "<option value=\"" + db.rs.getString("cat_id") + "\">" + db.rs.getString("cat_name") + "</option>";

			}
			return data;
		} catch (Exception e) {
			return "<b style=\"color:red\" title=\"" + e.toString() + "\">Something Went Wrong</b>";
		} finally {
			try {
				db.closeConnection();
			} catch (SQLException ex) {

			}
		}

	}

	public static String loadTeachingStaff() {
		String data = "";
		DBConnect db = new DBConnect();
		try {
			db.getConnection();
			data += "<option value=\"\" disabled> -Faculty Name- </option>";
			String query = "SELECT  CONCAT('<option value=\"',sm.`staff_id`,'\" >',CONCAT(sm.`staff_id`,'-',TRIM(CONCAT(sm.`legend`,' ',IFNULL(sm.last_name,''),' ',IFNULL(sm.first_name,''),' ',IFNULL(sm.middle_name,''),' ',' (',IFNULL(md.`dept_code`,''),')'))),'</option>') val  FROM camps.`staff_master` sm  INNER JOIN camps.`staff_promotion` sp ON sp.staff_id=sm.staff_id AND sp.status=2 AND sm.sc_id=1 INNER JOIN camps.`master_department` md ON md.`department_id`=sp.department_id ORDER BY md.`dept_order_no`";
			db.read(query);
			while (db.rs.next()) {
				data += db.rs.getString("val");
			}
			return data;
		} catch (Exception e) {
			return "<b style=\"color:red\" title=\"" + e.toString() + "\">Something Went Wrong</b>";
		} finally {
			try {
				db.closeConnection();
			} catch (SQLException ex) {

			}
		}

	}

	public static String loadProgrammeLevel() {
		String data = "";
		DBConnect db = new DBConnect();
		try {
			db.getConnection();
			data += "<option value=\"\" disabled> -Programme Level- </option>";
			String query = "SELECT DISTINCT(a.programme_level) degree_level FROM camps.master_programme a";
			db.read(query);
			while (db.rs.next()) {
				data += "<option value=\"" + db.rs.getString("degree_level") + "\">" + db.rs.getString("degree_level") + "</option>";
			}
			return data;
		} catch (Exception e) {
			return "<b style=\"color:red\" title=\"" + e.toString() + "\">Something Went Wrong</b>";
		} finally {
			try {
				db.closeConnection();
			} catch (SQLException ex) {

			}
		}

	}

	public static String getCurrentDate() {
		String data = "";
		DBConnect db = new DBConnect();
		try {
			db.getConnection();

			String query = "SELECT DATE(NOW()) cdate";
			db.read(query);
			if (db.rs.next()) {
				data += db.rs.getString("cdate");

			}
			return data;
		} catch (Exception e) {
			return "<b style=\"color:red\" title=\"" + e.toString() + "\">Something Went Wrong</b>";
		} finally {
			try {
				db.closeConnection();
			} catch (SQLException ex) {

			}
		}

	}

	public static String getPreviousDate(int year) {
		String data = "";
		DBConnect db = new DBConnect();
		try {
			db.getConnection();

			String query = "SELECT DATE(NOW())-INTERVAL " + year + " YEAR fdate";
			db.read(query);
			if (db.rs.next()) {
				data += db.rs.getString("fdate");

			}
			return data;
		} catch (Exception e) {
			return "<b style=\"color:red\" title=\"" + e.toString() + "\">Something Went Wrong</b>";
		} finally {
			try {
				db.closeConnection();
			} catch (SQLException ex) {

			}
		}

	}

	public static String loadMarkType() {
		String data = "";
		DBConnect db = new DBConnect();
		try {
			db.getConnection();
			data += "<option disabled value=\"\">--- Select ---</option>";
			String query = "SELECT mt.mt_id,mt.mark_type FROM curriculum.coe_mark_type mt WHERE mt.status>0";
			db.read(query);
			while (db.rs.next()) {
				data += "<option value=\"" + db.rs.getString("mt_id") + "\">" + db.rs.getString("mark_type") + "</option>";

			}
			return data;
		} catch (Exception e) {
			return "<b style=\"color:red\" title=\"" + e.toString() + "\">Something Went Wrong</b>";
		} finally {
			try {
				db.closeConnection();
			} catch (SQLException ex) {

			}
		}

	}

	public static String loadExamGroup() {
		String data = "";
		DBConnect db = new DBConnect();
		try {
			db.getConnection();
			data += "<option disabled selected value=\"\">-- Select Exam Group --</option>";
			String query = "SELECT CONCAT('<option data-term_id=\"',ceg.term_id,'\" value=\"',ceg.`exam_group_id`,'\" ',' >',IFNULL(ceg.`exam_group_name`,''),'</option>') val FROM curriculum.`coe_exam_group` ceg  WHERE ceg.record_status>0  ORDER BY ceg.`exam_group_id` DESC";
			db.read(query);
			while (db.rs.next()) {
				data += db.rs.getString("val");
			}
			return data;
		} catch (Exception e) {
			return "<b style=\"color:red\" title=\"" + e.toString() + "\">Something Went Wrong</b>";
		} finally {
			try {
				db.closeConnection();
			} catch (SQLException ex) {

			}
		}

	}

	public static String loadCurrentExamGroup() {
		String data = "";
		DBConnect db = new DBConnect();
		try {
			db.getConnection();
			data += "<option disabled selected value=\"\">-- Select Exam Group --</option>";
			String query = "SELECT CONCAT('<option  value=\"',ceg.`exam_group_id`,'\" ',' >',IFNULL(ceg.`exam_group_name`,''),'</option>') val FROM curriculum.`coe_exam_group` ceg  WHERE ceg.record_status>0  ORDER BY ceg.`term_id` DESC  limit 1";
			db.read(query);
			while (db.rs.next()) {
				data += db.rs.getString("val");
			}
			return data;
		} catch (Exception e) {
			return "<b style=\"color:red\" title=\"" + e.toString() + "\">Something Went Wrong</b>";
		} finally {
			try {
				db.closeConnection();
			} catch (SQLException ex) {

			}
		}

	}

	public static String loadMarkTableId() {
		String data = "";
		DBConnect db = new DBConnect();
		try {
			db.getConnection();
			data += "<option disabled selected value=\"\">-- Select --</option>";
			String query = "SELECT ctm.`mark_table_id`, ctm.`regulation_name` FROM curriculum.`coe_table_master` ctm WHERE ctm.`record_status`>0 ORDER BY ctm.`ctm_id` DESC";
			db.read(query);
			while (db.rs.next()) {
				data += "<option value='" + db.rs.getString("mark_table_id") + "'>" + db.rs.getString("regulation_name") + "</option>";
			}
			return data;
		} catch (Exception e) {
			return "<b style=\"color:red\" title=\"" + e.toString() + "\">Something Went Wrong</b>";
		} finally {
			try {
				db.closeConnection();
			} catch (SQLException ex) {

			}
		}

	}

	public static String loadCIAExam() {
		String data = "";
		DBConnect db = new DBConnect();
		try {
			db.getConnection();
			data += "<option disabled value=\"\">--- Select Exam ---</option>";
			String query = "SELECT mt.ay_id,IFNULL(cem.cia_exam_name,'') cia_exam_name,cem.cem_id FROM studacad.cia_exam_master cem INNER JOIN camps.master_term mt on mt.term_id=cem.term_id WHERE cem.record_status>0";
			db.read(query);
			while (db.rs.next()) {
				data += "<option data-ay_id=\"" + db.rs.getString("ay_id") + "\" value=\"" + db.rs.getString("cem_id") + "\">" + db.rs.getString("cia_exam_name") + "</option>";
			}
			return data;
		} catch (Exception e) {
			return "<b style=\"color:red\" title=\"" + e.toString() + "\">Something Went Wrong</b>";
		} finally {
			try {
				db.closeConnection();
			} catch (SQLException ex) {

			}
		}
	}

	public static String loadRecruitment() {
		String data = "";
		DBConnect db = new DBConnect();
		try {
			db.getConnection();
			data += "<option value=\"\" disabled> -Recruitment- </option>";
			String query = "SELECT CONCAT('<option  value=\"',rm.`recruitment_id`,'\" ',' >',CONCAT(rm.`recruitment_name`),'</option>') val FROM placement.`recruitment_master` rm where rm.record_status>0 ORDER BY rm.`recruitment_id` DESC";
			db.read(query);
			while (db.rs.next()) {
				data += db.rs.getString("val");
			}
			return data;
		} catch (Exception e) {
			return "<b style=\"color:red\" title=\"" + e.toString() + "\">Something Went Wrong</b>";
		} finally {
			try {
				db.closeConnection();
			} catch (SQLException ex) {
			}
		}
	}

	public static String loadBatch() {
		String data = "";
		DBConnect db = new DBConnect();
		try {
			db.getConnection();
			data += "<option value=\"\" disabled> -Batch- </option>";

			String query = "SELECT CONCAT('<option value=\"',mb.batch_id,'\" ',' >',CONCAT(mb.batch_name),'</option>') val FROM   camps.master_batch mb  ORDER BY mb.batch_id DESC";
			db.read(query);
			while (db.rs.next()) {
				data += db.rs.getString("val");
			}

			return data;
		} catch (Exception e) {
			return "<b style=\"color:red\" title=\"" + e.toString() + "\">Something Went Wrong</b>";
		} finally {
			try {
				db.closeConnection();
			} catch (SQLException ex) {

			}
		}
	}

	public static String loadBatch2() {
		String data = "";
		DBConnect db = new DBConnect();
		try {
			db.getConnection();
			data += "<option value=\"\" disabled> -Batch- </option>";

			String query = "SELECT CONCAT('<option value=\"',mb.batch_id,'\" ',' >',CONCAT(mb.batch_name,' ','Passed Out'),'</option>') val FROM   camps.master_batch mb  ORDER BY mb.batch_id DESC";
			db.read(query);
			while (db.rs.next()) {
				data += db.rs.getString("val");
			}

			return data;
		} catch (Exception e) {
			return "<b style=\"color:red\" title=\"" + e.toString() + "\">Something Went Wrong</b>";
		} finally {
			try {
				db.closeConnection();
			} catch (SQLException ex) {

			}
		}
	}

	public static String loadboard() {
		String data = "";
		DBConnect db = new DBConnect();
		try {
			db.getConnection();
			data += "<option value=\"\" disabled>--Select Board--</option>";
			String query = "SELECT CONCAT('<option value=\"',cbm.cbm_id,'\" ',' >',cbm.board_name,'</option>') val FROM   curriculum.coe_board_master cbm WHERE cbm.status>0";
			db.read(query);
			while (db.rs.next()) {
				data += db.rs.getString("val");
			}

			return data;
		} catch (Exception e) {
			return "<b style=\"color:red\" title=\"" + e.toString() + "\">Something Went Wrong</b>";
		} finally {
			try {
				db.closeConnection();
			} catch (SQLException ex) {

			}
		}
	}

}