package phase2;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.*;

public class StatPopularTH 
{
	private String sqlStatement;
	private ResultSet result;
	
	public boolean displayHighestRatedTH(int limit, Statement stmt)
	{
		
		/*
		 * This will return the max starRating per thid, but not Per category. For that we need to cross reference
		 * the feedback thid with the userhousing thid and group by category, but I couldnt not get it to work in workbench.
		 * 
		 * IT SHOULD RETURN THE HIGHEST RATED THID FOR EACH CATEGORY, HIGHEST RATING DENOTED BY AVG STAR RATING
		 * 
		 * SELECT F.thid, MAX(F.starRating) from feedback F
			group by F.thid
			
		 * Try to edit this if you'd like. plzfixfeifei
		 */
		//sqlStatement = //????
		
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
