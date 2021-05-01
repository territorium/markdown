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

import java.io.PrintWriter;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;
import java.util.stream.Collectors;

import it.smartio.docs.Book;
import it.smartio.docs.Chapter;
import it.smartio.docs.CodeNode;
import it.smartio.docs.Image;
import it.smartio.docs.Inline;
import it.smartio.docs.Link;
import it.smartio.docs.List;
import it.smartio.docs.Message;
import it.smartio.docs.Message.Style;
import it.smartio.docs.NodeVisitor;
import it.smartio.docs.Paragraph;
import it.smartio.docs.Renderer;
import it.smartio.docs.Table;
import it.smartio.docs.Table.AreaType;
import it.smartio.docs.Table.Column;
import it.smartio.docs.Table.Row;
import it.smartio.docs.Text;
import it.smartio.docs.fop.config.FoContext;
import it.smartio.docs.fop.config.FontSymbols;
import it.smartio.docs.fop.nodes.FoBasicLink;
import it.smartio.docs.fop.nodes.FoBlock;
import it.smartio.docs.fop.nodes.FoBookmark;
import it.smartio.docs.fop.nodes.FoExternalGraphic;
import it.smartio.docs.fop.nodes.FoFlow;
import it.smartio.docs.fop.nodes.FoFootnote;
import it.smartio.docs.fop.nodes.FoListBlock;
import it.smartio.docs.fop.nodes.FoListItem;
import it.smartio.docs.fop.nodes.FoTable;
import it.smartio.docs.fop.nodes.FoTableArea;
import it.smartio.docs.fop.nodes.FoTableCell;
import it.smartio.docs.fop.nodes.FoTableRow;
import it.smartio.docs.util.PageUtil;

/**
 * The {@link FoRenderer} class.
 */
class FoRenderer implements Renderer, NodeVisitor<FoContext> {

  private static final String BOOK_ID = "ROOT";

  private final PrintWriter   writer;
  private final FoContext     config;

  /**
   * Constructs an instance of {@link FoRenderer}.
   *
   * @param writer
   * @param layout
   */
  public FoRenderer(PrintWriter writer, FoContext config) {
    this.writer = writer;
    this.config = config;
  }

  /**
   * Get the {@link PrintWriter}.
   */
  protected final PrintWriter getWriter() {
    return this.writer;
  }

  /**
   * Renders the {@link Book}.
   *
   * @param node
   */
  @Override
  public final void render(Book node) {
    String title = PageUtil.encode(node.getTitle().trim());

    // Renders the Bookmark
    FoBookmark bookmark = this.config.addBookmark(FoRenderer.BOOK_ID).setTitle(title);
    node.nodes().forEach(n -> n.accept(new FoRendererBookmark(), bookmark));

    // Renders the cover page
    Properties properties = new Properties();
    properties.put("TITLE", title);

    FoFlow flow = this.config.createFlow(FoRenderer.BOOK_ID, Fo.PAGESET_BOOK, false, properties);
    flow.addBlock().setBreakAfter("page").setColor("white").setLineHeight("1.4em").setFontSize("26pt")
        .setFontWeight("bold").addContent(title);

    this.config.push(flow.addBlock().setBreakBefore("page"));
    node.nodes().forEach(c -> c.accept(this, this.config));
    this.config.pop();

    Renderer renderer = new FoRendererToC(this.config, "Table of Contents");
    renderer.render(node); // Table of Content

    getWriter().write(this.config.toString());
  }

