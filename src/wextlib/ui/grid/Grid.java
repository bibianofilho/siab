/* 
 * WExtLib - A Component Library for the Superwaba Virtual Machine
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

import wextlib.util.*;
import waba.util.*;
import waba.sys.*;
import waba.ui.*;
import waba.fx.*;

/**
 * This class implements a grid control with support for fonts, colors, 
 * editing, text alignment, etc. See javadocs for details.
 * 
 * @author Virgilio Alexandre Fornazin (mailto:virgiliofornazin@gmail.com)
 */
public class Grid extends Container
{
	/** Cell storage model */
	private GridStorageModel m_Storage;
	
	/** Number of fixed rows in the grid */
	private int m_iFixedRowCount;
	/** Number of fixed columns in the grid */
	private int m_iFixedColCount;
	
	/** Grid큦 horizontal scrollbar */
	private ScrollBar m_HorzScroll;
	/** Grid큦 vertical scrollbar */
	private ScrollBar m_VertScroll;
 
	/** Grid큦 vertical scrollbar live scrolling */
	private boolean m_bLiveScrollingHorizontal;
	/** Grid큦 horizontal scrollbar live scrolling */
	private boolean m_bLiveScrollingVertical;
	
	/** Height of the vertical scroll bar */
	private int m_iHorizontalScrollHeight;
	/** Width of the vertical scroll bar */
	private int m_iVerticalScrollWidth;
	
	/** Indicator for updating grid display on operations */
	private boolean m_bUpdating;
	
	/** Indicator if grid allow cell editing */
	private boolean m_bAllowEditCells;
	/** Indicator if grid will start editing on a single click on a cell */
	private boolean m_bEditOnSingleClick;
	
	/** Hashtable that store rows that couldn't be edited */
	private IntHashtable m_mapEditRowLocks;
	
	/** Hashtable that store columns that couldn't be edited */
	private IntHashtable m_mapEditColLocks;

	/** Indicator for displaying full row selection */
	private boolean m_bFullRowSelectionDisplay;

	/** Indicator for highlight selected cell in full row selection mode */
	private boolean m_bHighlightSelectedCell;
	
	/** Indicator if user can resize columns at runtime */
	private boolean m_bAllowUserResizeCols;
	/** Indicator if user can resize rows at runtime */
	private boolean m_bAllowUserResizeRows;
	
	/** Grid border color */
	private Color m_clrGridBorderColor;
	/** Grid background color */
	private Color m_clrGridBackColor;
	/** Grid line separators color */
	private Color m_clrGridCellSeparatorColor;
	
	/** Indicator for drawing cell row separators */
	private boolean m_bDrawRowSeparators;
	/** Indicator for drawing cell column separators */
	private boolean m_bDrawColSeparators;

	/** Size of the row separators */
	private int m_iRowSeparatorHeight;
	/** Size of the column separators */
	private int m_iColSeparatorWidth;

	/** Default row height (if not specified by setRowHeight() call) */
	private int m_iRowHeightDefault;
	/** Default column width (if not specified by setColumnWidth() call) */
	private int m_iColWidthDefault;

	/** Hashtable containing the user configured row heights */
	private IntHashtable m_mapRowHeights;
	/** Hashtable containing the user configured column widths */
	private IntHashtable m_mapColWidths;

	/** Hashtable containing the user configured row attributes. Note that row attributes take priority over column attributes */
	private GridCellAttributesHashtable m_mapRowAttributes;
	/** Hashtable containing the user configured column attributes. Note that row attributes take priority over column attributes */
	private GridCellAttributesHashtable m_mapColAttributes;
	
	/** Hashtable of default cell types for columns */
	private IntClassHashtable m_mapColCellTypes;
	
	/** Default grid cell (used for drawing) */
	private GridCell m_cellAttributesDefault;
	/** Default fixed grid cell (used for drawing) */
	private GridCell m_cellAttributesFixed;
	/** Selected grid cell (used for drawing) */
	private GridCell m_cellAttributesSelected;

	/** Indicator if we will draw alternated colors for odd/even rows */
	private boolean m_bAlternatedRowColors;
	/** Default odd rows grid cell (used for drawing) */
	private GridCell m_cellAttributesOdd;
	/** Default even rows grid cell (used for drawing) */
	private GridCell m_cellAttributesEven;
	
	/** Controls if we will draw fixed cells in 3D-style */
	private boolean m_bDraw3DFixedCells;
	
	/** Number of the top row displayed in the grid */
	private int m_iTopRow;
	/** Number of the left column displayed in the grid */
	private int m_iLeftCol;
	/** Number of the bottom row displayed in the grid */
	private int m_iBottomRow;
	/** Number of the right column displayed in the grid */
	private int m_iRightCol;

	/** Y position of the top row */
	private int m_iTopRowY;
	/** X position of the left column */
	private int m_iLeftColX;
	
	/** Indicate if the last visible row was fully Draw on the screen */
	private boolean m_bBottomRowFullDraw;
	/** Indicate if the last visible column was fully Draw on the screen */
	private boolean m_bRightColFullDraw;
	
	/** Current cell being edited */
	private GridCell m_cellEditing;
	
	/** GridCellID of the selected row in the grid */
	private GridCellID m_userSelectedCellID;
	
	/** Last time when used clicked on a cell (used for editing cell큦) */
	private Time m_lastTimeClicked;
	
	/** Vector of columns rightmost points painted in screen */
	private IntVectorExtended m_arrRightColPoints;
	/** Vector of rows bottommost points painted in screen */
	private IntVectorExtended m_arrBottomRowPoints;
	
	/** Indicator if user is resizing grid */
	private boolean m_bUserResizingGrid;

	/** Column being resized */
	private int m_iColBeingResized;
	/** Row being resized */
	private int m_iRowBeingResized;
	
	/** Original value of resizing column / row */
	private int m_iResizingOriginalValue;
	/** Currnet value of resizing column / row */
	private int m_iResizingCurrentValue;
	
	/** Graphics object used to draw grid elements */
	private Graphics m_grphGrid;
	
	/** Flag if we allow sorting of rows by clicking on a column header */
	private boolean m_bAllowSortRows;
	/** Flag if we allow sorting of columns by clicking on a row header */
	private boolean m_bAllowSortCols;
	
	/** Latest row sorted */
	private int m_iLatestRowSortedCol;
	/** Latest row sort-order */
	private boolean m_bLatestRowSortOrder;
	
	/** Latest column sorted */
	private int m_iLatestColSortedRow;
	/** Latest column sort-order */
	private boolean m_bLatestColSortOrder;
	
	/** Row sorting limits (start) */
	private int m_iRowSortLimitStart;
	/** Row sorting limits (end) */
	private int m_iRowSortLimitEnd;
	
	/** Column sorting limits (start) */
	private int m_iColSortLimitStart;
	/** Column sorting limits (end) */
	private int m_iColSortLimitEnd;
	
	/**
	 * Default constructor
	 */
	public Grid()
	{
		m_Storage = new GridStorageSingleHashtable();
		
		_initGrid(true);
	}
	
	/**
	 * Storage constructor
	 */
	public Grid(GridStorageModel model)
	{
		m_Storage = model;
		
		_initGrid(false);
	}
	
	private void _initGrid(boolean bClearGrid)
	{
		m_bUpdating = false;
		
		if (bClearGrid)
		{
			clearGrid();
		}
		else
		{
			_initSettings();
		}
		
		m_bUpdating = true;
		
		_repaintGridIfNeeded();
	}
	
	private void _initSettings()
	{
		if (m_cellEditing != null)
		{
			endCellEditing();
		}

		m_bDraw3DFixedCells = true;
		
		m_iFixedRowCount = 0;
		m_iFixedColCount = 0;

		if (m_HorzScroll == null)
		{
			m_HorzScroll = new ScrollBar(ScrollBar.HORIZONTAL);
			m_HorzScroll.setVisibleItems(1);
			
			add(m_HorzScroll);
		}
		
		if (m_VertScroll == null)
		{
			m_VertScroll = new ScrollBar(ScrollBar.VERTICAL);
			m_VertScroll.setVisibleItems(1);
			
			add(m_VertScroll);
		}

		setHorizontalScrollVisible(true);
		setVerticalScrollVisible(true);
		
		setHorizontalScrollLiveScrolling(true);
		setVerticalScrollLiveScrolling(true);
		
		m_iHorizontalScrollHeight = fm.height;
		m_iVerticalScrollWidth = fm.height;

		m_bFullRowSelectionDisplay = false;
		m_bHighlightSelectedCell = true;

		m_bAllowUserResizeCols = true;
		m_bAllowUserResizeRows = true;

		if (m_arrRightColPoints == null)
		{
			m_arrRightColPoints = new IntVectorExtended(16);
		}
		
		if (m_arrBottomRowPoints == null)
		{
			m_arrBottomRowPoints = new IntVectorExtended(32);
		}
		
		m_bUserResizingGrid = false;
		m_iColBeingResized = -1;
		m_iRowBeingResized = -1;
		m_iResizingOriginalValue = -1;
		m_iResizingCurrentValue = -1;
		
		if (Settings.uiStyle == Settings.PalmOS)
		{
			if (Settings.isColor)
			{
				m_clrGridCellSeparatorColor = new Color(0, 0, 0);
				m_clrGridBackColor = new Color(128, 128, 128);
				m_clrGridBorderColor = new Color(0, 0, 0);
			}
			else
			{
				m_clrGridCellSeparatorColor = new Color(0, 0, 0);
				m_clrGridBackColor = new Color(128, 128, 128);
				m_clrGridBorderColor = new Color(0, 0, 0);
			}
		}
		else // if (Settings.uiStyle == Settings.WinCE)
		{
			if (Settings.isColor)
			{
				m_clrGridCellSeparatorColor = new Color(64, 64, 64);
				m_clrGridBackColor = new Color(128, 128, 128);
				m_clrGridBorderColor = new Color(0, 0, 0);
			}
			else
			{
				m_clrGridCellSeparatorColor = new Color(0, 0, 0);
				m_clrGridBackColor = new Color(128, 128, 128);
				m_clrGridBorderColor = new Color(0, 0, 0);
			}
		}
		
		m_bAllowEditCells = true;
		m_bEditOnSingleClick = false;
		
		m_mapEditRowLocks = new IntHashtable(4);
		m_mapEditColLocks = new IntHashtable(4);
		
		m_bDrawRowSeparators = true;
		m_bDrawColSeparators = true;
		
		m_iRowSeparatorHeight = 1;
		m_iColSeparatorWidth = 1;
		
		m_iRowHeightDefault = fm.height + 2;
		m_iColWidthDefault = (fm.height * 3) + 2;
	
		m_mapRowHeights = new IntHashtable(4);
		m_mapColWidths = new IntHashtable(8);

		m_mapRowAttributes = new GridCellAttributesHashtable(4);
		m_mapColAttributes = new GridCellAttributesHashtable(8);
		
		m_mapColCellTypes = new IntClassHashtable(8);
		
		m_cellAttributesDefault = new GridCell();
		m_cellAttributesFixed = new GridCell();
		m_cellAttributesSelected = new GridCell();
		
		m_bAlternatedRowColors = false;
		
		m_cellAttributesOdd = new GridCell();
		m_cellAttributesEven = new GridCell();
		
		m_cellAttributesDefault.setCellAlignment(GridCellAttributes.HORIZONTAL_ALIGN_LEFT | GridCellAttributes.VERTICAL_ALIGN_CENTER);
		m_cellAttributesDefault.setCellFont(getFont());
		
		m_cellAttributesFixed.setCellAlignment(GridCellAttributes.HORIZONTAL_ALIGN_LEFT | GridCellAttributes.VERTICAL_ALIGN_CENTER);
		m_cellAttributesFixed.setCellFont(getFont());

		if (Settings.uiStyle == Settings.PalmOS)
		{
			if (Settings.isColor)
			{
				m_cellAttributesDefault.setCellBackColor(new Color(255, 255, 255));
				m_cellAttributesDefault.setCellForeColor(new Color(0, 0, 0));
		
				m_cellAttributesFixed.setCellBackColor(new Color(192, 192, 192));
				m_cellAttributesFixed.setCellForeColor(new Color(0, 0, 0));
				
				m_cellAttributesSelected.setCellBackColor(new Color(128, 128, 255));
				m_cellAttributesSelected.setCellForeColor(new Color(255, 255, 255));
				
				m_cellAttributesOdd.setCellBackColor(new Color(255, 255, 255));
				m_cellAttributesOdd.setCellForeColor(new Color(0, 0, 0));
				
				m_cellAttributesEven.setCellBackColor(new Color(255, 255, 192));
				m_cellAttributesEven.setCellForeColor(new Color(0, 0, 0));
			}
			else
			{
				m_cellAttributesDefault.setCellBackColor(new Color(255, 255, 255));
				m_cellAttributesDefault.setCellForeColor(new Color(0, 0, 0));
		
				m_cellAttributesFixed.setCellBackColor(new Color(0, 0, 0));
				m_cellAttributesFixed.setCellForeColor(new Color(255, 255, 255));
				
				m_cellAttributesSelected.setCellBackColor(new Color(128, 128, 128));
				m_cellAttributesSelected.setCellForeColor(new Color(255, 255, 255));
				
				m_cellAttributesOdd.setCellBackColor(new Color(255, 255, 255));
				m_cellAttributesOdd.setCellForeColor(new Color(0, 0, 0));
				
				m_cellAttributesEven.setCellBackColor(new Color(0, 0, 0));
				m_cellAttributesEven.setCellForeColor(new Color(255, 255, 255));
			}
		}
		else // if (Settings.uiStyle == Settings.WinCE)
		{
			if (Settings.isColor)
			{
				m_cellAttributesDefault.setCellBackColor(new Color(255, 255, 255));
				m_cellAttributesDefault.setCellForeColor(new Color(0, 0, 0));
		
				m_cellAttributesFixed.setCellBackColor(new Color(192, 192, 192));
				m_cellAttributesFixed.setCellForeColor(new Color(0, 0, 0));
				
				m_cellAttributesSelected.setCellBackColor(new Color(0, 0, 255));
				m_cellAttributesSelected.setCellForeColor(new Color(255, 255, 255));
				
				m_cellAttributesOdd.setCellBackColor(new Color(255, 255, 255));
				m_cellAttributesOdd.setCellForeColor(new Color(0, 0, 0));
				
				m_cellAttributesEven.setCellBackColor(new Color(255, 255, 192));
				m_cellAttributesEven.setCellForeColor(new Color(0, 0, 0));
			}
			else
			{
				m_cellAttributesDefault.setCellBackColor(new Color(255, 255, 255));
				m_cellAttributesDefault.setCellForeColor(new Color(0, 0, 0));
		
				m_cellAttributesFixed.setCellBackColor(new Color(0, 0, 0));
				m_cellAttributesFixed.setCellForeColor(new Color(255, 255, 255));
				
				m_cellAttributesSelected.setCellBackColor(new Color(128, 128, 128));
				m_cellAttributesSelected.setCellForeColor(new Color(255, 255, 255));
				
				m_cellAttributesOdd.setCellBackColor(new Color(255, 255, 255));
				m_cellAttributesOdd.setCellForeColor(new Color(0, 0, 0));
				
				m_cellAttributesEven.setCellBackColor(new Color(0, 0, 0));
				m_cellAttributesEven.setCellForeColor(new Color(255, 255, 255));
			}
		}
		
		m_iTopRow = 0;
		m_iLeftCol = 0;
		m_iBottomRow = 0;
		m_iRightCol = 0;

		m_iTopRowY = 0;
		m_iLeftColX = 0;
		
		m_bBottomRowFullDraw = true;
		m_bRightColFullDraw = true;
		
		m_cellEditing = null;
		
		m_userSelectedCellID = new GridCellID();
		
		m_lastTimeClicked = null;		

		m_bAllowSortRows = false;
		m_bAllowSortCols = false;

		m_iLatestRowSortedCol = -1;
		m_bLatestRowSortOrder = false;
						
		m_iLatestColSortedRow = -1;
		m_bLatestColSortOrder = false;

		m_iRowSortLimitStart = -1;
		m_iRowSortLimitEnd = 0x7ffffffe;
		
		m_iColSortLimitStart = -1;
		m_iColSortLimitEnd = 0x7ffffffe;
		
		setRowCount(m_Storage.getRowCount());
		setColCount(m_Storage.getColCount());
	}

	/**
	 * Define the new grid storage
	 */
	public void setStorage(GridStorageModel model)
	{
		m_Storage = model;
		
		_initGrid(false);
	}
		
	/**
	 * Reset the grid contents
	 */
	public void clearGrid()
	{		
		boolean bUpdating = m_bUpdating;

		m_bUpdating = false;

		_initSettings();
		m_Storage.eraseAllCells();
		
		m_bUpdating = bUpdating;
		
		_repaintGridIfNeeded();
	}   

