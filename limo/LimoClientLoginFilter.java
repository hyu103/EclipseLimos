package com.ipage.servlet.limo;
//Hankil Yu
import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.ipage.servlet.data.LimoClientDAOFactory;

import ca.on.senecac.prg556.common.StringHelper;
import ca.senecacollege.prg556.limser.bean.LimoClient;
import ca.senecacollege.prg556.limser.dao.LimoClientDAO;




/**
 * Servlet Filter implementation class LimoClientLoginFilters
 */
public class LimoClientLoginFilter implements Filter{

	/**
     * Default constructor. 
     */
    public LimoClientLoginFilter() {
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
			HttpSession session = request.getSession();
			LimoClient usession = (LimoClient)session.getAttribute("limoClientSession");

			if (null == usession)
			{
				String username = request.getParameter("userId");
				String password = request.getParameter("pswd");
				
				if ("POST".equals(request.getMethod()) && StringHelper.isNotNullOrEmpty(username) && StringHelper.isNotNullOrEmpty(password))
				{
					LimoClient client = LimoClientDAOFactory.getLimoClientDAO().authorizeLimoClient(username, password);
					if (client != null)
					{
						session.setAttribute("limoClientSession", new LimoClient(client.getUserId(), client.getFirstName(), client.getLastName()));
						response.sendRedirect(request.getContextPath() + "/"); // redirect to context root folder
						return;
					}
					else
					{	
						request.setAttribute("username", username);
						request.setAttribute("unsuccessfulLogin", Boolean.TRUE);
					}
				}
				chain.doFilter(req, resp); // continue on to login.jspx
			}
			else
			{
				response.sendRedirect(request.getContextPath() + "/"); // already logged in -- redirect to context root folder
				return;
			}
		}
		catch (SQLException sqle)
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

