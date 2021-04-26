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

import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import it.smartio.docs.builder.CodeParserDefault;
import it.smartio.docs.builder.SectionBuilder;

/**
 * The {@link CodeParserCpp} class.
 */
class CodeParserCpp extends CodeParserDefault {

	private static final List<String> KEYWORDS = Arrays.asList("synchronized", "implements", "instanceof", "interface",
			"protected", "transient", "abstract", "continue", "strictfp", "volatile", "boolean", "default", "extends",
			"finally", "package", "private", "assert", "double", "import", "native", "public", "return", "static",
			"switch", "throws", "break", "catch", "class", "const", "final", "float", "short", "super", "throw",
			"while", "byte", "case", "char", "else", "enum", "goto", "long", "this", "void", "for", "int", "new", "try",
			"do", "if");

	private static final Pattern CPP = Pattern.compile("(" + String.join("|", CodeParserCpp.KEYWORDS) + ")",
			Pattern.CASE_INSENSITIVE);

	/**
	 * Constructs an instance of {@link CodeParserCpp}.
	 *
	 * @param builder
	 */
	public CodeParserCpp(SectionBuilder builder) {
		super(builder);
	}

	@Override
	public void parse(String text) {
		boolean isComment = false;
		for (String line : text.split("\\n")) {
			if (isComment) {
				addInline(line + "\n").setItalic().setColor(CodeToken.COMMENT.COLOR);
				if (line.contains("*/")) {
					isComment = false;
				}
			} else if (line.contains("/**")) {
				isComment = true;
				addInline(line + "\n").setItalic().setColor(CodeToken.COMMENT.COLOR);
			} else if (line.startsWith("#")) {
				addInline(line + "\n").setBold().setColor(CodeToken.DEFINES.COLOR);
			} else {
				Matcher matcher = CodeParserCpp.CPP.matcher(line);
				int offset = 0;
				while (matcher.find()) {
					if (matcher.start() > offset) {
						addText(line.substring(offset, matcher.start()));
					}
					addInline(matcher.group(1)).setBold().setColor(CodeToken.KEYWORD.COLOR);
					offset = matcher.end();
				}
				addText(line.substring(offset) + "\n");
			}
		}
	}

}
