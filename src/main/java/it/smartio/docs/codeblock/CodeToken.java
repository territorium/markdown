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

package it.smartio.docs.codeblock;

/**
 * The {@link CodeToken} class.
 */
enum CodeToken {

	TEXT("#000000"), COMMENT("#808080"),

	DEFINES("#469BA5"), KEYWORD("#9d1a92"), SECTION("#ff0008"), PARAMETER("#000082"),

	NAME("#a9142b"), VALUE("#007000"),

	JSON_NAME("#a9142b"), JSON_VALUE("#078967"), JSON_TEXT("#0758a0"),

	YAML_ATTR("#f92370"), YAML_VALUE("#e3d774"),

	YAML_COLOR("#f8f8ee"), YAML_COMMENT("#90918b"), YAML_BACKGROUND("#282923");

	public final String COLOR;

	/**
	 * Create a literal with a color
	 *
	 * @param color
	 */
	CodeToken(String color) {
		this.COLOR = color;
	}
}
