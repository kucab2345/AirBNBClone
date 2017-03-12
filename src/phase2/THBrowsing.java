package phase2;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashSet;

public class THBrowsing
{
	private String sqlStatement;
	private String login;
	private HashSet<String> output;
	
	public THBrowsing(String desiredLogin)
	{
		login = desiredLogin;
	}
	
	public HashSet<String> RequestHousingInformation(String price, String city, String state, String keywords, String category, String orderOption, Statement statement)
	{
		output = new HashSet<String>();
		sqlStatement = "SELECT t.thid" + price + city + state + keywords + category + " FROM temphousing t";
		if(!price.equals("") && !keywords.equals(""))
		{
			sqlStatement += ", available a, keywords w WHERE a.thid = t.thid AND w.wordsID = ANY(SELECT wordsID FROM haskeywords WHERE thid = t.thid)";
		}
		else if(!price.equals(""))
		{
			sqlStatement += ", available a WHERE a.thid = t.thid";
		}
		else if(!keywords.equals(""))
		{
			sqlStatement += ", keywords w WHERE w.wordsID = ANY(SELECT wordsID FROM haskeywords WHERE thid = t.thid)";
		}
		sqlStatement += " ORDER BY " + orderOption;
		if(!orderOption.equals("a.pricePerNight"))
		{
			sqlStatement += " DESC";
		}
		sqlStatement += ";";
		
		
		ResultSet result = null;
		try
		{
			result = statement.executeQuery(sqlStatement);
			System.out.println("===============================================================================================");

			while(result.next())
			{
				for(int i = 1; i <= result.getMetaData().getColumnCount(); i++)
				{
					System.out.println(result.getMetaData().getColumnName(i) + ": " + result.getString(i) + " ");
				}
				System.out.println("===============================================================================================");

			}
			return output;
		}
		catch(Exception e)
		{
			System.err.println(e.getMessage() + "\n" +  e.getStackTrace());
			System.out.println("Cannot find the housing information requested.");
			return output;
		}
	}
	
	public HashSet<String> RequestSingleHousingInformation(String choice, String orderOption, Statement statement)
	{
		output = new HashSet<String>();
		sqlStatement = "SELECT t.thid, " + choice + "FROM temphousing t ORDER BY " + orderOption + ";";
		
		return output;
	}
}
