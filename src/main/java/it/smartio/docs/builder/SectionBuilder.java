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

package it.smartio.docs.builder;

import it.smartio.docs.List;

/**
 * The {@link SectionBuilder} class.
 */
public abstract class SectionBuilder extends NodeBuilder {

  /**
   * Add a new {@link Item} to the {@link List}.
   */
  public final ParagraphBuilder addParagraph() {
    return add(new ParagraphBuilder());
  }

  public final void addBreak() {
    add(new ParagraphBuilder().setSoftBreak());
  }

  public final void addLineBreak() {
    add(new ParagraphBuilder().setLineBreak());
  }

  /**
   * Add a new {@link Item} to the {@link List}.
   */
  public final ListBuilder addList() {
    return add(new ListBuilder());
  }

  /**
   * Add a new {@link Item} to the {@link List}.
   */
  public final ListBuilder addOrderedList() {
    return add(new ListBuilder(true));
  }

  /**
   * Add a new {@link Item} to the {@link List}.
   */
  public final CodeBuilder addCode() {
    return add(new CodeBuilder(false));
  }

  /**
   * Add a new {@link Item} to the {@link List}.
   */
  public final CodeBuilder addStyledCode() {
    return add(new CodeBuilder(true));
  }

  /**
   * Add a new {@link Item} to the {@link List}.
   */
  public final void addImage(String url, String title, String align, String width, String height) {
    add(new ImageBuilder(url, title, align, width, height));
  }

  public final TableBuilder addVirtualTable() {
    return add(new TableBuilder(true));
  }
}
