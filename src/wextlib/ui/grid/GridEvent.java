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

import waba.ui.*;

/**
 * This class implements the events for the Grid control
 * 
 * @author Virgilio Alexandre Fornazin (mailto:virgiliofornazin@gmail.com)
 */
public final class GridEvent extends Event 
{
	/** Event raised when user start edition of a cell */
	public static final int START_EDIT_CELL = Event.getNextAvailableEventId();
	/** Event raised when user end edition of a cell */
	public static final int END_EDIT_CELL = Event.getNextAvailableEventId();
	/** Event raised when user changed the current selected cell */
	public static final int SELECTION_CHANGED = Event.getNextAvailableEventId();
	/** Event raised when user double-click a cell */
	public static final int CELL_DOUBLE_CLICKED = Event.getNextAvailableEventId();
	/** Event raised when user un-press the pen into a cell */
	public static final int CELL_PEN_UP = Event.getNextAvailableEventId();
	/** Event raised when user press the pen into a cell */
	public static final int CELL_PEN_DOWN = Event.getNextAvailableEventId();
	
	/** GridCellID for event */
	public GridCellID cellID;
	
	/**
	 * Default constructor
	 */
	public GridEvent()
	{
		consumed = false;
	}
}
