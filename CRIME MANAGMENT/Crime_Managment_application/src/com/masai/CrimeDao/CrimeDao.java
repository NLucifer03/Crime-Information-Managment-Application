package com.masai.CrimeDao;

import java.util.List;

import com.masai.Bean.Crime;
import com.masai.Exceptions.CrimeException;

public interface CrimeDao {
//--------------------------------------------- add crime details -----------------------------------------------
	
	String AddCrimeDetails(Crime c);
	
//	------------------------------------------- List of all crimes ----------------------------------------------
	
	List<Crime> getAllCrimes() throws CrimeException;
	
//	---------------Delete crime details based on crime_id from crime and crime_by_criminal table------------------
	
	String deleteCrimeDetails(int id) throws CrimeException;
	
//  ----------------------- Delete crime details based on crime_id from crime table ------------------------------
	
	String deleteIvalidDetails(int id);
	
	
}