	private void _recreateColumnCells(int iCol, Class cellType)
	{
		if (m_Storage.isReadOnly())
		{
			return;
		}
		
		GridCellID cellID = new GridCellID(-1, iCol);
		GridCell cellOld;
		
		int iStorageRows = m_Storage.getRowCount();
		
		for (cellID.Row = m_iFixedRowCount; cellID.Row < iStorageRows; cellID.Row++)
		{
			cellOld = m_Storage.getCell(cellID);
			
			if (cellOld == null)
			{
				try
				{
					m_Storage.putCell(cellID, (GridCell) cellType.newInstance());
				}
				catch (Exception e)
				{
				}
			}
			else if (cellType.getName().compareTo(cellOld.getClass().getName()) != 0)
			{
				try
				{
					GridCell cellNew = (GridCell) cellType.newInstance();
					cellNew.copyContents(cellOld);
					
					m_Storage.putCell(cellID, cellNew);
				}
				catch (Exception e)
				{
				}
			}
		}
	}

	/** 
	 * Get if grid will start editing on a single click on a cell 
	 */
	public boolean getEditOnSingleClick()
	{
		return m_bEditOnSingleClick;
	}

	/** 
	 * Set if grid will start editing on a single click on a cell
	 */
	public void setEditOnSingleClick(boolean bEditOnSingleClick)
	{
		m_bEditOnSingleClick = bEditOnSingleClick;
	}
	
	/** 
	 * Get if we will allow cell-contents editing 
	 */
	public boolean getAllowEditCells()
	{
		return m_bAllowEditCells;
	}

	/** 
	 * Set if we will allow cell-contents editing
	 */
	public void setAllowEditCells(boolean bAllowEditCells)
	{
		m_bAllowEditCells = bAllowEditCells;
		
		endCellEditing();
	}
	
	/** 
	 * Lock a row for editing 
	 */
	public void lockRowEditing(int iRow)
	{
		m_mapEditRowLocks.put(iRow, iRow);
	}
	
	/** 
	 * Unlock a row for editing 
	 */
	public void unlockRowEditing(int iRow)
	{
		m_mapEditRowLocks.remove(iRow);
	}
	
	/** 
	 * Unlock all row for editing 
	 */
	public void unlockAllRowsEditing()
	{
		m_mapEditRowLocks = new IntHashtable(4);
	}
	
	/** 
	 * Lock a column for editing 
	 */
	public void lockColEditing(int iCol)
	{
		m_mapEditColLocks.put(iCol, iCol);
	}
	
	/** 
	 * Unlock a column for editing 
	 */
	public void unlockColEditing(int iCol)
	{
		m_mapEditColLocks.remove(iCol);
	}
	
	/** 
	 * Unlock all column for editing 
	 */
	public void unlockAllColsEditing()
	{
		m_mapEditColLocks = new IntHashtable(4);
	}
		
	/** 
	 * Get if we will draw alternated colors for odd/even rows 
	 */
	public boolean getAlternatedRowColors()
	{
		return m_bAlternatedRowColors;
	}

	/** 
	 * Set if we will draw alternated colors for odd/even rows 
	 */
	public void setAlternatedRowColors(boolean bAlternatedRowColors)
	{
		m_bAlternatedRowColors = bAlternatedRowColors;
		
		_repaintGridIfNeeded();
	}

	/** 
	 * Set if we will draw alternated colors for odd/even rows and define the colors 
	 */
	public void setAlternatedRowColorsEx(boolean bAlternatedRowColors, 
			Color clrCellForeColorOdd, Color clrCellBackColorOdd,
			Color clrCellForeColorEven, Color clrCellBackColorEven)
	{
		m_bAlternatedRowColors = bAlternatedRowColors;
		
		m_cellAttributesOdd.setCellBackColor(clrCellBackColorOdd);
		m_cellAttributesOdd.setCellForeColor(clrCellForeColorOdd);
		
		m_cellAttributesEven.setCellBackColor(clrCellBackColorEven);
		m_cellAttributesEven.setCellForeColor(clrCellForeColorEven);
		
		_repaintGridIfNeeded();
	}
	
	/** 
	 * Get if we allow sorting of rows by clicking on a column header 
	 */
	public boolean getAllowSortRows()
	{
		return m_bAllowSortRows;
	}
	
	/** 
	 * Set if we allow sorting of rows by clicking on a column header 
	 */
	public void setAllowSortRows(boolean bAllowSortRows)
	{
		m_bAllowSortRows = bAllowSortRows;
	}

	/** 
	 * Set the row start / end limits allowed for sorting rows by clicking on header row 
	 */
	public void setRowSortLimits(int iRowSortLimitStart, int iRowSortLimitEnd)
	{
		m_iRowSortLimitStart = iRowSortLimitStart;
		m_iRowSortLimitEnd = iRowSortLimitEnd;
	}
	
	/** 
	 * Get the row start limit allowed for sorting rows by clicking on header row 
	 */
	public int getRowSortLimitStart()
	{
		return m_iRowSortLimitStart;
	}

	/** 
	 * Set the row start limit allowed for sorting rows by clicking on header row 
	 */
	public void setRowSortLimitStart(int iRowSortLimitStart)
	{
		m_iRowSortLimitStart = iRowSortLimitStart;
	}
	
	/** 
	 * Get the row end limit allowed for sorting rows by clicking on header row 
	 */
	public int getRowSortLimitEnd()
	{
		return m_iRowSortLimitEnd;
	}

	/** 
	 * Set the row end limit allowed for sorting rows by clicking on header row 
	 */
	public void setRowSortLimitEnd(int iRowSortLimitEnd)
	{
		m_iRowSortLimitEnd = iRowSortLimitEnd;
	}
	
	/** 
	 * Get if we allow sorting of columns by clicking on a row header 
	 */
	public boolean getAllowSortCols()
	{
		return m_bAllowSortCols;
	}
	
	/** 
	 * Set if we allow sorting of columns by clicking on a row header 
	 */
	public void setAllowSortCols(boolean bAllowSortCols)
	{
		m_bAllowSortCols = bAllowSortCols;
	}

	/** 
	 * Set the column start / end limits allowed for sorting columns by clicking on header column 
	 */
	public void setColSortLimits(int iColSortLimitStart, int iColSortLimitEnd)
	{
		m_iColSortLimitStart = iColSortLimitStart;
		m_iColSortLimitEnd = iColSortLimitEnd;
	}
	
	/** 
	 * Get the column start limit allowed for sorting column by clicking on header column 
	 */
	public int getColSortLimitStart()
	{
		return m_iColSortLimitStart;
	}

	/** 
	 * Set the column start limit allowed for sorting columns by clicking on header column 
	 */
	public void setColSortLimitStart(int iColSortLimitStart)
	{
		m_iColSortLimitStart = iColSortLimitStart;
	}
	
	/** 
	 * Get the column end limit allowed for sorting column by clicking on header column 
	 */
	public int getColSortLimitEnd()
	{
		return m_iColSortLimitEnd;
	}

	/** 
	 * Set the column end limit allowed for sorting columns by clicking on header column 
	 */
	public void setColSortLimitEnd(int iColSortLimitEnd)
	{
		m_iColSortLimitEnd = iColSortLimitEnd;
	}
		
	/**
	 * Get if we will draw fixed cells in 3D style
	 */
	public boolean getDraw3DFixedCells()
	{
		return m_bDraw3DFixedCells;
	}
	
	/**
	 * Set if we will draw fixed cells in 3D style
	 */
	public void setDraw3DFixedCells(boolean bDraw3DFixedCells)
	{
		m_bDraw3DFixedCells = bDraw3DFixedCells;
		
		_repaintGridIfNeeded();
	}
	
	/**
	 * Get the grid row count
	 */
	public int getRowCount()
	{
		return m_Storage.getRowCount();
	}
	
	/**
	 * Set the grid row count
	 */
	public boolean setRowCount(int iRowCount)
	{
		if (iRowCount < 0)
		{
			return false;
		}

		int iStorageRows = m_Storage.getRowCount();
		
		if (!m_Storage.setRowCount(iRowCount))
		{
			return false;
		}
		
		boolean bMustCreateCells = (iRowCount > iStorageRows);
		
		if (iRowCount < iStorageRows)
		{
			for (int i = iRowCount; i < iStorageRows; i++)
			{
				m_mapRowAttributes.remove(i);
				m_mapRowHeights.remove(i);
			}
		}
		
		if (m_iTopRow > (iRowCount - 1))
		{
			m_iTopRow = iRowCount - 1;
		}
		
		if (m_iFixedRowCount > iRowCount)
		{
			m_iFixedRowCount = iRowCount;
		}
		
		if (m_userSelectedCellID.Row > (iRowCount - 1))
		{
			m_userSelectedCellID.Row = -1;
			m_userSelectedCellID.Col = -1;
		}
		
		iStorageRows = iRowCount;
		
		if ((iStorageRows > 0) && (m_iTopRow < m_iFixedRowCount))
		{
			m_iTopRow = m_iFixedRowCount;
		}

		if (bMustCreateCells)
		{
			IntVector keys;
			int iCount;
			int iCol;
			
			keys = m_mapColCellTypes.getKeys();
			iCount = m_mapColCellTypes.size();
			
			for (int c = 0; c < iCount; c++)
			{
				iCol = keys.items[c];
				
				_recreateColumnCells(iCol, m_mapColCellTypes.get(iCol));
			}
		}
		
		m_VertScroll.setMinimum(0);
		m_VertScroll.setMaximum(iStorageRows - m_iFixedRowCount);
		m_VertScroll.setValue(m_iTopRow - m_iFixedRowCount);
		m_VertScroll.setEnabled((iStorageRows > m_iFixedRowCount));
		
		_repaintGridIfNeeded();
		
		return true;
	}

	/**
	 * Get the grid column count
	 */
	public int getColCount()
	{
		return m_Storage.getColCount();
	}
	
	/**
	 * Set the grid column count
	 */
	public boolean setColCount(int iColCount)
	{
		if (iColCount < 0)
		{
			return false;
		}

		int iStorageCols = m_Storage.getColCount();
		
		if (!m_Storage.setColCount(iColCount))
		{
			return false;
		}
		
		if (iColCount < iStorageCols)
		{
			for (int i = iColCount; i < iStorageCols; i++)
			{
				m_mapColCellTypes.remove(i);
				m_mapColAttributes.remove(i);
				m_mapColWidths.remove(i);
			}
		}
		
		if (m_iLeftCol > (iColCount - 1))
		{
			m_iLeftCol = iColCount - 1;
		}
		
		if (m_iFixedColCount > iColCount)
		{
			m_iFixedColCount = iColCount;
		}
		
		if (m_userSelectedCellID.Col > (iColCount - 1))
		{
			m_userSelectedCellID.Row = -1;
			m_userSelectedCellID.Col = -1;
		}
		
		iStorageCols = iColCount;
		
		if ((iStorageCols > 0) && (m_iLeftCol < m_iFixedColCount))
		{
			m_iLeftCol = m_iFixedColCount;
		}
		
		m_HorzScroll.setMinimum(0);
		m_HorzScroll.setMaximum(iStorageCols - m_iFixedColCount);
		m_HorzScroll.setValue(m_iLeftCol - m_iFixedColCount);
		m_HorzScroll.setEnabled((iStorageCols > m_iFixedColCount));

		_repaintGridIfNeeded();
		
		return true;
	}
	
	/**
	 * Get the fixed row count
	 */
	public int getFixedRowCount()
	{
		return m_iFixedRowCount;
	}
	
	/**
	 * Set the fixed row count
	 */
	public void setFixedRowCount(int iFixedRowCount)
	{
		int iStorageRows = m_Storage.getRowCount();
		
		if (iFixedRowCount > iStorageRows)
		{
			return;
		}

		if (m_iTopRow < iFixedRowCount)
		{
			m_iTopRow = iFixedRowCount;
		}

		m_iFixedRowCount = iFixedRowCount;
		
		m_VertScroll.setMinimum(0);
		m_VertScroll.setMaximum(iStorageRows - m_iFixedRowCount);
		m_VertScroll.setValue(m_iTopRow - m_iFixedRowCount);
		m_VertScroll.setEnabled((iStorageRows > m_iFixedRowCount));
		
		_repaintGridIfNeeded();
	}
	
	/**
	 * Get the fixed column count
	 */
	public int getFixedColCount()
	{
		return m_iFixedColCount;
	}
	
	/**
	 * Set the fixed column count
	 */
	public void setFixedColCount(int iFixedColCount)
	{
		int iStorageCols = m_Storage.getColCount();
		
		if (iFixedColCount > iStorageCols)
		{
			return;
		}
		
		if (m_iLeftCol < iFixedColCount)
		{
			m_iLeftCol = iFixedColCount;
		}

		m_iFixedColCount = iFixedColCount;
		
		m_HorzScroll.setMinimum(0);
		m_HorzScroll.setMaximum(iStorageCols - m_iFixedColCount);
		m_HorzScroll.setValue(m_iLeftCol - m_iFixedColCount);
		m_HorzScroll.setEnabled((iStorageCols > m_iFixedColCount));

		_repaintGridIfNeeded();
	}
	
	/**
	 * Get the horizontal scrollbar visibility
	 */
	public boolean getHorizontalScrollVisible()
	{
		return m_HorzScroll.isVisible();
	}
	
	/**
	 * Set the horizontal scrollbar visibility
	 */
	public void setHorizontalScrollVisible(boolean bVisible)
	{
		m_HorzScroll.setVisible(bVisible);
		
		_repaintGridIfNeeded();
	}
	
	/**
	 * Get the vertical scrollbar visibility
	 */
	public boolean getVerticalScrollVisible()
	{
		return m_VertScroll.isVisible();
	}
		
	/**
	 * Set the vertical scrollbar visibility
	 */
	public void setVerticalScrollVisible(boolean bVisible)
	{
		m_VertScroll.setVisible(bVisible);
		
		_repaintGridIfNeeded();
	}

	/**
	 * Get the horizontal scrollbar live scrolling
	 */
	public boolean getHorizontalScrollLiveScrolling()
	{
		return m_bLiveScrollingHorizontal;
	}
	
	/**
	 * Set the horizontal scrollbar live scrolling
	 */
	public void setHorizontalScrollLiveScrolling(boolean bLiveScrolling)
	{
		m_bLiveScrollingHorizontal = bLiveScrolling;
		m_HorzScroll.setLiveScrolling(m_bLiveScrollingHorizontal);
		
		_repaintGridIfNeeded();
	}
	
	/**
	 * Get the vertical scrollbar live scrolling
	 */
	public boolean getVerticalScrollLiveScrolling()
	{
		return m_bLiveScrollingVertical;
	}
	
	/**
	 * Set the vertical scrollbar live scrolling
	 */
	public void setVerticalScrollLiveScrolling(boolean bLiveScrolling)
	{
		m_bLiveScrollingVertical = bLiveScrolling;
		m_VertScroll.setLiveScrolling(m_bLiveScrollingVertical);
		
		_repaintGridIfNeeded();
	}

	/**
	 * Get the current horizontal scroll height
	 */
	public int getHorizontalScrollHeight()
	{
		return m_iHorizontalScrollHeight;
	}
	
	/**
	 * Set the current horizontal scroll height
	 */
	public void setHorizontalScrollHeight(int iHorizontalScrollHeight)
	{
		m_iHorizontalScrollHeight = iHorizontalScrollHeight;
		
		_repositionScrollBars();
	}

	/**
	 * Get the current vertical scroll width
	 */
	public int getVerticalScrollWidth()
	{
		return m_iVerticalScrollWidth;
	}
	
	/**
	 * Set the current vertical scroll width
	 */
	public void setVerticalScrollWidth(int iVerticalScrollWidth)
	{
		m_iVerticalScrollWidth = iVerticalScrollWidth;
		
		_repositionScrollBars();
	}

	/**
	 * Get if the grid will update the screen after operations
	 */
	public boolean getUpdating()
	{
		return m_bUpdating;
	}
	
	/**
	 * Set if the grid will update the screen after operations
	 */
	public void setUpdating(boolean bUpdating)
	{
		if (isVisible() && !m_bUpdating && bUpdating)
		{
			m_bUpdating = bUpdating;
			
			_repaintGridIfNeeded();
		}
	}

	/**
	 * Get if the grid will highlight entire row of the selected cell
	 */
	public boolean getFullRowSelectionDisplay()
	{
		return m_bFullRowSelectionDisplay;
	}

	/**
	 * Set if the grid will highlight entire row of the selected cell
	 */
	public void setFullRowSelectionDisplay(boolean bFullRowSelectionDisplay)
	{
		m_bFullRowSelectionDisplay = bFullRowSelectionDisplay;
		
		_repaintGridIfNeeded();
	}

	/**
	 * Get if the grid will highlight the selected cell in full-row selection mode
	 */
	public boolean getHighlightSelectedCell()
	{
		return m_bHighlightSelectedCell;
	}

	/**
	 * Set if the grid will highlight the selected cell in full-row selection mode
	 */
	public void setHighlightSelectedCell(boolean bHighlightSelectedCell)
	{
		m_bHighlightSelectedCell = bHighlightSelectedCell;
		
		_repaintGridIfNeeded();
	}

	/**
	 * Get if the user can dinamically resize rows at runtime
	 */
	public boolean getAllowUserResizeRows()
	{
		return m_bAllowUserResizeRows;
	}

	/**
	 * Set if the user can dinamically resize rows at runtime
	 */
	public void setAllowUserResizeRows(boolean bAllowUserResizeRows)
	{
		m_bAllowUserResizeRows = bAllowUserResizeRows;
	}

	/**
	 * Get if the user can dinamically resize columns at runtime
	 */
	public boolean getAllowUserResizeCols()
	{
		return m_bAllowUserResizeCols;
	}

