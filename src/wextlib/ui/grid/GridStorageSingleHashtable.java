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
 * This class implements an single hashtable-based storage model for the grid control.
 * Use this storage when you plan to add/remove lines or columns. This is the default
 * storage model for the Grid control, because it could grow too fast (don't need to 
 * copy from old to new storage like GridStorageArray).
 * 
 * @author Virgilio Alexandre Fornazin (mailto:virgiliofornazin@gmail.com)
 */
public final class GridStorageSingleHashtable extends GridStorageModel 
{
	/** Model rows count */
	private int m_iRowCount;
	/** Model cols count */
	private int m_iColCount;
	/** Cell's Hashtable */
	private GridCellHashtable m_mapCells;
	
	/**
	 * Default constructor
	 */
	public GridStorageSingleHashtable()
	{
		eraseAllCells();
	}
	
	public boolean isReadOnly()
	{
		return false;
	}

	public GridCell createCell(GridCellID cellID) 
	{
		int ID = cellID.getCellID();
		
		GridCell cell = m_mapCells.get(ID);
		
		if (cell == null)
		{
			cell = new GridCell();
			
			m_mapCells.put(ID, cell);
		}
		
		return cell;
	}

	public GridCell getCell(GridCellID cellID) 
	{
		return m_mapCells.get(cellID.getCellID());
	}

	public boolean putCell(GridCellID cellID, GridCell cell) 
	{
		m_mapCells.put(cellID.getCellID(), cell);
		
		return true;
	}

	public boolean eraseCell(GridCellID cellID) 
	{
		m_mapCells.remove(cellID.getCellID());
		
		return true;
	}

	public boolean eraseAllCells()
	{
		m_iRowCount = 0;
		m_iColCount = 0;
		
		m_mapCells = new GridCellHashtable(24);
		
		return true;
	}

	public GridCellHashtable getRowCells(int iRow)
	{
		GridCellHashtable gcht = new GridCellHashtable((int) (m_iColCount / 4));
		GridCellID cellID = new GridCellID(iRow, -1);
		GridCell cell;
		
		for (int i = 0; i < m_iColCount; i++)
		{
			cellID.Col = i;
			
			cell = m_mapCells.get(cellID.getCellID());
			
			if (cell != null)
			{
				gcht.put(i, cell);
			}
		}
		
		return gcht;
	}
	
	public void putRowCells(int iRow, GridCellHashtable gcht)
	{
		if ((iRow > -1) && (iRow < m_iRowCount))
		{
			GridCellID cellID = new GridCellID(iRow, -1);
			IntVector vecKeys = gcht.getKeys();
			int iCount = vecKeys.getCount();
			
			for (int i = 0; i < iCount; i++)
			{
				cellID.Col = vecKeys.items[i];
				
				if ((cellID.Col > -1) && (cellID.Col < m_iColCount))
				{
					m_mapCells.put(cellID.getCellID(), gcht.get(cellID.Col));
				}
			}
		}
	}

	public void eraseRowCells(int iRow)
	{
		if ((iRow > -1) && (iRow < m_iRowCount))
		{
			GridCellID cellID = new GridCellID(iRow, -1);
			
			for (cellID.Col = 0; cellID.Col < m_iColCount; cellID.Col++)
			{
				m_mapCells.remove(cellID.getCellID());
			}	
		}
	}
	
	public GridCellHashtable getColCells(int iCol)
	{
		GridCellHashtable gcht = new GridCellHashtable((int) (m_iRowCount / 4));
		GridCellID cellID = new GridCellID(-1, iCol);
		GridCell cell;
		
		for (int i = 0; i < m_iRowCount; i++)
		{
			cellID.Row = i;
			
			cell = m_mapCells.get(cellID.getCellID());
			
			if (cell != null)
			{
				gcht.put(i, cell);
			}
		}
		
		return gcht;
	}
	
	public void putColCells(int iCol, GridCellHashtable gcht)
	{
		if ((iCol > -1) && (iCol < m_iColCount))
		{
			GridCellID cellID = new GridCellID(-1, iCol);
			IntVector vecKeys = gcht.getKeys();
			int iCount = vecKeys.getCount();
			
			for (int i = 0; i < iCount; i++)
			{
				cellID.Row = vecKeys.items[i];
				
				if ((cellID.Row > -1) && (cellID.Row < m_iRowCount))
				{
					m_mapCells.put(cellID.getCellID(), gcht.get(cellID.Row));
				}
			}
		}
	}

