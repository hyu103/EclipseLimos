package com.ipage.servlet.data;
//Hankil Yu
import ca.senecacollege.prg556.limser.dao.LimoClientDAO;

public class LimoClientDAOFactory 
{
	public static LimoClientDAO getLimoClientDAO()
	{
		return new LimoClientData(DataSourceFactory.getDataSource());
		
	}
}
