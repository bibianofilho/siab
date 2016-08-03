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

/**
 * This class implements string search and replace
 * 
 * @author Virgilio Alexandre Fornazin
 */
public class StringReplacer
{
	/**
	 * Replace a string pattern with a new pattern
	 */
	public static String replaceString(final String strInput, final String strOldPattern, final String strNewPattern)
	{
		if (strOldPattern.equals(""))
		{
			return strInput;
		}
	
		final StringBuffer result = new StringBuffer();

		int startIdx = 0;
		int idxOld = 0;
		
		while ((idxOld = strInput.indexOf(strOldPattern, startIdx)) >= 0)
		{
			result.append(strInput.substring(startIdx, idxOld));
			result.append(strNewPattern);
			
			startIdx = idxOld + strOldPattern.length();
		}

		result.append(strInput.substring(startIdx));
		
		return result.toString();
	}
}
