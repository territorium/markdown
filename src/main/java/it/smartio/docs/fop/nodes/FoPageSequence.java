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

/**
 * The {@link FoPageSequence} class.
 */
public class FoPageSequence extends FoNode {

  /**
   * Constructs an instance of {@link FoPageSequence}.
   *
   * @param reference
   */
  public FoPageSequence(String reference) {
    super("fo:page-sequence");
    set("master-reference", reference);
  }

  public FoPageSequence setLanguage(String language) {
    set("language", language);
    return this;
  }

  public FoPageSequence setFormat(String format) {
    set("format", format);
    return this;
  }

  public FoPageSequence setInitialPageNumber(String initial) {
    set("initial-page-number", initial);
    return this;
  }

  public FoPageSequence setForcePageCount(String force) {
    set("force-page-count", force);
    return this;
  }

  public FoFlow flow(String reference) {
    FoFlow builder = new FoFlow(reference);
    addNode(builder);
    return builder;
  }
}
