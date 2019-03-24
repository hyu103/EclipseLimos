package com.ipage.servlet.data;
//Hankil Yu
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.List;

import javax.sql.DataSource;




import ca.on.senecac.prg556.common.StringHelper;
import ca.senecacollege.prg556.limser.bean.LimoClient;
import ca.senecacollege.prg556.limser.dao.LimoClientDAO;

class LimoClientData implements LimoClientDAO
{
	private DataSource ds;
	
	LimoClientData(DataSource ds)
	{
		super();
		setDs(ds);
	}
	private DataSource getDs()
	{
		return ds;
	}
	private void setDs(DataSource ds)
	{
		this.ds = ds;
	}

	@Override
	public LimoClient authorizeLimoClient(String userId, String pswd) throws SQLException
	{
		if(StringHelper.isNotNullOrEmpty(userId) && StringHelper.isNotNullOrEmpty(pswd))
		{
			try (Connection conn = getDs().getConnection())
			{
				try (PreparedStatement statement = conn.prepareStatement("SELECT firstname, lastname FROM limoclient WHERE userid = ? AND pswd = ?"))
				{
					statement.setString(1, userId);
					statement.setString(2, pswd);
					try (ResultSet rs = statement.executeQuery())
					{
						if(rs.next())
							return new LimoClient(userId, rs.getString("firstname"), rs.getString("lastname"));
					}
				}
			}
		}
		return null;
	}
	
	@Override
	public LimoClient getLimoClient(String userId) throws SQLException
	{
		try(Connection conn = getDs().getConnection())
		{
			try(PreparedStatement statement = conn.prepareStatement("SELECT firstname, lastname FROM limoclient WHERE userid = ?"))
			{
				statement.setString(1, userId);
				try (ResultSet rs = statement.executeQuery())
				{
					if(rs.next())
						return new LimoClient(userId, rs.getString("firstname"), rs.getString("lastname"));
					else
						return null;
				}
			}
		}
	}
}
