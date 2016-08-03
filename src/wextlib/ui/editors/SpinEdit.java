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

import waba.ui.*;
import waba.fx.*;
import waba.sys.*;

/**
 * This class implements a up/down spin editor for numeric values
 * 
 * @author Virgilio Alexandre Fornazin (mailto:virgiliofornazin@gmail.com)
 */
public class SpinEdit extends Container
{
	/** Internal Editor */
	private DecimalEdit m_Editor;
	/** Up button */
	private Button m_ButtonUp;
	/** Down button */
	private Button m_ButtonDown;
	/** Increment/Decrement step */
	private double m_dIncrementDecrementStep;
	/** Spin Buttons alignment */
	private int m_iAlignment;
	
	/* Spin alignment constants */
	
	/** Align the spin buttons in the left side of the control */
	public static final int SPINALIGN_LEFT = 0;
	/** Align the spin buttons in the right side of the control */
	public static final int SPINALIGN_RIGHT = 1;
	
	/**
	 * Default constructor
	 */
	public SpinEdit()
	{
		m_Editor = new DecimalEdit();
		
		m_ButtonUp = Button.createArrowButton(Graphics.ARROW_UP, 3, Color.BLACK);
		m_ButtonUp.setBorder((Settings.uiStyle == Settings.PalmOS) ? Button.BORDER_NONE : Button.BORDER_3D);

		m_ButtonDown = Button.createArrowButton(Graphics.ARROW_DOWN, 3, Color.BLACK);
		m_ButtonDown.setBorder((Settings.uiStyle == Settings.PalmOS) ? Button.BORDER_NONE : Button.BORDER_3D);
	
		add(m_Editor);
		add(m_ButtonUp);
		add(m_ButtonDown);
		
		m_iAlignment = SpinEdit.SPINALIGN_RIGHT;
		
		onBoundsChanged();
		
		m_dIncrementDecrementStep = 1;
	}

	/**
	 * Update display of the number
	 */
	public void updateDisplay()
	{
		m_Editor.updateDisplay();
	}
	
	/**
	 * Get the number format prefix
	 */
	public String getPrefix()
	{
		return m_Editor.getPrefix();
	}
	
	/**
	 * Set the number format prefix
	 */
	public void setPrefix(String strPrefix)
	{
		m_Editor.setPrefix(strPrefix);
	}
	
	/**
	 * Get current precision (not currently used)
	 */
	public int getPrecision()
	{
		return m_Editor.getPrecision();
	}

	/**
	 * Set current precision (not currently used)
	 */
	public void setPrecision(int iPrecision)
	{
		m_Editor.setPrecision(iPrecision);
	}

	/**
	 * Get current decimal places
	 */
	public int getDecimals()
	{
		return m_Editor.getDecimals();
	}
	
	/**
	 * Set current decimal places
	 */
	public void setDecimals(int iDecimals)
	{
		m_Editor.setDecimals(iDecimals);
	}
	
	/**
	 * Get current value as double
	 */
	public double getDouble()
	{
		return m_Editor.getDouble();
	}

	/**
	 * Set current value as double and update precision and decimal places
	 */
	public void setDouble(double dblDouble, int iPrecision, int iDecimals)
	{
		m_Editor.setDouble(dblDouble, iPrecision, iDecimals);
	}
	
	/**
	 * Set current value as a double
	 */
	public void setDouble(double dblDouble)
	{
		m_Editor.setDouble(dblDouble);
	}
	
	/**
	 * Get current text
	 */
	public String getText()
	{
		return m_Editor.getText();
	}
	
	/**
	 * Set current text
	 */
	public void setText(String strText)
	{
		m_Editor.setText(strText);
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
	
	public void setCursorPos(int iStart, int iLength)
	{
		m_Editor.setCursorPos(iStart, iLength);
	}
	
	public void onBoundsChanged()
	{
		Rect rc = getClientRect();
		
		int w = rc.width / 2;
		int h = rc.height / 2;
		
		if (w > h)
		{
			if (m_iAlignment == SpinEdit.SPINALIGN_RIGHT)
			{
				m_Editor.setRect(0, 0, rc.width - h, rc.height);
				m_ButtonUp.setRect(rc.width - h, 0, h, h);
				m_ButtonDown.setRect(rc.width - h, h, h, h);
			}
			else
			{
				m_Editor.setRect(h, 0, rc.width - h, rc.height);
				m_ButtonUp.setRect(0, 0, h, h);
				m_ButtonDown.setRect(0, h, h, h);
			}
		}
		else
		{
			if (m_iAlignment == SpinEdit.SPINALIGN_RIGHT)
			{
				m_Editor.setRect(0, 0, rc.width - w, rc.height);
				m_ButtonUp.setRect(rc.width - w, 0, w, h);
				m_ButtonDown.setRect(rc.width - w, h, w, h);		
			}
			else
			{
				m_Editor.setRect(w, 0, rc.width - w, rc.height);
				m_ButtonUp.setRect(0, 0, w, h);
				m_ButtonDown.setRect(0, h, w, h);
			}
		}
	}
	
	public void onEvent(Event e)
	{
		super.onEvent(e);
		
		switch (e.type)
		{
		case PenEvent.PEN_DOWN:
			{
				if (e.target == m_ButtonUp)
				{
					incrementValue();
					
					e.consumed = true;
				}
				else if (e.target == m_ButtonDown)
				{
					decrementValue();
					
					e.consumed = true;
				}
				
				break;
			}
		case KeyEvent.KEY_PRESS:
			{
				int iKey = ((KeyEvent) e).key;
				
				switch (iKey)
				{
				case IKeys.PAGE_UP:
					{
						incrementValue(5);
						
						e.consumed = true;
						
						break;
					}
				case IKeys.PAGE_DOWN:
					{
						decrementValue(5);
						
						e.consumed = true;
						
						break;
					}
				case IKeys.UP:
					{
						incrementValue();
						
						e.consumed = true;
						
						break;
					}
				case IKeys.DOWN:
					{
						decrementValue();
						
						e.consumed = true;
						
						break;
					}
				}
				
				break;
			}
		}
	}
}
