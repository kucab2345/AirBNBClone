<%@ page language="java" import="cs5530.*" %>
<%@ page import="java.util.*" %>
<html>
<head>
<script LANGUAGE="javascript">
function check_all_fields(form_obj){
	if( form_obj.usernameAttributeValue.value == "" || form_obj.passwordAttributeValue.value == "" || form_obj.nameAttributeValue.value == "" || form_obj.ageAttributeValue.value == ""){
		alert("One of your property's information box wasn't filled in when it should have been.");
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
Login log = new Login();
	if(!log.LoginToServer(login, password, connection.stmt))
	{
%>
<h1>Invalid Login</h1><BR>
	<form action="/~5530u47/MainWindow.jsp" method="POST">
		<button type="submit", class="retryButton">Go Back</button>
	</form>
	<%
	}
	String housingCategoryAttribute = request.getParameter("housingCategoryAttribute");
	String housingCityAttribute = request.getParameter("housingCityAttribute");
	String housingStateAttribute = request.getParameter("housingStateAttribute");
	if(housingCategoryAttribute == null || housingCityAttribute == null || housingStateAttribute == null)
	{
	%>
	To create a new housing listing, please fill in the following:<BR>
	<form name="createANewHouse" method=get onsubmit="return check_all_fields(this)" action="CreateNewPropertyListing.jsp">
	Housing Category (max 50 characters):<BR>
		<input type=hidden name="housingCategoryAttribute" value="category">
		<input type=text name="housingCategoryAttributeValue" length=50>
		<BR>
	Housing Description (optional, max 140 characters):<BR>
			<input type=hidden name="housingDescriptionAttribute" value="description">
		<input type=text name="housingDescriptionAttributeValue" length=140>
		<BR>
	Housing Keywords (optional, max 50 characters):<BR>
	<input type=hidden name="housingKeywordsAttribute" value="keywords">
		<input type=text name="housingKeywordsAttributeValue" length=50>
		<BR>
	Keywords Language (optional, max 20 characters):<BR>
	<input type=hidden name="housingKeywordsLanguageAttribute" value="language">
		<input type=text name="housingKeywordsLanguageAttributeValue" length=20>
		<BR>
	Housing Square Footage (optional, can have decimal values):<BR>
	<input type=hidden name="housingSquareFeetAttribute" value="footage">
		<input type=number name="housingSquareFeetAttributeValue" min=1 step="0.001">
		<BR>
	Housing Car Limit (optional, max 2,147,483,647):<BR>
	<input type=hidden name="housingCarLimitAttribute" value="carLimit">
		<input type=number name="housingCarLimitAttributeValue" min=0 max=2147483647>
		<BR>
	Housing Has Neighbors:<BR>
		<select id="theNeighborType" name="theNeighborType">
			<option value = "empty"></option>
			<option value = "false">No Neighbors</option>
			<option value = "true">Has Neighbors</option>
		</select>
		<BR>
	Housing City Location (max 30 characters):<BR>
	<input type=hidden name="housingCityAttribute" value="city">
		<input type=text name="housingCityAttributeValue" length=30>
		<BR>
	Housing State Location (max 30 characters): <BR>
	<input type=hidden name="housingStateAttribute" value="state">
		<input type=text name="usernameAttributeValue" length=30>
		<BR>
		<input type=submit>
		</form>
	<%
	}
	%>
	
</body>
</html>