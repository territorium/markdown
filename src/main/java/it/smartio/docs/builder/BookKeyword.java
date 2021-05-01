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

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The {@link BookKeyword} class.
 */
public class BookKeyword {

  private static final Pattern PATTERN_MAPPING = Pattern.compile("/([^/]+)/([^/]+)/");
  private static final Pattern PATTERN_REPLACE = Pattern.compile("\\{\\{([^;]+);([^}]+)\\}\\}");

  private final String         keyword;
  private final List<Item>     expressions;

  /**
   * Constructs an instance of {@link BookKeyword}.
   *
   * @param keyword
   * @param expressions
   */
  private BookKeyword(String keyword, List<Item> expressions) {
    this.keyword = keyword;
    this.expressions = expressions;
  }

  /**
   * Get the keyword.
   */
  public final String getKeyword() {
    return this.keyword;
  }

  /**
   * Handles the keywords for the {@link InlineBuilder}.
   *
   * @param input
   * @param inline
   */
  public final void handle(String input, InlineBuilder inline) {
    this.expressions.forEach(i -> i.accept(this, inline));
  }

  /**
   * Processes a text item.
   *
   * @param item
   * @param inline
   */
  protected final void visit(Item item, InlineBuilder inline) {
    inline.add(new TextBuilder(item.text));
  }

  /**
   * Processes a node item.
   *
   * @param item
   * @param inline
   */
  protected final void visit(Node item, InlineBuilder inline) {
    InlineBuilder text = inline.add(new InlineBuilder());
    if (item.color != null) {
      text.setColor(item.color);
    }
    if (item.bold) {
      text.setBold();
    }
    if (item.italic) {
      text.setItalic();
    }
    visit((Item) item, text);
  }

  /**
   * Create a {@link BookKeyword} for the provided input. Processes the input for patterns of kind:
   *
   * <pre>
   *   /PATTERN/ => {{VALUE1;STYLE1}}SOME_TEXT{{VALUE2;STYLE2,STYLE3}}
   * </pre>
   *
   * @param input
   */
  public static BookKeyword of(String input) {
    List<Item> items = new ArrayList<>();
    Matcher matcher = BookKeyword.PATTERN_MAPPING.matcher(input);
    while (matcher.find()) {
      String pattern = matcher.group(1);
      String replace = matcher.group(2);

      int offset = 0;
      Matcher r = BookKeyword.PATTERN_REPLACE.matcher(replace);
      while (r.find()) {
        if (offset < r.start()) {
          String text = replace.substring(offset, r.start());
          items.add(new Item(text));
        }
        offset = r.end();

        String value = r.group(1);
        String styles = r.group(2);

        String color = null;
        boolean bold = false;
        boolean italic = false;

        for (String style : styles.split(",")) {
          if ("bold".equalsIgnoreCase(style)) {
            bold = true;
          } else if ("italic".equalsIgnoreCase(style)) {
            italic = true;
          } else if (style.startsWith("#")) {
            color = style;
          }
        }
        items.add(new Node(value, bold, italic, color));
      }

      String keyword = pattern = pattern.replaceAll("\\(", "").replaceAll("\\)", "");
      return new BookKeyword(keyword, items);
    }

    return null;
  }

  private static class Item {

    private final String text;

    private Item(String text) {
      this.text = text;
    }

    public void accept(BookKeyword visitor, InlineBuilder inline) {
      visitor.visit(this, inline);
    }
  }

  private static class Node extends Item {

    private final boolean bold;
    private final boolean italic;
    private final String  color;

    private Node(String text, boolean bold, boolean italic, String color) {
      super(text);
      this.bold = bold;
      this.italic = italic;
      this.color = color;
    }

    @Override
    public void accept(BookKeyword visitor, InlineBuilder inline) {
      visitor.visit(this, inline);
    }
  }
}
