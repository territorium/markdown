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

package it.smartio.docs.fop.template;

/**
 * The {@link FoSpace} class.
 */
public interface FoSpace<F extends FoSpace<?>> extends Fo {

	@SuppressWarnings("unchecked")
	default F setSpace(String value) {
		setSpaceAfter(value);
		setSpaceBefore(value);
		return (F) this;
	}

	@SuppressWarnings("unchecked")
	default F setSpaceBefore(String value) {
		set("space-before", value);
		return (F) this;
	}

	@SuppressWarnings("unchecked")
	default F setSpaceAfter(String value) {
		set("space-after", value);
		return (F) this;
	}

	@SuppressWarnings("unchecked")
	default F setSpaceBefore(String minimum, String optimum, String maximum) {
		set("space-before.minimum", minimum);
		set("space-before.optimum", optimum);
		set("space-before.maximum", maximum);
		return (F) this;
	}

	@SuppressWarnings("unchecked")
	default F setSpaceAfter(String minimum, String optimum, String maximum) {
		set("space-after.minimum", minimum);
		set("space-after.optimum", optimum);
		set("space-after.maximum", maximum);
		return (F) this;
	}

	@SuppressWarnings("unchecked")
	default F setSpace(String minimum, String optimum, String maximum) {
		setSpaceAfter(minimum, optimum, maximum);
		setSpaceBefore(minimum, optimum, maximum);
		return (F) this;
	}
}
