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
import it.smartio.docs.fop.template.FoMargin;
import it.smartio.docs.fop.template.FoSpace;

/**
 * The {@link FoBlockContainer} class.
 */
public class FoBlockContainer extends FoNode implements FoSpace<FoBlockContainer>, FoMargin<FoBlockContainer>,
		FoBorder<FoBlockContainer>, FoFont<FoBlockContainer>, FoBackground<FoBlockContainer> {

	public enum Position {

		Absolute("absolute"), Auto("auto"), Fixed("fixed");

		public final String value;

		Position(String value) {
			this.value = value;
		}
	}

	/**
	 * Constructs an instance of {@link FoBlockContainer}.
	 */
	public FoBlockContainer(Position position) {
		super("fo:block-container");
		set("absolute-position", position.value);
	}

	public FoBlockContainer setPosition(String left, String right, String top, String bottom) {
		setPositionTop(top);
		setPositionLeft(left);
		setPositionRight(right);
		setPositionBottom(bottom);
		return this;
	}

	public FoBlockContainer setPositionTop(String value) {
		set("top", value);
		return this;
	}

	public FoBlockContainer setPositionLeft(String value) {
		set("left", value);
		return this;
	}

	public FoBlockContainer setPositionRight(String value) {
		set("right", value);
		return this;
	}

	public FoBlockContainer setPositionBottom(String value) {
		set("bottom", value);
		return this;
	}

	public FoBlockContainer setWidth(String value) {
		set("width", value);
		return this;
	}

	public FoBlockContainer setHeight(String value) {
		set("height", value);
		return this;
	}

	public FoBlock addBlock(String content) {
		FoBlock builder = FoBlock.block();
		addNode(builder);
		builder.addContent(content);
		return builder;
	}

	public FoBlock addBlock() {
		FoBlock builder = FoBlock.block();
		addNode(builder);
		return builder;
	}
}