	/**
	 * Set if the user can dinamically resize columns at runtime
	 */
	public void setAllowUserResizeCols(boolean bAllowUserResizeCols)
	{
		m_bAllowUserResizeCols = bAllowUserResizeCols;
	}
	
	/**
	 * Swap a grid row with another 
	 */
	public boolean swapRows(int iRow1, int iRow2)
	{
		return m_Storage.swapRows(iRow1, iRow2);
	}
	
	/**
	 * Swap a grid column with another 
	 */
	public boolean swapCols(int iCol1, int iCol2)
	{
		return m_Storage.swapCols(iCol1, iCol2);
	}
	
	/**
	 * Get the grid border color
	 */
	public Color getGridBorderColor()
	{
		return m_clrGridBorderColor;
	}
	
	/**
	 * Set the grid border color
	 */
	public void setGridBorderColor(Color clrGridBorderColor)
	{
		m_clrGridBorderColor = clrGridBorderColor;
		
		_repaintGridIfNeeded();
	}

	/**
	 * Get the grid background color
	 */
	public Color getGridBackColor()
	{
		return m_clrGridBackColor;
	}
	
	/**
	 * Set the grid background color
	 */
	public void setGridBackColor(Color clrGridBackColor)
	{
		m_clrGridBackColor = clrGridBackColor;
		
		_repaintGridIfNeeded();
	}

	/**
	 * Get the grid line separator color
	 */
	public Color getGridCellSeparatorColor()
	{
		return m_clrGridCellSeparatorColor;
	}
	
	/**
	 * Set the grid line separator color
	 */
	public void setGridCellSeparatorColor(Color clrGridCellSeparatorColor)
	{
		m_clrGridCellSeparatorColor = clrGridCellSeparatorColor;
		
		_repaintGridIfNeeded();
	}
	
	/**
	 * Get if the Grid will draw cell큦 row separators 
	 */
	public boolean getDrawRowSeparators()
	{
		return m_bDrawRowSeparators;
	}
	
	/**
	 * Set if the Grid will draw cell's row separators 
	 */
	public void setDrawRowSeparators(boolean bDrawRowSeparators)
	{
		m_bDrawRowSeparators = bDrawRowSeparators;
		
		_repaintGridIfNeeded();
	}
	
	/**
	 * Get if the Grid will draw cell큦 column separators 
	 */
	public boolean getDrawColSeparators()
	{
		return m_bDrawColSeparators;
	}
	
	/**
	 * Set if the Grid will draw cell's column separators 
	 */
	public void setDrawColSeparators(boolean bDrawColSeparators)
	{
		m_bDrawColSeparators = bDrawColSeparators;
		
		_repaintGridIfNeeded();
	}
	
	/**
	 * Get the cell's row separator size 
	 */
	public int getRowSeparatorHeight()
	{
		return m_iRowSeparatorHeight;
	}
	
	/**
	 * Set the cell's row separator size 
	 */
	public void setRowSeparatorHeight(int iRowSeparatorHeight)
	{
		if (iRowSeparatorHeight < 1)
		{
			return;
		}
		
		m_iRowSeparatorHeight = iRowSeparatorHeight;
		
		_repaintGridIfNeeded();
	}
	
	/**
	 * Get the cell's column separator size 
	 */
	public int getColSeparatorWidth()
	{
		return m_iColSeparatorWidth;
	}
	
	/**
	 * Set the cell's column separator size 
	 */
	public void setColSeparatorWidth(int iColSeparatorWidth)
	{
		if (iColSeparatorWidth < 1)
		{
			return;
		}
		
		m_iColSeparatorWidth = iColSeparatorWidth;
		
		_repaintGridIfNeeded();
	}

	/**
	 * Get the row height default for drawing the grid 
	 */
	public int getRowHeightDefault()
	{
		return m_iRowHeightDefault;
	}

	/**
	 * Set the row height default for drawing the grid (must be >= 2)
	 */
	public void setRowHeightDefault(int iRowHeightDefault)
	{
		if (iRowHeightDefault < 2)
		{
			return;
		}
		
		m_iRowHeightDefault = iRowHeightDefault;

		_repaintGridIfNeeded();
	}

	/**
	 * Get the column width default for drawing the grid 
	 */
	public int getColWidthDefault()
	{
		return m_iColWidthDefault;
	}

	/**
	 * Set the column width default for drawing the grid (must be >= 2)
	 */
	public void setColWidthDefault(int iColWidthDefault)
	{
		if (iColWidthDefault < 2)
		{
			return;
		}

		m_iColWidthDefault = iColWidthDefault;

		_repaintGridIfNeeded();
	}

	/**
	 * Get the column display width
	 */
	public int getColWidth(int iCol)
	{
		int iRes = m_mapColWidths.get(iCol);
		
		if (iRes == IntHashtable.INVALID)
		{
			iRes = m_iColWidthDefault;
		}
		
		return iRes;
	}
	
	/**
	 * Set the column display width
	 */
	public void setColWidth(int iCol, int iWidth)
	{
		if (iWidth < 0)
		{
			return;
		}
		
		m_mapColWidths.put(iCol, iWidth);
		
		_repaintGridIfNeeded();
	}
		
	/**
	 * Clear the column display width
	 */
	public void clearColWidth(int iCol)
	{
		if (m_mapColWidths.remove(iCol) != IntHashtable.INVALID)
		{
			_repaintGridIfNeeded();
		}
	}
	
	/**
	 * Get the row display height
	 */
	public int getRowHeight(int iRow)
	{
		int iRes = m_mapRowHeights.get(iRow);
		
		if (iRes == IntHashtable.INVALID)
		{
			iRes = m_iRowHeightDefault;
		}
		
		return iRes;
	}
	
	/**
	 * Set the row display height
	 */
	public void setRowHeight(int iRow, int iHeight)
	{
		if (iHeight < 0)
		{
			return;
		}
		
		m_mapRowHeights.put(iRow, iHeight);

		_repaintGridIfNeeded();
	}
	
	/**
	 * Clear the row display height
	 */
	public void clearRowHeight(int iRow)
	{
		if (m_mapRowHeights.remove(iRow) != IntHashtable.INVALID)
		{
			_repaintGridIfNeeded();
		}
	}
	
	/**
	 * Get the row alignment
	 */
	public int getRowAlignment(int iRow)
	{
		try
		{
			return m_mapRowAttributes.get(iRow).m_iCellAlignment;
		}
		catch (Exception e)
		{
			return GridCellAttributes.ALIGN_DEFAULT;
		}
	}

	/**
	 * Set the row alignment
	 */
	public void setRowAlignment(int iRow, int rowAlignment)
	{
		GridCellAttributes rowAttr = m_mapRowAttributes.get(iRow);

		if (rowAttr == null)
		{
			rowAttr = new GridCellAttributes();

			m_mapRowAttributes.put(iRow, rowAttr);
		}

		rowAttr.m_iCellAlignment = rowAlignment;

		_repaintGridIfNeeded();
	}

	/**
	 * Set the row alignment
	 */
	public void clearRowAlignment(int iRow)
	{
		try
		{
			m_mapRowAttributes.get(iRow).m_iCellAlignment = GridCellAttributes.ALIGN_DEFAULT;
		
			_repaintGridIfNeeded();
		}
		catch (Exception e)
		{
		}
	}

	/**
	 * Get the row background color
	 */
	public Color getRowBackColor(int iRow)
	{
		try
		{
			return m_mapRowAttributes.get(iRow).m_clrCellBackColor;
		}
		catch (Exception e)
		{
			return null;
		}
	}

	/**
	 * Set the row background color
	 */
	public void setRowBackColor(int iRow, Color rowBackColor)
	{
		GridCellAttributes rowAttr = m_mapRowAttributes.get(iRow);

		if (rowAttr == null)
		{
			rowAttr = new GridCellAttributes();

			m_mapRowAttributes.put(iRow, rowAttr);
		}

		rowAttr.m_clrCellBackColor = rowBackColor;

		_repaintGridIfNeeded();
	}

	/**
	 * Set the row background color
	 */
	public void clearRowBackColor(int iRow)
	{
		try
		{
			m_mapRowAttributes.get(iRow).m_clrCellBackColor = null;
			
			_repaintGridIfNeeded();
		}
		catch (Exception e)
		{
		}
	}
	
	/**
	 * Get the row foreground color
	 */
	public Color getRowForeColor(int iRow)
	{
		try
		{
			return m_mapRowAttributes.get(iRow).m_clrCellForeColor;
		}
		catch (Exception e)
		{
			return null;
		}
	}

	/**
	 * Set the row foreground color
	 */
	public void setRowForeColor(int iRow, Color rowForeColor)
	{
		GridCellAttributes rowAttr = m_mapRowAttributes.get(iRow);

		if (rowAttr == null)
		{
			rowAttr = new GridCellAttributes();

			m_mapRowAttributes.put(iRow, rowAttr);
		}

		rowAttr.m_clrCellForeColor = rowForeColor;

		_repaintGridIfNeeded();
	}

	/**
	 * Set the row foreground color
	 */
	public void clearRowForeColor(int iRow)
	{
		try
		{
			m_mapRowAttributes.get(iRow).m_clrCellForeColor = null;
			
			_repaintGridIfNeeded();
		}
		catch (Exception e)
		{
		}
	}

	/**
	 * Get the row font
	 */
	public Font getRowFont(int iRow)
	{
		try
		{
			return m_mapRowAttributes.get(iRow).m_CellFont;
		}
		catch (Exception e)
		{
			return null;
		}
	}

	/**
	 * Set the row font
	 */
	public void setRowFont(int iRow, Font rowFont)
	{
		GridCellAttributes rowAttr = m_mapRowAttributes.get(iRow);

		if (rowAttr == null)
		{
			rowAttr = new GridCellAttributes();

			m_mapRowAttributes.put(iRow, rowAttr);
		}

		rowAttr.m_CellFont = rowFont;

		_repaintGridIfNeeded();
	}

	/**
	 * Set the row font
	 */
	public void clearRowFont(int iRow)
	{
		try
		{
			m_mapRowAttributes.get(iRow).m_CellFont = null;
			
			_repaintGridIfNeeded();
		}
		catch (Exception e)
		{
		}
	}
	
	/**
	 * Get the column alignment
	 */
	public int getColAlignment(int iCol)
	{
		try
		{
			return m_mapColAttributes.get(iCol).m_iCellAlignment;
		}
		catch (Exception e)
		{
			return GridCellAttributes.ALIGN_DEFAULT;
		}
	}

	/**
	 * Set the column alignment
	 */
	public void setColAlignment(int iCol, int rowAlignment)
	{
		GridCellAttributes colAttr = m_mapColAttributes.get(iCol);

		if (colAttr == null)
		{
			colAttr = new GridCellAttributes();

			m_mapColAttributes.put(iCol, colAttr);
		}

		colAttr.m_iCellAlignment = rowAlignment;

		_repaintGridIfNeeded();
	}

	/**
	 * Set the column alignment
	 */
	public void clearColAlignment(int iCol)
	{
		try
		{
			m_mapColAttributes.get(iCol).m_iCellAlignment = GridCellAttributes.ALIGN_DEFAULT;
		
			_repaintGridIfNeeded();
		}
		catch (Exception e)
		{
		}
	}

	/**
	 * Get the column background color
	 */
	public Color getColBackColor(int iCol)
	{
		try
		{	
			return m_mapColAttributes.get(iCol).m_clrCellBackColor;
		}
		catch (Exception e)
		{
			return null;
		}
	}

	/**
	 * Set the column background color
	 */
	public void setColBackColor(int iCol, Color rowBackColor)
	{
		GridCellAttributes colAttr = m_mapColAttributes.get(iCol);

		if (colAttr == null)
		{
			colAttr = new GridCellAttributes();

			m_mapColAttributes.put(iCol, colAttr);
		}

		colAttr.m_clrCellBackColor = rowBackColor;

		_repaintGridIfNeeded();
	}

	/**
	 * Set the column background color
	 */
	public void clearColBackColor(int iCol)
	{
		try
		{
			m_mapColAttributes.get(iCol).m_clrCellBackColor = null;
			
			_repaintGridIfNeeded();
		}
		catch (Exception e)
		{
		}
	}
	/**
	 * Get the column foreground color
	 */
	public Color getColForeColor(int iCol)
	{
		try
		{	
			return m_mapColAttributes.get(iCol).m_clrCellForeColor;
		}
		catch (Exception e)
		{
			return null;
		}
	}

	/**
	 * Set the column foreground color
	 */
	public void setColForeColor(int iCol, Color rowForeColor)
	{
		GridCellAttributes colAttr = m_mapColAttributes.get(iCol);

		if (colAttr == null)
		{
			colAttr = new GridCellAttributes();

			m_mapColAttributes.put(iCol, colAttr);
		}

		colAttr.m_clrCellForeColor = rowForeColor;

		_repaintGridIfNeeded();
	}

	/**
	 * Set the column foreground color
	 */
	public void clearColForeColor(int iCol)
	{
		try
		{
			m_mapColAttributes.get(iCol).m_clrCellForeColor = null;
			
			_repaintGridIfNeeded();
		}
		catch (Exception e)
		{
		}
	}

	/**
	 * Get the column font
	 */
	public Font getColFont(int iCol)
	{
		try
		{	
			return m_mapColAttributes.get(iCol).m_CellFont;
		}
		catch (Exception e)
		{
			return null;
		}
	}

	/**
	 * Set the column font
	 */
	public void setColFont(int iCol, Font rowFont)
	{
		GridCellAttributes colAttr = m_mapColAttributes.get(iCol);

		if (colAttr == null)
		{
			colAttr = new GridCellAttributes();

			m_mapColAttributes.put(iCol, colAttr);
		}

		colAttr.m_CellFont = rowFont;

		_repaintGridIfNeeded();
	}

	/**
	 * Set the column font
	 */
	public void clearColFont(int iCol)
	{
		try
		{
			m_mapColAttributes.get(iCol).m_CellFont = null;
			
			_repaintGridIfNeeded();
		}
		catch (Exception e)
		{
		}
	}

	/**
	 * Get the column cell type
	 */
	public Class getColCellType(int iCol)
	{
		return m_mapColCellTypes.get(iCol);
	}
	
	/**
	 * Set the column cell type
	 */
	public void setColCellType(int iCol, Class cellType)
	{
		m_mapColCellTypes.put(iCol, cellType);
		
		boolean bUpdating = m_bUpdating;
		
		m_bUpdating = false;
		
		_recreateColumnCells(iCol, cellType);
		
		m_bUpdating = bUpdating;
		
		_repaintGridIfNeeded();
	}
	
	/**
	 * Clear the column cell type
	 */
	public void clearColCellType(int iCol)
	{
		m_mapColCellTypes.remove(iCol);
	}
	
	/**
	 * Get the default cell alignment
	 */
	public int getDefaultCellAlignment()
	{
		return m_cellAttributesDefault.m_iCellAlignment;
	}
	
	/** 
	 * Set the default cell alignment
	 */
	public void setDefaultCellAlignment(int iAlignment)
	{
		if (iAlignment == GridCellAttributes.ALIGN_DEFAULT)
		{
			return;
		}
		
		m_cellAttributesDefault.m_iCellAlignment = iAlignment;
		
		_repaintGridIfNeeded();
	}

	/**
	 * Get the default cell background color
	 */
	public Color getDefaultCellBackColor()
	{
		return m_cellAttributesDefault.m_clrCellBackColor;
	}
	
	/** 
	 * Set the default cell background color 
	 */
	public void setDefaultCellBackColor(Color clrBackColor)
	{
		if (clrBackColor == null)
		{
			return;
		}
		
		m_cellAttributesDefault.m_clrCellBackColor = clrBackColor;
		
		_repaintGridIfNeeded();
	}	
	/** 
	 * Get the default cell foreground color 
	 */
	public Color getDefaultCellForeColor()
	{
		return m_cellAttributesDefault.m_clrCellForeColor;
	}
		
	/** 
	 * Set the default cell foreground color 
	 */
	public void setDefaultCellForeColor(Color clrForeColor)
	{
		if (clrForeColor == null)
		{
			return;
		}
		
		m_cellAttributesDefault.m_clrCellForeColor = clrForeColor;
		
		_repaintGridIfNeeded();
	}
	
	/** 
	 * Get the default cell font 
	 */
	public Font getDefaultCellFont()
	{
		return m_cellAttributesDefault.m_CellFont;
	}
	
	/** 
	 * Set the default cell font 
	 */
	public void setDefaultCellFont(Font cellFont)
	{
		if (cellFont == null)
		{
			return;
		}
		
		m_cellAttributesDefault.m_CellFont = cellFont;
		
		_repaintGridIfNeeded();
	}

	/**
	 * Get the fixed cell alignment
	 */
	public int getFixedCellAlignment()
	{
		return m_cellAttributesFixed.m_iCellAlignment;
	}
	
	/** 
	 * Set the fixed cell alignment
	 */
	public void setFixedCellAlignment(int iCellAlignment)
	{
		if (iCellAlignment == GridCellAttributes.ALIGN_DEFAULT)
		{
			return;
		}
		
		m_cellAttributesFixed.m_iCellAlignment = iCellAlignment;
		
		_repaintGridIfNeeded();
	}

