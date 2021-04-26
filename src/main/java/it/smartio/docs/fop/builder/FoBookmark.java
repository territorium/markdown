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

package it.smartio.docs.fop.builder;

/**
 * The {@link FoBookmark} class.
 */
public class FoBookmark extends FoNode {

	private final FoNode title;

	/**
	 * Constructs an instance of {@link FoBookmark}.
	 *
	 * @param name
	 */
	public FoBookmark(String id) {
		super("fo:bookmark");
		set("internal-destination", id);
		set("starting-state", "hide");

		this.title = FoNode.of("fo:bookmark-title");
		addNode(this.title);
	}

	/**
	 * Set the title of the bookmark.
	 *
	 * @param title
	 */
	public FoBookmark setTitle(String title) {
		this.title.addText(title);
		return this;
	}

	/**
	 * Add a child bookmark.
	 *
	 * @param id
	 */
	public FoBookmark addBookmark(String id) {
		FoBookmark builder = new FoBookmark(id);
		addNode(builder);
		return builder;
	}
}
