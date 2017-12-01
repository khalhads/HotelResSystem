package com.MyHotel.reservation;


import java.io.PrintStream;
import java.sql.CallableStatement;
import java.sql.Statement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.sql.Timestamp;
import java.util.Date;
 

public class Reservation extends BASESqlInterface {
	static Reservation instance = new Reservation();
	private int  m_id;
	private int  m_room_option_id;
	private int  m_breakfast_option_id;
	private int  m_service_option_id;
	private int  m_customer_id;
	private int  m_creditcard_id;
	private float  m_total_cost;
	private String  m_checkin;
	private String  m_checkout;

	public int  getId () {
		return m_id;
	}

	public void  setId (int val) {
		m_id = val;
	}

	public int  getRoomOptionId () {
		return m_room_option_id;
	}

	public void  setRoomOptionId (int val) {
		m_room_option_id = val;
	}

	public int  getBreakfastOptionId () {
		return m_breakfast_option_id;
	}

	public void  setBreakfastOptionId (int val) {
		m_breakfast_option_id = val;
	}

	public int  getServiceOptionId () {
		return m_service_option_id;
	}

	public void  setServiceOptionId (int val) {
		m_service_option_id = val;
	}

	public int  getCustomerId () {
		return m_customer_id;
	}

	public void  setCustomerId (int val) {
		m_customer_id = val;
	}

	public int  getCreditcardId () {
		return m_creditcard_id;
	}

	public void  setCreditcardId (int val) {
		m_creditcard_id = val;
	}

	public float  getTotalCost () {
		return m_total_cost;
	}

	public void  setTotalCost (float val) {
		m_total_cost = val;
	}

	public String  getCheckin () {
		return m_checkin;
	}

	public void  setCheckin (String val) {
		m_checkin = val;
	}

	public String  getCheckout () {
		return m_checkout;
	}

	public void  setCheckout (String val) {
		m_checkout = val;
	}

	 
	 
	public static Reservation getInstance() {
		return instance;
	}
	 
	 
 

	public boolean deleteById(Connection conn) {
		
		String stmt = "DELETE FROM Reservation WHERE ID = ?";
		CallableStatement cs = null;
	 
		try {
			cs = conn.prepareCall(stmt);

	        cs.setInt(1, m_id);
 
			cs.execute();
			return true;
		} catch (Exception e) {
			System.err.println("Failed to execute: [" + stmt + "], exception: " + e);
			return false;
		} finally {
			closeJdbcResources(null, cs, null);
		}
	}
	
