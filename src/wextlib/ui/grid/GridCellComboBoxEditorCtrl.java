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

import waba.ui.*;

/**
 * This class provides a editor for GridCellComboBox.
 * 
 * @author Virgilio Alexandre Fornazin (mailto:virgiliofornazin@gmail.com)
 */
public final class GridCellComboBoxEditorCtrl extends ComboBox 
{
	private boolean m_bPoppedUp;
	
	public GridCellComboBoxEditorCtrl()
	{
		m_bPoppedUp = false;
	}
	
	public GridCellComboBoxEditorCtrl(Object [] items)
	{
		super(items);
		
		m_bPoppedUp = false;
	}
	
	public void popupPop()
	{
		m_bPoppedUp = true;
		
		super.popupPop();
		
		requestFocus();
	}
	
	public void onEvent(Event e)
	{
		if (e.type == ControlEvent.FOCUS_OUT)
		{
			if (m_bPoppedUp)
			{
				m_bPoppedUp = false;
				e.consumed = true;
			}
		}
		
		if (!e.consumed)
		{
			super.onEvent(e);
		}
	}
}
