package phase2;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;
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
				for(int i = 0; i < result.getMetaData().getColumnCount(); i++)
				{
					pricePerNight = Integer.parseInt(result.getString(i));
				}
			}
			//statement.close();
		}
		catch (Exception e) 
		{
			System.err.println(e.getMessage() + e.getStackTrace());
			System.out.println("Cannot execute the query.");
		}
		
		sqlStatement = "SELECT fromDate, toDate FROM period WHERE periodID = " + periodID + ";";
		 result = null;
		 Date fromDate = null;
		 Date toDate = null;
		try 
		{
			result = statement.executeQuery(sqlStatement);

			while(result.next())
			{
				DateFormat format = new SimpleDateFormat("MMMM d, yyyy", Locale.ENGLISH);
				fromDate = format.parse(result.getString(0));
				toDate = format.parse(result.getString(1));
			}
			//statement.close();
		}
		catch (Exception e) 
		{
			System.err.println(e.getMessage() + e.getStackTrace());
			System.out.println("Cannot execute the query.");
		}

		TimeUnit timeUnit = TimeUnit.DAYS;
		long diffInTime = toDate.getTime() - fromDate.getTime();
		long numDays = timeUnit.convert(diffInTime, TimeUnit.MILLISECONDS);
		float totalCost = pricePerNight * numDays;
		
		sqlStatement = "INSERT INTO reserve VALUES(\"" + curUser + "\" , " + requestedTHID + ", " + periodID + ", " + totalCost + ");";
		
		int resultNum;
		System.out.println("Executing " + sqlStatement);
		try 
		{
			resultNum = statement.executeUpdate(sqlStatement);
			System.out.println("Successfully inserted this reservation.");
			//statement.close();
		} catch (Exception e) 
		{
			System.err.println(e.getMessage() + e.getStackTrace());
			System.out.println("Cannot execute the query.");
		}

	}
	
	public void DisplayTempHousesAvailable(Statement statement)
	{
		sqlStatement = "SELECT t.thid, t.category, t.description, t.squareFootage, t.carLimit, t.neighbors "
				+ "FROM temphousing t";
		System.out.println("Executing " + sqlStatement);
		ResultSet output = null;
		try 
		{
			output = statement.executeQuery(sqlStatement);
			currentResult = output;
			while(output.next())
			{
				for(int i = 0; i < output.getMetaData().getColumnCount(); i++)
				{
					System.out.print(output.getMetaData().getColumnName(i) + ": " + output.getString(i) + " ");
				}
				System.out.println();
			}
			System.out.println("Please select which house you would like to reserve.");
			//statement.close();
		}
		catch (Exception e) 
		{
			System.err.println(e.getMessage() + e.getStackTrace());
			System.out.println("Cannot execute the query.");
		}

	}
	
	public void DisplayAvailableDays(int thid, Statement statement)
	{
		sqlStatement = "SELECT a.periodID, a.pricePerNight, p.fromDate, p.toDate FROM available a, period p WHERE a.periodID = p.periodID AND a.thid = " + thid + ";";
		System.out.println("Executing " + sqlStatement);
		ResultSet output = null;
		try 
		{
			output = statement.executeQuery(sqlStatement);
			//currentResult = output;
			while(output.next())
			{
				for(int i = 0; i < output.getMetaData().getColumnCount(); i++)
				{
					System.out.print(output.getMetaData().getColumnName(i) + ": " + output.getString(i) + " ");
				}
				System.out.println();
			}
			
			//statement.close();
		}
		catch (Exception e) 
		{
			System.err.println(e.getMessage() + e.getStackTrace());
			System.out.println("Cannot execute the query.");
		}
	}
}
