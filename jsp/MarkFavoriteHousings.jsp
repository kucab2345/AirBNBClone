<%@ page language="java" import="cs5530.*" %>
<html>

<%
session = request.getSession();
String login = session.getAttribute("theUser").toString();
StringBuilder sb = new StringBuilder();

Connector connection = new Connector();
FavoriteTH favTH = new FavoriteTH();

favTH.showAllVisitedTH(login, connection.stmt, sb);
out.println(sb);

connection.closeStatement();
connection.closeConnection();
%>
</html>