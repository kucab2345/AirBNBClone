<%@ page language="java" import="cs5530.*" %>
<%@ page import="java.util.*" %>
<html>
<head>
<script LANGUAGE="javascript">
function check_all_fields(form_obj){
	if( form_obj.thidAttributeValue.value == "" && form_obj.pidAttributeValue.value == "")
	{
		alert("Some fields were empty that couldn't be, please make sure they are not empty.");
		return false;
	}
	return true;
}
function checkoutValues()
{
	window.location.href = "/~5530u47/RecordStaysFinalize.jsp";
}
function storeValues()
{
	window.location.href = "/~5530u47/RecordStays.jsp";
}
function storeValues
</script> 
</head>
<body>
<%
session = request.getSession();
String login = session.getAttribute("theUser").toString();
String password = session.getAttribute("thePass").toString();
Connector connection = new Connector();
Reserve reservations = new Reserve(login,password);
StringBuilder output = new StringBuilder();

Integer[] stayPeriodIDs;
Integer[] stayThids;
Float[] stayCosts;

if(session.getAttribute("stayPeriodIDs") != null)
{
	stayPeriodIDs = (Integer[])session.getAttribute("stayPeriodIDs");
}
else
{
	stayPeriodIDs = new Integer[0];
}
if(session.getAttribute("stayThids") != null)
{
	stayThids = (Integer[])session.getAttribute("stayThids");
}
else
{
	stayThids = new Integer[0];
}
if(session.getAttribute("stayCosts") != null)
{
	stayCosts = (Float[])session.getAttribute("stayCosts");
}
else
{
	stayCosts = new Float[0];
}



HashSet<String> validReserves = reservations.DisplayAllReservationsForUser(login, connection.stmt, output);
out.println(output);

%>
<BR> Enter the temporary housing ID (THID), period ID, and cost for a particular reservation to confirm you've stayed there. <BR>
<BR>THID<BR>
	<form name="THID" method=get onsubmit="return check_all_fields(this)">
		<input type=hidden name="thidAttribute" value="thid">
		<input type=text name="thidValue" length=20>
	<BR>
<BR>Period ID<BR>
	<form name="PeriodID" method=get onsubmit="return check_all_fields(this)">
		<input type=hidden name="periodIDAttribute" value="periodID">
		<input type=text name="periodIDValue" length=20>
	<BR>
<BR>Cost Confirmation<BR>
	<form name="Cost" method=get onsubmit="return check_all_fields(this)">
		<input type=hidden name="costAttribute" value="cost">
		<input type=text name="costValue" length=20>
	<BR>

	Click submit to add the reservation. <BR>
	<input type=submit><BR>
	To submit these stays, click finish. Be sure to finish adding ALL visits before clicking "Finish". <BR>
	<button type="button" onclick="location.href = '/~5530u47/RecordStaysFinalize.jsp';" id="finishButton">Finish</button>
<%
if(request.getParameter("thidAttribute") != null && request.getParameter("periodIDAttribute") != null && request.getParameter("costAttribute") != null)
{
	Integer thid = Integer.valueOf(request.getParameter("thidValue"));
	Integer periodID = Integer.valueOf(request.getParameter("periodIDValue"));
	Float cost = Float.valueOf(request.getParameter("costValue"));
	
	stayPeriodIDs = Arrays.copyOf(stayPeriodIDs, stayPeriodIDs.length + 1);
	stayPeriodIDs[stayPeriodIDs.length - 1] = periodID;
	session.setAttribute("stayPeriodIDs", stayPeriodIDs);
	
	stayThids = Arrays.copyOf(stayThids, stayThids.length + 1);
	stayThids[stayThids.length - 1] = thid;
	session.setAttribute("stayThids", stayThids);
	
	stayCosts = Arrays.copyOf(stayCosts, stayCosts.length + 1);
	stayCosts[stayCosts.length - 1] = cost;
	session.setAttribute("stayCosts", stayCosts);
}
%>
</body>
</html>
