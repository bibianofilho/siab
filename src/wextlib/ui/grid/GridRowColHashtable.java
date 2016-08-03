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
 * This class implements a hashtable mapping integer values to GridCell objects.
 * It´s the same as waba.util.IntHashtable, except that values are 
 * GridCells, not integers. 
 * 
 * @author Virgilio Alexandre Fornazin
 */
public final class GridRowColHashtable 
{
	private static class Entry 
	{
		int key;
		GridCellHashtable value;
		Entry next;
	}
	
	private static int INVALID = -2147483648;
	
	private Entry table[];
	private transient int count;
	private int threshold;
	private float loadFactor;
	
	public GridRowColHashtable(int initialCapacity) 
	{
		this(initialCapacity, 0.75f);
	}

	public GridRowColHashtable(int initialCapacity, float fLoadFactor) 
	{
		if (initialCapacity <= 0)
		{
			initialCapacity = 5;
		}

		loadFactor = fLoadFactor;
		table = new Entry[initialCapacity];
		threshold = (int)(initialCapacity * loadFactor);
	}

	public void clear() 
	{
		Entry tab[] = table;
		
		for (int index = tab.length; --index >= 0; )
		{
			tab[index] = null;
		}
		
		count = 0;
	}

	public GridCellHashtable get(int key)
	{
		int index = (key & 0x7FFFFFFF) % table.length;
		
		for (Entry e = table[index] ; e != null ; e = e.next)
		{
			if (e.key == key)
			{
				return e.value;
			}
		}
		
		return null;
	}

	public IntVector getKeys() 
	{
		IntVector keys = new IntVector(size());
		
		if (table != null) 
		{
			int len = table.length;
			
			Entry entry = null;
			
			for (int i = 0; i < len; i++) 
			{
				entry = table[i];
				
				if (entry != null) 
				{
					keys.add(entry.key);

					while (entry.next != null) 
					{
						entry = entry.next;
					
						keys.add(entry.key);
					}
				}
			}
		}
		
		return keys;
	}
	
	public GridCellHashtable put(int key, GridCellHashtable value) 
	{
		if (key == INVALID || value == null)
		{
			return null;
		}

		Entry tab [] = table;
		
		int index = (key & 0x7FFFFFFF) % tab.length;
		
		for (Entry e = tab[index] ; e != null ; e = e.next)
		{
			if (e.key == key) 
			{
				GridCellHashtable old = e.value;
				e.value = value;
				
				return old;
			}
		}
		
		if (count >= threshold) 
		{
			rehash();
			
			return put(key, value);
		}
		
		Entry e = new Entry();

		e.key = key;
		e.value = value;
		e.next = tab[index];
		
		tab[index] = e;
		
		count++;
		
		return null;
	}

	protected void rehash()
	{
		int oldCapacity = table.length;

		Entry oldTable[] = table;
		
		int newCapacity = (((oldCapacity << 1) + oldCapacity) >> 1) + 1;
		
		if (waba.sys.Settings.onDevice)
		{
			if (oldCapacity == 16382)
			{
				newCapacity = 20000;
			}
			else
			{
				if (newCapacity > 16382) 
				{
					newCapacity = 16382;
				}
			}
		}
		
		Entry newTable[] = new Entry[newCapacity];
		
		threshold = (int) (newCapacity * loadFactor);
		table = newTable;
		
		for (int i = oldCapacity ; i-- > 0 ;)
		{
			for (Entry old = oldTable[i] ; old != null ; )
			{
				Entry e = old;
				old = old.next;
				
				int index = (e.key & 0x7FFFFFFF) % newCapacity;
				
				e.next = newTable[index];
				
				newTable[index] = e;
			}
		}
	}

	public GridCellHashtable remove(int key) 
	{
		Entry tab[] = table;
		
		int index = (key & 0x7FFFFFFF) % tab.length;
		
		for (Entry e = tab[index], prev = null ; e != null ; prev = e, e = e.next)
		{
			if (e.key == key) 
			{
				if (prev != null)
				{
					prev.next = e.next;
				}
				else
				{
					tab[index] = e.next;
				}
				
				count--;
				
				return e.value;
			}
		}
		
		return null;
	}

	public int size()
	{
		return count;
	}
}
