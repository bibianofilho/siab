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
 * This class implements an row hashtable-based storage model for the grid control.
 * Use this storage when you plan to add or remove columns in the grid.
 * 
 * @author Virgilio Alexandre Fornazin (mailto:virgiliofornazin@gmail.com)
 */
public final class GridStorageColumnHashtable extends GridStorageModel 
{
	/** Model rows count */
	private int m_iRowCount;
	/** Model cols count */
	private int m_iColCount;
	/** Cell's Hashtable */
	private GridRowColHashtable m_mapCols;
	
	/**
	 * Default constructor
	 */
	public GridStorageColumnHashtable()
	{
		eraseAllCells();
	}
	
	public boolean isReadOnly()
	{
		return false;
	}
	
	public GridCell createCell(GridCellID cellID) 
	{
		GridCellHashtable col = m_mapCols.get(cellID.Col);
		
		if (col == null)
		{
			col = new GridCellHashtable((int) m_iRowCount / 2);
			
			m_mapCols.put(cellID.Col, col);
		}
		
		GridCell cell = col.get(cellID.Row);
		
		if (cell == null)
		{
			cell = new GridCell();
			
			col.put(cellID.Row, cell);
		}
		
		return cell;
	}

	public GridCell getCell(GridCellID cellID) 
	{
		try
		{
			return m_mapCols.get(cellID.Col).get(cellID.Row);
		}
		catch (Exception e)
		{
		}

		return null;
	}

	public boolean putCell(GridCellID cellID, GridCell cell) 
	{
		GridCellHashtable col = m_mapCols.get(cellID.Col);
		
		if (col == null)
		{
			col = new GridCellHashtable((int) m_iRowCount / 2);
			
			m_mapCols.put(cellID.Col, col);
		}
		
		col.put(cellID.Row, cell);
		
		return true;
	}

