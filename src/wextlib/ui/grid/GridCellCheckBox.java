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

import waba.fx.*;

/**
 * This class implements a default Grid Cell with checkboxes.
 * 
 * @author Virgilio Alexandre Fornazin (mailto:virgiliofornazin@gmail.com)
 */
public final class GridCellCheckBox extends GridCell 
{
	/** Indicator if the cell is checked */
	protected boolean m_bChecked;
	
	/**
	 * Default constructor
	 */
	public GridCellCheckBox()
	{
		/* 
		Optimization trick: JAVA doesn't need to ZERO any variable
		as you must have to do in C++ or Pascal, so don't do this.
		
		m_bChecked = false;
		*/
	}
	
	/**
	 * Get if the cell is checked
	 */
	public boolean getChecked()
	{
		return m_bChecked;
	}
	
	/**
	 * Set if the cell is checked
	 */
	public void setChecked(boolean bChecked)
	{
		m_bChecked = bChecked;
	}
	
	public void onPaint(GridCellDrawParams params)
	{
		if ((params.rcClip.width < 1) || (params.rcClip.height < 1))
		{
			return;
		}

		drawCellBackground(params);
		drawCellBorders(params);
		
		int iCheckAux = (params.iCellWidth > params.iCellHeight ? params.iCellHeight : params.iCellWidth);
		int iCheckSize = (iCheckAux - 4);
		
		if (iCheckSize > 2)
		{
			int iCheckLeft = params.rcClip.x + 1;
			int iCheckTop = params.rcClip.y + ((params.iCellHeight / 2) - (iCheckSize / 2));
			
			params.graphics.setBackColor(new Color(255, 255, 255));
			params.graphics.fillRect(iCheckLeft + 1, iCheckTop + 1, iCheckSize - 2, iCheckSize - 2);
			
			if (m_bChecked)
			{
				params.graphics.setForeColor(new Color(0, 0, 0));
				params.graphics.drawLine(iCheckLeft + 2, iCheckTop + 2, iCheckLeft + iCheckSize - 3, iCheckTop + iCheckSize - 3);
				params.graphics.drawLine(iCheckLeft + 2, iCheckTop + iCheckSize - 3, iCheckLeft + iCheckSize - 3, iCheckTop + 2);
			}
	
			params.graphics.setForeColor(new Color(0, 0, 0));
			params.graphics.drawRect(iCheckLeft, iCheckTop, iCheckSize, iCheckSize);
			
			if (m_strCellText != null)
			{
				if (m_strCellText.length() > 0)
				{
					params.rcClip.x += (iCheckSize + 1);
					params.rcClip.width -= (iCheckSize + 1);
					params.iCellWidth -= (iCheckSize + 1);
					
					drawCellText(params);
		
					params.rcClip.x -= (iCheckSize + 1);
					params.rcClip.width += (iCheckSize + 1);
					params.iCellWidth += (iCheckSize + 1);
				}
			}
		}
		else
		{
			if (m_strCellText != null)
			{
				drawCellText(params);
			}
		}
	}
	
	public boolean supportEditing()
	{
		return true;
	}
	
	public boolean onStartEdit(Grid grid, GridCellID cellID, Rect cellRect, Color clrForeColor, Color clrBackColor, Font cellFont, int chrCode)
	{
		m_bChecked = !m_bChecked;
		grid.repaint();
		
		return false;
	}	
	
	public boolean onEndEdit()
	{
		return true;
	}
}
