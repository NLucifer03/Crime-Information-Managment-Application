package com.masai.crimeUseCase;

import java.util.Scanner;

import com.masai.CrimeDao.CrimeDao;
import com.masai.CrimeDao.CrimeDaoImpl;

public class DeleteIvalidDetails {

	public static void main(String[] args) {
		
//		------------------------ Scanner class to take crime_id from admin------------------------------
		
		Scanner s1 = new Scanner(System.in);
		System.out.println("Enter Crime_id to delete crime details");
		int id = s1.nextInt();
		
//		----------------------- Creating CrimeDao object to call method --------------------------------
		
		CrimeDao cd = new CrimeDaoImpl();
		String status = cd.deleteIvalidDetails(id);
		System.out.println(status);

	}

}
