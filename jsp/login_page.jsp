<%@ page language="java" import="cs5530.*" %>
<html>
<style>
 .loginButtons{
	 height:5%;
	 width:30%;
	 font-size: 18px;
	 background-color: #CC0A4A;
	 color: white;
	 text-align: center;
 }
 
  .loginButtons:hover,
  .loginButtons:focus {
    color: black;
    cursor: pointer;
	background-color: #FFFFFF;
}
 
 .retryButton{
	 height:10%;
	 width:25%;
	 font-size: 36px;
 }
</style>
<head>
	<%
	session = request.getSession();
	String username = request.getParameter("user");
	session.setAttribute("theUser", username);
	String password = request.getParameter("userpassword");
	session.setAttribute("thePass", password);
	Connector connection = new Connector();
	AdminTools adminStatus = new AdminTools();
	Login log = new Login();
	boolean isAdminUser = adminStatus.checkIfAdmin(username, connection.stmt);
	session.setAttribute("theAdminStatus", isAdminUser);
	
	if(log.LoginToServer(username, password, connection.stmt))
	{
	%>
	<center>
	<h2>Action Center</h2>
	<button class= "loginButtons", onclick="MakeReservations()">1. Make New Reservation </button><BR>
	<button class= "loginButtons", onclick="MakeListingOrUpdate()">2. Make Property Listing Or Update Existing</button><BR>
	<button class= "loginButtons", onclick="AddDates()">3. Add More Availability Dates to Owned Property</button><BR>
	<button class= "loginButtons", onclick="StayRecord()">4. Record a Stay</button><BR>
	<button class= "loginButtons", onclick="FeedbackJunk()">5. Leave Feedback, Feedback Ratings, and User Trust Rating</button><BR>
	<button class= "loginButtons", onclick="TheBrowsing()">6. Browse Property</button><BR>
	<button class= "loginButtons", onclick="TheStatistics()">7. Statistics</button><BR>
	<button class= "loginButtons", onclick="ExitTheProgram()">8. Exit</button><BR>
	Click on any button to go to the specified action...<BR>
	</center>
	<%
	}
	else
	{
	%>
	<h1>Failed to Login. </h1><BR>
	<form action="/~5530u47/MainWindow.jsp" method="POST">
		<button type="submit", class="retryButton">Retry</button>
	</form>
	<%
	}
	%>
		
</head>

<script LANGUAGE="javascript">
	function MakeReservations()
	{
		window.location.href = "/~5530u47/MakeReservation.jsp";
	}
	
	function MakeListingOrUpdate()
	{
		
	}
	
	function AddDates()
	{
		
	}
	
	function StayRecord()
	{
		
	}
	
	function FeedbackJunk()
	{
		window.location.href = "/~5530u47/FeedbackMenu.jsp";
	}
	
	function TheBrowsing()
	{
		
	}
	
	function TheStatistics()
	{
		window.location.href = "/~/5530u47/Statistics.jsp";
	}
	
	function ExitTheProgram()
	{
		window.location.href = "/~5530u47/MainWindow.jsp";
		sessionScope.put("theUser", "");
		sessionScope.put("thePass", "");
		
	}
</script>
	
</html>