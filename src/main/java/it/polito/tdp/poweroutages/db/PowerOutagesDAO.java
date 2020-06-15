package it.polito.tdp.poweroutages.db;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import it.polito.tdp.poweroutages.model.Nerc;
import it.polito.tdp.poweroutages.model.PowerOutage;
import it.polito.tdp.poweroutages.model.Relation;

public class PowerOutagesDAO {
	
	public List<Nerc> loadAllNercs(Map<Integer, Nerc> idMap) {

		String sql = "SELECT id, value FROM nerc ORDER BY value";
		List<Nerc> nercList = new ArrayList<>();

		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();

			while (res.next()) {
				Integer id = res.getInt("id");
				Nerc n = new Nerc(res.getInt("id"), res.getString("value"));
				nercList.add(n);
				
				if(!idMap.containsKey(id)) {
					idMap.put(id, n);
				}
				
			}

			conn.close();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

		return nercList;
	}
	
	
	public List<Relation> getRelations(Map<Integer, Nerc> idMap) {
		
		String sql = "SELECT tab.id1 AS id1, tab.id2 AS id2, SUM(tab.prov) AS peso " + 
				"FROM (SELECT p1.nerc_id AS id1, p2.nerc_id AS id2, COUNT(DISTINCT MONTH(p1.date_event_began)) AS prov " + 
				"FROM nercrelations AS nr, poweroutages AS p1, poweroutages AS p2 " + 
				"WHERE nr.nerc_one = p1.nerc_id AND nr.nerc_two = p2.nerc_id AND " + 
				"YEAR(p1.date_event_began) = YEAR(p2.date_event_began) AND " + 
				"MONTH(p1.date_event_began) = MONTH(p2.date_event_began) " + 
				"GROUP BY p1.nerc_id, p2.nerc_id, YEAR(p1.date_event_began)) AS tab " + 
				"GROUP BY tab.id1, tab.id2";
		List<Relation> result = new ArrayList<>();

		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();

			while (res.next()) {
				if(idMap.containsKey(res.getInt("id1")) && idMap.containsKey(res.getInt("id2"))) {
					result.add(new Relation(idMap.get(res.getInt("id1")), idMap.get(res.getInt("id2")), res.getInt("peso")));
				}
			}

			conn.close();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

		return result;
	}


	public List<PowerOutage> getPowerOutage(Map<Integer, Nerc> idMap) {
		String sql = "SELECT id, nerc_id, date_event_began AS b, date_event_finished AS f " + 
				"FROM poweroutages ORDER BY date_event_began";
		List<PowerOutage> result = new ArrayList<>();

		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();

			while (res.next()) {
				if(idMap.containsKey(res.getInt("nerc_id"))) {
					result.add(new PowerOutage(res.getInt("id"), idMap.get(res.getInt("nerc_id")), 
							res.getTimestamp("b").toLocalDateTime(), res.getTimestamp("f").toLocalDateTime()));
				}
				
			}

			conn.close();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

		return result;
	}
}
