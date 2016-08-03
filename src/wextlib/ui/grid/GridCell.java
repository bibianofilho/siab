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
public class GridCell extends GridCellAttributes
{
	/** Cell text */
	protected String m_strCellText;
	/** Cell editor interface */
	protected GridCellEditor m_Editor;
	
	/**
	 * Default constructor
	 */
	public GridCell()
	{
		/* 
		Optimization trick: JAVA doesn't need to ZERO any variable
		as you must have to do in C++ or Pascal, so don't do this.
		
		m_Editor = null;
		*/
		m_strCellText = "";
	}
	
	/**
	 * Get the cell comparation string value
	 */
	public String getCellCompareValue()
	{
		return m_strCellText;
	}
	
	/**
	 * Get the cell text
	 */
	public String getCellText()
	{
		return m_strCellText;
	}
	
	/**
	 * Set the cell text
	 */
	public void setCellText(String strCellText)
	{
		m_strCellText = strCellText;
	}
	
	/**
	 * Clear the cell text
	 */
	public void clearCellText()
	{
		m_strCellText = null;
	}

	/**
	 * Copy contents from another instance
	 */
	public void copyContents(GridCell cell)
	{
		copyAttributes(cell);
		
		m_strCellText = cell.m_strCellText;
	}
	
	/**
	 * This method tells grid that the cell wil support editing.
	 * Override this method returning TRUE to allow grid to invoke 
	 * onStartEdit() 
	 */
	public boolean supportEditing()
	{
		return (m_Editor != null);
	}
	
	/**
	 * This event occurs when the grid request a cell for editing. You must
	 * return FALSE if you don´t want to edit the cell contents. Override this 
	 * method for handling start of editing. 
	 */
	public boolean onStartEdit(Grid grid, GridCellID cellID, Rect cellRect, Color clrForeColor, Color clrBackColor, Font cellFont, int chrCode)
	{
		if (supportEditing())
		{
			return m_Editor.onStartEdit(grid, this, cellID, cellRect, clrForeColor, clrBackColor, cellFont, chrCode);
		}
		
		return false;
	}
	
	/**
	 * This event will be called by the cell editor when the editor loses input
	 * focus or the user finished entering cell data. Override this method for 
	 * handling end of editing. You must return TRUE for notify user that editing
	 * was finished.
	 */
	public boolean onEndEdit()
	{
		if (supportEditing())
		{
			return m_Editor.onEndEdit();
		}
		
		return false;
	}

	/**
	 * Draw the cell background
	 */
	protected void drawCellBackground(GridCellDrawParams params)
	{
		int iDiff = (!params.bIsFixed ? 0 : 1);
		
		params.graphics.setBackColor(params.clrCellBackColor);

		params.graphics.fillRect(params.rcClip.x + iDiff, params.rcClip.y + iDiff, 
				params.rcClip.width - params.iColSeparatorWidth - iDiff, 
				params.rcClip.height - params.iRowSeparatorHeight - iDiff);
	}

	/**
	 * Draw the cell borders
	 */
	protected void drawCellBorders(GridCellDrawParams params)
	{		
		if (params.bIsFixed)
		{
			params.graphics.setForeColor(params.clrCellBackColor.brighter(48));
				
			params.graphics.drawLine(
					params.rcClip.x, 
					params.rcClip.y, 
					(params.rcClip.x + params.rcClip.width) - params.iColSeparatorWidth - 1, 
					params.rcClip.y);
			
			params.graphics.drawLine(
					params.rcClip.x, 
					params.rcClip.y,
					params.rcClip.x,
					(params.rcClip.y + params.rcClip.height) - params.iRowSeparatorHeight - 1);

			params.graphics.setForeColor(params.clrCellBackColor.darker(48));
				
			if (params.iCellHeight <= params.rcClip.height)
			{
				int y = (params.rcClip.y + params.rcClip.height) - params.iRowSeparatorHeight - 1;
				
				params.graphics.drawLine(
						params.rcClip.x + 1, 
						y, 
						params.rcClip.x + params.rcClip.width - params.iColSeparatorWidth, 
						y);
			}
			
			if (params.iCellWidth <= params.rcClip.width)
			{
				int x = (params.rcClip.x + params.rcClip.width) - params.iColSeparatorWidth - 1;
				
				params.graphics.drawLine(
						x, 
						params.rcClip.y, 
						x, 
						params.rcClip.y + params.rcClip.height - params.iRowSeparatorHeight);
			}
		}
		
		if (params.iRowSeparatorHeight > 0)
		{
			if (params.iRowSeparatorHeight == 1)
			{
				params.graphics.setForeColor(params.clrGridCellSeparatorColor);
				
				params.graphics.drawLine(
					params.rcClip.x, 
					(params.rcClip.y + params.rcClip.height) - 1, 
					(params.rcClip.x + params.rcClip.width) - 1, 
					(params.rcClip.y + params.rcClip.height) - 1);
			}
			else
			{
				params.graphics.setBackColor(params.clrGridCellSeparatorColor);
				
				params.graphics.fillRect(
					params.rcClip.x, 
					(params.rcClip.y + params.rcClip.height) - params.iRowSeparatorHeight, 
					params.rcClip.width, 
					params.iRowSeparatorHeight);
			}
		}
			
		if (params.iColSeparatorWidth > 0)
		{
			if (params.iColSeparatorWidth == 1)
			{
				params.graphics.setForeColor(params.clrGridCellSeparatorColor);
				
				params.graphics.drawLine(
					(params.rcClip.x + params.rcClip.width) - 1, 
					params.rcClip.y, 
					(params.rcClip.x + params.rcClip.width) - 1, 
					(params.rcClip.y + params.rcClip.height) - 1);
			}
			else
			{
				params.graphics.setBackColor(params.clrGridCellSeparatorColor);
				
				params.graphics.fillRect(
					(params.rcClip.x + params.rcClip.width) - params.iColSeparatorWidth, 
					params.rcClip.y, 
					params.iColSeparatorWidth, 
					params.rcClip.height);
			}
		}
	}

