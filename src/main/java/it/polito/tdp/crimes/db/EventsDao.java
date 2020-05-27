package it.polito.tdp.crimes.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import it.polito.tdp.crimes.model.Coppia;
import it.polito.tdp.crimes.model.Event;

public class EventsDao {
	
	public List<String> listAllCategories(){
		
		List<String> daRitornare = new ArrayList<>();
		
		final String sql = "SELECT DISTINCT offense_category_id FROM events";


		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);

			ResultSet rs = st.executeQuery();

			while (rs.next()) {

				String temp = rs.getString("offense_category_id");
				daRitornare.add(temp);
			}

			conn.close();
			Collections.sort(daRitornare);
			return daRitornare;

		} catch (SQLException e) {
			// e.printStackTrace();
			throw new RuntimeException("Errore Db", e);
		}
	}
	
	public List<String> listTypeFromCategory(String category){
		
		List<String> daRitornare = new ArrayList<>();
		
		final String sql = "SELECT DISTINCT offense_type_id FROM events " + 
							"WHERE offense_category_id = ?";

		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setString(1, category);
			ResultSet rs = st.executeQuery();

			while (rs.next()) {

				String temp = rs.getString("offense_type_id");
				daRitornare.add(temp);
			}

			conn.close();
			Collections.sort(daRitornare);
			return daRitornare;

		} catch (SQLException e) {
			// e.printStackTrace();
			throw new RuntimeException("Errore Db", e);
		}
	}
	
	public List<Coppia> getCoppie(String categoria, int anno){
		
		List<Coppia> daRitornare = new ArrayList<>();
		
		final String sql = "SELECT e1.offense_type_id AS id1, e2.offense_type_id AS id2,  COUNT(DISTINCT(e2.district_id)) AS peso "+
							"FROM events e1, events e2 "+
							"WHERE e1.offense_category_id = e2.offense_category_id "+
							"AND e1.offense_category_id = ? "+
							"AND e1.offense_type_id != e2.offense_type_id "+
							"AND e1.neighborhood_id = e2.neighborhood_id "+
							"AND YEAR(e1.reported_date) = ? "+
							"AND YEAR(e2.reported_date) = ? "+
							"GROUP BY e1.offense_type_id, e2.offense_type_id";
		
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setString(1, categoria);
			st.setInt(2, anno);
			st.setInt(3, anno);
			ResultSet rs = st.executeQuery();

			while (rs.next()) {

				Coppia cTemp = new Coppia(rs.getString("id1"), rs.getString("id2"), rs.getInt("peso"));
				daRitornare.add(cTemp);
				
			}

			conn.close();
			return daRitornare;

		} catch (SQLException e) {
			// e.printStackTrace();
			throw new RuntimeException("Errore Db", e);
		}
	}
	
	public List<Integer> listAllYears(){
		
		List<Integer> daRitornare = new ArrayList<>();
		
		final String sql = "SELECT distinct YEAR(reported_date) as anno FROM events";
		
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);

			ResultSet rs = st.executeQuery();

			while (rs.next()) {

				int temp = rs.getInt("anno");
				daRitornare.add(temp);
			}

			conn.close();
			Collections.sort(daRitornare);
			return daRitornare;

		} catch (SQLException e) {
			// e.printStackTrace();
			throw new RuntimeException("Errore Db", e);
		}
	}
	
	public List<Event> listAllEvents(){
		String sql = "SELECT * FROM events" ;
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			
			List<Event> list = new ArrayList<>() ;
			
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				try {
					list.add(new Event(res.getLong("incident_id"),
							res.getInt("offense_code"),
							res.getInt("offense_code_extension"), 
							res.getString("offense_type_id"), 
							res.getString("offense_category_id"),
							res.getTimestamp("reported_date").toLocalDateTime(),
							res.getString("incident_address"),
							res.getDouble("geo_lon"),
							res.getDouble("geo_lat"),
							res.getInt("district_id"),
							res.getInt("precinct_id"), 
							res.getString("neighborhood_id"),
							res.getInt("is_crime"),
							res.getInt("is_traffic")));
				} catch (Throwable t) {
					t.printStackTrace();
					System.out.println(res.getInt("id"));
				}
			}
			
			conn.close();
			return list ;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null ;
		}
	}

}
