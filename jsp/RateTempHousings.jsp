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
FeedbackTH feedbackTH = new FeedbackTH();

feedbackTH.showAllVisitedTH(login, connection.stmt, sb);

String[] lines = sb.toString().split("\n");

int fourCounter = 0;
for(int i = 0; i < lines.length; i++)
{
	out.println(lines[i]);%><BR><%
	fourCounter++;
	if(fourCounter == 4)
	{
		out.println("==================================");%><BR><%
		fourCounter = 0;
	}
}

if(request.getParameter("THIDAttribute") == null || request.getParameter("starAttribute") == null)
{
%>
THID<BR>
<form name="THID" method=get onsubmit="return check_all_fields(this)">
		<input type=hidden name="THIDAttribute" value="THID">
		<input type=text name="THIDValue" length=30>
<BR>
Feedback (50 character max)<BR>
<form name="text" method=get onsubmit="return check_all_fields(this)">
		<input type=hidden name="textAttribute" value="text">
		<input type=text name="textAttributeValue" length=50>
<BR>
Keywords (seperated by a space)<BR>
<form name="keywords" method=get onsubmit="return check_all_fields(this)">
		<input type=hidden name="keywordAttribute" value="keyword">
		<input type=text name="keywordAttributeValue" length=30>
<BR>
Star Rating (1-10)<BR>
<form name="starRating" method=get onsubmit="return check_all_fields(this)">
		<input type=hidden name="starAttribute" value="star">
		<input type=text name="starAttributeValue" length=30>
<BR>
<input type=submit>
<%
}
else
{
	String THID = request.getParameter("THIDValue");
	String description = request.getParameter("textAttributeValue");
	String keywords = request.getParameter("keywordAttributeValue");
	String starRating = request.getParameter("starAttributeValue");
	
	if(feedbackTH.MarkTHFeedback(description, THID, login, keywords, starRating, connection.stmt))
	{
		out.println("Successfully added your feedback!");%>
		<button type="button" onclick="location.href = '/~5530u47/FeedbackMenu.jsp';" id="returnButton">Return</button><%
	}
	else
	{
		out.println("Could not add your feedback!");%>
		<button type="button" onclick="location.href = '/~5530u47/FeedbackMenu.jsp';" id="returnButton">Return</button><%
	}
}
connection.closeStatement();
connection.closeConnection();	
%>
</body>
</html>