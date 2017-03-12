package phase2;

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
	
	PropertyListing(String inLogin, String inCategory, String inDescription, double inSquareFootage, int inCarLimit, boolean inNeighbors, String inCity, String inState)
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
	
	public boolean AddListing(Statement stmt)
	{
		sqlStatement = "INSERT INTO temphousing VALUES(DEFAULT,\"" + category + "\",\"" + description + "\","
				+ squareFootage + "," + carLimit + "," + neighbors + ",\"" + login + "\",\"" + city + "\",\"" + state + "\");";
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
	
	public boolean AddKeywords(String words, String lang, Statement statement)
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
	
}
