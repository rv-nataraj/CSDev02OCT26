package CAMPS.Admin;

import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.json.JSONArray;
import org.json.JSONObject;

import CAMPS.Connect.DBConnect;

public class menu {

	DBConnect db = new DBConnect();
	String menu_string = "";

	public String menu_generator(int parent, String role, String hostname) {
		try {
			db.getConnection();
			// db.read("SELECT ifnull(rm.bg,'') bgstyle, rm.loading_mode, ifnull(rm.extraparameters,0) extraparameters, rm.sortorder, rm.resource_id, rm.label, IFNULL(rm.img,'link') img, CASE WHEN rm.link='#' THEN '#' WHEN (rm.extraParameters='1') THEN rm.link ELSE CONCAT('"+hostname+"',rm.link) END link, COUNT(rm1.resource_id) COUNT FROM admin.resource_master rm INNER JOIN admin.role_resource_mapping rrm ON FIND_IN_SET(rm.resource_id,rrm.resources) AND rrm.role_id IN ("+role+") AND rm.parent_id='"+parent+"' LEFT JOIN (admin.resource_master rm1 INNER JOIN admin.role_resource_mapping rrm1 ON FIND_IN_SET(rm1.resource_id,rrm1.resources) AND rrm1.role_id IN ("+role+")) ON rm.resource_id=rm1.parent_id WHERE ifnull(rm.extraParameters,'0')<>'2' and rm.rstatus=1 GROUP BY rm.resource_id ORDER BY rm.sortorder");
			db.read("SELECT ifnull(crm.controller_id,0) cont_id, IFNULL(rm.bg,'') bgstyle, rm.loading_mode, IFNULL(rm.extraparameters,0) extraparameters, rm.sortorder, rm.resource_id, rm.label, IFNULL(rm.img,'link') img, CASE WHEN rm.link='#' THEN '#' WHEN (rm.extraParameters='1') THEN rm.link ELSE CONCAT('" + hostname + "',rm.link) END link, COUNT(rm1.resource_id) COUNT FROM admin.resource_master rm INNER JOIN admin.role_resource_map     rrm ON rm.resource_id=rrm.resource_id  and rrm.record_status>0          AND rrm.role_id IN (" + role + ")                               LEFT JOIN  admin.resource_master rm1  ON rm.resource_id=rm1.parent_id LEFT JOIN admin.role_resource_map rrm1 ON rm1.resource_id=rrm1.resource_id AND rrm1.role_id IN (" + role + ")    left join admin.controller_resource_mapping crm on crm.resource_id=rm.resource_id and crm.record_status>0                              WHERE rm.parent_id='" + parent + "' AND IFNULL(rm.extraParameters,'0')<>'2' AND rm.rstatus=1 GROUP BY rm.resource_id ORDER BY rm.sortorder");

			while (db.rs.next()) {
				if (db.rs.getInt("count") == 0) {
					if (db.rs.getInt("loading_mode") == 1)
						menu_string += "<li style=\"background-color:" + db.rs.getString("bgstyle") + "\"  id='menu_" + db.rs.getString("resource_id") + "'><a " + ((db.rs.getString("extraparameters").equalsIgnoreCase("3") || db.rs.getString("extraparameters").equalsIgnoreCase("1")) ? "target='_blank'" : "") + " href='" + db.rs.getString("link") + "' title='" + db.rs.getString("resource_id") + "-" + db.rs.getString("sortorder") + "' ><i class=\"material-icons\"  " + (db.rs.getString("cont_id").equalsIgnoreCase("0") ? "" : "style=\"color:#6aa861\"") + " >" + db.rs.getString("img") + "</i><span>" + db.rs.getString("label") + "</span> </a></li>";
					else
						menu_string += "<li style=\"background-color:" + db.rs.getString("bgstyle") + "\"  id='menu_" + db.rs.getString("resource_id") + "'><a " + ((db.rs.getString("extraparameters").equalsIgnoreCase("3") || db.rs.getString("extraparameters").equalsIgnoreCase("1")) ? "target='_blank'" : "") + " href='javascript:void(0);' onclick=\"loadPage('" + db.rs.getString("link") + "?lm=ajax'); return false;\"  title='" + db.rs.getString("resource_id") + "-" + db.rs.getString("sortorder") + "' ><i class=\"material-icons\" " + (db.rs.getString("cont_id").equalsIgnoreCase("0") ? "" : "style=\"color:#6aa861\"") + "  >" + db.rs.getString("img") + "</i><span>" + db.rs.getString("label") + "</span> </a></li>";

				} else {
					menu a = new menu();
					menu_string += "<li style=\"background-color:" + db.rs.getString("bgstyle") + "\" id='menu_" + db.rs.getString("resource_id") + "'><a " + ((db.rs.getString("extraparameters").equalsIgnoreCase("3") || db.rs.getString("extraparameters").equalsIgnoreCase("1")) ? "target='_blank'" : "") + " href='" + db.rs.getString("link") + "' class=\"menu-toggle\" title='" + db.rs.getString("resource_id") + "-" + db.rs.getString("sortorder") + "' ><i class=\"material-icons\" " + (db.rs.getString("cont_id").equalsIgnoreCase("0") ? "" : "style=\"color:#6aa861\"") + ">" + db.rs.getString("img") + "</i><span>" + db.rs.getString("label") + " </span></a><ul class=\"ml-menu\">" + a.menu_generator(db.rs.getInt("resource_id"), role, hostname) + "</ul></li>";
				}
			}
		} catch (Exception e) {
			return e.toString();
			// return "<p title=\" "+ e.toString() + " \">Something went wrong </p>";
		} finally {
			try {
				db.closeConnection();
			} catch (SQLException ex) {
				Logger.getLogger(menu.class.getName()).log(Level.SEVERE, null, ex);
			}
		}
		return menu_string;
	}

