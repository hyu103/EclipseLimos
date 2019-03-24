package com.ipage.servlet.limo;

import javax.annotation.Resource;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.sql.DataSource;

import com.ipage.servlet.data.DataSourceFactory;

@WebListener

public class LimSerContextListener implements ServletContextListener
{
	@Resource(name="LimousineServiceDS")
	private DataSource LimousineServiceDS;
	
	@Override
	public void contextDestroyed(ServletContextEvent context) 
	{
		DataSourceFactory.setDataSource(null);
	}

	@Override
	public void contextInitialized(ServletContextEvent context) 
	{
		DataSourceFactory.setDataSource(LimousineServiceDS);
	}
	
}
