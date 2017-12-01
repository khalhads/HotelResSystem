<!DOCTYPE html>
<html>
<head>
<%@ page import="java.util.Enumeration" %>
<%@ page import="com.MyHotel.AdminDispatcher"%>
<title>
Hotel Reservation System
</title>
<link type = "text/css"  rel="stylesheet" href="${pageContext.request.contextPath}/css/HotelReservation.css"/>

<style>
ul {
    list-style-type: none;
    margin: 0;
    padding: 0;
   
    background-color: black;
}

 
/* Change the link color on hover */
li a:hover {

    background-color: white;
    color: black;
}
li a  {
    background-color: #555;
    color: white;
 
}
.active {
    background-color: #4CAF50;
    color: white;
}
 

li {
 	  width: 175px !important;
  display: inline-block;
    border: 1px solid white;
     color: white;
     background-color: green;
}


.customer_message {
	margin:15%;
	background-color : green;
	text-align: center;
	color : GoldenRod;
	font-size : 28px;
	border: 5px solid GoldenRod;
}
#admin-page{
	background-color: green;
	text-align: left;
	font-size: 28px;
	color: GoldenRod;
}
</style>
 
</head>
<body> <div class="imgcontainer">
	<%
		if(!AdminDispatcher.checkIfCustomerLoggedIn(request, response))
			return;
	%>
    <img src="${pageContext.request.contextPath}/images/hotel-reservation-service.jpg" alt="Royal Hotel" class="logo" />
  </div>
  <div id="admin-page"> Hotel Reservation Admin Page</div>
 <ul>
  <li><a href="${pageContext.request.contextPath}/admin/CheckIn.jsp"">Checkin</a></li>
  <li><a href="${pageContext.request.contextPath}/admin/CheckOut.jsp">Checkout</a></li>
</ul> 
  ${customer_message}
</body>
</html> 
 