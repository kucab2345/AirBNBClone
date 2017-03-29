//package phase2;
package cs5530;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.*;

public class StatPopularTH 
{
	private String sqlStatement;
	private ResultSet result;

	public boolean displayMostPopularTH(int limit, Statement stmt)
	{
		String currentCategory = "";
		String previousCategory = "";
		int categoryCount = 0;
		
		sqlStatement = "Select t.thid, t.category, Count(v.thid) as TotalVisits from temphousing t, visit v where (t.thid = v.thid and t.category = ANY(select category from temphousing)) group by t.thid order by t.category, Count(v.thid) DESC";
		try 
		{
			result = stmt.executeQuery(sqlStatement);

			while(result.next())
			{
				for(int i = 1; i <= result.getMetaData().getColumnCount(); i++)
				{
					
					if(result.getMetaData().getColumnName(i).equals("category"))//if we are looking at the category row
					{
						previousCategory = currentCategory;
						currentCategory = result.getString(i);
						
						if(previousCategory.equals(currentCategory) && categoryCount < limit)
						{
							System.out.println(result.getMetaData().getColumnName(i-1) + ": " + result.getString(i-1) + " ");
							System.out.println(result.getMetaData().getColumnName(i) + ": " + result.getString(i) + " ");
							System.out.println(result.getMetaData().getColumnName(i+1) + ": " + result.getString(i+1) + " ");
							categoryCount++;
						}
						else if(!previousCategory.equals(currentCategory))
						{
							categoryCount = 0;
							System.out.println(result.getMetaData().getColumnName(i-1) + ": " + result.getString(i-1) + " ");
							System.out.println(result.getMetaData().getColumnName(i) + ": " + result.getString(i) + " ");
							System.out.println(result.getMetaData().getColumnName(i+1) + ": " + result.getString(i+1) + " ");
							categoryCount++;
						}
					}
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
	public boolean displayMostExpensiveTH(int limit, Statement stmt)
	{
		String currentCategory = "";
		String previousCategory = "";
		int categoryCount = 0;
		
		sqlStatement = "Select t.thid, t.category, AVG(v.cost) as AverageCost from temphousing t, visit v where (t.thid = v.thid and t.category = ANY(select category from temphousing)) group by t.thid order by t.category, AVG(v.cost) DESC";
		try 
		{
			result = stmt.executeQuery(sqlStatement);

			while(result.next())
			{
				for(int i = 1; i <= result.getMetaData().getColumnCount(); i++)
				{
					
					if(result.getMetaData().getColumnName(i).equals("category"))//if we are looking at the category row
					{
						previousCategory = currentCategory;
						currentCategory = result.getString(i);
						
						if(previousCategory.equals(currentCategory) && categoryCount < limit)
						{
							System.out.println(result.getMetaData().getColumnName(i-1) + ": " + result.getString(i-1) + " ");
							System.out.println(result.getMetaData().getColumnName(i) + ": " + result.getString(i) + " ");
							System.out.println(result.getMetaData().getColumnName(i+1) + ": " + result.getString(i+1) + " ");
							categoryCount++;
						}
						else if(!previousCategory.equals(currentCategory))
						{
							categoryCount = 0;
							System.out.println(result.getMetaData().getColumnName(i-1) + ": " + result.getString(i-1) + " ");
							System.out.println(result.getMetaData().getColumnName(i) + ": " + result.getString(i) + " ");
							System.out.println(result.getMetaData().getColumnName(i+1) + ": " + result.getString(i+1) + " ");
							categoryCount++;
						}
					}
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
	
	public boolean displayHighestRatedTH(int limit, Statement stmt)
	{
		String currentCategory = "";
		String previousCategory = "";
		int categoryCount = 0;
		
		sqlStatement = "Select f.thid, t.category, AVG(f.starRating) as AverageRating from feedback f, temphousing t where (f.thid = t.thid and t.category = ANY(select category from temphousing)) group by f.thid order by t.category, AVG(f.starRating) DESC;";
		try 
		{
			result = stmt.executeQuery(sqlStatement);

			while(result.next())
			{
				for(int i = 1; i <= result.getMetaData().getColumnCount(); i++)
				{
					
					if(result.getMetaData().getColumnName(i).equals("category"))//if we are looking at the category row
					{
						previousCategory = currentCategory;
						currentCategory = result.getString(i);
						
						if(previousCategory.equals(currentCategory) && categoryCount < limit)
						{
							System.out.println(result.getMetaData().getColumnName(i-1) + ": " + result.getString(i-1) + " ");
							System.out.println(result.getMetaData().getColumnName(i) + ": " + result.getString(i) + " ");
							System.out.println(result.getMetaData().getColumnName(i+1) + ": " + result.getString(i+1) + " ");
							categoryCount++;
						}
						else if(!previousCategory.equals(currentCategory))
						{
							categoryCount = 0;
							System.out.println(result.getMetaData().getColumnName(i-1) + ": " + result.getString(i-1) + " ");
							System.out.println(result.getMetaData().getColumnName(i) + ": " + result.getString(i) + " ");
							System.out.println(result.getMetaData().getColumnName(i+1) + ": " + result.getString(i+1) + " ");
							categoryCount++;
						}
					}
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
