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

import it.smartio.docs.fop.template.FoPadding;

/**
 * The {@link FoLeader} class.
 */
public class FoLeader extends FoNode implements FoPadding<FoLeader> {

	public FoLeader() {
		super("fo:leader");
	}

	public FoLeader setPattern(String value) {
		set("leader-pattern", value);
		return this;
	}

	public FoLeader setWidth(String value) {
		set("leader-pattern-width", value);
		return this;
	}

	public FoLeader setAlign(String value) {
		set("leader-alignment", value);
		return this;
	}

	public FoLeader setLength(String value) {
		set("leader-length", value);
		return this;
	}

	public FoLeader setColor(String value) {
		set("color", value);
		return this;
	}

	public FoLeader setRuleThickness(String value) {
		set("rule-thickness", value);
		return this;
	}
}
