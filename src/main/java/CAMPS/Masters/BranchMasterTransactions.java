package CAMPS.Masters;

import java.sql.SQLException;
import CAMPS.Connect.DBConnect;
import jakarta.servlet.http.HttpSession;

public class BranchMasterTransactions { 

	public String loadBranchData() { 
		String data = "", query = ""; String fix_id="";  int i=1;
		DBConnect db = new DBConnect();
		try {
			db.getConnection();
			db.read("SELECT mp.`programme_id`,IFNULL(mp.`programme_name`,'') programme_name FROM camps.master_programme mp WHERE mp.`status`>0");
			String opt = "<option disabled value=\"\">--- Programme ---</option>";
            while (db.rs.next()) {
            	opt += "<option value='" + db.rs.getString("programme_id") + "'>" + db.rs.getString("programme_name") + "</option>";
            }
            db.read("SELECT mpp.`prog_pattern_id`, IFNULL(mpp.`prog_pattern_name`,'') prog_pattern_name FROM camps.`master_programme_pattern` mpp WHERE mpp.`status`>0");
			String opt1 = "<option disabled value=\"\">--- Programme Pattern ---</option>";
            while (db.rs.next()) {
            	opt1 += "<option value='" + db.rs.getString("prog_pattern_id") + "'>" + db.rs.getString("prog_pattern_name") + "</option>";
            }
            db.read("SELECT md.`department_id`, CONCAT(IFNULL(md.`dept_code`,''),' - ',IFNULL(md.`dept_name`,'')) dept_name FROM camps.master_department md WHERE md.`status`>0 order by md.dept_name");
			String opt2 = "<option disabled value=\"\">--- Department ---</option>";
            while (db.rs.next()) {
            	opt2 += "<option value='" + db.rs.getString("department_id") + "'>" + db.rs.getString("dept_name") + "</option>";
            }
            String opt3 = "<option selected disabled value=\"\">--- Medium ---</option>";
        	opt3 += "<option value=\"Tamil\">Tamil</option>";
        	opt3 += "<option value=\"English\">English</option>";
        	
            query="SELECT ifnull(mb.order_no,'') order_no, mb.branch_id, IFNULL(mb.branch_code,'') branch_code, IFNULL(mb.`branch_name`,'') branch_name, IFNULL(mb.`programme_id`,'') programme_id, IFNULL(mb.`prog_pattern_id`,'') prog_pattern_id, IFNULL(mb.`department_id`,'') department_id, IFNULL(mb.`moi`,'') moi,IFNULL(mb.`email_code`,'') email_code, IFNULL(mb.`status`,'') status, IFNULL(mp.`programme_name`,'') programme_name, IFNULL(mpp.`prog_pattern_name`,'') prog_pattern_name, IFNULL(md.`dept_name`,'') dept_name FROM camps.`master_branch` mb LEFT JOIN camps.`master_programme` mp ON mp.`programme_id`=mb.`programme_id` AND mp.`status`>0 LEFT JOIN camps.`master_department` md ON md.`department_id`=mb.`department_id` AND md.`status`>0 LEFT JOIN camps.`master_programme_pattern` mpp ON mpp.`prog_pattern_id`=mb.`prog_pattern_id` AND mpp.`status`>0 WHERE mb.`status`>0 ORDER BY ifnull(mb.order_no,9999)";
            db.read(query);
            data = "<div class='table-responsive text-wrap' ><div><table class='tbl_new' style=\"width:100%\" border=\"1\">";
			data+="<tr><th style=\"width:20px\">S.NO</th><th style=\"width:90px\">Branch Code</th><th>Branch Name</th><th style=\"width:80px\">Programme</th><th style=\"width:60px\">Pattern</th><th style=\"width:300px\">Department</th><th style=\"width:60px\">Medium</th><th style=\"width:60px\">Email Code</th><th style=\"width:60px\">Order No</th><th style=\"width:70px\">Actions</th></tr>";

        	while(db.rs.next()) {
        		if (db.rs.getString("status").equalsIgnoreCase("1")) {
					data+="<tr>";
					data+="<td data-column=\"S.No\">"+ i++ +"</td>";
					data+="<td title=\"Branch Code\" 	data-column=\"Branch Code\" >"+	"<input maxlength=\"10\" type=\"text\" 	id=\"branch_code"+db.rs.getString("branch_id")+"\" 		style=\"width:100%\" 	value=\""+db.rs.getString("branch_code")+"\" 	onchange=\"updateBranchData('branch_code',"+db.rs.getString("branch_id")+");	this.style.background = '#f4e88e'\" >"		+"</td>";
					data+="<td title=\"Branch Name\" 	data-column=\"Branch Name\" >"+	"<input maxlength=\"100\" type=\"text\" 	id=\"branch_name"+db.rs.getString("branch_id")+"\" 		style=\"width:100%\" 	value=\""+db.rs.getString("branch_name")+"\" 	onchange=\"updateBranchData('branch_name',"+db.rs.getString("branch_id")+");	this.style.background = '#f4e88e'\" >"		+"</td>";
					data+="<td title=\"Programme\" 		data-column=\"Programme\">" + 	"<select 				id=\"programme_id"+db.rs.getString("branch_id")+"\" 	style=\"width:100%\" 													onchange=\"updateBranchData('programme_id',"+db.rs.getString("branch_id")+");	this.style.background = '#f4e88e'\" >"+ opt	+"</select> <script> $('#programme_id" + db.rs.getString("branch_id") + "').val('" + db.rs.getString("programme_id") + "');</script> <br></td>";
					data+="<td title=\"Pattern\"   		data-column=\"Pattern\">" + 	"<select 				id=\"prog_pattern_id"+db.rs.getString("branch_id")+"\" 	style=\"width:100%\" 													onchange=\"updateBranchData('prog_pattern_id',"+db.rs.getString("branch_id")+");this.style.background = '#f4e88e'\" >"+ opt1+"</select> <script> $('#prog_pattern_id" + db.rs.getString("branch_id") + "').val('" + db.rs.getString("prog_pattern_id") + "');</script> <br></td>";
					data+="<td title=\"Department\" 	data-column=\"Department\">" + 	"<select 				id=\"department_id"+db.rs.getString("branch_id")+"\" 	style=\"width:100%\" 													onchange=\"updateBranchData('department_id',"+db.rs.getString("branch_id")+");	this.style.background = '#f4e88e'\" >"+ opt2+"</select> <script> $('#department_id" + db.rs.getString("branch_id") + "').val('" + db.rs.getString("department_id") + "');</script> <br></td>";
					data+="<td title=\"Medium\"   		data-column=\"Medium\">" + 		"<select 				id=\"moi"+db.rs.getString("branch_id")+"\" 				style=\"width:100%\" 													onchange=\"updateBranchData('moi',"+db.rs.getString("branch_id")+");			this.style.background = '#f4e88e'\" >"+ opt3+"</select> <script> $('#moi" + db.rs.getString("branch_id") + "').val('" + db.rs.getString("moi") + "');</script> <br></td>";
					data+="<td title=\"Email Code\" 	data-column=\"Email Code\">"+ 	"<input maxlength=\"5\" type=\"text\" 	id=\"email_code"+db.rs.getString("branch_id")+"\" 		style=\"width:100%\" 	value=\""+db.rs.getString("email_code")+"\" 	onchange=\"updateBranchData('email_code',"+db.rs.getString("branch_id")+");		this.style.background = '#f4e88e'\" >" 		+"</td>";
					data+="<td title=\"Order No\" 	data-column=\"Order No\">"+ 	"<input maxlength=\"5\" type=\"number\" 	id=\"order_no"+db.rs.getString("branch_id")+"\" 		style=\"width:100%\" 	value=\""+db.rs.getString("order_no")+"\" 	onchange=\"updateBranchData('order_no',"+db.rs.getString("branch_id")+");		this.style.background = '#f4e88e'\" >" 		+"</td>";
					data+="<td data-column=\"Actions\" >" + "<button style=\"padding:3px 10px\" class=\"btn btn-danger waves-effect\" onclick=\"deleteBranchData("+db.rs.getString("branch_id")+")\">X</button>";
					data+="<button style=\"padding:3px 10px\" class=\"btn btn-success waves-effect\" onclick=\"freezeBranchData("+ db.rs.getString("branch_id")+")\">Fz</button>" + "</td>";
					data+="</tr>"; 
				}
				else if (db.rs.getString("status").equalsIgnoreCase("2")) {
					data+="<tr>";
					data+="<td  data-column=\"S.No\">"+ i++ +"</td>";
					data+="<td title=\"Branch Code\" 	data-column=\"Branch Code\" >"+	"<input readonly type=\"text\" 	id=\"branch_code"+db.rs.getString("branch_id")+"\" 		style=\"width:100%;border:none;background:transparent\" value=\""+db.rs.getString("branch_code")+"\" 	 	 ></td>";
					data+="<td title=\"Branch Name\" 	data-column=\"Branch Name\" >"+	"<input readonly type=\"text\" 	id=\"branch_name"+db.rs.getString("branch_id")+"\" 		style=\"width:100%;border:none;background:transparent\" value=\""+db.rs.getString("branch_name")+"\" 	     ></td>";
					data+="<td title=\"Programme\" 		data-column=\"Programme\">" + 	"<input readonly type=\"text\"	id=\"programme_id"+db.rs.getString("branch_id")+"\" 	style=\"width:100%;border:none;background:transparent\" value=\""+db.rs.getString("programme_name")+"\"		 ></td>";
					data+="<td title=\"Pattern\"   		data-column=\"Pattern\">" + 	"<input readonly type=\"text\"	id=\"prog_pattern_id"+db.rs.getString("branch_id")+"\" 	style=\"width:100%;border:none;background:transparent\" value=\""+db.rs.getString("prog_pattern_name")+"\"	 ></td>";
					data+="<td title=\"Department\" 	data-column=\"Department\">" + 	"<input readonly type=\"text\"	id=\"department_id"+db.rs.getString("branch_id")+"\" 	style=\"width:100%;border:none;background:transparent\" value=\""+db.rs.getString("dept_name")+"\"			 ></td>";
					data+="<td title=\"Medium\"   		data-column=\"Medium\">" + 		"<input readonly type=\"text\"	id=\"moi"+db.rs.getString("branch_id")+"\" 				style=\"width:100%;border:none;background:transparent\" value=\""+db.rs.getString("moi")+"\"				 ></td>";
					data+="<td title=\"Email Code\" 	data-column=\"Email Code\">"+ 	"<input readonly type=\"text\" 	id=\"email_code"+db.rs.getString("branch_id")+"\" 		style=\"width:100%;border:none;background:transparent\" 	value=\""+db.rs.getString("email_code")+"\" 	 ></td>";
					data+="<td title=\"Order No\" 	data-column=\"Order No\">"+ 	"<input readonly type=\"text\" 	id=\"order_no"+db.rs.getString("branch_id")+"\" 		style=\"width:100%;border:none;background:transparent\" 	value=\""+db.rs.getString("order_no")+"\" 	 ></td>";
					data+="<td></td></tr>";
				}
    		} 
        	data +="</table></div></div>";
			if (i==1) {
				return "No Details Found." + "<!--"+query+"-->";
			}

			return data + "<!--"+query+"-->";
		} catch (Exception e) {
			return "<b style=\"color:red\" title=\""+e.toString()+"\">Something Went Wrong</b>" + "<!--"+query+"-->";
		} finally {
			try {
				db.closeConnection();
			} catch (SQLException ex) {
	
			}
		}
	}
		
