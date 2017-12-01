package com.MyHotel;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspWriter;

import org.apache.commons.dbcp2.BasicDataSource;

import com.MyHotel.reservation.Admin;
import com.MyHotel.reservation.Breakfast;
import com.MyHotel.reservation.Breakfastoption;
import com.MyHotel.reservation.Customer;
import com.MyHotel.reservation.Discount;
import com.MyHotel.reservation.Hotel;
import com.MyHotel.reservation.Reservation;
import com.MyHotel.reservation.Room;
import com.MyHotel.reservation.Roomoption;
import com.MyHotel.reservation.Service;
import com.MyHotel.reservation.Serviceoption;

import static com.MyHotel.CustomerDispatcher.getDataSource;
/**
 * Servlet implementation class AdminDispatch
 */
@WebServlet("/AdminDispatcher")
public class AdminDispatcher extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AdminDispatcher() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}

	///////////////////// Main processing function /////////////////////////////////////
	
	private void checkInOut(HttpServletRequest request, HttpServletResponse response, Connection connection, boolean Checkin) throws Exception {
		String firstname = request.getParameter("firstname");
		String lastname = request.getParameter("lastname");
		String invoiceid = request.getParameter("invoiceid");
		
		System.out.println("firstname  " + firstname);
		System.out.println("lastname " + lastname);
		String checkStr = Checkin ? "checkin" : "checkout";
			
		List<Customer> customerList = Customer.fetchWithJoin(connection, " where t.first_name = ? AND t.last_name = ?", firstname, lastname);
		if((customerList == null || customerList.size() == 0)) {
			if(invoiceid == null) {
				String customer_message = "<div class='welcome-heading'>Sorry no customer with this name " + firstname + " " + lastname + " found</div>";
				request.setAttribute("customer_message", customer_message);
		 
				request.getRequestDispatcher("/admin/index.jsp").forward(request, response);
				
			} else {
				Reservation reservation = Reservation.fetchById(connection, invoiceid);
				if(reservation == null) {
					String customer_message = "<div class='welcome-heading'>Sorry no invoice  withID " + invoiceid + " found</div>";
					request.setAttribute("customer_message", customer_message);
			 
					request.getRequestDispatcher("/admin/index.jsp").forward(request, response);
					
				} else {
					Reservation.update(connection, "update Reservation set " + Checkin + " = ? where id = ?", new Date().toString(), "" + invoiceid);
					String customer_message = "<div class='welcome-heading'>Welcome, All set</div>";
					request.setAttribute("customer_message", customer_message);
			 
					request.getRequestDispatcher("/admin/index.jsp").forward(request, response);
				}
			}
			return;
		}
		List<Reservation> reservations = Reservation.fetchWithJoin(connection, " where t.customer_id = ?", "" + customerList.get(0).getId());
		// should display another form if there are more than reservation but for now checkin on the first reservation
		Reservation.update(connection, "update Reservation set " + Checkin + " = ? where id = ?", new Date().toString(), "" + reservations.get(0).getId());
		String customer_message = "<div class='welcome-heading'>Welcome, All set</div>";
		request.setAttribute("customer_message", customer_message);
 
		request.getRequestDispatcher("/admin/index.jsp").forward(request, response);
	}
		 
	protected void processRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession(true);
		AuthRecord auth = (AuthRecord) session.getAttribute("auth");

		String pageType = request.getParameter("page-type");
		// System.out.println("Page Type: " + pageType);
		if (pageType == null) {
			request.getRequestDispatcher("/admin/AdminLogin.jsp").forward(request, response);
			return;
		}

		Connection connection = null;
		try {
			connection = getDataSource().getConnection();
			if (auth == null && processSignRequest(session, pageType, connection, request, response))
				return;

			switch (pageType) {
				case "checkin":  
				checkInOut( request,  response,  connection, true);
				
				break;
			case "checkout": 
				checkInOut( request,  response,  connection, false);
 
			}

		} catch (Exception e) {
			request.setAttribute("errmsg", "Failed to excute request with exception " + e);
			request.getRequestDispatcher("admin/AdminLogin.jsp").forward(request, response);
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

	///////////////////////////////  support processing functions /////////////////////////
	
	 
	private boolean processSignRequest(HttpSession session, String pageType, Connection connection,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		if ("AdminLogin".equals(pageType)) {
			String username = request.getParameter("username");
			String password = request.getParameter("password");

			List<Admin> adminList = Admin.fetchWithJoin(connection, " where t.login_id = ? and t.password = ?",
					username, password);
			System.out.println("customerList " + adminList);
			if (adminList == null || adminList.size() == 0) {
				
				request.setAttribute("errmsg",
						"<div id='err-msg'>Failed to login. Please try again </div>");
				request.getRequestDispatcher("admin/AdminLogin.jsp").forward(request, response);
				return true;
			}
			System.out.println("adminList " + adminList);
			Admin admin = adminList.get(0);
			
			if(password.equals(admin.getPassword())) // check also for username
			 {
				AuthRecord authRecord = new AuthRecord();
				authRecord.user_id = admin.getId();
				session.setAttribute("auth", authRecord);
				request.setAttribute("errmsg", "");
				request.getRequestDispatcher("/admin/index.jsp").forward(request, response);
			} else {
				request.setAttribute("errmsg",
						"<div id='err-msg'>Failed to login. Please press the Signup button if you are not registered </div>");
				request.getRequestDispatcher("admin/AdminLogin.jsp").forward(request, response);
			}
			
			return true;
		}
		 
		return false;
	}
	


	////////////////////// static Support functions called from JSPs /////////////////////////////////////////////
	
	public static  boolean checkIfCustomerLoggedIn(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession(true);
		AuthRecord auth = (AuthRecord) session.getAttribute("auth");

		if (auth == null) {
			// request.getContextPath() +
			request.getRequestDispatcher( "/admin/index.jsp").forward(request, response);
			return false;
		}
		return true;
	}	
	
	 
	 
	public static void printAvailableHotels(JspWriter out) {
		Connection connection = null;
		try {
			connection = getDataSource().getConnection();
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
