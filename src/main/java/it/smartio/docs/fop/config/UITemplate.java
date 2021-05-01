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

import org.apache.fop.apps.FopFactory;
import org.apache.fop.apps.FopFactoryBuilder;
import org.apache.fop.fonts.EmbedFontInfo;
import org.apache.fop.render.RendererFactory;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import it.smartio.docs.fop.Fo;
import it.smartio.docs.fop.nodes.FoNode;
import it.smartio.docs.fop.nodes.FoRoot;

/**
 * The {@link UITemplate} class.
 */
class UITemplate {

  private final File workingDir;

  private String     width;
  private String     height;


  private final Map<String, UIFont>         fonts    = new LinkedHashMap<>();
  private final Map<String, UIPage>         pages    = new LinkedHashMap<>();
  private final Map<String, UIPageSequence> pageSets = new LinkedHashMap<>();

  /**
   * Constructs an instance of {@link UITemplate}.
   *
   * @param workingDir
   */
  public UITemplate(File workingDir) {
    this.workingDir = workingDir;
  }

  /**
   * Gets the working directory.
   */
  public final File getWorkingDir() {
    return this.workingDir;
  }

  /**
   * Gets the width.
   */
  public final String getWidth() {
    return this.width;
  }

  /**
   * Gets the height.
   */
  public final String getHeight() {
    return this.height;
  }

  /**
   * Get {@link UIPage} by name.
   *
   * @param name
   */
  public final UIPage getPage(String name) {
    return this.pages.get(name);
  }

  /**
   * Sets the size.
   *
   * @param width
   * @param height
   */
  public final UITemplate setSize(String width, String height) {
    this.width = width;
    this.height = height;
    return this;
  }

  /**
   * Add a named {@link UIPage}.
   *
   * @param name
   */
  public final UIPage addPage(String name) {
    UIPage page = new UIPage(name);
    this.pages.put(name, page);
    return page;
  }

  /**
   * Add a named {@link UIPage}.
   *
   * @param name
   */
  public final UIPageSequence addPageSet(String name) {
    this.pageSets.put(name, new UIPageSequence(name));
    return this.pageSets.get(name);
  }

  /**
   * Adds a font.
   *
   * @param name
   */
  public final UIFont addFontName(String name) {
    this.fonts.put(name, new UIFont(name));
    return this.fonts.get(name);
  }


  /**
   * Create a {@link FopFactory} for the current {@link FoContext}.
   */
  public final FopFactory createFactory() {
    FopFactoryBuilder builder = new FopFactoryBuilder(getWorkingDir().toURI(), new FontResolver());
    builder.setStrictFOValidation(true).setStrictUserConfigValidation(true);
    builder.setSourceResolution(72).setTargetResolution(72);
    builder.setPageWidth(getWidth()).setPageHeight(getHeight());
    builder.setPreferRenderer(true);

    FopFactory factory = builder.build();
    RendererFactory rendererFactory = factory.getRendererFactory();


    List<EmbedFontInfo> embedFonts = new ArrayList<>();
    this.fonts.values().forEach(f -> f.build(embedFonts));
    rendererFactory.addDocumentHandlerMaker(new PDFRenderer(embedFonts));
    return factory;
  }

  /**
   * Creates the {@link FoContext}.
   *
   * @param config
   */
  public FoContext build() {
    FoRoot root = FoNode.root(Fo.FONT_TEXT);
    root.set("xmlns:fox", "http://xmlgraphics.apache.org/fop/extensions");

    UIFont.loadDefaults(this.fonts);
    UIPageSequence.loadDefaults(this);

    this.pages.values().stream().map(UIPage::getSimplePage).forEach(p -> root.getLayouts().addNode(p));
    this.pageSets.values().stream().map(UIPageSequence::getPageSet).forEach(s -> root.getLayouts().addNode(s));

    FopFactory factory = createFactory();
    return new FoContext(root, factory, this.pageSets);
  }
}