	public String loadAddBranchData() { 
		String data = "", query = ""; String fix_id="";  int i=1;
		DBConnect db = new DBConnect();
		try {
			db.getConnection();
			
			data = "<div class='table-responsive text-wrap' ><div><table style=\"margin-left:auto;margin-right:auto\" class='tbl_new'  border=\"1\">";
			data+="<tr><th>Branch Code</th><th>Branch Name</th><th>Actions</th></tr>";
			data+="<tr>";
			data+="<td style=\"background-color:green\" title=\"Branch Code\" data-column=\"Branch Code\" >" + 	"<input type=\"text\" id=\"branch_code\" 	style=\"width:200px;background-color:#c5ecd1\" placeholder=\"Branch Code\">" 	+"</td>";
			data+="<td style=\"background-color:green\" title=\"Branch Name\" data-column=\"Branch Name\">" + 	"<input type=\"text\" id=\"branch_name\" 	style=\"width:300px;background-color:#c5ecd1\" placeholder=\"Branch Name\">"	+"</td>";
			data+="<td data-column=\"Add\" >" + "<button class=\"btn btn-warning waves-effect\" onclick=\"addBranchData()\">Add</button>" + "</td>";
			data+="</tr>"; 
			data+="</table></div></div>";
			return data + "<!--"+query+"-->";
		} catch (Exception e) {
			return "<b style=\"color:red\" title=\""+e.toString()+"\">Something Went Wrong</b>" + "<!--"+query+"-->";
		} finally {
			try {
				db.closeConnection();
			} catch (SQLException ex) {

			}
		}
	}
	
