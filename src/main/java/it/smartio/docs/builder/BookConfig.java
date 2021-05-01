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
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * The {@link BookConfig} class.
 */
public class BookConfig {

  private static final String KEYWORD_TOL     = "/TOL/{{TOL;bold,#000000}}/";
  private static final String KEYWORD_SMARTIO = "/smart\\.IO/{{smart.;bold}}{{IO;bold,#469ba5}}/";

  private static BookConfig   INSTANCE;

  public static BookConfig instance() {
    if (BookConfig.INSTANCE == null) {
      BookConfig.init(Arrays.asList(BookConfig.KEYWORD_TOL, BookConfig.KEYWORD_SMARTIO));
    }
    return BookConfig.INSTANCE;
  }

  private final List<BookKeyword> keywords;
  private final Pattern           pattern;

  private BookConfig(List<BookKeyword> keywords) {
    this.keywords = keywords;
    this.pattern = keywords.isEmpty() ? null
        : Pattern.compile(keywords.stream().map(k -> "(" + k.getKeyword() + ")").collect(Collectors.joining("|")),
            Pattern.CASE_INSENSITIVE);
  }

  /**
   * . Process the content string for the {@link ContentBuilder}.
   *
   * @param content
   * @param builder
   */
  public final void processKeywords(String content, Consumer<String> consumer, Supplier<InlineBuilder> supplier) {
    int offset = 0;

    if (!this.keywords.isEmpty() && (this.pattern != null)) {
      Matcher matcher = this.pattern.matcher(content);
      while (matcher.find()) {
        if (matcher.start() > offset) {
          consumer.accept(content.substring(offset, matcher.start()));
        }

        for (int i = 0; i < matcher.groupCount(); i++) {
          String input = matcher.group(i + 1);
          if (input != null) {
            this.keywords.get(i).handle(input, supplier.get());
          }
        }
        offset = matcher.end();
      }
    }

    consumer.accept(content.substring(offset));
  }

  public static void init(List<String> mappings) {
    List<BookKeyword> keywords = new ArrayList<>();
    mappings.forEach(m -> keywords.add(BookKeyword.of(m)));
    BookConfig.INSTANCE = new BookConfig(keywords);
  }
}
