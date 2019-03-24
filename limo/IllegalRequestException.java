package com.ipage.servlet.limo;
//Hankil Yu
import javax.servlet.ServletException;

public class IllegalRequestException extends ServletException
{

		/**
		 * 
		 */
	private static final long serialVersionUID = 1L;
	public IllegalRequestException()
	{
		
	}

	public IllegalRequestException(String message)
	{
		super(message);
	}

	public IllegalRequestException(Throwable rootCause)
	{
		super(rootCause);
	}

	public IllegalRequestException(String message, Throwable rootCause)
	{
		super(message, rootCause);
	}

}
