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
session = request.getSession();
String login = session.getAttribute("theUser").toString();
StringBuilder sb = new StringBuilder();

Connector connection = new Connector();
FavoriteTH favTH = new FavoriteTH();

favTH.showAllVisitedTH(login, connection.stmt, sb);
out.println(sb);
%>
<BR><BR>
<%
out.println("Enter the THID you wish to favorite");

if(request.getParameter("favoriteTHIDAttribute") == null)
{
%>
<form name="favoriteTHID" method=get onsubmit="return check_all_fields(this)">
		<input type=hidden name="favoriteTHIDAttribute" value="THID">
		<input type=text name="favoriteTHIDValue" length=30>
<BR>
<input type=submit>
<%
}
else
{
	String favoriteTHID = request.getParameter("favoriteTHIDValue");
	if(!favTH.MarkFavoriteTH(favoriteTHID, login, connection.stmt))
	{
		%><BR><BR><%
		out.println("Error marking " + favoriteTHID + " as a favorite");
	}
	else
	{
		%><BR><BR><%
		out.println("Successful!");
	}
}
connection.closeStatement();
connection.closeConnection();
%>
</body>
</html>