package com.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.dto.Engineer;
import com.dto.HOD;
import com.exception.EngineerException;
import com.exception.HODException;

public class HodDAOImpl implements HodDAO {

	@Override
	public HOD loginHodDAO(String username, String password) throws HODException, ClassNotFoundException {
		HOD hod = new HOD();

		try (Connection conn = DBUtils.getConnectionToDatabase()) {

			PreparedStatement ps = conn.prepareStatement("SELECT * FROM hod WHERE username = ? AND password=?");
			ps.setString(1, username);
			ps.setString(2, password);
			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				hod.setHodId(rs.getInt("hodId"));
				hod.setName(rs.getString("name"));
				hod.setUserName(rs.getString("username"));
				hod.setPassword(rs.getString("password"));
			} else {
				throw new HODException("Invalid Username or Password.");
			}

		} catch (SQLException e) {
			e.printStackTrace();
			throw new HODException(e.getMessage());
		}

		return hod;
	}
	
	@Override
	public String registerEngineer(String name, String username, String password, String type, String location) throws EngineerException, ClassNotFoundException {
		String res = "";
		
		try(Connection conn = DBUtils.getConnectionToDatabase()) {
			
			PreparedStatement ps = conn.prepareStatement("INSERT INTO Engineer (name,username,password,type,location) values(?,?,?,?,?)");
			
			ps.setString(1, name);
			ps.setString(2, username);
			ps.setString(3, password);
			ps.setString(4, type);
			ps.setString(5, location);
			
			int x = ps.executeUpdate();
			if(x>0) {
				res = "Engineer Registered Sucessfully";
			}else {
				throw new EngineerException("Invalid Entries. Please Try Again Later.");
			}
			
		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
			throw new EngineerException(e.getMessage());
		}
		
		return res;
	}
	
	@Override
	public List<Engineer> getEngineers() throws EngineerException, ClassNotFoundException {
		List<Engineer> engineers = new ArrayList<>();
		
		try(Connection con = DBUtils.getConnectionToDatabase()) {
			
			PreparedStatement ps = con.prepareStatement("SELECT * FROM engineer");
			
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()) {
				Engineer eng = new Engineer();
				
				eng.setEngId(rs.getInt("engId"));
				eng.setName(rs.getString("name"));
				eng.setUserName(rs.getString("username"));
				eng.setPassword(rs.getString("password"));
				eng.setType(rs.getString("type"));
				eng.setLocation(rs.getString("location"));
				
				engineers.add(eng);
				
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return engineers;
	}


}
