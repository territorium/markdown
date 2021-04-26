/**
 * 
 */

package it.smartio.docs.fop;

import org.apache.fop.fonts.EmbedFontInfo;
import org.apache.fop.fonts.FontTriplet;
import org.apache.fop.fonts.FontUris;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URL;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Manages {@link FoFont}'s
 */
public abstract class FoFont {

  private static Pattern                PATTERN     = Pattern.compile("^([^\\s]+)\\s([^\\s]+)$");
  private static Pattern                PLACEHOLDER = Pattern.compile("(@([^@]+)@)");

  private static Map<String, String>    FONTS       = new HashMap<>();
  private static Map<String, Character> SYMBOLS     = new HashMap<>();

  /**
   * Avoid to create an instance of {@link FoFont}
   */
  private FoFont() {}

  /**
   * Creates an {@link EmbedFontInfo}.
   * 
   * @param name
   * @param style
   * @param weight
   * @param fontfile
   */
  public static EmbedFontInfo createEmbed(String name, String style, int weight, String fontfile) {
    URI fontURI = URI.create(fontfile);
    URI metricURI = URI.create(fontfile + ".xml");
    List<FontTriplet> triplets = Collections.singletonList(new FontTriplet(name, style, weight));
    return new EmbedFontInfo(new FontUris(fontURI, metricURI), true, false, triplets, null);
  }

  /**
   * Register the code-points for the font.
   * 
   * @param name
   * @param codepoints
   */
  public static void registerSymbols(String name, URL codepoints) {
    try (BufferedReader reader = new BufferedReader(new InputStreamReader(codepoints.openStream()))) {
      reader.lines().forEach(l -> {
        Matcher matcher = FoFont.PATTERN.matcher(l);
        if (matcher.find()) {
          String symbol = matcher.group(1);
          String value = matcher.group(2);
          FoFont.FONTS.put(symbol, name);
          FoFont.SYMBOLS.put(symbol, Character.valueOf((char) Integer.valueOf(value, 16).intValue()));
        }
      });
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * Renders each material symbol of the text using the provided {@link Function}.
   *
   * @param text
   * @param function
   */
  public static String forEachSymbol(String text, BiFunction<Character, String, String> function) {
    int offset = 0;
    StringBuffer buffer = new StringBuffer();

    Matcher matcher = FoFont.PLACEHOLDER.matcher(text);
    while (matcher.find()) {
      buffer.append(text.substring(offset, matcher.start(1)));
      offset = matcher.end(1);

      String symbolName = matcher.group(2);
      if ("web".equals(symbolName)) {
        symbolName = "language";
      } else if ("mobile".equals(symbolName)) {
        symbolName = "smartphone";
      } else if ("config".equals(symbolName)) {
        symbolName = "miscellaneous_services";
      }

      if (FoFont.SYMBOLS.containsKey(symbolName)) {
        buffer.append(function.apply(FoFont.SYMBOLS.get(symbolName), FoFont.FONTS.get(symbolName)));
      } else {
        buffer.append("@" + symbolName + "@");
      }
    }

    buffer.append(text.substring(offset, text.length()));
    return buffer.toString();
  }
}
