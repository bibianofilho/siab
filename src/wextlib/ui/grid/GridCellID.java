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

import waba.sys.*;

/**
 * This class implements the Grid internal cell identifier
 * 
 * @author Virgilio Alexandre Fornazin (mailto:virgiliofornazin@gmail.com)
 */
public final class GridCellID
{
	/** Row identifier */
	public int Row;
	/** Columns identifier */
	public int Col;
	
	/**
	 * Default constructor
	 */
	public GridCellID()
	{
		Row = -1;
		Col = -1;
	}
	
	/**
	 * Constructs a GridCellID taking the Row and Col values
	 */
	public GridCellID(int iRow, int iCol)
	{
		Row = iRow;
		Col = iCol;
	}
	
	/**
	 * Tests if the contents are valid (non-negative values only)
	 */
	public boolean isValid()
	{
		return (Row > -1) && (Col > -1);
	}
	
	/**
	 * Tests if the contents are valid (referring to a grid storage model)
	 */
	public boolean isValid(int iMaxRows, int iMaxCols)
	{
		return ((Row > -1) && (Row < iMaxRows) && (Col > -1) && (Col < iMaxCols));
	}
	
	/**
	 * Obtain a integer representation of this object
	 */
	public int getCellID()
	{
		return ((Row << 16) + ((Col << 16) >> 16));
	}
	
	/**
	 * Obtains Row and Col values from the integer representation
	 */
	public void setCellID(int iCellID)
	{
		Row = (iCellID >> 16);
		Col = ((iCellID << 16) >> 16);
	}

	/**
	 * Get a String representation of the GridCellID object
	 */
	public String toString()
	{
		return Convert.unsigned2hex(Row, 4) + Convert.unsigned2hex(Col, 4);
	}
}
