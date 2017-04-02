//package phase2;
package cs5530;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.*;

public class THFeedbackUseful 
{
	private String sqlStatement;
	private HashSet<String> feedbacks = new HashSet<String>();
	private Map<String,String> sorted = new HashMap<String,String>();
	private ResultSet result;
	
	public boolean getAllThidFeedbacks(String thid, String numberOfResultsS, Statement statement, StringBuilder sb)
	{
		//int limitCount = 0;
		int numberOfResults = Integer.parseInt(numberOfResultsS);
		sqlStatement = "SELECT f.feedbackText, AVG(r.rating) FROM rates r, feedback f WHERE r.feedbackID = f.feedbackID AND f.thid = " + thid + " GROUP BY r.feedbackID ORDER BY AVG(r.rating) DESC LIMIT " + numberOfResults + ";";
		try 
		{
			result = statement.executeQuery(sqlStatement);
			//&& limitCount < numberOfResults
			while(result.next() )
			{
				for(int i = 1; i <= result.getMetaData().getColumnCount(); i++)
				{
					System.out.println(result.getMetaData().getColumnName(i) + ": " + result.getString(i) + " ");
					sb.append(result.getMetaData().getColumnName(i) + ": " + result.getString(i) + " \n");
				}
				System.out.println();
				//limitCount+= 1;
			}
			return true;
		}
		catch(Exception e)
		{
			return false;
		}
	}
}
