package phase2;

public class CreateUser 
{
	private String userLogin;
	private String userName;
	private int userAge;
	private String userPassword;
	
	private String description;
	private boolean userType;
	private String gender;
	
	private String sqlStatement;
	
	public CreateUser(String login, String name, int age, String password)
	{
		userLogin = login;
		userName = name;
		userAge = age;
		userPassword = password;
	}
	
	public void SetDescriptionText(String desc)
	{
		description = desc;
	}
	
	public void SetType(boolean type)
	{
		userType = type;
	}
	
	public void SetGender(String gend)
	{
		gender = gend;
	}
	
	public void SetAllOptional(String desc, boolean type, String gend)
	{
		description = desc;
		userType = type;
		gender = gend;
	}
	
	public void AddUserToDatabase()
	{
		
	}
}
