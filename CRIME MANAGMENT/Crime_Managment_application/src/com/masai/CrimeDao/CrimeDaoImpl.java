package com.masai.CrimeDao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.masai.Bean.Crime;
import com.masai.Exceptions.CrimeException;
import com.masai.utility.DB_Connection;

public class CrimeDaoImpl implements CrimeDao{

//	-------------------------------------------------------------Add crime details-----------------------------------------------------------
	
	@Override
	public String AddCrimeDetails(Crime c) {
//		--------------- Initialize String -------------------------
		String message = "failed to insert details.....";
		
		
		
//		---------creating the connection between database-------------
		try(Connection con = DB_Connection.getconnection())
		{
			
// 		-------------SQL query to insert the data into the crime table----------------		
			PreparedStatement ps = con.prepareStatement("insert into Crime (Name_of_crime, Victims, Detailed_des,Date,Police_station_name,suspected,status) values(?,?,?,?,?,?,?);");
			
			ps.setString(1, c.getName());
			ps.setInt(2, c.getVictims());
			ps.setString(3, c.getDetails());
			ps.setString(4, c.getDate());
			ps.setString(5, c.getPolice());
			ps.setString(6, c.getSuspected());
			ps.setString(7, c.getStatus());
			
			int update = ps.executeUpdate();
			
			if(update > 0)
			{
				message = "details are successfully inserted";
			}
		
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			message = e.getMessage();
		}
		
		
		return message;
	}

	
	
//	--------------------------------------------------------get all list of crimes----------------------------------------------------------
	
	
	
	@Override
	public List<Crime> getAllCrimes() throws CrimeException {
		
//		------------Initializing List----------------------
		
		List<Crime> list = new ArrayList<>();
		
//		------------connection to the data base----------------
		
		try(Connection con = DB_Connection.getconnection())
		{
			PreparedStatement ps = con.prepareStatement("select * from Crime;");
			
			ResultSet rs = ps.executeQuery();
			
			while(rs.next())
			{
				
				int id = rs.getInt("Crime_id");
				String name = rs.getString("Name_of_crime");
				int vic = rs.getInt("victims");
				String des = rs.getString("detailed_des");
				String date = rs.getString("date");
				String police = rs.getString("police_station_name");
				String sus = rs.getString("Suspected");
				String status = rs.getString("Status");
				
//				---------creating crime object and add it to list--------------
				
				Crime c = new Crime(id,name,vic,des,date,police,sus,status);
				list.add(c);
				
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw new CrimeException(e.getMessage());
		}
		
//		----------------check if list is empty then throw exception---------------
		
		if(list.isEmpty())
		{
			throw new CrimeException();
		}
		
		
		return list;
		
	}

//	-------------------------------------------Delete crime details based on crime_id-------------------------------------------------------

//	--> this method is used to delete the crime details which is register into crime table as well as crime_by_criminal table
	
	@Override
	public String deleteCrimeDetails(int id) throws CrimeException {
		String message = "crime Id " + id + " is not a valid id into crime talbe.";
		
//		-----------------getting the DB connection-------------------------------
		
		try(Connection con = DB_Connection.getconnection())
		{
			
//			----------------------first need to delete from crime_by_criminal table-------------------
			
			PreparedStatement ps1 = con.prepareStatement("Delete from crime_by_criminal where crime_id = ?;");
			
			ps1.setInt(1, id);
			
			int status = ps1.executeUpdate();
			
//			---------------checking that details are deleted then delete from crime table----------------
			
			if(status > 0)
				
			{
			
				PreparedStatement ps = con.prepareStatement("Delete from crime where crime_id = ?;");
				
				ps.setInt(1, id);
				
				int delete = ps.executeUpdate();
				
				if(delete > 0)
				{
					message = "Crime details of crime_id " + id + " deleted successfully";
					
					PreparedStatement ps3 = con.prepareStatement("alter table crime auto_increment = ?;");
					ps3.setInt(1, id);
					
					int statusAM = ps3.executeUpdate();
					
					if(statusAM >= 0)
					{
						System.out.println("AutoIncrement is modified");
					}
					else
					{
						System.out.println("AutoIncrement Modification failed");
					}
					
				}
		
			}	
//			--------------if id is not valid then throw the exception--------------------
			
			else
			{
				throw new CrimeException("Crime Id " + id + " is not a valid id into Crime_by_criminal table. you can use DeleteIvalidDetails method");
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			message = e.getMessage();
		}
		
		
		return message;
	}

//---------------------------------------------Delete invalid Detailed from Crime table------------------------------------------------------

//  --> this method is used to delete the crime details which is not registered into the crime_by_criminal tab

@Override
public String deleteIvalidDetails(int id) {
	
//	---------------Initiating the String to return -----------------
	
	String message = "Invalid crime_id " + id;
	
//	---------------establishing the database connection------------------
	
	try(Connection con = DB_Connection.getconnection())
	{
		PreparedStatement ps = con.prepareStatement("delete from crime where crime_id = ?;");
		ps.setInt(1, id);
		int status = ps.executeUpdate();
		
		if(status > 0)
		{
			
			message = "Details of crime id " + id + " is successfully deleted ";
			
			PreparedStatement ps3 = con.prepareStatement("alter table crime auto_increment = ?;");
			ps3.setInt(1, id);
			
			int statusAM = ps3.executeUpdate();
			
			if(statusAM >= 0)
			{
				System.out.println("AutoIncrement is modified");
			}
			else
			{
				System.out.println("AutoIncrement Modification failed");
			}
			
		}
		
	} catch (SQLException e) {
		
		message = e.getMessage();
		
	}
	
	
	return message;
	
}

}




	
	


