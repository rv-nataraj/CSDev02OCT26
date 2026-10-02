package CAMPS.Masters;

import java.sql.SQLException;
import CAMPS.Connect.DBConnect;
import jakarta.servlet.http.HttpSession;

public class DesignationMasterTransactions { 

	public String loadDesignationData() { 
		String data = "", query = ""; String fix_id="";  int i=1;
		DBConnect db = new DBConnect();
		try {
			db.getConnection();
            query="SELECT md.md_id, IFNULL(md.desigination,'') desigination, IFNULL(md.`order_no`,'') order_no, IFNULL(md.`status`,'') STATUS,ifnull(md.short_name,'')short_name  FROM camps.`master_desigination` md WHERE md.`status`>0 ORDER BY md.`order_no`";
            db.read(query);
            data = "<div class='table-responsive text-wrap' ><div><table class='tbl_new' style=\"margin-left:auto;margin-right:auto\" border=\"1\">";
			data+="<tr><th>S.NO</th><th>Designation</th><th>Short Name</th><th style=\"width:100px\">Order No</th><th style=\"width:70px\">Actions</th></tr>";

        	while(db.rs.next()) {
        		if (db.rs.getString("status").equalsIgnoreCase("1")) {
					data+="<tr>";
					data+="<td  data-column=\"S.No\">"+ i++ +"</td>";
					data+="<td title=\"Designation\" data-column=\"Designation\" >"+"<input type=\"text\" 	id=\"desigination"+db.rs.getString("md_id")+"\" style=\"width:250px\" 	value=\""+db.rs.getString("desigination")+"\" 	onchange=\"updateDesignationData('desigination',"+db.rs.getString("md_id")+");	this.style.background = '#f4e88e'\" >"		+"</td>";
					data+="<td title=\"Designation\" data-column=\"Short Name\" >"+ "<input type=\"text\" 	id=\"short_name"+db.rs.getString("md_id")+"\" style=\"width:250px\" 	value=\""+db.rs.getString("short_name")+"\" 	onchange=\"updateDesignationData('short_name',"+db.rs.getString("md_id")+");	this.style.background = '#f4e88e'\" >"		+"</td>";
					data+="<td title=\"Order No\" 	 data-column=\"Order No\">"+ 		"<input type=\"number\" id=\"order_no"+db.rs.getString("md_id")+"\" 	style=\"width:100%\" 	value=\""+db.rs.getString("order_no")+"\" 		onchange=\"updateDesignationData('order_no',"+db.rs.getString("md_id")+");		this.style.background = '#f4e88e'\" >" 		+"</td>";
					data+="<td data-column=\"Actions\" >" + "<button style=\"padding:3px 10px\" class=\"btn btn-danger waves-effect\" onclick=\"deleteDesignationData("+db.rs.getString("md_id")+")\">X</button>";
					data+="<button style=\"padding:3px 10px\" class=\"btn btn-success waves-effect\" onclick=\"freezeDesignationData("+ db.rs.getString("md_id")+")\">Fz</button>" + "</td>";
					data+="</tr>"; 
				}
				else if (db.rs.getString("status").equalsIgnoreCase("2")) {
					data+="<tr>";
					data+="<td  data-column=\"S.No\">"+ i++ +"</td>";
					data+="<td title=\"Designation\" data-column=\"Designation\" >"+	"<input readonly type=\"text\" 	 id=\"desigination"+db.rs.getString("md_id")+"\" style=\"width:100%;border:none;background:transparent\" 	value=\""+db.rs.getString("desigination")+"\" 	 >"		+"</td>";
					data+="<td title=\"Designation\" data-column=\"Short Name\" >"+    "<input readonly  type=\"text\" 	id=\"short_name"+db.rs.getString("md_id")+"\" style=\"width:250px\" 	value=\""+db.rs.getString("short_name")+"\" 	onchange=\"updateDesignationData('short_name',"+db.rs.getString("md_id")+");	this.style.background = '#f4e88e'\" >"		+"</td>";
					data+="<td title=\"Order No\" 	 data-column=\"Order No\">"+ 		"<input readonly type=\"number\" id=\"order_no"+db.rs.getString("md_id")+"\" 	 style=\"width:100%;border:none;background:transparent\" 	value=\""+db.rs.getString("order_no")+"\" 		 >" 		+"</td>";
					data+="<td></td></tr>";
				}
    		}
        	data +="</table></div></div>";
			if (i==1) {
				return "No Details Found." + "<!--"+query+"-->";
			}

			return data + "<!--"+query+"-->";
		} catch (Exception e) {
			return "Error: <b style=\"color:red\" title=\""+e.toString()+"\">Something Went Wrong</b>" + "<!--"+query+"-->";
		} finally {
			try {
				db.closeConnection();
			} catch (SQLException ex) {
	
			}
		}
	}
		
