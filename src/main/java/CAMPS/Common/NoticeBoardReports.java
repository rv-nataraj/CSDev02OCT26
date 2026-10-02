package CAMPS.Common;

import java.sql.SQLException;

import CAMPS.Connect.DBConnect;

public class NoticeBoardReports {

	public String todayDayOrder() {
		String data = " ", query = "";
		String debuginfo = "";
		DBConnect db = new DBConnect();
		try {
			db.getConnection();
			query = "SELECT dm.`day_name` FROM studacad.`day_order_mapping` dom INNER JOIN studacad.`day_master` dm ON dm.`dm_id`=dom.`dm_id` WHERE dom.status>0 and doc=DATE(NOW())";
			db.read(query);
			if (db.rs.next())
				data = "Today's Day Order: " + db.rs.getString("day_name");
			else
				data = "No Day Order is mapped Today." + "<!--" + query + "-->";
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