	public String addBranchData(String branch_code, String branch_name, String ss_id) {
		String data = "", query = ""; String debuginfo=""; String p_id="";
		DBConnect db = new DBConnect();
		try {
			db.getConnection();
			query="INSERT INTO camps.`master_branch` (`branch_id`,`branch_code`,`branch_name`,`status`,`inserted_by`,`inserted_date`,`updated_by`,`updated_date`) VALUES (NULL,'"+branch_code+"','"+branch_name+"',1,'"+ss_id+"',NOW(),'"+ss_id+"',NOW())"; 	
            if (db.insert2(query) != 1)
            	data="Error: Something Went Wrong";
            else
            	data="Added Successfully";
            return data;
		} catch (Exception e) {
			return "Error: <b style=\"color:red\" title=\""+e.toString()+"\">Something Went Wrong1</b>";
		} finally {
			try {
				db.closeConnection();
			} catch (SQLException ex) {

			}
		}
	}
	
	public String updateBranchData(String branch_id, String fname, String fvalue, String ss_id) {
		String data = "", query = ""; String debuginfo=""; 
		DBConnect db = new DBConnect();
		try {
			db.getConnection();
			query="UPDATE camps.`master_branch` SET "+fname+"='"+fvalue+"', updated_by='"+ss_id+"', updated_date=NOW() WHERE branch_id='"+branch_id+"'";
			db.update(query);
			return query;
		} catch (Exception e) {
			return "Error: <b style=\"color:red\" title=\""+e.toString()+"\">Something Went Wrong</b>";
		} finally {
			try {
				db.closeConnection();
			} catch (SQLException ex) {

			}
		}
	}
	
