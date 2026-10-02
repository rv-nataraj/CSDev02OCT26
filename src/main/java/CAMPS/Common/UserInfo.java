package CAMPS.Common;

import java.sql.SQLException;

import CAMPS.Connect.DBConnect;

public class UserInfo {
	public String userInfo(String ss_type, String ss_id) {
		String query = "";
		String data = "";
		int cc = 0;
		DBConnect db = new DBConnect();
		try {
			db.getConnection();
			if (ss_type.equalsIgnoreCase("1")) {
				query = "SELECT sm.staff_id `Staff ID`,TRIM(CONCAT(IFNULL(sm.first_name,''),' ',IFNULL(sm.middle_name,''),' ',IFNULL(sm.last_name,''),' ')) `Name`,ifnull(sm.dob,'-') `Date of Birth`,ifnull(sm.doj,'') `Date of Joining`,ifnull(`gender`,'-') `Gender`,IFNULL(`mobile_no`, '') `Mobile No`,IFNULL(`email_id`,' ') `Personal Email`, ifnull(sm.institute_email_id,'') `Official Email`, sm.working_status `Status`,ifnull(sm.dor,'-') `Date of Relieving`, ifnull(sai.alternate_id,'-') `Staff Alternate ID`, ifnull(sbi.biometric_id,'-') `Staff Biometric ID` FROM camps.staff_master sm left join camps.staff_alternate_id sai on sai.staff_id=sm.staff_id left join camps.staff_biometric_id sbi on sbi.staff_id=sm.staff_id  WHERE sm.staff_id='" + ss_id + "'";
			} else if (ss_type.equalsIgnoreCase("2")) {
				query = "SELECT sm.student_id `Student ID`,sm.emi_no `RegNo`,ifnull(sp.roll_no,'') `Roll No`,TRIM(CONCAT(IFNULL(sm.first_name,''),' ',IFNULL(sm.middle_name,''),' ',IFNULL(sm.last_name,''),' ')) `Name`,ifnull(sm.dob,'') `Date of Birth`,ifnull(`gender`,'') `Gender`,IFNULL(`student_mobile_no`, '') `Mobile No`,IFNULL(`student_email_id`,' ') `Personal Email`, IFNULL(sm.institute_email_id,'') `Official Email`, IFNULL(sm.scholar,'-') `Scholar`, concat(mp.programme_name,' ',mb.branch_name)  Programme, mbt.batch_name Batch FROM camps.student_master sm inner join camps.student_admission_master sam on sam.student_id=sm.student_id inner join camps.student_promotion sp on sp.sp_id=sam.sp_id and sp.student_id=sm.student_id inner join camps.master_branch mb on mb.branch_id=sam.branch_id inner join camps.master_programme mp on mp.programme_id=mb.programme_id inner join camps.master_batch mbt on mbt.batch_id=sam.batch_id left join curriculum.regulation_student_mapping srm on srm.student_id=sm.student_id WHERE sm.student_id='" + ss_id + "'";
			}
			db.read(query);

			if (db.rs.next()) {
				cc = db.rs.getMetaData().getColumnCount();
				for (int i = 1; i <= cc; i++) {
					data += "<div class=\"col-lg-6 col-md-6 col-sm-12 col-xs-12\" style=\"margin-bottom: 5px;\"><span width='15%' style='word-break:break-all;display: inline-grid;width: 40%;'>" + db.rs.getMetaData().getColumnLabel(i) + "</span><span width='5%' style='display: inline-grid;width: 5%;'>:</span><span width='35%' style='display: inline-grid;'><b>" + db.rs.getString(i) + "</b></span></div>";
					if (i <= cc) {
						i++;
						data += "<div class=\"col-lg-6 col-md-6 col-sm-12 col-xs-12\" style=\"margin-bottom: 5px;\"><span width='15%' style='word-break:break-all;display: inline-grid;width: 40%;'>" + db.rs.getMetaData().getColumnLabel(i) + "</span><span width='5%' style='display: inline-grid;width: 5%;'>:</span><span width='35%' style='display: inline-grid;'><b>" + db.rs.getString(i) + "</b></span></div>";

					}
				}
			}
			return data;

		} catch (Exception e) {
			return "Error - <b style=\"color:red\" title=\"" + e.toString() + "\">Something Went Wrong</b>" + "<!--" + query + data + "-->";
		} finally {
			try {
				db.closeConnection();
			} catch (SQLException ex) {

			}
		}
	}

}
