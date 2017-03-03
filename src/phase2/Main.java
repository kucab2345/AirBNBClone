package phase2;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;

public class Main {

	public static void displayMenu()
	{
		 System.out.println("Welcome to the Uotel AirBNB");
    	 System.out.println("1. Login");
    	 System.out.println("2. Create new user");
    	 System.out.println("3. Exit");
		 System.out.println("To pick your option type in the number associated with that option.");
    	 System.out.print("Enter the number here: ");
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		Connector connection = null;
		String choice;
        String login;
        String password;
        String sql=null;
        int count = 0;
         try
		 {
			//remember to replace the password
        	 	connection = new Connector();
	             System.out.println ("Database connection established");
	         
	             BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
	             
	             while(true)
	             {
	            	 displayMenu();
	            	 while ((choice = input.readLine()) == null && choice.length() == 0);
	            	 try
	            	 {
	            		 count = Integer.parseInt(choice);
	            	 }
	            	 catch (Exception e)
	            	 {
	            		 System.err.println("Error parsing option to int.");
	            		 continue;
	            	 }
	            	 if (count < 1 | count > 3)
	            	 {
	            		 System.out.println("Your option " + count + " was not a valid number.");
	            		 continue;
	            	 }
	            		 
	            	 if (count == 1)
	            	 {
	            		 System.out.println("Enter your user name login:");
	            		 while ((login = input.readLine()) == null && login.length() == 0);
	            		 System.out.println("Enter your password:");
	            		 while ((password = input.readLine()) == null && password.length() == 0);
	            		 Course course = new Course();
	            		 System.out.println(course.getCourse(login, password, connection.stmt));
	            	 }
	            	 else if (count == 2)
	            	 {	 
	            		 String name = "";
	            		 String age ="";
	            		 String description = null;
	            		 String gender = null;
	            		 String userType = "";
	            		 boolean type = false;
	            		 
	            		 CreateUser user;
	            		 System.out.print("Enter your username:");
	            		 login = input.readLine();
	            		 System.out.print("Enter your password:");
	            		 password = input.readLine();
	            		 System.out.print("Enter your name:");
	            		 name = input.readLine();
	            		 System.out.print("Enter your age:");
	            		 age = input.readLine();
	            		 
	            		 System.out.print("Enter a desciption about yourself (press ENTER to skip):");
	            		 description = input.readLine();
	            		 System.out.print("Enter your gender (M/F/O) (press ENTER to skip):");
	            		 gender = input.readLine();
	            		 System.out.print("Enter your type of account (Type 0 for renter, 1 for owner, press ENTER to skip):");
	            		 userType = input.readLine();
	            		 
	            		 if(userType == "1")
	            		 {
	            			 type = true;
	            		 }
	            		 
	            		 if(login == "" || password == "" || name == "" || age == "")
	            		 {
	            			 System.out.println("You must complete all fields.");
	            		 }
	            		 else
	            		 {
	            			 user = new CreateUser(login,name,Integer.parseInt(age),password);
	            			 
	            			 //TODO
	            			 //Setting as nulls does not seem to actually set the fields in the table to NULL. Look into this. Scrub
	            			 if(description == "")
	            			 {
	            				 description = null;
	            			 }
	            			 if(gender == "")
	            			 {
	            				 gender = null;
	            			 }
	            			 
	            			 user.SetAllOptional(description,type,gender);
	            			 user.AddUserToDatabase(connection.stmt);
	            			 //ResultSet rs = connection.stmt.executeQuery(sql);
		            		 //ResultSetMetaData rsmd = rs.getMetaData();
		            		 //int numCols = rsmd.getColumnCount();
		            		 /*while (rs.next())
		            		 {

		            			 for (int i=1; i<=numCols;i++)
		            				 System.out.print(rs.getString(i)+"  ");
		            			 System.out.println("");
		            		 }*/
		            		 System.out.println("User Created");
		            		 //rs.close();
	            		 }
	            		 
	            	 }
	            	 else
	            	 {   
	            		 System.out.println("EoM");
	            		 connection.stmt.close();
	            		 
	            		 break;
	            	 }
	             }
		 }
         catch (Exception e)
         {
        	 e.printStackTrace();
        	 System.err.println ("Either connection error or query execution error! " + e.getMessage());
         }
         finally
         {
        	 if (connection != null)
        	 {
        		 try
        		 {
        			 connection.closeConnection();
        			 System.out.println ("Database connection terminated");
        		 }
        	 
        		 catch (Exception e) 
        		 { 
        			 e.printStackTrace();
        			 System.err.println("There was some error closing: " + e.getMessage());
        		 }
        	 }	 
         }
	}

}
