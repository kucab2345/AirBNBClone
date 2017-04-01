<%@ page language="java" import="cs5530.*" %>
<html>
<style>
 .loginButtons{
	 height:5%;
	 width:30%;
	 font-size: 18px;
	 background-color: #CC0A4A;
	 color: white;
	 text-align: center;
 }
 
  .loginButtons:hover,
  .loginButtons:focus {
    color: black;
    cursor: pointer;
	background-color: #FFFFFF;
}
 
 .retryButton{
	 height:10%;
	 width:25%;
	 font-size: 36px;
 }
</style>
<head>
<h2>Feedback Menu</h2>
	<button class= "loginButtons", onclick="MarkFavoriteHousings()">1. Mark Favorite Housings </button><BR>
	<button class= "loginButtons", onclick="RateTempHousings()">2. Rate Temp Housings</button><BR>
	<button class= "loginButtons", onclick="RateUserFeedback()">3. Rate another user's feedback on temp housings</button><BR>
	<button class= "loginButtons", onclick="MarkUserTrusted()">4. Mark another user as trusted</button><BR>
	<button class= "loginButtons", onclick="FindUsefulFeedback()">5. Find useful feedback on a temp housing</button><BR>

</head>

<script LANGUAGE="javascript">
function MarkFavoriteHousings()
{
	window.location.href = "/~5530u47/MarkFavoriteHousings.jsp";
}
function RateTempHousings()
{
	//window.location.href = "/~5530u47/RateTempHousings.jsp";
}
function RateUserFeedback()
{
	//window.location.href = "/~5530u47/RateUserFeedback.jsp";
}
function MarkUserTrusted()
{
	//window.location.href = "/~5530u47/MarkUserTrusted.jsp";
}
function FindUsefulFeedback()
{
	//window.location.href = "/~5530u47/FindUsefulFeedback.jsp";
}
</script>
</html>