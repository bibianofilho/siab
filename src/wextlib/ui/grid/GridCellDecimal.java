/* 
 * WExtLib - A class library for the Superwaba Virtual Machine
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

package wextlib.ui.grid;

import wextlib.util.*;

/**
 * This class implements a default Grid Cell with decimal editing support
 * 
 * @author Virgilio Alexandre Fornazin (mailto:virgiliofornazin@gmail.com)
 */
public final class GridCellDecimal extends GridCell
{
	/** Defines numeric precision */
	protected int m_iPrecision;
	/** Defines numeric scale */
	protected int m_iDecimals;
	/** Prefix for formatting number */
	protected String m_strPrefix;
	
	/**
	 * Default constructor
	 */
	public GridCellDecimal()
	{
		m_iPrecision = 12;
		m_iDecimals = 2;
		
		m_Editor = new GridCellDecimalEditor();
				
		setDouble(0);
	}

	/**
	 * Update display of the number
	 */
	public void updateDisplay()
	{
		setDouble(getDouble());
	}
	
	/**
	 * Get the number format prefix
	 */
	public String getPrefix()
	{
		return m_strPrefix;
	}
	
	/**
	 * Set the number format prefix
	 */
	public void setPrefix(String strPrefix)
	{
		m_strPrefix = strPrefix;
		
		updateDisplay();
	}
	
	/**
	 * Get current precision (not currently used)
	 */
	public int getPrecision()
	{
		return m_iPrecision;
	}

	/**
	 * Set current precision (not currently used)
	 */
	public void setPrecision(int iPrecision)
	{
		m_iPrecision = iPrecision;
		
		updateDisplay();
	}

	/**
	 * Get current decimal places
	 */
	public int getDecimals()
	{
		return m_iDecimals;
	}
	
	/**
	 * Set current decimal places
	 */
	public void setDecimals(int iDecimals)
	{
		m_iDecimals = iDecimals;
		
		updateDisplay();
	}
	
	public String getCellCompareValue()
	{
		return NumberFormat.formatNumber("", getDouble(), m_iDecimals);
	}

	/**
	 * Get current value as double
	 */
	public double getDouble()
	{
		return NumberFormat.parseNumber(m_strCellText);
	}

	/**
	 * Set current value as double and update precision and decimal places
	 */
	public void setDouble(double dblDouble, int iPrecision, int iDecimals)
	{
		if (iPrecision != -1)
		{
			m_iPrecision = iPrecision;
		}
		
		if (iDecimals != -1)
		{
			m_iDecimals = iDecimals;
		}
		
		m_strCellText = NumberFormat.formatNumber(m_strPrefix, dblDouble, m_iDecimals);
	}
	
	/**
	 * Set current value as a double
	 */
	public void setDouble(double dblDouble)
	{
		setDouble(dblDouble, -1, -1);
	}
	
	/**
	 * Get the cell text
	 */
	public String getCellText()
	{
		return m_strCellText;
	}
	
	/**
	 * Set the cell text
	 */
	public void setCellText(String strText)
	{
		setDouble(NumberFormat.parseNumber(strText));
	}
}
