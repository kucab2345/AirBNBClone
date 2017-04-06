<%@ page language="java" import="cs5530.*" %>
<%@ page import="java.util.*" %>
<html>
<head>
<script LANGUAGE="javascript">
</script>
</head>
<body>
<%
session = request.getSession();
String login = session.getAttribute("theUser").toString();
String password = session.getAttribute("thePass").toString();
Connector connection = new Connector();
StringBuilder output = new StringBuilder();

Reserve reservation = new Reserve(login, password);
Login log = new Login();

if(!log.LoginToServer(login, password, connection.stmt))
{
%>
<h1>Invalid Login</h1><BR>
	<form action="/~5530u47/MainWindow.jsp" method="POST">
		<button type="submit", class="retryButton">Go Back</button>
	</form>
	<%
}

HashSet<String> housesAvail = reservation.DisplayTempHousesAvailable(login, connection.stmt, false, output);

out.println(output.toString());%><BR><%
out.println("Enter the temporary housing ID of the property you would like to update.");
if(request.getParameter("thidAttribute") == null)
{
%>
<BR>THID (Required)<BR>
	<form name="THID" method=get onsubmit="return check_all_fields(this)">
		<input type=hidden name="thidAttribute" value="thid">
		<input type=text name="thidValue" length=20>
	<BR>
Housing Category <BR>
	<form name="Category" method=get onsubmit="return check_all_fields(this)">
		<input type=hidden name="categoryAttribute" value="category">
		<input type=text name="categoryValue" length=50>
	<BR>	
Housing Description <BR>
	<form name="Description" method=get onsubmit="return check_all_fields(this)">
		<input type=hidden name="descriptionAttribute" value="description">
		<input type=text name="descriptionValue" length=140>
	<BR>
Housing Keywords <BR>
	<form name="Keywords" method=get onsubmit="return check_all_fields(this)">
		<input type=hidden name="keywordsAttribute" value="keywords">
		<input type=text name="keywordsValue" length=50>
	<BR>
Housing Language <BR>
	<form name="Language" method=get onsubmit="return check_all_fields(this)">
		<input type=hidden name="languageAttribute" value="language">
		<input type=text name="languageValue" length=20>
	<BR>
Housing Square Footage <BR>
	<form name="Footage" method=get onsubmit="return check_all_fields(this)">
		<input type=hidden name="footageAttribute" value="footage">
		<input type=number name="footageValue" min=1>
	<BR>
Housing Car Limit <BR>
	<form name="Car" method=get onsubmit="return check_all_fields(this)">
		<input type=hidden name="carAttribute" value="car">
		<input type=number name="carValue" min=1>
	<BR>
Housing Neighbors <BR>
	<select id="neighborID" name="neighbor">
			<option value = "hasNeighbors">Has Neighbors</option>
			<option value = "noNeighbors">No Neighbors</option>
		</select><BR>
City <BR>
	<form name="City" method=get onsubmit="return check_all_fields(this)">
		<input type=hidden name="cityAttribute" value="city">
		<input type=text name="cityValue" length=30>
	<BR>
State <BR>
	<form name="State" method=get onsubmit="return check_all_fields(this)">
		<input type=hidden name="stateAttribute" value="state">
		<input type=text name="stateValue" length=30>
	<BR>
<input type=submit>
<%
}
else
{
	%><BR><%
	String thid = request.getParameter("thidValue");
	String category = request.getParameter("categoryValue");
	String description = request.getParameter("descriptionValue");
	String keywords = request.getParameter("keywordsValue");
	String language = request.getParameter("languageValue");
	String footage = request.getParameter("footageValue");
	String car = request.getParameter("carValue");
	String city = request.getParameter("cityValue");
	String state = request.getParameter("stateValue");
	
	boolean neighbors = false;
	if(request.getParameter("neighbor").equals("hasNeighbors"))
	{
		neighbors = true;
	}
	
	
	Double footageDouble = Double.valueOf(footage);
	Integer carLimit = Integer.valueOf(car);
	PropertyListing listing = new PropertyListing(login,category,description,
						footageDouble,carLimit,neighbors, city, state);
	
	Integer thidInt = Integer.valueOf(thid);
	if(listing.UpdateTH(thidInt, connection.stmt))
	{
		out.println("Successfully updated temporary housing general information.");%><BR><%
	}
	else
	{
		out.println("Failed to update temporary housing general information.");%><BR><%
	}
	if(listing.UpdateTHKeywords(thidInt, keywords, language, connection.stmt))
	{
		out.println("Successfully updated temporary housing keywords.");%><BR><%
	}
	else
	{
		out.println("Failed to update temporary housing keywords.");%><BR><%
	}
	
}
%>
</body>
</html>