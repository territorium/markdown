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

import java.util.Properties;
import java.util.Random;

import it.smartio.docs.Book;
import it.smartio.docs.Chapter;
import it.smartio.docs.NodeVisitor;
import it.smartio.docs.Renderer;
import it.smartio.docs.fop.config.FoContext;
import it.smartio.docs.fop.nodes.FoBasicLink;
import it.smartio.docs.fop.nodes.FoBlock;
import it.smartio.docs.fop.nodes.FoFlow;
import it.smartio.docs.fop.nodes.FoLeader;
import it.smartio.docs.fop.nodes.FoPageNumberCitation;
import it.smartio.docs.fop.nodes.FoRoot;
import it.smartio.docs.util.PageUtil;

/**
 * The {@link FoRendererToC} class.
 */
class FoRendererToC implements Renderer, NodeVisitor<FoBlock> {

  private final FoContext data;
  private final String    title;

  /**
   * Constructs an instance of {@link FoRenderer}.
   *
   * @param root
   * @param title
   */
  public FoRendererToC(FoContext data, String title) {
    this.data = data;
    this.title = title;
  }

  /**
   * Get the {@link FoRoot}.
   */
  protected final FoContext getData() {
    return this.data;
  }

  /**
   * Renders the {@link Book}.
   *
   * @param node
   */
  @Override
  public final void render(Book node) {
    Properties properties = new Properties();
    properties.put("TITLE", "Table of Content");

    FoFlow flow = getData().createFlow(this.title, Fo.PAGESET_STANDARD, true, properties);
    flow.setStartIndent("0pt").setEndIndent("0pt");
    flow.addBlock().setBreakAfter("page");

    String id = Long.toHexString(new Random().nextLong());
    getData().addBookmark(id).setTitle(this.title);

    FoBlock content = FoBlock.block();
    content.setId(id);
    content.setSpaceBefore("0.5em", "1.0em", "2.0em");
    content.setSpaceAfter("0.5em", "1.0em", "2.0em");
    content.setColor("#000000").setTextAlign("left");
    flow.addNode(content);

    FoBlock block = content.addBlock();
    block.setSpaceBefore("1.0em", "1.5em", "2.0em");
    block.setSpaceAfter("0.5em").setStartIndent("0pt");
    block.setFontWeight("bold").setFontSize("18pt");
    block.addText("Table of Contents");

    node.nodes().forEach(n -> n.accept(this, content));
  }

  @Override
  public final void visit(Chapter node, FoBlock data) {
    int intent = node.getLevel() - 1;
    String title = PageUtil.encode(node.getTitle());

    FoBlock content = data.addBlock();
    content.setMarginLeft(String.format("%sem", intent));
    FoRendererToC.createEntry(node.getId(), content).addText(title);

    node.forEach(n -> n.accept(this, data));
  }

  /**
   * Creates an entry for the Table of Content- {@link #createEntry}.
   *
   * @param id
   * @param container
   */
  public static FoBasicLink createEntry(String id, FoBlock container) {
    FoBlock block = FoBlock.block();
    block.setTextAlign("start").setTextAlignLast("justify");
    block.setEndIndent("1em").setEndIndentLastLine("-1em");
    container.addNode(block);

    FoBasicLink link = new FoBasicLink(id);
    block.addInline().setKeepWithNext("always").addNode(link);

    FoBlock inline = block.addInline().setKeepWithNext("always");
    FoLeader leader = new FoLeader();
    leader.setPaddingLeftRight("3pt");
    leader.setPattern("dots").setWidth("3pt").setAlign("reference-area");
    inline.addNode(leader);

    FoBasicLink link2 = new FoBasicLink(id);
    link2.addNode(new FoPageNumberCitation(id));
    inline.addNode(link2);
    return link;
  }
}