	public String freezeBranchData(String branch_id, String ss_id) {
		String data = "", query = ""; String debuginfo=""; 
		DBConnect db = new DBConnect();
		try {
			db.getConnection();
			query="UPDATE camps.`master_branch` SET status='2', updated_by='"+ss_id+"', updated_date=NOW() WHERE branch_id='"+branch_id+"'";
			db.update(query);
			return ("Freezed Successfully...");
		} catch (Exception e) {
			return "<b style=\"color:red\" title=\""+e.toString()+"\">Something Went Wrong1</b>";
		} finally {
			try {
				db.closeConnection();
			} catch (SQLException ex) {

			}
		}
	}
	
	public String deleteBranchData(String branch_id, String ss_id) {
		String data = "", query = ""; String debuginfo=""; 
		DBConnect db = new DBConnect();
		try {
			db.getConnection();
			query="UPDATE camps.`master_branch` SET status='0', updated_by='"+ss_id+"', updated_date=NOW() WHERE branch_id='"+branch_id+"'";
			db.update(query);
			return ("Deleted Successfully...");
		} catch (Exception e) {
			return "<b style=\"color:red\" title=\""+e.toString()+"\">Something Went Wrong1</b>";
		} finally {
			try {
				db.closeConnection();
			} catch (SQLException ex) {

			}
		}
	}
	