  /**
   * Renders a {@link Chapter} node.
   *
   * @param node
   * @param data
   */
  @Override
  public final void visit(Chapter node, FoContext data) {
    switch (node.getLevel()) {
      case 1:
        String title = PageUtil.encode(node.getTitle());
        Properties properties = new Properties();
        properties.put("TITLE", title);
        properties.put("CHAPTER", PageUtil.getPageNumber(node));

        FoFlow flow = data.createFlow(node.getId(), Fo.PAGESET_CHAPTER, false, properties);

        flow.addBlock().setSpan("all").setMarginBottom("2em").setBorderBottom("1pt", "solid", "#a6d4d9");
        FoBlock index = flow.addBlock();
        index.setColor("#eeeeee").setFontSize("12pt").setTextAlign("left").setLineHeight("1.8em");

        node.nodes().stream().filter(n -> n instanceof Chapter).map(n -> (Chapter) n).forEach(
            p -> FoRendererToC.createEntry(p.getId(), index.addBlock()).addText(PageUtil.encode(p.getTitle())));

        data.push(flow.addBlock().setBreakBefore("page"));
        node.nodes().forEach(c -> c.accept(this, data));
        data.pop();
        break;

      default:
        String number =
            (node.getParent().getOffset() < 0) ? PageUtil.getRomanNumber(node) : PageUtil.getPageNumber(node);

        FoBlock block = FoBlock.block();
        block.setFontWeight("bold").setFontSize("24pt");
        block.setColor("#349aa3");
        block.setId(node.getId());
        data.top().addNode(block);

        FoBlock block1 = block.addBlock();
        block1.setLineHeight("1.2em");
        block1.setTextAlign("start").setKeepWithNext("always");
        block1.setSpaceBefore("1em * 0.8", "1em", "1em * 1.2");
        block1.setSpaceAfter("0.5em * 0.8", "0.5em", "0.5em * 1.2");
        block1.addContent(String.format("%s.%s", number, PageUtil.encode(node.getTitle())));

        FoBlock content = FoBlock.block();
        data.top().addNode(content);
        data.push(content);
        node.nodes().forEach(c -> c.accept(this, data));
        data.pop();
        break;
    }
  }

  /**
   * Renders a {@link Paragraph} node with different kind of break's.
   *
   * @param node
   * @param data
   */
  @Override
  public final void visit(Paragraph node, FoContext data) {
    FoBlock content = FoBlock.block().setLineHeight("1.4em");

    if (node.getIntent() > 0) { // Blockquote
      content.setMarginLeft(String.format("%sin", 0.15 * node.getIntent()));
      content.setPaddingLeft(".7em");
      content.setBorderLeft("4pt", "solid", "#dddddd");
    } else if (node.getPaddingTop() != null) { // Custom paragraph
      content.setBackgroundColor(node.getBackground());
      content.setMargin(node.getPaddingLeft(), node.getPaddingRight(), node.getPaddingTop(), node.getPaddingBottom());
    } else if (node.isSoftBreak()) { // Soft break
      content.setSpaceBefore("0").setSpaceAfter("0");
    } else { // Standard break
      content.setSpaceBefore("0.5em").setSpaceAfter("0.5em");
      if (node.isLineBreak()) {
        content.set("page-break-before", "always");
      }
    }
    data.top().addNode(content);

    data.push(content);
    node.nodes().forEach(c -> c.accept(this, data));
    data.pop();
  }

  /**
   * Renders a {@link Text} node. The renderer encodes all XML keywords and symbols with font icons.
   *
   * @param node
   * @param data
   */
  @Override
  public final void visit(Text node, FoContext data) {
    String text = PageUtil.encode(node.getText());
    if (node.isCode()) {
      FoBlock content = FoBlock.inline();
      content.setTextAlign("start");
      content.setFontSize("10pt");
      content.setFontFamily(Fo.FONT_CODE);
      content.setBackgroundColor("#eeeeee");
      content.setPadding("0.2em", "0.2em");
      content.setBorder(".5pt", "solid", "#aaaaaa");
      content.setBorderRadius(".2em");
      content.addText(text);
      data.top().addNode(content);
    } else {
      data.top().addText(FontSymbols.forEach(text,
          (s, f) -> FoBlock.inline().setFontFamily(f).setFontSize("14pt").addContent("" + s).build()));
    }
  }

  /**
   * Renders an {@link Image} node. Optionally a description will be provided.
   *
   * @param node
   * @param data
   */
  @Override
  public final void visit(Image node, FoContext data) {
    if (node.getAlign() != null) {
      data.top().set("text-align", node.getAlign());
    }

    FoExternalGraphic graphic = new FoExternalGraphic(node.getUrl());
    graphic.setSize(node.getWidth(), node.getHeight());
    graphic.set("content-width", "scale-to-fit");
    graphic.set("content-height", "scale-to-fit");
    data.top().addNode(graphic);

    if (node.getText() != null) {
      String text = PageUtil.encode(node.getText());
      data.top().addNode(FoBlock.block().setFontStyle("italic").addContent(text));
    }
  }

