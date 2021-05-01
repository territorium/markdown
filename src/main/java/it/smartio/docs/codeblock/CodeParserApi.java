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

import it.smartio.docs.builder.CodeParser;
import it.smartio.docs.builder.CodeParserDefault;
import it.smartio.docs.builder.ParagraphBuilder;
import it.smartio.docs.builder.SectionBuilder;
import it.smartio.docs.builder.TableBuilder;
import it.smartio.docs.builder.TableBuilder.RowBuilder;

/**
 * The {@link CodeParserApi} class.
 */
class CodeParserApi implements CodeParser {

  private static String        HEAD_COLOR        = "#aaaaaa";
  private static String        GET_COLOR         = "#61affe";
  private static String        POST_COLOR        = "#49cc90";
  private static String        PUT_COLOR         = "#fca130";
  private static String        DELETE_COLOR      = "#f13e3e";

  private static String        HEAD_BACKGROUND   = "#eeeeee";
  private static String        GET_BACKGROUND    = "#c7e3ff";
  private static String        POST_BACKGROUND   = "#bfedd8";
  private static String        PUT_BACKGROUND    = "#fdcf96";
  private static String        DELETE_BACKGROUND = "#fbcbcb";

  private static final String  PATTERN_TEXT      = "^(\\s*)([\\w-]+):\\s*(.+)?$";
  private static final String  PATTERN_IMAGE     = "!\\[\\]\\(([^\\)]+)\\)(?:\\{width=(.+)\\})?";
  private static final Pattern PATTERN           =
      Pattern.compile(CodeParserApi.PATTERN_TEXT, Pattern.CASE_INSENSITIVE);
  private static final Pattern PATTERN2          =
      Pattern.compile(CodeParserApi.PATTERN_IMAGE, Pattern.CASE_INSENSITIVE);

  private final TableBuilder   table;

  /**
   * Constructs an instance of {@link CodeParserApi}.
   *
   * @param builder
   */
  public CodeParserApi(SectionBuilder builder) {
    this.table = builder.addVirtualTable();
  }

  protected final String getColor(String method) {
    switch (method) {
      case "GET":
        return CodeParserApi.GET_COLOR;
      case "POST":
        return CodeParserApi.POST_COLOR;
      case "PUT":
      case "PATCH":
        return CodeParserApi.PUT_COLOR;
      case "DELETE":
        return CodeParserApi.DELETE_COLOR;
      case "HEAD":
      default:
        return CodeParserApi.HEAD_COLOR;
    }
  }

  protected final String getBackground(String method) {
    switch (method) {
      case "GET":
        return CodeParserApi.GET_BACKGROUND;
      case "POST":
        return CodeParserApi.POST_BACKGROUND;
      case "PUT":
      case "PATCH":
        return CodeParserApi.PUT_BACKGROUND;
      case "DELETE":
        return CodeParserApi.DELETE_BACKGROUND;
      case "HEAD":
      default:
        return CodeParserApi.HEAD_BACKGROUND;
    }
  }

  /**
   * Parses the code text
   *
   * @param node
   */
  @Override
  public void parse(String text) {
    String method = "";
    String content = null;
    String contentType = "application/json";

    this.table.addColumn(1, "left");
    this.table.addColumn(4, "left");
    this.table.addBody();

    for (String line : text.split("\\n")) {
      Matcher matcher = CodeParserApi.PATTERN.matcher(line);
      if (matcher.find()) {
        int intend = matcher.group(1).length();
        String name = matcher.group(2);
        String value = matcher.group(3);

        if (content != null) {
          processContent(this.table, content, contentType);
          content = null;
          contentType = "application/json";
        }

        if ("content".equalsIgnoreCase(name)) {
          content = value;
        } else if (intend == 0) {
          if ("method".equalsIgnoreCase(name)) {
            method = value.toUpperCase();
          } else if ("path".equalsIgnoreCase(name)) {
            RowBuilder row = this.table.addRow();
            ParagraphBuilder cell = row.addCell(1, 2).getContent().setPadding("5pt", "7pt");
            cell.addInline().setPadding("15pt", "5pt").setBackground(getColor(method)).setBold().setRadius("3px")
                .addContent(method.toUpperCase());
            cell.addInline().setPadding("5pt", "5pt").setBold().addContent(value);
          } else {
            RowBuilder row = this.table.addRow();
            row.addCell(1, 2).getContent().setPadding("5pt", "2pt").addInline().setPadding("5pt", "2pt")
                .setBackground("#ffffff").setRadius("3px").setBold().addContent(name);
          }
        } else if (intend > 0) {
          if ("content-type".equalsIgnoreCase(name)) {
            contentType = value.trim();
          }

          if ("content".equalsIgnoreCase(name)) {
            content = value;
          } else if (value != null) {
            RowBuilder row = this.table.addRow();
            row.addCell(1, 1).getContent().setPadding("5pt", "2pt").addInline().setPadding("15pt", "5pt").setBold()
                .addContent(name);
            row.addCell(1, 1).getContent().setPadding("5pt", "2pt").addInline().setPadding("15pt", "5pt")
                .addContent(value);
          }
        }
      } else if (content != null) {
        content += "\n" + line;
      }
    }

    if (content != null) {
      processContent(this.table, content, contentType);
      content = null;
      contentType = "application/json";
    }

    this.table.setBorderColor(getColor(method));
    this.table.setBackgroundColor(getBackground(method));
  }

  protected final void processContent(TableBuilder table, String content, String contentType) {
    RowBuilder row = table.addRow();
    ParagraphBuilder paragraph = row.addCell(1, 2).getContent().setPadding("1pt", "2pt");

    if ("application/json".equalsIgnoreCase(contentType.trim())) {
      ((CodeParserDefault) CodeFactory.of("json", paragraph)).setStyled(false).parse(content);
    } else if ("application/xml".equalsIgnoreCase(contentType.trim())) {
      ((CodeParserDefault) CodeFactory.of("xml", paragraph)).setStyled(false).parse(content);
    } else if (contentType.trim().toLowerCase().startsWith("image/")) {
      Matcher matcher = CodeParserApi.PATTERN2.matcher(content.trim());
      if (matcher.find()) {
        String source = matcher.group(1);
        String width = matcher.group(2);
        paragraph.addImage(source, null, null, width, null);
      } else {
        paragraph.addContent(content);
      }
    } else {
      paragraph.addCode().addContent(content);
    }
  }
}
