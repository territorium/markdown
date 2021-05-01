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

import org.commonmark.node.CustomNode;

import it.smartio.docs.builder.TableBuilder;
import it.smartio.docs.builder.TableBuilder.CellBuilder;
import it.smartio.docs.markdown.tables.TableBody;
import it.smartio.docs.markdown.tables.TableCell;
import it.smartio.docs.markdown.tables.TableHead;
import it.smartio.docs.markdown.tables.TableRow;

/**
 * The {@link MarkdownTable} class.
 */
class MarkdownTable extends MarkdownVisitor {

  private final TableBuilder table;

  /**
   * Constructs an instance of {@link MarkdownTable}.
   *
   * @param content
   */
  public MarkdownTable(TableBuilder table) {
    this.table = table;
  }

  /**
   * Get the current {@link TableBuilder} .
   */
  protected final TableBuilder getTable() {
    return this.table;
  }

  /**
   * Processes the {@link CustomNode} of a table
   *
   * @param node
   */
  @Override
  public final void visit(CustomNode node) {
    if (node instanceof TableHead) {
      getTable().addHead();
      visitChildren(node);
    } else if (node instanceof TableBody) {
      getTable().addBody();
      visitChildren(node);
    } else if (node instanceof TableRow) {
      getTable().addRow();
      visitChildren(node);
    } else if (node instanceof TableCell) {
      TableCell cell = (TableCell) node;
      if (node.getParent().getParent() instanceof TableHead) {
        getTable().addColumn(cell.getWidth(), cell.getAlignment().name().toLowerCase());
      }
      CellBuilder content = getTable().addCell(1, 1);
      MarkdownBuilder builder = new MarkdownBuilder(content.getContent(), 0);
      builder.visitChildren(node);
    } else {
      super.visitChildren(node);
    }
  }
}
