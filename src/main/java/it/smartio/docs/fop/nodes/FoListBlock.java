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
import it.smartio.docs.fop.nodes.set.FoBreak;
import it.smartio.docs.fop.nodes.set.FoFont;
import it.smartio.docs.fop.nodes.set.FoIndent;
import it.smartio.docs.fop.nodes.set.FoMargin;
import it.smartio.docs.fop.nodes.set.FoSpace;

/**
 * The {@link FoListBlock} class.
 */
public class FoListBlock extends FoNode implements FoSpace<FoListBlock>, FoMargin<FoListBlock>, FoFont<FoListBlock>,
    FoBreak<FoListBlock>, FoBackground<FoListBlock>, FoIndent<FoListBlock> {

  /**
   * Constructs an instance of {@link FoListBlock}.
   */
  public FoListBlock() {
    super("fo:list-block");
  }

  public FoListBlock setKeepWithNext(String keep) {
    set("keep-with-next.within-column", keep);
    return this;
  }

  public FoListBlock setLabelSeparation(String value) {
    set("provisional-label-separation", value);
    return this;
  }

  public FoListBlock setDistanceBetweenStarts(String value) {
    set("provisional-distance-between-starts", value);
    return this;
  }

  public FoListItem addItem(String label) {
    FoListItem item = new FoListItem(label);
    addNode(item);
    return item;
  }

  public FoListBlock addItem(FoListItem item) {
    addNode(item);
    return this;
  }
}
