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
 * This class implements a default Grid Cell with spin editing support
 * 
 * @author Virgilio Alexandre Fornazin (mailto:virgiliofornazin@gmail.com)
 */
public final class GridCellSpin extends GridCell
{
	/** Defines numeric precision */
	protected int m_iPrecision;
	/** Defines numeric scale */
	protected int m_iDecimals;
	/** Prefix for formatting number */
	protected String m_strPrefix;
	/** Increment/decrement step */
	protected double m_dIncrementDecrementStep;
	
	/**
	 * Default constructor
	 */
	public GridCellSpin()
	{
		m_iPrecision = 12;
		m_iDecimals = 2;
		
		m_Editor = new GridCellSpinEditor();
		
		m_dIncrementDecrementStep = 1;
				
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
	public void setDouble(double dblDouble, int iPrecision, int iSpins)
	{
		if (iPrecision != -1)
		{
			m_iPrecision = iPrecision;
		}
		
		if (iSpins != -1)
		{
			m_iDecimals = iSpins;
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
	
	/**
	 * Get the increment/decrement step
	 */
	public double getIncrementDecrementStep()
	{
		return m_dIncrementDecrementStep;
	}
	
	/**
	 * Set the increment/decrement step
	 */
	public void setIncrementDecrementStep(double dIncrementDecrementStep)
	{
		m_dIncrementDecrementStep = dIncrementDecrementStep;
	}
	
	/**
	 * Increment one time the value based on the step property
	 */
	public void incrementValue()
	{
		setDouble(getDouble() + m_dIncrementDecrementStep);
	}
	
	/**
	 * Call incrementValue the number of times specified
	 */
	public void incrementValue(int iTimes)
	{
		double dblValue = getDouble();
		
		for (int i = 0; i < iTimes; i++)
		{
			dblValue += m_dIncrementDecrementStep;	
		}
		
		setDouble(dblValue);
	}
	
	/**
	 * Decrement one time the value based on the step property
	 */
	public void decrementValue()
	{
		setDouble(getDouble() - m_dIncrementDecrementStep);
	}
	
	/**
	 * Call decrementValue the number of times specified
	 */
	public void decrementValue(int iTimes)
	{
		double dblValue = getDouble();
		
		for (int i = 0; i < iTimes; i++)
		{
			dblValue -= m_dIncrementDecrementStep;	
		}
		
		setDouble(dblValue);	
	}
}
