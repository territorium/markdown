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
import it.smartio.docs.fop.nodes.set.FoMargin;
import it.smartio.docs.fop.nodes.set.FoPadding;

/**
 * The {@link FoRegion} class.
 */
public class FoRegion extends FoNode
    implements FoMargin<FoRegion>, FoBorder<FoRegion>, FoPadding<FoRegion>, FoBackground<FoRegion> {

  /**
   * Constructs an instance of {@link FoRegion}.
   */
  public FoRegion() {
    super("fo:region-body");
  }

  /**
   * Constructs an instance of {@link FoRegion}.
   *
   * @param name
   * @param region
   */
  public FoRegion(String name, String region) {
    super("fo:region-" + region);
    setRegionName(name);
  }

  public FoRegion setRegionName(String name) {
    set("region-name", name);
    return this;
  }

  public FoRegion setDisplayAlign(String displayAlign) {
    set("display-align", displayAlign);
    return this;
  }

  public FoRegion setColumns(String count, String gap) {
    set("column-gap", gap);
    set("column-count", count);
    return this;
  }

  public FoRegion setExtent(String extent) {
    set("extent", extent);
    return this;
  }

  public FoRegion setPrecedence(String precedence) {
    set("precedence", precedence);
    return this;
  }

  public FoRegion setReferenceOrientation(String referenceOrientation) {
    set("reference-orientation", referenceOrientation);
    return this;
  }
}
