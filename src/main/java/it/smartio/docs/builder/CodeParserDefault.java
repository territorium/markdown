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

/**
 * The {@link CodeParserDefault} implements a tokenizer for a specific language.
 */
public class CodeParserDefault implements CodeParser {

	private final CodeBuilder builder;

	/**
	 * Constructs an instance of {@link CodeParserDefault}.
	 *
	 * @param handler
	 */
	public CodeParserDefault(SectionBuilder builder) {
		this.builder = builder.addStyledCode();
	}

	public final CodeParserDefault setStyled(boolean styled) {
		this.builder.setStyled(styled);
		return this;
	}

	/**
	 * Add a child {@link TextBuilder}.
	 *
	 * @param text
	 */
	protected final TextBuilder addText(String text) {
		return this.builder.addNode(new TextBuilder(text));
	}

	/**
	 * Add a child {@link InlineBuilder}.
	 */
	protected final InlineBuilder addInline() {
		return this.builder.addNode(new InlineBuilder());
	}

	/**
	 * Add a child {@link InlineBuilder}.
	 */
	protected final InlineBuilder addInline(String text) {
		InlineBuilder inline = addInline();
		inline.add(new TextBuilder(text));
		return inline;
	}

	/**
	 * Add a child {@link InlineBuilder}.
	 */
	protected final TableBuilder addCustomTable() {
		return this.builder.addNode(new TableBuilder(true));
	}

	protected final void setPadding(String padding) {
		this.builder.setPadding(padding);
	}

	protected final void setBackground(String background) {
		this.builder.setBackground(background);
	}

	protected final void setBorderColor(String borderColor) {
		this.builder.setBorderColor(borderColor);
	}

	protected final void setTextColor(String textColor) {
		this.builder.setTextColor(textColor);
	}

	protected final void setFontSize(String fontSize) {
		this.builder.setFontSize(fontSize);
	}

	@Override
	public void parse(String text) {
		addText(text);
	}
}
