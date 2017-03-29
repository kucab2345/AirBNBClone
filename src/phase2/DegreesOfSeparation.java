//package phase2;
package cs5530;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashSet;

public class DegreesOfSeparation 
{
	String user1;
	String user2;
	String sqlStatement;
	
	public DegreesOfSeparation()
	{
		
	}
	
	public HashSet<String> GetAllUsers(Statement statement)
	{
		HashSet<String> users = new HashSet<String>();
		ResultSet result = null;
		sqlStatement = "SELECT login FROM users;";
		int count = 1;
		try
		{
			result = statement.executeQuery(sqlStatement);
			System.out.println("==================================================================================================");
			while(result.next())
			{
				System.out.println("User " + count + ": " + result.getString(1));
				users.add(result.getString(1));
				count++;
			}
			System.out.println("==================================================================================================");
			return users;
		}
		catch(Exception e)
		{
			System.err.println(e.getMessage() + "\n" +  e.getStackTrace());
			System.out.println("Could not obtain all the users.");
			return null;
		}
	}
	
	public int discoverDegreeOfSeparation(String login1, String login2, Statement statement)
	{
		user1 = login1;
		user2 = login2;
		sqlStatement = "SELECT COUNT(*) FROM favorites f1, favorites f2 WHERE f1.thid = f2.thid AND f1.login = \"" + user1 + "\" AND f2.login = \"" + user2 + "\";";
		ResultSet result = null;
		try
		{
			result = statement.executeQuery(sqlStatement);
			while(result.next())
			{
				if(result.getInt(1) > 0)
				{
					return 1;
				}
			}
			
			sqlStatement = "SELECT COUNT(*) FROM favorites f3, favorites f4 WHERE f3.thid = f4.thid AND f3.login = \"" + user2 + "\" AND f4.login = ANY(SELECT DISTINCT(f2.login) "
					+ "FROM favorites f1, favorites f2 WHERE f1.thid = f2.thid AND f1.login = \"" + user1 + "\" AND f2.login = ANY(SELECT login "
							+ "FROM favorites WHERE login != \"" + user1 + "\" AND login != \"" + user2 + "\"));";
			Statement statement2 = statement.getConnection().createStatement();
			result = statement2.executeQuery(sqlStatement);
			while(result.next())
			{
				if(result.getInt(1) > 0)
				{
					return 2;
				}
			}
			
			return -1;
		}
		catch(Exception e)
		{
			System.err.println(e.getMessage() + "\n" +  e.getStackTrace());
			System.out.println("Could not get degree of separation.");
			return -1;
		}		
	}
}
