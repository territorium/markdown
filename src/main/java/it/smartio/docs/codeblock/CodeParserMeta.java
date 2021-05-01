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

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import it.smartio.docs.builder.CodeParserDefault;
import it.smartio.docs.builder.SectionBuilder;

/**
 * The {@link CodeParserMeta} class.
 */
class CodeParserMeta extends CodeParserDefault {

  private static final String  TYPES    = String.join("|", "void", "number", "integer", "string", "boolean");
  private static final String  KEYWORDS = String.join("|", "private", "public", "enum", "class", "interface",
      "implements", "extends", "return", "const", "final", "super", "this", "transient", "default");

  private static final Pattern PATTERN  = Pattern.compile(
      String.format("(?:(%s)|(%s)|([\\s\\t]+)|([{}<>(),]+))", CodeParserMeta.TYPES, CodeParserMeta.KEYWORDS),
      Pattern.CASE_INSENSITIVE);

  /**
   * Constructs an instance of {@link CodeParserCpp}.
   *
   * @param builder
   */
  public CodeParserMeta(SectionBuilder builder) {
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
      } else {
        String comment = null;
        if (line.contains("/**")) {
          isComment = true;
          comment = line.substring(line.indexOf("/**"));
          line = line.substring(0, line.indexOf("/**") - 1);
        } else if (line.contains("//")) {
          comment = line.substring(line.indexOf("//"));
          line = line.substring(0, line.indexOf("//") - 1);
        }

        Matcher matcher = CodeParserMeta.PATTERN.matcher(line);
        int offset = 0;
        while (matcher.find()) {
          if (matcher.start() > offset) {
            addText(line.substring(offset, matcher.start()));
          }

          if (matcher.group(1) != null) {
            addInline(matcher.group(1)).setBold().setColor(CodeToken.VALUE.COLOR);
          }
          if (matcher.group(2) != null) {
            addInline(matcher.group(2)).setBold().setColor(CodeToken.KEYWORD.COLOR);
          }
          if (matcher.group(3) != null) {
            addText(matcher.group(3));
          }
          if (matcher.group(4) != null) {
            addText(matcher.group(4));
          }

          offset = matcher.end();
        }

        addText(line.substring(offset));

        if (comment != null) {
          addInline(comment).setItalic().setColor(CodeToken.COMMENT.COLOR);
        }

        addText("\n");
      }
    }
  }
}
