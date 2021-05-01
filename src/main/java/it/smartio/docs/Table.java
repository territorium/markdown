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

package it.smartio.docs;

import java.util.List;

/**
 * The {@link Table} class.
 */
public interface Table extends Node {

  enum AreaType {
    HEAD,
    BODY,
    TAIL
  }

  boolean isVirtual();

  String getBorderColor();

  String getBackgroundColor();

  List<Column> getColumns();

  List<Area> getAreas();

  @Override
  default <R> void accept(NodeVisitor<R> visitor, R data) {
    visitor.visit(this, data);
  }

  interface Column {

    int getIndex();

    int getWidth();

    String getAlign();
  }

  interface Area {

    AreaType getType();

    List<Row> getRows();
  }

  interface Row {

    List<Cell> getCells();
  }

  interface Cell {

    int getRowSpan();

    int getColSpan();

    String getAlign();

    Paragraph getContent();
  }
}
