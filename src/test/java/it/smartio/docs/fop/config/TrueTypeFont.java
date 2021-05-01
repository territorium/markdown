
package it.smartio.docs.fop.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.InputStream;
import java.io.OutputStream;

public class TrueTypeFont implements FilenameFilter {

  private static final File FONT_PATH = new File("src/main/resources/fonts");

  @Override
  public final boolean accept(File dir, String name) {
    return name.toLowerCase().endsWith(".ttf");
  }

  public static void main(String[] args) throws Exception {
    for (File font : TrueTypeFont.FONT_PATH.listFiles(new TrueTypeFont())) {
      File metric = new File(String.format("%s/%s.xml", TrueTypeFont.FONT_PATH.getAbsolutePath(), font.getName()));
      // TTFReader.main(new String[] { font.getAbsolutePath(), metric.getAbsolutePath() });

      try (InputStream fontStream = new FileInputStream(font.getAbsolutePath())) {
        try (OutputStream metricStream = new FileOutputStream(metric.getAbsolutePath())) {
          FontResolver.createFontMetric(fontStream, metricStream);
        }
      }
    }
  }
}
