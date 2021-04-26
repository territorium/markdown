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

import it.smartio.docs.fop.builder.FoBlockContainer.Position;

/**
 * The {@link FoStaticContent} class.
 */
public class FoStaticContent extends FoNode {

	/**
	 * Constructs an instance of {@link FoStaticContent}.
	 *
	 * @param reference
	 */
	public FoStaticContent(String reference) {
		super("fo:static-content");
		set("flow-name", reference);
	}

	public FoBlock addBlock() {
		FoBlock builder = FoBlock.block();
		addNode(builder);
		return builder;
	}

	public FoBlockContainer blockContainer(Position position) {
		FoBlockContainer builder = new FoBlockContainer(position);
		addNode(builder);
		return builder;
	}
}
