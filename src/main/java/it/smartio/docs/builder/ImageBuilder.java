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

package it.smartio.docs.builder;

import it.smartio.docs.Image;

/**
 * The {@link ImageBuilder} class.
 */
public class ImageBuilder extends ContentBuilder implements Image {

	private final String url;
	private final String text;
	private final String align;
	private final String width;
	private final String height;

	/**
	 * Constructs an instance of {@link ImageBuilder}.
	 */
	public ImageBuilder(String url, String text, String align, String width, String height) {
		this.url = url;
		this.text = text;
		this.align = (align == null) ? "center" : align;
		this.width = width;
		this.height = height;
	}

	/**
	 * Gets the URL.
	 */
	@Override
	public final String getUrl() {
		return this.url;
	}

	/**
	 * Gets the title.
	 */
	@Override
	public final String getText() {
		return this.text;
	}

	/**
	 * Gets the align.
	 */
	@Override
	public final String getAlign() {
		return this.align;
	}

	/**
	 * Gets the width.
	 */
	@Override
	public final String getWidth() {
		return this.width;
	}

	/**
	 * Gets the height.
	 */
	@Override
	public final String getHeight() {
		return this.height;
	}
}
