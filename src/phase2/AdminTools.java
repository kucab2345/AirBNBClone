package phase2;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.*;

public class AdminTools 
{
	private String sqlStatement;
	private ResultSet result;
	
	public boolean displayMostTrustedUsers(int limit, Statement stmt)
	{
		int limitCount = 0;
		sqlStatement = "Select t.loginTrusted, Sum(t.isTrusted = 1) - Sum(t.isTrusted = 0) as TrustRating from trusted t group by t.loginTrusted order by Sum(t.isTrusted = 1) - Sum(t.isTrusted = 0) DESC;";
		try 
		{
			result = stmt.executeQuery(sqlStatement);

			while(result.next() && limitCount < limit)
			{
				for(int i = 1; i <= result.getMetaData().getColumnCount(); i++)
				{
					System.out.println(result.getMetaData().getColumnName(i) + ": " + result.getString(i) + " ");
				}
				System.out.println();
				limitCount+= 1;
			}
			return true;
		}
		catch(Exception e)
		{
			return false;
		}
	}
	public boolean displayMostUsefulUsers(int limit, Statement stmt)
	{
		int limitCount = 0;
		sqlStatement = "Select u.login, Avg(r.rating) as Usefulness from users u, rates r where (u.login = r.login) group by u.login order by AVG(r.rating) DESC;";	
		try 
		{
			result = stmt.executeQuery(sqlStatement);

			while(result.next() && limitCount < limit)
			{
				for(int i = 1; i <= result.getMetaData().getColumnCount(); i++)
				{
					System.out.println(result.getMetaData().getColumnName(i) + ": " + result.getString(i) + " ");
				}
				System.out.println();
				limitCount+= 1;
			}
			return true;
		}
		catch(Exception e)
		{
			return false;
		}
	}
}