	public boolean eraseCell(GridCellID cellID) 
	{
		try
		{
			m_mapCols.get(cellID.Col).remove(cellID.Row);
			
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
		
		m_mapCols = new GridRowColHashtable(8);
		
		return true;
	}

	public GridCellHashtable getRowCells(int iRow)
	{
		GridCellHashtable gcht = new GridCellHashtable((int) (m_iColCount / 4));
		GridCellHashtable col;
		GridCell cell;
		
		for (int i = 0; i < m_iColCount; i++)
		{
			col = m_mapCols.get(i);
			
			if (col != null)
			{
				cell = col.get(iRow);
				
				if (cell != null)
				{
					gcht.put(i, cell);
				}
			}
		}
		
		return gcht;
	}
	
	public void putRowCells(int iRow, GridCellHashtable gcht)
	{
		if ((iRow > -1) && (iRow < m_iRowCount))
		{
			GridCellHashtable col;
			IntVector vecKeys = gcht.getKeys();
			int iCount = vecKeys.getCount();
			int iCol;
			
			for (int i = 0; i < iCount; i++)
			{
				iCol = vecKeys.items[i];

				if ((iCol > -1) && (iCol < m_iColCount))
				{
					col = m_mapCols.get(iCol);
					
					if (col == null)
					{
						col = new GridCellHashtable((int) m_iRowCount / 2);
						
						m_mapCols.put(iCol, col);
					}
					
					col.put(iRow, gcht.get(iCol));
				}
			}
		}
	}

	public void eraseRowCells(int iRow)
	{
		if ((iRow > -1) && (iRow < m_iRowCount))
		{
			GridCellHashtable col;
			
			for (int i = 0; i < m_iColCount; i++)
			{
				col = m_mapCols.get(i);
	
				try
				{
					col.remove(iRow);
				}
				catch (Exception e)
				{
				}
			}
		}
	}
	
	public GridCellHashtable getColCells(int iCol)
	{
		GridCellHashtable col = m_mapCols.get(iCol);
		
		if (col == null)
		{
			return new GridCellHashtable(1);
		}
		
		int iSize = col.size();
		GridCellHashtable gcht = new GridCellHashtable(iSize);
		IntVector keys = col.getKeys();
		int iKey;
		
		for (int i = 0; i < iSize; i++)
		{
			iKey = keys.items[i];			
			gcht.put(iKey, col.get(iKey));
		}
		
		return gcht;
	}
	
	public void putColCells(int iCol, GridCellHashtable gcht)
	{
		GridCellHashtable col = m_mapCols.get(iCol);
		
		if (col == null)
		{
			col = new GridCellHashtable(gcht.size());
			
			m_mapCols.put(iCol, col);
		}
		
		IntVector vecKeys = gcht.getKeys();
		int iCount = vecKeys.getCount();
		int iRow;
		
		for (int i = 0; i < iCount; i++)
		{
			iRow = vecKeys.items[i];
			
			if ((iRow > -1) && (iRow < m_iRowCount))
			{
				col.put(iRow, gcht.get(iRow));
			}
		}
	}

	public void eraseColCells(int iCol)
	{
		GridCellHashtable col = m_mapCols.get(iCol);
		
		try
		{
			col.clear();
		}
		catch (Exception e)
		{
		}
	}
	
	public boolean setRowCount(int iRowCount) 
	{
		if (m_iRowCount == iRowCount)
		{
			return true;
		}
		
		if (iRowCount < m_iRowCount)
		{
			_removePendingRows(iRowCount, m_iRowCount);
		}

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
			try
			{
				cell = m_mapCols.get(i).get(iRow);
			}
			catch (Exception e)
			{
				cell = null;
			}

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

					GridCellHashtable col = m_mapCols.get(i);
					
					if (col == null)
					{
						col = new GridCellHashtable((int) m_iRowCount / 2);
						
						m_mapCols.put(i, col);
					}
					
					col.put(iRow, cell);
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
				cell = m_mapCols.get(i).get(iRow);
			
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
		
		if ((iRowCount < 1) || (m_iColCount == 0))
		{
			iRowCount = 0;
			
			m_mapCols = new GridRowColHashtable(8);
		}
		else
		{
			GridCellHashtable col;
			GridCell cell;
			
			int iReadID;
			int iWriteID;
			
			for (int i = 0; i < m_iColCount; i++)
			{
				col = m_mapCols.get(i);
				
				if (col != null)
				{
					iReadID = iRowEnd + 1;
					iWriteID = iRowStart;
					
					while (iReadID <= m_iRowCount)
					{
						cell = col.remove(iReadID);
						
						if (cell != null)
						{
							col.put(iWriteID, cell);
						}
						else
						{
							col.remove(iWriteID);
						}
						
						iReadID++;
						iWriteID++;
					}
				}
			}
		}
	
		if (iRowCount < m_iRowCount)
		{
			_removePendingRows(iRowCount, m_iRowCount);
		}
		
		m_iRowCount = iRowCount;
		
		return m_iRowCount;
	}

	public boolean setColCount(int iColCount) 
	{
		if (m_iColCount == iColCount)
		{
			return true;
		}
		
		if (iColCount < m_iColCount)
		{
			_removePendingCols(iColCount, m_iColCount);
		}
	
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
			try
			{
				cell = m_mapCols.get(iCol).get(i);
			}
			catch (Exception e)
			{
				cell = null;
			}

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

					GridCellHashtable col = m_mapCols.get(iCol);
					
					if (col == null)
					{
						col = new GridCellHashtable((int) m_iRowCount / 2);
						
						m_mapCols.put(iCol, col);
					}
					
					col.put(i, cell);
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
				cell = m_mapCols.get(iCol).get(i);
			
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
		
		if ((iColCount < 1) || (m_iRowCount == 0))
		{
			iColCount = 0;
			
			m_mapCols = new GridRowColHashtable(8);
		}
		else
		{
			GridCellHashtable col;

			int iReadID = iColEnd + 1;
			int iWriteID = iColStart;
			
			while (iReadID <= iColCount)
			{
				col = m_mapCols.remove(iReadID);
				
				if (col != null)
				{
					m_mapCols.put(iWriteID, col);
				}
				else
				{
					m_mapCols.remove(iWriteID);
				}
				
				iReadID++;
				iWriteID++;
			}
		}
		
		if (iColCount < m_iColCount)
		{
			_removePendingCols(iColCount, m_iColCount);
		}
		
		m_iColCount = iColCount;
		
		return m_iColCount;
	}
	
	private void _removePendingRows(int iRowStart, int iRowEnd)
	{
		GridCellHashtable col;
		
		for (int c = 0; c < m_iColCount; c++)
		{
			col = m_mapCols.get(c);
			
			if (col != null)
			{
				for (int r = iRowStart; r < iRowEnd; r++)
				{
					col.remove(r);
				}
			}
		}
	}
	
	private void _removePendingCols(int iColStart, int iColEnd)
	{
		for (int c = iColStart; c < iColEnd; c++)
		{
			m_mapCols.remove(c);
		}
	}
}
