//package phase2;
package cs5530;

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
	
	public boolean RequestHousingInformation(String price, String city, String state, String keywords, String category, String orderOption, Statement statement, StringBuilder newOutput, String conjunction)
	{
		int lastPos = 0;
		boolean first = true;
		String[] strings = new String[5];
		
		strings[0] = price;
		strings[1] = city;
		strings[2] = state;
		strings[3] = keywords;
		strings[4] = category;
		
		output = new HashSet<String>();
		sqlStatement = "SELECT t.*, a.pricePerNight, w.words from temphousing t, available a, keywords w "
				+ "WHERE a.thid = t.thid AND w.wordsID = ANY(SELECT wordsID FROM haskeywords WHERE thid = t.thid) "
				+ "AND (";
		
		
			if(!price.equals(""))
			{
				sqlStatement += price;
				lastPos = 0;
			}
			else if(!city.equals(""))
			{
				sqlStatement += city;
				lastPos = 1;
			}
			else if(!state.equals(""))
			{
				sqlStatement += state;
				lastPos = 2;
			}
			else if(!keywords.equals(""))
			{
				sqlStatement += keywords;
				lastPos = 3;
			}
			else if(!category.equals(""))
			{
				sqlStatement += category;
				lastPos = 4;
			}
			
		for(int i = lastPos + 1; i < strings.length; i++)
		{
			if(!strings[i].equals(""))
			{
				sqlStatement += conjunction + strings[i];
			}
		}
		sqlStatement += ") group by t.thid, a.pricePerNight, w.words ";
		
		
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
			//System.out.println("===============================================================================================");
			newOutput.append("===============================================================================================<br/>");
			while(result.next())
			{
				for(int i = 1; i <= result.getMetaData().getColumnCount(); i++)
				{
					//System.out.println(result.getMetaData().getColumnName(i) + ": " + result.getString(i) + " ");
					newOutput.append(result.getMetaData().getColumnName(i) + ": " + result.getString(i) + " <br/>");
				}
				//System.out.println("===============================================================================================");
				newOutput.append("===============================================================================================<br/>");
			}
			return true;
		}
		catch(Exception e)
		{
			System.err.println(e.getMessage() + "\n" +  e.getStackTrace());
			System.out.println("Cannot find the housing information requested.");
			return false;
		}
	}
	
	public HashSet<String> RequestSingleHousingInformation(String choice, String orderOption, Statement statement)
	{
		output = new HashSet<String>();
		sqlStatement = "SELECT t.thid, " + choice + "FROM temphousing t ORDER BY " + orderOption + ";";
		
		return output;
	}
}
