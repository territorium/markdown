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

package it.smartio.docs.fop;

import org.apache.fop.apps.FOUserAgent;
import org.apache.fop.apps.Fop;
import org.apache.fop.apps.FopFactory;
import org.apache.xmlgraphics.util.MimeConstants;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Properties;

import javax.xml.transform.Result;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.sax.SAXResult;
import javax.xml.transform.stream.StreamSource;

import it.smartio.docs.Book;
import it.smartio.docs.Renderer;
import it.smartio.docs.fop.config.FoContext;
import it.smartio.docs.markdown.MarkdownReader;

/**
 * The {@link FoGenerator} class.
 */
public class FoGenerator {

  private final File       source;
  private final Properties properties = new Properties();

  private String           name;
  private String           config;
  private File             target;
  private boolean          debug;

  /**
   * Constructs an instance of {@link FoGenerator}.
   *
   * @param source
   */
  public FoGenerator(File source) {
    this.source = source;
  }

  /**
   * Add properties to the {@link FoGenerator}.
   *
   * @param properties
   */
  public final FoGenerator addProperties(Properties properties) {
    this.properties.putAll(properties);
    return this;
  }

  /**
   * Sets the filename
   */
  public final FoGenerator setName(String name) {
    this.name = name;
    return this;
  }

  /**
   * Sets the config directory.
   */
  public final FoGenerator setConfig(String config) {
    this.config = config;
    return this;
  }

  /**
   * Sets the target directory.
   */
  public final FoGenerator setTarget(File target) {
    this.target = target;
    return this;
  }

  /**
   * Sets the <code>true</code> if the fo file will be generated
   */
  public final FoGenerator setDebug(boolean debug) {
    this.debug = debug;
    return this;
  }

  /**
   * Writes the {@link Book} to FO.
   *
   * @param book
   * @param config
   * @param writer
   * @throws IOException
   */
  protected static void writeFo(Book book, FoContext config, Writer writer) throws IOException {
    try (PrintWriter printer = new PrintWriter(writer)) {
      Renderer renderer = new FoRenderer(printer, config);
      renderer.render(book);
    }
  }

  /**
   * Writes the FO stream to PDF.
   *
   * @param file
   * @param stream
   * @param factory
   */
  protected static void writePdf(File file, InputStream source, FopFactory factory) throws Exception {
    // a user agent is needed for transformation
    FOUserAgent foUserAgent = factory.newFOUserAgent();
    // foUserAgent.getRendererOptions().put("encryption-length", 128);
    // foUserAgent.getRendererOptions().put("user-password", "test");
    // foUserAgent.getRendererOptions().put("owner-password", "test");
    // foUserAgent.getRendererOptions().put("noprint", "true");
    // foUserAgent.getRendererOptions().put("nocopy", "true");
    // foUserAgent.getRendererOptions().put("noedit", "true");
    // foUserAgent.getRendererOptions().put("noannotations", "true");
    // foUserAgent.getRendererOptions().put("nofillinforms", "true");
    // foUserAgent.getRendererOptions().put("noaccesscontent", "true");
    // foUserAgent.getRendererOptions().put("noassembledoc", "true");
    // foUserAgent.getRendererOptions().put("noprinthq", "true");

    try (OutputStream target = new FileOutputStream(file)) {
      // Step 1: Construct a FopFactory by specifying a reference to the
      // configuration file (reuse if you plan to render multiple documents!)

      // Step 2: Construct fop with desired output format
      Fop fop = factory.newFop(MimeConstants.MIME_PDF, foUserAgent, target);

      // Step 3: Setup JAXP using identity transformer
      Transformer transformer = TransformerFactory.newInstance().newTransformer();

      // Step 4: Setup input and output for XSLT transformation
      Result result = new SAXResult(fop.getDefaultHandler());

      // Step 5: Start XSLT transformation and FOP processing
      transformer.transform(new StreamSource(source), result);
    }
  }

  /**
   * Build the markdown based book.
   *
   * @throws IOException
   */
  public final Book buildBook() throws IOException {
    MarkdownReader parser = new MarkdownReader(this.properties);
    return parser.parse(this.source);
  }

  /**
   * Get the FO {@link InputStream}.
   *
   * @param book
   * @param config
   */
  protected final InputStream getFoStream(Book book, FoContext config) throws IOException {
    if (this.debug) {
      String name = this.name;
      File fo = new File(this.target, name + ".fo");
      if (fo.exists()) {
        fo.delete();
      }

      FileWriter writer = new FileWriter(fo);
      FoGenerator.writeFo(book, config, writer);
      return new FileInputStream(fo);
    }

    StringWriter writer = new StringWriter();
    FoGenerator.writeFo(book, config, writer);
    return new ByteArrayInputStream(writer.toString().getBytes("UTF-8"));
  }

  /**
   * Build the markdown based book.
   *
   * @throws IOException
   */
  public final void generate() throws IOException {
    MarkdownReader parser = new MarkdownReader(this.properties);
    Book book = parser.parse(this.source);

    String name = this.name;
    if (!this.target.exists()) {
      this.target.mkdirs();
    }

    File pdf = new File(this.target, name + ".pdf");
    if (pdf.exists()) {
      pdf.delete();
    }

    FoContext config = FoContext.parse(this.config);
    try (InputStream stream = getFoStream(book, config)) {
      FoGenerator.writePdf(pdf, stream, config.getFactory());
    } catch (Throwable e) {
      throw new IOException(e);
    }
  }
}
