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
 

public class Discount extends BASESqlInterface {
	static Discount instance = new Discount();
	private int  m_id;
	private int  m_hotel_id;
	private int  m_fromday;
	private int  m_today;

	public int  getId () {
		return m_id;
	}

	public void  setId (int val) {
		m_id = val;
	}

	public int  getHotelId () {
		return m_hotel_id;
	}

	public void  setHotelId (int val) {
		m_hotel_id = val;
	}

	public int  getFromday () {
		return m_fromday;
	}

	public void  setFromday (int val) {
		m_fromday = val;
	}

	public int  getToday () {
		return m_today;
	}

	public void  setToday (int val) {
		m_today = val;
	}

	 
	 
	public static Discount getInstance() {
		return instance;
	}
	 
	 
 

	public boolean deleteById(Connection conn) {
		
		String stmt = "DELETE FROM Discount WHERE ID = ?";
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
	
	static public List<Discount> fetchWithJoin(Connection conn, String joindAndWhereStr, String ... params) {
		
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT t.id, t.hotel_id, t.fromday, t.today FROM Discount t ");
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
			ArrayList<Discount> list = new ArrayList<Discount>();
			while(rs.next()) {
				Discount obj = instance.getNextRow(rs, 1);
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

 
	static public Discount fetchById(Connection conn, String id) {
		String stmt =  "SELECT t.id, t.hotel_id, t.fromday, t.today FROM Discount t  WHERE ID = ?";
		PreparedStatement cs = null;
		ResultSet rs = null;

		try {
			cs = conn.prepareStatement(stmt);
	        cs.setString(1, id);
            rs = cs.executeQuery();
			Discount newobj = null;
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
		String stmt = "INSERT into Discount(id, hotel_id, fromday, today ) VALUES (?,?,?,?)";
	    try {
	        cs = connection.prepareStatement(stmt, Statement.RETURN_GENERATED_KEYS);
			int idx = 1;
			       	cs.setInt(idx++, m_id);
	    			       	cs.setInt(idx++, m_hotel_id);
	    			       	cs.setInt(idx++, m_fromday);
	    			       	cs.setInt(idx++, m_today);
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
				out.println("hotel_id = " + m_hotel_id);
				out.println("fromday = " + m_fromday);
				out.println("today = " + m_today);
	}
	 public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("id = " + m_id);
		sb.append(";hotel_id = " + m_hotel_id);
		sb.append(";fromday = " + m_fromday);
		sb.append(";today = " + m_today);
		return sb.toString();
    }

 
	public  Discount getNextRow(ResultSet rs, int idx) throws SQLException {
			Discount obj = new Discount();
			 
			obj.m_id =  rs.getInt(idx++);
			obj.m_hotel_id =  rs.getInt(idx++);
			obj.m_fromday =  rs.getInt(idx++);
			obj.m_today =  rs.getInt(idx++);
			 
			return obj;
	}
}


