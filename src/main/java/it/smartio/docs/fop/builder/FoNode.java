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

import it.smartio.docs.fop.FoBuilder;

/**
 * The {@link FoNode} creates a simple text content.
 */
public class FoNode extends FoBuilder {

	private final String name;

	/**
	 * Constructs an instance of {@link FoNode}.
	 *
	 * @param name
	 */
	protected FoNode(String name) {
		this.name = name;
	}

	/**
	 * Get the name.
	 */
	protected final String getTagName() {
		return this.name;
	}

	/**
	 * Set the ID.
	 *
	 * @param id
	 */
	public final FoBuilder setId(String id) {
		return set("id", id);
	}

	/**
	 * Add a new child text {@link FoNode}.
	 *
	 * @param text
	 */
	public final void addText(String text) {
		addNode(FoNode.text(text));
	}

	/**
	 * Build the String.
	 */
	@Override
	public String build() {
		StringBuffer buffer = new StringBuffer();
		if (hasChildren()) {
			FoUtil.writeStart(buffer, this.name, getAttributes());
			forEach(n -> buffer.append(n.build()));
			FoUtil.writeEnd(buffer, this.name);
		} else {
			FoUtil.writeEmpty(buffer, this.name, getAttributes());
		}
		return buffer.toString();
	}

	/**
	 * Creates a new instance of a {@link FoRoot}.
	 */
	public static FoRoot root(String font) {
		return new FoRoot(font);
	}

	/**
	 * Constructs a text {@link FoNode}.
	 *
	 * @param text
	 */
	public static FoNode text(String text) {
		return new FoNodeText(text);
	}

	/**
	 * Creates a new instance of a {@link FoNode}.
	 *
	 * @param name
	 */
	public static FoNode of(String name) {
		return new FoNode(name);
	}

	/**
	 * The {@link FoNodeText} implements a {@link FoNode}, that defines a simple
	 * text based on the node name.
	 */
	private static class FoNodeText extends FoNode {

		/**
		 * Constructs an instance of {@link FoNodeText}.
		 *
		 * @param name
		 */
		private FoNodeText(String name) {
			super(name);
		}

		/**
		 * Build the String version of the {@link FoNode}.
		 */
		@Override
		public final String build() {
			return getTagName();
		}
	}
}
