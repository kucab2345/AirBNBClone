package phase2;
import java.sql.ResultSet;
import java.sql.Statement;

public class Available 
{
	
	private int thid;
	private int periodID;
	private String sqlStatement;
	
	public boolean AddAvailable(float cost, Statement statement)
	{
		return AddAvailable(cost, statement, -1);
	}
	
	public boolean AddAvailable(float cost, Statement stmt, int thid)
	{
	
		if(thid < 0)
		{
			//We need thid from tempHousing and periodID from period. Get these through 2 sql calls
			sqlStatement = "SELECT thid FROM temphousing ORDER BY thid DESC LIMIT 1;";//Gets thid
		}
		else
		{
			//We need thid from tempHousing and periodID from period. Get these through 2 sql calls
			sqlStatement = "SELECT thid FROM temphousing WHERE thid =" + thid + ";";//Gets thid
		}
		
		ResultSet rs = null;
		try
		{
			rs = stmt.executeQuery(sqlStatement);
			while(rs.next())
			{
				for(int i = 1; i <= rs.getMetaData().getColumnCount(); i++)
				{
					thid = Integer.parseInt(rs.getString(i));
				}
			}
			
		}
		catch(Exception e)
		{
			return false;
		}
		
		
		sqlStatement = "SELECT periodID FROM period ORDER BY periodID DESC LIMIT 1;";//Gets periodID
		rs = null;
		try
		{
			rs = stmt.executeQuery(sqlStatement);
			while(rs.next())
			{
				for(int i = 1; i <= rs.getMetaData().getColumnCount(); i++)
				{
					periodID = Integer.parseInt(rs.getString(i));
				}
			}
			
		}
		catch(Exception e)
		{
			return false;
		}
		
		
		sqlStatement = "INSERT INTO available VALUES("+ thid + "," + periodID + "," + cost + ");";
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
