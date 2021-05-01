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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Manages {@link FontSymbols}'s
 */
public abstract class FontSymbols {

  private static Pattern                PATTERN     = Pattern.compile("^([^\\s]+)\\s([^\\s]+)$");
  private static Pattern                PLACEHOLDER = Pattern.compile("(@([^@]+)@)");

  private static Map<String, String>    FONTS       = new HashMap<>();
  private static Map<String, Character> SYMBOLS     = new HashMap<>();

  /**
   * Avoid to create an instance of {@link FontSymbols}
   */
  private FontSymbols() {}

  /**
   * Register the code-points for the font.
   *
   * @param name
   * @param codepoints
   */
  static void registerSymbols(String name, URL codepoints) {
    try (BufferedReader reader = new BufferedReader(new InputStreamReader(codepoints.openStream()))) {
      reader.lines().forEach(l -> {
        Matcher matcher = FontSymbols.PATTERN.matcher(l);
        if (matcher.find()) {
          String symbol = matcher.group(1);
          String value = matcher.group(2);
          FontSymbols.FONTS.put(symbol, name);
          FontSymbols.SYMBOLS.put(symbol, Character.valueOf((char) Integer.valueOf(value, 16).intValue()));
        }
      });
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * Register an alias for a symbol.
   *
   * @param name
   * @param alias
   * @param symbol
   */
  static void registerSymbol(String name, String alias, String symbol) {
    FontSymbols.FONTS.put(alias, name);
    FontSymbols.SYMBOLS.put(alias, FontSymbols.SYMBOLS.get(symbol));
  }

  /**
   * Renders each material symbol of the text using the provided {@link Function}.
   *
   * @param text
   * @param function
   */
  public static String forEach(String text, BiFunction<Character, String, String> function) {
    int offset = 0;
    StringBuffer buffer = new StringBuffer();

    Matcher matcher = FontSymbols.PLACEHOLDER.matcher(text);
    while (matcher.find()) {
      buffer.append(text.substring(offset, matcher.start(1)));
      offset = matcher.end(1);

      String symbolName = matcher.group(2);
      if (FontSymbols.SYMBOLS.containsKey(symbolName)) {
        String font = FontSymbols.FONTS.get(symbolName);
        Character symbol = FontSymbols.SYMBOLS.get(symbolName);
        buffer.append(function.apply(symbol, font));
      } else {
        buffer.append("@" + symbolName + "@");
      }
    }

    buffer.append(text.substring(offset, text.length()));
    return buffer.toString();
  }
}
