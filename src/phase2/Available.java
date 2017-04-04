//package phase2;
package cs5530;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashSet;

public class Available 
{
	
	private int thid;
	private int periodID;
	private String sqlStatement;
	
	public boolean AddAvailable(float cost, Statement statement)
	{
		return AddAvailable(cost, statement, -1);
	}
	
	public boolean AddAvailable(float cost, Statement stmt, int thid)
	{
	
		if(thid < 0)
		{
			//We need thid from tempHousing and periodID from period. Get these through 2 sql calls
			sqlStatement = "SELECT thid FROM temphousing ORDER BY thid DESC LIMIT 1;";//Gets thid
		}
		else
		{
			//We need thid from tempHousing and periodID from period. Get these through 2 sql calls
			sqlStatement = "SELECT thid FROM temphousing WHERE thid =" + thid + ";";//Gets thid
		}
		
		ResultSet rs = null;
		try
		{
			rs = stmt.executeQuery(sqlStatement);
			while(rs.next())
			{
				for(int i = 1; i <= rs.getMetaData().getColumnCount(); i++)
				{
					thid = Integer.parseInt(rs.getString(i));
				}
			}
			
		}
		catch(Exception e)
		{
			return false;
		}
		
		
		sqlStatement = "SELECT periodID FROM period ORDER BY periodID DESC LIMIT 1;";//Gets periodID
		rs = null;
		try
		{
			rs = stmt.executeQuery(sqlStatement);
			while(rs.next())
			{
				for(int i = 1; i <= rs.getMetaData().getColumnCount(); i++)
				{
					periodID = Integer.parseInt(rs.getString(i));
				}
			}
			
		}
		catch(Exception e)
		{
			return false;
		}
		
		
		sqlStatement = "INSERT INTO available VALUES("+ thid + "," + periodID + "," + cost + ");";
		int rowsAffected = 0;
		try
		{
			rowsAffected = stmt.executeUpdate(sqlStatement);
			return true;
		}
		catch(Exception e)
		{
			return false;
		}
	}
	
	public HashSet<Integer> THAvailabilityPeriods(int thid, Statement statement, StringBuilder newOutput)
	{
		HashSet<Integer> output = new HashSet<Integer>();
		ResultSet result = null;
		sqlStatement = "SELECT a.periodID, p.fromDate, p.toDate FROM available a, period p WHERE a.thid = " + thid + " AND a.periodID = p.periodID;";
		try
		{
			result = statement.executeQuery(sqlStatement);
			//System.out.println("===============================================================================================");
			newOutput.append("<br/>===============================================================================================<br/>");
			while(result.next())
			{
				for(int i = 1; i <= result.getMetaData().getColumnCount(); i++)
				{
					if(i == 1)
					{
						output.add(result.getInt(i));
						//System.out.println("Period ID: " + result.getString(i));
						newOutput.append("Period ID: " + result.getString(i)+"<br/>");
					}
					else if(i == 2)
					{
						//System.out.print("Start Date " + result.getString(i) + " to ");
						newOutput.append("Start Date " + result.getString(i)+ " to ");
					}
					else if(i == 3)
					{
						//System.out.println("End Date " + result.getString(i) + ".");
						newOutput.append("End Date " + result.getString(i) + ".<br/>");
					}
					//System.out.println(result.getMetaData().getColumnName(i) + ": " + result.getString(i) + " ");
				}
				//System.out.println("===============================================================================================");
				newOutput.append("===============================================================================================<br/>");
			}
		}
		catch(Exception e)
		{
			System.err.println(e.getMessage() + "\n" +  e.getStackTrace());
			System.out.println("Could not obtain period ids.");
		}
		
		return output;
	}
	
	public boolean RemoveDate(int thid, int periodID, Statement statement)
	{
		int woah = 0;
		sqlStatement = "DELETE FROM available WHERE periodID = " + periodID + ";";
		try
		{
			woah = statement.executeUpdate(sqlStatement);
			
			if(woah == 0)
			{
				return false;
			}
			Statement statement2 = statement.getConnection().createStatement();
			sqlStatement = "DELETE FROM period WHERE periodID = " + periodID + ";";
			woah = statement2.executeUpdate(sqlStatement);
			if(woah > 0)
			{
				return true;
			}
			else
			{
				return false;
			}
			
		}
		catch(Exception e)
		{
			System.err.println(e.getMessage() + "\n" +  e.getStackTrace());
			System.out.println("Could not remove date.");
			return false;
		}
	}
}
