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

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Properties;
import java.util.Stack;

import it.smartio.docs.fop.Fo;
import it.smartio.docs.fop.nodes.FoBlock;
import it.smartio.docs.fop.nodes.FoBookmark;
import it.smartio.docs.fop.nodes.FoFlow;
import it.smartio.docs.fop.nodes.FoLeader;
import it.smartio.docs.fop.nodes.FoNode;
import it.smartio.docs.fop.nodes.FoPageSequence;
import it.smartio.docs.fop.nodes.FoRoot;
import it.smartio.docs.fop.nodes.FoStaticContent;
import it.smartio.docs.util.DataUri;
import it.smartio.docs.util.StAX;

/**
 * The {@link FoContext} class.
 */
public class FoContext {

  private final FoRoot                      root;
  private final FopFactory                  factory;
  private final Map<String, UIPageSequence> pageSet;
  private final Stack<FoNode>               nodes = new Stack<>();


  /**
   * Constructs an instance of {@link FoContext}.
   *
   * @param root
   * @param factory
   * @param pageSet
   */
  public FoContext(FoRoot root, FopFactory factory, Map<String, UIPageSequence> pageSet) {
    this.root = root;
    this.factory = factory;
    this.pageSet = pageSet;
  }


  /**
   * Create a {@link FopFactory} for the current {@link FoContext}.
   */
  public final FopFactory getFactory() {
    return this.factory;
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
   * Add a bookmark by id.
   *
   * @param id
   */
  public final FoBookmark addBookmark(String id) {
    return this.root.addBookmark(id);
  }

  /**
   * Set the supplier that creates a {@link FoFlow}.
   *
   * @param id
   * @param name
   */
  public final FoFlow createFlow(String id, String name, boolean initial, Properties properties) {
    FoPageSequence page = this.root.addPageSequence(name);
    page.setLanguage("en").setInitialPageNumber(initial ? "1" : "auto");
    page.setId(id);

    switch (name) {
      case Fo.PAGESET_CHAPTER:
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

    if (this.pageSet.containsKey(name)) {
      this.pageSet.get(name).render(page, properties);
    }

    FoFlow flow = page.flow(Fo.PAGE_CONTENT);
    flow.setStartIndent("0pt").setEndIndent("0pt");
    return flow;
  }

  /**
   * Builds the the {@link FoRoot}.
   */
  @Override
  public final String toString() {
    return this.root.build();
  }

  /**
   * Parses the configuration from the file.
   *
   * @param config
   */
  public static FoContext parse(String config) throws IOException {
    File workingDir = config.startsWith(":") ? new File(".") : new File(config).getParentFile();
    UITemplate template = new UITemplate(workingDir);
    try (InputStream inputStream = DataUri.toInputStream(workingDir, config)) {
      StAX.parse(inputStream, new FoContextHandler(template));
    }

    return template.build();
  }
}