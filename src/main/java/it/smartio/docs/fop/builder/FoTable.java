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
import it.smartio.docs.fop.template.FoSpace;

/**
 * The {@link FoTable} class.
 */
public class FoTable extends FoNode implements FoSpace<FoTable>, FoBorder<FoTable>, FoBackground<FoTable> {

	/**
	 * Constructs an instance of {@link FoTable}.
	 */
	public FoTable() {
		super("fo:table");
	}

	public FoTable setTableLayout(String fixed) {
		set("table-layout", fixed);
		return this;
	}

	public FoTable setTableWidth(String width) {
		set("width", width);
		return this;
	}

	public FoTable setBorderBefore(String before) {
		set("border-before-width.conditionality", before);
		return this;
	}

	public FoTable setBorderCollapse(String before) {
		set("border-collapse", before);
		return this;
	}

	public FoTable setBorderSpacing(String spacing) {
		set("border-spacing", spacing);
		return this;
	}

	public FoTable addColumn(String number, String width) {
		FoNode builder = FoNode.of("fo:table-column");
		builder.set("column-number", number);
		builder.set("column-width", width);
		addNode(builder);
		return this;
	}

	public FoTableArea addHead() {
		return addArea("fo:table-header", "0pt", "0pt");
	}

	public FoTableArea addBody() {
		return addArea("fo:table-body", "0pt", "0pt");
	}

	public FoTableArea addFoot() {
		return addArea("fo:table-footer", "0pt", "0pt");
	}

	private FoTableArea addArea(String foName, String startIndent, String endIndent) {
		FoTableArea builder = new FoTableArea(foName);
		addNode(builder);
		return builder.setStartIndent(startIndent).setEndIndentLastLine(endIndent);
	}
}
