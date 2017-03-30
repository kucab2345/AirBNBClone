<%@ page language="java" import="cs5530.*" %>
<html>

	
<body>
Username:
	<form name="username">
		<input type=hidden name="usernameAttribute" value="username">
		<input type=text name="usernameAttributeValue" length=10>
	</form>
	
Password:
	<form name="password">
		<input type=hidden name="passwordAttribute" value="password">
		<input type=text name="passwordAttributeValue" length=10>
	</form>
	
Sex (M/F/O) (Optional):
	<form name="sex">
		<input type=hidden name="sexAttribute" value="sex">
		<input type=text name="sexAttributeValue" length=1>
	</form>	

Name:
	<form name="name">
		<input type=hidden name="nameAttribute" value="name">
		<input type=text name="nameAttributeValue" length=10>
	</form>
	
Age:
	<form name="age">
		<input type=hidden name="ageAttribute" value="age">
		<input type=text name="ageAttributeValue" length=10>
	</form>
	
Description (optional):
	<form name="description">
		<input type=hidden name="descriptionAttribute" value="description">
		<input type=text name="descriptionAttributeValue" length=10>
	</form>
	
Type of Account (optional):
	<form name="type">
		<select>
			<option value = "empty"></option>
			<option value = "regular">Regular</option>
			<option value = "admin">Admin</option>
		</select>
	</form>
	
	<form name="submit">
	<input type=submit>
	<method=get onsubmit="createNewUser()" action="/~5530u47/MainWindow.jsp">
	</form>
	
	<p id = "test"></p>
</body>
<script LANGUAGE="javascript">
function createNewUser()
{
<%
	String username = request.getParameter("usernameAttribute");
	String name = request.getParameter("nameAttribute");
	String age = request.getParameter("ageAttribute");
	String password = request.getParameter("passwordAttribute");
	
	String description = request.getParameter("descriptionAttribute");
	String sex = request.getParameter("sexAttribute");
	
	%>
	document.getElementById("test").innerHTML = username + "," + name + "," + age + "," + password;
	<%
	boolean type;
	
	%>
	if(document.getElementById("type").options[document.getElementById("type").selectedIndex].value == "admin"))
	<%
	{
		type = true;
	}
	%>
	else
	<%
	{
		type = false;
	}

	Connector connection = new Connector();
	if(age != null)
	{
		CreateUser createUser = new CreateUser(username,name,age,password);
		createUser.SetAllOptional(description, type, sex);
		createUser.AddUserToDatabase(connection.stmt); 
	}
	
%>
}
</script> 

</html>
