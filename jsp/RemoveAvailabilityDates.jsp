<%@ page language="java" import="cs5530.*" %>
<%@ page import="java.util.*" %>
<html>
<head>
<script LANGUAGE="javascript">
function check_all_fieldsTHID(form_obj){
	if( form_obj.thid.value == ""){
		alert("Please enter some TH ID value.");
		return false;
	}
	return true;
}
function check_all_fieldsDates(form_obj){
	if( form_obj.fromDate.value == "" || form_obj.toDate.value == "" || form_obj.costPerNight.value == ""){
		alert("All of the fields associated with the dates must be inputted.");
		return false;
	}
	return true;
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
if(!log.LoginToServer(login, password, connection.stmt))
{
%>
<h2>Invalid Login</h2><BR>
<form action="/~5530u47/MainWindow.jsp" method="POST">
	<button type="submit", class="retryButton">Go Back</button>
</form>
<%
}
Reserve reservation = new Reserve(login, password);
StringBuilder output = new StringBuilder();
HashSet<String> housesAvail = reservation.DisplayTempHousesAvailable(login,connection.stmt,false, output);
//might need to store added dates in array
String thid = request.getParameter("thid");
if(thid == null)
{
	out.println(output.toString());
	%>
	Enter the temporary housing ID number associated with the place you would like to remove availability dates:<BR>
	<form name="removeDatesFromHouses" method=get onsubmit="return check_all_fieldsTHID(this)" action="RemoveAvailabilityDates.jsp">
	<input type=number name="thid" min=0 max=2147483647> <BR>
	<input type=submit>
	</form>
	<%
}
else
{
	Integer thidInt = Integer.valueOf(thid);
	Available avail = new Available();
	avail.THAvailabilityPeriods(thidToRemove, connection.stmt, output);
	if(fromDate == null || toDate == null || costPerNight == null)
	{
		%>
		Please enter the starting date of availability:<BR>
		<form name="dateStuff" method=get onsubmit="return check_all_fieldsDates(this)" action="AddAvailabilityDates.jsp">
		<input type=hidden name="thid" value="<%=thid%>"> 
		<input type=date name="fromDate"><BR>
		Please enter the ending date of availability:<BR>
		<input type=date name="toDate"><BR>
		Please enter the cost per night during these dates:<BR>
		<input type=number name="costPerNight" min=0 step="0.01"><BR>
		<input type=submit>
		</form>
		<%
	}
	else
	{
		Float costPerNightFloat = Float.valueOf(costPerNight);
		Period period = new Period();
		if(!period.AddPeriod(fromDate, toDate, connection.stmt))
		{
			%>
			There was a problem in adding the dates associated with this housing. <BR>
			<%
		}
		else
		{
			%>
			Success in adding the dates for this housing!<BR>
			<%			
		}
		Available available = new Available();
		if(!available.AddAvailable(costPerNightFloat,connection.stmt, thidInt))
		{
			%>
			There was a problem adding the availability for this housing.<BR>
			<%
		}
		{
			%>
			Success in adding the housing dates and availability to the database!<BR>
			<%
		}
		
		%>
		Would you like to add more dates? <BR>
		<button type="button" onclick="location.href= '/~5530u47/AddAvailabilityDates.jsp?thid=<%=thid%>';" id="addMoreButton">Add More</button>  
		<button type="button" onclick="location.href= '/~5530u47/AddAvailabilityDates.jsp';" id="addMoreButtonButDifferent">Change Temporary House ID</button>  
		<button type="button" onclick="location.href = '/~5530u47/login_page.jsp?user=<%=login%>&userpassword=<%=password%>';" id="returnButton">Main Menu</button>
		<%
	}
}
%>
</body>
</html>
