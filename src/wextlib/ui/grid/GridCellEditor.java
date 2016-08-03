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
import waba.ui.*;

/**
 * This class is the base class for a grid cell editor.
 * 
 * @author Virgilio Alexandre Fornazin (mailto:virgiliofornazin@gmail.com)
 */
public class GridCellEditor extends Control 
{
	/** Editing Grid */
	protected Grid m_EditGrid;
	/** Editing Control */
	protected Control m_EditControl;
	/** Editing Cell */
	protected GridCell m_EditCell;
	/** Editing CellID */
	protected GridCellID m_EditCellID;
	/** Editing Display Rect */
	protected Rect m_EditRect;
	/** Editing Foreground Color */
	protected Color m_EditForeColor;
	/** Editing Background Color */
	protected Color m_EditBackColor;
	/** Editing Font */
	protected Font m_EditFont;

	public GridCellEditor()
	{
		/* 
		Optimization trick: JAVA doesn't need to ZERO any variable
		as you must have to do in C++ or Pascal, so don't do this.
		
		m_strCellText = "";
		
		m_EditGrid = null;
		m_EditControl = null;
		m_EditCell = null;
		m_EditCellID = null;
		m_EditRect = null;
		m_EditForeColor = null;
		m_EditBackColor = null;
		m_EditFont = null;
		*/
	}
	
	public void onBoundsChanged()
	{
		if (m_EditControl != null)
		{
			Rect rc = getRect();
			
			m_EditControl.setRect(rc);
		}
	}   
	
	/**
	 * This event occurs when the grid request a cell for editing. You must
	 * return FALSE if you don´t want to edit the cell contents. Override this 
	 * method for handling start of editing. 
	 */
	public boolean onStartEdit(Grid grid, GridCell cell, GridCellID cellID, Rect cellRect, Color clrForeColor, Color clrBackColor, Font cellFont, int chrCode)
	{
		m_EditGrid = grid;
		m_EditControl = null;
		m_EditCell = cell;
		m_EditCellID = cellID;
		m_EditRect = cellRect;
		m_EditForeColor = clrForeColor;
		m_EditBackColor = clrBackColor;
		m_EditFont = cellFont;
		
		// Code to prevent warnings
		chrCode++;
		chrCode--;
		
		return true;
	}
	
	/**
	 * This event will be called by the cell editor when the editor loses input
	 * focus or the user finished entering cell data. Override this method for 
	 * handling end of editing. You must return TRUE for notify user that editing
	 * was finished.
	 */
	public boolean onEndEdit()
	{
		return false;
	}
	
	public void onEvent(Event e)
	{
		if (e.type == ControlEvent.FOCUS_IN)
		{
			e.consumed = true;
		}
	}
}
