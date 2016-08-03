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
 * This class represents a read-only resultSet (in wextlib.sql universe)<br>
 * Note that READ-WRITE resultSet ARE NOT SUPPORTED
 *
 * 
 */
public class ResultSet
{
    /** Reserved for internal use */
	private int m_iReserved;
	
	/**
	 * Default constructor
	 */
    public ResultSet() throws wextlib.sql.DBException
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
	 * Close the result set
	 */
    public void close() throws wextlib.sql.DBException
    {
    }

    /**
     * Get row count of the result set
     */
    public int getRowCount() throws wextlib.sql.DBException
    {
        return 0;
    }
    
    /**
     * Get column count of the result set
     */
    public int getColumnCount() throws wextlib.sql.DBException
    {
        return 0;
    }

    /**
     * Get information about a specific column from the result set
     */
    public wextlib.sql.Column getColumn(int iColumnIndex) throws wextlib.sql.DBException
    {
        return null;
    }

    /**
     * Move record pointer to the first row in the result set
     */
    public void moveFirst() throws wextlib.sql.DBException
    {
    }
    
    /**
     * Move record pointer to the previous row in the result set
     */
    public void movePrevious() throws wextlib.sql.DBException
    {
    }
    
    /**
     * Move record pointer to the next row in the result set
     */
    public void moveNext() throws wextlib.sql.DBException
    {
    }
    
    /**
     * Move record pointer to the last row in the result set
     */
    public void moveLast() throws wextlib.sql.DBException
    {
    }
    
    /**
     * Move record pointer to the specified row in the result set
     */
    public void moveTo(int iRow) throws wextlib.sql.DBException
    {
    }
    
    /**
     * Indicator if record pointer reached the begin of the result set
     */
    public boolean atBOF() throws wextlib.sql.DBException
    {
        return true;
    }
    
    /**
     * Indicator if record pointer reached the end of the result set
     */
    public boolean atEOF() throws wextlib.sql.DBException
    {
        return true;
    }
    
    /**
     * Get the column value as a string
     */
    public String getAsString(int iColumnIndex) throws wextlib.sql.DBException
    {
        return null;
    }
    
    /**
     * Get the column value as a string (By column name)
     */
    public String getAsStringByName(String strName) throws wextlib.sql.DBException
    {
        return null;
    }
    
    /**
     * Get the column value as a integer (32-bits)
     */
    public int getAsInteger(int iColumnIndex) throws wextlib.sql.DBException
    {
        return 0;
    }
    
    /**
     * Get the column value as a integer (32-bits) (by column name)
     */
    public int getAsIntegerByName(String strName) throws wextlib.sql.DBException
    {
        return 0;
    }
    
    /**
     * Get the column value as a long integer (64-bits)
     */
    public long getAsLong(int iColumnIndex) throws wextlib.sql.DBException
    {
        return 0;
    }
    
    /**
     * Get the column value as a long integer (64-bits) (by column name)
     */
    public long getAsLongByName(String strName) throws wextlib.sql.DBException
    {
        return 0;
    }
    
    /**
     * Get the column value as a double
     */
    public double getAsDouble(int iColumnIndex) throws wextlib.sql.DBException
    {
        return 0;
    }
    
    /**
     * Get the column value as a double (by column name)
     */
    public double getAsDoubleByName(String strName) throws wextlib.sql.DBException
    {
        return 0;
    }
    
    /**
     * Get the column value as a BLOB
     */
    public byte [] getAsBlob(int iColumnIndex) throws wextlib.sql.DBException
    {
        return null;
    }
    
    /**
     * Get the column value as a BLOB (by column name)
     */
    public byte [] getAsBlobByName(String strName) throws wextlib.sql.DBException
    {
        return null;
    }
    
    /**
     * Get the column value as a date/time
     * <br><b>Must no be overriden, because it uses LONG as storage internally.</b>
     */
    public waba.sys.Time getAsDateTime(int iColumnIndex) throws wextlib.sql.DBException
    {
	    long res = getAsLong(iColumnIndex);
	    
	    return new waba.sys.Time(res);
    }
    
    /**
     * Get the column value as a date/time (by column name)
     * <br><b>Must no be overriden, because it uses LONG as storage internally.</b>
     */
    public waba.sys.Time getAsDateTimeByName(String strName) throws wextlib.sql.DBException
    {
	    long res = getAsLongByName(strName);
	    
	    return new waba.sys.Time(res);
    }
}