	/**
	 * Draw the cell text
	 */
	protected void drawCellText(GridCellDrawParams params)
	{
		if ((params.rcClip.width > 3) && (params.rcClip.height > 2))
		{
			Rect rcNewClip = new Rect();
			Rect rcOldClip = new Rect();

			int iArrowHeight = 0;
			int iArrowX = 0;
			int iArrowY = 0;
			int iSpacing = 0;
			int iTextX;
			int iTextY;
			int hAlign = (GridCellAttributes.HORIZONTAL_ALIGN_MASK & params.iCellAlignment);
			int vAlign = (GridCellAttributes.VERTICAL_ALIGN_MASK & params.iCellAlignment);
			int iTextGapX = (!params.bIsFixed ? 0 : 2) + params.iColSeparatorWidth;
			int iTextGapY = (!params.bIsFixed ? 0 : 2) + params.iRowSeparatorHeight; 
					
			rcNewClip.x = params.rcClip.x + 1;
			rcNewClip.y = params.rcClip.y;
			rcNewClip.width = params.rcClip.width - (params.iColSeparatorWidth + 2);
			rcNewClip.height = params.rcClip.height - params.iRowSeparatorHeight;
			
			if (params.bIsFixed)
			{
				rcNewClip.x++;
				rcNewClip.y++;
				rcNewClip.width -= 2;
				rcNewClip.height -= 2;
				
				if (params.iArrowDrawStyle != GridCellDrawParams.ARROW_DRAW_NONE)
				{
					iArrowHeight = Math.min(Math.min(rcNewClip.height - 2, rcNewClip.width - 2), 5);
					
					if (iArrowHeight > 0)
					{
						iArrowX = rcNewClip.x;
						iArrowY = rcNewClip.y + ((params.iCellHeight / 2) - (iArrowHeight / 2)) - 2;
						
						iSpacing = 3;
						
						rcNewClip.x += (iArrowHeight + iSpacing);
						rcNewClip.width -= (iArrowHeight + iSpacing);
					}
					else
					{
						iArrowHeight = 0;
					}
				}
			}
			
			switch (hAlign)
			{
			case GridCellAttributes.HORIZONTAL_ALIGN_LEFT:
				{
					iTextX = rcNewClip.x;
					
					break;
				}
			case GridCellAttributes.HORIZONTAL_ALIGN_RIGHT:
				{
					iTextX = rcNewClip.x + ((params.iCellWidth - iSpacing - iTextGapX - iArrowHeight) - params.font.fm.getTextWidth(m_strCellText)) - 1;
					
					break;
				}
			case GridCellAttributes.HORIZONTAL_ALIGN_CENTER:
				{
					iTextX = rcNewClip.x + (((params.iCellWidth - iSpacing - iTextGapX - iArrowHeight) / 2) - (params.font.fm.getTextWidth(m_strCellText) / 2));
					
					break;
				}
			default:
				{
					iTextX = 0;
					
					break;
				}
			}
			
			switch (vAlign)
			{
			case GridCellAttributes.VERTICAL_ALIGN_TOP:
				{
					iTextY = rcNewClip.y;
					
					break;
				}
			case GridCellAttributes.VERTICAL_ALIGN_BOTTOM:
				{
					iTextY = rcNewClip.y + ((params.iCellHeight - iTextGapY) - params.font.fm.height) + 1;
					
					break;
				}
			case GridCellAttributes.VERTICAL_ALIGN_CENTER:
				{
					iTextY = rcNewClip.y + (((params.iCellHeight - iTextGapY) / 2) - (params.font.fm.height / 2));
					
					break;
				}
			default:
				{
					iTextY = 0;
					
					break;
				}
			}
			
			if (iArrowHeight > 0)
			{
				if (iArrowHeight == 1)
				{
					params.graphics.setPixel(iArrowX, iArrowY);
				}
				else
				{
					byte arrow;
					
					switch (params.iArrowDrawStyle)
					{
					case GridCellDrawParams.ARROW_DRAW_UP:
						{
							arrow = Graphics.ARROW_UP;
						
							break;
						}
					case GridCellDrawParams.ARROW_DRAW_DOWN:
						{
							arrow = Graphics.ARROW_DOWN;
						
							break;
						}
					case GridCellDrawParams.ARROW_DRAW_LEFT:
						{
							arrow = Graphics.ARROW_LEFT;
						
							break;
						}
					case GridCellDrawParams.ARROW_DRAW_RIGHT:
						{
							arrow = Graphics.ARROW_RIGHT;
						
							break;
						}
					default:
						{
							arrow = 0;
						}
					}

					params.graphics.drawArrow(iArrowX, iArrowY, (iArrowHeight / 2) + 1, arrow, true, true, params.clrCellForeColor);
				}
			}
			
			params.graphics.getClip(rcOldClip);
			params.graphics.setClip(rcNewClip);		
			params.graphics.setFont(params.font);				
			params.graphics.setForeColor(params.clrCellForeColor);
			params.graphics.setBackColor(params.clrCellBackColor);
			params.graphics.drawText(m_strCellText, iTextX, iTextY);
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
		
		if (m_strCellText != null)
		{
			if (m_strCellText.length() > 0)
			{
				drawCellText(params);
			}
		}
	}
}
