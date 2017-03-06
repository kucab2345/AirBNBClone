package phase2;
import java.sql.Statement;

public class Period 
{	
	private String sqlStatement; 

	boolean AddPeriod(String inFromDate, String inToDate, Statement stmt)
	{
		sqlStatement = "INSERT INTO period VALUES(DEFAULT,\"" + inFromDate + "\",\"" + inToDate + "\");";
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
