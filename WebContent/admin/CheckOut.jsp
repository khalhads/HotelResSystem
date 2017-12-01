<!DOCTYPE html>
<html>
<head>
 
<title>
Hotel Reservation System
</title>
<link type = "text/css"  rel="stylesheet" href="${pageContext.request.contextPath}/css/HotelReservation.css"/>
 
<%@ page import="com.MyHotel.AdminDispatcher" %>
 
 
 <style>
#admin-page{
	background-color: green;
	text-align: left;
	font-size: 28px;
	color: GoldenRod;
}
</style> 
</head>
<body>
<div class="imgcontainer">
    <img src="${pageContext.request.contextPath}/images/hotel-reservation-service.jpg" alt="Royal Hotel" class="logo" />
</div>
#admin-page{
	background-color: green;
	text-align: left;
	font-size: 28px;
	color: GoldenRod;
}
 <div id='dialog-hotel' title=' Customer Checkout '> 
	 <form action="${pageContext.request.contextPath}/AdminDispatcher">
	 <input type="hidden" name="page-type" value="checkout">
        <fieldset class="ui-widget ui-widget-content ui-corner-all">
			<legend class="ui-widget ui-widget-header ui-corner-all">Customer Checkout</legend>
            <p class='data-entry'> 
              <label><b>Customer First Name</b></label>
   			 <input type="text" placeholder="Enter Customer First name" name="firstname" required>
            </p> 
            <p class='data-entry'> 
              <label><b>Last Name</b></label>
   			 <input type="text" placeholder="Enter Customer Last name" name="lastname" required>
            </p> 
            <p class='data-entry'> 
              <label><b>Invoice Id</b></label>
   			 <input type="text" placeholder="Enter Customer Last name" name="invoiceid">
            </p>
            <button type="submit">Checkout</button>  <button type="button" class="cancelbtn">Cancel</button>
        </fieldset> 
     </form> 
  </div> 
</body>
</html> 
 