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
 * This class is a grid cell editor for a select-style (combobox) cell.
 * 
 * @author Virgilio Alexandre Fornazin (mailto:virgiliofornazin@gmail.com)
 */
public class GridCellComboBoxEditor extends GridCellEditor 
{
	public boolean onStartEdit(Grid grid, GridCell cell, GridCellID cellID, Rect cellRect, Color clrForeColor, Color clrBackColor, Font cellFont, int chrCode)
	{
		if (!super.onStartEdit(grid, cell, cellID, cellRect, clrForeColor, clrBackColor, cellFont, chrCode))
		{
			return false;
		}
		
		GridCellComboBoxEditorCtrl combo = new GridCellComboBoxEditorCtrl(((GridCellComboBox) cell).m_arrItems);
		
		m_EditGrid.add(combo);
		
		combo.setVisible(false);
		combo.setRect(m_EditRect);

		if (m_EditBackColor != null)
		{
			combo.setBackColor(m_EditBackColor);
		}
		
		if (m_EditForeColor != null)
		{
			combo.setForeColor(m_EditForeColor);
		}
		
		if (m_EditFont != null)
		{
			combo.setFont(m_EditFont);
		}
		
		combo.select(combo.indexOf(cell.getCellText()));

		combo.setVisible(true);
		combo.requestFocus();

		m_EditControl = combo;
		
		return true;
	}
	
	public boolean onEndEdit()
	{
		if (m_EditControl == null)
		{
			return false;
		}
		
		GridCellComboBoxEditorCtrl combo = (GridCellComboBoxEditorCtrl) m_EditControl;
		
		int iSel = combo.getSelectedIndex();
		String strCellText;
		
		if (iSel == -1)
		{
			strCellText = "";
		}
		else
		{
			strCellText = ((GridCellComboBox) m_EditCell).m_arrItems[iSel].toString();
		}
		
		m_EditCell.setCellText(strCellText);
		m_EditGrid.remove(combo);
		
		combo.setVisible(false);
		combo = null;
		
		m_EditControl = null;
		
		return true;
	}
}
