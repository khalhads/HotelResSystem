<!DOCTYPE html>
<html>
<head>
 
<title>
Hotel Reservation System
</title>
<link type = "text/css"  rel="stylesheet" href="${pageContext.request.contextPath}/css/HotelReservation.css"/>
 
<%@ page import="com.MyHotel.CustomerDispatcher" %>
 
 
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
<body>
<div class="imgcontainer">
    <img src="${pageContext.request.contextPath}/images/hotel-reservation-service.jpg" alt="Royal Hotel" class="logo" />
</div>
 <div id='dialog-hotel' title=' Update Hotel record '> 
	 <form action="${pageContext.request.contextPath}/CustomerDispatcher">
	 <input type="hidden" name="page-type" value="hotel">
        <fieldset class="ui-widget ui-widget-content ui-corner-all">
			<legend class="ui-widget ui-widget-header ui-corner-all">Please select a hotel</legend>
            <p class='data-entry'> 
              <label class="label" for='hotel_id'>  Hotel Name</label> 
              <select name='hotel_id' required >
              <%
              CustomerDispatcher.printAvailableHotels(out);	 
              %>
				  
				</select> 
            </p> 
            <button type="submit">OK</button>  <button type="button" class="cancelbtn">Cancel</button>
        </fieldset> 
     </form> 
  </div> 
</body>
</html> 
 