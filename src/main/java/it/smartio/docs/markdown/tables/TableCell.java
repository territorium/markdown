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

package it.smartio.docs.markdown.tables;

import org.commonmark.node.CustomNode;

/**
 * Table cell of a {@link TableRow} containing inline nodes.
 */
public class TableCell extends CustomNode {

  private boolean   header;
  private int       width;
  private Alignment alignment;

  /**
   * @return whether the cell is a header or not
   */
  public boolean isHeader() {
    return this.header;
  }

  public void setHeader(boolean header) {
    this.header = header;
  }

  /**
   * @return the cell alignment
   */
  public int getWidth() {
    return this.width == 0 ? 1 : this.width;
  }

  public void setWidth(int width) {
    this.width = width;
  }

  /**
   * @return the cell alignment
   */
  public Alignment getAlignment() {
    return this.alignment == null ? Alignment.LEFT : this.alignment;
  }

  public void setAlignment(Alignment alignment) {
    this.alignment = alignment;
  }

  /**
   * How the cell is aligned horizontally.
   */
  public enum Alignment {
    LEFT,
    CENTER,
    RIGHT
  }

}
