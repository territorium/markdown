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
import it.smartio.docs.fop.nodes.set.FoBreak;
import it.smartio.docs.fop.nodes.set.FoFont;
import it.smartio.docs.fop.nodes.set.FoIndent;
import it.smartio.docs.fop.nodes.set.FoMargin;
import it.smartio.docs.fop.nodes.set.FoPadding;
import it.smartio.docs.fop.nodes.set.FoSpace;

/**
 * The {@link FoBlock} class.
 */
public class FoBlock extends FoNode implements FoSpace<FoBlock>, FoMargin<FoBlock>, FoBorder<FoBlock>,
    FoPadding<FoBlock>, FoFont<FoBlock>, FoBreak<FoBlock>, FoBackground<FoBlock>, FoIndent<FoBlock> {

  /**
   * Constructs an instance of {@link FoBlock}.
   *
   * @param name
   */
  private FoBlock(String name) {
    super(name);
  }

  public FoBlock setTextAlignLast(String align) {
    set("text-align-last", align);
    return this;
  }

  public FoBlock setKeepWithNext(String keep) {
    set("keep-with-next.within-column", keep);
    return this;
  }

  public FoBlock setWarp(String value) {
    set("wrap-option", value);
    return this;
  }

  public FoBlock setWhiteSpaceCollapse(String value) {
    set("white-space-collapse", value);
    return this;
  }

  public FoBlock setWhiteSpaceTreatment(String value) {
    set("white-space-treatment", value);
    return this;
  }

  public FoBlock setLineFeed(String value) {
    set("linefeed-treatment", value);
    return this;
  }

  public FoBlock setSpan(String value) {
    set("span", value);
    return this;
  }

  public FoBlock setWidth(String value) {
    set("width", value);
    return this;
  }

  public FoBlock setHeight(String value) {
    set("height", value);
    return this;
  }

  /**
   * Add a simple content.
   *
   * @param content
   */
  public FoBlock addContent(String content) {
    addText(content);
    return this;
  }

  public FoBlock addBlock() {
    FoBlock block = FoBlock.block();
    addNode(block);
    return block;
  }

  public FoBlock addInline() {
    FoBlock block = FoBlock.inline();
    addNode(block);
    return block;
  }

  public static FoBlock block() {
    return new FoBlock("fo:block");
  }

  public static FoBlock inline() {
    return new FoBlock("fo:inline");
  }
}
