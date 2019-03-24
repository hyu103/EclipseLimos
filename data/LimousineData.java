package com.ipage.servlet.data;
//Hankil Yu
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.sql.DataSource;

import ca.senecacollege.prg556.limser.bean.Limousine;
import ca.senecacollege.prg556.limser.dao.LimousineDAO;

class LimousineData implements LimousineDAO
{
	private DataSource ds;
	public LimousineData(DataSource ds)
	{
		setDs(ds);
	}
	
	@Override
	public List<Limousine> getAvailableLimousines(Date bookingDate, String style) throws SQLException 
	{
		try (Connection conn = getDs().getConnection())
		{
			try (Statement statement = conn.createStatement())
			{
				String query;
				if (style == null)
					query = "SELECT model, style, MIN(vin) AS vin FROM limousine WHERE vin NOT IN (SELECT vin FROM servicebooking WHERE booking_date = ?) GROUP BY model, style";
				else
					query = "SELECT model, style, MIN(vin) AS vin FROM limousine WHERE vin NOT IN (SELECT vin FROM servicebooking WHERE booking_date = ?) AND style = ? GROUP BY model, style";
				
				try (PreparedStatement pstmt = conn.prepareStatement(query))
				{
					pstmt.setDate(1, new java.sql.Date(bookingDate.getTime()));
					
					if (style != null)
						pstmt.setString(2, style);
					
					try (ResultSet rslt = pstmt.executeQuery())
					{
						List<Limousine> limousines = new ArrayList<>();
						
						while (rslt.next())
							limousines.add(new Limousine(rslt.getString("model"), rslt.getString("style"), rslt.getString("vin")));
						
						return limousines;
					}
				}
			}
		}
	}

	@Override
	public Limousine getLimousine(String vin) throws SQLException 
	{
		try (Connection conn = getDs().getConnection())
		{
			try (PreparedStatement statement = conn.prepareStatement("SELECT model, style, vin FROM limousine WHERE vin = ?"))
			{
				statement.setString(1,  vin);
				try(ResultSet rslt = statement.executeQuery())
				{
					if(rslt.next())
						return new Limousine(rslt.getString("model"), rslt.getString("style"), vin);
					else
						return null;
				}
			}
		}
	}
	
	private DataSource getDs()
	{
		return ds;
	}

	private void setDs(DataSource ds)
	{
		this.ds = ds;
	}
			
	
}
