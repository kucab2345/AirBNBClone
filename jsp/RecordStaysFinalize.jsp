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
boolean success = true;
for(int i = 0; i < stayPeriodIDs.length; i++)
{
	try
	{
		reservations.RemoveReservation(login, stayThids[i], stayPeriodIDs[i], connection.stmt);
		String combinedThidAndPeriodID = stayThids[i] + " " + stayPeriodIDs[i];
		Stays newStay = new Stays(combinedThidAndPeriodID);
		newStay.AddStay(login, stayCosts[i], connection.stmt); 

	}
	catch(Exception e)
	{
			success = false;
	}
}
if(success)
{
	stayPeriodIDs = null;
	stayThids = null;
	stayCosts = null;
		
	session.setAttribute("stayPeriodIDs", stayPeriodIDs);
	session.setAttribute("stayThids", stayThids);
	session.setAttribute("stayCosts", stayCosts);
	out.println("Successfully recorded all stays!");%><BR><%
}
else
{
	out.println("Failed to record all stays!");%><BR><%
}

%>
	Return to the Action Center
	<button type="button" onclick="location.href = '/~5530u47/login_page.jsp?user=<%=login%>&userpassword=<%=password%>';" id="returnButton">Return</button>
</body>
</html>