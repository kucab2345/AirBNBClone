//package phase2;
package cs5530;

import java.sql.Statement;
import java.sql.ResultSet;

public class PropertyListing 
{
	private int thid;
	private String category;
	private String description;
	private double squareFootage;
	private int carLimit;
	private boolean neighbors;
	private String city;
	private String state;
	private String login;
	private String sqlStatement;
	
	public PropertyListing(String inLogin, String inCategory, String inDescription, double inSquareFootage, int inCarLimit, boolean inNeighbors, String inCity, String inState)
	{
		category = inCategory;
		description = inDescription;//optional
		squareFootage = inSquareFootage;//optional
		carLimit = inCarLimit;//optional
		neighbors = inNeighbors;//optional
		login = inLogin;
		city = inCity;
		state = inState;
	}
	
	public boolean AddListing(Statement statement)
	{
		sqlStatement = "INSERT INTO temphousing VALUES(DEFAULT,\"" + category + "\",\"" + description + "\","
				+ squareFootage + "," + carLimit + "," + neighbors + ",\"" + login + "\",\"" + city + "\",\"" + state + "\");";
		int rowsAffected = 0;
		try
		{
			rowsAffected = statement.executeUpdate(sqlStatement);
			return true;
		}
		catch(Exception e)
		{
			return false;
		}
	}
	
	public boolean AddKeywords(String words, String lang, Statement statement, boolean updatingExisting, int existingThid)
	{
		if(lang.equals(""))
		{
			lang = "English";
		}
		sqlStatement = "INSERT INTO keywords VALUES(DEFAULT, \"" + words + "\", \"" + lang + "\");";
		int rowsAffected;
		try
		{
			rowsAffected = statement.executeUpdate(sqlStatement);

		}
		catch(Exception e)
		{
			System.err.println(e.getMessage() + "\n" +  e.getStackTrace());
			System.out.println("Cannot add the keywords to new housing.");
			return false;
		}
		int thid = 0;
		int wordsID = 0;
		sqlStatement = "SELECT wordsID FROM keywords ORDER BY wordsID DESC LIMIT 1;";
		ResultSet result = null;
		try
		{
			Statement statement2 = statement.getConnection().createStatement();

			result = statement2.executeQuery(sqlStatement);
			while(result.next())
			{

					wordsID = Integer.parseInt(result.getString(1));
				
			}

		}
		catch(Exception e)
		{
			System.err.println(e.getMessage() + "\n" +  e.getStackTrace());
			System.out.println("Cannot add the keywords to new housing.");
			return false;
		}
		if(!updatingExisting)
		{
			sqlStatement = "SELECT thid FROM temphousing ORDER BY thid DESC LIMIT 1;";
		
		
			result = null;
			try
			{
				Statement statement3 = statement.getConnection().createStatement();
	
				result = statement3.executeQuery(sqlStatement);
				while(result.next())
				{
	
						thid = Integer.parseInt(result.getString(1));
					
				}
	
			}
			catch(Exception e)
			{
				System.err.println(e.getMessage() + "\n" +  e.getStackTrace());
				System.out.println("Cannot add the keywords to new housing.");
				return false;
			}
		}
		else
		{
			thid = existingThid;
		}
		sqlStatement = "INSERT INTO haskeywords VALUES(" + thid + ", " + wordsID + ");";
		try
		{
			Statement statement4 = statement.getConnection().createStatement();

			rowsAffected = statement4.executeUpdate(sqlStatement);
			return true;
		}
		catch(Exception e)
		{
			System.err.println(e.getMessage() + "\n" +  e.getStackTrace());
			System.out.println("Cannot add the keywords to new housing.");
			return false;
		}
		
	}
	
	public boolean UpdateTH(int desiredThid, Statement statement)
	{
		sqlStatement = "UPDATE temphousing SET neighbors = " + neighbors;
		if(!category.equals(""))
		{
			sqlStatement += ", category = \"" + category + "\"";
		}
		if(!description.equals(""))
		{
			sqlStatement += ", description = \"" + description + "\"";
		}
		if(squareFootage != -1)
		{
			sqlStatement += ", squareFootage = " + squareFootage;
		}
		if(carLimit != -1)
		{
			sqlStatement += ", carLimit = " + carLimit;
		}
		if(!city.equals(""))
		{
			sqlStatement += ", city = \"" + city + "\"";
		}
		if(!state.equals(""))
		{
			sqlStatement += ", state = \"" + state + "\"";
		}
		sqlStatement += " WHERE login = \"" + login +"\" AND thid = " + desiredThid + ";";
		
		int rowsAffected = 0;
		try
		{
			rowsAffected = statement.executeUpdate(sqlStatement);
			return true;
		}
		catch(Exception e)
		{
			System.err.println(e.getMessage() + "\n" +  e.getStackTrace());
			return false;
		}

	}
	
	public boolean UpdateTHKeywords(int desiredThid, String keywords, String language, Statement statement)
	{
		if(keywords.equals("") && language.equals(""))
		{
			return true;
		}
		sqlStatement = "SELECT wordsID FROM haskeywords WHERE thid = " + desiredThid + ";";
		ResultSet result = null;
		int wordsID = 0;
		try
		{
			Statement statement2 = statement.getConnection().createStatement();
			result = statement.executeQuery(sqlStatement);
			while(result.next())
			{
				wordsID = result.getInt(1);
			}
			if(wordsID == 0)
			{
				return AddKeywords(keywords, language, statement2, true, desiredThid);
			}
			
			sqlStatement = "UPDATE keywords SET ";
			if(!keywords.equals("") && !language.equals(""))
			{
				sqlStatement += "words = \"" + keywords + "\", languageIn = \"" + language + "\"";
				
			}
			else
			{
				if(!keywords.equals(""))
				{
					sqlStatement += "words = \"" + keywords + "\"";
				}
				else if(!language.equals(""))
				{
					sqlStatement += "languageIn = \"" + language + "\"";
				}
			}
			sqlStatement += " WHERE wordsID = " + wordsID + ";";
			
			statement2.executeUpdate(sqlStatement);
			
			return true;
		}
		catch(Exception e)
		{
			System.err.println(e.getMessage() + "\n" +  e.getStackTrace());
			return false;
		}
		
	}
	
}
