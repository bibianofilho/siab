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

/**
 * This class represents a statement (in wextlib.sql universe)
 * 
 * @author Virgilio Alexandre Fornazin (mailto:virgiliofornazin@gmail.com)
 */
public class Statement
{
    /** Reserved for internal use */
	private int m_iReserved;
    
	/**
	 * Default constructor
	 */
    public Statement() throws wextlib.sql.DBException
    {
	    m_iReserved = 0;
    }
    
    /**
     * Default finalizator
     */
    public void finalize() throws Throwable
    {
	    close();
    }
    
    /**
     * Close the statement
     */
    public void close() throws wextlib.sql.DBException
    {
    }
    
    /**
     * Prepare a SQL statement
     */
    public void prepare(String strSQL) throws wextlib.sql.DBException
    {
    }

	/**
	 * Get the number of parameters in the prepared statement
	 */
	public int getParameterCount() throws wextlib.sql.DBException
	{
		return 0;
	}

	/**
	 * Get the name of the parameter in the prepared statement
	 */
	public String getParameterName(int iIndex) throws wextlib.sql.DBException
	{
		return "";
	}

	/**
	 * Clear all parameters bindings of the prepared statement
	 */
	public void clearParameterBindings() throws wextlib.sql.DBException
	{
	}

    /**
     * Bind a string parameter to the prepared statement
     */
    public void bindParamString(int iIndex, String strValue) throws wextlib.sql.DBException
    {
    }

    /**
     * Bind a integer (32-bits) parameter to the prepared statement
     */
    public void bindParamInteger(int iIndex, int iValue) throws wextlib.sql.DBException
    {
    }

    /**
     * Bind a long integer (64-bits) parameter to the prepared statement
     */
    public void bindParamLong(int iIndex, long lValue) throws wextlib.sql.DBException
    {
    }

    /**
     * Bind a double parameter to the prepared statement
     */
    public void bindParamDouble(int iIndex, double dblValue) throws wextlib.sql.DBException
    {
    }

    /**
     * Bind a BLOB parameter to the prepared statement
     */
    public void bindParamBlob(int iIndex, byte [] bytValue) throws wextlib.sql.DBException
    {
    }

    /**
     * Bind a NULL parameter to the prepared statement
     */
    public void bindParamNull(int iIndex) throws wextlib.sql.DBException
    {
    }
     
    /**
     * Bind a date/time parameter to the prepared statement
     * <br><b>Must no be overriden, because it uses LONG as storage internally.</b>
     */
    public void bindParamDateTime(int iIndex, waba.sys.Time timeValue) throws wextlib.sql.DBException
    {
	    bindParamLong(iIndex, timeValue.getTimeLong());
    }
   
    /**
     * Execute the prepared statement
     */
    public void execute() throws wextlib.sql.DBException
    {
    }
    
    /**
     * Prepare and execute the SQL statement
     */
    public void executeSQL(String strSQL) throws wextlib.sql.DBException
    {
    }
    
	/**
	 * Open a result set from the prepared statement
	 */
	public wextlib.sql.ResultSet openResultSet() throws wextlib.sql.DBException
	{
		return new ResultSet();
	}    
    
	/**
	 * Prepare and open a result set from the SQL statement
	 */
	public wextlib.sql.ResultSet openResultSetSQL(String strSQL) throws wextlib.sql.DBException
	{
		return new ResultSet();
	}    
}
