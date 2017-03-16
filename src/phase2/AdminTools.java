package phase2;
import java.sql.ResultSet;
import java.sql.Statement;


public class AdminTools 
{
	private String sqlStatement;
	private ResultSet result;
	
	public boolean displayMostTrustedUsers(int limit, Statement statement)
	{
		//int limitCount = 0;
		sqlStatement = "SELECT t.loginTrusted, SUM(t.isTrusted = 1) - SUM(t.isTrusted = 0) AS TrustRating FROM trusted t GROUP BY t.loginTrusted ORDER BY TrustRating DESC LIMIT " + limit +";";
		try 
		{
			result = statement.executeQuery(sqlStatement);
			//&& limitCount < limit
			while(result.next())
			{
				for(int i = 1; i <= result.getMetaData().getColumnCount(); i++)
				{
					System.out.println(result.getMetaData().getColumnName(i) + ": " + result.getString(i) + " ");
				}
				System.out.println();
				//limitCount+= 1;
			}
			return true;
		}
		catch(Exception e)
		{
			return false;
		}
	}
	public boolean displayMostUsefulUsers(int limit, Statement statement)
	{
		//int limitCount = 0;
		sqlStatement = "SELECT u.login, AVG(r.rating) AS Usefulness FROM users u, rates r WHERE (u.login = r.login) GROUP BY u.login ORDER BY AVG(r.rating) DESC LIMIT " + limit + ";";	
		try 
		{
			result = statement.executeQuery(sqlStatement);
			// && limitCount < limit
			while(result.next())
			{
				for(int i = 1; i <= result.getMetaData().getColumnCount(); i++)
				{
					System.out.println(result.getMetaData().getColumnName(i) + ": " + result.getString(i) + " ");
				}
				System.out.println();
				//limitCount+= 1;
			}
			return true;
		}
		catch(Exception e)
		{
			return false;
		}
	}
}
