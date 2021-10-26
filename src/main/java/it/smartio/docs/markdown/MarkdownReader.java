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

package it.smartio.docs.markdown;

import org.commonmark.Extension;
import org.commonmark.parser.Parser;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import it.smartio.docs.Book;
import it.smartio.docs.builder.BookBuilder;
import it.smartio.docs.markdown.alerts.AlertExtension;
import it.smartio.docs.markdown.images.ImageExtension;
import it.smartio.docs.markdown.markers.MarkerExtension;
import it.smartio.docs.markdown.tables.TableExtension;

/**
 * The {@link MarkdownReader} implements a reader based on Markdown. The Reader starts from a file
 * an includes referenced files to create a single huge Markdown file.
 */
public class MarkdownReader {

  private static final List<Extension> EXTENSIONS = new ArrayList<>();

  static {
    MarkdownReader.EXTENSIONS.add(ImageExtension.create());
    MarkdownReader.EXTENSIONS.add(TableExtension.create());
    MarkdownReader.EXTENSIONS.add(AlertExtension.create());
    MarkdownReader.EXTENSIONS.add(MarkerExtension.create());
  }

  // File includes: [TITLE?](RELATIVE_PATH)
  private static final Pattern INCLUDE      =
      Pattern.compile("^\\[([^\\]]*)\\]\\((.+\\.md)\\)", Pattern.CASE_INSENSITIVE);
  // Header definition: #+ HEADER
  private static final Pattern HEADER       = Pattern.compile("^([#]+)\\s.+", Pattern.CASE_INSENSITIVE);
  // Footnote definition: [ID]: URL "TITLE"
  private static final Pattern FOOTNOTE     =
      Pattern.compile("^\\[(\\w+)\\]:\\s([^\\s]+)(\\s\".+\")?", Pattern.CASE_INSENSITIVE);
  // Footnote reference: [TEXT]?[ID]
  private static final Pattern FOOTNOTE_REF =
      Pattern.compile("(?:\\[([^\\]]*)\\])\\[(\\w+)\\]", Pattern.CASE_INSENSITIVE);

  // Environment variables: ${ENVIRONMENT_VARIABLE}
  private static final Pattern PARAMETER = Pattern.compile("\\$\\{([^}]+)}", Pattern.CASE_INSENSITIVE);
  // Image definition: ![](IMAGE_PATH)
  private static final Pattern IMAGE     = Pattern.compile("!\\[([^\\]]*)\\]\\(([^\\)]+)\\)", Pattern.CASE_INSENSITIVE);

  private final Properties     properties;
  private final List<String>   idIndex;

  /**
   * Constructs an instance of {@link MarkdownReader}.
   *
   * @param properties
   */
  public MarkdownReader(Properties properties) {
    this.properties = properties;
    this.idIndex = new ArrayList<>();
  }

  /**
   * Get an environment variable by name.
   *
   * @param name
   */
  protected final String getProperty(String name) {
    return this.properties.containsKey(name) ? this.properties.getProperty(name) : "";
  }

  /**
   * Get the footnote index.
   *
   * @param file
   * @param name
   */
  protected final int getFootnote(String file, String name) {
    return this.idIndex.indexOf(String.format("%s_%s", file, name)) + 1;
  }

  /**
   * Calculates the new footnote reference.
   *
   * @param file
   * @param name
   * @param text
   */
  protected final String getFootnote(String file, String name, String text) {
    this.idIndex.add(String.format("%s_%s", file, name));
    return (text == null) ? String.format("[%s]", this.idIndex.size())
        : String.format("[%s][%s]", text, this.idIndex.size());
  }

  /**
   * Merge the files to the markdown content.
   *
   * @param file
   * @param header
   * @param writer
   */
  protected final void merge(File file, String header, PrintWriter writer) throws IOException {
    List<String> footnodes = new ArrayList<>();

    String level = header;
    File path = file.getParentFile().getAbsoluteFile();
    for (String line : Files.readAllLines(file.toPath())) {

      // Ignore lines that starting with a tab
      if (line.startsWith("\t")) {
        writer.println(line);
        continue;
      }

      // Processes file includes.
      Matcher matcher = MarkdownReader.INCLUDE.matcher(line);
      if (matcher.find()) {
        File md = new File(path, matcher.group(2));
        if (md.exists()) {
          merge(md, "\n" + level, writer);
        }
        continue;
      }

      // Replace all environment variables
      line = MarkdownReader.replaceAll(line, MarkdownReader.PARAMETER, m -> getProperty(m.group(1)));

      // Update headers
      matcher = MarkdownReader.HEADER.matcher(line);
      if (matcher.find()) {
        level = header + matcher.group(1);
        writer.println(header + line);
        continue;
      }

      // Update footnotes
      matcher = MarkdownReader.FOOTNOTE.matcher(line);
      if (matcher.find()) {
        int indexOf = getFootnote(file.getName(), matcher.group(1));
        String text = String.format("[%s]: %s", indexOf, matcher.group(2));
        if (matcher.group(3) != null) {
          text += matcher.group(3);
        }
        footnodes.add(text);
        continue;
      }

      // Replace all link references
      line = MarkdownReader.replaceAll(line, MarkdownReader.FOOTNOTE_REF,
          m -> getFootnote(file.getName(), m.group(2), m.group(1)));

      // Normalize image paths
      line = MarkdownReader.replaceAll(line, MarkdownReader.IMAGE,
          m -> m.group(2).startsWith("/") || m.group(2).startsWith("data:") ? m.group(0)
              : String.format("![%s](%s/%s)", m.group(1), path.getPath(), m.group(2)));

      writer.println(line);
    }

    if (!footnodes.isEmpty()) {
      writer.println();
      footnodes.stream().forEach(t -> writer.println(t));
      writer.println();
    }
  }

  /**
   * Uses the {@link Pattern} to replace parts of the text.
   *
   * @param text
   * @param pattern
   * @param function
   */
  private static String replaceAll(String text, Pattern pattern, Function<Matcher, String> function) {
    StringBuilder content = new StringBuilder();
    Matcher matcher = pattern.matcher(text);

    int offset = 0;
    while (matcher.find()) {
      String result = function.apply(matcher);
      if (result != null) {
        content.append(text.substring(offset, matcher.start()));
        content.append(result);
        offset = matcher.end();
      }
    }
    content.append(text.substring(offset));
    return content.toString();
  }

  /**
   * Constructs an instance of {@link MarkdownReader}.
   *
   * @param source
   */
  public Book parse(File source) throws IOException {
    StringWriter text = new StringWriter();
    try (PrintWriter writer = new PrintWriter(text)) {
      merge(source, "", writer);
    }
    
    try(Writer writer = new FileWriter("/tmp/test.md")) {
      writer.write(text.toString());
    }
    
    BookBuilder book = new BookBuilder();
    Parser parser = Parser.builder().extensions(MarkdownReader.EXTENSIONS).build();
    parser.parse(text.toString()).accept(new MarkdownParser(book));
    return book.build();
  }
}
