package phase2;
import java.sql.ResultSet;
import java.sql.Statement;

public class FavoriteTH 
{
	private String sqlStatement;
	private ResultSet result = null;
	
	public boolean showAllVisitedTH(String login, Statement stmt)
	{
		sqlStatement = "SELECT * FROM visit WHERE (login = \"" + login + "\");";
		try 
		{
			result = stmt.executeQuery(sqlStatement);

			while(result.next())
			{
				for(int i = 1; i <= result.getMetaData().getColumnCount(); i++)
				{
					System.out.print(result.getMetaData().getColumnName(i) + ": " + result.getString(i) + " ");
					
				}
				System.out.println();
			}
			
			return true;
		}
		catch (Exception e) 
		{
			System.err.println(e.getMessage() + e.getStackTrace());
			System.out.println("Cannot execute the query selecting the price for the requested house and period.");
			return false;
		}
	}
	
	public boolean MarkFavoriteTH(String thid, String login, Statement stmt)
	{
		
		sqlStatement = "INSERT INTO favorites VALUES (\"" + thid + "\",\"" + login + "\", now());";//Now() works but it throws a warning since it is also trying to push in the time of date as well as today's date
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
}
