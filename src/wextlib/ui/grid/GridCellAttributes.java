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
 * This class implements the grid cell attributes
 * 
 * @author Virgilio Alexandre Fornazin (mailto:virgiliofornazin@gmail.com)
 */
public class GridCellAttributes
{
	/** Horizontal default alignment */
	public static final int HORIZONTAL_ALIGN_DEFAULT = 0;
	/** Horizontally align text on cell at left */
	public static final int HORIZONTAL_ALIGN_LEFT = 1;
	/** Horizontally align text on cell at right */
	public static final int HORIZONTAL_ALIGN_RIGHT = 2;
	/** Horizontally align text on cell at center */
	public static final int HORIZONTAL_ALIGN_CENTER = 4;
	/** Mask for handling horizontal alignment*/
	protected static final int HORIZONTAL_ALIGN_MASK = 7;
	/** Vertical default alignment */
	public static final int VERTICAL_ALIGN_DEFAULT = 0;
	/** Vertically align text on cell at left */
	public static final int VERTICAL_ALIGN_TOP = 8;
	/** Vertically align text on cell at right */
	public static final int VERTICAL_ALIGN_BOTTOM = 16;
	/** Vertically align text on cell at center */
	public static final int VERTICAL_ALIGN_CENTER = 32;
	/** Mask for handling vertical alignment*/
	protected static final int VERTICAL_ALIGN_MASK = 56;

	/**
	 * Internally handling for cell alignments 
	 */
	protected static int handleAlign(int iAlignCurrent, int iAlignDefault)
	{
		int hAlign = (GridCellAttributes.HORIZONTAL_ALIGN_MASK & iAlignCurrent);
		
		if (hAlign == GridCellAttributes.HORIZONTAL_ALIGN_DEFAULT)
		{
			hAlign = (GridCellAttributes.HORIZONTAL_ALIGN_MASK & iAlignDefault);
		}
		
		int vAlign = (GridCellAttributes.VERTICAL_ALIGN_MASK & iAlignCurrent);

		if (vAlign == GridCellAttributes.VERTICAL_ALIGN_DEFAULT)
		{
			vAlign = (GridCellAttributes.VERTICAL_ALIGN_MASK & iAlignDefault);
		}
		
		return (hAlign | vAlign);
	}

	/** Default cell alignment */
	public static final int ALIGN_DEFAULT = HORIZONTAL_ALIGN_DEFAULT | VERTICAL_ALIGN_DEFAULT;

	/** Backward compatibility alignment to left */
	public static final int ALIGN_LEFT = HORIZONTAL_ALIGN_LEFT | VERTICAL_ALIGN_TOP;
	/** Backward compatibility alignment to left */
	public static final int ALIGN_RIGHT = HORIZONTAL_ALIGN_RIGHT | VERTICAL_ALIGN_TOP;
	/** Backward compatibility alignment to left */
	public static final int ALIGN_CENTER = HORIZONTAL_ALIGN_CENTER | VERTICAL_ALIGN_TOP;
	
	/** Cell alignment */
	protected int m_iCellAlignment;
	/** Cell background color */
	protected Color m_clrCellBackColor;
	/** Cell foreground color */
	protected Color m_clrCellForeColor;
	/** Cell font */
	protected Font m_CellFont;
	
	/**
	 * Default constructor
	 */
	public GridCellAttributes()
	{
		/* 
		Optimization trick: JAVA doesn't need to ZERO any variable
		as you must have to do in C++ or Pascal, so don't do this.
		
		m_iCellAlignment = GridCellAttributes.ALIGN_DEFAULT;

		m_clrCellForeColor = null;
		m_clrCellBackColor = null;
		
		m_CellFont = null;
		*/
	}
	
	/**
	 * Get the cell alignment
	 */
	public int getCellAlignment()
	{
		return m_iCellAlignment;
	}
	
	/**
	 * Set cell alignment
	 */
	public void setCellAlignment(int iCellAlignment)
	{
		m_iCellAlignment = iCellAlignment;
	}
	
	/**
	 * Clear cell alignment
	 */
	public void clearCellAlignment()
	{
		m_iCellAlignment = GridCellAttributes.ALIGN_DEFAULT;
	}

	/**
	 * Get cell foreground color
	 */
	public Color getCellForeColor()
	{
		return m_clrCellForeColor;
	}
	
	/**
	 * Set cell foreground color
	 */
	public void setCellForeColor(Color clrCellForeColor)
	{
		m_clrCellForeColor = clrCellForeColor;
	}
	
	/**
	 * Clear cell foreground color
	 */
	public void clearCellForeColor()
	{
		m_clrCellForeColor = null;
	}
	
	/**
	 * Get cell background color
	 */
	public Color getCellBackColor()
	{
		return m_clrCellBackColor;
	}
	
	/**
	 * Set cell background color
	 */
	public void setCellBackColor(Color clrCellBackColor)
	{
		m_clrCellBackColor = clrCellBackColor;
	}
	
	/**
	 * Clear cell background color
	 */
	public void clearCellBackColor()
	{
		m_clrCellBackColor = null;
	}
	
	/**
	 * Get cell font
	 */
	public Font getCellFont()
	{
		return m_CellFont;
	}
	
	/**
	 * Set cell font
	 */
	public void setCellFont(Font CellFont)
	{
		m_CellFont = CellFont;
	}
	
	/**
	 * Clear cell font
	 */
	public void clearCellFont()
	{
		m_CellFont = null;
	}
	
	/**
	 * Copy attributes from another instance
	 */
	public void copyAttributes(GridCellAttributes attributes)
	{
		m_iCellAlignment = attributes.m_iCellAlignment;
		m_clrCellBackColor = attributes.m_clrCellBackColor;
		m_clrCellForeColor = attributes.m_clrCellForeColor;
		m_CellFont = attributes.m_CellFont;
	}
}
