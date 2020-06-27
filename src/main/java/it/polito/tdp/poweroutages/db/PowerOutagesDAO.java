package it.polito.tdp.poweroutages.db;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import it.polito.tdp.poweroutages.model.Adiacenza;
import it.polito.tdp.poweroutages.model.Nerc;
import it.polito.tdp.poweroutages.model.PowerOutage;

public class PowerOutagesDAO {
	
	public Map<Integer, Nerc> loadAllNercs(Map<Integer, Nerc> nercs) {

		String sql = "SELECT id, value FROM nerc";

		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();

			while (res.next()) {
				Nerc n = new Nerc(res.getInt("id"), res.getString("value"));
				nercs.put(n.getId(), n);
			}

			conn.close();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

		return nercs;
	}
	
	public List<Adiacenza> getAdiacenze(Map<Integer, Nerc> nercs) {
		String sql = "SELECT tab.id1 AS id1, tab.id2 AS id2, SUM(tab.mesi) AS peso " + 
				"FROM (SELECT p1.nerc_id AS id1, p2.nerc_id AS id2, COUNT(DISTINCT MONTH(p1.date_event_began)) AS mesi " + 
				"FROM nercrelations AS nr, poweroutages AS p1, poweroutages AS p2 " + 
				"WHERE nr.nerc_one = p1.nerc_id AND nr.nerc_two = p2.nerc_id AND MONTH(p1.date_event_began) = MONTH(p2.date_event_began) " + 
				"AND YEAR(p1.date_event_began) = YEAR(p2.date_event_began) " + 
				"GROUP BY p1.nerc_id, p2.nerc_id, YEAR(p1.date_event_began)) AS tab " + 
				"GROUP BY tab.id1, tab.id2";
		
		List<Adiacenza> result = new ArrayList<>();

		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();

			while (res.next()) {
				Adiacenza n = new Adiacenza(nercs.get(res.getInt("id1")), nercs.get(res.getInt("id2")), res.getInt("peso"));
				result.add(n);
			}

			conn.close();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

		return result;
	}
	
	public List<PowerOutage> getAllPowerOutages() {
		String sql = "SELECT po.id, po.nerc_id, po.date_event_began, po.date_event_finished " + 
				"FROM poweroutages AS po";
		
		List<PowerOutage> result = new ArrayList<>();

		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();

			while (res.next()) {
				PowerOutage n = new PowerOutage(res.getInt("id"), res.getInt("nerc_id"), res.getTimestamp("date_event_began").toLocalDateTime(), res.getTimestamp("date_event_finished").toLocalDateTime());
				result.add(n);
			}

			conn.close();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

		return result;
	}
}