	/**
	 * Get the fixed cell background color
	 */
	public Color getFixedCellBackColor()
	{
		return m_cellAttributesFixed.m_clrCellBackColor;
	}
	
	/** 
	 * Set the fixed cell background color 
	 */
	public void setFixedCellBackColor(Color clrBackColor)
	{
		if (clrBackColor == null)
		{
			return;
		}
		
		m_cellAttributesFixed.m_clrCellBackColor = clrBackColor;
		
		_repaintGridIfNeeded();
	}
	
	/** 
	 * Get the fixed cell foreground color 
	 */
	public Color getFixedCellForeColor()
	{
		return m_cellAttributesFixed.m_clrCellForeColor;
	}
		
	/** 
	 * Set the fixed cell foreground color 
	 */
	public void setFixedCellForeColor(Color clrForeColor)
	{
		if (clrForeColor == null)
		{
			return;
		}
		
		m_cellAttributesFixed.m_clrCellForeColor = clrForeColor;
		
		_repaintGridIfNeeded();
	}
	
	/** 
	 * Get the fixed cell font 
	 */
	public Font getFixedCellFont()
	{
		return m_cellAttributesFixed.m_CellFont;
	}
	
	/** 
	 * Set the fixed cell font 
	 */
	public void setFixedCellFont(Font cellFont)
	{
		if (cellFont == null)
		{
			return;
		}
		
		m_cellAttributesFixed.m_CellFont = cellFont;
		
		_repaintGridIfNeeded();
	}

	/**
	 * Get the selected cell alignment
	 */
	public int getSelectedCellAlignment()
	{
		return m_cellAttributesSelected.m_iCellAlignment;
	}
	
	/** 
	 * Set the selected cell alignment
	 */
	public void setSelectedCellAlignment(int iCellAlignment)
	{
		m_cellAttributesSelected.m_iCellAlignment = iCellAlignment;
		
		_repaintGridIfNeeded();
	}

	/**
	 * Get the selected cell background color
	 */
	public Color getSelectedCellBackColor()
	{
		return m_cellAttributesSelected.m_clrCellBackColor;
	}
	
	/** 
	 * Set the selected cell background color 
	 */
	public void setSelectedCellBackColor(Color clrBackColor)
	{
		if (clrBackColor == null)
		{
			return;
		}
		
		m_cellAttributesSelected.m_clrCellBackColor = clrBackColor;
		
		_repaintGridIfNeeded();
	}
	
	/** 
	 * Get the selected cell foreground color 
	 */
	public Color getSelectedCellForeColor()
	{
		return m_cellAttributesSelected.m_clrCellForeColor;
	}
		
	/** 
	 * Set the selected cell foreground color 
	 */
	public void setSelectedCellForeColor(Color clrForeColor)
	{
		if (clrForeColor == null)
		{
			return;
		}
		
		m_cellAttributesSelected.m_clrCellForeColor = clrForeColor;
		
		_repaintGridIfNeeded();
	}

	/** 
	 * Get the selected cell font 
	 */
	public Font getSelectedCellFont()
	{
		return m_cellAttributesSelected.m_CellFont;
	}
	
	/** 
	 * Set the selected cell font 
	 */
	public void setSelectedCellFont(Font cellFont)
	{
		m_cellAttributesSelected.m_CellFont = cellFont;
		
		_repaintGridIfNeeded();
	}

	/**
	 * Get the grid top displaying row  
	 */
	public int getTopRow()
	{
		return m_iTopRow;
	}
	
	private void _setTopRowEx(int iRow)
	{
		int iStorageRows = m_Storage.getRowCount();

		if (iRow > iStorageRows - 1)
		{
			m_iTopRow = iStorageRows - 1;
		}
		else
		{
			if (iRow < m_iFixedRowCount)
			{
				m_iTopRow = m_iFixedRowCount;
			}
			else
			{
				m_iTopRow = iRow;
			}
		}

		_repaintGridIfNeeded();
	}
	
	/**
	 * Set the grid top displaying row  
	 */
	public void setTopRow(int iRow)
	{
		boolean bUpdating = m_bUpdating;
		
		m_bUpdating = false;
		
		_setTopRowEx(iRow);
		
		m_VertScroll.setValue(m_iTopRow);
		
		m_bUpdating = bUpdating;
		
		_repaintGridIfNeeded();
	}

	/**
	 * Get the bottommost displaying row 
	 */
	public int getBottomRow()
	{
		return m_iBottomRow;
	}
	
	/**
	 * Get if the bottommost displaying row was fully draw
	 */
	public boolean getBottomRowFullDraw()
	{
		return m_bBottomRowFullDraw;
	}
	
	/**
	 * Get the grid left displaying column
	 */
	public int getLeftCol()
	{
		return m_iLeftCol;
	}
	
	private void _setLeftColEx(int iCol)
	{
		int iStorageCols = m_Storage.getColCount();

		if (iCol > iStorageCols - 1)
		{
			m_iLeftCol = iStorageCols - 1;
		}
		else
		{
			if (iCol < m_iFixedColCount)
			{
				m_iLeftCol = m_iFixedColCount;
			}
			else
			{
				m_iLeftCol = iCol;
			}
		}
		
		_repaintGridIfNeeded();
	}
	
	/**
	 * Set the grid left displaying column
	 */
	public void setLeftCol(int iCol)
	{
		boolean bUpdating = m_bUpdating;
		
		m_bUpdating = false;
		
		_setLeftColEx(iCol);
		
		m_HorzScroll.setValue(m_iLeftCol);
		
		m_bUpdating = bUpdating;
		
		_repaintGridIfNeeded();
	}

	/**
	 * Get the rightmost displaying column 
	 */
	public int getRightCol()
	{
		return m_iRightCol;
	}
	
	/**
	 * Get if the rightmost displaying column was fully draw
	 */
	public boolean getRightColFullDraw()
	{
		return m_bRightColFullDraw;
	}
	
	/**
	 * Get the grid top-left displaying cell object
	 */
	public GridCell getTopLeftCell()
	{
		return getCell(m_iTopRow, m_iLeftCol);
	}

	/**
	 * Get the grid top-left displaying cell id
	 */
	public GridCellID getTopLeftCellID()
	{
		return new GridCellID(m_iTopRow, m_iLeftCol);
	}
	
	/**
	 * Set the grid top-left displaying cell id
	 */
	public void setTopLeftCellID(GridCellID cellID)
	{
		int iStorageRows = m_Storage.getRowCount();
		int iStorageCols = m_Storage.getColCount();
		
		if (!cellID.isValid(iStorageRows, iStorageCols))
		{
			return;
		}

		boolean bUpdating = m_bUpdating;
		
		setUpdating(false);
		
		setTopRow(cellID.Row);
		setLeftCol(cellID.Col);
		
		setUpdating(bUpdating);
	}
	
	/**
	 * Set the grid top-left displaying cell id
	 */
	public void setTopLeftCellID(int iRow, int iCol)
	{
		setTopLeftCellID(new GridCellID(iRow, iCol));
	}
	
	/**
	 * Get all cells from a row
	 */
	public GridCellHashtable getRowCells(int iRow)
	{
		return m_Storage.getRowCells(iRow);
	}
	
	/**
	 * Get cells into a row
	 */
	public void putRowCells(int iRow, GridCellHashtable gcht)
	{
		m_Storage.putRowCells(iRow, gcht);
	}
	
	/**
	 * Erase all cells from a row
	 */
	public void eraseRowCells(int iRow)
	{
		m_Storage.eraseRowCells(iRow);
	}
	
	/**
	 * Get all cells from a non-fixed row
	 */
	public GridCellHashtable getNonFixedRowCells(int iNonFixedRow)
	{
		return m_Storage.getRowCells(m_iFixedRowCount + iNonFixedRow);
	}
	
	/**
	 * Get all cells from a column
	 */
	public GridCellHashtable getColCells(int iCol)
	{
		return m_Storage.getColCells(iCol);
	}
	
	/**
	 * Get cells into a column
	 */
	public void putColCells(int iCol, GridCellHashtable gcht)
	{
		m_Storage.putColCells(iCol, gcht);
	}
	
	/**
	 * Erase all cells from a column
	 */
	public void eraseColCells(int iCol)
	{
		m_Storage.eraseColCells(iCol);
	}
	
	/**
	 * Get all cells from a non-fixed column
	 */
	public GridCellHashtable getNonFixedColCells(int iNonFixedCol)
	{
		return m_Storage.getColCells(m_iFixedColCount + iNonFixedCol);
	}
	
	/**
	 * Get a grid cell object (creates one if it큦 not already created)
	 */
	public GridCell getCell(GridCellID cellID)
	{
		GridCell cell = m_Storage.getCell(cellID);

		if (cell == null)
		{
			if (!m_Storage.isReadOnly())
			{
				Class cellType = m_mapColCellTypes.get(cellID.Col);
			
				if (cellType == null)
				{
					cellType = m_mapColCellTypes.get(cellID.Row);
				}

				if (cellType == null)
				{
					cell = m_Storage.createCell(cellID);
				}
				else
				{
					try
					{
						cell = (GridCell) cellType.newInstance();

						m_Storage.putCell(cellID, cell);
					}
					catch (Exception e)
					{
						cell = null;
					}
				}
			}
		}
		
		return cell;
	}	

	/**
	 * Get a grid cell object (creates one if it큦 not already created)
	 */
	public GridCell getCell(int iRow, int iCol)
	{
		return getCell(new GridCellID(iRow, iCol));
	}
	
	/**
	 * Set a grid cell object
	 */
	public void setCell(GridCellID cellID, GridCell cell)
	{
		m_Storage.putCell(cellID, cell);
		
		_repaintGridIfNeeded();
	}
	
	/**
	 * Set a grid cell object
	 */
	public void setCell(int iRow, int iCol, GridCell cell)
	{
		setCell(new GridCellID(iRow, iCol), cell);
	}
	
	/**
	 * Clear a grid cell object
	 */
	public boolean clearCell(GridCellID cellID)
	{
		if (!m_Storage.eraseCell(cellID))
		{
			return false;
		}
		
		_repaintGridIfNeeded();
		
		return true;
	}	

	/**
	 * Clear a grid cell object
	 */
	public void clearCell(int iRow, int iCol)
	{
		clearCell(new GridCellID(iRow, iCol));
	}
	
	/**
	 * Get the grid cell text
	 */
	public String getCellText(GridCellID cellID)
	{
		try
		{
			return m_Storage.getCell(cellID).m_strCellText;
		}
		catch (Exception e)
		{
			return "";
		}
	}

	/**
	 * Get the grid cell text
	 */
	public String getCellText(int iRow, int iCol)
	{
		return getCellText(new GridCellID(iRow, iCol));
	}
	
	/**
	 * Set the grid cell text
	 */
	public void setCellText(GridCellID cellID, String strCellText)
	{
		try
		{
			getCell(cellID).m_strCellText = strCellText;

			_repaintGridIfNeeded();
		}
		catch (Exception e)
		{
		}
	}
	
	/**
	 * Set the grid cell text
	 */
	public void setCellText(int iRow, int iCol, String strCellText)
	{
		setCellText(new GridCellID(iRow, iCol), strCellText);
	}
	
	/**
	 * Clear the grid cell text
	 */
	public void clearCellText(GridCellID cellID)
	{
		try
		{
			m_Storage.getCell(cellID).m_strCellText = "";
		
			_repaintGridIfNeeded();
		}
		catch (Exception e)
		{
		}
	}
	
	/**
	 * Clear the grid cell text
	 */
	public void clearCellText(int iRow, int iCol)
	{
		clearCellText(new GridCellID(iRow, iCol));
	}	

	/**
	 * Get the grid cell Alignment
	 */
	public int getCellAlignment(GridCellID cellID)
	{
		try
		{
			return m_Storage.getCell(cellID).m_iCellAlignment;
		}
		catch (Exception e)
		{
			return GridCellAttributes.ALIGN_DEFAULT;
		}
	}

	/**
	 * Get the grid cell Alignment
	 */
	public int getCellAlignment(int iRow, int iCol)
	{
		return getCellAlignment(new GridCellID(iRow, iCol));
	}
	
	/**
	 * Set the grid cell Alignment
	 */
	public void setCellAlignment(GridCellID cellID, int iCellAlignment)
	{
		try
		{
			getCell(cellID).m_iCellAlignment = iCellAlignment;

			_repaintGridIfNeeded();
		}
		catch (Exception e)
		{
		}
	}
	
	/**
	 * Set the grid cell Alignment
	 */
	public void setCellAlignment(int iRow, int iCol, int iCellAlignment)
	{
		setCellAlignment(new GridCellID(iRow, iCol), iCellAlignment);
	}
	
	/**
	 * Clear the grid cell Alignment
	 */
	public void clearCellAlignment(GridCellID cellID)
	{
		try
		{
			m_Storage.getCell(cellID).m_iCellAlignment = GridCellAttributes.ALIGN_DEFAULT;

			_repaintGridIfNeeded();
		}
		catch (Exception e)
		{
		}
	}
	
	/**
	 * Clear the grid cell Alignment
	 */
	public void clearCellAlignment(int iRow, int iCol)
	{
		clearCellAlignment(new GridCellID(iRow, iCol));
	}	
	
	/**
	 * Get the grid cell background color
	 */
	public Color getCellBackColor(GridCellID cellID)
	{
		try
		{
			return m_Storage.getCell(cellID).m_clrCellBackColor;
		}
		catch (Exception e)
		{
			return null;
		}
	}

	/**
	 * Get the grid cell background color
	 */
	public Color getCellBackColor(int iRow, int iCol)
	{
		return getCellBackColor(new GridCellID(iRow, iCol));
	}
	
	/**
	 * Set the grid cell background color
	 */
	public void setCellBackColor(GridCellID cellID, Color clrCellBackColor)
	{
		try
		{
			getCell(cellID).m_clrCellBackColor = clrCellBackColor;

			_repaintGridIfNeeded();
		}
		catch (Exception e)
		{
		}
	}
	
	/**
	 * Set the grid cell background color
	 */
	public void setCellBackColor(int iRow, int iCol, Color clrCellBackColor)
	{
		setCellBackColor(new GridCellID(iRow, iCol), clrCellBackColor);
	}	

	/**
	 * Clear the grid cell background color
	 */
	public void clearCellBackColor(GridCellID cellID)
	{
		try
		{
			m_Storage.getCell(cellID).m_clrCellBackColor = null;

			_repaintGridIfNeeded();
		}
		catch (Exception e)
		{
		}
	}
	
	/**
	 * Clear the grid cell background color
	 */
	public void clearCellBackColor(int iRow, int iCol)
	{
		clearCellBackColor(new GridCellID(iRow, iCol));
	}	
	
	/**
	 * Get the grid cell foreground color
	 */
	public Color getCellForeColor(GridCellID cellID)
	{
		try
		{
			return m_Storage.getCell(cellID).m_clrCellForeColor;
		}
		catch (Exception e)
		{
			return null;
		}
	}
	
	/**
	 * Get the grid cell foreground color
	 */
	public Color getCellForeColor(int iRow, int iCol)
	{
		return getCellForeColor(new GridCellID(iRow, iCol));
	}
	
	/**
	 * Set the grid cell foreground color
	 */
	public void setCellForeColor(GridCellID cellID, Color clrCellForeColor)
	{
		try
		{
			getCell(cellID).m_clrCellForeColor = clrCellForeColor;

			_repaintGridIfNeeded();
		}
		catch (Exception e)
		{
		}
	}
	
	/**
	 * Set the grid cell foreground color
	 */
	public void setCellForeColor(int iRow, int iCol, Color clrCellForeColor)
	{
		setCellForeColor(new GridCellID(iRow, iCol), clrCellForeColor);
	}	
	
	/**
	 * Clear the grid cell foreground color
	 */
	public void clearCellForeColor(GridCellID cellID)
	{
		try
		{
			m_Storage.getCell(cellID).m_clrCellForeColor = null;
		
			_repaintGridIfNeeded();
		}
		catch (Exception e)
		{
		}
	}	
	
	/**
	 * Clear the grid cell foreground color
	 */
	public void clearCellForeColor(int iRow, int iCol)
	{
		clearCellForeColor(new GridCellID(iRow, iCol));
	}	
	
	/**
	 * Get the grid cell font
	 */
	public Font getCellFont(GridCellID cellID)
	{
		try
		{
			return m_Storage.getCell(cellID).m_CellFont;
		}
		catch (Exception e)
		{
			return null;
		}
	}
	
	/**
	 * Get the grid cell font
	 */
	public Font getCellFont(int iRow, int iCol)
	{
		return getCellFont(new GridCellID(iRow, iCol));
	}
	
	/**
	 * Set the grid cell font
	 */
	public void setCellFont(GridCellID cellID, Font cellFont)
	{
		try
		{
			getCell(cellID).m_CellFont = cellFont;

			_repaintGridIfNeeded();
		}
		catch (Exception e)
		{			
		}
	}
	
	/**
	 * Set the grid cell font
	 */
	public void setCellFont(int iRow, int iCol, Font cellFont)
	{
		setCellFont(new GridCellID(iRow, iCol), cellFont);
	}	
	
	/**
	 * Clear the grid cell font
	 */
	public void clearCellFont(GridCellID cellID)
	{
		try
		{
			m_Storage.getCell(cellID).m_CellFont = null;
		
			_repaintGridIfNeeded();
		}
		catch (Exception e)
		{
		}
	}
	
