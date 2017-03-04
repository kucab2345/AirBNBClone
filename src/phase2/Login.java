package phase2;

import java.sql.ResultSet;
import java.sql.Statement;

public class Login 
{
	public boolean LoginToServer(String inLogin, String inPassword, Statement stmt)
	{
		String sql = "select * from users where login = \"" + inLogin + "\" and userPassword = \"" + inPassword + "\";";

		boolean output = false;
		ResultSet rs = null;
		System.out.println("Executing " + sql);
		int counter = 0;
		try 
		{
			rs = stmt.executeQuery(sql);
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
