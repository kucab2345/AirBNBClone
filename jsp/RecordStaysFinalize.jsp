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
for(int i = 0; i < stayPeriodIDs.length; i++)
{
	reservations.RemoveReservation(login, stayThids.get(i), stayPeriodIDs.get(i), connection.stmt);
	String combinedThidAndPeriodID = stayThids.get(i) + " " + stayPeriodIDs.get(i);
	Stays newStay = new Stays(combinedThidAndPeriodID);
	newStay.AddStay(login, stayCosts.get(i), connection.stmt); 
}
%>
</body>
</html>