<!DOCTYPE html>
<html>
<head>
 
<title>
Hotel Reservation System
</title>
<link type = "text/css"  rel="stylesheet" href="${pageContext.request.contextPath}/css/HotelReservation.css"/>
<%@ page import="java.util.Enumeration" %>
<%@ page import="com.MyHotel.CustomerDispatcher"%>
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
</style>
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
</head>
<body> <div class="imgcontainer">
	<%
		if(!CustomerDispatcher.checkIfCustomerLoggedIn(request, response))
			return;
	%>
    <img src="${pageContext.request.contextPath}/images/hotel-reservation-service.jpg" alt="Royal Hotel" class="logo" />
  </div>
 <ul>
  <li><a href="${pageContext.request.contextPath}/jsp/NewReservation.jsp"">Make New Reservation</a></li>
  <li><a href="${pageContext.request.contextPath}/jsp/ListReservation.jsp">List my Reservation</a></li>
  <li><a href="${pageContext.request.contextPath}/jsp/MakeReview.jsp">Make a Review</a></li>
</ul> 
  ${customer_message}
</body>
</html> 
 