	/**
	 * Clear the grid cell font
	 */
	public void clearCellFont(int iRow, int iCol)
	{
		clearCellFont(new GridCellID(iRow, iCol));
	}
	
	/**
	 * Get current selected cell ID
	 */
	public GridCellID getUserSelectedCellID()
	{
		return m_userSelectedCellID;
	}
	
	/**
	 * Set current selected cell ID
	 */
	public void setUserSelectedCellID(GridCellID cellID)
	{
		int iStorageRows = m_Storage.getRowCount();
		int iStorageCols = m_Storage.getColCount();
		
		if (!cellID.isValid(iStorageRows, iStorageCols))
		{
			return;
		}
		
		boolean bUpdating = m_bUpdating;
		
		m_bUpdating = false;

		setUserSelectedCol(cellID.Col);
		setUserSelectedCol(cellID.Row);
		
		m_bUpdating = bUpdating;
		
		_repaintGridIfNeeded();
	}
	
	/**
	 * Get current selected row
	 */
	public int getUserSelectedRow()
	{
		return m_userSelectedCellID.Row;
	}
	
	/**
	 * Set current selected row
	 */
	public void setUserSelectedRow(int iSelectedRow)
	{
		int iStorageRows = m_Storage.getRowCount();

		if ((iSelectedRow < m_iFixedRowCount) || (iSelectedRow >= iStorageRows))
		{
			m_userSelectedCellID.Row = -1;
			m_userSelectedCellID.Col = -1;
		}
		else
		{
			m_userSelectedCellID.Row = iSelectedRow;
			
			if (m_userSelectedCellID.Col == -1)
			{
				m_userSelectedCellID.Col = m_iFixedColCount;
			}
		}

		GridEvent evt = new GridEvent();
		
		evt.target = this;
		evt.type = GridEvent.SELECTION_CHANGED;
		evt.cellID = new GridCellID(m_userSelectedCellID.Row, m_userSelectedCellID.Col);
		
		postEvent(evt);

		_repaintGridIfNeeded();
	}
	
	/**
	 * Get current selected column
	 */
	public int getUserSelectedCol()
	{
		return m_userSelectedCellID.Col;
	}
	
	/**
	 * Set current selected column
	 */
	public void setUserSelectedCol(int iSelectedCol)
	{
		int iStorageCols = m_Storage.getColCount();
		
		if ((iSelectedCol < m_iFixedColCount) || (iSelectedCol >= iStorageCols))
		{
			m_userSelectedCellID.Col = -1;
			m_userSelectedCellID.Row = -1;
		}
		else
		{
			m_userSelectedCellID.Col = iSelectedCol;
			
			if (m_userSelectedCellID.Row == -1)
			{
				m_userSelectedCellID.Row = m_iFixedRowCount;
			}
		}
		
		GridEvent evt = new GridEvent();
		
		evt.target = this;
		evt.type = GridEvent.SELECTION_CHANGED;
		evt.cellID = new GridCellID(m_userSelectedCellID.Row, m_userSelectedCellID.Col);
		
		postEvent(evt);

		_repaintGridIfNeeded();
	}
	
	/**
	 * Remove rows from grid
	 */
	public void removeRows(int iRowStart, int iRowCount)
	{
		if (m_Storage.isReadOnly())
		{
			return;
		}

		int iStorageRows = m_Storage.getRowCount();
		
		if ((iRowStart < 0) || (iRowStart > (iStorageRows - 1)) || (iRowCount < 1))
		{
			return;
		}
		
		if ((iStorageRows - iRowCount) < 1)
		{
			clearGrid();
			
			return;
		}
		
		int iRowStep;
		int iRowEnd;
		int iHeight;
		int i;
		
		GridCellAttributes rowAttr;
		
		boolean bUpdating = m_bUpdating;

		m_bUpdating = false;
		
		iRowEnd = (iRowStart + iRowCount) - 1;
		
		if (iRowEnd >= (iStorageRows - 1))
		{
			iRowEnd = iStorageRows - 1;
			iRowStep = (iRowEnd - iRowStart) + 1;
			
			if (iRowStep < 0)
			{
				iRowStep = 0;
			}
		}
		else
		{
			iRowStep = iRowCount;
		}

		for (i = iRowStart; i < iStorageRows; i++)
		{
			iHeight = m_mapRowHeights.remove(i + iRowStep);
			
			if (iHeight == IntHashtable.INVALID)
			{
				m_mapRowHeights.remove(i);
			}
			else
			{
				m_mapRowHeights.put(i, iHeight);
			}
		}

		for (i = iRowStart; i < iStorageRows; i++)
		{
			rowAttr = m_mapRowAttributes.remove(i + iRowStep);
			
			if (rowAttr == null)
			{
				m_mapRowAttributes.remove(i);
			}
			else
			{
				m_mapRowAttributes.put(i, rowAttr);
			}
		}
		
		if (iRowEnd == iStorageRows - 1)
		{
			setRowCount(iRowStart);
		}
		else
		{
			setRowCount(m_Storage.removeRows(iRowStart, iRowEnd));
		}
		
		if ((iRowStart <= m_userSelectedCellID.Row) && (m_userSelectedCellID.Row <= iRowEnd))
		{
			m_userSelectedCellID.Row = -1;
		}
		
		m_bUpdating = bUpdating;
		
		_repaintGridIfNeeded();
	}

	/**
	 * Remove a row from grid
	 */
	public void removeRow(int iRow)
	{	
		removeRows(iRow, 1);
	}
	
	/**
	 * Remove columns from grid
	 */
	public void removeCols(int iColStart, int iColCount)
	{
		if (m_Storage.isReadOnly())
		{
			return;
		}

		int iStorageCols = m_Storage.getColCount();
		
		if ((iColStart < 0) || (iColStart > (iStorageCols - 1)) || (iColCount < 1))
		{
			return;
		}
		
		if ((iStorageCols - iColCount) < 1)
		{
			clearGrid();
			
			return;
		}
		
		int iColStep;
		int iColEnd;
		int iHeight;
		int i;
		
		GridCellAttributes colAttr;
		
		Class cellType;
		
		boolean bUpdating = m_bUpdating;

		m_bUpdating = false;
		
		iColEnd = (iColStart + iColCount) - 1;
		
		if (iColEnd >= (iStorageCols - 1))
		{
			iColEnd = iStorageCols - 1;
			iColStep = (iColEnd - iColStart) + 1;
			
			if (iColStep < 0)
			{
				iColStep = 0;
			}
		}
		else
		{
			iColStep = iColCount;
		}

		for (i = iColStart; i < iStorageCols; i++)
		{
			iHeight = m_mapColWidths.remove(i + iColStep);
			
			if (iHeight == IntHashtable.INVALID)
			{
				m_mapColWidths.remove(i);
			}
			else
			{
				m_mapColWidths.put(i, iHeight);
			}
		}

		for (i = iColStart; i < iStorageCols; i++)
		{
			colAttr = m_mapColAttributes.remove(i + iColStep);
			
			if (colAttr == null)
			{
				m_mapColAttributes.remove(i);
			}
			else
			{
				m_mapColAttributes.put(i, colAttr);
			}
		}

		for (i = iColStart; i < iStorageCols; i++)
		{
			cellType = m_mapColCellTypes.remove(i + iColStep);
			
			if (cellType == null)
			{
				m_mapColCellTypes.remove(i);
			}
			else
			{
				m_mapColCellTypes.put(i, cellType);
			}
		}
		
		if (iColEnd == iStorageCols - 1)
		{
			setColCount(iColStart);
		}
		else
		{
			setColCount(m_Storage.removeCols(iColStart, iColEnd));
		}
		
		if ((iColStart <= m_userSelectedCellID.Col) && (m_userSelectedCellID.Col <= iColEnd))
		{
			m_userSelectedCellID.Col = -1;
		}
		
		m_bUpdating = bUpdating;
		
		_repaintGridIfNeeded();
	}
	
	/**
	 * Remove a column from grid
	 */
	public void removeCol(int iCol)
	{
		removeCols(iCol, 1);
	}
	
	/**
	 * Scroll the grid vertically
	 */
	public void scrollVertical(int iDelta)
	{
		int iStorageRows = m_Storage.getRowCount();

		if ((iDelta == 0) || (iStorageRows == 0))
		{
			return;
		}
		
		int iNewRow = m_userSelectedCellID.Row + iDelta;
		
		if (iNewRow < m_iFixedRowCount)
		{
			iNewRow = m_iFixedRowCount;
		}
		else if (iNewRow > (iStorageRows - 1))
		{
			iNewRow = iStorageRows - 1;
		}
		
		if (m_userSelectedCellID.Row == iNewRow)
		{
			return;
		}
		
		m_userSelectedCellID.Row = iNewRow;
		
		if (m_userSelectedCellID.Row < m_iTopRow)
		{
			m_iTopRow = m_userSelectedCellID.Row;
		}
		else if (m_userSelectedCellID.Row >= m_iBottomRow)
		{
			m_iTopRow += ((m_userSelectedCellID.Row + (m_bBottomRowFullDraw ? 0 : 1)) - m_iBottomRow);
		}
		
		if (m_userSelectedCellID.Col == -1)
		{
			m_userSelectedCellID.Col = m_iFixedColCount;
		}
		
		m_VertScroll.setValue(m_iTopRow - m_iFixedRowCount);
		
		GridEvent evt = new GridEvent();
		
		evt.target = this;
		evt.type = GridEvent.SELECTION_CHANGED;
		evt.cellID = new GridCellID(m_userSelectedCellID.Row, m_userSelectedCellID.Col);
		
		postEvent(evt);
		
		_repaintGridIfNeeded();
	}

	/**
	 * Scroll the grid horizontally
	 */
	public void scrollHorizontal(int iDelta)
	{
		int iStorageCols = m_Storage.getColCount();
		
		if ((iDelta == 0) || (iStorageCols == 0))
		{
			return;
		}

		int iNewCol = m_userSelectedCellID.Col + iDelta;
		
		if (iNewCol < m_iFixedColCount)
		{
			iNewCol = m_iFixedColCount;
		}
		else if (iNewCol > (iStorageCols - 1))
		{
			iNewCol = iStorageCols - 1;
		}

		if (m_userSelectedCellID.Col == iNewCol)
		{
			return;
		}
		
		m_userSelectedCellID.Col = iNewCol;
		
		if (m_userSelectedCellID.Col < m_iLeftCol)
		{
			m_iLeftCol = m_userSelectedCellID.Col;
		}
		else if (m_userSelectedCellID.Col >= m_iRightCol)
		{
			m_iLeftCol += ((m_userSelectedCellID.Col + (m_bRightColFullDraw ? 0 : 1)) - m_iRightCol);
		}
		
		if (m_userSelectedCellID.Row == -1)
		{
			m_userSelectedCellID.Row = m_iFixedRowCount;
		}
		
		m_HorzScroll.setValue(m_iLeftCol - m_iFixedColCount);
		
		GridEvent evt = new GridEvent();
		
		evt.target = this;
		evt.type = GridEvent.SELECTION_CHANGED;
		evt.cellID = new GridCellID(m_userSelectedCellID.Row, m_userSelectedCellID.Col);
		
		postEvent(evt);
		
		_repaintGridIfNeeded();
	}
	
	/**
	 * Scroll the grid up one row
	 */
	public void scrollUp()
	{
		scrollVertical(-1);
	}
	
	/**
	 * Scroll the grid down one row
	 */
	public void scrollDown()
	{
		int iStorageRows = m_Storage.getRowCount();
		
		if ((m_userSelectedCellID.Row == (iStorageRows - 1)) && !m_bBottomRowFullDraw)
		{
			m_iTopRow++;
			
			_repaintGridIfNeeded();
		}
		else
		{
			scrollVertical(1);
		}
	}
	   
	/**
	 * Scroll the grid left one column
	 */
	public void scrollLeft()
	{
		scrollHorizontal(-1);
	}
	
	/**
	 * Scroll the grid right one column
	 */
	public void scrollRight()
	{
		int iStorageCols = m_Storage.getColCount();
		
		if ((m_userSelectedCellID.Col == (iStorageCols - 1)) && !m_bRightColFullDraw)
		{
			m_iLeftCol++;
			
			_repaintGridIfNeeded();
		}
		else
		{
			scrollHorizontal(1);
		}
	}

	/**
	 * Scroll the grid up N rows
	 */
	public void scrollUpLines(int iLinesCount)
	{
		scrollVertical(0 - iLinesCount);
	}

	/**
	 * Scroll the grid down N rows
	 */
	public void scrollDownLines(int iLinesCount)
	{
		int iStorageRows = m_Storage.getRowCount();
		
		if ((m_userSelectedCellID.Row == (iStorageRows - 1)) && !m_bBottomRowFullDraw)
		{
			m_iTopRow++;
			
			_repaintGridIfNeeded();
		}
		else
		{
			scrollVertical(iLinesCount);
		}
	}

	/**
	 * Scroll the grid left N columns
	 */
	public void scrollLeftCols(int iColumnsCount)
	{
		scrollHorizontal(0 - iColumnsCount);
	}
	
	/**
	 * Scroll the grid right N columns
	 */
	public void scrollRightCols(int iColumnsCount)
	{
		int iStorageCols = m_Storage.getColCount();
		
		if ((m_userSelectedCellID.Col == (iStorageCols - 1)) && !m_bRightColFullDraw)
		{
			m_iLeftCol++;
			
			_repaintGridIfNeeded();
		}
		else
		{
			scrollHorizontal(iColumnsCount);
		}
	}
	
	/**
	 * Scroll to the first grid row 
	 */
	public void toUp()
	{
		m_userSelectedCellID.Row = 0;
		
		if (m_userSelectedCellID.Col == -1)
		{
			m_userSelectedCellID.Col = m_iFixedColCount;
		}
		
		setTopRow(0);
   }
	
	/**
	 * Scroll to the last grid row 
	 */
	public void toDown()
	{
		int iStorageRows = m_Storage.getRowCount();

		m_userSelectedCellID.Row = iStorageRows - 1;
		
		if (m_userSelectedCellID.Col == -1)
		{
			m_userSelectedCellID.Col = m_iFixedColCount;
		}
		
		setTopRow(iStorageRows - 1);
	}
	
	/**
	 * Scroll to the first grid column 
	 */
	public void toLeft()
	{
		m_userSelectedCellID.Col = 0;
		
		if (m_userSelectedCellID.Row == -1)
		{
			m_userSelectedCellID.Row = m_iFixedRowCount;
		}
		
		setLeftCol(0);
	}
	
	/**
	 * Scroll to the last grid column 
	 */
	public void toRight()
	{
		int iStorageCols = m_Storage.getColCount();
		
		m_userSelectedCellID.Col = iStorageCols - 1;
		
		if (m_userSelectedCellID.Row == -1)
		{
			m_userSelectedCellID.Row = m_iFixedRowCount;
		}
		
		setLeftCol(iStorageCols - 1);
	}
	
	/** 
	 * Ensure that current selected cell is visible 
	 */
	public void ensureVisibleSelection()
	{
		boolean bChanged = false;
		boolean bUpdating = m_bUpdating;

		m_bUpdating = false;
				
		if (m_userSelectedCellID.Col != -1) 
		{
			if ((m_userSelectedCellID.Col < m_iLeftCol) || ((m_userSelectedCellID.Col > m_iRightCol) 
				|| ((m_userSelectedCellID.Col == m_iRightCol) && !m_bRightColFullDraw)))
			{
				m_iLeftCol = m_userSelectedCellID.Col;
				m_HorzScroll.setValue(m_iLeftCol - m_iFixedColCount);
				
				bChanged = true;
			}
		}
		
		if (m_userSelectedCellID.Row != -1) 
		{
			if ((m_userSelectedCellID.Row < m_iTopRow) || ((m_userSelectedCellID.Row > m_iBottomRow) 
				|| ((m_userSelectedCellID.Row == m_iBottomRow) && !m_bBottomRowFullDraw)))
			{
				m_iTopRow = m_userSelectedCellID.Row;
				m_VertScroll.setValue(m_iTopRow - m_iFixedRowCount);
				
				bChanged = true;
			}
		}
		
		m_bUpdating = bUpdating;
		
		if (bChanged)
		{
			_repaintGridIfNeeded();
		}
	}
	
	/**
	 * Stop editing grid cell contents if active
	 */
	public void endCellEditing()
	{
		if (m_cellEditing != null)
		{
			GridCellID cellID = new GridCellID(
					m_cellEditing.m_Editor.m_EditCellID.Row, 
					m_cellEditing.m_Editor.m_EditCellID.Col);
			
			if (m_cellEditing.onEndEdit())
			{
				m_cellEditing = null;

				GridEvent evt = new GridEvent();
				
				evt.target = this;
				evt.type = GridEvent.END_EDIT_CELL;
				evt.cellID = cellID;
				
				postEvent(evt);
			}
		}
	}
	
	/**
	 * Append a new row and set the string array elements into new row cell큦
	 * (NOTE that if the cell object doesn't exists previously, it will 
	 *  create it as a GridCell instance, which is readonly)
	 */
	public void appendRowStrings(String [] strCellsText)
	{
		boolean bUpdating = m_bUpdating;

		m_bUpdating = false;
		
		int iRowCount = getRowCount();
		
		setRowCount(iRowCount + 1);
		
		m_Storage.setRowStrings(iRowCount, strCellsText);
		
		m_bUpdating = bUpdating;
		
		if (bUpdating)
		{
			_repaintGridIfNeeded();
		}
	}
	
