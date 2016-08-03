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

import waba.sys.*;

/**
 * This class implements number formatting routines. 
 * 
 * @author Virgilio Alexandre Fornazin
 */
public class NumberFormat 
{
	/** Brazilian Real prefix */
	public static final String BRL_PREFIX = "R$";
	/** U.S. Dollar prefix */
	public static final String USD_PREFIX = "US$";
	
	/**
	 * Format a number to Brazilian Real (BRL) format 
	 */
	public static String formatBRL(double dblNumber)
	{
		return formatNumber(BRL_PREFIX, dblNumber, 2);
	}
	
	/**
	 * Format a number to U.S. Dollars (USD) format 
	 */
	public static String formatUSD(double dblNumber)
	{
		return formatNumber(USD_PREFIX, dblNumber, 2);
	}
	
	/** 
	 * Format a number whitout prefix
	 */
	public static String formatNumber(double dblNumber, int iDecimals)
	{
		return formatNumber(null, dblNumber, iDecimals);
	}
	
	/**
	 * Format a integer
	 */
	public static String formatInteger(String strPrefix, int iNumber)
	{
		return formatNumber(strPrefix, (double) iNumber, 0);
	}
	
	/**
	 * Format a integer without a prefix
	 */
	public static String formatInteger(int iNumber)
	{
		return formatNumber(null, (double) iNumber, 0);
	}
	
	/** 
	 * Format a number putting a prefix before it
	 */
	public static String formatNumber(String strPrefix, double dblNumber, int iDecimals)
	{
		char chrCurrent;
		char [] chrNumber;
		char [] chrPrefix;
		char [] chrOutput;
		int iDigitsBefore;
		int iSeparatorCount;
		int iSeparatorStep;
		int iOutput;
		int iLength;
		int iLengthPrefix;
		boolean bInDecimals;
		String strNumber;

		strNumber = Convert.toString(dblNumber, iDecimals);
		
		chrNumber = strNumber.toCharArray();
		iLength = chrNumber.length;
		
		if (strPrefix != null)
		{
			chrPrefix = strPrefix.toCharArray();
			iLengthPrefix = strPrefix.length();
		}
		else
		{
			chrPrefix = null;
			iLengthPrefix = 0;
		}
		
		iDigitsBefore = strNumber.lastIndexOf('.');
		
		if (iDigitsBefore == -1)
		{
			iDigitsBefore = iLength;
		}
		
		if (iDigitsBefore < 4)
		{
			iSeparatorStep = 0;
			iSeparatorCount = 0;
		}
		else
		{
			iSeparatorStep = (iDigitsBefore % 3);
			iSeparatorCount = ((iDigitsBefore - iSeparatorStep) / 3) - (iSeparatorStep == 0 ? 1 : 0); 
		}
		
		chrOutput = new char [iLength + iSeparatorCount + ((iLengthPrefix > 0) ? iLengthPrefix + 1 : 0)];
		
		if (iLengthPrefix > 0)
		{
			for (int i = 0; i < iLengthPrefix; i++)
			{
				chrOutput[i] = chrPrefix[i];
			}
			
			chrOutput[iLengthPrefix] = ' ';
			iOutput = iLengthPrefix + 1;
		}
		else
		{
			iOutput = 0;
		}
		
		bInDecimals = false;
		
		for (int i = 0; i < iLength; i++)
		{
			chrCurrent = chrNumber[i];
			
			if (!bInDecimals)
			{
				if (chrCurrent == '.')
				{
					chrOutput[iOutput] = Settings.decimalSeparator;
					iOutput++;
					
					bInDecimals = true;
					
					continue;
				}
				else if ((iSeparatorStep == 0) && (iSeparatorCount > 0))
				{
					if (i > 0)
					{
						chrOutput[iOutput] = Settings.thousandsSeparator;
						iOutput++;
					}
					
					iSeparatorStep = 2;
				}
				else
				{
					iSeparatorStep--;
				}
			}

			chrOutput[iOutput] = chrCurrent;
			iOutput++;
		}
		
		return new String(chrOutput);
	}
	
	/** 
	 * Parse a formatted number putting it back to a double
	 */
	public static double parseNumber(String strNumber)
	{
		char [] arrnumber = strNumber.toCharArray();
		int iLength = arrnumber.length;
		int iCount = 0;
		char chrCode;
		char chrSeparator = 0;
		char [] arrout = new char [iLength];
		int iLastIndexOfDot = strNumber.lastIndexOf('.');
		int iLastIndexOfComma = strNumber.lastIndexOf(',');

		if (iLastIndexOfDot > iLastIndexOfComma)
		{
			chrSeparator = strNumber.charAt(iLastIndexOfDot);
		}
		else if (iLastIndexOfComma > iLastIndexOfDot)
		{
			chrSeparator = strNumber.charAt(iLastIndexOfComma);
		}
		
		for (int i = 0; i < iLength; i++)
		{
			chrCode = arrnumber[i];
			
			if ((('0' <= chrCode) && (chrCode <= '9')) || (chrCode == '+') || (chrCode == '-'))
			{
				arrout[iCount] = chrCode;
				iCount++;
			}
			else if (chrCode == chrSeparator)
			{
				arrout[iCount] = '.';
				iCount++;
			}
		}
		
		return Convert.toDouble(new String(arrout, 0, iCount));
	}
}
