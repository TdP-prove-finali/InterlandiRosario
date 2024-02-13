package it.polito.s284166.Tesi.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


import it.polito.s284166.Tesi.model.Veicolo;

public class macchineDAO {
	
	public List<String> getColor(){
		String sql = "SELECT DISTINCT color\r\n "
				+ "FROM macchine";
		List<String> result = new ArrayList<String>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			
			
			ResultSet res = st.executeQuery();
			while (res.next()) {
				
				String color = res.getString("color");
				result.add(color);
			}
			conn.close();
			return result;

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public List<String> getFuelType(){
		String sql = "SELECT DISTINCT Fuel_Type\r\n "
				+ "FROM macchine";
		List<String> result = new ArrayList<String>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {
				
				String fuelType = res.getString("Fuel_Type");
				result.add(fuelType);
			}
			conn.close();
			return result;

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public List<Veicolo> applyFilter(Integer kilometer,Integer price, String fuel, String color, String ownerType, String marca){
	    StringBuilder sqlBuilder = new StringBuilder("SELECT * FROM macchine WHERE");

	    List<Object> parametriPresenti = new ArrayList<>();

	    if (kilometer != null) {
	        sqlBuilder.append(" Kilometer <= ? AND");
	        parametriPresenti.add(kilometer);
	    }
	    if (price != null) {
	        sqlBuilder.append(" Price <= ? AND");
	        parametriPresenti.add(price);
	    }
	    if (fuel != null) {
	        sqlBuilder.append(" Fuel_Type = ? AND");
	        parametriPresenti.add(fuel);
	    }
	    if (color != null) {
	        sqlBuilder.append(" Color = ? AND");
	        parametriPresenti.add(color);
	    }
	    
	    if (ownerType != null) {
	    	sqlBuilder.append(" Owner = ? AND");
	    	parametriPresenti.add(ownerType);
	    }
	    if (marca != null) {
	    	sqlBuilder.append(" Make = ? AND");
	    	parametriPresenti.add(marca);
	    }

	    if (sqlBuilder.toString().endsWith("AND")) {
	        sqlBuilder.setLength(sqlBuilder.length() - 3);
	    }
	    if (sqlBuilder.toString().endsWith("WHERE")) {
	    	sqlBuilder.setLength(sqlBuilder.length()-5);
	    }

	    String sql = sqlBuilder.toString();
		List<Veicolo> result = new ArrayList<Veicolo>();
		Connection conn = DBConnect.getConnection();
		
		try {
			PreparedStatement st = conn.prepareStatement(sql);
	        for (int i = 0; i < parametriPresenti.size(); i++) {
	            st.setObject(i + 1, parametriPresenti.get(i));
	        }
	        ResultSet res = st.executeQuery();
	        while (res.next()) {
	        	Veicolo veicolo = new Veicolo(res.getInt("id"), res.getString("Make"), res.getString("Model"), res.getInt("Price"), res.getInt("Year"), res.getInt("Kilometer"), res.getString("Fuel_Type"),res.getString("Transmission"),res.getString("Location"), res.getString("Color"), res.getString("Owner"), res.getString("Seller_Type"), res.getInt("Engine"), res.getInt("bhp"),res.getInt("rpm"), res.getString("Drivetrain"), res.getDouble("Length"), res.getDouble("Width"), res.getDouble("Height"), res.getInt("Seating_Capacity"),res.getInt("Fuel_Tank_Capacity"));
	        	result.add(veicolo);
	        }
			conn.close();
			return result;
	
		}catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public List<String> getMarcaType() {
		String sql = "SELECT DISTINCT Make\r\n "
				+ "FROM macchine";
		List<String> result = new ArrayList<String>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {
				
				String make = res.getString("Make");
				result.add(make);
			}
			conn.close();
			return result;

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		
	}
	
}