	/**
	 * Set the string array elements into specified row cell큦
	 * (NOTE that if the cell object doesn't exists previously, it will 
	 *  create it as a GridCell instance, which is readonly)
	 */
	public void setRowStrings(int iRow, String [] strCellsText)
	{
		boolean bUpdating = m_bUpdating;

		m_bUpdating = false;
		
		m_Storage.setRowStrings(iRow, strCellsText);
		
		m_bUpdating = bUpdating;
		
		if (bUpdating)
		{
			_repaintGridIfNeeded();
		}
	}

	/**
	 * Get the string array elements from specified row cell큦
	 */
	public String [] getRowStrings(int iRow)
	{
		return m_Storage.getRowStrings(iRow);
	}
	
	/**
	 * Append a new column and set the string array elements into new column cell큦
	 * (NOTE that if the cell object doesn't exists previously, it will 
	 *  create it as a GridCell instance, which is readonly)
	 */
	public void appendColStrings(String [] strCellsText)
	{
		boolean bUpdating = m_bUpdating;

		m_bUpdating = false;
		
		int iColCount = getColCount();
		
		setColCount(iColCount + 1);
		
		m_Storage.setColStrings(iColCount, strCellsText);
		
		m_bUpdating = bUpdating;
		
		if (bUpdating)
		{
			_repaintGridIfNeeded();
		}
	}
	
	/**
	 * Set the string array elements into specified column cell큦
	 * (NOTE that if the cell object doesn't exists previously, it will 
	 *  create it as a GridCell instance, which is readonly)
	 */
	public void setColStrings(int iCol, String [] strCellsText)
	{
		boolean bUpdating = m_bUpdating;

		m_bUpdating = false;
		
		m_Storage.setColStrings(iCol, strCellsText);
		
		m_bUpdating = bUpdating;
		
		if (bUpdating)
		{
			_repaintGridIfNeeded();
		}
	}

	/**
	 * Get the string array elements from specified column cell큦
	 */
	public String [] getColStrings(int iCol)
	{
		return m_Storage.getColStrings(iCol);
	}
	
	/**
	 * Sort grid rows by a choosen column
	 */
	public boolean sortGridRows(int iCol, boolean bDescendent, boolean bSortFixedRows)
	{
		return sortGridRowsEx(iCol, -1, 0x7ffffffe, bDescendent, bSortFixedRows);
	}

	/**
	 * Sort grid rows by a choosen column in the given row start/end range
	 */
	public boolean sortGridRowsEx(int iCol, int iRowStart, int iRowEnd, boolean bDescendent, boolean bSortFixedRows)
	{
		if ((iCol < 0) || (iCol >= getColCount()))
		{
			return false;
		}
		
		int iLoop;
		int iRowCount = Math.min(iRowEnd + 1, getRowCount());
		int iStart = Math.max(iRowStart, (bSortFixedRows ? 0 : getFixedRowCount()));
		int iCount = iRowCount - iStart;
		GridSorter sorter;
		GridSorter [] sortInfo = new GridSorter [iCount];
		GridCellHashtable cellHT = m_Storage.getColCells(iCol);
		GridCellHashtable [] cellsOrdered = new GridCellHashtable [iCount];
		
		for (iLoop = iStart; iLoop < iRowCount; iLoop++)
		{
			sorter = new GridSorter();
			sorter.index = iLoop;
			sorter.cell = cellHT.get(iLoop);

			sortInfo[iLoop - iStart] = sorter; 
		}
		
		GridSorter.QuickSort(sortInfo, 0, iRowCount - iStart - 1);

		int iRow;
		
		for (iLoop = 0; iLoop < iCount; iLoop++)
		{
			iRow = sortInfo[iLoop].index;
			
			cellsOrdered[iLoop] = m_Storage.getRowCells(iRow);
			m_Storage.eraseRowCells(iRow);
		}
		
		if (bDescendent)
		{
			iRow = iStart;
			
			for (iLoop = 0; iLoop < iCount; iLoop++, iRow++)
			{
				m_Storage.putRowCells(iRow, cellsOrdered[iLoop]);
			}
		}
		else
		{
			iRow = iRowCount - 1;
			
			for (iLoop = 0; iLoop < iCount; iLoop++, iRow--)
			{
				m_Storage.putRowCells(iRow, cellsOrdered[iLoop]);
			}
		}

		_repaintGridIfNeeded();
		
		return true;
	}
	
	/**
	 * Sort grid columns by a choosen row
	 */
	public boolean sortGridCols(int iCol, boolean bDescendent, boolean bSortFixedCols)
	{
		return sortGridColsEx(iCol, -1, 0x7ffffffe, bDescendent, bSortFixedCols);
	}

	/**
	 * Sort grid columns by a choosen row in the given column start/end range
	 */
	public boolean sortGridColsEx(int iRow, int iColStart, int iColEnd, boolean bDescendent, boolean bSortFixedCols)
	{
		if ((iRow < 0) || (iRow >= getRowCount()))
		{
			return false;
		}
		
		int iLoop;
		int iColCount = Math.min(iColEnd + 1, getColCount());
		int iStart = Math.max(iColStart, (bSortFixedCols ? 0 : getFixedColCount()));
		int iCount = iColCount - iStart;
		GridSorter sorter;
		GridSorter [] sortInfo = new GridSorter [iCount];
		GridCellHashtable cellHT = m_Storage.getRowCells(iRow);
		GridCellHashtable [] cellsOrdered = new GridCellHashtable [iCount];
		
		for (iLoop = iStart; iLoop < iColCount; iLoop++)
		{
			sorter = new GridSorter();
			sorter.index = iLoop;
			sorter.cell = cellHT.get(iLoop);

			sortInfo[iLoop - iStart] = sorter; 
		}
		
		GridSorter.QuickSort(sortInfo, 0, iColCount - iStart - 1);

		int iCol;
		
		for (iLoop = 0; iLoop < iCount; iLoop++)
		{
			iCol = sortInfo[iLoop].index;
			
			cellsOrdered[iLoop] = m_Storage.getColCells(iCol);
			m_Storage.eraseColCells(iCol);
		}
		
		if (bDescendent)
		{
			iCol = iStart;
			
			for (iLoop = 0; iLoop < iCount; iLoop++, iCol++)
			{
				m_Storage.putColCells(iCol, cellsOrdered[iLoop]);
			}
		}
		else
		{
			iCol = iColCount - 1;
			
			for (iLoop = 0; iLoop < iCount; iLoop++, iCol--)
			{
				m_Storage.putColCells(iCol, cellsOrdered[iLoop]);
			}
		}

		_repaintGridIfNeeded();
		
		return true;
	}
	
	private Rect _getCellDrawRect()
	{
		Rect rc = getRect();

		rc.x = 1;
		rc.y = 1;
		rc.width -= (2 + (m_VertScroll.isVisible() ? m_iVerticalScrollWidth : 0));
		rc.height -= (2 + (m_HorzScroll.isVisible() ? m_iHorizontalScrollHeight : 0));
		
		return rc;
	}
	
	private void _repaintGridIfNeeded()
	{
		if (isVisible() && m_bUpdating)
		{
			repaint();
		}
	}

