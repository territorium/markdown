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

import it.smartio.docs.fop.template.FoBackground;
import it.smartio.docs.fop.template.FoBorder;
import it.smartio.docs.fop.template.FoFont;
import it.smartio.docs.fop.template.FoPadding;

/**
 * The {@link FoTableCell} class.
 */
public class FoTableCell extends FoNode
		implements FoBorder<FoTableCell>, FoPadding<FoTableCell>, FoFont<FoTableCell>, FoBackground<FoTableCell> {

	/**
	 * Constructs an instance of {@link FoTableCell}.
	 */
	FoTableCell() {
		super("fo:table-cell");
	}

	public FoTableCell setDisplayAlign(String align) {
		set("display-align", align);
		return this;
	}

	public FoTableCell setRelativeAlign(String align) {
		set("relative-align", align);
		return this;
	}

	public FoTableCell setRowSpan(int span) {
		if (span > 1) {
			set("number-rows-spanned", "" + span);
		}
		return this;
	}

	public FoTableCell setColSpan(int span) {
		if (span > 1) {
			set("number-columns-spanned", "" + span);
		}
		return this;
	}

	public FoBlock addBlock() {
		FoBlock block = FoBlock.block();
		addNode(block);
		return block;
	}

	public FoBlock addInline() {
		FoBlock block = FoBlock.inline();
		addNode(block);
		return block;
	}
}
