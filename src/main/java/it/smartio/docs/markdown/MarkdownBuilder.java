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
import org.commonmark.node.Code;
import org.commonmark.node.CustomNode;
import org.commonmark.node.Emphasis;
import org.commonmark.node.FencedCodeBlock;
import org.commonmark.node.HtmlInline;
import org.commonmark.node.Image;
import org.commonmark.node.IndentedCodeBlock;
import org.commonmark.node.Link;
import org.commonmark.node.ListItem;
import org.commonmark.node.OrderedList;
import org.commonmark.node.Paragraph;
import org.commonmark.node.SoftLineBreak;
import org.commonmark.node.StrongEmphasis;
import org.commonmark.node.Text;
import org.commonmark.node.ThematicBreak;

import it.smartio.docs.builder.ContentBuilder;
import it.smartio.docs.builder.InlineBuilder;
import it.smartio.docs.builder.ListBuilder;
import it.smartio.docs.builder.ParagraphBuilder;
import it.smartio.docs.codeblock.CodeFactory;
import it.smartio.docs.markdown.images.ImageAttributes;
import it.smartio.docs.markdown.markers.Marker;

/**
 * The {@link MarkdownBuilder} class.
 */
public class MarkdownBuilder extends MarkdownVisitor {

  private final int            intent;
  private final ContentBuilder content;

  /**
   * Constructs an instance of {@link MarkdownBuilder}.
   *
   * @param content
   * @param intent
   */
  public MarkdownBuilder(ContentBuilder content, int intent) {
    super(false);
    this.content = content;
    this.intent = intent;
  }

  /**
   * Get the current {@link ContentBuilder} node.
   */
  protected ContentBuilder getContent() {
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
    MarkdownBuilder builder = new MarkdownBuilder(content, this.intent);
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
    content.setIntent(this.intent + 1);
    MarkdownBuilder builder = new MarkdownBuilder(content, this.intent + 1);
    builder.visitChildren(node);
  }

  /**
   * Visit the {@link Text}.
   *
   * @param node
   */
  @Override
  public final void visit(Text node) {
    getContent().addContent(node.getLiteral());
  }

  /**
   * Visit the {@link Emphasis}.
   *
   * @param node
   */
  @Override
  public final void visit(Emphasis node) {
    InlineBuilder content = getContent().addInline();
    content.setItalic();
    MarkdownBuilder builder = new MarkdownBuilder(content, this.intent);
    builder.visitChildren(node);
  }

  /**
   * Visit the {@link StrongEmphasis}.
   *
   * @param node
   */
  @Override
  public final void visit(StrongEmphasis node) {
    InlineBuilder content = getContent().addInline();
    content.setBold();
    MarkdownBuilder builder = new MarkdownBuilder(content, this.intent);
    builder.visitChildren(node);
  }

  /**
   * Visit the {@link Link}.
   *
   * @param node
   */
  @Override
  public final void visit(Link node) {
    if (node.getFirstChild() != null) {
      ContentBuilder content = getContent().addLink(node.getDestination());
      MarkdownBuilder builder = new MarkdownBuilder(content, this.intent);
      builder.visitChildren(node);
    }

    if (node.getTitle() != null) {
      InlineBuilder content = getContent().addFootnote();
      content.addLink(node.getDestination()).addContent(node.getTitle());
    }
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
   * Process the {@link BulletList}.
   *
   * @param node
   */
  @Override
  public final void visit(BulletList node) {
    ListBuilder list = getContent().addList();
    MarkdownBuilder builder = new MarkdownBuilder(list, this.intent);
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
    MarkdownBuilder builder = new MarkdownBuilder(list, this.intent);
    builder.visitChildren(node);
  }

  /**
   * Visit the {@link ListItem}.
   *
   * @param node
   */
  @Override
  public final void visit(ListItem node) {
    if (getContent() == null) {
      throw new IllegalArgumentException();
    }

    ContentBuilder content = getContent();// ((ListBuilder) getContent()).newItem();
    MarkdownBuilder builder = new MarkdownBuilder(content, this.intent);
    builder.visitChildren(node);
  }

  /**
   * Visit the {@link Code}.
   *
   * @param node
   */
  @Override
  public final void visit(Code node) {
    getContent().addInlineCode(node.getLiteral());
  }

  /**
   * Visit the {@link IndentedCodeBlock}.
   *
   * @param node
   */
  @Override
  public final void visit(IndentedCodeBlock node) {
    ContentBuilder code = getContent().addCode();
    code.addContent(node.getLiteral());
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
   * Visit the {@link HtmlInline}.
   *
   * @param node
   */
  @Override
  public final void visit(HtmlInline node) {
    getContent().addContent(node.getLiteral());
  }

  /**
   * Visit the {@link CustomNode}.
   *
   * @param node
   */
  @Override
  public final void visit(CustomNode node) {
    if (node instanceof Marker) {
      InlineBuilder content = getContent().addInline();
      switch (((Marker) node).getDecoration()) {
        case Highlight:
          content.setOverline();
          content.setUnderline();
          break;
        case Overline:
          content.setOverline();
          break;
        case Underline:
          content.setUnderline();
          break;
        default:
          content.setStrikethrough();
          break;
      }

      MarkdownBuilder builder = new MarkdownBuilder(content, this.intent);
      builder.visitChildren(node);
    } else {
      super.visit(node);
    }
  }
}
