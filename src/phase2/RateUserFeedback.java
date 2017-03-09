package phase2;

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
		sqlStatement = "SELECT t.thid FROM temphousing t;";
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
					}
					System.out.println("Temporary Housing ID: " + output.getString(i) + " ");
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
	
	public HashSet<Integer> GetAllUsersFeedback(String givenLogin, int givenThid, Statement statement)
	{
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
	
	public boolean RateFeedback(String givenLogin, int rating, int feedbackID, Statement statement)
	{
		login = givenLogin;
		sqlStatement = "INSERT INTO rates VALUES (\"" + login + "\"," + feedbackID + ", " + rating +  ");";//Now() works but it throws a warning since it is also trying to push in the time of date as well as today's date
		int rowsAffected = 0;
		try
		{
			if(thids.contains(thid))
			{
				rowsAffected = statement.executeUpdate(sqlStatement);
				return true;
			}
			else
			{
				System.out.println("You have entered an invalid feedbackID. Did not successfully update feedback rating.");
				return false;
			}
		}
		catch(Exception e)
		{
			System.err.println(e.getMessage() + "\n" + e.getStackTrace());
			return false;
		}	
	}
}
