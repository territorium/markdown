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

import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import it.smartio.docs.fop.builder.FoBasicLink;
import it.smartio.docs.fop.builder.FoBlock;

/**
 * The {@link FoPageItemText} class.
 */
public class FoPageItemText implements FoPageItem {

	private static final Pattern ENV = Pattern.compile("\\{\\{\\$([^}]+)\\}\\}", Pattern.CASE_INSENSITIVE);
	private static final Pattern LINK = Pattern.compile("\\[([^\\]]+)\\]\\(([^\\)]+)\\)", Pattern.CASE_INSENSITIVE);

	private String text;
	private String color;
	private String fontSize;
	private String fontStyle;
	private String fontWeight;
	private String textAlign;
	private String lineHeight;

	private String top;
	private String left;
	private String right;
	private String bottom;

	/**
	 * Constructs an instance of {@link FoPageItemText}.
	 *
	 */
	public FoPageItemText() {
		this.textAlign = "left";
		this.lineHeight = "1.5em";
	}

	public final void setText(String text) {
		this.text = text;
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

	@Override
	public void render(FoBlock block, Properties properties) {
		properties.put("PAGE_NUMBER", "<fo:page-number/>");

		block.setColor(this.color);
		block.setFontSize(this.fontSize);
		block.setFontStyle(this.fontStyle);
		block.setFontWeight(this.fontWeight);
		block.setTextAlign(this.textAlign);
		block.setLineHeight(this.lineHeight);
		block.setPadding(this.left, this.right, this.top, this.bottom);

		FoPageItemText.addText(FoPageItemText.replaceText(this.text, properties), block);
	}

	private static String replaceText(String text, Properties properties) {
		int offset = 0;
		StringBuffer buffer = new StringBuffer();

		Matcher matcher = FoPageItemText.ENV.matcher(text);
		while (matcher.find()) {
			buffer.append(text.substring(offset, matcher.start()));
			buffer.append(properties.get(matcher.group(1)));
			offset = matcher.end();
		}
		buffer.append(text.substring(offset));
		return buffer.toString();
	}

	private static void addText(String text, FoBlock block) {
		int offset = 0;
		// StringBuffer buffer = new StringBuffer();
		//
		Matcher matcher = FoPageItemText.LINK.matcher(text);
		while (matcher.find()) {
			if (offset < matcher.start()) {
				block.addNode(FoBlock.inline().addContent(text.substring(offset, matcher.start())));
			}

			FoBasicLink link = new FoBasicLink("mailto:" + matcher.group(2));
			link.addText(matcher.group(1));
			block.addNode(link);

			offset = matcher.end();
		}
		if (offset < text.length()) {
			block.addNode(FoBlock.inline().addContent(text.substring(offset)));
		}
	}
}