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
 * This class implements a default Grid Cell without editing support
 * 
 * @author Virgilio Alexandre Fornazin (mailto:virgiliofornazin@gmail.com)
 */
public class GridCellImage extends GridCell
{
	/** Cell image */
	private Image m_imgCell;
	
	/**
	 * Default constructor
	 */
	public GridCellImage()
	{
		/* 
		Optimization trick: JAVA doesn't need to ZERO any variable
		as you must have to do in C++ or Pascal, so don't do this.
		
		m_imgCell = null;
		*/
	}
	
	/**
	 * Get the cell image
	 */
	public Image getCellImage()
	{
		return m_imgCell;
	}
	
	/**
	 * Set the cell image
	 */
	public void setCellImage(Image imgCell)
	{
		m_imgCell = imgCell;
	}
	
	/**
	 * Clear the cell image
	 */
	public void clearCellImage()
	{
		m_imgCell = null;
	}

	/**
	 * Copy contents from another instance
	 */
	public void copyContents(GridCell cell)
	{
		super.copyContents(cell);
		
		if (cell instanceof GridCellImage)
		{
			m_imgCell = ((GridCellImage) cell).m_imgCell;
		}
	}

	/**
	 * Draw the cell image
	 */
	protected void drawCellImage(GridCellDrawParams params)
	{
		if ((params.rcClip.width > 1) && (params.rcClip.height > 1))
		{
			Rect rcNewClip = new Rect();
			Rect rcOldClip = new Rect();

			rcNewClip.x = params.rcClip.x;
			rcNewClip.y = params.rcClip.y;
			rcNewClip.width = params.rcClip.width - (params.iColSeparatorWidth + 1);
			rcNewClip.height = params.rcClip.height - params.iRowSeparatorHeight;
			
			if (params.bIsFixed)
			{
				rcNewClip.x++;
				rcNewClip.y++;
				rcNewClip.width -= 2;
				rcNewClip.height -= 2;
			}
			
			int hAlign = (GridCellAttributes.HORIZONTAL_ALIGN_MASK & params.iCellAlignment);
			int vAlign = (GridCellAttributes.VERTICAL_ALIGN_MASK & params.iCellAlignment);
			int iImageX;
			int iImageY;
			int iImageGapX = (!params.bIsFixed ? 0 : 2) + params.iColSeparatorWidth;
			int iImageGapY = (!params.bIsFixed ? 0 : 2) + params.iRowSeparatorHeight; 
			
			switch (hAlign)
			{
			case GridCellAttributes.HORIZONTAL_ALIGN_LEFT:
				{
					iImageX = rcNewClip.x;
					
					break;
				}
			case GridCellAttributes.HORIZONTAL_ALIGN_RIGHT:
				{
					iImageX = rcNewClip.x + ((params.iCellWidth - iImageGapX) - m_imgCell.getWidth()) - 1;
					
					break;
				}
			case GridCellAttributes.HORIZONTAL_ALIGN_CENTER:
				{
					iImageX = rcNewClip.x + (((params.iCellWidth - iImageGapX) / 2) - (m_imgCell.getWidth() / 2));
					
					break;
				}
			default:
				{
					iImageX = 0;
					
					break;
				}
			}
			
			switch (vAlign)
			{
			case GridCellAttributes.VERTICAL_ALIGN_TOP:
				{
					iImageY = rcNewClip.y;
					
					break;
				}
			case GridCellAttributes.VERTICAL_ALIGN_BOTTOM:
				{
					iImageY = rcNewClip.y + ((params.iCellHeight - iImageGapY) - m_imgCell.getHeight()) + 1;
					
					break;
				}
			case GridCellAttributes.VERTICAL_ALIGN_CENTER:
				{
					iImageY = rcNewClip.y + (((params.iCellHeight - iImageGapY) / 2) - (m_imgCell.getHeight() / 2));
					
					break;
				}
			default:
				{
					iImageY = 0;
					
					break;
				}
			}
			
			params.graphics.getClip(rcOldClip);
			params.graphics.setClip(rcNewClip);
			params.graphics.drawImage(m_imgCell, iImageX, iImageY);
			params.graphics.setClip(rcOldClip);
		}	
	}

	/**
	 * This events draw the cell contents on the Grid Graphics surface. Override
	 * this to paint your own cell
	 */
	public void onPaint(GridCellDrawParams params)
	{
		if ((params.rcClip.width < 1) || (params.rcClip.height < 1))
		{
			return;
		}
		
		drawCellBackground(params);
		drawCellBorders(params);
		
		if (m_imgCell != null)
		{
			drawCellImage(params);
		}
		else
		{
			if (m_strCellText != null)
			{
				if (m_strCellText.length() > 0)
				{
					drawCellText(params);
				}
			}
		}
	}
}
