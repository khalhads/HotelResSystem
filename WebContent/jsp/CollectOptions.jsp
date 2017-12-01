<!DOCTYPE html>
<html>
<head>

<title>Hotel Reservation System</title>
<link type="text/css" rel="stylesheet"
	href="${pageContext.request.contextPath}/css/HotelReservation.css" />
<%@ page import="com.MyHotel.CustomerDispatcher"%>


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
	width: 90%;
}

.welcome-heading {
	background-color: green;
	text-align: center;
	font-size: 28px;
	color: GoldenRod;
}

fieldset {
	border: 2px solid green
}

legend {
	padding: 0.2em 0.5em;
	border: 1px solid green;
	color: green;
	font-size: 90%;
	text-align: right;
}

p {
	line-height: 20%;
}

</style>
</head>
<body>
	<%
		if(!CustomerDispatcher.checkIfCustomerLoggedIn(request, response))
			return;
	%>
	<div class="imgcontainer">
		<img
			src="${pageContext.request.contextPath}/images/hotel-reservation-service.jpg"
			alt="Royal Hotel" class="logo" />
	</div>
	${heading}
	<div id='dialog-reservation' title='Customer Reservation Form '>

		<form action="${pageContext.request.contextPath}/CustomerDispatcher">
		 <input type="hidden" name="page-type" value="reserve">
		 <input type="hidden" name="hotel_id" value="${hotel_id}">
			<fieldset>
				<legend>Room Option</legend>
				<table>
					<tr>
						<td><label class="label" for='room_id'> Room </label></td>
						<td colspan="2"><select name='room_id' required>
								<%
									CustomerDispatcher.printAvailableRooms((String) request.getParameter("hotel_id"), out);
								%>
						</select></td>
					</tr>
					<tr>
						<td><label class="label" for='discount_id'> Discount
						</label></td>
						<td colspan="2"><select name='discount_id' required>
								<%
									CustomerDispatcher.printAvailableDiscounts((String) request.getParameter("hotel_id"), out);
								%>
						</select></td>
						 
					</tr>
					<tr>
						<td><label class="label" for='checkin_date'> Checkin Date
						</label></td>
						<td><input  type="date" class='input-field' name='checkin_date'
							id='checkin_date' required /></td>
						<td><label class="label" for='checkout_date'>Checkout Date
						</label></td>
						<td><input  type="date" class='input-field' name='checkout_date'
							id='checkout_date' required /></td>
					</tr>
				</table>
			</fieldset>
			<fieldset>
				<legend>Breakfast Option</legend>

				<table>
					<tr>
						<td><label class="label" for='breakfast_id'>
								Breakfast </label></td>
						<td colspan="2"><select name='breakfast_id' required>
								<%
									CustomerDispatcher.printAvailableBreakfasts((String) request.getParameter("hotel_id"), out);
								%>
						</select></td>
					 
					</tr>
				</table>
			</fieldset>
			<fieldset>
				<legend>Service Option</legend>

				<table>
					<tr>
						<td><label class="label" for='service_id'> Service </label></td>
						<td colspan="2"><select name='service_id' required>
								<%
									CustomerDispatcher.printAvailableServices((String) request.getParameter("hotel_id"), out);
								%>
						</select></td>
					</tr>
				</table>

			</fieldset>
			<fieldset>
				<legend>Creadit Card Info</legend>

				<table>
					<tr>
					<td><label class="label" for='card_owner'>Name on Card</label></td>
						<td><input type='text' class='input-field'
							name='card_owner' id='card_owner' required /></td>
						<td><label class="label" for='card_no'> Card No </label></td>
						<td><input type='text' class='input-field'
							name='card_no' id='card_no' required /></td>
						
						<td><label class="label" for='security_code'>Security code</label></td>
						<td><input type='text' class='input-field'
							name='security_code' id='security_code' required /></td>
							<td><label class="label" for='expiration_date'>Expiration Date
						</label></td>
						<td><input  type="date" class='input-field' name='expiration_date'
							id='expiration_date' required /></td>	
					</tr>
				</table>
					<p class='data-entry'>
					<label class="label" for='billing_address'> Biilng Address </label> <input
						type='text' class='input-field' name='billing_address'
						id='billing_address' required />
				</p>
			</fieldset>
			<button type="submit">Reserve</button>
			<button type="button" class="cancelbtn">Cancel</button>
		</form>
	</div>
</body>
</html>