	public void onPaint(Graphics g)
	{
		m_iRightCol = -1;
		m_iBottomRow = -1;
		
		m_bBottomRowFullDraw = true;
		m_bRightColFullDraw = true;
		
		if (!m_bUpdating)
		{
			return;
		}
		
		if (m_grphGrid == null)
		{
		    m_grphGrid = createGraphics();
		}
				
		Rect rc = _getCellDrawRect();

		if ((rc.width < 1) || (rc.height < 1))
		{
			return;
		}
		
		int iStorageRows = m_Storage.getRowCount();
		int iStorageCols = m_Storage.getColCount();
		
		boolean bExceededHeight = true;
		boolean bExceededWidth = true;
		
		if ((iStorageRows > 0) && (iStorageCols > 0))
		{
			GridCellID cellID = new GridCellID();
			GridCellDrawParams params = new GridCellDrawParams();
			GridCell cell;
			GridCellAttributes rowAttr;
			GridCellAttributes colAttr;
			GridCellAttributes cellAttr;		
			
			boolean bFixedRowExceededHeight = false;
			boolean bFixedColExceededWidth = false;
			
			int iRowLoop;
			int iColLoop;
			
			int iRightX; 
			int iBottomY;
			
			int iResizeAux;
			
			int iMaxRightColPoints;
			int iMaxBottomRowPoints;

			params.rcClip.y = rc.y;
			params.rcClip.x = rc.x;
			
			iRightX = params.rcClip.x;
			iBottomY = params.rcClip.y;
			
			m_iLeftColX = params.rcClip.x;
			m_iTopRowY = params.rcClip.y;
			
			params.graphics = g;
			params.clrGridCellSeparatorColor = m_clrGridCellSeparatorColor;
			
			params.bIsFixed = m_bDraw3DFixedCells;
			
			params.clrCellBackColor = m_cellAttributesFixed.m_clrCellBackColor;
			params.clrCellForeColor = m_cellAttributesFixed.m_clrCellForeColor;
			params.font = m_cellAttributesFixed.m_CellFont;
			
			params.iRowSeparatorHeight = (m_bDrawRowSeparators ? m_iRowSeparatorHeight : 0);
			params.rcClip.y = rc.y;
	
			// bFixedRowExceededHeight = false;

			m_arrBottomRowPoints.clear();
			m_arrRightColPoints.clear();

			iMaxRightColPoints = 0;
			iMaxBottomRowPoints = 0;
			
			if ((m_iFixedRowCount > 0) && (m_iFixedColCount > 0))
			{
				for (iRowLoop = 0; (iRowLoop < m_iFixedRowCount) && !bFixedRowExceededHeight; iRowLoop++)
				{
					rowAttr = m_mapRowAttributes.get(iRowLoop);
						
					cellID.Row = iRowLoop;
					params.iCellHeight = getRowHeight(iRowLoop);
					
					params.iColSeparatorWidth = (m_bDrawColSeparators ? m_iColSeparatorWidth : 0);
					params.rcClip.x = rc.x;

					bFixedRowExceededHeight = ((params.rcClip.y + params.iCellHeight) > rc.height);
					
					if (bFixedRowExceededHeight)
					{
						params.rcClip.height = rc.height - params.rcClip.y;
						params.iRowSeparatorHeight = 0;
					}
					else
					{
						params.rcClip.height = params.iCellHeight;
						
						iResizeAux = params.rcClip.y + params.iCellHeight - 1;

						if (iMaxBottomRowPoints > iRowLoop)
						{
							m_arrBottomRowPoints.items[iRowLoop] = iResizeAux;
						}
						else
						{
							m_arrBottomRowPoints.insertAtGrow(iRowLoop, iResizeAux);
							iMaxBottomRowPoints = iRowLoop + 1;
						}
					}
					
					bFixedColExceededWidth = false;
					
					for (iColLoop = 0; (iColLoop < m_iFixedColCount) && !bFixedColExceededWidth; iColLoop++)
					{
						colAttr = m_mapColAttributes.get(iColLoop);
						
						cellID.Col = iColLoop;
						params.iCellWidth = getColWidth(iColLoop);
	
						cell = m_Storage.getCell(cellID);
						
						if (cell == null)
						{
							cell = m_cellAttributesFixed;
						}
						
						bFixedColExceededWidth = ((params.rcClip.x + params.iCellWidth) > rc.width);
						
						if (bFixedColExceededWidth)
						{
							params.rcClip.width = rc.width - params.rcClip.x;
							params.iColSeparatorWidth = 0;
						}
						else
						{
							params.rcClip.width = params.iCellWidth;

							iResizeAux = params.rcClip.x + params.iCellWidth - 1;
							
							if (iMaxRightColPoints > iColLoop)
							{
								m_arrRightColPoints.items[iColLoop] = iResizeAux;
							}
							else
							{
							    m_arrRightColPoints.insertAtGrow(iColLoop, iResizeAux);
								iMaxRightColPoints = iColLoop + 1;
							}
						}
						
						params.iCellAlignment = cell.m_iCellAlignment;
						
						if (rowAttr != null)
						{
							params.iCellAlignment = GridCellAttributes.handleAlign(params.iCellAlignment, rowAttr.m_iCellAlignment);
						}
						
						if (colAttr != null)
						{
							params.iCellAlignment = GridCellAttributes.handleAlign(params.iCellAlignment, colAttr.m_iCellAlignment);
						}
						
						params.iCellAlignment = GridCellAttributes.handleAlign(params.iCellAlignment, m_cellAttributesFixed.m_iCellAlignment);
		
						g.setClip(params.rcClip);
						cell.onPaint(params);
						
						if (!bFixedColExceededWidth)
						{
							params.rcClip.x += params.iCellWidth;
							m_iLeftColX = params.rcClip.x;
							
							if (iRightX < params.rcClip.x)
							{
								iRightX = params.rcClip.x;
							}
						}
					}
					
					if (!bFixedRowExceededHeight)
					{
						params.rcClip.y += params.iCellHeight;
						m_iTopRowY = params.rcClip.y;
						
						if (iBottomY < params.rcClip.y)
						{
							iBottomY = params.rcClip.y;
						}
					}
				}
			}
			
			if (!bFixedColExceededWidth && (m_iFixedRowCount > 0))
			{
				params.iRowSeparatorHeight = (m_bDrawRowSeparators ? m_iRowSeparatorHeight : 0);
				params.rcClip.y = rc.y;
				
				bExceededHeight = false;
				
				for (iRowLoop = 0; (iRowLoop < m_iFixedRowCount) && !bExceededHeight; iRowLoop++)
				{
					rowAttr = m_mapRowAttributes.get(iRowLoop);
					
					cellID.Row = iRowLoop;
					params.iCellHeight = getRowHeight(iRowLoop);
					
					params.iColSeparatorWidth = (m_bDrawColSeparators ? m_iColSeparatorWidth : 0);
					params.rcClip.x = m_iLeftColX;
					
					bExceededHeight = ((params.rcClip.y + params.iCellHeight) > rc.height);
					
					if (bExceededHeight)
					{
						params.rcClip.height = rc.height - params.rcClip.y;
						params.iRowSeparatorHeight = 0;
					}
					else
					{
						params.rcClip.height = params.iCellHeight;
						
						iResizeAux = params.rcClip.y + params.iCellHeight - 1;
					
						if (iMaxBottomRowPoints > iRowLoop)
						{
							m_arrBottomRowPoints.items[iRowLoop] = iResizeAux;
						}
						else
						{
							m_arrBottomRowPoints.insertAtGrow(iRowLoop, iResizeAux);
							iMaxBottomRowPoints = iRowLoop + 1;
						}
					}
					
					bExceededWidth = false;
					
					for (iColLoop = m_iLeftCol; (iColLoop < iStorageCols) && !bExceededWidth; iColLoop++)
					{
						colAttr = m_mapColAttributes.get(iColLoop);
						
						cellID.Col = iColLoop;
						params.iCellWidth = getColWidth(iColLoop);
						
						cell = m_Storage.getCell(cellID);
						
						if (cell == null)
						{
							cell = m_cellAttributesFixed;
						}
						
						bExceededWidth = ((params.rcClip.x + params.iCellWidth) > rc.width);
						
						if (bExceededWidth)
						{
							params.rcClip.width = rc.width - params.rcClip.x;
							params.iColSeparatorWidth = 0;
						}
						else
						{
							params.rcClip.width = params.iCellWidth;
							
							iResizeAux = params.rcClip.x + params.iCellWidth - 1;

							if (iMaxRightColPoints > iColLoop)
							{
								m_arrRightColPoints.items[iColLoop] = iResizeAux;
							}
							else
							{
							    m_arrRightColPoints.insertAtGrow(iColLoop, iResizeAux);
								iMaxRightColPoints = iColLoop + 1;
							}
						}
						
						params.iCellAlignment = cell.m_iCellAlignment;			
						
						if (rowAttr != null)
						{
							params.iCellAlignment = GridCellAttributes.handleAlign(params.iCellAlignment, rowAttr.m_iCellAlignment);
						}
						
						if (colAttr != null)
						{
							params.iCellAlignment = GridCellAttributes.handleAlign(params.iCellAlignment, colAttr.m_iCellAlignment);
						}
						
						params.iCellAlignment = GridCellAttributes.handleAlign(params.iCellAlignment, m_cellAttributesFixed.m_iCellAlignment);
						
						if (m_bAllowSortRows && (m_iLatestRowSortedCol == iColLoop))
						{
							if (Settings.isColor)
							{
								params.highlightColors(24);
							}

							params.iArrowDrawStyle = (m_bLatestRowSortOrder ? GridCellDrawParams.ARROW_DRAW_DOWN : GridCellDrawParams.ARROW_DRAW_UP);
						}
											
						g.setClip(params.rcClip);
						cell.onPaint(params);
						
						if (m_bAllowSortRows && (m_iLatestRowSortedCol == iColLoop))
						{
							if (Settings.isColor)
							{
								params.dehighlightColors();
							}

							params.iArrowDrawStyle = GridCellDrawParams.ARROW_DRAW_NONE;
						}
						
						if (!bExceededWidth)
						{
							params.rcClip.x += params.iCellWidth;
							
							if (iRightX < params.rcClip.x)
							{
								iRightX = params.rcClip.x;
							}
						}
					}
					
					if (!bExceededHeight)
					{
						params.rcClip.y += params.iCellHeight;
						m_iTopRowY = params.rcClip.y;
						
						if (iBottomY < params.rcClip.y)
						{
							iBottomY = params.rcClip.y;
						}
					}
				}
			}
			
			if (!bFixedRowExceededHeight && (m_iFixedColCount > 0))
			{
				params.iColSeparatorWidth = (m_bDrawColSeparators ? m_iColSeparatorWidth : 0);
				params.rcClip.x = rc.x;
				
				bExceededWidth = false;
				
				for (iColLoop = 0; (iColLoop < m_iFixedColCount) && !bExceededWidth; iColLoop++)
				{
					colAttr = m_mapColAttributes.get(iColLoop);
					
					cellID.Col = iColLoop;
					params.iCellWidth = getColWidth(iColLoop);
					
					params.iRowSeparatorHeight = (m_bDrawRowSeparators ? m_iRowSeparatorHeight : 0);
					params.rcClip.y = m_iTopRowY;
					
					bExceededWidth = ((params.rcClip.x + params.iCellWidth) > rc.width);
					
					if (bExceededWidth)
					{
						params.rcClip.width = rc.width - params.rcClip.x;
						params.iColSeparatorWidth = 0;
					}
					else
					{
						params.rcClip.width = params.iCellWidth;
						
						iResizeAux = params.rcClip.x + params.iCellWidth - 1;

						if (iMaxRightColPoints > iColLoop)
						{
							m_arrRightColPoints.items[iColLoop] = iResizeAux;
						}
						else
						{
						    m_arrRightColPoints.insertAtGrow(iColLoop, iResizeAux);
							iMaxRightColPoints = iColLoop + 1;
						}
					}
					
					bExceededHeight = false;
					
					for (iRowLoop = m_iTopRow; (iRowLoop < iStorageRows) && !bExceededHeight; iRowLoop++)
					{
						rowAttr = m_mapRowAttributes.get(iRowLoop);
						
						cellID.Row = iRowLoop;
						params.iCellHeight = getRowHeight(iRowLoop);
						
						cell = m_Storage.getCell(cellID);
						
						if (cell == null)
						{
							cell = m_cellAttributesFixed;
						}
						
						bExceededHeight = ((params.rcClip.y + params.iCellHeight) > rc.height);
						
						if (bExceededHeight)
						{
							params.rcClip.height = rc.height - params.rcClip.y;
							params.iRowSeparatorHeight = 0;
						}
						else
						{
							params.rcClip.height = params.iCellHeight;
							
							iResizeAux = params.rcClip.y + params.iCellHeight - 1;
							
							if (iMaxBottomRowPoints > iRowLoop)
							{
								m_arrBottomRowPoints.items[iRowLoop] = iResizeAux;
							}
							else
							{
								m_arrBottomRowPoints.insertAtGrow(iRowLoop, iResizeAux);
								iMaxBottomRowPoints = iRowLoop + 1;
							}
						}
						
						params.iCellAlignment = cell.m_iCellAlignment;			
						
						if (rowAttr != null)
						{
							params.iCellAlignment = GridCellAttributes.handleAlign(params.iCellAlignment, rowAttr.m_iCellAlignment);
						}
						
						if (colAttr != null)
						{
							params.iCellAlignment = GridCellAttributes.handleAlign(params.iCellAlignment, colAttr.m_iCellAlignment);
						}
						
						params.iCellAlignment = GridCellAttributes.handleAlign(params.iCellAlignment, m_cellAttributesFixed.m_iCellAlignment);
						
						if (m_bAllowSortCols && (m_iLatestColSortedRow == iRowLoop))
						{
							if (Settings.isColor)
							{
								params.highlightColors(24);
							}

							params.iArrowDrawStyle = (m_bLatestColSortOrder ? GridCellDrawParams.ARROW_DRAW_RIGHT : GridCellDrawParams.ARROW_DRAW_LEFT); 
						}
											
						g.setClip(params.rcClip);
						cell.onPaint(params);
						
						if (m_bAllowSortCols && (m_iLatestColSortedRow == iRowLoop))
						{
							if (Settings.isColor)
							{
								params.dehighlightColors();
							}

							params.iArrowDrawStyle = GridCellDrawParams.ARROW_DRAW_NONE;
						}
						
						if (!bExceededHeight)
						{
							params.rcClip.y += params.iCellHeight;
							
							if (iBottomY < params.rcClip.y)
							{
								iBottomY = params.rcClip.y;
							}
						}
					}
					
					if (!bExceededWidth)
					{
						params.rcClip.x += params.iCellWidth;
						m_iLeftColX = params.rcClip.x;
						
						if (iRightX < params.rcClip.x)
						{
							iRightX = params.rcClip.x;
						}
					}
				}
			}
			
			if (!bFixedColExceededWidth && !bFixedRowExceededHeight)
			{
				boolean bCellIsSelected;
				
				params.bIsFixed = false;
				
				params.iRowSeparatorHeight = (m_bDrawRowSeparators ? m_iRowSeparatorHeight : 0);
				params.rcClip.y = m_iTopRowY;
				
				bExceededHeight = false;
				
				for (iRowLoop = m_iTopRow; (iRowLoop < iStorageRows) && !bExceededHeight; iRowLoop++)
				{
					rowAttr = m_mapRowAttributes.get(iRowLoop);
					
					m_iBottomRow = iRowLoop;
					
					cellID.Row = iRowLoop;
					params.iCellHeight = getRowHeight(iRowLoop);
					
					params.iColSeparatorWidth = (m_bDrawColSeparators ? m_iColSeparatorWidth : 0);
					params.rcClip.x = m_iLeftColX;
					
					bExceededHeight = ((params.rcClip.y + params.iCellHeight) > rc.height);
					
					if (bExceededHeight)
					{
						params.rcClip.height = rc.height - params.rcClip.y;
						params.iRowSeparatorHeight = 0;
					}
					else
					{
						params.rcClip.height = params.iCellHeight;
					}
					
					bExceededWidth = false;
					
					for (iColLoop = m_iLeftCol; (iColLoop < iStorageCols) && !bExceededWidth; iColLoop++)
					{
						colAttr = m_mapColAttributes.get(iColLoop);
						
						m_iRightCol = iColLoop;
						
						cellID.Col = iColLoop;
						params.iCellWidth = getColWidth(iColLoop);
						
						cell = m_Storage.getCell(cellID);
						cellAttr = cell;
						
						if (cell == null)
						{
							cell = m_cellAttributesDefault;
						}
						
						bExceededWidth = ((params.rcClip.x + params.iCellWidth) > rc.width);
						
						if (bExceededWidth)
						{
							params.rcClip.width = rc.width - params.rcClip.x;
							params.iColSeparatorWidth = 0;
						}
						else
						{
							params.rcClip.width = params.iCellWidth;
						}
		
						bCellIsSelected =  ((!m_bFullRowSelectionDisplay && ((cellID.Row == m_userSelectedCellID.Row) && (cellID.Col == m_userSelectedCellID.Col)))
								|| (m_bFullRowSelectionDisplay && (cellID.Row == m_userSelectedCellID.Row)));
						
						if (bCellIsSelected)
						{
							params.font = m_cellAttributesSelected.m_CellFont;
							params.clrCellBackColor = m_cellAttributesSelected.m_clrCellBackColor;
							params.clrCellForeColor = m_cellAttributesSelected.m_clrCellForeColor;
							params.iCellAlignment = GridCellAttributes.handleAlign(m_cellAttributesSelected.m_iCellAlignment, cell.m_iCellAlignment);

							if (cellAttr != null)
							{
								if (params.font == null)
								{
									params.font = cellAttr.m_CellFont;
								}
			
								if (params.clrCellBackColor == null)
								{
									params.clrCellBackColor = cellAttr.m_clrCellBackColor;
								}
			
								if (params.clrCellForeColor == null)
								{
									params.clrCellForeColor = cellAttr.m_clrCellForeColor;
								}
							}
						}
						else
						{
							if (cellAttr != null)
							{
								params.font = cellAttr.m_CellFont;
								
								if (m_bAlternatedRowColors)
								{
									GridCell cellAlternated = ((iRowLoop % 2) == 0 ? m_cellAttributesOdd : m_cellAttributesEven);
									
									params.clrCellBackColor = cellAttr.m_clrCellBackColor;
									
									if (params.clrCellBackColor == null)
									{
										if (rowAttr != null)
										{
											params.clrCellBackColor = rowAttr.m_clrCellBackColor;
										}
										
										if (params.clrCellBackColor == null)
										{
											if (colAttr != null)
											{
												params.clrCellBackColor = colAttr.m_clrCellBackColor;
											}
											
											if (params.clrCellBackColor == null)
											{
												params.clrCellBackColor = cellAlternated.m_clrCellBackColor;
											}
										}
									}
									
									params.clrCellForeColor = cellAttr.m_clrCellForeColor;
									
									if (params.clrCellForeColor == null)
									{
										if (rowAttr != null)
										{
											params.clrCellForeColor = rowAttr.m_clrCellForeColor;
										}
										
										if (params.clrCellForeColor == null)
										{
											if (colAttr != null)
											{
												params.clrCellForeColor = colAttr.m_clrCellForeColor;
											}
											
											if (params.clrCellForeColor == null)
											{
												params.clrCellForeColor = cellAlternated.m_clrCellForeColor;
											}
										}
									}
								}
								else
								{
									params.clrCellBackColor = cellAttr.m_clrCellBackColor;
									params.clrCellForeColor = cellAttr.m_clrCellForeColor;
								}
								
								params.iCellAlignment = cellAttr.m_iCellAlignment;
							}
							else
							{
								params.font = null;
								
								if (m_bAlternatedRowColors)
								{
									GridCell cellAlternated = ((iRowLoop % 2) == 0 ? m_cellAttributesOdd : m_cellAttributesEven);
									
									if (rowAttr != null)
									{
										params.clrCellBackColor = rowAttr.m_clrCellBackColor;
									}
									else
									{
										params.clrCellBackColor = null;
									}
									
									if (params.clrCellBackColor == null)
									{
										if (colAttr != null)
										{
											params.clrCellBackColor = colAttr.m_clrCellBackColor;
										}
										
										if (params.clrCellBackColor == null)
										{
											params.clrCellBackColor = cellAlternated.m_clrCellBackColor;
										}
									}
									
									if (rowAttr != null)
									{
										params.clrCellForeColor = rowAttr.m_clrCellForeColor;
									}
									else
									{
										params.clrCellForeColor = null;
									}
									
									if (params.clrCellForeColor == null)
									{
										if (colAttr != null)
										{
											params.clrCellForeColor = colAttr.m_clrCellForeColor;
										}
										
										if (params.clrCellForeColor == null)
										{
											params.clrCellForeColor = cellAlternated.m_clrCellForeColor;
										}
									}
								}
								else
								{
									params.clrCellBackColor = null;
									params.clrCellForeColor = null;
								}
								
								params.iCellAlignment = GridCellAttributes.ALIGN_DEFAULT;
							}
						}
						
						if (params.font == null)
						{
							if (rowAttr != null)
							{
								params.font = rowAttr.m_CellFont;
							}
							
							if (params.font == null)
							{
								if (colAttr != null)
								{
									params.font = colAttr.m_CellFont;
								}
								
								if (params.font == null)
								{
									params.font = m_cellAttributesDefault.m_CellFont;
								}
							}
						}
						
						if (params.clrCellBackColor == null)
						{
							if (rowAttr != null)
							{
								params.clrCellBackColor = rowAttr.m_clrCellBackColor;
							}
							
							if (params.clrCellBackColor == null)
							{
								if (colAttr != null)
								{
									params.clrCellBackColor = colAttr.m_clrCellBackColor;
								}
								
								if (params.clrCellBackColor == null)
								{
									params.clrCellBackColor = m_cellAttributesDefault.m_clrCellBackColor;
								}
							}
						}
						
						if (params.clrCellForeColor == null)
						{
							if (rowAttr != null)
							{
								params.clrCellForeColor = rowAttr.m_clrCellForeColor;
							}
							
							if (params.clrCellForeColor == null)
							{
								if (colAttr != null)
								{
									params.clrCellForeColor = colAttr.m_clrCellForeColor;
								}
								
								if (params.clrCellForeColor == null)
								{
									params.clrCellForeColor = m_cellAttributesDefault.m_clrCellForeColor;
								}
							}
						}
						
						if (rowAttr != null)
						{
							params.iCellAlignment = GridCellAttributes.handleAlign(params.iCellAlignment, rowAttr.m_iCellAlignment);
						}
						
						if (colAttr != null)
						{
							params.iCellAlignment = GridCellAttributes.handleAlign(params.iCellAlignment, colAttr.m_iCellAlignment);
						}
						
						params.iCellAlignment = GridCellAttributes.handleAlign(params.iCellAlignment, m_cellAttributesDefault.m_iCellAlignment);
						
						if ((bCellIsSelected) && (m_bFullRowSelectionDisplay) && (m_bHighlightSelectedCell) && (cellID.Col == m_userSelectedCellID.Col))
						{
							params.clrCellBackColor = params.clrCellBackColor.darker(64);
						}
						
						g.setClip(params.rcClip);
						cell.onPaint(params);
						
						if (!bExceededWidth)
						{
							params.rcClip.x += params.iCellWidth;
							
							if (iRightX < params.rcClip.x)
							{
								iRightX = params.rcClip.x;
							}
						}
						else
						{
							m_bRightColFullDraw = false;
						}
					}
					
					if (!bExceededHeight)
					{
						params.rcClip.y += params.iCellHeight;
						
						if (iBottomY < params.rcClip.y)
						{
							iBottomY = params.rcClip.y;
						}
					}
					else
					{
						m_bBottomRowFullDraw = false;
					}
				}
			}
			
			if (!bExceededWidth || !bExceededHeight)
			{
				rc = _getCellDrawRect();

				g.clearClip();
				g.setBackColor(m_clrGridBackColor);
				
				if (!bExceededWidth)
				{
					g.fillRect(iRightX, 0, rc.width - iRightX, rc.height);
				}
				
				if (!bExceededHeight)
				{
					g.fillRect(0, iBottomY, 
						(bExceededWidth ? rc.width : rc.width - (rc.width - iRightX)),  
						rc.height - iBottomY);
				}
			}
		}
		
		int iVisibleItemsOld;
		int iVisibleItemsNew;
		
		iVisibleItemsOld = m_HorzScroll.getVisibleItems();
		iVisibleItemsNew = (m_iRightCol == -1 ? 1 : (m_iRightCol - m_iLeftCol) + (!bExceededWidth ? 1 : 0));
		
		if (iVisibleItemsOld != iVisibleItemsNew)
		{
			m_HorzScroll.setVisibleItems(iVisibleItemsNew);
		}
		
		iVisibleItemsOld = m_VertScroll.getVisibleItems();
		iVisibleItemsNew = (m_iBottomRow == -1 ? 1 : (m_iBottomRow - m_iTopRow) + (!bExceededHeight ? 1 : 0));
		
		if (iVisibleItemsOld != iVisibleItemsNew)
		{
			m_VertScroll.setVisibleItems(iVisibleItemsNew);
		}
		
		rc = getRect();
		
		g.clearClip();
		g.setForeColor(m_clrGridBorderColor);
		g.drawRect(0, 0, rc.width, rc.height);

		if (m_HorzScroll.isVisible())
		{
			g.drawLine(1, rc.height - m_iHorizontalScrollHeight - 2,
				rc.width - (m_VertScroll.isVisible() ? m_iVerticalScrollWidth : 0) - 2,
				rc.height - m_iHorizontalScrollHeight - 2);
		}
		
		if (m_VertScroll.isVisible())
		{
			g.drawLine(rc.width - m_iVerticalScrollWidth - 2, 1,
				rc.width - m_iVerticalScrollWidth - 2,
				rc.height - (m_HorzScroll.isVisible() ? m_iHorizontalScrollHeight : 0) - 2);
		}
		
		if (m_HorzScroll.isVisible() && m_VertScroll.isVisible())
		{
			g.setBackColor(m_clrGridBackColor);
			
			g.fillRect(rc.width - 1 - m_iHorizontalScrollHeight, rc.height - 1 - m_iVerticalScrollWidth, 
				m_iHorizontalScrollHeight, m_iVerticalScrollWidth);
		}
	}
	
	public void onBoundsChanged()
	{
		if (m_grphGrid != null)
		{
			m_grphGrid.free();
		}
		
		m_grphGrid = createGraphics();

		_repositionScrollBars();
	}   

	private void _repositionScrollBars()
	{
		Rect rc = getRect();
	
		m_HorzScroll.setRect(1, rc.height - m_iHorizontalScrollHeight - 1, 
			rc.width - (m_VertScroll.isVisible() ? m_iVerticalScrollWidth : 0) - 2, 
			m_iHorizontalScrollHeight);
	
		m_VertScroll.setRect(rc.width - m_iHorizontalScrollHeight - 1, 
			1, m_iVerticalScrollWidth, 
			rc.height - (m_HorzScroll.isVisible() ? m_iHorizontalScrollHeight : 0) - 2);
		
		_repaintGridIfNeeded();
	}
	
