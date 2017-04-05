<%@ page language="java" import="cs5530.*" %>
<%@ page import="java.util.*" %>
<html>
<style>
 .AddOrRemoveButtons{
	 height:5%;
	 width:30%;
	 font-size: 18px;
	 background-color: #CC0A4A;
	 color: white;
	 text-align: center;
 }
 
  .AddOrRemoveButtons:hover,
  .AddOrRemoveButtons:focus {
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
<script LANGUAGE="javascript">
	function AddMoreDates()
	{
		window.location.href = "/~5530u47/AddAvailabilityDates.jsp";
	}
	
	function RemoveDates()
	{
		window.location.href = "/~5530u47/RemoveAvailabilityDates.jsp";
	}

</script> 
</head>
<body>
<%
session = request.getSession();
String login = session.getAttribute("theUser").toString();
String password = session.getAttribute("thePass").toString();
Connector connection = new Connector();
Login log = new Login();
	if(log.LoginToServer(login, password, connection.stmt))
	{
%>
<center>
	<h2>Add or Remove Dates of Availability</h2>
	<button class= "AddOrRemoveButtons", onclick="AddMoreDates()">1. Add Availability Dates</button><BR>
	<button class= "AddOrRemoveButtons", onclick="RemoveDates()">2. Remove Availability Dates</button><BR>
	<button class= "AddOrRemoveButtons", onclick="location.href = '/~5530u47/login_page.jsp?user=<%=login%>&userpassword=<%=password%>';">3. Main Menu</button><BR>
	</center>
	<%
	}
	else
	{
	%>
	<h1>Invalid Login</h1><BR>
	<form action="/~5530u47/MainWindow.jsp" method="POST">
		<button type="submit", class="retryButton">Go Back</button>
	</form>
	<%
	}
	%>
</body>
</html>