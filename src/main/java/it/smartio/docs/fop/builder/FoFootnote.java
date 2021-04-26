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

import it.smartio.docs.fop.template.FoFont;

/**
 * The {@link FoFootnote} class.
 */
public class FoFootnote extends FoNode implements FoFont<FoFootnote> {

	private final FoBlock body;

	/**
	 * Constructs an instance of {@link FoFootnote}.
	 *
	 * @param id
	 */
	public FoFootnote(String id) {
		super("fo:footnote");
		FoBlock inline = FoBlock.inline();
		addNode(inline);
		inline.set("baseline-shift", "super").set("font-size", "smaller").addNode(FoNode.text(id));

		FoNode content = new FoNode("fo:footnote-body");
		addNode(content);
		this.body = FoBlock.block();
		content.addNode(this.body);

		inline = FoBlock.inline();
		inline.set("baseline-shift", "super").set("font-size", "smaller").addNode(FoNode.text(id));
		this.body.addNode(inline);
	}

	/**
	 * Get the footnote body.
	 */
	public FoBlock getBody() {
		return this.body;
	}
}
