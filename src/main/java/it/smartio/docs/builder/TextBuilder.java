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

import it.smartio.docs.Text;

/**
 * The {@link TextBuilder} class.
 */
class TextBuilder extends NodeBuilder implements Text {

  private final String  text;
  private final boolean isCode;

  /**
   * Constructs an instance of {@link TextBuilder}.
   *
   * @param text
   */
  public TextBuilder(String text) {
    this(text, false);
  }

  /**
   * Constructs an instance of {@link TextBuilder}.
   *
   * @param text
   * @param isCode
   */
  public TextBuilder(String text, boolean isCode) {
    this.text = text;
    this.isCode = isCode;
  }

  /**
   * Gets the text value.
   */
  @Override
  public final String getText() {
    return this.text;
  }

  /**
   * Gets the text value.
   */
  @Override
  public final boolean isCode() {
    return this.isCode;
  }
}
