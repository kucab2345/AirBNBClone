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
 }
 
 /* Full-width input fields */
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

/* Extra styles for the cancel button */
.cancelbtn {
    width: auto;
    padding: 10px 18px;
    background-color: #f44336;
}

/* Center the image and position the close button */
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
 /* The Modal (background) */
.modal {
    display: none; /* Hidden by default */
    position: fixed; /* Stay in place */
    z-index: 1; /* Sit on top */
    left: 0;
    top: 0;
    width: 100%; /* Full width */
    height: 100%; /* Full height */
    overflow: auto; /* Enable scroll if needed */
    background-color: rgb(0,200,0); /* Fallback color */
    background-color: rgba(0,255,0,0.4); /* Black w/ opacity */
    padding-top: 2%;
}

/* Modal Content/Box */
.modal-content {
    background-color: #baccff;
    margin: 5px auto; /* 15% from the top and centered */
    border: 1px solid #888;
    width: 80%; /* Could be more or less, depending on screen size */
}

/* The Close Button */
.close {
    /* Position it in the top right corner outside of the modal */
    position: absolute;
    right: 25px;
    top: 0; 
    color: #000;
    font-size: 35px;
    font-weight: bold;
}

/* Close button on hover */
.close:hover,
.close:focus {
    color: red;
    cursor: pointer;
}

/* Add Zoom Animation */
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

/* Change styles for span and cancel button on extra small screens */
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
<center>| <h1>- Welcome to the Uotel AirBNB! -</h1> |</center>
</head>
<BR>
<body>
<font size="8">In order to access our services, you need to login:</font>
<BR>
<center><button class="mainMenuButton", onclick="LoginButton()">Login</button> <button class="mainMenuButton">Create New User</button> <button class="mainMenuButton">Exit</button></center>

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
      <input type="text" placeholder="Enter Username" name="uname" required>

      <label><b>Password</b></label>
      <input type="password" placeholder="Enter Password" name="psw" required>

      <button type="submit">Login</button>
      <input type="checkbox" checked="checked"> Remember me
    </div>

    <div class="container" style="background-color:#f1f1f1">
      <button type="button" onclick="document.getElementById('userLogin').style.display='none'" class="cancelbtn">Cancel</button>
      <span class="psw">Forgot <a href="#">password?</a></span>
    </div>
  </form>
</div>
</body>

<script LANGUAGE="javascript">
var modal = document.getElementById('userLogin');
function LoginButton()
{
	modal.style.display='block'

}
// When the user clicks anywhere outside of the modal, close it
window.onclick = function(event) {
    if (event.target == modal) {
        modal.style.display = "none";
    }
}
</script>

