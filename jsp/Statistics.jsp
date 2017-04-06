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
String password = session.getAttribute("thePass").toString();
Connector connection = new Connector();
StringBuilder sb = new StringBuilder();

if(request.getParameter("statistic") == null || request.getParameter("maxResultsAttribute") == null)
{	
%>
Maximum number of results per housing category<BR>
	<form name="maxResults" method=get onsubmit="return check_all_fields(this)">
		<input type=hidden name="maxResultsAttribute" value="max">
		<input type=number name="maxResultsValue" min=1>
	<BR>

Type of Statistic <BR>
		<select id="statisticID" name="statistic">
			<option value = "empty"></option>
			<option value = "popular">Most Popular Housing</option>
			<option value = "expensive">Most Expensive Housing</option>
			<option value = "rated">Highest Rated Housing</option>
		</select>
<BR>
<input type=submit>

<%
}
else
{
	String max = request.getParameter("maxResultsValue");
	String statType = request.getParameter("statistic");
	StatPopularTH statTH = new StatPopularTH();
	if(statType.equals("popular"))
	{
		statTH.displayMostPopularTH(max, connection.stmt, sb);
	}
	else if(statType.equals("expensive"))
	{
		statTH.displayMostExpensiveTH(max, connection.stmt, sb);
	}
	else if(statType.equals("rated"))
	{
		statTH.displayHighestRatedTH(max, connection.stmt, sb);
	}
	
	String[] resultant = sb.toString().split("\n");
	for(int i = 0; i < resultant.length; i++)
	{
		out.println(resultant[i]);%><BR><%
	}
	%>
	Return to the Action Center
	<button type="button" onclick="location.href = '/~5530u47/login_page.jsp?user=<%=login%>&userpassword=<%=password%>';" id="returnButton">Return</button>
	<%
}
%>
</body>
</script>