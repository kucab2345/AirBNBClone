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

function createNewUser()
{

}

</script> 
</head>
<body>
<%String usernameAttribute = request.getParameter("usernameAttribute");
String passwordAttribute = request.getParameter("passwordAttribute");
String nameAttribute = request.getParameter("nameAttribute");
String ageAttribute = request.getParameter("ageAttribute");
if( usernameAttribute == null || passwordAttribute == null || nameAttribute == null || ageAttribute == null){
%>
Username:
<form name="createANewUser" method=get onsubmit="return check_all_fields(this)" action="CreateUser.jsp">
		<input type=hidden name="usernameAttribute" value="username">
		<input type=text name="usernameAttributeValue" length=30>
<BR>
Password: <BR>

		<input type=hidden name="passwordAttribute" value="password">
		<input type=text name="passwordAttributeValue" length=30>
<BR>
Sex (M/F/O) (Optional):<BR>

		<input type=hidden name="sexAttribute" value="sex">
		<input type=text name="sexAttributeValue" length=1>
<BR>
Name:<BR>

		<input type=hidden name="nameAttribute" value="name">
		<input type=text name="nameAttributeValue" length=30>
<BR>
	
Age:<BR>
		<input type=hidden name="ageAttribute" value="age">
		<input type=text name="ageAttributeValue" length=10>
<BR>
Description (optional):<BR>
		<input type=hidden name="descriptionAttribute" value="description">
		<input type=text name="descriptionAttributeValue" length=140>
<BR>	
Type of Account (optional):<BR>
		<select id="theUtype" name="theUtype">
			<option value = "empty"></option>
			<option value = "regular">Regular</option>
			<option value = "admin">Admin</option>
		</select>
<BR>
<input type=submit>

<% }
else{
	String username = request.getParameter("usernameAttributeValue");
	String theirName = request.getParameter("nameAttributeValue");
	String age = request.getParameter("ageAttributeValue");
	String password = request.getParameter("passwordAttributeValue");
	
	String description = request.getParameter("descriptionAttributeValue");
	String sex = request.getParameter("sexAttributeValue"); //optional of description not returning null
	boolean usertype = false;
	
	if(request.getParameter("theUtype").equals("admin"))
	{
		usertype = true;
	}
	
	Connector connection = new Connector();
	CreateUser createUser = new CreateUser(username,theirName,age,password);
	createUser.SetAllOptional(description, usertype, sex);
	createUser.AddUserToDatabase(connection.stmt); //need to check if this is successful
	connection.closeStatement();
	connection.closeConnection();
	%>
	Successfully created user.  Please return to login.
	<button type="button" onclick="location.href = '/~5530u47/MainWindow.jsp';" id="returnButton">Return</button>
	<%
 }

%>
</body>
</html>
