<%@ page language="java" import="cs5530.*" %>
<html>
<head>
<script LANGUAGE="javascript">
function check_all_fields(form_obj){
	//alert(form_obj.searchAttribute.value+"='"+form_obj.attributeValue.value+"'");
	if( form_obj.usernameAttributeValue.value == "" || form_obj.passwordAttributeValue.value == "" || form_obj.nameAttributeValue.value == "" || form_obj.ageAttributeValue.value == ""){
		//alert("Search field should be nonempty");
		return false;
	}
	return true;
}
</script> 
</head>
<body>
<%
String login = session.getAttribute("theUser").toString();
Connector connection = new Connector();
Trusted trustObj = new Trusted(login);
StringBuilder sb = new StringBuilder();
if(request.getParameter("loginIDAttribute") == null || request.getParameter("trustedAttribute") == null)
{
	trustObj.DisplayAllUserLogins(connection.stmt, sb);
	String[] allLogins = sb.toString().split("\n");

	for(int i = 0; i < allLogins.length; i++)
	{
		out.println(allLogins[i]);%><BR><%
	}
%>
Login to mark trust of<BR>
	<form name="LoginID" method=get onsubmit="return check_all_fields(this)">
		<input type=hidden name="loginIDAttribute" value="loginID">
		<input type=text name="loginIDValue" length=30>
	<BR>
Trust Rating: 0: Not Trusted, 1: Trusted<BR>
	<form name="TrustedRating" method=get onsubmit="return check_all_fields(this)">
		<input type=hidden name="trustedAttribute" value="trusted">
		<input type=text name="trustedValue" length=1>
	<BR>
<input type=submit>
<%
}
else
{
	String targetID = request.getParameter("loginIDValue");
	String trust = request.getParameter("trustedValue");
	if(trustObj.MarkUserAsTrusted(login, targetID, trust, connection.stmt))
	{
		out.println("Successfully recorded trust rating! Return to Feedback Menu");%><BR><%
	}
	else
	{
		out.println("Failed to record trust rating! Return to Feedback Menu.");%><BR><%
	}
	%>
	<button type="button" onclick="location.href = '/~5530u47/FeedbackMenu.jsp';" id="returnButton">Return</button><%
}
connection.closeStatement();
connection.closeConnection();
%>
</body>
</html>