//package phase2;
package cs5530;

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
	
	public HashSet<Integer> MakeSuggestions(Statement statement, StringBuilder newOutput)
	{
		result = new HashSet<Integer>();
		sqlStatement = "SELECT DISTINCT(t.thid), t.category, t.description, t.squareFootage, t.carLimit, t.neighbors, t.city, t.state FROM temphousing t, visit v WHERE t.thid != " + referencedThid + " AND v.login = ANY(SELECT login FROM visit WHERE thid = " + referencedThid + ") AND v.thid = t.thid ORDER BY (SELECT COUNT(periodID) FROM visit WHERE thid = t.thid) DESC;";
		ResultSet output = null;
		try
		{
			output = statement.executeQuery(sqlStatement);
			while(output.next())
			{
				newOutput.append("==================================================================================================<br/>");
				//System.out.println("==================================================================================================");
				for(int i = 1; i <= output.getMetaData().getColumnCount(); i++)
				{
					if(i == 1)
					{
						result.add(output.getInt(i));
						//System.out.println("Temporary Housing ID: " + output.getInt(i));
						newOutput.append("Temporary Housing ID: " + output.getInt(i)+"<br/>");
					}
					else if(i == 2)
					{
						//System.out.println("Category: " + output.getString(i));
						newOutput.append("Category: " + output.getString(i)+"<br/>");
					}
					else if(i == 3)
					{
						//System.out.println("Housing description: " + output.getString(i));
						newOutput.append("Housing description: " + output.getString(i)+"<br/>");
					}
					else if(i == 4)
					{
						//System.out.println("Square footage of housing: " + output.getString(i));
						newOutput.append("Square footage of housing: " + output.getString(i)+"<br/>");
					}
					else if(i == 5)
					{
						//System.out.println("Number of cars allowed: " + output.getString(i));
						newOutput.append("Number of cars allowed: " + output.getString(i) + "<br/>");
					}
					else if(i == 6)
					{
						if(output.getBoolean(i))
						{
							//System.out.println("Does this housing have neighbors: Yes");
							newOutput.append("Does this housing have neighbors: Yes<br/>");
						}
						else
						{
							//System.out.println("Does this housing have neighbors: No");
							newOutput.append("Does this housing have neighbors: No<br/>");
						}
						
					}
					else if(i == 7)
					{
						//System.out.println("The city this housing is located: " + output.getString(i));
						newOutput.append("The city this housing is located: " + output.getString(i)+"<br/>");
					}
					else if(i == 8)
					{
						//System.out.println("The state this housing is located: " + output.getString(i));
						newOutput.append("The state this housing is located: " + output.getString(i)+"<br/>");
					}
				}
			}
			if(result.isEmpty())
			{
				//System.out.println("There are no suggestions for this temporary house.");
				newOutput.append("There are no suggestions for this temporary house.<br/>");
			}
			//System.out.println("==================================================================================================");
			newOutput.append("==================================================================================================<br/>");
		}
		catch(Exception e)
		{
			System.err.println(e.getMessage() + "\n" +  e.getStackTrace());
			System.out.println("Could not obtain suggested houses.");
		}
		
		return result;
	}
}
