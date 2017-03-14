package phase2;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.*;

public class THFeedbackUseful 
{
	private String sqlStatement;
	private HashSet<String> feedbacks = new HashSet<String>();
	private ResultSet result;
	
	public boolean getAllThidFeedbacks(String thid, Statement stmt)
	{
		//sqlStatement = "SELECT * FROM visit WHERE (login = \"" + login + "\");";
		try 
		{
			result = stmt.executeQuery(sqlStatement);

			while(result.next())
			{
				for(int i = 1; i <= result.getMetaData().getColumnCount(); i++)
				{
					System.out.println(result.getMetaData().getColumnName(i) + ": " + result.getString(i) + " ");
					//thids.add(result.getString(i));
				}
				System.out.println();
			}
			
			return true;
		}
		catch(Exception e)
		{
			return false;
		}
	}
}
