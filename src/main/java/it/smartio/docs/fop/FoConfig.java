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

import org.apache.fop.apps.FopFactory;
import org.apache.fop.apps.FopFactoryBuilder;
import org.apache.fop.fonts.EmbedFontInfo;
import org.apache.fop.fonts.apps.TTFReader;
import org.apache.fop.fonts.truetype.FontFileReader;
import org.apache.fop.fonts.truetype.TTFFile;
import org.apache.fop.render.RendererFactory;
import org.apache.xmlgraphics.io.Resource;
import org.apache.xmlgraphics.io.ResourceResolver;
import org.w3c.dom.Document;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Stack;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import it.smartio.docs.config.FoPage;
import it.smartio.docs.fop.builder.FoBlock;
import it.smartio.docs.fop.builder.FoFlow;
import it.smartio.docs.fop.builder.FoLeader;
import it.smartio.docs.fop.builder.FoNode;
import it.smartio.docs.fop.builder.FoPageSequence;
import it.smartio.docs.fop.builder.FoPageSequenceMaster;
import it.smartio.docs.fop.builder.FoRoot;
import it.smartio.docs.fop.builder.FoSimplePageMaster;
import it.smartio.docs.fop.builder.FoStaticContent;
import it.smartio.docs.util.DataUri;
import it.smartio.docs.util.StAX;

/**
 * The {@link FoConfig} class.
 */
public class FoConfig implements ResourceResolver {

  public static final String        PAGES_BOOK     = "book";
  public static final String        PAGES_CHAPTER  = "chapter";
  public static final String        PAGES_STANDARD = "standard";
  public static final String        REGION_BODY    = "region-body";

  private final File                base;
  private final FoRoot              root;
  private final Map<String, FoPage> pages          = new HashMap<>();
  private final Stack<FoNode>       nodes          = new Stack<>();
  private final List<EmbedFontInfo> fonts          = new ArrayList<>();

  /**
   * Constructs an instance of {@link FoConfig}.
   */
  public FoConfig(File base) {
    this.base = base;
    this.root = FoNode.root(Fo.FONT_TEXT);
    this.root.set("xmlns:fox", "http://xmlgraphics.apache.org/fop/extensions");
  }

  /**
   * Adds an {@link EmbedFontInfo}.
   *
   * @param embed
   */
  public final void addFontInfo(EmbedFontInfo embed) {
    this.fonts.add(embed);
  }

  /**
   * Adds a {@link FoSimplePageMaster}.
   *
   * @param name
   */
  public FoSimplePageMaster addSimplePage(String name) {
    FoSimplePageMaster master = new FoSimplePageMaster(name);
    getRoot().getLayouts().addNode(master);
    return master;
  }

  /**
   * Adds a {@link FoPageSequenceMaster}.
   *
   * @param name
   */
  public FoPageSequenceMaster addMasterPage(String name) {
    FoPageSequenceMaster master = new FoPageSequenceMaster(name);
    getRoot().getLayouts().addNode(master);
    return master;
  }

  /**
   * Get the {@link FoRoot}.
   */
  public final FoRoot getRoot() {
    return this.root;
  }

  /**
   * Get the top {@link FoNode}.
   */
  public final FoNode top() {
    return this.nodes.peek();
  }

  /**
   * Pop the top {@link FoNode}.
   */
  public final FoNode pop() {
    return this.nodes.pop();
  }

  /**
   * Push the a {@link FoNode}.
   *
   * @param node
   */
  public final FoNode push(FoNode node) {
    return this.nodes.push(node);
  }

  /**
   * Set the supplier that creates a {@link FoFlow}.
   *
   * @param name
   */
  public final FoPage get(String name) {
    return this.pages.get(name);
  }

  /**
   * Set the supplier that creates a {@link FoFlow}.
   *
   * @param name
   * @param page
   */
  public final void add(String name, FoPage page) {
    this.pages.put(name, page);
  }

