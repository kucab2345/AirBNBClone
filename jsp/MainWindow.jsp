<%@ page language="java" import="cs5530.*" %>
<html>
<style>
<!-- This adds the ability for enters to add newline characters
 body {
  white-space: pre;
 }-->
 .mainMenuButton{
	 height:10%;
	 width:25%;
	 font-size: 24px;
	 background-color: #CC0A4A;
	 color: black;
	 text-align: center;
	 border-style: solid;
 }
 
  .mainMenuButton:hover,
  .mainMenuButton:focus {
    color: black;
    cursor: pointer;
	background-color: #990133;
}
 
input[type=text], input[type=password] {
    width: 100%;
    padding: 12px 20px;
    margin: 8px 0;
    display: inline-block;
    border: 1px solid #ccc;
    box-sizing: border-box;
}
 
 button:hover {
    opacity: 0.8;
}


.cancelbtn {
    width: auto;
    padding: 10px 18px;
    background-color: #f44336;
}


.imgcontainer {
    text-align: center;
    margin: 24px 0 12px 0;
    position: relative;
}

img.avatar {
    width: 30%;
    border-radius: 50%;
}

.container {
    padding: 16px;
}

span.psw {
    float: right;
    padding-top: 16px;
}

.modal {
    display: none; 
    position: fixed;
    z-index: 1;
    left: 0;
    top: 0;
    width: 100%; 
    height: 100%; 
    overflow: auto; 
    background-color: rgb(0,200,0); 
    background-color: rgba(0,255,0,0.4);
    padding-top: 2%;
}


.modal-content {
    background-color: #baccff;
    margin: 5px auto; 
    border: 1px solid #888;
    width: 80%; 
}


.close {
    position: absolute;
    right: 25px;
    top: 0; 
    color: #000;
    font-size: 35px;
    font-weight: bold;
}


.close:hover,
.close:focus {
    color: red;
    cursor: pointer;
}

.animate {
    -webkit-animation: animatezoom 0.6s;
    animation: animatezoom 0.6s
}

@-webkit-keyframes animatezoom {
    from {-webkit-transform: scale(0)} 
    to {-webkit-transform: scale(1)}
}

@keyframes animatezoom {
    from {transform: scale(0)} 
    to {transform: scale(1)}
}

@media screen and (max-width: 300px) {
    span.psw {
       display: block;
       float: none;
    }
    .cancelbtn {
       width: 100%;
    }
}
</style>
<head>
<%
session = request.getSession();
session.setAttribute("theUser", null);
session.setAttribute("thePass", null);
%>
<center>| <h1>- Welcome to the Uotel AirBNB! -</h1> |</center>
</head>
<BR>
<body>
<center>
<font size="8">In order to access our services, you need to login:</font>
<BR>
<button class="mainMenuButton", onclick="LoginButton()">Login</button> <button class="mainMenuButton", onclick="CreateUserButton()">Create New User</button> </center>
<!-- The Modal -->
<div id="userLogin" class="modal">
  <span onclick="document.getElementById('userLogin').style.display='none'" 
class="close" title="Close Modal">&times;</span>

  <!-- Modal Content -->
  <form class="modal-content animate" action="/~5530u47/login_page.jsp">
    <div class="imgcontainer">
      <span onclick="document.getElementById('userLogin').style.display='none'" class="close" title="Close Modal">&times;</span>
      <img src="images/UofUAvatar.jpg" alt="University of Utah" class="avatar">
    </div>
    <div class="container">
      <label><b>Username</b></label>
      <input type="text" placeholder="Enter Username" name="user" required>

      <label><b>Password</b></label>
      <input type="password" placeholder="Enter Password" name="userpassword" required>

      <button type="submit">Login</button>
      <input type="checkbox" checked="checked"> Remember me
    </div>

    <div class="container" style="background-color:#f1f1f1">
      <button type="button" onclick="document.getElementById('userLogin').style.display='none'" class="cancelbtn">Cancel</button>
      
    </div>
  </form>
</div>

<script LANGUAGE="javascript">
var modal = document.getElementById('userLogin');

function CreateUserButton()
{
	var CreateUserJSPCall = "CreateUser.jsp";
	window.location.href = CreateUserJSPCall;
}
function LoginButton()
{
	modal.style.display='block';
}
// When the user clicks anywhere outside of the modal, close it
window.onclick = function(event) {
    if (event.target == modal) {
        modal.style.display = "none";
    }
}
</script>

</body>
</html>

