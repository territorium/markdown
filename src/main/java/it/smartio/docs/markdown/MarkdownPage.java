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

import org.commonmark.node.BlockQuote;
import org.commonmark.node.BulletList;
import org.commonmark.node.CustomBlock;
import org.commonmark.node.FencedCodeBlock;
import org.commonmark.node.Image;
import org.commonmark.node.IndentedCodeBlock;
import org.commonmark.node.LinkReferenceDefinition;
import org.commonmark.node.OrderedList;
import org.commonmark.node.Paragraph;
import org.commonmark.node.SoftLineBreak;
import org.commonmark.node.Text;
import org.commonmark.node.ThematicBreak;

import it.smartio.docs.Message.Style;
import it.smartio.docs.builder.ContentBuilder;
import it.smartio.docs.builder.ListBuilder;
import it.smartio.docs.builder.PageBuilder;
import it.smartio.docs.builder.ParagraphBuilder;
import it.smartio.docs.codeblock.CodeFactory;
import it.smartio.docs.markdown.alerts.AlertBlock;
import it.smartio.docs.markdown.images.ImageAttributes;
import it.smartio.docs.markdown.tables.Table;

/**
 * The {@link MarkdownPage} class.
 */
public class MarkdownPage extends MarkdownVisitor {

  private final PageBuilder content;

  /**
   * Constructs an instance of {@link MarkdownPage}.
   *
   * @param content
   */
  public MarkdownPage(PageBuilder content) {
    this.content = content;
  }

  /**
   * Get the current {@link ContentBuilder} node.
   */
  protected PageBuilder getContent() {
    return this.content;
  }

  /**
   * Visit the {@link SoftLineBreak}.
   *
   * @param node
   */
  @Override
  public final void visit(SoftLineBreak node) {
    getContent().addBreak();
  }

  /**
   * Visit the {@link ThematicBreak}.
   *
   * @param node
   */
  @Override
  public void visit(ThematicBreak node) {
    getContent().addLineBreak();
  }

  /**
   * Visit the {@link Paragraph}.
   *
   * @param node
   */
  @Override
  public final void visit(Paragraph node) {
    ContentBuilder content = getContent().addParagraph();
    MarkdownBuilder builder = new MarkdownBuilder(content, 0);
    builder.visitChildren(node);
  }

  /**
   * Process the {@link BlockQuote}.
   *
   * @param node
   */
  @Override
  public final void visit(BlockQuote node) {
    ParagraphBuilder content = getContent().addParagraph();
    content.setIntent(1);
    MarkdownBuilder builder = new MarkdownBuilder(content, 0);
    builder.visitChildren(node);
  }

  /**
   * Visit the {@link Image}.
   *
   * @param node
   */
  @Override
  public final void visit(Image node) {
    String title = null;
    String align = null;
    String width = null;
    String height = null;

    if (node.getFirstChild() instanceof Text) {
      Text text = (Text) node.getFirstChild();
      title = text.getLiteral();
    }
    if (node.getLastChild() instanceof ImageAttributes) {
      ImageAttributes attrs = (ImageAttributes) node.getLastChild();
      align = attrs.getAttributes().get("align");
      width = attrs.getAttributes().get("width");
      height = attrs.getAttributes().get("height");
    }
    getContent().addImage(node.getDestination(), title, align, width, height);
  }

  /**
   * Visit the {@link LinkReferenceDefinition}.
   *
   * @param node
   */
  @Override
  public final void visit(LinkReferenceDefinition node) {
    visitChildren(node);
  }

  /**
   * Visit the {@link FencedCodeBlock}.
   *
   * @param node
   */
  @Override
  public final void visit(FencedCodeBlock node) {
    CodeFactory.of(node.getInfo(), getContent()).parse(node.getLiteral());
  }

  /**
   * Process the {@link BulletList}.
   *
   * @param node
   */
  @Override
  public final void visit(BulletList node) {
    ListBuilder list = getContent().addList();
    MarkdownBuilder builder = new MarkdownBuilder(list, 0);
    builder.visitChildren(node);
  }

  /**
   * Process the {@link OrderedList}.
   *
   * @param node
   */
  @Override
  public final void visit(OrderedList node) {
    ListBuilder list = getContent().addOrderedList();
    MarkdownBuilder builder = new MarkdownBuilder(list, 0);
    builder.visitChildren(node);
  }

  /**
   * Process the {@link IndentedCodeBlock}.
   *
   * @param node
   */
  @Override
  public final void visit(IndentedCodeBlock node) {
    ContentBuilder code = getContent().addCode();
    code.addContent(node.getLiteral());
  }

  /**
   * Process the {@link CustomBlock}.
   *
   * @param node
   */
  @Override
  public final void visit(CustomBlock node) {
    if (node instanceof Table) {
      PageBuilder page = getContent();
      MarkdownTable builder = new MarkdownTable(page.addTable());
      builder.visitChildren(node);
    } else if (node instanceof AlertBlock) {
      PageBuilder page = getContent();
      ContentBuilder content = page.addNotification(MarkdownPage.getNotification(node));

      MarkdownBuilder builder = new MarkdownBuilder(content, 0);
      builder.visitChildren(node);
    } else {
      super.visit(node);
    }
  }

  /**
   * Get the {@link AlertBlock}.
   *
   * @param block
   */
  protected static Style getNotification(CustomBlock node) {
    AlertBlock block = (AlertBlock) node;
    switch (block.getType()) {
      case NOTE:
        return Style.INFO;
      case SUCCESS:
        return Style.SUCCESS;
      case WARNING:
        return Style.WARNING;
      case ERROR:
        return Style.ERROR;
      default:
        return Style.INFO;
    }
  }
}
