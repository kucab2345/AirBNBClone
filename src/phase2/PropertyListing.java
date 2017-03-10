package phase2;

import java.sql.Statement;

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
	
}
