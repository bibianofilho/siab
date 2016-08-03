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

package wextlib.util;

import waba.util.*;
import waba.io.*;

/**
 * This class is basically a waba.util.IntVector with new methods for finding
 * items
 * 
 * @author Virgilio Alexandre Fornazin
 */
public class IntVectorExtended extends IntVector 
{
	public IntVectorExtended()
	{
		super();
	}

	public IntVectorExtended(DataStream in)
	{
		super(in);
	}

	public IntVectorExtended(int size) 
	{
		super(size);
	}
	
	/** 
	 * Inserts an object at the given index and grow array if necessary
	 */
	public void insertAtGrow(int index, int val)
	{
		int count = size();
		
		if (index >= count)
		{
			int dif = (index - count) + 1;
			
			while (dif > 0)
			{
				add(0);
				dif--;
			}
		}

	    items[index] = val;
	}
	
	/**
	 * Do a binary search in the vector (note that vector must had been
	 * sorted by qsort() or another sort method prior calling this)
	 */
	public int bSearch(int obj)
	{
		int count = size();
		
		if (count > 0)
		{
		    int left = 0;
		    int right = count;
		    int pos;
		    int val;
		    
			while (left != right - 1)
			{
				pos = (right + left) / 2;
				val = items[pos];
				
				if (val < obj)
				{
					left = pos;
				}
				else if (val > obj)
				{
					right = pos;
				}
				else
				{
					return pos;
				}
			}
		}
		
		return IntVector.INVALID;
	}
	
	/**
	 * Do a left-to-right linear search in the vector (note that vector must
	 * had been sorted by qsort() or another sort method prior calling this)
	 */
	public int lFind(int obj)
	{
		int count = size();
		
		for (int i = 0; i < count; i++)
		{
			if (items[i] == obj)
			{
				return i;
			}
		}
		
		return IntVector.INVALID;
	}
	
	/**
	 * Do a right-to-left linear search in the vector (note that vector must
	 * had been sorted by qsort() or another sort method prior calling this)
	 */
	public int rFind(int obj)
	{
		int count = size();
		
		for (int i = count - 1; i > -1; i--)
		{
			if (items[i] == obj)
			{
				return i;
			}
		}
		
		return IntVector.INVALID;
	}
}