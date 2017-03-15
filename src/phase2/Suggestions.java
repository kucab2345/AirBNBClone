package phase2;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashSet;

public class Suggestions 
{
	String login;
	int referencedThid;
	String sqlStatement;
	HashSet<Integer> result;
	
	public Suggestions(String givenLogin, int givenThid)
	{
		login = givenLogin;
		referencedThid = givenThid;
	}
	
	public HashSet<Integer> MakeSuggestions(Statement statement)
	{
		result = new HashSet<Integer>();
		sqlStatement = "SELECT DISTINCT(t.thid), t.category, t.description, t.squareFootage, t.carLimit, t.neighbors, t.city, t.state FROM temphousing t, reserve r WHERE t.thid != " + referencedThid + " AND r.login = ANY(SELECT login FROM reserve WHERE thid = " + referencedThid+ ") AND r.thid = t.thid ORDER BY (SELECT COUNT(periodID) FROM visit WHERE thid = t.thid) DESC;";
		ResultSet output = null;
		try
		{
			output = statement.executeQuery(sqlStatement);
			while(output.next())
			{
				System.out.println("==================================================================================================");
				for(int i = 1; i <= output.getMetaData().getColumnCount(); i++)
				{
					if(i == 1)
					{
						result.add(output.getInt(i));
						System.out.println("Temporary Housing ID: " + output.getInt(i));
					}
					else if(i == 2)
					{
						System.out.println("Category: " + output.getString(i));
					}
					else if(i == 3)
					{
						System.out.println("Housing description: " + output.getString(i));
					}
					else if(i == 4)
					{
						System.out.println("Square footage of housing: " + output.getString(i));
					}
					else if(i == 5)
					{
						System.out.println("Number of cars allowed: " + output.getString(i));
					}
					else if(i == 6)
					{
						if(output.getBoolean(i))
						{
							System.out.println("Does this housing have neighbors: Yes");
						}
						else
						{
							System.out.println("Does this housing have neighbors: No");
						}
						
					}
					else if(i == 7)
					{
						System.out.println("The city this housing is located: " + output.getString(i));
					}
					else if(i == 8)
					{
						System.out.println("The state this housing is located: " + output.getString(i));
					}
				}
			}
			if(result.isEmpty())
			{
				System.out.println("There are no suggestions for this temporary house.");
			}
			System.out.println("==================================================================================================");

		}
		catch(Exception e)
		{
			System.err.println(e.getMessage() + "\n" +  e.getStackTrace());
			System.out.println("Could not obtain suggested houses.");
		}
		
		return result;
	}
}
