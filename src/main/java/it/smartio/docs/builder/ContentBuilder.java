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

import java.util.Arrays;
import java.util.stream.Collectors;

import it.smartio.docs.List;

/**
 * The {@link ContentBuilder} class.
 */
public abstract class ContentBuilder extends SectionBuilder {

	/**
	 * Add a new {@link Item} to the {@link List}.
	 */
	public final InlineBuilder addInline() {
		return add(new InlineBuilder());
	}

	/**
	 * Add a new {@link Item} to the {@link List}.
	 */
	public final InlineBuilder addFootnote() {
		InlineBuilder builder = add(new InlineBuilder());
		builder.setFootnote();
		return builder;
	}

	/**
	 * Add a new {@link Item} to the {@link List}.
	 */
	public final LinkBuilder addLink(String url) {
		return add(new LinkBuilder(url));
	}

	public void addContent(String content) {
		content = Arrays.asList(content.split("\n")).stream().filter(s -> !s.isEmpty())
				.collect(Collectors.joining(" "));
		BookConfig.instance().processKeywords(content, t -> add(new TextBuilder(t)), () -> add(new InlineBuilder()));
	}

	/**
	 * Add a new {@link Item} to the {@link List}.
	 */
	public final void addInlineCode(String text) {
		add(new TextBuilder(text, true));
	}
}
