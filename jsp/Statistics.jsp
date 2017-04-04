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

%>
Type of Statistic
		<select id="theUtype" name="theUtype">
			<option value = "empty"></option>
			<option value = "regular">Most Popular Housing</option>
			<option value = "admin">Most Expensive Housing</option>
			<option value = "admin">Highest Rated Housing</option>
		</select>
<BR>
<input type=submit>

<%
%>
</body>
</script>