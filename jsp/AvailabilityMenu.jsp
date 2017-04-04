<%@ page language="java" import="cs5530.*" %>
<%@ page import="java.util.*" %>
<html>
<style>
 .ListOrUpdateButton{
	 height:5%;
	 width:30%;
	 font-size: 18px;
	 background-color: #CC0A4A;
	 color: white;
	 text-align: center;
 }
 
  .ListOrUpdateButton:hover,
  .ListOrUpdateButton:focus {
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
	<h2>Make Property Listing Or Update Existing</h2>
	<button class= "ListOrUpdateButton", onclick="AddMoreDates()">1. Create New Property Listing </button><BR>
	<button class= "ListOrUpdateButton", onclick="RemoveDates()">2. Update Housing</button><BR>
	<button class= "ListOrUpdateButton", onclick="location.href = '/~5530u47/login_page.jsp?user=<%=login%>&userpassword=<%=password%>';">3. Main Menu</button><BR>
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