	public String loadAddDesignationData() { 
		String data = "", query = ""; String fix_id="";  int i=1;
		DBConnect db = new DBConnect();
		try {
			db.getConnection();
			
			data = "<div class='table-responsive text-wrap' ><div><table style=\"margin-left:auto;margin-right:auto\" class='tbl_new'  border=\"1\">";
			data+="<tr><th>Designation</th><th>Order No</th><th>Actions</th></tr>";
			data+="<tr>";
			data+="<td style=\"background-color:green\" title=\"Designation\" 	data-column=\"Designation\" >" + 	"<input type=\"text\" id=\"desigination\" 	style=\"width:300px;background-color:#c5ecd1\" placeholder=\"Designation\">" 	+"</td>";
			data+="<td style=\"background-color:green\" title=\"Order No\" 		data-column=\"Order No\">" + 		"<input type=\"number\" id=\"order_no\" 		style=\"width:100px;background-color:#c5ecd1\" placeholder=\"Order No\">"	+"</td>";
			data+="<td data-column=\"Add\" >" + "<button class=\"btn btn-warning waves-effect\" onclick=\"addDesignationData()\">Add</button>" + "</td>";
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
	
	public String addDesignationData(String desigination, String order_no, String ss_id) {
		String data = "", query = ""; String debuginfo=""; String p_id="";
		DBConnect db = new DBConnect();
		try {
			db.getConnection();
			query="INSERT INTO camps.`master_desigination` (`md_id`,`desigination`,`order_no`,`status`,`inserted_by`,`inserted_date`,`updated_by`,`updated_date`) VALUES (NULL,'"+desigination+"','"+order_no+"',1,'"+ss_id+"',NOW(),'"+ss_id+"',NOW())"; 	
            db.insert(query);
            
            return ("Added Successfully...");
		} catch (Exception e) {
			return "<b style=\"color:red\" title=\""+e.toString()+"\">Something Went Wrong1</b>";
		} finally {
			try {
				db.closeConnection();
			} catch (SQLException ex) {

			}
		}
	}
	
	public String updateDesignationData(String md_id, String fname, String fvalue, String ss_id) {
		String data = "", query = ""; String debuginfo=""; 
		DBConnect db = new DBConnect();
		try {
			db.getConnection();
			query="UPDATE camps.`master_desigination` SET "+fname+"='"+fvalue+"', updated_by='"+ss_id+"', updated_date=NOW() WHERE md_id='"+md_id+"'";
			db.update(query);
			return query;
		} catch (Exception e) {
			return "<b style=\"color:red\" title=\""+e.toString()+"\">Something Went Wrong1</b>";
		} finally {
			try {
				db.closeConnection();
			} catch (SQLException ex) {

			}
		}
	}
	
	public String freezeDesignationData(String md_id, String ss_id) {
		String data = "", query = ""; String debuginfo=""; 
		DBConnect db = new DBConnect();
		try {
			db.getConnection();
			query="UPDATE camps.`master_desigination` SET status='2', updated_by='"+ss_id+"', updated_date=NOW() WHERE md_id='"+md_id+"'";
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
	
	public String deleteDesignationData(String md_id, String ss_id) {
		String data = "", query = ""; String debuginfo=""; 
		DBConnect db = new DBConnect();
		try {
			db.getConnection();
			query="UPDATE camps.`master_desigination` SET status='0', updated_by='"+ss_id+"', updated_date=NOW() WHERE md_id='"+md_id+"'";
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
	
}
	
	
	
	
