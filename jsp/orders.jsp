<%@ page language="java" import="cs5530.*" %>
<html>
<head>
<script LANGUAGE="javascript">

function check_all_fields(form_obj){
	alert(form_obj.searchAttribute.value+"='"+form_obj.attributeValue.value+"'");
	if( form_obj.attributeValue.value == ""){
		alert("Search field should be nonempty");
		return false;
	}
	return true;
}

</script> 
</head>
<body>

<%
String searchAttribute = request.getParameter("searchAttribute");
if( searchAttribute == null ){
%>

	Form1: Search orders on user name:
	<form name="user_search" method=get onsubmit="return check_all_fields(this)" action="orders.jsp">
		<input type=hidden name="searchAttribute" value="login">
		<input type=text name="attributeValue" length=10>
		<input type=submit>
	</form>
	<BR><BR>
	Form2: Search orders on director name:
	<form name="director_search" method=get onsubmit="return check_all_fields(this)" action="orders.jsp">
		<input type=hidden name="searchAttribute" value="director">
		<input type=text name="attributeValue" length=10>
		<input type=submit>
	</form>

<%

} else {

	String attributeValue = request.getParameter("attributeValue");
	Connector connector = new Connector();
	Order order = new Order();
	
%>  

  <p><b>Listing orders in JSP: </b><BR><BR>

  The orders for query: <b><%=searchAttribute%>='<%=attributeValue%>'</b> are  as follows:<BR><BR>
  <%=order.getOrders(searchAttribute, attributeValue, connector.stmt)%> <BR><BR>
  
  <b>Alternate way (servlet method):</b> <BR><BR>
  <%
		out.println("The orders for query: <b>"+searchAttribute+"='"+attributeValue+
					"'</b> are as follows:<BR><BR>");
		out.println(order.getOrders(searchAttribute, attributeValue, connector.stmt));
  %>

<%
 connector.closeStatement();
 connector.closeConnection();
}  // We are ending the braces for else here
%>

<BR><a href="orders.jsp"> New query </a></p>

<p>Schema for Order table: <font face="Geneva, Arial, Helvetica, sans-serif">( 
  title varchar(100), quantity integer, login varchar(8), director varchar(10) 
  )</font></p>

</body>
