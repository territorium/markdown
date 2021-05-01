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

import java.util.Random;

import it.smartio.docs.Chapter;
import it.smartio.docs.Message.Style;

/**
 * The {@link PageBuilder} class.
 */
public abstract class PageBuilder extends SectionBuilder implements Chapter {

  private String            title;

  private final String      id;
  private final int         level;
  private final PageBuilder parent;

  /**
   * Constructs an instance of {@link PageBuilder}.
   */
  protected PageBuilder(int level, PageBuilder parent) {
    this.level = level;
    this.parent = parent;
    this.id = Long.toHexString(new Random().nextLong());
  }

  @Override
  public final int getLevel() {
    return this.level;
  }

  @Override
  public final String getId() {
    return this.id;
  }

  @Override
  public final String getTitle() {
    return this.title == null ? "" : this.title;
  }

  @Override
  public final Chapter getParent() {
    return this.parent;
  }

  public final PageBuilder setTitle(String title) {
    this.title = (title == null) ? title : title.trim();
    addIndex(this);
    return this;
  }

  public abstract PageBuilder addSection();

  public final MessageBuilder addNotification(Style style) {
    return add(new MessageBuilder(style));
  }

  public final TableBuilder addTable() {
    return add(new TableBuilder(false));
  }

  protected void addIndex(Chapter node) {
    this.parent.addIndex(node);
  }
}
