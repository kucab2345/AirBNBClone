<%@ page language="java" import="cs5530.*" %>
<%@ page import="java.util.*" %>
<html>
<head>
<script LANGUAGE="javascript">
function checkoutValues()
{
	window.location.href = "/~5530u47/FinalizeReservations.jsp";
}

function check_all_fields(form_obj){
	if( form_obj.thidAttributeValue.value == "" && form_obj.pidAttributeValue.value == "")
	{
		alert("Some fields were empty that couldn't be, please make sure they are not empty.");
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
Reserve reservation = new Reserve(login, password);
StringBuilder output = new StringBuilder();
HashSet<String> housesAvail = reservation.DisplayTempHousesAvailable(connection.stmt, output);
out.println(output.toString());
Integer[] housesID;
Integer[] periodsID;
if(session.getAttribute("housesID") != null)
{
	housesID = (Integer[])session.getAttribute("housesID");
}
else
{
	housesID = new Integer[0];
}
if(session.getAttribute("periodsID")!= null)
{
	periodsID = (Integer[])session.getAttribute("periodsID");
}
else
{
	periodsID = new Integer[0];
} 
String thidAttribute = request.getParameter("thidAttribute");
String pidAttribute = request.getParameter("pidAttribute");

if( thidAttribute == null){		
	if(pidAttribute != null)
	{
		Integer requestedPID = Integer.valueOf(request.getParameter("pidAttributeValue"));
		periodsID = Arrays.copyOf(periodsID, periodsID.length + 1);
		periodsID[periodsID.length - 1] = requestedPID;
		session.setAttribute("periodsID", periodsID);
	}
%>
Type which temporary housing ID you would like to reserve:<BR>
<form name="makeReservations" method=get onsubmit="return check_all_fields(this)" action="MakeReservation.jsp">
		<input type=hidden name="thidAttribute" value="thid">
		<input type=number name="thidAttributeValue" min="0">
<BR>
To get the dates, click submit. <BR>
<input type=submit>
<BR><BR>
<button type="button" onclick="checkoutValues()" id="finishButton">Finish</button>
</form>

<%
 }
else{

		Integer requested = Integer.valueOf(request.getParameter("thidAttributeValue"));
		Suggestions suggest = new Suggestions(login, requested);
		%>
		Here are the available dates: <BR>
		<%
		StringBuilder output2 = new StringBuilder();
		int numAvail = reservation.DisplayAvailableDays(requested, connection.stmt, output2);
		out.println(output2.toString());
		if(numAvail > 0)
		{
			housesID = Arrays.copyOf(housesID, housesID.length + 1);
			housesID[housesID.length - 1] = requested;
			session.setAttribute("housesID", housesID);
			%>
			Input the period ID related to which dates you want:<BR>
			<form name="makeReservationsWDates" method=get onsubmit="return check_all_fields(this)" action="MakeReservation.jsp">

			<input type=hidden name="pidAttribute" value="pid">
			<input type=number name="pidAttributeValue" min="0"><BR>
			Click submit to add the reservation. <BR>
			<input type=submit>
			</form>
			<BR>
			<%
			StringBuilder output3 = new StringBuilder();
			suggest.MakeSuggestions(connection.stmt, output2);
			out.println(output3.toString());
			%>
			Above are some housing options that other users have reserved who have also used temporary housing ID <%=housesID[housesID.length - 1]%>. 
<%
		}
		else
		{%>
			Ooops! There are no available dates for that house!<BR>
			<form method=get onsubmit="return check_all_fields(this)" action="MakeReservation.jsp">
			<input type=submit>Try Again</input>
			</form>
		<%
		}
}
connection.closeStatement();
connection.closeConnection();

%>
</body>
</html>