	public String menu_generator_json(String role, String hostname) {
		JSONArray menuArray = new JSONArray();
		DBConnect db = new DBConnect();
		try {
			db.getConnection();

			String query = "SELECT rm.resource_id, rm.parent_id, rm.label, IFNULL(rm.img,'link') img, CASE WHEN rm.link='#' THEN '#' WHEN (rm.extraParameters='1') THEN rm.link ELSE CONCAT('" + hostname + "',rm.link) END link, IFNULL(rm.bg,'') bgstyle, rm.sortorder,IFNULL(crm.controller_id,0) cont_id FROM admin.resource_master rm INNER JOIN admin.role_resource_map rrm ON rm.resource_id=rrm.resource_id AND rrm.role_id IN (" + role + ") LEFT JOIN admin.role_resource_map rrm1 ON rm.resource_id=rrm1.resource_id AND rrm1.role_id IN (" + role + ") LEFT JOIN admin.controller_resource_mapping crm ON crm.resource_id=rm.resource_id AND crm.record_status>0 WHERE IFNULL(rm.extraParameters,'0')<>'2' AND rm.rstatus=1 GROUP BY rm.resource_id ORDER BY rm.sortorder, rm.resource_id";

			db.read(query);
			while (db.rs.next()) {
				JSONObject obj = new JSONObject();

				obj.put("resource_id", db.rs.getString("resource_id"));
				obj.put("parent_id", db.rs.getString("parent_id"));
				obj.put("label", db.rs.getString("label"));
				obj.put("icon", db.rs.getString("img"));
				obj.put("link", db.rs.getString("link"));
				obj.put("sortorder", db.rs.getString("sortorder"));
				obj.put("bgstyle", db.rs.getString("bgstyle"));
				obj.put("cont_id", db.rs.getString("cont_id"));

				menuArray.put(obj);
			}
			return menuArray.toString();
		} catch (Exception e) {
			JSONObject err = new JSONObject();
			err.put("status", "error");
			err.put("message", e.toString());
			return err.toString();
		} finally {
			try {
				db.closeConnection();
			} catch (SQLException ex) {
				Logger.getLogger(menu.class.getName()).log(Level.SEVERE, null, ex);
			}
		}
	}

}