	public void eraseColCells(int iCol)
	{
		if ((iCol > -1) && (iCol < m_iColCount))
		{
			GridCellID cellID = new GridCellID(-1, iCol);
			
			for (cellID.Row = 0; cellID.Row < m_iRowCount; cellID.Row++)
			{
				m_mapCells.remove(cellID.getCellID());
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
		
		GridCellID cellID = new GridCellID(iRow, 0);
		GridCell cell;
		String strText;
		int iID;
		
		for (cellID.Col = 0; cellID.Col < iMax; cellID.Col++)
		{
			iID = cellID.getCellID();

			cell = m_mapCells.get(iID);
			strText = strCellsText[cellID.Col];
			
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

					m_mapCells.put(iID, cell);
				}
				
				cell.m_strCellText = strText;
			}			
		}
	}

	public String [] getRowStrings(int iRow)
	{
		String [] ret = new String [m_iColCount];
		GridCellID cellID = new GridCellID(iRow, 0);
		GridCell cell;
		
		for (cellID.Col = 0; cellID.Col < m_iColCount; cellID.Col++)
		{
			try
			{
				cell = m_mapCells.get(cellID.getCellID());
			
				ret[cellID.Col] = cell.m_strCellText;
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
			
			m_mapCells = new GridCellHashtable(24);
		}
		else
		{
			GridCellID readID = new GridCellID();
			GridCellID writeID = new GridCellID();
			
			GridCell cell;
			
			readID.Row = iRowEnd + 1;
			writeID.Row = iRowStart;
			
			while (readID.Row <= iRowCount)
			{
				for (int c = 0; c < m_iColCount; c++)
				{
					readID.Col = c;
					writeID.Col = c;
					
					cell = m_mapCells.get(readID.getCellID());
					
					if (cell == null)
					{
						m_mapCells.remove(writeID.getCellID());
					}
					else
					{
						m_mapCells.put(writeID.getCellID(), cell);
					}
				}
				
				readID.Row++;
				writeID.Row++;
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
		
		GridCellID cellID = new GridCellID(0, iCol);
		GridCell cell;
		String strText;
		int iID;
		
		for (cellID.Row = 0; cellID.Row < iMax; cellID.Row++)
		{
			iID = cellID.getCellID();

			cell = m_mapCells.get(iID);
			strText = strCellsText[cellID.Row];
		
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

					m_mapCells.put(iID, cell);
				}
				
				cell.m_strCellText = strText;
			}			
		}
	}

	public String [] getColStrings(int iCol)
	{
		String [] ret = new String [m_iRowCount];
		GridCellID cellID = new GridCellID(0, iCol);
		GridCell cell;
		
		for (cellID.Row = 0; cellID.Row < m_iRowCount; cellID.Row++)
		{
			try
			{
				cell = m_mapCells.get(cellID.getCellID());
			
				ret[cellID.Row] = cell.m_strCellText;
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
			
			m_mapCells = new GridCellHashtable(24);
		}
		else
		{
			GridCellID readID = new GridCellID();
			GridCellID writeID = new GridCellID();
			
			GridCell cell;
			
			readID.Col = iColEnd + 1;
			writeID.Col = iColStart;
			
			while (readID.Col <= iColCount)
			{
				for (int r = 0; r < m_iRowCount; r++)
				{
					readID.Row = r;
					writeID.Row = r;
					
					cell = m_mapCells.get(readID.getCellID());
					
					if (cell == null)
					{
						m_mapCells.remove(writeID.getCellID());
					}
					else
					{
						m_mapCells.put(writeID.getCellID(), cell);
					}
				}
				
				readID.Col++;
				writeID.Col++;
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
		GridCellID cellID = new GridCellID();
		
		for (int r = iRowStart; r < iRowEnd; r++)
		{
			cellID.Row = r;
			
			for (int c = 0; c < m_iRowCount; c++)
			{
				cellID.Col = c;
				
				m_mapCells.remove(cellID.getCellID());
			}
		}
	}
	
	private void _removePendingCols(int iColStart, int iColEnd)
	{
		GridCellID cellID = new GridCellID();
		
		for (int c = iColStart; c < iColEnd; c++)
		{
			cellID.Col = c;
			
			for (int r = 0; r < m_iRowCount; r++)
			{
				cellID.Row = r;
				
				m_mapCells.remove(cellID.getCellID());
			}
		}
	}
}
