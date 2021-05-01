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

package it.smartio.docs.fop.config;

import org.apache.fop.fonts.EmbedFontInfo;
import org.apache.fop.fonts.Font;
import org.apache.fop.fonts.FontTriplet;
import org.apache.fop.fonts.FontUris;

import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import it.smartio.docs.fop.Fo;

/**
 * The {@link UIFont} class.
 */
class UIFont {

  private final String       name;
  private final List<Metric> metrics = new ArrayList<>();

  /**
   * Constructs an instance of {@link UIFont}.
   *
   * @param name
   */
  public UIFont(String name) {
    this.name = name;
  }

  /**
   * Gets the font name.
   */
  public final String getName() {
    return this.name;
  }

  /**
   * Sets the {@link URI} to the font.
   */
  public final void addMetric(URI uri, boolean bold, boolean italic) {
    this.metrics.add(new Metric(uri, bold, italic));
  }

  /**
   * Generate the {@link EmbedFontInfo}'s from this instance.
   *
   * @param embedFonts
   */
  public final void build(List<EmbedFontInfo> embedFonts) {
    for (Metric metric : this.metrics) {
      int weight = metric.bold ? Font.WEIGHT_BOLD : Font.WEIGHT_NORMAL;
      String style = metric.italic ? Font.STYLE_ITALIC : Font.STYLE_NORMAL;
      FontUris fontUri = new FontUris(metric.uri, URI.create(metric.uri.toString() + ".xml"));
      List<FontTriplet> triplets = Collections.singletonList(new FontTriplet(this.name, style, weight));
      embedFonts.add(new EmbedFontInfo(fontUri, true, false, triplets, null));
    }
  }

  /**
   * Creates an {@link URI} from an internal resource.
   *
   * @param resource
   */
  private static URI fromResource(String resource) {
    return URI.create(UIFont.class.getResource(resource).toString());
  }

  /**
   * Load fonts the internal fonts if not defined.
   *
   * @param fonts
   */
  public static void loadDefaults(Map<String, UIFont> fonts) {
    if (!fonts.containsKey(Fo.FONT_SYMBOLS)) {
      UIFont font = new UIFont(Fo.FONT_SYMBOLS);
      font.addMetric(UIFont.fromResource("/fonts/MaterialIcons-Regular.ttf"), false, false);
      fonts.put(font.getName(), font);

      URL codepoints = UIFont.class.getResource("/fonts/MaterialIcons-Regular.ttf.codepoints");
      FontSymbols.registerSymbols(Fo.FONT_SYMBOLS, codepoints);
      FontSymbols.registerSymbol(Fo.FONT_SYMBOLS, "web", "language");
      FontSymbols.registerSymbol(Fo.FONT_SYMBOLS, "mobile", "smartphone");
      FontSymbols.registerSymbol(Fo.FONT_SYMBOLS, "config", "miscellaneous_services");
    }

    if (!fonts.containsKey(Fo.FONT_CODE)) {
      UIFont font = new UIFont(Fo.FONT_CODE);
      font.addMetric(UIFont.fromResource("/fonts/UbuntuMono-Regular.ttf"), false, false);
      font.addMetric(UIFont.fromResource("/fonts/UbuntuMono-Bold.ttf"), true, false);
      font.addMetric(UIFont.fromResource("/fonts/UbuntuMono-Italic.ttf"), false, true);
      font.addMetric(UIFont.fromResource("/fonts/UbuntuMono-BoldItalic.ttf"), true, true);
      fonts.put(font.getName(), font);
    }

    if (!fonts.containsKey(Fo.FONT_TEXT)) {
      UIFont font = new UIFont(Fo.FONT_TEXT);
      font.addMetric(UIFont.fromResource("/fonts/RobotoCondensed-Regular.ttf"), false, false);
      font.addMetric(UIFont.fromResource("/fonts/RobotoCondensed-Bold.ttf"), true, false);
      font.addMetric(UIFont.fromResource("/fonts/RobotoCondensed-Italic.ttf"), false, true);
      font.addMetric(UIFont.fromResource("/fonts/RobotoCondensed-BoldItalic.ttf"), true, true);
      fonts.put(font.getName(), font);
    }
  }

  /**
   * The {@link Metric} defines a metric for the {@link UIFont}.
   */
  private class Metric {

    private final URI     uri;
    private final boolean bold;
    private final boolean italic;

    /**
     * Constructs an instance of {@link UIFontMetric}.
     *
     * @param uri
     * @param bold
     * @param italic
     */
    private Metric(URI uri, boolean bold, boolean italic) {
      this.uri = uri;
      this.bold = bold;
      this.italic = italic;
    }
  }
}
