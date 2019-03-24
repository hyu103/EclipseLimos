package com.ipage.servlet.data;
//Hankil Yu
import ca.senecacollege.prg556.limser.dao.LimousineDAO;

public class LimousineDAOFactory 
{
	public static LimousineDAO getLimousineDAO()
	{
		return new LimousineData(DataSourceFactory.getDataSource());
	}
}
