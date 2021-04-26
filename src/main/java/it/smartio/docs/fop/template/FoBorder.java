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
 * The {@link FoBorder} class.
 */
public interface FoBorder<F extends FoBorder<?>> extends Fo {

	@SuppressWarnings("unchecked")
	default F setBorder(String width, String style, String color) {
		setBorderTop(width, style, color);
		setBorderLeft(width, style, color);
		setBorderRight(width, style, color);
		setBorderBottom(width, style, color);
		return (F) this;
	}

	@SuppressWarnings("unchecked")
	default F setBorderWidth(String borderWidth) {
		set("border-width", borderWidth);
		return (F) this;
	}

	@SuppressWarnings("unchecked")
	default F setBorderTop(String width, String style, String color) {
		set("border-top-width", width);
		set("border-top-style", style);
		set("border-top-color", color);
		return (F) this;
	}

	@SuppressWarnings("unchecked")
	default F setBorderLeft(String width, String style, String color) {
		set("border-start-width", width);
		set("border-start-style", style);
		set("border-start-color", color);
		return (F) this;
	}

	@SuppressWarnings("unchecked")
	default F setBorderRight(String width, String style, String color) {
		set("border-end-width", width);
		set("border-end-style", style);
		set("border-end-color", color);
		return (F) this;
	}

	@SuppressWarnings("unchecked")
	default F setBorderBottom(String width, String style, String color) {
		set("border-bottom-width", width);
		set("border-bottom-style", style);
		set("border-bottom-color", color);
		return (F) this;
	}

	@SuppressWarnings("unchecked")
	default F setBorderRadius(String radius) {
		set("fox:border-radius", radius);
		return (F) this;
	}

}