	public void onEvent(Event e)
	{
		switch (e.type)
		{
		case ControlEvent.WINDOW_MOVED:
			{
				if (m_grphGrid != null)
				{
					m_grphGrid.free();
				}
				
				m_grphGrid = createGraphics();

				break;
			}
		case ControlEvent.PRESSED:
			{
				if (e.target == m_VertScroll)
				{
					if (m_cellEditing != null)
					{
						endCellEditing();
					}
						
					int iScrollValue = m_VertScroll.getValue() + m_iFixedRowCount;
					
					if (m_iTopRow != iScrollValue)
					{
						_setTopRowEx(iScrollValue);
					}
				}
				else if (e.target == m_HorzScroll)
				{
					if (m_cellEditing != null)
					{
						endCellEditing();
					}
						
					int iScrollValue = m_HorzScroll.getValue() + m_iFixedColCount;
					
					if (m_iLeftCol != iScrollValue)
					{
						_setLeftColEx(iScrollValue);
					}
				}
				
				break;
			}
		case PenEvent.PEN_DRAG:
			{
				if (m_bUserResizingGrid)
				{
					Rect rc = _getCellDrawRect();
					
					if (m_iColBeingResized > -1)
					{
						int x = ((PenEvent) e).x;
						
						if ((m_iResizingCurrentValue != x) && (x < rc.x2())) 
						{
							m_grphGrid.drawCursor(m_iResizingCurrentValue, rc.y, 1, rc.height);

							m_iResizingCurrentValue = x;

							m_grphGrid.drawCursor(m_iResizingCurrentValue, rc.y, 1, rc.height);
						}
					}
					else // if (m_iRowBeingResized > -1)
					{
						int y = ((PenEvent) e).y;
						
						if ((m_iResizingCurrentValue != y) && (y < rc.y2())) 
						{
							m_grphGrid.drawCursor(rc.x, m_iResizingCurrentValue, rc.width, 1);

							m_iResizingCurrentValue = y;

							m_grphGrid.drawCursor(rc.x, m_iResizingCurrentValue, rc.width, 1);
						}
					}			
				}

				break;
			}
		case PenEvent.PEN_UP:
		case PenEvent.PEN_DOWN:
			{
				if (e.type == PenEvent.PEN_UP)
				{
					if ((e.target == m_VertScroll) || (e.target == m_HorzScroll))
					{
						requestFocus();
					}
				}

				if (e.target != this)
				{
					break;
				}

				Rect rc;
				
				int xPoint;
				int yPoint;
				int iLeft;
				int iTop;
				int iRight;
				int iBottom;

				if (e.type == PenEvent.PEN_UP)
				{
					if (m_bUserResizingGrid)
					{
						int iAux;
						
						if (m_iColBeingResized > -1)
						{
							iAux = getColWidth(m_iColBeingResized) + (m_iResizingCurrentValue - m_iResizingOriginalValue);
							
							if (iAux < 0)
							{
								iAux = 0;
							}
							
							setColWidth(m_iColBeingResized, iAux);
							m_iColBeingResized = -1;
						}
						else // if (m_iRowBeingResized > -1)
						{
							iAux = getRowHeight(m_iRowBeingResized) + (m_iResizingCurrentValue - m_iResizingOriginalValue);
							
							if (iAux < 0)
							{
								iAux = 0;
							}
							
							setRowHeight(m_iRowBeingResized, iAux);
							m_iRowBeingResized = -1;
						}
						
						m_iResizingOriginalValue = -1;
						m_iResizingCurrentValue = -1;
						
						m_bUserResizingGrid = false;
					}
					else
					{
						rc = _getCellDrawRect();
						
						xPoint = ((PenEvent) e).x;
						yPoint = ((PenEvent) e).y;
						
						if (m_bAllowSortRows && (m_iFixedRowCount == 1) && (yPoint < m_iTopRowY))
						{
							iLeft = m_iLeftColX - 1;

							for (int iCol = m_iLeftCol; iCol <= m_iRightCol; iCol++)
							{
								iRight = iLeft + getColWidth(iCol);
								
								if (iRight > rc.width)
								{
									iRight = rc.width;
								}
								
								if ((xPoint >= iLeft) && (xPoint <= iRight))
								{
									if (m_iLatestRowSortedCol != iCol)
									{
										m_iLatestRowSortedCol = iCol;
										m_bLatestRowSortOrder = true;
									}
									else
									{
										m_bLatestRowSortOrder = !m_bLatestRowSortOrder;
									}
									
									m_iLatestColSortedRow = -1;
									
									sortGridRowsEx(m_iLatestRowSortedCol, m_iRowSortLimitStart, m_iRowSortLimitEnd, m_bLatestRowSortOrder, false);
									
									break;
								}
								
								iLeft = iRight;
							}							
						}
						
						if (m_bAllowSortCols && (m_iFixedColCount == 1) && (xPoint < m_iLeftColX))
						{
							iTop = m_iTopRowY - 1;
							
							for (int iRow = m_iTopRow; iRow <= m_iBottomRow; iRow++)
							{
								iBottom = iTop + getRowHeight(iRow);
								
								if (iBottom > rc.height)
								{
									iBottom = rc.height;
								}
								
								if ((yPoint >= iTop) && (yPoint <= iBottom))
								{
									if (m_iLatestColSortedRow != iRow)
									{
										m_iLatestColSortedRow = iRow;
										m_bLatestColSortOrder = true;
									}
									else
									{
										m_bLatestColSortOrder = !m_bLatestColSortOrder;
									}
									
									m_iLatestRowSortedCol = -1;
									
									sortGridColsEx(m_iLatestColSortedRow, m_iColSortLimitStart, m_iColSortLimitEnd, m_bLatestColSortOrder, false);
									
									break;
								}
								
								iTop = iBottom;
							}							
						}					
					}
					
					break;
				}
				
				if (m_cellEditing != null)
				{
					endCellEditing();
				}
				
				if (m_bUserResizingGrid)
				{
					m_iColBeingResized = -1;
					m_iRowBeingResized = -1;
					m_iResizingOriginalValue = -1;
					m_iResizingCurrentValue = -1;
					
					m_grphGrid.free();
					m_grphGrid = null;
					
					m_bUserResizingGrid = false;
					
					repaintNow();
				}
				
				rc = _getCellDrawRect();

				xPoint = ((PenEvent) e).x;
				yPoint = ((PenEvent) e).y;
						
				if (((xPoint < m_iLeftColX) || (yPoint < m_iTopRowY)) && (m_bAllowUserResizeCols || m_bAllowUserResizeRows))
				{
					int iResize;
					int iLoopPoint;
					
					for (iLoopPoint = -3; iLoopPoint < 6; iLoopPoint++)
					{
						if (m_bAllowUserResizeCols)
						{
							iResize = m_arrRightColPoints.rFind(xPoint + iLoopPoint);
							
							if (iResize != IntVector.INVALID)
							{
								m_iRowBeingResized = -1;
								m_iColBeingResized = iResize;
								
								m_iResizingOriginalValue = xPoint + iLoopPoint;
								m_iResizingCurrentValue = m_iResizingOriginalValue;
								
								m_bUserResizingGrid = true;
	
								break;
							}
						}
						
						if (m_bAllowUserResizeRows)
						{
							iResize = m_arrBottomRowPoints.rFind(yPoint + iLoopPoint);

							if (iResize != IntVector.INVALID)
							{
								m_iRowBeingResized = iResize;
								m_iColBeingResized = -1;
								
								m_iResizingOriginalValue = yPoint + iLoopPoint;
								m_iResizingCurrentValue = m_iResizingOriginalValue;
								
								m_bUserResizingGrid = true;
	
								break;
							}
						}
					}
					
					if (m_bUserResizingGrid)
					{
						if (m_iColBeingResized > -1)
						{
							m_grphGrid.drawCursor(m_iResizingCurrentValue, rc.y, 1, rc.height);
						}
						else // if (m_iRowBeingResized > -1)
						{
							m_grphGrid.drawCursor(rc.x, m_iResizingCurrentValue, rc.width, 1);
						}
					}

					break;
				}
				
				int iCellCol = -1;
				int iCellRow = -1;

				iLeft = m_iLeftColX - 1;
				iTop = m_iTopRowY - 1;
				iRight = -1;
				iBottom = -1;
				
				for (int iCol = m_iLeftCol; iCol <= m_iRightCol; iCol++)
				{
					iRight = iLeft + getColWidth(iCol);
					
					if (iRight > rc.width)
					{
						iRight = rc.width;
					}
					
					if ((xPoint >= iLeft) && (xPoint <= iRight))
					{
						iCellCol = iCol;
						
						break;
					}
					
					iLeft = iRight;
				}
				
				for (int iRow = m_iTopRow; iRow <= m_iBottomRow; iRow++)
				{
					iBottom = iTop + getRowHeight(iRow);
					
					if (iBottom > rc.height)
					{
						iBottom = rc.height;
					}
					
					if ((yPoint >= iTop) && (yPoint <= iBottom))
					{
						iCellRow = iRow;
						
						break;
					}
					
					iTop = iBottom;
				}
				
				if ((iCellRow != -1) && (iCellCol != -1))
				{
					boolean bUpdating = m_bUpdating;
					
					m_bUpdating = false;

					boolean bSelectionChanged = ((iCellRow != m_userSelectedCellID.Row) || (iCellCol != m_userSelectedCellID.Col));
					boolean bWillRedraw = bSelectionChanged;
					boolean bWillScroll = false;
					
					m_userSelectedCellID.Col = iCellCol;
					m_userSelectedCellID.Row = iCellRow;
					
					if ((m_userSelectedCellID.Col == m_iRightCol) && (!m_bRightColFullDraw))
					{
						bWillScroll = true;
						bWillRedraw = true;
						m_iLeftCol++;
						
						m_HorzScroll.setValue(m_iLeftCol - m_iFixedColCount);
					}
					
					if ((m_userSelectedCellID.Row == m_iBottomRow) && (!m_bBottomRowFullDraw))
					{
						bWillScroll = true;
						bWillRedraw = true;
						m_iTopRow++;
						
						m_VertScroll.setValue(m_iTopRow - m_iFixedRowCount);
					}
					
					m_bUpdating = bUpdating;
					
					if ((m_userSelectedCellID.Row > -1) && (m_userSelectedCellID.Col > -1))
					{
						GridEvent evt = new GridEvent();
										
						evt.target = this;
						evt.cellID = new GridCellID(m_userSelectedCellID.Row, m_userSelectedCellID.Col);

						if (e.type == PenEvent.PEN_UP)
						{
							evt.type = GridEvent.CELL_PEN_UP;
						}
						else if (e.type == PenEvent.PEN_DOWN)
						{
							evt.type = GridEvent.CELL_PEN_DOWN;
						}

						postEvent(evt);
					}

					if (bSelectionChanged)
					{
						GridEvent evt = new GridEvent();
						
						evt.target = this;
						evt.type = GridEvent.SELECTION_CHANGED;
						evt.cellID = new GridCellID(m_userSelectedCellID.Row, m_userSelectedCellID.Col);
						
						postEvent(evt);
					}					

					if ((!bWillScroll) && (e.type == PenEvent.PEN_DOWN))
					{
						Time timeClick = new Time();
						long lDiffTime;
						boolean bWillEdit;

						if (m_lastTimeClicked != null)
						{
							lDiffTime = timeClick.getTimeLong() - m_lastTimeClicked.getTimeLong();
						}
						else
						{
							lDiffTime = 999;
						}
						
						if (m_bEditOnSingleClick)
						{
							bWillEdit = true;
						}
						else
						{					
							if (bSelectionChanged)
							{
								lDiffTime = 999;
							}

							bWillEdit = (lDiffTime < 3);
						}
						
						if (bWillEdit)
						{
							GridCell cell = null;

							bWillEdit = m_bAllowEditCells;

							if (bWillEdit)
							{
								if (m_mapEditRowLocks.get(m_userSelectedCellID.Row) == IntHashtable.INVALID)
								{
									if (m_mapEditColLocks.get(m_userSelectedCellID.Col) == IntHashtable.INVALID)
									{
										cell = m_Storage.getCell(m_userSelectedCellID);

										try
										{
											bWillEdit = cell.supportEditing();
										}
										catch (Exception e2)
										{
											bWillEdit = false;
										}
									}
									else
									{
										bWillEdit = false;
									}
								}
								else
								{
									bWillEdit = false;
								}
							}
							
							if (bWillEdit)
							{
								Rect cellRect = new Rect();
								
								cellRect.x = iLeft;
								cellRect.y = iTop;
								cellRect.width = (iRight - iLeft) + 1;
								cellRect.height = (iBottom - iTop) + 1;
								cellRect.modify(1, 1, -1 + (m_bDrawColSeparators ? -1 : 0), -1 + (m_bDrawRowSeparators ? -1 : 0));
								
								Color clrForeColor = cell.getCellForeColor();
								Color clrBackColor = cell.getCellBackColor();
								
								if (clrForeColor == null)
								{
									clrForeColor = m_cellAttributesDefault.getCellForeColor();
								}
								
								if (clrBackColor == null)
								{
									clrBackColor = m_cellAttributesDefault.getCellBackColor();
								}
	
								Font cellFont = cell.getCellFont();
								
								if (cellFont == null)
								{
									cellFont = m_cellAttributesDefault.getCellFont();
								}
								
								GridEvent evt = new GridEvent();
								
								evt.target = this;
								evt.type = GridEvent.START_EDIT_CELL;
								evt.cellID = new GridCellID(m_userSelectedCellID.Row, m_userSelectedCellID.Col);
								
								postEvent(evt);
								
								timeClick = null;

								if (cell.onStartEdit(this, evt.cellID, cellRect, clrForeColor, clrBackColor, cellFont, 0))
								{
									m_cellEditing = cell;
								}
								else
								{
									m_cellEditing = null;
									
									evt.type = GridEvent.END_EDIT_CELL;

									postEvent(evt);
									
									cell = null;
									
									requestFocus();
								}
							}
							
							if (m_cellEditing == null)
							{
								if (!m_bEditOnSingleClick || (m_bEditOnSingleClick && (lDiffTime < 3)))
								{
									GridEvent evt = new GridEvent();
									
									evt.target = this;
									evt.type = GridEvent.CELL_DOUBLE_CLICKED;
									evt.cellID = new GridCellID(m_userSelectedCellID.Row, m_userSelectedCellID.Col);
									
									postEvent(evt);
									
									timeClick = null;
								}
							}
						}
						
						m_lastTimeClicked = timeClick;
					}
					
					if (bWillRedraw)
					{
						_repaintGridIfNeeded();
					}
				}

				break;
			}
		case KeyEvent.KEY_PRESS:
			{
				if (e.target != this)
				{
					break;
				}
				
				int iKey = ((KeyEvent) e).key;
				
				switch (iKey)
				{
				case IKeys.PAGE_UP:
					{
						scrollUpLines(8);
						
						break;
					}
				case IKeys.PAGE_DOWN:
					{
						scrollDownLines(8);
						
						break;
					}
				case IKeys.HOME:
					{
						scrollLeftCols(3);
						
						break;   
					}
				case IKeys.END:
					{
						scrollRightCols(3);
						
						break;
					}
				case IKeys.UP:
					{
						scrollUp();
						
						break;
					}
				case IKeys.DOWN:
					{
						scrollDown();
						
						break;
					}
				case IKeys.LEFT:
					{
						scrollLeft();
						
						break;
					}
				case IKeys.RIGHT:
					{
						scrollRight();
						
						break;
					}
				}
			}
		}			
	}
}

/*
 * Private class used for sorting grid
 */
class GridSorter
{
	/* Container cell */
	public GridCell cell;
	/* Index */
	public int index;
	
	/*
	 * Routine used to compare cell contents
	 */
	public int compareTo(GridSorter other)
	{
		if (cell == null)
		{
			if (other.cell == null)
			{
				return 0;
			}
			else
			{
				return -1;
			}
		}
		else
		{
			if (other.cell == null)
			{
				return 1;
			}
			else
			{
				if ((cell instanceof GridCellDecimal) && 
					(other.cell instanceof GridCellDecimal))
				{
					double d1 = ((GridCellDecimal) cell).getDouble();
					double d2 = ((GridCellDecimal) other.cell).getDouble();
					
					if (d1 == d2)
					{
						return 0;
					}
					else
					{
						return (d2 > d1 ? -1 : 1);
					}		
				}
				else
				{
					return cell.getCellCompareValue().compareTo(other.cell.getCellCompareValue());
				}
			}
		}
	}
	
	/*
	 * Routine used to sort the GridSorter array
	 */
	public static void QuickSort(GridSorter [] items, int first, int last)
	{
		if (first >= last)
		{
			return;
		}

		int low = first;
		int high = last;

		GridSorter mid = items[(first + last) >> 1];

		do
		{
			while (mid.compareTo(items[low]) > 0)
			{	
				low++;
			}
			
			while (mid.compareTo(items[high]) < 0)
			{
				high--;
			}
			
			if (low <= high)
			{
				GridSorter temp = items[low];
				
				items[low++] = items[high];
				items[high--] = temp;
			}
		}
		while (low <= high);

		QuickSort(items, first, high);
		QuickSort(items, low, last);
	}
}
