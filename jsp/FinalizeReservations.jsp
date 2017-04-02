<%@ page language="java" import="cs5530.*" %>
<%@ page import="java.util.*" %>
<html>
<head>
<script LANGUAGE="javascript">
function canceled()
{
	window.location.href = "/~5530u47/login_page.jsp";
}

function check_all_fields(form_obj){
	if( form_obj.wantedToReserveValue.value == "")
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
Connector connection = new Connector();
%>
Are the following reservations correct? <BR>
<%
session = request.getSession();
String login = session.getAttribute("theUser").toString();
String password = session.getAttribute("thePass").toString();
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
Reserve reservation = new Reserve(login, password);
String wantedToReserve = request.getParameter("wantedToReserve");
if(wantedToReserve == null)
{
%>
<ol> <% for(int i = 0; i < housesID.length; i++) { %>
 <li>Temporary house of ID: <%=reservation.GetHouseInformation(housesID[i], connection.stmt)%> <BR>
 Staying between the dates of <%=reservation.GetDates(periodsID[i], connection.stmt)%>
 </li>
 <% } %> 
 </ol>
<form name="yesOrNo" method=get onsubmit="return check_all_fields(this)" action="FinalizeReservations.jsp">
<input type=hidden name="wantedToReserve" value="yesOrNo">
<input type=text name="wantedToReserveValue" length=10> Please type yes or no only. <BR>
<input type=submit>
</form>
<%
}
else
{
	String wantedToReserveValue = request.getParameter("wantedToReserveValue");
	wantedToReserveValue = wantedToReserveValue.toLowerCase();
	StringBuilder res = new StringBuilder();
	if(wantedToReserveValue.equals("yes"))
	{
		for(int j = 0; j < housesID.length; j++)
		{
			reservation.AddReservation(housesID[j], periodsID[j], connection.stmt, res);
			out.println(res.toString());
		}
		%>
		Successfully added reservations.  Please return to login.<BR>
		<%
	}
	else
	{
		%>
		Cancelling reservations.<BR>
		<%
	}
connection.closeStatement();
connection.closeConnection();
session.setAttribute("housesID", null);
session.setAttribute("periodsID", null);
%>
<button type="button" onclick="location.href = '/~5530u47/login_page.jsp?user=<%=login%>&userpassword=<%=password%>';" id="returnButton">Return</button>
<%
}
%>

</body>
		
</html>
