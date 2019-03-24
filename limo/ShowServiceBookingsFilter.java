package com.ipage.servlet.limo;
//Hankil Yu
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;



import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;



import org.procrastinatorsanonymous.data.ServiceBookingDAOFactory;

import ca.on.senecac.prg556.common.StringHelper;
import ca.senecacollege.prg556.limser.bean.LimoClient;
import ca.senecacollege.prg556.limser.bean.ServiceBooking;
import ca.senecacollege.prg556.limser.dao.ServiceBookingDAO;

import com.ipage.servlet.data.LimousineDAOFactory;



/**
 * Servlet Filter implementation class ShowServiceBookingsFilter
 */
public class ShowServiceBookingsFilter implements Filter {

    /**
     * Default constructor. 
     */
    public ShowServiceBookingsFilter() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException 
	{
		HttpServletRequest request = (HttpServletRequest)req;
		HttpServletResponse response = (HttpServletResponse)resp;
		try
		{
			String userId = ((LimoClient)request.getSession().getAttribute("limoClientSession")).getUserId();
			ServiceBookingDAO bookingDAO = ServiceBookingDAOFactory.getServiceBookingDAO();
			List<ServiceBooking> bookings;
			
			if("POST".equals(request.getMethod()))
			{
				String checkCancel = request.getParameter("cancel");
				int servId = Integer.parseInt(request.getParameter("servID"));
				if (checkCancel != null)
					bookingDAO.cancelServiceBooking(servId);
			}
			
			bookings = bookingDAO.getServiceBookings(userId);
			request.setAttribute("bookings", bookings);
			chain.doFilter(request, response);
		}
		catch(SQLException sqle)
		{
			throw new ServletException(sqle);
		}

	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}


}
