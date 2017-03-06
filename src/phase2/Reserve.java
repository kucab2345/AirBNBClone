package phase2;

import java.sql.ResultSet;

import java.sql.Statement;
import java.util.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class Reserve
{
	private String curUser;
	private String curPassword;
	private String sqlStatement;
	private ResultSet currentResult;
	
	public Reserve()
	{
		
	}
	
	public Reserve(String login, String password)
	{
		curUser = login;
		curPassword = password;
		currentResult = null;
	}
	
	public void AddReservation(int requestedTHID, int periodID, Statement statement)
	{
		sqlStatement = "SELECT pricePerNight FROM available WHERE thid = " + requestedTHID + " AND periodID = " + periodID + ";";
		ResultSet result = null;
		float pricePerNight = 10;
		try 
		{
			result = statement.executeQuery(sqlStatement);

			while(result.next())
			{
				for(int i = 1; i <= result.getMetaData().getColumnCount(); i++)
				{
					pricePerNight = Float.parseFloat(result.getString(i));
				}
			}
			
			
			//statement.close();
		}
		catch (Exception e) 
		{
			System.err.println(e.getMessage() + e.getStackTrace());
			System.out.println("Cannot execute the query selecting the price for the requested house and period.");
		}
		
		sqlStatement = "SELECT fromDate, toDate FROM period WHERE periodID = " + periodID + ";";
		 result = null;
		 ResultSet result2 = null;
		 int difference = -1;
		try 
		{
			result = statement.executeQuery(sqlStatement);

			while(result.next())
			{
				result2 = statement.executeQuery("SELECT DATEDIFF( \"" + result.getString(2) + "\", \"" + result.getString(1) + "\");");
				while(result2.next())
				{
					difference = Integer.parseInt(result2.getString(1));
				}
			}
			//statement.close();
		}
		catch (Exception e) 
		{
			System.err.println(e.getMessage() + e.getStackTrace());
			System.out.println("Cannot execute the query where the date is selected.");
		}

		float totalCost = pricePerNight * difference;
		
		sqlStatement = "INSERT INTO reserve VALUES(\"" + curUser + "\" , " + requestedTHID + ", " + periodID + ", " + totalCost + ");";
		
		int resultNum;
		System.out.println("Processing reservation...");
		try 
		{
			resultNum = statement.executeUpdate(sqlStatement);
			System.out.println("Successfully inserted this reservation.");
			//statement.close();
		} 
		catch (Exception e) 
		{
			System.err.println(e.getMessage() + e.getStackTrace());
			System.out.println("Cannot execute the query inserting requested user into reserve table.");
		}
		
		sqlStatement = "DELETE FROM available WHERE thid = " + requestedTHID + " AND periodID = " + periodID + ";";
		try
		{
			resultNum = statement.executeUpdate(sqlStatement);
			
		}
		catch (Exception e) 
		{
			System.err.println(e.getMessage() + e.getStackTrace());
			System.out.println("Cannot execute the query to remove the row from available.");
		}

	}
	
	public HashSet<String> DisplayTempHousesAvailable(Statement statement)
	{
		sqlStatement = "SELECT t.thid, t.category, t.description, t.squareFootage, t.carLimit, t.neighbors "
				+ "FROM temphousing t";
		System.out.println("Retreiving housing information...");
		ResultSet output = null;
		HashSet<String> thids = new HashSet<String>();
		try 
		{
			output = statement.executeQuery(sqlStatement);
			currentResult = output;
			System.out.println("===============================================================================================");
			while(output.next())
			{
				for(int i = 1; i <= output.getMetaData().getColumnCount(); i++)
				{
					if(i == 1)
					{
						thids.add(output.getString(i));
					}
					System.out.println(output.getMetaData().getColumnName(i) + ": " + output.getString(i) + " ");
				}
				System.out.println("===============================================================================================");
				//System.out.println();
			}
			System.out.println("Please select which house you would like to reserve.");
			//statement.close();
			return thids;
		}
		catch (Exception e) 
		{
			System.err.println(e.getMessage() + e.getStackTrace());
			System.out.println("Cannot execute the query.");
			return thids;
		}

	}
	
	public int DisplayAvailableDays(int thid, Statement statement)
	{
		sqlStatement = "SELECT a.periodID, a.pricePerNight, p.fromDate, p.toDate FROM available a, period p WHERE a.periodID = p.periodID AND a.thid = " + thid + ";";
		System.out.println("Requesting information...");
		ResultSet output = null;
		int numAvailable = 0;
		try 
		{
			output = statement.executeQuery(sqlStatement);
			while(output.next())
			{
				numAvailable++;
				for(int i = 1; i <= output.getMetaData().getColumnCount(); i++)
				{
					System.out.print(output.getMetaData().getColumnName(i) + ": " + output.getString(i) + " ");
					
				}
				System.out.println();
			}
			
			return numAvailable;
		}
		catch (Exception e) 
		{
			System.err.println(e.getMessage() + e.getStackTrace());
			System.out.println("Cannot execute the query.");
			return 0;
		}
	}
}
