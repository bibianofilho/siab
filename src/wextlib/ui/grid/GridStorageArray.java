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

import waba.util.*;

/**
 * This class implements an array-based storage model for the grid control.
 * Use this storage model when you don't want to change the number of the
 * row and columns, because it uses a array of array of GridCell objects
 * and copy from the old array to the new array when you change the number
 * of rows and coluns, and this could be EXTREME SLOW!
 * 
 * @author Virgilio Alexandre Fornazin (mailto:virgiliofornazin@gmail.com)
 */
public final class GridStorageArray extends GridStorageModel 
{
	/** Model rows count */
	private int m_iRowCount;
	/** Model cols count */
	private int m_iColCount;
	/** Cell's array */
	private GridCell [][] m_Cells;
	
	/**
	 * Default constructor
	 */
	public GridStorageArray()
	{
		eraseAllCells();
	}
	
	public boolean isReadOnly()
	{
		return false;
	}
	
	public GridCell createCell(GridCellID cellID) 
	{
		try
		{
			GridCell cell = m_Cells[cellID.Row][cellID.Col];
			
			if (cell == null)
			{
				cell = new GridCell();
				
				m_Cells[cellID.Row][cellID.Col] = cell;
			}
			
			return cell;
		}
		catch (Exception e)
		{
		}	

		return null;
	}

	public GridCell getCell(GridCellID cellID) 
	{
		try
		{
			return m_Cells[cellID.Row][cellID.Col];
		}
		catch (Exception e)
		{
		}

		return null;
	}

	public boolean putCell(GridCellID cellID, GridCell cell) 
	{
		try
		{
			m_Cells[cellID.Row][cellID.Col] = cell;
			
			return true;
		}
		catch (Exception e)
		{
		}
		
		return false;
	}

	public boolean eraseCell(GridCellID cellID) 
	{
		try
		{
			m_Cells[cellID.Row][cellID.Col] = null;
			
			return true;
		}
		catch (Exception e)
		{
		}
		
		return false;
	}

	public boolean eraseAllCells()
	{	
		m_iRowCount = 0;
		m_iColCount = 0;

		m_Cells = null;
		
		return true;
	}

	public GridCellHashtable getRowCells(int iRow)
	{
		GridCellHashtable gcht = new GridCellHashtable((int) (m_iColCount / 2));
		
		try
		{
			for (int i = 0; i < m_iColCount; i++)
			{
				if (m_Cells[iRow][i] != null)
				{
					gcht.put(i, m_Cells[iRow][i]);
				}
			}
		}
		catch (Exception e)
		{
		}
		
		return gcht;
	}

	public void putRowCells(int iRow, GridCellHashtable gcht)
	{
		if ((iRow > -1) && (iRow < m_iRowCount))
		{
			IntVector vecKeys = gcht.getKeys();
			int iCount = vecKeys.getCount();
			int iCol;
			
			for (int i = 0; i < iCount; i++)
			{
				iCol = vecKeys.items[i];
				
				if ((iCol > -1) && (iCol < m_iColCount))
				{
					m_Cells[iRow][iCol] =  gcht.get(iCol);
				}
			}
		}
	}
	
	public void eraseRowCells(int iRow)
	{
		if ((iRow > -1) && (iRow < m_iRowCount))
		{
			for (int i = 0; i < m_iColCount; i++)
			{
				m_Cells[iRow][i] = null;
			}
		}
	}
		
	public GridCellHashtable getColCells(int iCol)
	{
		GridCellHashtable gcht = new GridCellHashtable((int) (m_iRowCount / 2));
		
		try
		{
			for (int i = 0; i < m_iRowCount; i++)
			{
				if (m_Cells[i][iCol] != null)
				{
					gcht.put(i, m_Cells[i][iCol]);
				}
			}
		}
		catch (Exception e)
		{
		}
		
		return gcht;
	}
	
	public void putColCells(int iCol, GridCellHashtable gcht)
	{
		if ((iCol > -1) && (iCol < m_iColCount))
		{
			IntVector vecKeys = gcht.getKeys();
			int iCount = vecKeys.getCount();
			int iRow;
			
			for (int i = 0; i < iCount; i++)
			{
				iRow = vecKeys.items[i];
				
				if ((iRow > -1) && (iRow < m_iRowCount))
				{
					m_Cells[iRow][iCol] = gcht.get(iRow);
				}
			}
		}
	}
	
	public void eraseColCells(int iCol)
	{
		if ((iCol > -1) && (iCol < m_iColCount))
		{
			for (int i = 0; i < m_iRowCount; i++)
			{
				m_Cells[i][iCol] = null;
			}
		}
	}
	
	public boolean setRowCount(int iRowCount) 
	{
		if (m_iRowCount == iRowCount)
		{
			return true;
		}
		
		GridCell [][] cells = null;
		
		if ((iRowCount > 0) && (m_iColCount > 0))
		{
			int iLoop = Math.min(m_iRowCount, iRowCount);

			cells = new GridCell[iRowCount][m_iColCount];
			
			for (int r = 0; r < iLoop; r++)
			{
				for (int c = 0; c < m_iColCount; c++)
				{
					cells[r][c] = m_Cells[r][c];
				}
			}
		}
			
		m_Cells = cells;
	
		m_iRowCount = iRowCount;
		
		return true;
	}
	
	public int getRowCount()
	{
		return m_iRowCount;
	}
	
