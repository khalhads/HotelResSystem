<!DOCTYPE html>
<html>
<head>
<%@ page import="com.MyHotel.AdminDispatcher"%> 
<title>
Hotel Reservation System
</title>
<link type = "text/css"  rel="stylesheet" href="${pageContext.request.contextPath}/css/HotelReservation.css"/>
  
<style>
body {
	width :50%;
}
#admin-page{
	background-color: green;
	text-align: left;
	font-size: 28px;
	color: GoldenRod;
}
</style>
</head>
<body>
 
 <form action="${pageContext.request.contextPath}/AdminDispatcher">
 <input type="hidden" name="page-type" value="AdminLogin">
  <div class="imgcontainer">
    <img src="${pageContext.request.contextPath}/images/hotel-reservation-service.jpg" alt="Royal Hotel" class="logo" />
  </div>
<div id="admin-page"> Hotel Reservation Admin Page</div>
  <div class="container">
    ${errmsg}
    <label><b>Email</b></label>
    <input type="text" placeholder="Enter Email" name="username" required>

    <label><b>Password</b></label>
    <input type="password" placeholder="Enter Password" name="password" required>

    <button type="submit">Login</button>  <button type="button" class="cancelbtn">Cancel</button>
    <input type="checkbox" checked="checked"> Remember me
     
  </div>
 
</body>
</html> 
 