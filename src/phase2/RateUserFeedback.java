//package phase2;
package cs5530;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashSet;

public class RateUserFeedback 
{
	private String sqlStatement;
	private String login;
	private int thid;
	
	private HashSet<Integer> thids = new HashSet<Integer>();
	private HashSet<Integer> feedbacks = new HashSet<Integer>();
	
	public RateUserFeedback() //String givenLogin, int givenThid
	{
		//login = givenLogin;
		//thid = givenThid;
	}
	
	public HashSet<Integer> DisplayTempHouseTHIDS(Statement statement)
	{
		
		sqlStatement = "SELECT t.thid, COUNT(feedbackID) FROM temphousing t, feedback f WHERE f.thid = t.thid GROUP BY t.thid ORDER BY t.thid;";
		thids = new HashSet<Integer>();
		
		System.out.println("Retreiving housing information...");
		ResultSet output = null;
		try 
		{
			output = statement.executeQuery(sqlStatement);
			System.out.println("===============================================================================================");
			while(output.next())
			{
				for(int i = 1; i <= output.getMetaData().getColumnCount(); i++)
				{
					if(i == 1)
					{
						thids.add(Integer.parseInt(output.getString(i)));

						System.out.println("Temporary Housing ID: " + output.getString(i) + " ");
					}
					else if(i == 2)
					{
						System.out.println("with this amount of feedbacks available: " + output.getInt(i));
					}
				}
				System.out.println("===============================================================================================");
				//System.out.println();
			}
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
	public void DisplayTempHouseTHIDS(Statement statement, StringBuilder sb)
	{
		sqlStatement = "SELECT t.thid, COUNT(feedbackID) FROM temphousing t, feedback f WHERE f.thid = t.thid GROUP BY t.thid ORDER BY t.thid;";

		System.out.println("Retreiving housing information...");
		ResultSet output = null;
		try 
		{
			output = statement.executeQuery(sqlStatement);
			System.out.println("===============================================================================================");
			while(output.next())
			{
				for(int i = 1; i <= output.getMetaData().getColumnCount(); i++)
				{
					if(i == 1)
					{
						thids.add(Integer.parseInt(output.getString(i)));
						System.out.println("Temporary Housing ID: " + output.getString(i) + " ");
						sb.append("Temporary Housing ID: " + output.getString(i) + " ");
					}
					else if(i == 2)
					{
						System.out.println("with this amount of feedbacks available: " + output.getInt(i));
						sb.append("with this amount of feedbacks available: " + output.getInt(i) + "\n");
					}
				}
				System.out.println("===============================================================================================");
				//System.out.println();
			}
			//statement.close();
		}
		catch (Exception e) 
		{
			System.err.println(e.getMessage() + e.getStackTrace());
			System.out.println("Cannot execute the query.");
		}

	}
	
	public HashSet<Integer> GetAllUsersFeedback(String givenLogin, String givenThidS, Statement statement)
	{
		int givenThid = Integer.parseInt(givenThidS);
		login = givenLogin;
		thid = givenThid;
		feedbacks = new HashSet<Integer>();
		sqlStatement = "SELECT f.feedbackID, f.feedbackText, f.login, f.feedbackKeyWords, f.starRating FROM feedback f WHERE f.thid = " + thid + " AND f.login != \"" + login + "\";";
		ResultSet result = null;
		try
		{
			result = statement.executeQuery(sqlStatement);
			while(result.next())
			{
				System.out.println("===============================================================================================");
				for(int i = 1; i <= result.getMetaData().getColumnCount(); i++)
				{
					System.out.println(result.getMetaData().getColumnName(i) + ": " + result.getString(i) + " ");
					if(i == 1)
					{
						feedbacks.add(Integer.parseInt(result.getString(i)));
					}
				}
				System.out.println("===============================================================================================");
			}
			
			return feedbacks;
		}
		catch(Exception e)
		{
			System.err.println(e.getMessage() + e.getStackTrace());
			System.out.println("Cannot execute the query.");
			return feedbacks;
		}
		
	}
	public void GetAllUsersFeedback(String givenLogin, String givenThidS, Statement statement, StringBuilder sb)
	{
		int givenThid = Integer.parseInt(givenThidS);
		login = givenLogin;
		thid = givenThid;
		sqlStatement = "SELECT f.feedbackID, f.feedbackText, f.login, f.feedbackKeyWords, f.starRating FROM feedback f WHERE f.thid = " + thid + " AND f.login != \"" + login + "\";";
		ResultSet result = null;
		try
		{
			result = statement.executeQuery(sqlStatement);
			while(result.next())
			{
				System.out.println("===============================================================================================");
				for(int i = 1; i <= result.getMetaData().getColumnCount(); i++)
				{
					System.out.println(result.getMetaData().getColumnName(i) + ": " + result.getString(i) + " ");
					sb.append(result.getMetaData().getColumnName(i) + ": " + result.getString(i) + " \n");
				}
				System.out.println("===============================================================================================");
				sb.append("===============================================================================================\n");
			}
		}
		catch(Exception e)
		{
			System.err.println(e.getMessage() + e.getStackTrace());
			System.out.println("Cannot execute the query.");
		}
		
	}
	
	public boolean RateFeedback(String givenLogin, String ratingS, String feedbackIDS, Statement statement)
	{
		int rating = Integer.parseInt(ratingS);
		int feedbackID = Integer.parseInt(feedbackIDS);
		login = givenLogin;
		sqlStatement = "INSERT INTO rates VALUES (\"" + login + "\"," + feedbackID + ", " + rating +  ");";//Now() works but it throws a warning since it is also trying to push in the time of date as well as today's date
		int rowsAffected = 0;
		try
		{
			rowsAffected = statement.executeUpdate(sqlStatement);
			return true;
		}
		catch(Exception e)
		{
			System.err.println(e.getMessage() + "\n" + e.getStackTrace());
			return false;
		}	
	}
}
