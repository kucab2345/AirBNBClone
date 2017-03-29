//package phase2;
package cs5530;

import java.sql.ResultSet;

import java.sql.Statement;
import java.util.*;


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
		}
		catch (Exception e) 
		{
			System.err.println(e.getMessage() + e.getStackTrace());
			System.out.println("Cannot execute the query selecting the price for the requested house and period.");
		}
		
		sqlStatement = "SELECT fromDate, toDate FROM period WHERE periodID = " + periodID + ";";
		 ResultSet result2 = null;
		 int difference = -1;
		try 
		{
			result = statement.executeQuery(sqlStatement);
			Statement statement2 = statement.getConnection().createStatement();
			while(result.next())
			{
				result2 = statement2.executeQuery("SELECT DATEDIFF( \"" + result.getString(2) + "\", \"" + result.getString(1) + "\");");
				while(result2.next())
				{
					difference = Integer.parseInt(result2.getString(1));
				}
			}
		}
		catch (Exception e) 
		{
			System.err.println(e.getMessage() + "\n" + e.getStackTrace());
			System.out.println("Cannot execute the query is selected date.");
		}

		float totalCost = pricePerNight * difference;
		
		sqlStatement = "INSERT INTO reserve VALUES(\"" + curUser + "\" , " + requestedTHID + ", " + periodID + ", " + totalCost + ");";
		
		int resultNum;
		System.out.println("Processing reservation...");
		try 
		{
			resultNum = statement.executeUpdate(sqlStatement);
			System.out.println("Successfully inserted this reservation.");
			System.out.println("Your total cost will be: $" + totalCost);
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
		return DisplayTempHousesAvailable("", statement, true);

	}
	
	public HashSet<String> DisplayTempHousesAvailable(String login, Statement statement, boolean all)
	{
		if(all)
		{
			sqlStatement = "SELECT t.thid, t.category, t.description, t.squareFootage, t.carLimit, t.neighbors, t.city, t.state "
					+ "FROM temphousing t;";
		}
		else
		{
			sqlStatement = "SELECT t.thid, t.category, t.description, t.squareFootage, t.carLimit, t.neighbors, t.city, t.state "
					+ "FROM temphousing t WHERE t.login = \"" + login + "\";";
		}
		
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

			}
			if(all)
			{
				System.out.println("Please select which house you would like to reserve.");
			}
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
	
	public HashSet<String> DisplayAllReservationsForUser(String login, Statement statement)
	{
		ResultSet result = null;
		HashSet<String> valid = new HashSet();
		sqlStatement = "SELECT r.thid, r.periodID, r.cost FROM reserve r WHERE r.login = \"" + login + "\";";
		try
		{
			result = statement.executeQuery(sqlStatement);
			System.out.println("===============================================================================================");

			while(result.next())
			{
				String valueToAdd = "";
				for(int i = 1; i <= result.getMetaData().getColumnCount(); i++)
				{
					if(i == 1)
					{
						valueToAdd = result.getString(i);
					}
					else if(i == 2)
					{
						valueToAdd += " " + result.getString(i);
					}
					System.out.println(result.getMetaData().getColumnName(i) + ": " + result.getString(i) + " ");
				}
				valid.add(valueToAdd);
				System.out.println("===============================================================================================");

			}
			return valid;
		}
		catch(Exception e)
		{
			System.err.println(e.getMessage() + "\n" +  e.getStackTrace());
			System.out.println("Cannot execute the query to display reservations available.");
			return valid;
		}

		
	}
	
	public float SelectCostOfReserve(int thid, int givenPeriodID, String login, Statement statement)
	{
		float cost = -1;
		ResultSet result = null;
		sqlStatement = "SELECT r.cost FROM reserve r WHERE r.thid = " + thid + " AND r.login = \"" + login + "\" AND r.periodID = " + givenPeriodID + ";";
		try
		{
			result = statement.executeQuery(sqlStatement);
			while(result.next())
			{
				for(int i = 1; i <= result.getMetaData().getColumnCount(); i++)
				{
					cost = Float.parseFloat(result.getString(i));
				}
			}
			return cost;

		}
		catch(Exception e)
		{
			System.err.println(e.getMessage() + "\n" +  e.getStackTrace());
			System.out.println("Cannot execute the query to obtain cost of requested thid.");
			return cost;
		}
		
	}
	
	public int SelectPeriodIDOfReserve(int thid, String login, Statement statement)
	{
		int periodid = -1;
		ResultSet result = null;
		sqlStatement = "SELECT r.periodID FROM reserve r WHERE r.thid = " + thid + " AND r.login = \"" + login + "\";";
		try
		{
			result = statement.executeQuery(sqlStatement);
			while(result.next())
			{
				for(int i = 1; i <= result.getMetaData().getColumnCount(); i++)
				{
					periodid = Integer.parseInt(result.getString(i));
				}
			}
			return periodid;

		}
		catch(Exception e)
		{
			System.err.println(e.getMessage() + "\n" +  e.getStackTrace());
			System.out.println("Cannot execute the query to obtain periodid of requested thid.");
			return periodid;
		}
		
	}
	
	public boolean RemoveReservation(String login, int thid, int periodID, Statement statement)
	{
		sqlStatement = "DELETE FROM reserve WHERE login = \"" + login + "\" AND thid = " + thid + " AND periodID = " + periodID + ";";
		
		int rowsAffected = 0;
		try
		{
			rowsAffected = statement.executeUpdate(sqlStatement);
			if(rowsAffected > 0)
			{
				return true;

			}
			else
			{
				System.err.println("Did not remove entry desired from reserve table.");

				return false;
			}
		}
		catch(Exception e)
		{
			System.err.println(e.getMessage() + "\n" +  e.getStackTrace());
			System.out.println("Cannot execute the query to update visits.");
			return false;
		}
		
	}
	
	public String GetDates(int desiredPID, Statement statement)
	{
		String info = "";
		sqlStatement = "SELECT fromDate, toDate FROM period WHERE periodID = " + desiredPID + ";";
		ResultSet result = null;
		try
		{
			result = statement.executeQuery(sqlStatement);
			while(result.next())
			{
				for(int i = 1; i <= result.getMetaData().getColumnCount(); i++)
				{
					if(i == 1)
					{
						info += result.getString(i);
					}
					else if(i == 2)
					{
						info += " until " + result.getString(i) + ".";
					}
				}
			}
		}
		catch(Exception e)
		{
			System.err.println(e.getMessage() + "\n" +  e.getStackTrace());
			System.out.println("Cannot execute the query to get dates.");
		}
		return info;
	}
	
	public String GetHouseInformation(int desiredTHID, Statement statement)
	{
		String info = "";
		sqlStatement = "SELECT * FROM temphousing WHERE thid = " + desiredTHID + ";";
		ResultSet result = null;
		try
		{
			result = statement.executeQuery(sqlStatement);
			while(result.next())
			{
				for(int i = 1; i <= result.getMetaData().getColumnCount(); i++)
				{
					if(i == 1)
					{
						info += result.getString(i);
					}
					else if(i == 2)
					{
						info += " of category type: " + result.getString(i);
					}
					else if(i == 3)
					{
						info += " with the description: \"" + result.getString(i) + "\".";
					}
					else if(i == 4)
					{
						info += "\n  It has this square footage of space: " + result.getString(i);
					}
					else if(i == 5)
					{
						info += " and this many cars allowed: " + result.getString(i);
					}
					else if(i == 6)
					{
						if(result.getBoolean(i))
						{
							info += ".\n  There are neighbors with this temporary housing.";
						}
						else
						{
							info += ".\n  There are no neighbors with this temporary housing.";
						}
						
					}
					else if(i == 7)
					{
						info += "\n  This place is owned by the user " + result.getString(i);
					}
					else if(i == 8)
					{
						info += " and located in " + result.getString(i);
					}
					else if(i == 9)
					{
						info += ", " + result.getString(i) + ".";
					}
				}
			}
		}
		catch(Exception e)
		{
			System.err.println(e.getMessage() + "\n" +  e.getStackTrace());
			System.out.println("Cannot execute the query to get house info.");
		}
		return info;
	}
}
