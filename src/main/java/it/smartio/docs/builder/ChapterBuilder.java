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

/**
 * The {@link ChapterBuilder} class.
 */
class ChapterBuilder extends PageBuilder {

  private final int offset;
  private int       sectionOffset;

  /**
   * Constructs an instance of {@link ChapterBuilder}.
   *
   */
  public ChapterBuilder(PageBuilder parent, int level, int offset) {
    super(level, parent);
    this.offset = offset;
    this.sectionOffset = 0;
  }

  @Override
  public final int getOffset() {
    return this.offset;
  }

  @Override
  public final PageBuilder addSection() {
    return add(new ChapterBuilder(this, getLevel() + 1, this.sectionOffset++));
  }
}