  /**
   * Renders a {@link Link} node.
   *
   * @param node
   * @param data
   */
  @Override
  public final void visit(Link node, FoContext data) {
    FoBasicLink link = new FoBasicLink(node.getLink()).setColor("#4da8b3");
    data.top().addNode(link);

    data.push(link);
    node.forEach(c -> c.accept(this, data));
    data.pop();
  }

  /**
   * Renders a {@link List} node.
   *
   * @param node
   * @param data
   */
  @Override
  public final void visit(List node, FoContext data) {
    FoListBlock list = new FoListBlock().setStartIndent("1pc");
    list.setDistanceBetweenStarts("1.0em").setLabelSeparation("0.2em");
    list.setSpace("0.8em", "1.0em", "1.2em");
    data.top().addNode(list);

    for (int index = 0; index < node.nodes().size(); index++) {
      FoListItem item = new FoListItem(node.isOrdered() ? String.format("%s.", (index + 1)) : "•").setSpace("0.3em");
      list.addNode(item);

      data.push(item.getContent());
      node.nodes().get(index).forEach(i -> i.accept(this, data));
      data.pop();
    }
  }

  /**
   * Renders an alert {@link Table} node.
   *
   * @param node
   * @param data
   */
  @Override
  public final void visit(Table node, FoContext data) {
    FoTable table = new FoTable().setTableLayout("fixed");

    if (!node.isVirtual()) {
      table.setSpace("1em");
      table.setBorderTop("1px", "solid", "#777777");
      table.setBorderBottom("1px", "solid", "#777777");
      table.setBorderBefore("retain").setBorderCollapse("collapse");
    } else {
      if (node.getBorderColor() != null) {
        table.setBorder(".5px", "solid", node.getBorderColor());
      }
      table.setBackgroundColor(node.getBackgroundColor());
    }

    data.top().addNode(table);

    // Render columns
    for (Column column : node.getColumns()) {
      table.addColumn("" + (column.getIndex() + 1), "" + column.getWidth() + "%");
    }

    // Render the areas
    for (Table.Area a : node.getAreas()) {
      FoTableArea area = null;
      AreaType type = a.getType();
      switch (type) {
        case HEAD:
          area = table.addHead();
          break;

        case TAIL:
          area = table.addFoot();
          break;

        default:
          area = table.addBody();
      }

      // Render rows
      int i = 0;
      for (Row r : a.getRows()) {
        FoTableRow row = area.addRow();
        switch (type) {
          case HEAD:
            row.setFontWeight("bold");
            row.setBackgroundColor("#cccccc");
            break;

          case BODY:
            if (!node.isVirtual()) {
              row.setBackgroundColor(((i++ % 2) == 1) ? "#eeeeee" : "#f7f7f7");
            }
            break;

          default:
            break;
        }

        // Render Cells
        for (Table.Cell c : r.getCells()) {
          FoTableCell cell = row.addCell();
          cell.setRowSpan(c.getRowSpan());
          cell.setColSpan(c.getColSpan());
          cell.setTextAlign(c.getAlign());

          if (node.isVirtual()) {
            cell.setBorder("0", "none", "transparent");
          } else {
            cell.setDisplayAlign("center");
            cell.setPadding("2pt");
            cell.setBorderBottom("0.5px", "solid", "#777777");
          }

          data.push(cell);
          c.getContent().accept(this, data);
          data.pop();
        }
      }
    }
  }