	static public List<Reservation> fetchWithJoin(Connection conn, String joindAndWhereStr, String ... params) {
		
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT t.id, t.room_option_id, t.breakfast_option_id, t.service_option_id, t.customer_id, t.creditcard_id, t.total_cost, t.checkin, t.checkout FROM Reservation t ");
		sb.append(joindAndWhereStr );
		
		String stmt = sb.toString();
		PreparedStatement cs = null;
		ResultSet rs = null;

		try {
			cs = conn.prepareStatement(stmt);
        	if(params.length > 0) {
				int idx = 1;
				for(String arg : params)
					cs.setString(idx++, arg);
			}
			rs = cs.executeQuery();
			ArrayList<Reservation> list = new ArrayList<Reservation>();
			while(rs.next()) {
				Reservation obj = instance.getNextRow(rs, 1);
				// obj.printRow(System.out);
				list.add(obj);
			}
			return list;
		} catch (Exception e) {
			System.err.println("Failed to execute: [" + stmt + "], exception: " + e);
			return null;
		}  finally {
			closeJdbcResources(null, cs, null);
		}
	}

 
	static public Reservation fetchById(Connection conn, String id) {
		String stmt =  "SELECT t.id, t.room_option_id, t.breakfast_option_id, t.service_option_id, t.customer_id, t.creditcard_id, t.total_cost, t.checkin, t.checkout FROM Reservation t  WHERE ID = ?";
		PreparedStatement cs = null;
		ResultSet rs = null;

		try {
			cs = conn.prepareStatement(stmt);
	        cs.setString(1, id);
            rs = cs.executeQuery();
			Reservation newobj = null;
            if(rs.next()) {
            	newobj = instance.getNextRow(rs, 1);
            }
			return newobj;
		} catch (Exception e) {
			System.err.println("Failed to execute: [" + stmt + "], exception: " + e);
			return null;
		} finally {
			closeJdbcResources(null, cs, rs);
		}
	}
	 
	 
	public  int insertRecord(Connection connection) throws SQLException {
	    PreparedStatement cs = null;
		String stmt = "INSERT into Reservation(id, room_option_id, breakfast_option_id, service_option_id, customer_id, creditcard_id, total_cost, checkin, checkout ) VALUES (?,?,?,?,?,?,?,?,?)";
	    try {
	        cs = connection.prepareStatement(stmt, Statement.RETURN_GENERATED_KEYS);
			int idx = 1;
			       	cs.setInt(idx++, m_id);
	    			       	cs.setInt(idx++, m_room_option_id);
	    			       	cs.setInt(idx++, m_breakfast_option_id);
	    			       	cs.setInt(idx++, m_service_option_id);
	    			       	cs.setInt(idx++, m_customer_id);
	    			       	cs.setInt(idx++, m_creditcard_id);
	    			       	cs.setFloat(idx++, m_total_cost);
	    			       	cs.setString(idx++, m_checkin);
	    			       	cs.setString(idx++, m_checkout);
	    		 	cs.executeUpdate();
		 	int autoIncKeyFromApi = -1;

		    ResultSet rs = cs.getGeneratedKeys();
		
		    if (rs != null && rs.next()) {
		        autoIncKeyFromApi = rs.getInt(1);
		    } else {
		
		        System.err.println("Failed to execute: retrieve GeneratedKeys ");
		        autoIncKeyFromApi = 0;
		    }
			return autoIncKeyFromApi;
	    } catch (Exception e) {
			System.err.println("Failed to execute: [" + stmt + "], exception: " + e);
			return -1;
	    } finally {
	        closeJdbcResources(null, cs, null);
	    }
	}
 
	public void printRow(PrintStream out) throws SQLException {
				out.println("id = " + m_id);
				out.println("room_option_id = " + m_room_option_id);
				out.println("breakfast_option_id = " + m_breakfast_option_id);
				out.println("service_option_id = " + m_service_option_id);
				out.println("customer_id = " + m_customer_id);
				out.println("creditcard_id = " + m_creditcard_id);
				out.println("total_cost = " + m_total_cost);
				out.println("checkin = " + m_checkin);
				out.println("checkout = " + m_checkout);
	}
	 public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("id = " + m_id);
		sb.append(";room_option_id = " + m_room_option_id);
		sb.append(";breakfast_option_id = " + m_breakfast_option_id);
		sb.append(";service_option_id = " + m_service_option_id);
		sb.append(";customer_id = " + m_customer_id);
		sb.append(";creditcard_id = " + m_creditcard_id);
		sb.append(";total_cost = " + m_total_cost);
		sb.append(";checkin = " + m_checkin);
		sb.append(";checkout = " + m_checkout);
		return sb.toString();
    }

 
	public  Reservation getNextRow(ResultSet rs, int idx) throws SQLException {
			Reservation obj = new Reservation();
			 
			obj.m_id =  rs.getInt(idx++);
			obj.m_room_option_id =  rs.getInt(idx++);
			obj.m_breakfast_option_id =  rs.getInt(idx++);
			obj.m_service_option_id =  rs.getInt(idx++);
			obj.m_customer_id =  rs.getInt(idx++);
			obj.m_creditcard_id =  rs.getInt(idx++);
			obj.m_total_cost =  rs.getFloat(idx++);
			obj.m_checkin =  rs.getString(idx++);
			obj.m_checkout =  rs.getString(idx++);
			 
			return obj;
	}
}