	public void setRowStrings(int iRow, String [] strCellsText)
	{
		if (!((-1 < iRow) && (iRow < m_iRowCount)) || (strCellsText == null))
		{
			return;
		}
		
		int iLen = strCellsText.length;
		int iMax = (iLen < m_iColCount ? iLen : m_iColCount);
		
		GridCell cell;
		String strText;
		
		for (int i = 0; i < iMax; i++)
		{
			cell = m_Cells[iRow][i];
			strText = strCellsText[i];
			
			if (strText == null)
			{
				if (cell != null)
				{
					cell.m_strCellText = "";
				}
			}
			else
			{
				if (cell == null)
				{
					cell = new GridCell();

					m_Cells[iRow][i] = cell;
				}
				
				cell.m_strCellText = strText;
			}			
		}
	}

	public String [] getRowStrings(int iRow)
	{
		String [] ret = new String [m_iColCount];
		GridCell cell;
		
		for (int i = 0; i < m_iColCount; i++)
		{
			try
			{
				cell = m_Cells[iRow][i];
			
				ret[i] = cell.m_strCellText;
			}
			catch (Exception e)
			{
			}
		}
		
		return ret;
	}
	
	public int removeRows(int iRowStart, int iRowEnd)
	{
		if (iRowEnd == m_iRowCount - 1)
		{
			setRowCount(iRowStart);
			
			return m_iRowCount;
		}
		
		int iRowCount = m_iRowCount - ((iRowEnd - iRowStart) + 1);
		
		GridCell [][] cells = null;
		
		if ((iRowCount > 0) && (m_iColCount > 0))
		{
			int i;
			
			cells = new GridCell[iRowCount][m_iColCount];
			
			for (i = 0; i < iRowStart; i++)
			{
				for (int c = 0; c < m_iColCount; c++)
				{
					cells[i][c] = m_Cells[i][c];
				}
			}
			
			for (int ni = iRowEnd + 1; ni < m_iRowCount; ni++)
			{
				for (int c = 0; c < m_iColCount; c++)
				{
					cells[i][c] = m_Cells[ni][c];
				}

				i++;
			}
		}
			
		m_Cells = cells;
		m_iRowCount = iRowCount;

		return m_iRowCount;
	}

	public boolean swapRows(int iRow1, int iRow2)
	{
		try
		{
			GridCell [] celldst = m_Cells[iRow2];
			GridCell [] cellsrc = m_Cells[iRow1];
			GridCell cellswp;
			int i;
			
			for (i = 0; i < m_iColCount; i++)
			{
				cellswp = celldst[i];
				celldst[i] = cellsrc[i];
				cellsrc[i] = cellswp;
			}
			
			return true;
		}
		catch (Exception e)
		{
			return false;
		}
	}
	
	public boolean setColCount(int iColCount) 
	{
		if (m_iColCount == iColCount)
		{
			return true;
		}
		
		GridCell [][] cells = null;
		
		if ((iColCount > 0) && (m_iRowCount > 0))
		{
			int iLoop = Math.min(m_iColCount, iColCount);

			cells = new GridCell[m_iRowCount][iColCount];
			
			for (int c = 0; c < iLoop; c++)
			{
				for (int r = 0; r < m_iRowCount; r++)
				{
					cells[r][c] = m_Cells[r][c];
				}
			}
		}
			
		m_Cells = cells;
	
		m_iColCount = iColCount;
		
		return true;
	}

	public int getColCount()
	{
		return m_iColCount;
	}
	
	public void setColStrings(int iCol, String [] strCellsText)
	{
		if (!((-1 < iCol) && (iCol < m_iColCount)) || (strCellsText == null))
		{
			return;
		}
		
		int iLen = strCellsText.length;
		int iMax = (iLen < m_iRowCount ? iLen : m_iRowCount);
		
		GridCell cell;
		String strText;
		
		for (int i = 0; i < iMax; i++)
		{
			cell = m_Cells[i][iCol];
			strText = strCellsText[i];
			
			if (strText == null)
			{
				if (cell != null)
				{
					cell.m_strCellText = "";
				}
			}
			else
			{
				if (cell == null)
				{
					cell = new GridCell();

					m_Cells[i][iCol] = cell;
				}
				
				cell.m_strCellText = strText;
			}			
		}
	}

	public String [] getColStrings(int iCol)
	{
		String [] ret = new String [m_iRowCount];
		GridCell cell;
		
		for (int i = 0; i < m_iRowCount; i++)
		{
			try
			{
				cell = m_Cells[i][iCol];

				ret[i] = cell.m_strCellText;
			}
			catch (Exception e)
			{
			}
		}
		
		return ret;
	}
	
	public int removeCols(int iColStart, int iColEnd)
	{
		if (iColEnd == m_iColCount - 1)
		{
			setColCount(iColStart);
			
			return m_iColCount;
		}
		
		int iColCount = m_iColCount - ((iColEnd - iColStart) + 1);
		
		GridCell [][] cells = null;
		
		if ((iColCount > 0) && (m_iRowCount > 0))
		{
			int i;
			
			cells = new GridCell[m_iRowCount][iColCount];
			
			for (i = 0; i < iColStart; i++)
			{
				for (int r = 0; r < m_iRowCount; r++)
				{
					cells[r][i] = m_Cells[r][i];
				}
			}
			
			for (int ni = iColEnd + 1; ni < m_iColCount; ni++)
			{
				for (int r = 0; r < m_iRowCount; r++)
				{
					cells[r][i] = m_Cells[r][ni];
				}

				i++;
			}
		}
			
		m_Cells = cells;
		m_iColCount = iColCount;
		
		return m_iColCount;
	}

	public boolean swapCols(int iCol1, int iCol2)
	{
		try
		{
			GridCell cellswp;
			int i;
			
			for (i = 0; i < m_iRowCount; i++)
			{
				cellswp = m_Cells[i][iCol1];
				m_Cells[i][iCol1] = m_Cells[i][iCol2];
				m_Cells[i][iCol2] = cellswp;
			}
			
			return true;
		}
		catch (Exception e)
		{
			return false;
		}
	}
}
