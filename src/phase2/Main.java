package phase2;

//import java.io.BufferedReader;
import java.io.*;
//import java.io.InputStreamReader;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.text.DecimalFormat;
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
					if (count < 1 | count > 7)
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
						HashSet<String> housesAvail = reservation.DisplayTempHousesAvailable(login, connection.stmt, false);
						String requested;
						do
						{
							System.out.println("Enter a valid temporary housing ID number:");
							while((requested = input.readLine()) == null && requested.length() == 0);
						}while(!housesAvail.contains(requested));
						//Adding dates of availability
						String continueWithDates = "yes";
						String fromDate, toDate;
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
							if(!available.AddAvailable(costPerNightf,connection.stmt, Integer.parseInt(requested)))
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
						System.out.println("Which reservation are you checking into at this time?");
						System.out.println("Here are the reservations you have at this time.");
						Reserve reservations = new Reserve(login,password);
						String requested;
						String thidAndPeriodID;
						int thid;
						float cost;
						int periodID;
						HashSet<String> validReserves = reservations.DisplayAllReservationsForUser(login, connection.stmt);
						boolean wantsToStay = true;
						do
						{
							System.out.print("Enter the temporary house ID matching the reservation you wish to record your stay at this time: ");
							while((requested = input.readLine()) == null && requested.length() == 0); //TODO need to do error checking
							thidAndPeriodID = requested;
							thid = Integer.parseInt(requested);
							cost = reservations.SelectCostOfReserve(thid, login, connection.stmt);
							periodID = reservations.SelectPeriodIDOfReserve(thid, login, connection.stmt);
							System.out.print("And the period ID: ");
							while((requested = input.readLine()) == null && requested.length() == 0);//need to do error checking here as well
							thidAndPeriodID += " " + requested;
							DecimalFormat decimalFormat = new DecimalFormat("0.00");
					        String costAsString = decimalFormat.format(cost);
							System.out.print("Please retype the amount shown without the dollar sign ($" + costAsString + "), the cost associated with the temporary housing, to confirm the price: ");
							while((requested = input.readLine()) == null && requested.length() == 0);//need to do error checking here as well
							if(Float.parseFloat(requested) != cost)
							{
								wantsToStay = false;
								break;
							}
						}while(!validReserves.contains(thidAndPeriodID));
						
						if(wantsToStay)
						{
							reservations.RemoveReservation(login, thid, periodID, connection.stmt);
							System.out.println("Thanks for staying with Uotel AirBNB!");
							Stays newStay = new Stays(thidAndPeriodID);
							newStay.AddStay(login, cost, connection.stmt); //TODO WRONG RIGHT NOW, PAYMENT ALL OF RESERVE COST
						}
						else
						{
							System.out.println("Cancelling check in.");
						}
						
						
						
						
					}
					else if (count == 5)//All types of feedback
					{
						int feedbackCount = 0;
						String feedbackChoice;
						System.out.println("What you like to leave feedback on?");
						System.out.println("1. Mark favorite temp housings:");
						System.out.println("2. Rate temp housings:");
						System.out.println("3. Rate another user's feedback on temp housings:");
						System.out.print("Enter your choice here: ");
						while ((feedbackChoice = input.readLine()) == null && feedbackChoice.length() == 0);
						try
						{
							feedbackCount = Integer.parseInt(feedbackChoice);
						} 
						catch (Exception e)
						{
							System.err.println("Error parsing option to int.");
							continue;
						}
						if (feedbackCount < 1 | feedbackCount > 4)
						{
							System.out.println("Your option " + feedbackChoice + " was not a valid number.");
							continue;
						}
						if(feedbackCount == 1)//Mark a th as favorite
						{
							String favTHID = "";
							String favPeriodID = "";
							FavoriteTH favTH = new FavoriteTH();
							
							if(!favTH.showAllVisitedTH(login,connection.stmt))
							{
								System.out.println("Error displaying displaying your visits.");
							}
							
							System.out.print("Enter THID of temp housing you would like to favorite:");
							while ((favTHID = input.readLine()) == null && favTHID.length() == 0);
							
							if(!favTH.MarkFavoriteTH(favTHID, login, connection.stmt))
							{
								System.out.println("Error marking " + favTHID + " as a favorite");
							}
							
						}
						else if(feedbackCount == 2)//rate temp housing
						{
							FeedbackTH feedbackTH = new FeedbackTH();
							String feedbackTHID = "";
							String feedbackText = "";
							String feedbackKeyWords = "";
							String starRatingString = "";
							
							if(!feedbackTH.showAllVisitedTH(login, connection.stmt))
							{
								System.out.println("Error displaying displaying your visits.");
							}
							//Get THID for feedback
							System.out.print("Enter THID of temp housing you would like to provide feedback for:");
							while ((feedbackTHID = input.readLine()) == null && feedbackTHID.length() == 0);
							//Get feedback text
							System.out.print("Please type any feedback you may have for THID " + feedbackTHID + ". (<=100 characters, ENTER to skip):");
							feedbackText = input.readLine();
							while(feedbackText.length() > 100)
							{
								System.out.print("Too long! Please try again!");
								feedbackText = input.readLine();
							}
							//Get feedback keywords
							System.out.print("Please type any keywords you may have for THID " + feedbackTHID + ". (<=50 characters, ENTER to skip):");
							feedbackKeyWords = input.readLine();
							while(feedbackKeyWords.length() > 50)
							{
								System.out.print("Too long! Please try again!");
								feedbackKeyWords = input.readLine();
							}
							//Get star rating
							System.out.print("Please enter a rating for THID " + feedbackTHID + ". (1-10, 1 being lowest and 10 being highest):");
							starRatingString = input.readLine();
							int starRating = 0;//Does this need to really be initialized to pass the while loop?
							do
							{
								try
								{
									starRating = Integer.parseInt(starRatingString);
									if(starRating < 1 || starRating > 10)
									{
										System.out.print("The number you entered is out of range. Please try again: ");
										while ((starRatingString = input.readLine()) == null && starRatingString.length() == 0);
									}
								}
								catch(Exception e)
								{
									System.out.print("The number you entered is invalid. Please try again: ");
									while ((starRatingString = input.readLine()) == null && starRatingString.length() == 0);
								}
							}while(starRating < 1 || starRating > 10);
							
							if(!feedbackTH.MarkTHFeedback(feedbackText, feedbackTHID, login, feedbackKeyWords, starRating, connection.stmt))
							{
								System.out.println("Error marking " + feedbackTHID + "'s feedback.");
							}
							
						}
						else if(feedbackCount == 3)//rate another user's feedback
						{
							RateUserFeedback rate = new RateUserFeedback();
							HashSet<Integer>theThids = rate.DisplayTempHouseTHIDS(connection.stmt);
							System.out.println("Above are the temporary houses IDs.  Select the one on which you would like to see user feedbacks.");
							System.out.println("Type in the ID number only associated with the house:");
							String desiredThid = "";
							int theThid = -1;
							do
							{
								if(theThid < 0)
								{
									while ((desiredThid = input.readLine()) == null && desiredThid.length() == 0);
									theThid = Integer.parseInt(desiredThid);

								}
								else
								{
									System.out.println("The desired ID either doesn't exist or your input is invalid.");
									while ((desiredThid = input.readLine()) == null && desiredThid.length() == 0);
									theThid = Integer.parseInt(desiredThid);

								}
								
							}while(!theThids.contains(theThid));
										
							HashSet<Integer> feedbackIDs = rate.GetAllUsersFeedback(login, theThid, connection.stmt);
							System.out.println("Here are the feedbacks associated with the THID: " + theThid);
							
							System.out.println("Which feedback do you wish to rate?");
							System.out.print("Type in only the feedback ID you wish to rate: ");
							String desiredFeedbackID = "";
							int feedbackID = -1;
							do
							{
								if(feedbackID < 0)
								{
									while ((desiredFeedbackID = input.readLine()) == null && desiredFeedbackID.length() == 0);
									feedbackID = Integer.parseInt(desiredFeedbackID);

								}
								else
								{
									System.out.println("The desired ID either doesn't exist or your input is invalid.");
									while ((desiredFeedbackID = input.readLine()) == null && desiredFeedbackID.length() == 0);
									feedbackID = Integer.parseInt(desiredFeedbackID);

								}
							}while(!feedbackIDs.contains(feedbackID));
							String desiredRating ="";
							int rating = -1;
							System.out.print("Now give the rating you want, can be 0-2, where 0 is the worst, and 2 is the best: ");
							do
							{
								if(rating < 0)
								{
									while ((desiredRating = input.readLine()) == null && desiredRating.length() == 0);
									rating = Integer.parseInt(desiredRating);

								}
								else
								{
									System.out.println("Your input was invalid, please enter a number from 0-2.");
									while ((desiredRating = input.readLine()) == null && desiredRating.length() == 0);
									rating = Integer.parseInt(desiredRating);

								}
							}while(rating < 0 || rating > 2);
							if(rate.RateFeedback(login, rating, feedbackID, connection.stmt))
							{
								System.out.println("You have successfully rated feedback ID " + feedbackID + " on housing ID " + theThid + ".");
							}
							else
							{
								System.out.println("Failed to rate feedback.");
							}
						}
						
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