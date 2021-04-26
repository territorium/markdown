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

package it.smartio.docs.markdown.markers;

import org.commonmark.node.Node;
import org.commonmark.node.Text;
import org.commonmark.parser.delimiter.DelimiterProcessor;
import org.commonmark.parser.delimiter.DelimiterRun;

import it.smartio.docs.markdown.markers.Marker.Decoration;

class MarkerProcessor implements DelimiterProcessor {

	private final int length;
	private final char character;
	private final Decoration decoration;

	/**
	 * Constructs an instance of {@link MarkerProcessor}.
	 */
	public MarkerProcessor() {
		this(2, '/', Decoration.Highlight);
	}

	/**
	 * Constructs an instance of {@link MarkerProcessor}.
	 *
	 * @param length
	 * @param character
	 * @param decoration
	 */
	protected MarkerProcessor(int length, char character, Decoration decoration) {
		this.length = length;
		this.character = character;
		this.decoration = decoration;
	}

	@Override
	public final int getMinLength() {
		return this.length;
	}

	@Override
	public final char getOpeningCharacter() {
		return this.character;
	}

	@Override
	public final char getClosingCharacter() {
		return this.character;
	}

	/*
	 * @see
	 * org.commonmark.parser.delimiter.DelimiterProcessor#process(org.commonmark.
	 * parser.delimiter. DelimiterRun, org.commonmark.parser.delimiter.DelimiterRun)
	 */
	@Override
	public final int process(DelimiterRun openingRun, DelimiterRun closingRun) {
		if ((openingRun.length() < getMinLength()) || (closingRun.length() < getMinLength())) {
			return 0;
		}

		// Use exactly two delimiters even if we have more, and don't care about
		// internal
		// openers/closers.
		Text opener = openingRun.getOpener();
		Text closer = closingRun.getCloser();

		// Wrap nodes between delimiters in marker.
		Node textDecoration = new Marker(this.decoration);

		Node tmp = opener.getNext();
		while ((tmp != null) && (tmp != closer)) {
			Node next = tmp.getNext();
			textDecoration.appendChild(tmp);
			tmp = next;
		}

		opener.insertAfter(textDecoration);
		return getMinLength();
	}
}
