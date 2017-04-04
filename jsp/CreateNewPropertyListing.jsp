<%@ page language="java" import="cs5530.*" %>
<%@ page import="java.util.*" %>
<html>
<style>
h2{
	color:red
}
</style>
<head>
<script LANGUAGE="javascript">
function check_all_fields(form_obj){
	if( form_obj.housingCategoryAttributeValue.value == "" || form_obj.housingCityAttributeValue.value == "" || form_obj.housingStateAttributeValue.value == ""){
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
<h2>Invalid Login</h2><BR>
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
		<input type=text name="housingStateAttributeValue" length=30>
		<BR>
		<input type=submit>
		</form>
	<%
	}
	else
	{
		String housingCategory = request.getParameter("housingCategoryAttributeValue");
		String housingDescription = request.getParameter("housingDescriptionAttributeValue");
		String housingKeywords = request.getParameter("housingKeywordsAttributeValue");
		String housingLanguage = request.getParameter("housingKeywordsLanguageAttributeValue");
		String housingSquareFootage = request.getParameter("housingSquareFeetAttributeValue");
		String housingCarLimit = request.getParameter("housingCarLimitAttributeValue");
		String housingCity = request.getParameter("housingCityAttributeValue");
		String housingState = request.getParameter("housingStateAttributeValue");
		boolean housingNeighborsBool = false;

	if(request.getParameter("theNeighborType").equals("true"))
	{
		housingNeighborsBool = true;
	}
	Integer housingCarLimitInt = Integer.valueOf(housingCarLimit);
	Double housingSquareFootageDouble = Double.valueOf(housingSquareFootage);
	
	PropertyListing listing = new PropertyListing(login,housingCategory,housingDescription,housingSquareFootageDouble,housingCarLimitInt,housingNeighborsBool, housingCity, housingState);
	//Property added to tempHousing here					
	if(!listing.AddListing(connection.stmt))
	{%>
		<h2>Failed to add listing to the database!</h2><%
	}

	if(!listing.AddKeywords(housingKeywords, housingLanguage, connection.stmt, false, -1))
	{%>
		<h2>Failed to add keywords of the listing to the database!</h2><%
	}
	StringBuilder output = new StringBuilder();
	Reserve reservation = new Reserve(login, password);
	HashSet<String> houses = reservation.DisplayTempHousesAvailable(login, connection.stmt, false,output);
	List<String> housesList = new ArrayList<>(houses);
	Integer tempBig = 0;
	for(int i = 0; i < housesList.size(); i++)
	{
		Integer houseID = Integer.valueOf(housesList.get(i));
		if(houseID > tempBig)
		{
			tempBig = houseID;
		}
	}
	connection.closeStatement();
	connection.closeConnection();
	%>
	<h1>Proceed to adding availability dates to your new housing.</h1><BR>
	<button type="button" onclick="location.href = '/~5530u47/AddAvailabilityDates.jsp?thid=<%=tempBig%>';" id="proceedButton">Go to Availabilities</button>
	<%
	}
	%>
</body>
</html>