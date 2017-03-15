package phase2;

import java.sql.ResultSet;
import java.sql.Statement;

public class DegreesOfSeparation 
{
	String user1;
	String user2;
	String sqlStatement;
	
	public DegreesOfSeparation(String login1, String login2)
	{
		user1 = login1;
		user2 = login2;
	}
	
	public int discoverDegreeOfSeparation(Statement statement)
	{
		int degrees = 0;
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
			
			sqlStatement = "";  //TODO: Find users that are 1 degree away from the two given, then if there exists one they are two apart otherwise return -1 as they aren't close enough
			Statement statement2 = statement.getConnection().createStatement();
			result = statement2.executeQuery(sqlStatement);
		}
		catch(Exception e)
		{
			System.err.println(e.getMessage() + "\n" +  e.getStackTrace());
			System.out.println("Could not get degree of separation.");
		}
		
		return degrees;
	}
}
