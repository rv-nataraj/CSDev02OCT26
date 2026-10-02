package CAMPS.Common;

import java.sql.SQLException;

import org.json.JSONArray;
import org.json.JSONObject;

import CAMPS.Connect.DBConnect;

public class PageHeader {

	public String getLogoImageURL() {
		String data = "";
		DBConnect db = new DBConnect();
		try {
			db.getConnection();
			db.read("SELECT ifnull(org_logo_cdn,'https://cdn.campusstack.in/clients/csc/logo.png') logoimg FROM camps.master_organization mo");
			if (db.rs.next())
				data += db.rs.getString("logoimg");
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

	public String getUserPhoto(String utype, String ss_id) {
		String data = "";
		DBConnect db = new DBConnect();
		try {
			db.getConnection();
			if (utype.equalsIgnoreCase("student")) {
				db.read("SELECT TO_BASE64(sp.photo) photo FROM camps.student_photo sp WHERE sp.student_id='" + ss_id + "' AND sp.status=1 ");
			} else {
				db.read("SELECT TO_BASE64(sp.photo) photo FROM camps.staff_photo sp WHERE sp.staff_id='" + ss_id + "' AND sp.status=1 ");
			}
			if (db.rs.next()) {
				data = "data:image/jpeg;base64," + db.rs.getString("photo");
			} else
				data = "https://cdn.campusstack.in/assets/images/user.png";
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

	public static String loadDepartmentOptions() {
		String data = "";
		DBConnect db = new DBConnect();
		try {
			db.getConnection();
			data += "<option value=\"\">--Select--</option>";
			db.read("SELECT bm.branch_id,CONCAT(IFNULL(mp.programme_code,''),' ',bm.branch_name) branch_name FROM camps.master_branch bm INNER JOIN camps.master_programme mp ON mp.programme_id=bm.programme_id  WHERE bm.status=1");
			while (db.rs.next()) {
				data += "<option title=\"" + db.rs.getString("branch_id") + "\" value=\"" + db.rs.getString("branch_id") + "\">" + db.rs.getString("branch_name") + "</option>";
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

	public static String loadNotifications(String roles, String user_id, String ss_id) {
		String data = "";
		int count = 0;
		DBConnect db = new DBConnect();
		try {
			db.getConnection();

			db.read("SELECT nm.nm_id,nm.nm_title,nm.nm_desc,nm.nm_link,nm.trigger_type,nq.query,nm.trigger_type,CONCAT('\"title\":\"',nm.nm_title,'\",\"msg\":\"',nm.nm_desc,'\",\"url\":\"',nm.nm_link,'\"') DATA,nm.trigger_type FROM camps.notification_master nm LEFT JOIN camps.notification_specific ns ON nm.nm_id=ns.nm_id AND nm.nm_type=ns.nm_type AND (IF(ns.nm_type='role',ns.type_value='" + roles + "' , ns.type_value='" + user_id + "' ) )  LEFT JOIN (SELECT nh.nm_id,nh.user_id,MAX(nh.viewed_date)viewed_date,MAX(nh.delivery_date)delivery_date FROM camps.notification_history nh WHERE nh.user_id='" + user_id + "' GROUP BY nh.nm_id)nh ON nh.nm_id=nm.nm_id  LEFT JOIN camps.notification_query nq ON nq.nm_id=nm.nm_id  WHERE  CASE WHEN nm.notify='once' THEN nh.nm_id IS NULL WHEN nm.notify='until view'  THEN nh.viewed_date IS NULL AND (nh.delivery_date IS NULL OR nh.delivery_date< NOW() - INTERVAL nm.repeat_after MINUTE)WHEN nm.notify='repeat'  THEN (nh.delivery_date IS NULL OR nh.delivery_date< NOW() - INTERVAL nm.repeat_after MINUTE) END AND nm.status=1");
			while (db.rs.next()) {
				if (db.rs.getString("trigger_type").equalsIgnoreCase("query")) {
					db.read1(db.rs.getString("query").replaceAll("__XXX__", ss_id));
					if (db.rs1.next()) {
						data += "<li><a href='" + db.rs.getString("nm_link") + "' class=' waves-effect waves-block'><div class='menu-info'><h4>" + db.rs.getString("nm_title") + "</h4><p>" + db.rs1.getString("qdesc") + "</p></div></a> </li>";
						count++;
					}
				} else {
					data += "<li><a href='" + db.rs.getString("nm_link") + "' class=' waves-effect waves-block'><div class='menu-info'><h4>" + db.rs.getString("nm_title") + "</h4><p>" + db.rs.getString("nm_desc") + "</p></div></a> </li>";
					count++;
				}

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

	public static String pinResource(String resource_id, String ss_id, String status) {
		String data = "";
		DBConnect db = new DBConnect();
		try {
			db.getConnection();
			db.insert("INSERT INTO camps.`pinned_resources` (staff_id,resource_id,`rstatus`) values ('" + ss_id + "','" + resource_id + "','" + status + "') on duplicate key update rstatus=values(rstatus)");
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

	public static String showResourceInfo(String resource_id, String uType) {
		String data = "";
		int count = 0;
		int i = 1;
		DBConnect db = new DBConnect();
		try {
			db.getConnection();

			db.read("select ifnull(help_text,'No Details Found for this resource') help_text from admin.resource_help_text rht where rht.resource_id=" + resource_id);
			if (db.rs.next()) {
				data += db.rs.getString("help_text");
			} else
				data += "Resource Information Not Available";
			if (uType.equalsIgnoreCase("staff")) {
				data += "<br><hr>This resource is assigned to the following roles: ";
				// db.read("SELECT group_concat(rm.display_name) role_det FROM admin.role_master rm INNER JOIN admin.`role_resource_mapping` rrm ON rm.`role_id`=rrm.`role_id` WHERE FIND_IN_SET(" + resource_id + ",rrm.`resources`)");
				db.read("SELECT GROUP_CONCAT(rm.display_name) role_det FROM admin.role_master rm INNER JOIN admin.role_resource_map       rrm ON rm.role_id = rrm.role_id WHERE rrm.record_status>0 and rrm.resource_id = " + resource_id);

				while (db.rs.next()) {
					data += db.rs.getString("role_det");
				}

				// db.read("SELECT rm.role_id, um.staff_id, sm.`first_name`,GROUP_CONCAT(rm.`display_name`) role_name FROM admin.`role_master` rm INNER JOIN admin.role_resource_mapping rrm ON rm.role_id=rrm.role_id LEFT JOIN admin.user_master um ON FIND_IN_SET(rm.role_id,um.roles) INNER JOIN camps.staff_master sm ON sm.staff_id=um.staff_id WHERE FIND_IN_SET(" + resource_id + ",rrm.`resources`) GROUP BY sm.`staff_id`");
				db.read("SELECT rm.role_id, um.staff_id, sm.first_name,  GROUP_CONCAT(rm.display_name)   role_name FROM admin.role_master rm   INNER JOIN admin.role_resource_map     rrm ON rm.role_id=rrm.role_id LEFT JOIN admin.user_master um ON FIND_IN_SET(rm.role_id,um.roles) INNER JOIN camps.staff_master sm ON sm.staff_id=um.staff_id WHERE rrm.record_status>0 and  rrm.resource_id=" + resource_id + " GROUP BY sm.staff_id");

				data += "<br><hr>This resource is available to the following users:<br> ";
				while (db.rs.next()) {
					i++;
					if (i <= 50)
						data += db.rs.getString("staff_id") + "-" + db.rs.getString("first_name") + " <span title=\"" + db.rs.getString("role_id") + "\">(" + db.rs.getString("role_name") + ")</span><br>";

				}
				if (i > 50)
					data += "<br>and " + i + " more";
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

	public static String loadResourceInfoBar(String host_string, String link, String url, String ss_id) {
		String data = "";
		String script_data = "";
		int count = 0;
		int i = 1;
		String resource_id = "";
		DBConnect db = new DBConnect();
		try {
			db.getConnection();

			data += " <section class=\"content1\" title=\"This is info bar. This bar shows the current resource being accessed on the left side.  On the right side, it shows three icons: The home symbol icon takes the user to the Home->Pinned resources, the pin icon lets user to pin the current resource as their favorite, the info icon displays help text for the current resource.\">";
			data += "  <ol style=\"border-radius:8px\"" + (host_string.contains("test") ? "class=\"breadcrumb breadcrumb-bg-green\"" : "class=\"breadcrumb breadcrumb-bg-blue\"") + ">";

			db.read("SELECT @r AS id, (SELECT CONCAT('<li><i class=\"material-icons\" d=\"',@r := c.parent_id,'\">',IFNULL(c.img,'link'),'</i> ',IF(@l=0,concat('<a target=\"_blank\" href=\"https://github.com/campusstack2/CSV9/blob/main/src/main/webapp/',c.link,'\">',c.label,'</a>'),c.label),'</li>') FROM admin.resource_master c  WHERE c.resource_id = id ) AS parent, @l := @l + 1 AS LEVEL,u.label FROM (SELECT @r := (SELECT resource_id FROM admin.resource_master a WHERE a.link='" + link + "'), @l := 0) vars, admin.resource_master u WHERE @r <> 0 ORDER BY LEVEL DESC");
			while (db.rs.next()) {
				data += db.rs.getString("parent");
				script_data += "$('#menu_" + db.rs.getString("id") + "').addClass(\"active\");";
				resource_id = db.rs.getString("id");
			}

			data += "<li style=\"float: right;\"><a title=\"Goto Home Page\" href=\"" + url + "/JSP/home/pinned_resources.jsp\" ><i class=\"material-icons\">home</i></a>";

			if (!resource_id.equalsIgnoreCase("359")) {
				db.read("select resource_id,staff_id,rstatus from camps.pinned_resources pr where pr.rstatus=1 and pr.resource_id='" + resource_id + "' and pr.staff_id='" + ss_id + "'");
				if (db.rs.next()) {
					data += "<a title=\"unpin this resource\" href=\"#\" onclick=\"unpin_this_resource(" + resource_id + ")\"><i class=\"material-icons\">delete</i></a>";
				} else {
					data += "<a title=\"pin this resource\" href=\"#\" onclick=\"pin_this_resource(" + resource_id + ")\"><i class=\"material-icons\">push_pin</i></a>";
				}
			}

			data += "<a title=\"Info about this resource - Reserved for future use\"  data-toggle=\"modal\" data-target=\"#largeModalinfo\"  onclick='showResourceDetails(" + resource_id + ")' href=\"javascript:showResourceDetails(" + resource_id + ")\"><i class=\"material-icons\">info</i></a></li>";
			data += "</ol>";

			script_data = "<script>" + script_data + "</script>";
			data += "</section>";

			return data + script_data;
		} catch (Exception e) {
			return "<b style=\"color:red\" title=\"" + e.toString() + "\">Something Went Wrong</b>";
		} finally {
			try {
				db.closeConnection();
			} catch (SQLException ex) {

			}
		}
	}

	public String getUserInfo(String utype, String ss_id) {
		String data = "";
		DBConnect db = new DBConnect();
		try {
			db.getConnection();
			if (utype.equalsIgnoreCase("student")) {
				db.read("SELECT sm.student_id AS user_id, CONCAT(IFNULL(sm.first_name,''),' ',IFNULL(sm.last_name,'')) AS user_name, 'Student' AS designation FROM camps.student_master sm WHERE sm.student_id='" + ss_id + "';");
			} else {
				db.read("SELECT sm.staff_id AS user_id, CONCAT(IFNULL(sm.legend,''), '. ', IFNULL(sm.first_name,''),' ',IFNULL(sm.last_name,'')) AS user_name, IFNULL(md.desigination,'') AS designation FROM camps.staff_master sm LEFT JOIN camps.staff_promotion sp ON sm.staff_id=sp.staff_id AND sp.status=2 LEFT JOIN camps.master_desigination md ON md.md_id=sp.md_id AND md.status>0 WHERE sm.staff_id='" + ss_id + "';");
			}

			if (db.rs.next()) {
				String userId = db.rs.getString("user_id");
				String userName = db.rs.getString("user_name");
				String designation = db.rs.getString("designation");

				data = "{\"user_id\":\"" + userId + "\",\"user_name\":\"" + userName + "\",\"designation\":\"" + designation + "\"}";
			} else {
				data = "{\"user_id\":\"\",\"user_name\":\"\",\"designation\":\"\"}";
			}
			return data;
		} catch (Exception e) {
			return "{\"user_id\":\"\",\"user_name\":\"\",\"designation\":\"\",\"error\":\"Something Went Wrong\"}";
		} finally {
			try {
				db.closeConnection();
			} catch (SQLException ex) {
			}
		}
	}

	public String getUserNotification(String utype, String ss_id) {
		String data = "";
		DBConnect db = new DBConnect();
		String query = "";
		try {
			db.getConnection();
			if (utype.equalsIgnoreCase("student")) {
				query = "SELECT sn_id, message, view_status, created_date FROM camps.student_notification WHERE student_id='" + ss_id + "' AND view_status=1 AND record_status>0 AND created_date >= DATE_SUB(NOW(), INTERVAL 3 DAY) ORDER BY created_date DESC;";
			} else {
				query = "SELECT sn_id, message, view_status, created_date  FROM camps.staff_notification WHERE staff_id='" + ss_id + "' AND view_status=1 AND record_status>0 AND created_date >= DATE_SUB(NOW(), INTERVAL 3 DAY) ORDER BY created_date DESC;";
			}

			db.read(query);
			StringBuilder json = new StringBuilder("[");
			boolean first = true;

			while (db.rs.next()) {
				if (!first) {
					json.append(",");
				}
				json.append("{").append("\"sn_id\":\"").append(db.rs.getString("sn_id")).append("\",").append("\"message\":\"").append(db.rs.getString("message")).append("\",").append("\"view_status\":\"").append(db.rs.getString("view_status")).append("\",").append("\"created_date\":\"").append(db.rs.getString("created_date")).append("\"").append("}");
				first = false;
			}
			json.append("]");
			data = "{\"query\":\"" + query.replace("\"", "\\\"") + "\",\"notifications\":" + json.toString() + "}";
			return data;
		} catch (Exception e) {
			return "{\"query\":\"" + query.replace("\"", "\\\"") + "\",\"notifications\":[],\"error\":\"Something Went Wrong\"}";
		} finally {
			try {
				db.closeConnection();
			} catch (SQLException ex) {
			}
		}
	}

	public String UpdateUserNotificationViewed(String utype, String sn_id, String ss_id) {
		String query = "";
		DBConnect db = new DBConnect();

		try {
			db.getConnection();
			if (utype.equalsIgnoreCase("student")) {
				query = "UPDATE camps.student_notification SET view_status='0', last_updated_date=NOW(), last_updated_by=" + ss_id + " WHERE sn_id=" + sn_id + " AND record_status>0";
			} else {
				query = "UPDATE camps.staff_notification SET view_status='0', last_updated_date=NOW(), last_updated_by=" + ss_id + " WHERE sn_id=" + sn_id + " AND record_status>0";
			}
			db.update(query);
			JSONObject res = new JSONObject();
			res.put("status", "success");
			res.put("query", query);
			return res.toString();
		} catch (Exception e) {
			JSONObject err = new JSONObject();
			err.put("status", "error");
			err.put("message", "Update Failed");
			err.put("query", query);
			return err.toString();
		} finally {
			try {
				db.closeConnection();
			} catch (SQLException ex) {
			}
		}
	}

	public static String load_Resource_Info_Bar_JSON(String host_string, String link, String url, String ss_id) {
		JSONObject response = new JSONObject();
		JSONArray breadcrumb = new JSONArray();
		String resource_id = "";
		DBConnect db = new DBConnect();

		try {
			db.getConnection();

			String resourceLink = link;

			// Remove query string from current request URL
			if (resourceLink.contains("?")) {
				resourceLink = resourceLink.substring(0, resourceLink.indexOf("?"));
			}

			db.read("SELECT @r AS id,(SELECT IFNULL(c.img,'link') FROM admin.resource_master c WHERE c.resource_id=id) AS icon,(SELECT c.label FROM admin.resource_master c WHERE c.resource_id=id) AS label,(SELECT c.link FROM admin.resource_master c WHERE c.resource_id=id) AS link,@r := (SELECT c.parent_id FROM admin.resource_master c WHERE c.resource_id=id) AS parent_id,@l := @l + 1 AS LEVEL,u.label FROM (SELECT @r := (SELECT resource_id FROM admin.resource_master a WHERE SUBSTRING_INDEX(a.link,'?',1)='" + resourceLink + "' LIMIT 1),@l := 0) vars,admin.resource_master u WHERE @r <> 0 ORDER BY LEVEL DESC");

			while (db.rs.next()) {

				JSONObject obj = new JSONObject();

				String id = db.rs.getString("id");
				String icon = db.rs.getString("icon");
				String label = db.rs.getString("label");
				String dbLink = db.rs.getString("link");

				String githubLink = dbLink;

				// Remove query string for GitHub source URL
				if (githubLink != null && githubLink.contains("?")) {
					githubLink = githubLink.substring(0, githubLink.indexOf("?"));
				}

				obj.put("id", id);
				obj.put("icon", icon);
				obj.put("label", label);
				obj.put("link", dbLink);
				obj.put("github_url", "https://github.com/campusstack2/CSV9/blob/main/src/main/webapp/" + githubLink);

				breadcrumb.put(obj);

				resource_id = id;
			}

			response.put("breadcrumb", breadcrumb);
			response.put("resource_id", resource_id);
			response.put("home_url", url + "/JSP/home/pinned_resources.jsp");

			if (!resource_id.equalsIgnoreCase("") && !resource_id.equalsIgnoreCase("359")) {

				db.read("SELECT resource_id,staff_id,rstatus FROM camps.pinned_resources pr WHERE pr.rstatus=1 AND pr.resource_id='" + resource_id + "' AND pr.staff_id='" + ss_id + "'");

				if (db.rs.next()) {
					response.put("pin_status", "pinned");
				} else {
					response.put("pin_status", "unpinned");
				}

			} else {
				response.put("pin_status", "disabled");
			}

			response.put("status", "success");

			return response.toString();

		} catch (Exception e) {

			JSONObject err = new JSONObject();

			err.put("status", "error");
			err.put("message", e.toString());

			return err.toString();

		} finally {

			try {
				db.closeConnection();
			} catch (SQLException ex) {
			}
		}
	}

}