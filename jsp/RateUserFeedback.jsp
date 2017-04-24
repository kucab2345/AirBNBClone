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

Connector connection = new Connector();
RateUserFeedback rate = new RateUserFeedback();
StringBuilder sb = new StringBuilder();
String login = session.getAttribute("theUser").toString();

rate.DisplayTempHouseTHIDS(connection.stmt,sb);

String[] thidList = sb.toString().split("\n");

for(int i = 0; i < thidList.length; i++)
{
	out.println(thidList[i]);%><BR><%
}
if(request.getParameter("THIDAttribute") == null)
{
%><BR>
THID to view feedbacks of<BR>
<form name="THID" method=get onsubmit="return check_all_fields(this)">
		<input type=hidden name="THIDAttribute" value="THID">
		<input type=text name="THIDValue" length=30>
<BR>
<input type=submit>

<BR>
	<button type="button" onclick="location.href = '/~5530u47/FeedbackMenu.jsp';" id="returnButton">Return</button>
<%
}
if(request.getParameter("THIDAttribute") != null)
{
	String theThid = request.getParameter("THIDValue");
	sb.setLength(0);
	rate.GetAllUsersFeedback(login, theThid, connection.stmt, sb);
	
	String[] feedbacks = sb.toString().split("\n");
	%><BR><%
	for(int i = 0; i < feedbacks.length; i++)
	{
		out.println(feedbacks[i]);%><BR><%
	}
	%><BR>
	FeedbackID to rate<BR>
	<form name="FeedbackID" method=get onsubmit="return check_all_fields(this)">
		<input type=hidden name="feedbackIDAttribute" value="feedbackID">
		<input type=text name="feedbackIDValue" length=30>
	<BR>
	Rating: 0: Useless, 1: Semi-Useful, 2: Useful<BR>
	<form name="Rating" method=get onsubmit="return check_all_fields(this)">
		<input type=hidden name="ratingAttribute" value="rating">
		<input type=text name="ratingAttributeValue" length=30>
	<BR>
	<input type=submit>
	<%
}
if(request.getParameter("ratingAttribute") != null && request.getParameter("feedbackIDAttribute") != null)
{
	String rating = request.getParameter("ratingAttributeValue");
	String feedbackID = request.getParameter("feedbackIDValue");
	rate.RateFeedback(login, rating, feedbackID, connection.stmt);
}

connection.closeStatement();
connection.closeConnection();
%>
</body>
</html>