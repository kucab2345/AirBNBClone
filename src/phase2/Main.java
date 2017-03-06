package phase2;

//import java.io.BufferedReader;
import java.io.*;
//import java.io.InputStreamReader;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


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
    	 System.out.println("3. Add More Availability Dates to Owned Property");
    	 System.out.println("4. Record a Stay");
    	 System.out.println("5. Leave Feedback");
    	 System.out.println("6. Browse Property");
    	 System.out.println("7. Exit");
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
						HashSet<String> housesAvail = reservation.DisplayTempHousesAvailable(connection.stmt);
						List<Integer> housesID = new ArrayList<Integer>();
						List<Integer> periodsID = new ArrayList<Integer>();
						System.out.println("Type which temporary housing ID you would like to reserve:");
						String answer = "no";
						String requested;
						do
						{
							if(answer.equals("yes") || answer.equals("y"))
							{
								System.out.println("Type which temporary housing ID you would like to reserve:");
							}
							do
							{
								System.out.println("Enter a valid temporary housing ID number:");
								while((requested = input.readLine()) == null && requested.length() == 0);
							
								
							}while(!housesAvail.contains(requested));
							
							
							System.out.println("Here are the available dates: ");
							int numAvail = reservation.DisplayAvailableDays(Integer.parseInt(requested), connection.stmt);
							if(numAvail > 0)
							{
								housesID.add(Integer.parseInt(requested));
								System.out.println("Input the period ID related to which dates you want:");
								requested = input.readLine();
								periodsID.add(Integer.parseInt(requested));
								System.out.println("Would you like to reserve another? (yes/no)");
								answer = input.readLine();
								
							}
							else
							{
								
								System.out.println("Ooops! There are no available dates for that house!");
								System.out.println("Would you like to try another house? (yes/no)");
								answer = input.readLine();
							}
							answer = answer.toLowerCase();
							
						} while(answer.equals("yes") || answer.equals("y"));
						
						for(int i = 0; i < housesID.size(); i++)
						{
							reservation.AddReservation(housesID.get(i), periodsID.get(i), connection.stmt);
						}
					} 
					else if (count == 2)
					{
						String housingCategory;
						String housingDescription = "";
						String housingSquareFootage = "0";
						String housingCarLimit = "";
						String housingNeighbors = "";
						boolean housingNeighborsBool = false;
						
						System.out.print("Type in the housing category (ie, Apartment, Condo, House, etc):");
						while((housingCategory = input.readLine()) == null && housingCategory.length() == 0);
						
						System.out.print("Enter a description of the housing (press ENTER to skip):");
						while((housingDescription = input.readLine()) == null && housingDescription.length() == 0);
						
						System.out.print("Enter the square footage of the housing (press ENTER to skip):");
						while((housingSquareFootage = input.readLine()) == null && housingSquareFootage.length() == 0);

						System.out.print("Enter the car parking limit of the housing (press ENTER to skip):");
						while((housingCarLimit = input.readLine()) == null && housingCarLimit.length() == 0);
						
						System.out.print("Enter whether or not there are neighbors((y)es or (n)o). (press ENTER to skip):");
						while((housingNeighbors = input.readLine()) == null && housingNeighbors.length() == 0);
						housingNeighbors = housingNeighbors.toLowerCase();
						
						if(housingNeighbors.equals("yes") || housingNeighbors.equals("y"))
						{
							housingNeighborsBool = true;
						}
						
						int housingCarLimitInt = Integer.parseInt(housingCarLimit);
						double housingSquareFootageDouble = Double.parseDouble(housingSquareFootage);
						
						PropertyListing listing = new PropertyListing(login,housingCategory,housingDescription,
								housingSquareFootageDouble,housingCarLimitInt,housingNeighborsBool);
						
						if(!listing.AddListing(connection.stmt))
						{
							System.out.println("Failure to add housing!");
						};//Property added to tempHousing here
						
						//Adding dates of availability
						String continueWithDates = "yes";
						String fromDate, toDate;
						Map<Date,Date> dateMap = new Hashtable<Date,Date>();
						while(continueWithDates.equals("yes") || continueWithDates.equals("y"))
						{
							String pattern = "^[0-9]{4}-[0-9]{2}-[0-9]{2}$";
							Pattern patternThing = Pattern.compile(pattern);
							Matcher match = null;
							System.out.print("Enter the starting date this property is available in YYYY-MM-DD format:");
							boolean valid = false;
							do
							{
								while((fromDate = input.readLine()) == null && fromDate.length() == 0);
								match = patternThing.matcher(fromDate);
								if(match.find())
								{
									valid = true;
								}
								else
								{
									System.out.println("Please put your date in valid YYYY-MM-DD format.");
								}
							}while(!valid);
							valid = false;
							System.out.print("Enter the ending date this property is available in YYYY-MM-DD format:");
							do
							{
								while((toDate = input.readLine()) == null && toDate.length() == 0);
								match = patternThing.matcher(fromDate);
								if(match.find())
								{
									valid = true;
								}
								else
								{
									System.out.println("Please put your date in valid YYYY-MM-DD format.");
								}
							}while(!valid);
							
							Period period = new Period();
							if(!period.AddPeriod(fromDate, toDate, connection.stmt))
							{
								System.out.println("Failure to add periods!");
							}
							
							//Get the price, add price and dates to Available
							String costPerNight;
							System.out.print("Enter the cost per night:");
							while((costPerNight = input.readLine()) == null && costPerNight.length() == 0);
							float costPerNightf = Float.parseFloat(costPerNight);
							Available available = new Available();
							if(!available.AddAvailable(costPerNightf,connection.stmt))
							{
								System.out.println("Failure to add availablilties!");
							}
							
							System.out.print("Would you like to add more dates of availability? (y)es or (n)o:");
							while((continueWithDates = input.readLine()) == null && continueWithDates.length() == 0);
							continueWithDates = continueWithDates.toLowerCase();
							if(continueWithDates.equals("no") || continueWithDates == "n")
							{
								continueWithDates = "no";
							}
						}
						
						
					}
					else if (count == 3)
					{
						Reserve reservation = new Reserve(login, password);
						HashSet<String> housesAvail = reservation.DisplayTempHousesAvailable(connection.stmt);
						//Adding dates of availability
						String continueWithDates = "yes";
						String fromDate, toDate;
						Map<Date,Date> dateMap = new Hashtable<Date,Date>();
						while(continueWithDates.equals("yes") || continueWithDates.equals("y"))
						{
							String pattern = "^[0-9]{4}-[0-9]{2}-[0-9]{2}$";
							Pattern patternThing = Pattern.compile(pattern);
							Matcher match = null;
							System.out.print("Enter the starting date this property is available in YYYY-MM-DD format:");
							boolean valid = false;
							do
							{
								while((fromDate = input.readLine()) == null && fromDate.length() == 0);
								match = patternThing.matcher(fromDate);
								if(match.find())
								{
									valid = true;
								}
								else
								{
									System.out.println("Please put your date in valid YYYY-MM-DD format.");
								}
							}while(!valid);
							valid = false;
							System.out.print("Enter the ending date this property is available in YYYY-MM-DD format:");
							do
							{
								while((toDate = input.readLine()) == null && toDate.length() == 0);
								match = patternThing.matcher(fromDate);
								if(match.find())
								{
									valid = true;
								}
								else
								{
									System.out.println("Please put your date in valid YYYY-MM-DD format.");
								}
							}while(!valid);
							
							Period period = new Period();
							if(!period.AddPeriod(fromDate, toDate, connection.stmt))
							{
								System.out.println("Failure to add periods!");
							}
							
							//Get the price, add price and dates to Available
							String costPerNight;
							System.out.print("Enter the cost per night:");
							while((costPerNight = input.readLine()) == null && costPerNight.length() == 0);
							float costPerNightf = Float.parseFloat(costPerNight);
							Available available = new Available();
							if(!available.AddAvailable(costPerNightf,connection.stmt))
							{
								System.out.println("Failure to add availablilties!");
							}
							
							System.out.print("Would you like to add more dates of availability? (y)es or (n)o:");
							while((continueWithDates = input.readLine()) == null && continueWithDates.length() == 0);
							continueWithDates = continueWithDates.toLowerCase();
							if(continueWithDates.equals("no") || continueWithDates == "n")
							{
								continueWithDates = "no";
							}
						}
					} 
					else if (count == 4)
					{

					}
					else if (count == 5)
					{

					} 
					else if(count == 6)
					{
						
					}
					else if (count == 7) 
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