  /**
   * Set the supplier that creates a {@link FoFlow}.
   *
   * @param id
   * @param name
   */
  public final FoFlow createFlow(String id, String name, boolean initial, Properties properties) {
    FoPageSequence page = getRoot().addPageSequence(name);
    page.setLanguage("en").setInitialPageNumber(initial ? "1" : "auto");
    page.setId(id);

    switch (name) {
      case FoConfig.PAGES_CHAPTER:
        page.setFormat("1");
        break;

      default:
        page.setFormat("I");
    }

    // Foot separator
    FoStaticContent content = new FoStaticContent("xsl-footnote-separator");
    FoBlock block = content.addBlock().setTextAlignLast("justify").setPadding("0.5em");
    page.addNode(content);

    FoLeader leader = new FoLeader();
    leader.setPattern("rule").setLength("50%");
    leader.setRuleThickness("0.5pt").setColor("#777777");
    block.addNode(leader);

    if (this.pages.containsKey(name)) {
      this.pages.get(name).render(page, properties);
    }

    FoFlow flow = page.flow(FoConfig.REGION_BODY);
    flow.setStartIndent("0pt").setEndIndent("0pt");
    return flow;
  }

  /**
   * Builds the the {@link FoRoot}.
   */
  @Override
  public final String toString() {
    return getRoot().build();
  }

  @Override
  public final Resource getResource(URI uri) throws IOException {
    System.out.println("resource:" + uri);
    if (uri.toString().startsWith("/")) {
      return new Resource(new FileInputStream(uri.toString()));
    } else if (uri.toString().endsWith("ttf.xml")) {
      String ttf = uri.toString().substring(0, uri.toString().length() - 4);
      try (InputStream stream = URI.create(ttf).toURL().openStream()) {
        try (ByteArrayOutputStream metrics = new ByteArrayOutputStream()) {
          FoConfig.createFontMetric(stream, metrics);
          return new Resource(new ByteArrayInputStream(metrics.toByteArray()));
        }
      }
    }
    return new Resource(uri.toURL().openStream());
  }

  @Override
  public final OutputStream getOutputStream(URI uri) throws IOException {
    throw new UnsupportedOperationException();
  }


  /**
   * Create a {@link FopFactory} for the current {@link FoConfig}.
   */
  public final FopFactory createFactory() {
    FopFactoryBuilder builder = new FopFactoryBuilder(base.toURI(), this);
    builder.setStrictFOValidation(true).setStrictUserConfigValidation(true);
    builder.setSourceResolution(72).setTargetResolution(72);
    builder.setPageWidth("297mm").setPageHeight("210mm");
    builder.setPreferRenderer(true);

    FopFactory factory = builder.build();
    RendererFactory rendererFactory = factory.getRendererFactory();
    rendererFactory.addDocumentHandlerMaker(new PDFRenderer(fonts));
    return factory;
  }

  public static void createFontMetric(InputStream inputStream, OutputStream outputStream) throws IOException {
    TTFReader reader = new TTFReader();
    TTFFile ttf = new TTFFile(true, true);
    FontFileReader fontReader = new FontFileReader(inputStream);
    ttf.readFont(fontReader, null);
    if (ttf.isCFF()) {
      throw new UnsupportedOperationException("OpenType fonts with CFF data are not supported, yet");
    }

    Document doc = reader.constructFontXML(ttf, null, null, null, null, true, null);
    try (OutputStream bufferedStream = new BufferedOutputStream(outputStream)) {
      TransformerFactory factory = TransformerFactory.newInstance();
      Transformer transformer = factory.newTransformer();
      transformer.transform(new DOMSource(doc), new StreamResult(bufferedStream));
    } catch (TransformerException e) {
      throw new IOException(e);
    }
  }

  /**
   * Parses the configuration from the file.
   *
   * @param resource
   */
  public static FoConfig parse(String resource) throws IOException {
    File baseFile = resource.startsWith(":") ? new File(".") : new File(resource).getParentFile();
    FoConfig config = new FoConfig(baseFile);
    try (InputStream inputStream = DataUri.toInputStream(baseFile, resource)) {
      FoConfigHandler handler = new FoConfigHandler(baseFile, config);
      StAX.parse(inputStream, handler);
    }
    return config;
  }
}