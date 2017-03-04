package phase2;

//import java.io.BufferedReader;
import java.io.*;
//import java.io.InputStreamReader;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;

public class Main {
	
	private static String login = "";
	private static String password = "";
	
	public static void displayMenu()
	{
		 System.out.println("Welcome to the Uotel AirBNB");
    	 System.out.println("1. Login");
    	 System.out.println("2. Create new user");
    	 System.out.println("3. Exit");
		 System.out.println("To pick your option type in the number associated with that option.");
    	 System.out.print("Enter the number here: ");
	}
	
	public static void displayLoggedInMenu()
	{
		 System.out.println("Action Center");
    	 System.out.println("1. Make New Reservation");
    	 System.out.println("2. Make Property Listing");
    	 System.out.println("3. Record a Stay");
    	 System.out.println("4. Leave Feedback");
    	 System.out.println("5. Browse Property");
    	 System.out.println("6. Exit");
		 System.out.println("To pick your option type in the number associated with that option.");
    	 System.out.print("Enter the number here: ");
	}
	
	public static void CreateUserChoice(BufferedReader input, Connector connection) throws IOException
	{
		 //String login = "";
		 //String password = "";
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
			 login = "";
			password = "";
		 }
		 
	}
	
	public static boolean LoginChoice(BufferedReader input, Connector connection)throws IOException
	{
		
		
		Login loginObj = new Login();
		 System.out.println("Enter your user name login:");
		 while ((login = input.readLine()) == null && login.length() == 0);
		 System.out.println("Enter your password:");
		 while ((password = input.readLine()) == null && password.length() == 0);
		 
		return loginObj.LoginToServer(login, password, connection.stmt);
		 //System.out.println("Login State: " + loginState);
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		boolean loginState = false;
		boolean wantsToQuit = false;
		Connector connection = null;
		String choice;

		int count = 0;
		try 
		{
			do 
			{
				// remember to replace the password
				connection = new Connector();
				System.out.println("Database connection established");

				BufferedReader input = new BufferedReader(new InputStreamReader(System.in));

				while (!loginState) 
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
						try 
						{
							loginState = LoginChoice(input, connection);
						} 
						catch (Exception e) 
						{
							System.err.println(e.getMessage() + "\n" + e.getStackTrace());
						}
					} 
					else if (count == 2) 
					{
						try 
						{
							CreateUserChoice(input, connection);
						}
						catch (Exception e) 
						{
							System.err.println(e.getMessage() + "\n" + e.getStackTrace());
						}
					} 
					else
					{
						System.out.println("End of program, thank you for staying!");
						connection.stmt.close();
						loginState = false;
						wantsToQuit = true;
						break;
					}
				}
				while (loginState) 
				{
					displayLoggedInMenu();
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
					if (count < 1 | count > 6)
					{
						System.out.println("Your option " + count + " was not a valid number.");
						continue;
					}
					if (count == 1) 
					{
						Reserve reservation = new Reserve(login, password);
						reservation.DisplayTempHousesAvailable(connection.stmt);
						List<Integer> housesID = new ArrayList<Integer>();
						List<Integer> periodsID = new ArrayList<Integer>();
						System.out.println("Type which temporary housing ID you would like to reserve:");
						String answer = "no";
						String requested;
						do
						{
							requested = input.readLine();
							housesID.add(Integer.parseInt(requested));
							System.out.println("Here are the available dates: ");
							reservation.DisplayAvailableDays(Integer.parseInt(requested), connection.stmt);
							System.out.println("Input the period ID related to which dates you want:");
							requested = input.readLine();
							periodsID.add(Integer.parseInt(requested));
							System.out.println("Would you like to reserve another? (yes/no)");
							answer = input.readLine();
							answer = answer.toLowerCase();
						}while(answer == "y" || answer == "yes");
						
						for(int i = 0; i < housesID.size(); i++)
						{
							reservation.AddReservation(housesID.get(i), periodsID.get(i), connection.stmt);
						}
					} 
					else if (count == 2)
					{

					}
					else if (count == 3)
					{

					} 
					else if (count == 4)
					{

					}
					else if (count == 5)
					{

					} 
					else if (count == 6) 
					{
						// TODO: Make this return to the previous menu somehow.
						// Don't be bad
						loginState = false;
					}
				}
			} while (!wantsToQuit);
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			System.err.println("Either connection error or query execution error! " + e.getMessage());
		} 
		finally 
		{
			if (connection != null) 
			{
				try 
				{
					connection.closeConnection();
					System.out.println("Database connection terminated");
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
