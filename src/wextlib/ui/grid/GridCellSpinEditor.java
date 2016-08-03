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
 * This class is a grid cell editor for Spin edit
 * 
 * @author Virgilio Alexandre Fornazin (mailto:virgiliofornazin@gmail.com)
 */
public class GridCellSpinEditor extends GridCellEditor
{
	public boolean onStartEdit(Grid grid, GridCell cell, GridCellID cellID, Rect cellRect, Color clrForeColor, Color clrBackColor, Font cellFont, int chrCode)
	{
		if (!super.onStartEdit(grid, cell, cellID, cellRect, clrForeColor, clrBackColor, cellFont, chrCode))
		{
			return false;
		}
		
		GridCellSpinEditorCtrl edit = new GridCellSpinEditorCtrl();
		
		m_EditGrid.add(edit);
		m_EditControl = edit;
		
		edit.setVisible(false);
		edit.setRect(m_EditRect);

		if (m_EditBackColor != null)
		{
			edit.setBackColor(m_EditBackColor);
		}
		
		if (m_EditForeColor != null)
		{
			edit.setForeColor(m_EditForeColor);
		}
		
		if (m_EditFont != null)
		{
			edit.setFont(m_EditFont);
		}
		
		edit.setPrecision(((GridCellSpin) cell).m_iPrecision);
		edit.setDecimals(((GridCellSpin) cell).m_iDecimals);
		edit.setDouble(((GridCellSpin) cell).getDouble(), 
				((GridCellSpin) cell).m_iPrecision, 
				((GridCellSpin) cell).m_iDecimals);
		edit.setIncrementDecrementStep(((GridCellSpin) cell).m_dIncrementDecrementStep);		
		
		edit.setVisible(true);
		edit.setCursorPos(0, edit.getText().length());
		edit.requestFocus();
		
		m_EditControl = edit;
		
		return true;
	}
	
	public boolean onEndEdit()
	{
		if (m_EditControl == null)
		{
			return false;
		}
		
		GridCellSpinEditorCtrl edit = (GridCellSpinEditorCtrl) m_EditControl;

		((GridCellSpin) m_EditCell).setDouble(edit.getDouble());
		m_EditGrid.remove(edit);
		
		edit.setVisible(false);
		edit = null;
		
		m_EditControl = null;
		
		return true;
	}
}
