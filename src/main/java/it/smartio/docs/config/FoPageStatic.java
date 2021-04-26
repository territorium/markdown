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

import it.smartio.docs.fop.builder.FoBlockContainer;
import it.smartio.docs.fop.builder.FoStaticContent;
import it.smartio.docs.fop.builder.FoBlockContainer.Position;
import it.smartio.docs.util.DataUri;

/**
 * The {@link FoPageStatic} class.
 */
public class FoPageStatic {

	private String top;
	private String left;
	private String right;
	private String bottom;
	private String background;

	private String color;
	private String fontSize;
	private String fontStyle;
	private String fontWeight;
	private String textAlign;
	private String lineHeight;

	private final List<FoPageItem> items = new ArrayList<>();

	/**
	 * Constructs an instance of {@link FoPageStatic}.
	 *
	 */
	public FoPageStatic() {
		this.top = "0";
		this.left = "0";
		this.right = "0";
		this.bottom = "0";
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

	public final void setBackground(String background) {
		this.background = background;
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

	public void render(FoStaticContent content, Properties properties) {
		FoBlockContainer container = content.blockContainer(Position.Absolute);
		container.setPosition(this.left, this.right, this.top, this.bottom);

		if (this.background != null) {
			if (this.background.startsWith("#")) {
				container.setBackgroundColor(this.background);
			} else {
				container.setBackground(DataUri.loadImage(this.background), "no-repeat");
			}
		}

		container.setColor(this.color);
		container.setFontSize(this.fontSize);
		container.setFontStyle(this.fontStyle);
		container.setFontWeight(this.fontWeight);
		container.setTextAlign(this.textAlign);
		container.setLineHeight(this.lineHeight);

		this.items.forEach(c -> c.render(container.addBlock(), properties));
	}
}