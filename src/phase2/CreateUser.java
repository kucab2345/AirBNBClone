//package phase2;
package cs5530;

import java.sql.ResultSet;
import java.sql.Statement;

public class CreateUser 
{
	private String userLogin;
	private String userName;
	private int userAge;
	private String userPassword;
	
	private String description;
	private boolean userType;
	private String gender;
	
	private String sqlStatement;
	
	public CreateUser(String login, String name, String age, String password)
	{
		userLogin = login;
		userName = name;
		userAge = Integer.parseInt(age);
		userPassword = password;
	}
	
	public void SetDescriptionText(String desc)
	{
		description = desc;
	}
	
	public void SetType(boolean type)
	{
		userType = type;
	}
	
	public void SetGender(String gend)
	{
		gender = gend;
	}
	
	public void SetAllOptional(String desc, boolean type, String gend)
	{
		description = desc;
		userType = type;
		gender = gend;
	}
	
	public void AddUserToDatabase(Statement statement) 
	{
		sqlStatement = "INSERT INTO users VALUES(\"" + userLogin + "\",\"" + userName + "\"," + userType + ",\""
				+ gender + "\"," + userAge + ",\"" + description + "\",\"" + userPassword + "\");";

		int result;
		System.out.println("Executing " + sqlStatement);
		try 
		{
			result = statement.executeUpdate(sqlStatement);
		} 
		catch (Exception e) 
		{
			System.err.println(e.getMessage() + e.getStackTrace());
			System.out.println("Cannot execute the query.");
		}
	}
}
