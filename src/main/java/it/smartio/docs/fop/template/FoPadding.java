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
 * The {@link FoPadding} class.
 */
public interface FoPadding<F extends FoPadding<?>> extends Fo {

	@SuppressWarnings("unchecked")
	default F setPadding(String value) {
		set("padding", value);
		return (F) this;
	}

	default F setPadding(String vertical, String horizontal) {
		return setPadding(horizontal, horizontal, vertical, vertical);
	}

	@SuppressWarnings("unchecked")
	default F setPaddingLeftRight(String value) {
		set("padding-left", value);
		set("padding-right", value);
		return (F) this;
	}

	@SuppressWarnings("unchecked")
	default F setPadding(String left, String right, String top, String bottom) {
		setPaddingTop(top);
		setPaddingLeft(left);
		setPaddingRight(right);
		setPaddingBottom(bottom);
		return (F) this;
	}

	@SuppressWarnings("unchecked")
	default F setPaddingTop(String value) {
		set("padding-top", value);
		return (F) this;
	}

	@SuppressWarnings("unchecked")
	default F setPaddingLeft(String value) {
		set("padding-left", value);
		return (F) this;
	}

	@SuppressWarnings("unchecked")
	default F setPaddingRight(String value) {
		set("padding-right", value);
		return (F) this;
	}

	@SuppressWarnings("unchecked")
	default F setPaddingBottom(String value) {
		set("padding-bottom", value);
		return (F) this;
	}
}
