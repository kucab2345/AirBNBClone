//package phase2;
package cs5530;

import java.sql.ResultSet;
import java.sql.Statement;

public class Login 
{
	public boolean LoginToServer(String inLogin, String inPassword, Statement statement)
	{
		String sql = "SELECT * FROM users WHERE login = \"" + inLogin + "\" AND userPassword = \"" + inPassword + "\";";

		boolean output = false;
		ResultSet rs = null;
		int counter = 0;
		try 
		{
			rs = statement.executeQuery(sql);
			while (rs.next()) 
			{
				counter++;
			}

			rs.close();
			
			if(counter > 0)
			{
				output = true;
			}
		} 
		catch (Exception e)
		{
			System.out.println("Cannot execute the query.");
		} 
		finally 
		{
			try 
			{
				if (rs != null && !rs.isClosed())
					rs.close();
			} 
			catch (Exception e) 
			{
				System.out.println("Cannot close resultset.");
			}
		}
		return output;
	}
}
