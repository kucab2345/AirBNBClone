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
	if( form_obj.fromDate.value == ""){
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
	<input type=number name="thid" min=123 max=2147483647> <BR>
	<input type=submit>
	</form>
	<%
}
else
{
	Integer thidInt = Integer.valueOf(thid);
	String periodID = request.getParameter("periodID");
	Available avail = new Available();
	avail.THAvailabilityPeriods(thidToRemove, connection.stmt, output);
	if(periodID == null)
	{
		out.println(output.toString());
		%>
		Please enter the period ID associated to the date you want to remove:<BR>
		<form name="dateStuffRemove" method=get onsubmit="return check_all_fieldsDates(this)" action="RemoveAvailabilityDates.jsp">
		<input type=hidden name="thid" value="<%=thid%>"> 
		<input type=number name="periodID" min=1><BR>
		<input type=submit>
		</form>
		<%
	}
	else
	{
		Integer periodIDInt = Integer.valueOf(periodID);

		if(avail.RemoveDate(thidInt, periodIDInt, connection.stmt))
		{
			%>
			Successfully removed dates from Temporary House ID: <%=thidToRemove%>. <BR>
			<%
		}
		else
		{
			%>
			Failed to remove dates from temporary house!<BR>
			<%			
		}
				
		%>
		Would you like to remove more dates? <BR>
		<button type="button" onclick="location.href= '/~5530u47/RemoveAvailabilityDates.jsp?thid=<%=thid%>';" id="removeMoreButton">Remove More</button>  
		<button type="button" onclick="location.href= '/~5530u47/RemoveAvailabilityDates.jsp';" id="removeMoreButtonButDifferent">Change Temporary House ID</button>  
		<button type="button" onclick="location.href = '/~5530u47/login_page.jsp?user=<%=login%>&userpassword=<%=password%>';" id="returnButton">Main Menu</button>
		<%
	}
}
%>
</body>
</html>
