<%@ page language="java" import="cs5530.*" %>
<%@ page import="java.util.*" %>
<html>
<head>
<script LANGUAGE="javascript">
function check_all_fieldsBrowse(form_obj){
	if( form_obj.housingCategoryAttributeValue.value == "" || form_obj.housingCityAttributeValue.value == "" || form_obj.housingStateAttributeValue.value == ""){
		alert("One of your property's information box wasn't filled in when it should have been.");
		return false;
	}
	return true;
}
</script>
</head>
<body>
<h2>Temporary Housing Browsing!</h2>
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
	<input type=submit>
</form>
</body>
</html>