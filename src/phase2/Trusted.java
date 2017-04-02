//package phase2;
package cs5530;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashSet;

public class Trusted 
{
	private String sqlStatement;
	private String login;
	private HashSet<String> output;
	
	public Trusted(String givenLogin)
	{
		login = givenLogin;
	}
	
	public HashSet<String> DisplayAllUserLogins(Statement statement)
	{
		output = new HashSet<String>();
		sqlStatement = "SELECT u.login FROM users u WHERE u.login != \"" + login + "\";";
		ResultSet result = null;
		try
		{
			result = statement.executeQuery(sqlStatement);
			System.out.println("===============================================================================================");

			while(result.next())
			{
				for(int i = 1; i <= result.getMetaData().getColumnCount(); i++)
				{
					if(i == 1)
					{
						output.add(result.getString(i));
					}
					
					System.out.println(result.getMetaData().getColumnName(i) + ": " + result.getString(i) + " ");
				}
				System.out.println("===============================================================================================");

			}
			return output;
		}
		catch(Exception e)
		{
			System.err.println(e.getMessage() + "\n" +  e.getStackTrace());
			System.out.println("Cannot execute the query to display user logins.");
			return output;
		}
		
	}
	public void DisplayAllUserLogins(Statement statement, StringBuilder sb)
	{
		output = new HashSet<String>();
		sqlStatement = "SELECT u.login FROM users u WHERE u.login != \"" + login + "\";";
		ResultSet result = null;
		try
		{
			result = statement.executeQuery(sqlStatement);
			System.out.println("===============================================================================================");

			while(result.next())
			{
				for(int i = 1; i <= result.getMetaData().getColumnCount(); i++)
				{
					if(i == 1)
					{
						output.add(result.getString(i));
					}
					sb.append(result.getMetaData().getColumnName(i) + ": " + result.getString(i) + " \n");
					System.out.println(result.getMetaData().getColumnName(i) + ": " + result.getString(i) + " ");
				}
				System.out.println("===============================================================================================");
				sb.append("===============================================================================================\n");
			}
		}
		catch(Exception e)
		{
			System.err.println(e.getMessage() + "\n" +  e.getStackTrace());
			System.out.println("Cannot execute the query to display user logins.");

		}
		
	}
	
	///Marks whether or not a user is trusted by the user that is currently logged in.
	public boolean MarkUserAsTrusted(String currentUser, String trustedUser, String trustS, Statement statement)
	{
		login = currentUser;
		int trust = Integer.parseInt(trustS);
		if(trust == 1)
		{
			sqlStatement = "INSERT INTO trusted VALUES(\"" + trustedUser + "\", \"" + login + "\", TRUE);";
		}
		else
		{
			sqlStatement = "INSERT INTO trusted VALUES(\"" + trustedUser + "\", \"" + login + "\", FALSE);";
		}
		
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
}
