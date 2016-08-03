/* 
 * WExtLib - A Component Library for the Superwaba Virtual Machine
 * Copyright (C) 2005, Virgilio Alexandre Fornazin
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 * 
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 */

package wextlib.sql;

import waba.sys.*;

/**
 * This class represents DBMS exceptions (in wextlib.sql universe
 * 
 * 
 */
public class DBException extends Exception
{
	/** Database Error Return Code */
	public int ErrorCode;
	/** Database Error Error Message */
	public String ErrorMessage;
	/** Database Native Error Code */
	public int ReturnCode;
	/** Database Native Error Message */
	public String ReturnMessage;
	/** Serial Version UID */
	public static final long serialVersionUID = 1;
	
	public DBException()
	{
		ErrorCode = 0;
		ErrorMessage = "";
		ReturnCode = 0;
		ReturnMessage = "";
	}
	
	public DBException(String strMessage)
	{
		ErrorCode = -1;
		ErrorMessage = strMessage;
		ReturnCode = -1;
		ReturnMessage = strMessage;
	}
	
	public String getMessage()
	{
		StringBuffer sb = new StringBuffer();
		
		sb.append(getClass().getName());
		sb.append("\n\nError Code: ");
		sb.append(Convert.toString(ErrorCode));
		sb.append("\nError Message: ");
		sb.append(ErrorMessage);
		sb.append("\nReturn Code: ");
		sb.append(Convert.toString(ReturnCode));
		sb.append("\nReturn Message: ");
		sb.append(ReturnMessage);
		sb.append("\n");
		
		return sb.toString();
	}
}
