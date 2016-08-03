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
 * Class used for passing params to draw the grid cell´s
 * 
 * @author Virgilio Alexandre Fornazin (mailto:virgiliofornazin@gmail.com)
 */
public final class GridCellDrawParams
{
	/** Clipping rectangle */
	public Rect rcClip;
	/** Cell foreground color */
	public Color clrCellForeColor;
	/** Cell foreground color (save storage for highlighting) */
	public Color clrCellForeColorSave;
	/** Cell background color */
	public Color clrCellBackColor;
	/** Cell background color (save storage for highlighting) */
	public Color clrCellBackColorSave;
	/** Grid line color */
	public Color clrGridCellSeparatorColor;
	/** Graphics surface */
	public Graphics graphics;
	/** Cell font */
	public Font font;
	/** Height of the row separator line */
	public int iRowSeparatorHeight;
	/** Width of the column separator line */
	public int iColSeparatorWidth;
	/** Height of the cell */
	public int iCellHeight;
	/** Width of the cell */
	public int iCellWidth;
	/** Alignment of the text */
	public int iCellAlignment;
	/** Indicate if this is a fixed cell */
	public boolean bIsFixed;
	/** Arrow draw style (to indicate the sort column / row */
	public int iArrowDrawStyle;
	
	/* Constants used to define the arrow drawing style */
	
	/** Don't draw any arrow */
	public static final int ARROW_DRAW_NONE = 0;
	/** Draw a up arrow */
	public static final int ARROW_DRAW_UP = 1;
	/** Draw a down arrow */
	public static final int ARROW_DRAW_DOWN = 2;
	/** Draw a left arrow */
	public static final int ARROW_DRAW_LEFT = 3;
	/** Draw a right arrow */
	public static final int ARROW_DRAW_RIGHT = 4;
	
	/** Default constructor */
	public GridCellDrawParams()
	{
		rcClip = new Rect();

		/* 
		Optimization trick: JAVA doesn't need to ZERO any variable
		as you must have to do in C++ or Pascal, so don't do this.
		
		rcClip = null;
		clrCellForeColor = null;
		clrCellForeColorSave = null;
		clrCellBackColor = null;
		clrCellBackColorSave = null;
		clrGridCellSeparator = null;
		graphics = null;
		font = null;
		iRowSeparatorHeight = 1;
		iColSeparatorWidth = 1;
		iCellHeight = 0;
		iCellWidth = 0;
		iCellAlignment = GridCellAttributes.ALIGN_DEFAULT;
		bIsFixed = false;
		iArrowDrawStyle = 0;
		*/
	}
	
	public void highlightColors(int iHighlight)
	{
		clrCellForeColorSave = clrCellForeColor;
		clrCellBackColorSave = clrCellBackColor;
			
		clrCellForeColor = clrCellForeColorSave.brighter(iHighlight);
		clrCellBackColor = clrCellBackColorSave.brighter(iHighlight);
	}
	
	public void dehighlightColors()
	{
		clrCellForeColor = clrCellForeColorSave;
		clrCellBackColor = clrCellBackColorSave;
	}
}
