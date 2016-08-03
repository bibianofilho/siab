/* 
 * WExtLib - A Component Library for the Superwaba Virtual Machine
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

package wextlib.sql;


public class Column
{
    /** Column name */
	public String Name;
	/** Column type (see Column.TYPE_*) */
	public int Type;
	/** Size of the field */
	public int Size;
	/** Indicator of nullable column */
	public boolean Nullable;
    /** Default value */
	public String DefaultValue;

	/** Unknown field type */
	public static final int TYPE_UNKNOWN = 0;
	/** String field type */
	public static final int TYPE_STRING = 1;
	/** Integer field type */
	public static final int TYPE_INT = 2;
	/** Long field type */
	public static final int TYPE_DOUBLE = 3;
	/** Blob field type */
	public static final int TYPE_BLOB = 4;
	/** Date/Time field type */
	public static final int TYPE_DATETIME = 5;
	
	/**
	 * Default constructor
	 */
	public Column()
	{
		/* 
		Optimization trick: JAVA doesn't need to ZERO any variable
		as you must have to do in C++ or Pascal, so don't do this.
		
		Name = "";
		Type = Column.TYPE_UNKNOWN;
		Size = 0;
		Nullable = true;
		DefaultValue = null;
		*/
	}
}
