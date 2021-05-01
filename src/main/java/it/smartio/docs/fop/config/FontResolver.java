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

import org.apache.fop.fonts.apps.TTFReader;
import org.apache.fop.fonts.truetype.FontFileReader;
import org.apache.fop.fonts.truetype.TTFFile;
import org.apache.xmlgraphics.io.Resource;
import org.apache.xmlgraphics.io.ResourceResolver;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.net.URL;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

/**
 * The {@link FontResolver} implements a {@link ResourceResolver} which supports dynamic generation
 * of font-metrics.
 */
class FontResolver implements ResourceResolver {

  /**
   * Resolves the resource from an {@link URI} and supports dynamic font-metric generation.
   *
   * @param uri
   */
  @Override
  public final Resource getResource(URI uri) throws IOException {
    if (uri.toString().startsWith("/")) {
      return new Resource(new FileInputStream(uri.toString()));
    } else if (uri.toString().endsWith("ttf.xml")) {
      String ttf = uri.toString().substring(0, uri.toString().length() - 4);
      return new Resource(FontResolver.createFontMetric(URI.create(ttf).toURL()));
    }
    return new Resource(uri.toURL().openStream());
  }

  @Override
  public final OutputStream getOutputStream(URI uri) throws IOException {
    throw new UnsupportedOperationException();
  }

  /**
   * Load the font metric as {@link InputStream}.
   *
   * @param url
   */
  private static InputStream createFontMetric(URL url) throws IOException {
    try (InputStream stream = url.openStream()) {
      try (ByteArrayOutputStream metrics = new ByteArrayOutputStream()) {
        FontResolver.createFontMetric(stream, metrics);
        return new Resource(new ByteArrayInputStream(metrics.toByteArray()));
      }
    }
  }

  /**
   * Create a Font metric from a font definition (like TTF).
   *
   * @param inputStream
   * @param outputStream
   * @throws IOException
   */
  public static void createFontMetric(InputStream inputStream, OutputStream outputStream) throws IOException {
    TTFReader reader = new TTFReader();
    TTFFile ttf = new TTFFile(true, true);
    FontFileReader fontReader = new FontFileReader(inputStream);
    ttf.readFont(fontReader, null);
    if (ttf.isCFF()) {
      throw new UnsupportedOperationException("OpenType fonts with CFF data are not supported, yet");
    }

    DOMSource source = new DOMSource(reader.constructFontXML(ttf, null, null, null, null, true, null));
    try (OutputStream bufferedStream = new BufferedOutputStream(outputStream)) {
      Transformer transformer = TransformerFactory.newInstance().newTransformer();
      transformer.transform(source, new StreamResult(bufferedStream));
    } catch (TransformerException e) {
      throw new IOException(e);
    }
  }
}
