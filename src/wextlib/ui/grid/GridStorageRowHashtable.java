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
 * Use this storage when you plan to add or remove rows in the grid.
 * 
 * @author Virgilio Alexandre Fornazin (mailto:virgiliofornazin@gmail.com)
 */
public final class GridStorageRowHashtable extends GridStorageModel 
{
	/** Model rows count */
	private int m_iRowCount;
	/** Model cols count */
	private int m_iColCount;
	/** Cell's Hashtable */
	private GridRowColHashtable m_mapRows;
	
	/**
	 * Default constructor
	 */
	public GridStorageRowHashtable()
	{
		eraseAllCells();
	}
	
	public boolean isReadOnly()
	{
		return false;
	}
	
	public GridCell createCell(GridCellID cellID) 
	{
		GridCellHashtable row = m_mapRows.get(cellID.Row);
		
		if (row == null)
		{
			row = new GridCellHashtable((int) m_iColCount / 2);
			
			m_mapRows.put(cellID.Row, row);
		}
		
		GridCell cell = row.get(cellID.Col);
		
		if (cell == null)
		{
			cell = new GridCell();
			
			row.put(cellID.Col, cell);
		}
		
		return cell;
	}

	public GridCell getCell(GridCellID cellID) 
	{
		try
		{
			return m_mapRows.get(cellID.Row).get(cellID.Col);
		}
		catch (Exception e)
		{
		}

		return null;
	}

	public boolean putCell(GridCellID cellID, GridCell cell) 
	{
		GridCellHashtable row = m_mapRows.get(cellID.Row);
		
		if (row == null)
		{
			row = new GridCellHashtable((int) m_iColCount / 2);
			
			m_mapRows.put(cellID.Row, row);
		}
		
		row.put(cellID.Col, cell);
		
		return true;
	}

	public boolean eraseCell(GridCellID cellID) 
	{
		try
		{
			m_mapRows.get(cellID.Row).remove(cellID.Col);
			
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
		
		m_mapRows = new GridRowColHashtable(8);
		
		return true;
	}

	public GridCellHashtable getRowCells(int iRow)
	{
		GridCellHashtable row = m_mapRows.get(iRow);
		
		if (row == null)
		{
			return new GridCellHashtable(1);
		}
		
		int iSize = row.size();
		GridCellHashtable gcht = new GridCellHashtable(iSize);
		IntVector keys = row.getKeys();
		int iKey;
		
		for (int i = 0; i < iSize; i++)
		{
			iKey = keys.items[i];			
			gcht.put(iKey, row.get(iKey));
		}
		
		return gcht;
	}
	
	public void putRowCells(int iRow, GridCellHashtable gcht)
	{
		GridCellHashtable row = m_mapRows.get(iRow);
		
		if (row == null)
		{
			row = new GridCellHashtable(gcht.size());
			
			m_mapRows.put(iRow, row);
		}
		
		IntVector vecKeys = gcht.getKeys();
		int iCount = vecKeys.getCount();
		int iCol;
		
		for (int i = 0; i < iCount; i++)
		{
			iCol = vecKeys.items[i];
			
			if ((iCol > -1) && (iCol < m_iColCount))
			{
				row.put(iCol, gcht.get(iCol));
			}
		}
	}

	public void eraseRowCells(int iRow)
	{
		GridCellHashtable row = m_mapRows.get(iRow);
		
		try
		{
			row.clear();
		}
		catch (Exception e)
		{
		}
	}

	public GridCellHashtable getColCells(int iCol)
	{
		GridCellHashtable gcht = new GridCellHashtable((int) (m_iRowCount / 4));
		GridCellHashtable row;
		GridCell cell;
		
		for (int i = 0; i < m_iRowCount; i++)
		{
			row = m_mapRows.get(i);
			
			if (row != null)
			{
				cell = row.get(iCol);
				
				if (cell != null)
				{
					gcht.put(i, cell);
				}
			}
		}
		
		return gcht;
	}
	
	public void putColCells(int iCol, GridCellHashtable gcht)
	{
		if ((iCol > -1) && (iCol < m_iColCount))
		{
			GridCellHashtable row;
			IntVector vecKeys = gcht.getKeys();
			int iCount = vecKeys.getCount();
			int iRow;
			
			for (int i = 0; i < iCount; i++)
			{
				iRow = vecKeys.items[i];

				if ((iRow > -1) && (iRow < m_iRowCount))
				{
					row = m_mapRows.get(iRow);
					
					if (row == null)
					{
						row = new GridCellHashtable((int) m_iColCount / 2);
						
						m_mapRows.put(iRow, row);
					}
					
					row.put(iCol, gcht.get(iRow));
				}
			}
		}
	}

	public void eraseColCells(int iCol)
	{
		if ((iCol > -1) && (iCol < m_iColCount))
		{
			GridCellHashtable row;
			
			for (int i = 0; i < m_iRowCount; i++)
			{
				row = m_mapRows.get(i);
	
				try
				{
					row.remove(iCol);
				}
				catch (Exception e)
				{
				}
			}
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
				cell = m_mapRows.get(iRow).get(i);
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

					GridCellHashtable col = m_mapRows.get(iRow);
					
					if (col == null)
					{
						col = new GridCellHashtable((int) m_iColCount / 2);
						
						m_mapRows.put(iRow, col);
					}
					
					col.put(i, cell);
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
				cell = m_mapRows.get(iRow).get(i);
				
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
			
			m_mapRows = new GridRowColHashtable(8);
		}
		else
		{
			GridCellHashtable row;

			int iReadID = iRowEnd + 1;
			int iWriteID = iRowStart;
			
			while (iReadID <= iRowCount)
			{
				row = m_mapRows.remove(iReadID);
				
				if (row != null)
				{
					m_mapRows.put(iWriteID, row);
				}
				else
				{
					m_mapRows.remove(iWriteID);
				}
				
				iReadID++;
				iWriteID++;
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
				cell = m_mapRows.get(i).get(iCol);
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

					GridCellHashtable col = m_mapRows.get(i);
					
					if (col == null)
					{
						col = new GridCellHashtable((int) m_iColCount / 2);
						
						m_mapRows.put(i, col);
					}
					
					col.put(iCol, cell);
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
				cell = m_mapRows.get(i).get(iCol);
				
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
			
			m_mapRows = new GridRowColHashtable(8);
		}
		else
		{
			GridCellHashtable row;
			GridCell cell;
			
			int iReadID;
			int iWriteID;
			
			for (int i = 0; i < m_iRowCount; i++)
			{
				row = m_mapRows.get(i);
				
				if (row != null)
				{
					iReadID = iColEnd + 1;
					iWriteID = iColStart;
					
					while (iReadID <= m_iColCount)
					{
						cell = row.remove(iReadID);
						
						if (cell != null)
						{
							row.put(iWriteID, cell);
						}
						else
						{
							row.remove(iWriteID);
						}
						
						iReadID++;
						iWriteID++;
					}
				}
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
		for (int r = iRowStart; r < iRowEnd; r++)
		{
			m_mapRows.remove(r);
		}
	}
	
	private void _removePendingCols(int iColStart, int iColEnd)
	{
		GridCellHashtable row;
		
		for (int r = 0; r < m_iRowCount; r++)
		{
			row = m_mapRows.get(r);
			
			if (row != null)
			{
				for (int c = iColStart; c < iColEnd; c++)
				{
					row.remove(c);
				}
			}
		}
	}
}
