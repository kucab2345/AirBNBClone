<%@ page language="java" import="cs5530.*" %>
<%@ page import="java.util.*" %>
<html>
<head>
<script LANGUAGE="javascript">
function check_all_fieldsBrowse(form_obj){
	if(form_obj.price1.value == "" && form_obj.price2.value == "" && form_obj.city.value == ""  && form_obj.state.value == "" && form_obj.keywords.value == "" && form_obj.category.value == "")
	{
		alert("Please enter some search terms. If you would like to return all available, put in 0 for starting price range and submit.");
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

function changeInputBox()
{
	var item = document.getElementById("houseSearchType").value;
	var inputThings = document.getElementById("inputStuff");
	if(item.equals("price"))
	{
		
	}
	else if(item.equals("city"))
	{
		
	}
	else if(item.equals("state"))
	{
		
	}
	else if(item.equals("keywords"))
	{
		
	}
	else if(item.equals("category"))
	{
		
	}
	else if(item.equals("combination"))
	{
	inputThings.innerHTML = "";	
	}
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
String combinationType = request.getParameter("combinationType");
String houseOrder = request.getParameter("houseOrderType");
if(combinationType == null || houseOrder == null)
{
%>
<h2>Temporary Housing Browsing!</h2>
<h3> Of Available Properties</h3>
<form name="houseBrowsingForm" method=get onsubmit="return check_all_fieldsBrowse(this)" action="Browsing.jsp">
Type into each box what you would like to search:<BR>
Price per Night Range: <input type=number name="price1" min=0> to <input type=number name="price2" min=0><BR>
City: <input type=text name="city" length=30><BR>
State: <input type=text name="state" length=30><BR>
Keywords: <input type=text name="keywords" length=50><BR>
Category: <input type=text name="category" length=50><BR>
	<BR>
Please choose how you would like your query to be interpreted:<BR>
<select id="combinationType" name="combinationType">
	<option value="AND">AND</option>
	<option value="OR">OR</option>
</select><BR>
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
	String browsePrice = "";
	String browseCity = "";
	String browseState = "";
	String browseKeywords = ""; 
	String browseCategory = "";
	String price1 = request.getParameter("price1");
	String price2 = request.getParameter("price2");
	String city = request.getParameter("city");
	String state = request.getParameter("state");
	String keywords = request.getParameter("keywords");
	String category = request.getParameter("category");

	if(!price1.equals("") && !price2.equals(""))
	{
		browsePrice = " (a.pricePerNight >= " + price1 + " AND a.pricePerNight <= " + price2 + ") ";
	}
	else if(!price1.equals("") && price2.equals(""))
	{
		browsePrice =  " (a.pricePerNight >= " + price1 + ") ";
	}
	else if(price1.equals("") && !price2.equals(""))
	{
		browsePrice =  " (a.pricePerNight <= " + price2 + ") ";
	}
	if(!city.equals(""))
	{
		browseCity =  " (t.city = \"" + city + "\") ";
	}
	if(!state.equals(""))
	{
		browseState = " (t.state = \"" + state + "\") ";
	}
	if(!keywords.equals(""))
	{
		String[] words = keywords.split(" ");
		for(int i = 0; i < words.length; i++)
		{
			if(i == 0)
			{
				browseKeywords =  " (w.words LIKE \"%" + words[i] + "%\"";
			}
			else
			{
				browseKeywords +=  " OR w.words LIKE \"%" + words[i] + "%\"";
			}	
		}
		browseKeywords += ") ";
	}
	if(!category.equals(""))
	{
		browseCategory =  " (t.category = \"" + category + "\") ";
	}
		
	StringBuilder res = new StringBuilder();
	boolean success = browse.RequestHousingInformation(browsePrice, browseCity, browseState, browseKeywords, browseCategory, houseOrder, connection.stmt, res, combinationType);
	
	out.println(res.toString());
	%>
		
	<BR>
	<button type="button" onclick="location.href = '/~5530u47/login_page.jsp?user=<%=login%>&userpassword=<%=password%>';" id="returnButton">Return to Action Page</button>
	<%
}%>
</body>
</html>