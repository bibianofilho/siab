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

package wextlib.ui.editors;

import wextlib.util.*;
import waba.ui.*;

/**
 * This class implements a editor for numeric values
 * 
 * @author Virgilio Alexandre Fornazin (mailto:virgiliofornazin@gmail.com)
 */
public class DecimalEdit extends Edit
{
	/** Defines numeric precision */
	private int m_iPrecision;
	/** Defines numeric scale */
	private int m_iDecimals;
	/** Indicates if was editing */
	private boolean m_bEditing;
	/** Prefix for formatting number */
	private String m_strPrefix;
	
	/**
	 * Default constructor
	 */
	public DecimalEdit()
	{
		m_iPrecision = 12;
		m_iDecimals = 2;
		
		m_bEditing = false;
		
		m_strPrefix = "";
		
		setDouble(0);
		setValidChars("+-0123456789.,");
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
	
	/**
	 * Get current value as double
	 */
	public double getDouble()
	{
		return NumberFormat.parseNumber(super.getText());
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
		
		String strDouble = NumberFormat.formatNumber(m_strPrefix, dblDouble, m_iDecimals);

		super.setText(strDouble);
	}
	
	/**
	 * Set current value as a double
	 */
	public void setDouble(double dblDouble)
	{
		setDouble(dblDouble, -1, -1);
	}
	
	/**
	 * Get current text
	 */
	public String getText()
	{
		return NumberFormat.formatNumber(m_strPrefix, getDouble(), m_iDecimals);
	}
	
	/**
	 * Set current text
	 */
	public void setText(String strText)
	{
		setDouble(NumberFormat.parseNumber(strText));
	}
	
	public void onEvent(Event e)
	{
		super.onEvent(e);
		
		switch (e.type)
		{
		case ControlEvent.FOCUS_IN:
			{
				if (e.target == this)
				{
					m_bEditing = true;
				}
				
				break;
			}
		case ControlEvent.FOCUS_OUT:
			{
				if (m_bEditing)
				{
					m_bEditing = false;

					updateDisplay();
				}
				
				break;
			}
		}
	}
}