  /**
   * Renders an alert {@link Message} node.
   *
   * @param node
   * @param data
   */
  @Override
  public final void visit(Message node, FoContext data) {
    FoBlock content = FoBlock.block();
    content.setSpaceBefore("1.6em", "2em", "2.4em");
    content.setSpaceAfter("1.6em", "2em", "2.4em");
    content.setMargin("0");
    content.setPadding("0.2em", "1em");
    content.setBackgroundColor(FoRenderer.getColor(node.getStyle(), true));
    content.setBorder(".5px", "solid", FoRenderer.getColor(node.getStyle(), false));
    content.setBorderLeft(".1in", "solid", FoRenderer.getColor(node.getStyle(), false));
    content.setBorderRadius(".2em");
    data.top().addNode(content);

    data.push(content);
    node.forEach(c -> c.accept(this, data));
    data.pop();
  }

  private int footnote = 0;

  @Override
  public final void visit(Inline node, FoContext data) {
    FoBlock content = null;

    if (node.isFootnote()) {
      FoFootnote footnote = new FoFootnote(String.format("(%s)", ++this.footnote));
      data.top().addNode(footnote);
      content = footnote.getBody();
      content.addContent(" ");
    } else {
      content = FoBlock.inline();
      if (node.isBold()) {
        content.setFontWeight("bold");
      }
      if (node.isItalic()) {
        content.setFontStyle("italic");
      }
      Set<String> decoration = new HashSet<>();
      if (node.isOverline()) {
        decoration.add("overline");
      }
      if (node.isUnderline()) {
        decoration.add("underline");
      }
      if (node.isStrikethrough()) {
        decoration.add("line-through");
      }
      if (!decoration.isEmpty()) {
        content.setTextDecoration(decoration.stream().collect(Collectors.joining(" ")));
      }

      if (node.getRadius() != null) {
        content.setBorderRadius(node.getRadius());
      }
      if (node.getColor() != null) {
        content.setColor(node.getColor());
      }
      if (node.getBackground() != null) {
        content.setBackgroundColor(node.getBackground());
      }
      if (node.getPaddingTop() != null) {
        content.setPadding(node.getPaddingLeft(), node.getPaddingRight(), node.getPaddingTop(),
            node.getPaddingBottom());
      }

      data.top().addNode(content);
    }

    data.push(content);
    node.nodes().forEach(c -> c.accept(this, data));
    data.pop();
  }

  @Override
  public final void visit(CodeNode node, FoContext data) {
    FoBlock content = FoBlock.block();
    content.setTextAlign("start");
    content.setFontFamily(Fo.FONT_CODE);
    content.setFontSize(node.getFontSize());
    content.setSpaceBefore("0.8em", "1em", "1.2em");
    content.setSpaceAfter("0.8em", "1em", "1.2em");
    content.setWarp("wrap");
    content.setLineFeed("preserve");
    content.setWhiteSpaceCollapse("false");
    content.setWhiteSpaceTreatment("preserve");

    if (node.getTextColor() != null) {
      content.setColor(node.getTextColor());
    }
    content.setBackgroundColor(node.getBackground());
    content.setPadding(node.getPadding());
    if (node.isStyled()) {
      content.setMarginLeft("-0.75in");
      content.setMarginRight("-0.75in");
      content.setPadding("0.1in", "0.75in");
    } else {
      content.setPadding("0.1in");
      content.setMarginLeft("0");
      content.setMarginRight("0");
      content.setBorderRadius("0.2em");
      content.setBorderLeft(".5pt", "solid", node.getBorderColor());
      content.setBorderRight(".5pt", "solid", node.getBorderColor());
    }
    content.setLineHeight("1em");
    content.setBorderTop(".5pt", "solid", node.getBorderColor());
    content.setBorderBottom(".5pt", "solid", node.getBorderColor());
    data.top().addNode(content);

    data.push(content);
    node.nodes().forEach(c -> c.accept(this, data));
    data.pop();
  }

  private static String getColor(Style style, boolean background) {
    switch (style) {
      case INFO:
        return background ? "#bde5f8" : "#5bc2f3";
      case SUCCESS:
        return background ? "#dff2bf" : "#4f8a10";
      case WARNING:
        return background ? "#ffedbd" : "#ffbe35";
      case ERROR:
        return background ? "#f78082" : "#cc0013";

      default:
        break;
    }
    return background ? "#ffedbd" : "#ffbe35";
  }
}
