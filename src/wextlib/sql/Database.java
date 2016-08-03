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
 * This class represents a database (in wextlib.sql universe)
 * 
 * @author Virgilio Alexandre Fornazin (mailto:virgiliofornazin@gmail.com)
 */
public class Database
{
    /** Reserved for internal use */
	private int m_iReserved;
	
	/** Database connection string*/
    protected String m_strConnectionString;
    /** Indicator if database is opened */
    protected boolean m_bIsOpened;
    
    /**
     * Default constructor 
     */
    public Database() throws wextlib.sql.DBException
    {
	    m_iReserved = 0;
	    m_strConnectionString = "";
        m_bIsOpened = false;
    }
    
    /**
     * Default finalizator
     */
    public void finalize() throws Throwable
    {
	    close();
    }
    
    /**
     * Get the database driver name
     */
    public String getDatabaseDriverName() throws wextlib.sql.DBException
    {
	    return "";
    }
    
    /**
     * Get the database driver version
     */
    public String getDatabaseDriverVersion() throws wextlib.sql.DBException
    {
	    return "";
    }
    
    /**
     * Get the connection string
     */
    public String getConnectionString() throws wextlib.sql.DBException
    {
        return m_strConnectionString;
    }
    
    /**
     * Get if the database is opened
     */
    public boolean isOpened() throws wextlib.sql.DBException
    {
        return m_bIsOpened;
    }
    
	/**
	 * Create a new empty database
	 */
	public void create(String strConnectionString) throws wextlib.sql.DBException
	{
	}
    
	/**
	 * Open a existing database
	 */
	public void open(String strConnectionString) throws wextlib.sql.DBException
	{
	}
    
    /**
     * Close the database
     */
    public void close() throws wextlib.sql.DBException
    {
        if (m_bIsOpened)
        {
	        try
	        {
            	rollbackTransaction();
        	}
        	catch (DBException e)
        	{
        	}
        }
    }
    
    /**
     * Begin a database transaction
     */
    public void beginTransaction() throws wextlib.sql.DBException
    {
    }
    
    /**
     * Commit the active transaction
     */
    public void commitTransaction() throws wextlib.sql.DBException
    {
    }
    
    /**
     * Rollback the active transaction
     */
    public void rollbackTransaction() throws wextlib.sql.DBException
    {
    }
    
    /**
     * Create a statement object
     */
    public wextlib.sql.Statement createStatement() throws wextlib.sql.DBException
    {
        return new Statement();
    }
    
    /**
     * Execute a SQL statement
     */
    public void executeSQL(String strSQL) throws wextlib.sql.DBException
    {
	    Statement st = createStatement();

	    try
	    {
	        st.executeSQL(strSQL);
	        st.close();
	    }
	    catch (DBException e)
	    {
	        st.close();
	     
	        throw e;
	    }
    }
    
    /**
     * Open a Resultset from a SQL statement
     */
    public wextlib.sql.ResultSet openResultSetSQL(String strSQL) throws wextlib.sql.DBException
    {
        Statement st = createStatement();
	    ResultSet rs = st.openResultSetSQL(strSQL);

        st.close();
        
        return rs;
    }
}
