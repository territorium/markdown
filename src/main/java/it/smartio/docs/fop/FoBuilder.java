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

package it.smartio.docs.fop;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import it.smartio.docs.fop.template.Fo;

/**
 * The {@link FoBuilder} is a builder implementation for Apache Formating
 * Objects.
 */
public abstract class FoBuilder implements Fo, Iterable<FoBuilder> {

	private final Map<String, String> attributes = new LinkedHashMap<>();
	private final List<FoBuilder> children = new ArrayList<>();

	/**
	 * Get the attributes.
	 */
	protected final Map<String, String> getAttributes() {
		return this.attributes;
	}

	/**
	 * Returns {@code true} if this list contains no elements.
	 *
	 * @return {@code true} if this list contains no elements
	 */
	public final boolean hasChildren() {
		return !this.children.isEmpty();
	}

	/**
	 * Returns an iterator over child {@link FoBuilder}.
	 */
	@Override
	public final Iterator<FoBuilder> iterator() {
		return this.children.iterator();
	}

	/**
	 * Set an attribute.
	 *
	 * @param name
	 * @param value
	 */
	@Override
	public final FoBuilder set(String name, String value) {
		this.attributes.put(name, value);
		return this;
	}

	/**
	 * Add a new child {@link FoBuilder}.
	 *
	 * @param node
	 */
	public final FoBuilder addNode(FoBuilder builder) {
		this.children.add(builder);
		return this;
	}

	/**
	 * Build the String.
	 */
	public abstract String build();
}
