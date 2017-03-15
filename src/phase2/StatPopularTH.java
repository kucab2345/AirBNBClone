package phase2;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.*;

public class StatPopularTH 
{
	private String sqlStatement;
	private ResultSet result;
	
	public boolean displayHighestRatedTH(Statement stmt)
	{
		
		sqlStatement = "Select f.thid, t.category, AVG(f.starRating) from feedback f, temphousing t where (f.thid = t.thid and t.category = ANY(select category from temphousing)) group by f.thid order by AVG(f.starRating) DESC;";
		try 
		{
			result = stmt.executeQuery(sqlStatement);

			while(result.next())
			{
				for(int i = 1; i <= result.getMetaData().getColumnCount(); i++)
				{
					System.out.println(result.getMetaData().getColumnName(i) + ": " + result.getString(i) + " ");
				}
				System.out.println();
			}
			return true;
		}
		catch(Exception e)
		{
			return false;
		}
	}

}
