/*
 * Copyright (c) 2001-2021 Territorium Online Srl / TOL GmbH. All Rights Reserved.
 *
 * This file contains Original Code and/or Modifications of Original Code as defined in and that are
 * subject to the Territorium Online License Version 1.0. You may not use this file except in
 * compliance with the License. Please obtain a copy of the License at http://www.tol.info/license/
 * and read it before using this file.
 *
 * The Original Code and all software distributed under the License are distributed on an 'AS IS'
 * basis, WITHOUT WARRANTY OF ANY KIND, EITHER EXPRESS OR IMPLIED, AND TERRITORIUM ONLINE HEREBY
 * DISCLAIMS ALL SUCH WARRANTIES, INCLUDING WITHOUT LIMITATION, ANY WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE, QUIET ENJOYMENT OR NON-INFRINGEMENT. Please see the License for
 * the specific language governing rights and limitations under the License.
 */

package it.smartio.docs.util;

import it.smartio.docs.Chapter;

/**
 * The {@link PageUtil} class.
 */
public abstract class PageUtil {

	private static final int ARAB[] = { 1000, 990, 900, 500, 490, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1 };
	private static final String ROMAN[] = { "M", "XM", "CM", "D", "XD", "CD", "C", "XC", "L", "XL", "X", "IX", "V",
			"IV", "I" };

	/**
	 * Encode the text to avoid XML reserved characters.
	 *
	 * @param text
	 */
	public static String encode(String text) {
		return text.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;");
	}

	/**
	 * Get the page index number.
	 *
	 * @param page
	 */
	public static String getPageNumber(Chapter page) {
		String number = Integer.toString(page.getOffset() + 1);
		return (page.getParent().getOffset() < 0) ? number
				: String.format("%s.%s", PageUtil.getPageNumber(page.getParent()), number);
	}

	/**
	 * Get the preface index number.
	 *
	 * @param page
	 */
	public static String getRomanNumber(Chapter page) {
		StringBuilder result = new StringBuilder();
		int number = page.getOffset() + 1;
		int i = 0;
		while ((number > 0) || (PageUtil.ARAB.length == (i - 1))) {
			while ((number - PageUtil.ARAB[i]) >= 0) {
				number -= PageUtil.ARAB[i];
				result.append(PageUtil.ROMAN[i]);
			}
			i++;
		}
		return result.toString();
	}
}
