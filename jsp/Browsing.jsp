<%@ page language="java" import="cs5530.*" %>
<%@ page import="java.util.*" %>
<html>
<head>
<script LANGUAGE="javascript">
function check_all_fieldsBrowse(form_obj){
	if( form_obj.houseSearchType.value == "" || form_obj.houseOrderType.value == ""){
		alert("One of your property's information box wasn't filled in when it should have been.");
		return false;
	}
	return true;
}
function check_all_fieldsCombination(form_obj){
	if( form_obj.houseSearchType.value == "" || form_obj.houseOrderType.value == ""){
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
THBrowsing browse = new THBrowsing(login);
if(!log.LoginToServer(login, password, connection.stmt))
{
%>
<h2>Invalid Login</h2><BR>
<form action="/~5530u47/MainWindow.jsp" method="POST">
	<button type="submit", class="retryButton">Go Back</button>
</form>
<%
}
String houseSearch = request.getParameter("houseSearchType");
String houseOrder = request.getParameter("houseOrderType");
if(houseSearch == null || houseOrder == null)
{
%>
<h2>Temporary Housing Browsing!</h2>
<h3> Of Available Properties</h3>
<form name="houseBrowsingForm" method=get onsubmit="return check_all_fieldsBrowse(this)" action="Browsing.jsp">
Select terms to return from search:<BR>
	<select id="houseSearchType" name="houseSearchType">
		<option value = "price">Price per Night</option>
		<option value = "city">City</option>
		<option value = "state">State</option>
		<option value = "keywords">Keywords</option>
		<option value = "category">Category</option>
		<option value = "combination">2 or More of the Above Options</option>
	</select>
	<BR>
Select how you would like to order the information:<BR>
	<select id="houseOrderType" name="houseOrderType">
		<option value="a.pricePerNight">By Price</option>
		<option value="(SELECT AVG(starRating) FROM feedback WHERE thid = t.thid)">By Average Feedback Score</option>
		<option value="(SELECT AVG(starRating) FROM feedback WHERE thid = t.thid AND login = ANY(SELECT loginTrusted FROM trusted WHERE isTrusted = TRUE))">By Average Feedback Score of Trusted Users</option>
	</select>
	<BR>
	<input type=submit>
</form>
<%
}
else
{
	String priceCheck = request.getParameter("priceCheck");
	String cityCheck = request.getParameter("cityCheck");
	String stateCheck = request.getParameter("stateCheck");
	String keywordsCheck = request.getParameter("keywordsCheck");
	String categoryCheck = request.getParameter("categoryCheck");
	if((priceCheck == null || cityCheck == null || stateCheck == null || keywordsCheck == null || categoryCheck == null) && houseSearch.equals("combination"))
	{
		%>
		Pick which items you would like to include in your search:<BR>
		<form name="houseBrowseCombine" method=get onsubmit="return check_all_fieldsCombination(this)" action="Browsing.jsp">
			Type yes if you want to include the option, otherwise leave it blank.<BR>
			Price per Night: <input type=text name="priceCheck" length=10><BR>
			City: <input type=text name="cityCheck" length=10><BR>
			State: <input type=text name="stateCheck" length=10><BR>
			Keywords: <input type=text name="keywordsCheck" length=10><BR>
			Category: <input type=text name="categoryCheck" length=10><BR>
			<input type=hidden name="houseSearchType" value="<%=houseSearch%>">
			<input type=hidden name="houseOrderType" value="<%=houseOrder%>">
			<input type=submit>
		</form>
		<%
	}
	else
	{
		String browsePrice = "";
		String browseCity = "";
		String browseState = "";
		String browseKeywords = ""; 
		String browseCategory = "";
		if(houseSearch.equals("price"))
		{
			browsePrice = ", a.pricePerNight";
		}
		else if(houseSearch.equals("city"))
		{
			browseCity = ", t.city";
		}
		else if(houseSearch.equals("state"))
		{
			browseState = ", t.state";
		}
		else if(houseSearch.equals("keywords"))
		{
			browseKeywords = ", w.words"; 
		}
		else if(houseSearch.equals("category"))
		{
			browseCategory = ", t.category";
		}
		else if(houseSearch.equals("combination"))
		{
			priceCheck = priceCheck.toLowerCase();
			cityCheck = cityCheck.toLowerCase();
			stateCheck = stateCheck.toLowerCase();
			keywordsCheck = keywordsCheck.toLowerCase();
			categoryCheck = categoryCheck.toLowerCase();
			if(priceCheck.equals("yes") || priceCheck.equals("y"))
			{
				browsePrice = ", a.pricePerNight";
			}
			if(cityCheck.equals("yes") || cityCheck.equals("y"))
			{
				browseCity = ", t.city";
			}
			if(stateCheck.equals("yes") || stateCheck.equals("y"))
			{
				browseState = ", t.state";
			}
			if(keywordsCheck.equals("yes") || keywordsCheck.equals("y"))
			{
				browseKeywords = ", w.words";
			}
			if(categoryCheck.equals("yes") || categoryCheck.equals("y"))
			{
				browseCategory = ", t.category";
			}
		}
		
		StringBuilder res = new StringBuilder();
		browse.RequestHousingInformation(browsePrice, browseCity, browseState, browseKeywords, browseCategory, houseOrder, connection.stmt, res);
		out.println(res.toString());
		%>
		
		<BR>
		<button type="button" onclick="location.href = '/~5530u47/login_page.jsp?user=<%=login%>&userpassword=<%=password%>';" id="returnButton">Return to Action Page</button>
		<%
	}
}%>
</body>
</html>