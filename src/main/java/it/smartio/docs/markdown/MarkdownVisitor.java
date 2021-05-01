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

package it.smartio.docs.markdown;

import org.commonmark.node.AbstractVisitor;
import org.commonmark.node.BlockQuote;
import org.commonmark.node.BulletList;
import org.commonmark.node.Code;
import org.commonmark.node.CustomBlock;
import org.commonmark.node.CustomNode;
import org.commonmark.node.Document;
import org.commonmark.node.Emphasis;
import org.commonmark.node.FencedCodeBlock;
import org.commonmark.node.HardLineBreak;
import org.commonmark.node.Heading;
import org.commonmark.node.HtmlBlock;
import org.commonmark.node.HtmlInline;
import org.commonmark.node.Image;
import org.commonmark.node.IndentedCodeBlock;
import org.commonmark.node.Link;
import org.commonmark.node.LinkReferenceDefinition;
import org.commonmark.node.ListItem;
import org.commonmark.node.Node;
import org.commonmark.node.OrderedList;
import org.commonmark.node.Paragraph;
import org.commonmark.node.SoftLineBreak;
import org.commonmark.node.StrongEmphasis;
import org.commonmark.node.Text;
import org.commonmark.node.ThematicBreak;

/**
 * The {@link MarkdownVisitor} class.
 */
class MarkdownVisitor extends AbstractVisitor {

  private final boolean isRequired;

  /**
   * Constructs an instance of {@link MarkdownVisitor}.
   */
  protected MarkdownVisitor() {
    this.isRequired = true;
  }

  /**
   * Constructs an instance of {@link MarkdownVisitor}.
   */
  protected MarkdownVisitor(boolean isRequired) {
    this.isRequired = isRequired;
  }

  /**
   * Visit the named {@link Node}.
   *
   * @param node
   * @param name
   */
  protected final void visit(Node node, String name) {
    if (this.isRequired) {
      throw new UnsupportedOperationException(name);
    }

    visitChildren(node);
  }

  /**
   * Visit the {@link Document}.
   *
   * @param node
   */
  @Override
  public void visit(Document node) {
    visit(node, "Document");
  }

  /**
   * Visit the {@link Heading}.
   *
   * @param node
   */
  @Override
  public void visit(Heading node) {
    visit(node, "Heading");
  }

  /**
   * Visit the {@link Paragraph}.
   *
   * @param node
   */
  @Override
  public void visit(Paragraph node) {
    visit(node, "Paragraph");
  }

  /**
   * Visit the {@link SoftLineBreak}.
   *
   * @param node
   */
  @Override
  public void visit(SoftLineBreak node) {
    visit(node, "SoftLineBreak");
  }

  /**
   * Visit the {@link HardLineBreak}.
   *
   * @param node
   */
  @Override
  public void visit(HardLineBreak node) {
    visit(node, "HardLineBreak");
  }

  /**
   * Visit the {@link ThematicBreak}.
   *
   * @param node
   */
  @Override
  public void visit(ThematicBreak node) {
    visit(node, "ThematicBreak");
  }

  /**
   * Visit the {@link BlockQuote}.
   *
   * @param node
   */
  @Override
  public void visit(BlockQuote node) {
    visit(node, "BlockQuote");
  }

  /**
   * Visit the {@link Text}.
   *
   * @param node
   */
  @Override
  public void visit(Text node) {
    visit(node, "Text");
  }

  /**
   * Visit the {@link Emphasis}.
   *
   * @param node
   */
  @Override
  public void visit(Emphasis node) {
    visit(node, "Emphasis");
  }

  /**
   * Visit the {@link StrongEmphasis}.
   *
   * @param node
   */
  @Override
  public void visit(StrongEmphasis node) {
    visit(node, "StrongEmphasis");
  }

  /**
   * Visit the {@link Link}.
   *
   * @param node
   */
  @Override
  public void visit(Link node) {
    visit(node, "Link");
  }

  /**
   * Visit the {@link LinkReferenceDefinition}.
   *
   * @param node
   */
  @Override
  public void visit(LinkReferenceDefinition node) {
    visit(node, "LinkReferenceDefinition");
  }

  /**
   * Visit the {@link Image}.
   *
   * @param node
   */
  @Override
  public void visit(Image node) {
    visit(node, "Image");
  }

  /**
   * Visit the {@link BulletList}.
   *
   * @param node
   */
  @Override
  public void visit(BulletList node) {
    visit(node, "BulletList");
  }

  /**
   * Visit the {@link OrderedList}.
   *
   * @param node
   */
  @Override
  public void visit(OrderedList node) {
    visit(node, "OrderedList");
  }

  /**
   * Visit the {@link ListItem}.
   *
   * @param node
   */
  @Override
  public void visit(ListItem node) {
    visit(node, "ListItem");
  }

  /**
   * Visit the {@link Code}.
   *
   * @param node
   */
  @Override
  public void visit(Code node) {
    visit(node, "Code");
  }

  /**
   * Visit the {@link IndentedCodeBlock}.
   *
   * @param node
   */
  @Override
  public void visit(IndentedCodeBlock node) {
    visit(node, "IndentedCodeBlock");
  }

  /**
   * Visit the {@link FencedCodeBlock}.
   *
   * @param node
   */
  @Override
  public void visit(FencedCodeBlock node) {
    visit(node, "FencedCodeBlock");
  }

  /**
   * Visit the {@link HtmlBlock}.
   *
   * @param node
   */
  @Override
  public void visit(HtmlBlock node) {
    visit(node, "HtmlBlock");
  }

  /**
   * Visit the {@link HtmlInline}.
   *
   * @param node
   */
  @Override
  public void visit(HtmlInline node) {
    visit(node, "HtmlInline");
  }

  /**
   * Visit the {@link CustomBlock}.
   *
   * @param node
   */
  @Override
  public void visit(CustomBlock node) {
    visit(node, "CustomBlock");
  }

  /**
   * Visit the {@link CustomNode}.
   *
   * @param node
   */
  @Override
  public void visit(CustomNode node) {
    visit(node, "CustomNode");
  }

  /**
   * Visit the child nodes.
   *
   * @param node
   */
  @Override
  public final void visitChildren(Node node) {
    super.visitChildren(node);
  }
}
