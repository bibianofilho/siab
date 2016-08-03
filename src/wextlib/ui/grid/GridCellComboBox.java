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
import waba.sys.*;

/**
 * This class implements a default Grid Cell with combo-box style selection
 * 
 * @author Virgilio Alexandre Fornazin (mailto:virgiliofornazin@gmail.com)
 */
public final class GridCellComboBox extends GridCell
{
	/** Valid chars */
	protected Object [] m_arrItems;
	
	/**
	 * Default constructor
	 */   
	public GridCellComboBox()
	{
		m_Editor = new GridCellComboBoxEditor();
	}
	
	/**
	 * Get the vector of items for selection
	 */
	public Object [] getItems()
	{
		return m_arrItems;
	}
	
	/**
	 * Set the vector of items for selection
	 */
	public void setItems(Object [] arrItems)
	{
		int iCount = arrItems.length;
		m_arrItems = new Object [iCount];
		
		for (int i = 0; i < iCount; i++)
		{
			m_arrItems[i] = arrItems[i];
		}
	}
	
	public boolean supportEditing()
	{
		return true;
	}
	
	public void onPaint(GridCellDrawParams params)
	{
		if ((params.rcClip.width < 1) || (params.rcClip.height < 1))
		{
			return;
		}

		drawCellBackground(params);
		drawCellBorders(params);

		int iArrowWidth = params.font.fm.height * 3 / 11;
		int iComboWidth = iArrowWidth + 2;

		if (Settings.uiStyle == Settings.PalmOS)
		{
			params.graphics.drawArrow(params.rcClip.x, params.rcClip.y - 1 + (params.iCellHeight / 2) - 
					(iArrowWidth / 2), iArrowWidth, Graphics.ARROW_DOWN, false, true, Color.BLACK);
		}
		else // if (Settings.uiStyle == Settings.WinCE)
		{
			params.graphics.drawArrow(params.rcClip.x + params.rcClip.width - (iComboWidth + 3), params.rcClip.y - 1 + 
					(params.iCellHeight / 2) - (iArrowWidth / 2), iArrowWidth, Graphics.ARROW_DOWN, false, true, Color.BLACK);
		}
   	
		if (m_strCellText != null)
		{
			if (m_strCellText.length() > 0)
			{
				if (Settings.uiStyle == Settings.PalmOS)
				{
					params.rcClip.x += (iComboWidth + 1);
				}
				
				params.rcClip.width -= (iComboWidth + 1);
				params.iCellWidth -= (iComboWidth + 1);
				
				drawCellText(params);
	
				if (Settings.uiStyle == Settings.PalmOS)
				{
					params.rcClip.x -= (iComboWidth + 1);
				}
				
				params.rcClip.width += (iComboWidth + 1);
				params.iCellWidth += (iComboWidth + 1);
			}
		}
	}
}
