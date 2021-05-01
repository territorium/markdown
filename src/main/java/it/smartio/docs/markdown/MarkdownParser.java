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

import java.util.Stack;

import it.smartio.docs.builder.BookBuilder;
import it.smartio.docs.builder.PageBuilder;

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
import org.commonmark.node.OrderedList;
import org.commonmark.node.Paragraph;
import org.commonmark.node.SoftLineBreak;
import org.commonmark.node.StrongEmphasis;
import org.commonmark.node.Text;
import org.commonmark.node.ThematicBreak;

/**
 * The {@link MarkdownParser} implements a reader based on markdown. The Reader supports reading of
 * documents organized in multiple files.
 */
public class MarkdownParser extends MarkdownVisitor {

  private final BookBuilder         book;
  private final MarkdownPage        visitor;

  private final Stack<PageBuilder>  pages    = new Stack<>();
  private final Stack<MarkdownPage> visitors = new Stack<>();

  /**
   * Constructs an instance of {@link MarkdownParser}.
   *
   * @param book
   */
  public MarkdownParser(BookBuilder book) {
    this.book = book;
    this.visitor = new MarkdownPage(book);
  }

  /**
   * Return the top {@link BookBuilder} or the page of the preface.
   */
  protected final BookBuilder getBook() {
    return this.book;
  }

  /**
   * Return the top {@link MarkdownPage} or the page of the preface.
   */
  protected final MarkdownPage top() {
    return this.visitors.isEmpty() ? this.visitor : this.visitors.peek();
  }

  /**
   * Visit a {@link Document} node.
   *
   * @param node
   */
  @Override
  public final void visit(Document node) {
    visitChildren(node);
  }

  /**
   * Visit a {@link Heading} node.
   *
   * @param node
   */
  @Override
  public final void visit(Heading node) {
    switch (node.getLevel()) {
      case 1: // Book
        TitleVisitor visitor = new TitleVisitor(getBook());
        visitor.visitChildren(node);
        break;

      case 2: // Chapter
        while (!this.pages.isEmpty()) {
          this.pages.pop();
          this.visitors.pop();
        }

        this.pages.add(getBook().addChapter());
        this.visitors.push(new MarkdownPage(this.pages.peek()));

        visitor = new TitleVisitor(this.pages.peek());
        visitor.visitChildren(node);
        break;

      default: // Section
        while (!this.pages.isEmpty() && (node.getLevel() <= this.pages.peek().getLevel())) {
          this.pages.pop();
          this.visitors.pop();
        }

        if (!this.pages.isEmpty() && (node.getLevel() == (this.pages.peek().getLevel() + 1))) {
          this.pages.pop();
          this.visitors.pop();
        }

        if (this.pages.isEmpty() || (node.getLevel() == (this.pages.peek().getLevel() + 2))) {
          this.pages.add(top().getContent().addSection());
          this.visitors.push(new MarkdownPage(this.pages.peek()));
        }

        visitor = new TitleVisitor(this.pages.peek());
        visitor.visitChildren(node);
    }
  }

  /**
   * Process a {@link Paragraph} node.
   *
   * @param node
   */
  @Override
  public final void visit(Paragraph node) {
    top().visit(node);
  }

  /**
   * Visit a {@link SoftLineBreak} node.
   *
   * @param node
   */
  @Override
  public final void visit(SoftLineBreak node) {
    top().visit(node);
  }

  /**
   * Visit a {@link HardLineBreak} node.
   *
   * @param node
   */
  @Override
  public final void visit(HardLineBreak node) {
    top().visit(node);
  }

  /**
   * Visit a {@link ThematicBreak} node.
   *
   * @param node
   */
  @Override
  public final void visit(ThematicBreak node) {
    top().visit(node);
  }

  /**
   * Visit a {@link Text} node.
   *
   * @param node
   */
  @Override
  public final void visit(Text node) {
    top().visit(node);
  }

  /**
   * Visit a {@link Emphasis} node.
   *
   * @param node
   */
  @Override
  public final void visit(Emphasis node) {
    top().visit(node);
  }

  /**
   * Visit a {@link StrongEmphasis} node.
   *
   * @param node
   */
  @Override
  public final void visit(StrongEmphasis node) {
    top().visit(node);
  }

  /**
   * Visit a {@link Link} node.
   *
   * @param node
   */
  @Override
  public final void visit(Link node) {
    top().visit(node);
  }

  /**
   * Visit a {@link LinkReferenceDefinition} node.
   *
   * @param node
   */
  @Override
  public final void visit(LinkReferenceDefinition node) {
    top().visit(node);
  }

  /**
   * Visit a {@link Link} node.
   *
   * @param node
   */
  @Override
  public final void visit(Image node) {
    top().visit(node);
  }

  /**
   * Visit a {@link BulletList} node.
   *
   * @param node
   */
  @Override
  public final void visit(BulletList node) {
    top().visit(node);
  }

  /**
   * Visit a {@link OrderedList} node.
   *
   * @param node
   */
  @Override
  public final void visit(OrderedList node) {
    top().visit(node);
  }

  /**
   * Visit a {@link BlockQuote} node.
   *
   * @param node
   */
  @Override
  public final void visit(BlockQuote node) {
    top().visit(node);
  }

  /**
   * Visit a {@link Code} node.
   *
   * @param node
   */
  @Override
  public final void visit(Code node) {
    top().visit(node);
  }

  /**
   * Visit a {@link IndentedCodeBlock} node.
   *
   * @param node
   */
  @Override
  public final void visit(IndentedCodeBlock node) {
    top().visit(node);
  }

  /**
   * Visit a {@link FencedCodeBlock} node.
   *
   * @param node
   */
  @Override
  public final void visit(FencedCodeBlock node) {
    top().visit(node);
  }

  /**
   * Visit a {@link FencedCodeBlock} node.
   *
   * @param node
   */
  @Override
  public final void visit(CustomBlock node) {
    top().visit(node);
  }

  /**
   * Visit a {@link CustomNode} node.
   *
   * @param node
   */
  @Override
  public final void visit(CustomNode node) {
    top().visit(node);
  }

  /**
   * Visit a {@link HtmlInline} node.
   *
   * @param node
   */
  @Override
  public final void visit(HtmlInline node) {
    top().visit(node);
  }

  /**
   * Visit a {@link HtmlBlock} node.
   *
   * @param node
   */
  @Override
  public final void visit(HtmlBlock node) {
    top().visit(node);
  }

  /**
   * The {@link TitleVisitor} class.
   */
  private static class TitleVisitor extends MarkdownVisitor {

    private final PageBuilder builder;

    /**
     * Constructs an instance of {@link TitleVisitor}.
     *
     * @param builder
     */
    public TitleVisitor(PageBuilder builder) {
      this.builder = builder;
    }

    /**
     * Visit a {@link Text} node.
     *
     * @param node
     */
    @Override
    public final void visit(Text node) {
      this.builder.setTitle(node.getLiteral());
    }
  }
}
