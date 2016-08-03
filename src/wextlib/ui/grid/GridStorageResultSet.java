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

import wextlib.sql.*;
import waba.util.*;

/**
 * This class implements an array-based storage model with on-demand fetching
 * of the qrfSQL ResultSet for the grid control. Use this storage model when 
 * you want to load the contents of the resultset to the grid control on-demand
 * only.
 * 
 * @author Virgilio Alexandre Fornazin (mailto:virgiliofornazin@gmail.com)
 */
public final class GridStorageResultSet extends GridStorageModel 
{
	/** Model rows count */
	private int m_iRowCount;
	/** Model cols count */
	private int m_iColCount;
	/** Cell's array */
	private GridCell [][] m_Cells;
	/** ResultSet loaded rows control */
	private boolean [] m_bLoadedRows;
	/** ResultSet loaded rows control */
	private int [] m_iLoadedRows;
	/** ResultSet instance */
	private ResultSet m_ResultSet;
	
	/**
	 * Default constructor
	 */
	public GridStorageResultSet(ResultSet rs) throws DBException
	{
		eraseAllCells();
		
		m_ResultSet = rs;
		
		try
		{
			GridCellID cellID;
			GridCell cell;
			
			setRowCount(rs.getRowCount() + 1);
			setColCount(rs.getColumnCount());
			
			cellID = new GridCellID(0, 0);
			
			for (cellID.Col = 0; cellID.Col < m_iColCount; cellID.Col++)
			{
				cell = createCell(cellID);
				cell.m_strCellText = rs.getColumn(cellID.Col).Name;
			}
			
			m_bLoadedRows[0] = true;
			m_iLoadedRows[0] = -1;
			
			for (int i = 1; i < m_iRowCount; i++)
			{
				m_iLoadedRows[i] = i - 1;
				m_bLoadedRows[i] = false;
			}
		}
		catch (DBException e)
		{
			m_ResultSet = null;
			
			eraseAllCells();
			
			throw e;
		}
	}

	/**
	 * Constructor with custom header support
	 */
	public GridStorageResultSet(ResultSet rs, String [] arrHeaders) throws DBException
	{
		eraseAllCells();
		
		m_ResultSet = rs;
		
		try
		{
			GridCellID cellID;
			GridCell cell;
			
			setRowCount(rs.getRowCount() + 1);
			setColCount(rs.getColumnCount());
			
			cellID = new GridCellID(0, 0);
			
			int l = arrHeaders.length;
			int i;
			int c;
			
			for (i = 0; ((i < l) && (i < m_iColCount)); i++)
			{
				cell = createCell(cellID);
				cell.m_strCellText = arrHeaders[i];

				cellID.Col++;
			}
			
			for (c = i; c < m_iColCount; c++)
			{
				cell = createCell(cellID);
				cell.m_strCellText = rs.getColumn(c).Name;

				cellID.Col++;
			}
			
			m_bLoadedRows[0] = true;
			m_iLoadedRows[0] = -1;
			
			for (i = 1; i < m_iRowCount; i++)
			{
				m_iLoadedRows[i] = i - 1;
				m_bLoadedRows[i] = false;
			}
		}
		catch (DBException e)
		{
			m_ResultSet = null;
			
			eraseAllCells();
			
			throw e;
		}
	}
	
	/** 
	 * Fetch a row from the resultset 
	 */
	private boolean fetchRow(int iRow)
	{
		try
		{
			GridCell cell;
						
			m_ResultSet.moveTo(m_iLoadedRows[iRow]);
	
			for (int c = 0; c < m_iColCount; c++)
			{
				cell = new GridCell();
				cell.m_strCellText = m_ResultSet.getAsString(c);
				
				m_Cells[iRow][c] = cell; 
			}
			
			m_bLoadedRows[iRow] = true;
		}
		catch (DBException e)
		{
			m_bLoadedRows[iRow] = false;
		}
		
		return m_bLoadedRows[iRow];
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
				cell.m_strCellText = "";
				
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
			if (!m_bLoadedRows[cellID.Row])
			{
				if (!fetchRow(cellID.Row))
				{
					return null;
				}
			}
			
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
			if (!m_bLoadedRows[iRow])
			{
				if (!fetchRow(iRow))
				{
					return gcht;
				}
			}
			
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
				if (!m_bLoadedRows[i])
				{
					if (!fetchRow(i))
					{
						return gcht;
					}
				}
				
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
		boolean [] bLoadedRows = null;
		int [] iLoadedRows = null;
		
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

			if ((m_bLoadedRows == null) || (iRowCount > m_bLoadedRows.length))
			{
				bLoadedRows = new boolean[iRowCount];
				iLoadedRows = new int[iRowCount];
				
				if (m_bLoadedRows != null)
				{
					for (int r = 0; r < iLoop; r++)
					{
						bLoadedRows[r] = m_bLoadedRows[r];
						iLoadedRows[r] = m_iLoadedRows[r];
					}
				}
				else
				{
					for (int r = 0; r < iLoop; r++)
					{
						bLoadedRows[r] = true;
						iLoadedRows[r] = -1;;
					}
				}
			}
			else
			{
				bLoadedRows = m_bLoadedRows;
				iLoadedRows = m_iLoadedRows;
			}
		}
			
