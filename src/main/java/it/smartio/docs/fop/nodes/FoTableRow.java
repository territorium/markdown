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

package it.smartio.docs.fop.nodes;

import it.smartio.docs.fop.nodes.set.FoBackground;
import it.smartio.docs.fop.nodes.set.FoBorder;
import it.smartio.docs.fop.nodes.set.FoFont;

/**
 * The {@link FoTableRow} class.
 */
public class FoTableRow extends FoNode implements FoBorder<FoTableRow>, FoFont<FoTableRow>, FoBackground<FoTableRow> {

  /**
   * Constructs an instance of {@link FoTableRow}.
   */
  FoTableRow() {
    super("fo:table-row");
  }

  public FoTableRow setDisplayAlign(String align) {
    set("display-align", align);
    return this;
  }

  public FoTableRow setProgessionDimensionMin(String value) {
    set("block-progression-dimension.minimum", value);
    return this;
  }

  public FoTableCell addCell() {
    FoTableCell cell = new FoTableCell();
    addNode(cell);
    return cell;
  }
}
