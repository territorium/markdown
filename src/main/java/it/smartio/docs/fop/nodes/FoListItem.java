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
import it.smartio.docs.fop.nodes.set.FoFont;
import it.smartio.docs.fop.nodes.set.FoMargin;
import it.smartio.docs.fop.nodes.set.FoSpace;

/**
 * The {@link FoListItem} class.
 */
public class FoListItem extends FoNode
    implements FoSpace<FoListItem>, FoMargin<FoListItem>, FoFont<FoListItem>, FoBackground<FoListItem> {

  private final FoBlock content;

  /**
   * Constructs an instance of {@link FoListItem}.
   *
   * @param label
   */
  public FoListItem(String label) {
    super("fo:list-item");

    FoNode head = FoNode.create("fo:list-item-label");
    head.set("end-indent", "label-end()");
    head.addNode(FoBlock.block().addContent(label));

    FoNode body = FoNode.create("fo:list-item-body");
    body.set("start-indent", "body-start()");

    this.content = FoBlock.block();
    body.addNode(this.content);

    addNode(head);
    addNode(body);
  }

  public FoListItem setKeepWithNext(String keep) {
    set("keep-with-next.within-column", keep);
    return this;
  }

  public FoBlock getContent() {
    return this.content;
  }
}
