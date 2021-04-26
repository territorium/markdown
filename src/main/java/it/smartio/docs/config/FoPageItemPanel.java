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

package it.smartio.docs.config;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import it.smartio.docs.fop.builder.FoBlock;

/**
 * The {@link FoPageItemPanel} class.
 */
public class FoPageItemPanel implements FoPageItem {

	private String top;
	private String left;
	private String right;
	private String bottom;
	private String span;

	private String color;
	private String fontSize;
	private String fontStyle;
	private String fontWeight;
	private String textAlign;
	private String lineHeight;

	private final List<FoPageItem> items = new ArrayList<>();

	/**
	 * Constructs an instance of {@link FoPageItemPanel}.
	 *
	 */
	public FoPageItemPanel() {
		this.textAlign = "left";
		this.lineHeight = "1.5em";
	}

	public final void addItem(FoPageItem child) {
		this.items.add(child);
	}

	public final void setTop(String top) {
		this.top = top;
	}

	public final void setLeft(String left) {
		this.left = left;
	}

	public final void setRight(String right) {
		this.right = right;
	}

	public final void setBottom(String bottom) {
		this.bottom = bottom;
	}

	public final void setSpan(String span) {
		this.span = span;
	}

	public final void setColor(String color) {
		this.color = color;
	}

	public final void setFontSize(String fontSize) {
		this.fontSize = fontSize;
	}

	public final void setFontStyle(String fontStyle) {
		this.fontStyle = fontStyle;
	}

	public final void setFontWeight(String fontWeight) {
		this.fontWeight = fontWeight;
	}

	public final void setTextAlign(String textAlign) {
		this.textAlign = textAlign;
	}

	public final void setLineHeight(String lineHeight) {
		this.lineHeight = lineHeight;
	}

	@Override
	public void render(FoBlock block, Properties properties) {
		block.setSpan(this.span);
		block.setColor(this.color);
		block.setFontSize(this.fontSize);
		block.setFontStyle(this.fontStyle);
		block.setFontWeight(this.fontWeight);
		block.setTextAlign(this.textAlign);
		block.setLineHeight(this.lineHeight);
		block.setMargin(this.left, this.right, this.top, this.bottom);

		this.items.forEach(c -> c.render(block.addBlock(), properties));
	}
}