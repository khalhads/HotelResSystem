package com.MyHotel;

import java.io.IOException;
import java.io.PrintStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspWriter;

import org.apache.commons.dbcp2.BasicDataSource;

import com.MyHotel.reservation.Breakfast;
import com.MyHotel.reservation.Breakfastoption;
import com.MyHotel.reservation.Creditcard;
import com.MyHotel.reservation.Customer;
import com.MyHotel.reservation.Discount;
import com.MyHotel.reservation.Hotel;
import com.MyHotel.reservation.Reservation;
import com.MyHotel.reservation.Room;
import com.MyHotel.reservation.Roomoption;
import com.MyHotel.reservation.Service;
import com.MyHotel.reservation.Serviceoption;

/**
 * Servlet implementation class MainDispatcher
 */

@WebServlet("/CustomerDispatcher")
public class CustomerDispatcher extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public static BasicDataSource dataSource;

	static {
		try {
			Class.forName("com.mysql.jdbc.Driver");

			BasicDataSource ds = new BasicDataSource();
			ds.setUrl("jdbc:mysql://cs336-hoteldbms.cwop6c6w5v0u.us-east-2.rds.amazonaws.com/MyHotelReservation");
			ds.setUsername("HotelDBMS");
			ds.setPassword("password");

			ds.setMinIdle(5);
			ds.setMaxIdle(10);
			ds.setMaxOpenPreparedStatements(100);

			dataSource = ds;
		} catch (ClassNotFoundException e) {
			System.err.println("Failed to create connection pool: exception " + e);
		}
	}

	/**
	 * Default constructor.
	 */
	public CustomerDispatcher() {

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		processRequest(request, response);
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		processRequest(request, response);
	}

	///////////////////// Main processing function
	///////////////////// /////////////////////////////////////

	public static BasicDataSource getDataSource() {
		return dataSource;
	}

	protected void processRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession(true);
		AuthRecord auth = (AuthRecord) session.getAttribute("auth");

		String pageType = request.getParameter("page-type");
		// System.out.println("Page Type: " + pageType);
		if (pageType == null) {
			request.getRequestDispatcher("/jsp/signup.jsp").forward(request, response);
			return;
		}

		Connection connection = null;
		try {
			connection = dataSource.getConnection();
			if (auth == null && processSignRequest(session, pageType, connection, request, response))
				return;

			switch (pageType) {
			case "hotel": {
				String hotel_id = request.getParameter("hotel_id");
				Hotel hotel = Hotel.fetchById(connection, hotel_id);
				System.out.println("hotel id " + hotel_id);
				System.out.println("hotel name " + hotel.getName());

				String heading = "<div class='welcome-heading'>Welcome to the " + hotel.getName() + " Hotel</div>";
				session.setAttribute("heading", heading);
				request.setAttribute("hotel_id", hotel_id);
				request.getRequestDispatcher("/jsp/CollectOptions.jsp").forward(request, response);
			}
				break;
			case "reserve": {
				// room_id=1&discount_id=1&room_price=199.89&room_description=Room+with+service&breakfast_id=2&
				// breakfast_price=25.89&breakfast_description=Nice+breakfast&service_id=2&service_price=22.56

				System.out.println("Reserve");
				// 'checkin_date' ='checkin_date' 'checkout_date' 'card_owner'
				// 'security_code' 'expiration_date' 'billing_address'

				String hotel_id = request.getParameter("hotel_id");
				String room_id = request.getParameter("room_id");
				String discount_id = request.getParameter("discount_id");
				String checkout_date = request.getParameter("checkout_date");
				String checkin_date = request.getParameter("checkin_date");

				String breakfast_id = request.getParameter("breakfast_id");

				String service_id = request.getParameter("service_id");
				String service_price = request.getParameter("service_price");

				String card_owner = request.getParameter("card_owner");
				String card_no = request.getParameter("card_no");
				String security_code = request.getParameter("security_code");
				String expiration_date = request.getParameter("expiration_date");
				String billing_address = request.getParameter("billing_address");

				int invoiceNo = exceuteCustomerReservation(
						connection, 
						auth.user_id, 
						hotel_id, 
						room_id, 
						checkin_date,
						checkout_date,
						discount_id,
						breakfast_id,
						service_id,
						card_owner, 
						card_no, 
						security_code, 
						expiration_date, 
						billing_address
				);
				request.setAttribute("customer_message", "<div class='customer_message'>Customer Invoice no "
						+ invoiceNo + ". Please keep for reference</div>");
				request.getRequestDispatcher("/jsp/index.jsp").forward(request, response);
			}
			}

		} catch (Exception e) {
			request.setAttribute("errmsg", "Failed to excute request with exception " + e);
			request.getRequestDispatcher("/jsp/signup.jsp").forward(request, response);
			return;
		} finally {
			if (connection != null)
				try {
					connection.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
	}

	/////////////////////////////// support processing functions
	/////////////////////////////// /////////////////////////

	private int exceuteCustomerReservation(
			Connection connection, 
			int customer_id, 
			String hotel_id, 
			String room_id,
			String checkin_date,
			String checkout_date,
			String discount_id,
			String breakfast_id,
			String service_id, 
			String card_owner, 
			String card_no, 
			String security_code, 
			String expiration_date,
			String billing_address) throws SQLException {
		Roomoption roomOption = new Roomoption();

		roomOption.setRoomId(roomOption.parseInt(room_id));
		roomOption.setDiscountId(roomOption.parseInt(discount_id));
		 
		int roomOptionId = roomOption.insertRecord(connection);

		Breakfastoption breakfastoption = new Breakfastoption();
		breakfastoption.setBreakfastId(breakfastoption.parseInt(breakfast_id));
	 
		int breakfastOptionId = breakfastoption.insertRecord(connection);

		Serviceoption serviceoption = new Serviceoption();
		serviceoption.setServiceId(serviceoption.parseInt(service_id));
	 
		int serviceOptionId = serviceoption.insertRecord(connection);

		
		
		Creditcard card = new Creditcard();
		card.setCardNumber(card_no);
		card.setPersonName(card_owner);
		card.setSecurityCode(security_code);
		card.setExpirationDate(expiration_date);
		card.setBillingAddress(billing_address);
		
		int creditId = card.insertRecord(connection);
	 
		Reservation reservation = new Reservation();
		reservation.setBreakfastOptionId(breakfastOptionId);
		reservation.setServiceOptionId(serviceOptionId);
		reservation.setRoomOptionId(roomOptionId);
		reservation.setCreditcardId(creditId);
		reservation.setCustomerId(customer_id);
		reservation.setCheckin(checkin_date);
		reservation.setCheckout(checkout_date);
		
		Room room = Room.fetchById(connection, room_id);
		Breakfast breakfast = Breakfast.fetchById(connection, breakfast_id);
		Service service = Service.fetchById(connection, service_id);

		reservation.setTotalCost(room.getPrice() + breakfast.getPrice() + service.getPrice());
		return reservation.insertRecord(connection);
	}

	private boolean processSignRequest(HttpSession session, String pageType, Connection connection,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		if ("signin".equals(pageType)) {
			String username = request.getParameter("username");
			String password = request.getParameter("password");

			List<Customer> customerList = Customer.fetchWithJoin(connection, " where t.login_id = ? and t.password = ?",
					username, password);
			System.out.println("customerList " + customerList);
			if (customerList == null || customerList.size() == 0) {

				request.setAttribute("errmsg",
						"<div id='err-msg'>Failed to login. Please press the Signup button if you are not registered </div>");
				request.getRequestDispatcher("/jsp/signup.jsp").forward(request, response);
				return true;
			}
			System.out.println("customerList " + customerList);
			Customer customer = customerList.get(0);

			if (password.equals(customer.getPassword())) // check also for
															// username
			{
				AuthRecord authRecord = new AuthRecord();
				authRecord.user_id = customer.getId();
				session.setAttribute("auth", authRecord);
				request.setAttribute("errmsg", "");
				request.getRequestDispatcher("/jsp/index.jsp").forward(request, response);
			} else {
				request.setAttribute("errmsg",
						"<div id='err-msg'>Failed to login. Please press the Signup button if you are not registered </div>");
				request.getRequestDispatcher("/jsp/signup.jsp").forward(request, response);
			}

			return true;
		}
		if ("signup".equals(pageType)) {
			String firstname = request.getParameter("firstname");
			String lastname = request.getParameter("lastname");
			String address = request.getParameter("address");

			String username = request.getParameter("username");
			String password = request.getParameter("password");
			String passwordRepeat = request.getParameter("passwordRepeat");

			Customer customer = new Customer();
			customer.setFirstName(firstname);
			customer.setLastName(lastname);
			customer.setAddress(address);
			customer.setLoginId(username);
			customer.setPassword(password);
			customer.insertRecord(connection);

			AuthRecord authRecord = new AuthRecord();
			authRecord.user_id = customer.getId();
			session.setAttribute("auth", authRecord);

			request.setAttribute("errmsg", "");
			request.getRequestDispatcher("/jsp/index.jsp").forward(request, response);

			return true;
		}
		return false;
	}

	////////////////////// static Support functions called from JSPs
	////////////////////// /////////////////////////////////////////////

	public static boolean checkIfCustomerLoggedIn(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession(true);
		AuthRecord auth = (AuthRecord) session.getAttribute("auth");

		if (auth == null) {
			// request.getContextPath() +
			request.getRequestDispatcher("signup.jsp").forward(request, response);
			return false;
		}
		return true;
	}

	public static void printAvailableRooms(String hotel_id, JspWriter out) {
		Connection connection = null;
		try {
			connection = dataSource.getConnection();
			List<Room> list = Room.fetchWithJoin(connection, " where t.hotel_id = ?", hotel_id);
			if (list != null && list.size() > 0) {
				for (Room r : list)
					out.println("<option value='" + r.getId() + "'>" + "Room: " + r.getRoomNo() + ", Floor: "
							+ r.getFloorNo() + ", Type: " + r.getRoomType() + ", Capacity: " + r.getMaxPeople()
							+ "</option>");
			}
		} catch (Exception e) {
			try {
				out.println("<option value='0'>No Rooms available currently</option>");
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

		} finally {
			if (connection != null)
				try {
					connection.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}

	}

	public static void printAvailableDiscounts(String hotel_id, JspWriter out) {
		Connection connection = null;
		try {
			connection = dataSource.getConnection();
			List<Discount> list = Discount.fetchWithJoin(connection, " where t.hotel_id = ?", hotel_id);
			if (list != null && list.size() > 0) {
				for (Discount d : list)
					out.println("<option value='" + d.getId() + "'>" + "Discounted from day: " + d.getFromday()
							+ " to day: " + d.getToday() + "</option>");
			}
		} catch (Exception e) {
			try {
				out.println("<option value='0'>No Rooms available currently</option>");
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

		} finally {
			if (connection != null)
				try {
					connection.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
	}

	public static void printAvailableBreakfasts(String hotel_id, JspWriter out) {
		Connection connection = null;
		try {
			connection = dataSource.getConnection();
			List<Breakfast> list = Breakfast.fetchWithJoin(connection, " where t.hotel_id = ?", hotel_id);
			if (list != null && list.size() > 0) {
				for (Breakfast b : list)
					out.println("<option value='" + b.getId() + "'>" + "Breakfast type: " + b.getType() + "</option>");
			}
		} catch (Exception e) {
			try {
				out.println("<option value='0'>No Breakfast available currently</option>");
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

		} finally {
			if (connection != null)
				try {
					connection.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
	}

	public static void printAvailableServices(String hotel_id, JspWriter out) {
		Connection connection = null;
		try {
			connection = dataSource.getConnection();
			List<Service> list = Service.fetchWithJoin(connection, " where t.hotel_id = ?", hotel_id);
			if (list != null && list.size() > 0) {
				for (Service s : list)
					out.println("<option value='" + s.getId() + "'>" + "Service type: " + s.getType() + "</option>");
			}
		} catch (Exception e) {
			try {
				out.println("<option value='0'>No Breakfast available currently</option>");
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

		} finally {
			if (connection != null)
				try {
					connection.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
	}

	public static void printAvailableHotels(JspWriter out) {
		Connection connection = null;
		try {
			connection = dataSource.getConnection();
			List<Hotel> list = Hotel.fetchWithJoin(connection, "");
			if (list != null && list.size() > 0) {
				for (Hotel h : list)
					out.println("<option value='" + h.getId() + "'>" + h.getName() + "</option>");
			}
		} catch (Exception e) {
			try {
				out.println("<option value='0'>No Hotels available currently</option>");
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

		} finally {
			if (connection != null)
				try {
					connection.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}

	}

}
