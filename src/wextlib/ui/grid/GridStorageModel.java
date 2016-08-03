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

/**
 * This abstract class defines a storage model for the grid control
 * 
 * @author Virgilio Alexandre Fornazin (mailto:virgiliofornazin@gmail.com)
 */
public abstract class GridStorageModel 
{
	/**
	 * Returns if this storage model is read-only
	 */
	public abstract boolean isReadOnly();
	
	/**
	 * Create a grid cell if it doesn't exists
	 */
	public abstract GridCell createCell(GridCellID cellID);

	/**
	 * Get a grid cell
	 */
	public abstract GridCell getCell(GridCellID cellID);

	/**
	 * Put a grid cell
	 */
	public abstract boolean putCell(GridCellID cellID, GridCell cell);

	/**
	 * Erase a grid cell
	 */
	public abstract boolean eraseCell(GridCellID cellID);

	/**
	 * Erase all cells
	 */
	public abstract boolean eraseAllCells();
	
	/**
	 * Get the row cells
	 */
	public abstract GridCellHashtable getRowCells(int iRow);
	
	/**
	 * Put the row cells
	 */
	public abstract void putRowCells(int iRow, GridCellHashtable gcht);

	/**
	 * Erase the row cells
	 */
	public abstract void eraseRowCells(int iRow);
	
	/**
	 * Get the row cells
	 */
	public abstract GridCellHashtable getColCells(int iCol);
	
	/**
	 * Put the col cells
	 */
	public abstract void putColCells(int iCol, GridCellHashtable gcht);

	/**
	 * Erase the col cells
	 */
	public abstract void eraseColCells(int iCol);
	 
	/**
	 * Get row count
	 */
	public abstract boolean setRowCount(int iRowCount);
	
	/**
	 * Set row count
	 */
	public abstract int getRowCount();
	
	/**
	 * Set the string array elements into specified row cell큦
	 * (NOTE that if the cell object doesn't exists previously, it will 
	 *  create it as a GridCell instance, which is readonly)
	 */
	public abstract void setRowStrings(int iRow, String [] strCellsText);

	/**
	 * Get the string array elements from specified row cell큦
	 */
	public abstract String [] getRowStrings(int iRow);
	
	/**
	 * Remove rows from storage and returns the new row count
	 */
	public abstract int removeRows(int iRowStart, int iRowEnd);

	/**
	 * Swap a row with another row
	 */
	public boolean swapRows(int iRow1, int iRow2)
	{
		try
		{
			GridCellID cellID = new GridCellID();
			GridCell cellSource;
			GridCell cellDest;
			
			int iCols = getColCount();
			
			for (cellID.Col = 0; cellID.Col < iCols; cellID.Col++)
			{
				cellID.Row = iRow1;
				cellSource = getCell(cellID);
				
				cellID.Row = iRow2;
				cellDest = getCell(cellID);
				
				cellID.Row = iRow1;
				putCell(cellID, cellDest);
				
				cellID.Row = iRow2;
				putCell(cellID, cellSource);
			}
			
			return true;
		}
		catch (Exception e)
		{
			return false;
		}		
	}
	
	/**
	 * Get column count
	 */
	public abstract boolean setColCount(int iColCount);

	/**
	 * Set column count
	 */
	public abstract int getColCount();
	
	/**
	 * Set the string array elements into specified column cell큦
	 * (NOTE that if the cell object doesn't exists previously, it will 
	 *  create it as a GridCell instance, which is readonly)
	 */
	public abstract void setColStrings(int iCol, String [] strCellsText);
	/**
	 * Get the string array elements from specified column cell큦
	 */
	public abstract String [] getColStrings(int iCol);
	
	/**
	 * Remove columns from storage the new column count
	 */
	public abstract int removeCols(int iColStart, int iColEnd);

	/**
	 * Swap a column with another column
	 */
	public boolean swapCols(int iCol1, int iCol2)
	{
		try
		{
			GridCellID cellID = new GridCellID();
			GridCell cellSource;
			GridCell cellDest;
			
			int iRows = getRowCount();
			
			for (cellID.Row = 0; cellID.Row < iRows; cellID.Row++)
			{
				cellID.Col = iCol1;
				cellSource = getCell(cellID);
				
				cellID.Col = iCol2;
				cellDest = getCell(cellID);
				
				cellID.Col = iCol1;
				putCell(cellID, cellDest);
				
				cellID.Col = iCol2;
				putCell(cellID, cellSource);
			}
			
			return true;
		}
		catch (Exception e)
		{
			return false;
		}
	}
}
