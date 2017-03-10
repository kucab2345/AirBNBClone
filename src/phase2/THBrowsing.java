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
	
	public HashSet<String> RequestHousingInformation(String price, String city, String state, String keywords, String category, int orderOption, Statement statement)
	{
		output = new HashSet<String>();
		
		
		return output;
	}
}