	public String loadBranchAmountData() { 
		String data = "", query = ""; String fix_id="";  int i=1;
		DBConnect db = new DBConnect();
		try {
			db.getConnection();
			
            query="SELECT mb.branch_id, IFNULL(mb.`branch_code`,'') branch_code, IFNULL(mb.`branch_name`,'') branch_name, IFNULL(mb.`order_no`,'') order_no, IFNULL(mb.`a_amount`,'') a_amount, IFNULL(mb.`status`,'') STATUS FROM camps.master_branch mb WHERE mb.`status`>0 ORDER BY mb.`order_no`";
            db.read(query);
            data = "<div class='table-responsive text-wrap' ><div><table class='tbl_new' style=\"width:100%\" border=\"1\">";
			data+="<tr><th style=\"width:20px\">S.NO</th><th style=\"width:100px\">Branch Code</th><th>Branch Name</th><th style=\"width:100px\">Order No</th><th style=\"width:150px\">Amount</th></tr>";

        	while(db.rs.next()) {
        		//if (db.rs.getString("status").equalsIgnoreCase("1")) {
					data+="<tr>";
					data+="<td data-column=\"S.No\">"+ i++ +"</td>";
					data+="<td title=\"Branch Code\" 	data-column=\"Branch Code\" >"+	"<input readonly type=\"text\" 	id=\"branch_code"+db.rs.getString("branch_id")+"\" 		style=\"width:100%;border:none;background:transparent\" value=\""+db.rs.getString("branch_code")+"\" 	 	 ></td>";
					data+="<td title=\"Branch Name\" 	data-column=\"Branch Name\" >"+	"<input readonly type=\"text\" 	id=\"branch_name"+db.rs.getString("branch_id")+"\" 		style=\"width:100%;border:none;background:transparent\" value=\""+db.rs.getString("branch_name")+"\" 	     ></td>";
					data+="<td title=\"Order No\" 		data-column=\"Order No\">"+ 	"<input type=\"number\" 		id=\"order_no"+db.rs.getString("branch_id")+"\" 		style=\"width:100%\" 	value=\""+db.rs.getString("order_no")+"\" 	onchange=\"updateBranchAmountData('order_no',"+db.rs.getString("branch_id")+");		this.style.background = '#f4e88e'\" >" 		+"</td>";
					data+="<td title=\"Amount\" 		data-column=\"Amount\">"+ 		"<input type=\"number\" 		id=\"a_amount"+db.rs.getString("branch_id")+"\" 		style=\"width:100%\" 	value=\""+db.rs.getString("a_amount")+"\" 	onchange=\"updateBranchAmountData('a_amount',"+db.rs.getString("branch_id")+");		this.style.background = '#f4e88e'\" >" 		+"</td>";
					//data+="<td data-column=\"Actions\" >" + "<button style=\"padding:3px 10px\" class=\"btn btn-danger waves-effect\" onclick=\"deleteBranchAmountData("+db.rs.getString("branch_id")+")\">X</button>";
					//data+="<button style=\"padding:3px 10px\" class=\"btn btn-success waves-effect\" onclick=\"freezeBranchAmountData("+ db.rs.getString("branch_id")+")\">Fz</button>" + "</td>";
					data+="</tr>"; 
				//}
				//else if (db.rs.getString("status").equalsIgnoreCase("2")) {
				//	data+="<tr>";
				//	data+="<td  data-column=\"S.No\">"+ i++ +"</td>";
				//	data+="<td title=\"Branch Code\" data-column=\"Branch Code\" >"+	"<input readonly type=\"text\" 	id=\"branch_code"+db.rs.getString("branch_id")+"\" 	style=\"width:100%;border:none;background:transparent\" value=\""+db.rs.getString("branch_code")+"\" ></td>";
				//	data+="<td title=\"Branch Name\" data-column=\"Branch Name\" >"+	"<input readonly type=\"text\" 	id=\"branch_name"+db.rs.getString("branch_id")+"\" 	style=\"width:100%;border:none;background:transparent\" value=\""+db.rs.getString("branch_name")+"\" ></td>";
				//	data+="<td title=\"Order No\" 	 data-column=\"Order No\">" + 		"<input readonly type=\"number\"id=\"order_no"+db.rs.getString("branch_id")+"\" 	style=\"width:100%;border:none;background:transparent\" value=\""+db.rs.getString("order_no")+"\"	 ></td>";
				//	data+="<td title=\"Amount\"   	 data-column=\"Amount\">" + 		"<input readonly type=\"number\"id=\"a_amount"+db.rs.getString("branch_id")+"\" 	style=\"width:100%;border:none;background:transparent\" value=\""+db.rs.getString("a_amount")+"\"	 ></td>";
				//	data+="</tr>";
				//}
    		}
        	data +="</table></div></div>";
			if (i==1) {
				return "No Details Found." + "<!--"+query+"-->";
			}

			return data + "<!--"+query+"-->";
		} catch (Exception e) {
			return "<b style=\"color:red\" title=\""+e.toString()+"\">Something Went Wrong</b>" + "<!--"+query+"-->";
		} finally {
			try {
				db.closeConnection();
			} catch (SQLException ex) {
	
			}
		}
	}
	
}
	
	
	
	