		m_Cells = cells;
		m_bLoadedRows = bLoadedRows;
		m_iLoadedRows = iLoadedRows;
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
		
		m_bLoadedRows[iRow] = true;
		m_iLoadedRows[iRow] = -1;
		
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
		boolean [] bLoadedRows = null;
		int [] iLoadedRows = null;
		
		if ((iRowCount > 0) && (m_iColCount > 0))
		{
			int i;
			int ni;
			
			cells = new GridCell[iRowCount][m_iColCount];
			
			for (i = 0; i < iRowStart; i++)
			{
				for (int c = 0; c < m_iColCount; c++)
				{
					cells[i][c] = m_Cells[i][c];
				}
			}
			
			for (ni = iRowEnd + 1; ni < m_iRowCount; ni++)
			{
				for (int c = 0; c < m_iColCount; c++)
				{
					cells[i][c] = m_Cells[ni][c];
				}

				i++;
			}

			if ((m_bLoadedRows == null) || (iRowCount > m_bLoadedRows.length))
			{
				bLoadedRows = new boolean[iRowCount];
				iLoadedRows = new int[iRowCount];
				
				if (m_bLoadedRows != null)
				{
					for (int r = 0; r < iRowStart; r++)
					{
						bLoadedRows[r] = m_bLoadedRows[r];
						iLoadedRows[r] = m_iLoadedRows[r];
					}
				}
				else
				{
					for (int r = 0; r < iRowStart; r++)
					{
						bLoadedRows[r] = true;
						iLoadedRows[r] = -1;;
					}
				}
			}
			else
			{
				bLoadedRows = m_bLoadedRows;
				iLoadedRows = m_iLoadedRows;
				
				i = iRowStart;
			}
			
			if (m_bLoadedRows != null)
			{
				for (ni = iRowEnd + 1; ni < m_iRowCount; ni++)
				{
					bLoadedRows[i] = m_bLoadedRows[ni];
					iLoadedRows[i] = m_iLoadedRows[ni];

					i++;
				}
			}
			else
			{
			}
			
			if (ni < bLoadedRows.length)
			{
				i = bLoadedRows.length;
				
				for (; ni < i; ni++)
				{
					bLoadedRows[ni] = true;
					iLoadedRows[ni] = -1;
				}
			}
		}
			
		m_Cells = cells;
		m_bLoadedRows = bLoadedRows;
		m_iLoadedRows = iLoadedRows;
		m_iRowCount = iRowCount;

		return m_iRowCount;
	}

	public boolean setColCount(int iColCount) 
	{
		if (m_iColCount == iColCount)
		{
			return true;
		}
		
		GridCell [][] cells = null;
		boolean [] bLoadedRows = null;
		int [] iLoadedRows = null;
		
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
			
			if (m_bLoadedRows == null)
			{
				bLoadedRows = new boolean[m_iRowCount];
				iLoadedRows = new int[m_iRowCount];
				
				for (int i = 0; i < m_iRowCount; i++)
				{
					bLoadedRows[i] = true;
					iLoadedRows[i] = -1;
				}
			}
			else
			{
				bLoadedRows = m_bLoadedRows;
				iLoadedRows = m_iLoadedRows;
			}
		}
			
		m_Cells = cells;
		m_bLoadedRows = bLoadedRows;
		m_iLoadedRows = iLoadedRows;
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
			m_bLoadedRows[i] = true;
			m_iLoadedRows[i] = -1;

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
		boolean [] bLoadedRows = null;
		int [] iLoadedRows = null;
		
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
			
			if (m_bLoadedRows == null)
			{
				bLoadedRows = new boolean[m_iRowCount];
				iLoadedRows = new int[m_iRowCount];
				
				for (i = 0; i < m_iRowCount; i++)
				{
					bLoadedRows[i] = true;
					iLoadedRows[i] = -1;
				}
			}
			else
			{
				bLoadedRows = m_bLoadedRows;
				iLoadedRows = m_iLoadedRows;
			}
		}
			
		m_Cells = cells;
		m_bLoadedRows = bLoadedRows;
		m_iLoadedRows = iLoadedRows;
		m_iColCount = iColCount;
		
		return m_iColCount;
	}
}
