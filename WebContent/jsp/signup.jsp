<!DOCTYPE html>
<html>
<head>
<%@ page import="com.MyHotel.CustomerDispatcher"%> 
<title>
Hotel Reservation System
</title>
<link type = "text/css"  rel="stylesheet" href="${pageContext.request.contextPath}/css/HotelReservation.css"/>
 <script>
// Get the modal
var modal = document.getElementById('id01');

// When the user clicks anywhere outside of the modal, close it
window.onclick = function(event) {
    if (event.target == modal) {
        modal.style.display = "none";
    }
}
</script> 
<style>
body {
	width :50%;
}
</style>
</head>
<body>
 
 <form action="${pageContext.request.contextPath}/CustomerDispatcher">
 <input type="hidden" name="page-type" value="signin">
  <div class="imgcontainer">
    <img src="${pageContext.request.contextPath}/images/hotel-reservation-service.jpg" alt="Royal Hotel" class="logo" />
  </div>

  <div class="container">
   ${errmsg}
    <label><b>Email</b></label>
    <input type="text" placeholder="Enter Email" name="username" required>

    <label><b>Password</b></label>
    <input type="password" placeholder="Enter Password" name="password" required>

    <button type="submit">Login</button>  <button type="button" class="cancelbtn">Cancel</button>
    <input type="checkbox" checked="checked"> Remember me
    <!-- Button to open the modal -->
<button onclick="document.getElementById('id01').style.display='block'">Sign Up</button>
  </div>

 
</form> 



<!-- The Modal (contains the Sign Up form) -->
<div id="id01" class="modal">
  <span onclick="document.getElementById('id01').style.display='none'" class="close" title="Close Modal">&times;</span>
  <form class="modal-content animate" action="${pageContext.request.contextPath}/CustomerDispatcher">
    <input type="hidden" name="page-type" value="signup">
    <div class="container">
    <label><b>First Name</b></label>
      <input type="text" placeholder="Enter First Name" name="firstname" required>
       <label><b>Last Name</b></label>
      <input type="text" placeholder="Enter Last Name" name="lastname" required>
      <label><b>Address</b></label>
      <input type="text" placeholder="Enter address" name="address" required>
      <label><b>Email</b></label>
      <input type="text" placeholder="Enter Email" name="username" required>

      <label><b>Password</b></label>
      <input type="password" placeholder="Enter Password" name="password" required>

      <label><b>Repeat Password</b></label>
      <input type="password" placeholder="Repeat Password" name="passwordRepeat" required>
      <input type="checkbox" checked="checked"> Remember me
      <p>By creating an account you agree to our <a href="#">Terms & Privacy</a>.</p>

      <div class="clearfix">
        <button type="button" onclick="document.getElementById('id01').style.display='none'" class="cancelbtn">Cancel</button>
        <button type="submit" class="signupbtn">Sign Up</button>
      </div>
    </div>
  </form>
</div> 
</body>
</html> 
 