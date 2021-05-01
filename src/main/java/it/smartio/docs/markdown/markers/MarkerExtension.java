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

package it.smartio.docs.markdown.markers;

import org.commonmark.Extension;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;

import it.smartio.docs.markdown.markers.Marker.Decoration;

/**
 * Extension for GFM strikethrough using ~~ (GitHub Flavored Markdown). <p> Create it with
 * {@link #create()} and then configure it on the builders
 * ({@link org.commonmark.parser.Parser.Builder#extensions(Iterable)},
 * {@link HtmlRenderer.Builder#extensions(Iterable)}). </p> <p> The parsed strikethrough text
 * regions are turned into {@link Marker} nodes. </p>
 */
public class MarkerExtension implements Parser.ParserExtension {

  private MarkerExtension() {}

  public static Extension create() {
    return new MarkerExtension();
  }

  @Override
  public void extend(Parser.Builder parserBuilder) {
    parserBuilder.customDelimiterProcessor(new MarkerProcessor());
    parserBuilder.customDelimiterProcessor(new MarkerProcessor(4, '_', Decoration.Underline)); // underline
    parserBuilder.customDelimiterProcessor(new MarkerProcessor(2, '~', Decoration.Strikethrough)); // strikethrough
  }
}
