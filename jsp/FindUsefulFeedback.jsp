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
Connector connection = new Connector();
StringBuilder sb = new StringBuilder();

THFeedbackUseful feedbacks = new THFeedbackUseful();

if(request.getParameter("thidAttribute") == null || request.getParameter("maxAttribute") == null)
{
%>
THID of temporary housing you want feedback on<BR>
	<form name="THID" method=get onsubmit="return check_all_fields(this)">
		<input type=hidden name="thidAttribute" value="thid">
		<input type=text name="thidValue" length=30>
	<BR>
Maximum number of feedback ratings you'd like returned <BR>
	<form name="Maximum" method=get onsubmit="return check_all_fields(this)">
		<input type=hidden name="maxAttribute" value="max">
		<input type=text name="maxValue" length=30>
	<BR>
<input type=submit>
<%
}
else
{
	String thid = request.getParameter("thidValue");
	String max = request.getParameter("maxValue");
	feedbacks.getAllThidFeedbacks(thid, max, connection.stmt, sb);
	
	String[] feedback = sb.toString().split("\n");
	for(int i = 0; i < feedback.length; i++)
	{
		out.println(feedback[i]);%><BR><%
	}
	%>
	<button type="button" onclick="location.href = '/~5530u47/login_page.jsp?user=<%=login%>&userpassword=<%=password%>';" id="returnButton">Return</button><%
}
%>
</body>
</html>