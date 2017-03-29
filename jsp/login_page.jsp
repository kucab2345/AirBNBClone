<%@ page language="java" import="cs5530.*" %>
<html>
<head>
	<%
	String username = request.getParameter("user");
	String password = request.getParameter("userpassword");
	Connector connnection = new Connector();
	AdminTools adminStatus = new AdminTools();
	Login log = new Login();
	if(adminStatus.checkIfAdmin(username, connection.stmt))
	{
	%>
	User is an admin.
	<%
	}
	else
	{
	%>
	User is not an admin.
	<%
	}
	if(log.LoginToServer(username, password, connection.stmt))
	{
	%>
	Successfully logged in.
	<%
	}
	else
	{
	%>
	Login was not successful.
	<%
	}
	%>
		
</head>
	
</html>