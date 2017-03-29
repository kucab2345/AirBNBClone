//package phase2;
package cs5530;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.*;

public class Stays 
{
	private int thid;
	private int periodID;
	private String sqlStatement;
	
	public Stays(String info)
	{
		String[] thidAndPID = info.split("\\s+");
		thid = Integer.parseInt(thidAndPID[0]);
		periodID = Integer.parseInt(thidAndPID[1]);
	}
	
	public boolean AddStay(String login, float payment, Statement statement)
	{
		sqlStatement = "INSERT INTO visit VALUES(\"" + login + "\", " + thid + "," + periodID + "," + payment + ");";
		
		int rowsAffected = 0;
		try
		{
			rowsAffected = statement.executeUpdate(sqlStatement);
			return true;
		}
		catch(Exception e)
		{
			System.err.println(e.getMessage() + "\n" +  e.getStackTrace());
			System.out.println("Cannot execute the query to update visits.");
			return false;
		}
	}
}
