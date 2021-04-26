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

package it.smartio.docs.builder;

import java.util.HashMap;
import java.util.Map;

import it.smartio.docs.Book;
import it.smartio.docs.Chapter;
import it.smartio.docs.Node;

/**
 * The {@link BookBuilder} class.
 */
public class BookBuilder extends PageBuilder implements Book {

	private int prefaceOffset = 0;
	private int chapterOffset = 0;
	private final Map<String, String> identifiers = new HashMap<>();

	/**
	 * Constructs an instance of {@link BookBuilder}.
	 */
	public BookBuilder() {
		super(0, null);
	}

	@Override
	public final int getOffset() {
		return -1;
	}

	@Override
	public PageBuilder addSection() {
		return add(new ChapterBuilder(this, 2, this.prefaceOffset++));
	}

	public PageBuilder addChapter() {
		return add(new ChapterBuilder(this, 1, this.chapterOffset++));
	}

	private final String getId(String key) {
		String id = key.toLowerCase().replace(" ", "-").substring(1);
		return this.identifiers.containsKey(id) ? this.identifiers.get(id) : key;
	}

	@Override
	protected void addIndex(Chapter node) {
		if (!node.getTitle().isEmpty()) {
			String title = node.getTitle().toLowerCase().replace(" ", "-");
			this.identifiers.put(title, node.getId());
		}
	}

	private void processNode(Node node) {
		if (node instanceof LinkBuilder) {
			LinkBuilder link = (LinkBuilder) node;
			if (link.getLink().startsWith("#")) {
				link.setLink(getId(link.getLink()));
			}
		}
		node.nodes().forEach(this::processNode);
	}

	public final Book build() {
		processNode(this);
		return this;
	}
}
