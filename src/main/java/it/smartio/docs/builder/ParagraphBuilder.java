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

import it.smartio.docs.Paragraph;

/**
 * The {@link ParagraphBuilder} class.
 */
public class ParagraphBuilder extends ContentBuilder implements Paragraph {

  private int     intent;
  private boolean isSoftBreak;
  private boolean isLineBreak;

  private String  background;
  private String  paddingTop;
  private String  paddingLeft;
  private String  paddingRight;
  private String  paddingBottom;

  @Override
  public final int getIntent() {
    return this.intent;
  }

  @Override
  public final boolean isSoftBreak() {
    return this.isSoftBreak;
  }

  @Override
  public final boolean isLineBreak() {
    return this.isLineBreak;
  }

  @Override
  public final String getBackground() {
    return this.background;
  }

  @Override
  public final String getPaddingTop() {
    return this.paddingTop;
  }

  @Override
  public final String getPaddingLeft() {
    return this.paddingLeft;
  }

  @Override
  public final String getPaddingRight() {
    return this.paddingRight;
  }

  @Override
  public final String getPaddingBottom() {
    return this.paddingBottom;
  }

  public final ParagraphBuilder setIntent(int intent) {
    this.intent = intent;
    return this;
  }

  public final ParagraphBuilder setSoftBreak() {
    this.isSoftBreak = true;
    return this;
  }

  public final ParagraphBuilder setLineBreak() {
    this.isLineBreak = true;
    return this;
  }

  public final ParagraphBuilder setBackground(String background) {
    this.background = background;
    return this;
  }

  public final ParagraphBuilder setPadding(String horizontal, String vertical) {
    return setPadding(horizontal, horizontal, vertical, vertical);
  }

  public final ParagraphBuilder setPadding(String left, String right, String top, String bottom) {
    this.paddingTop = top;
    this.paddingLeft = left;
    this.paddingRight = right;
    this.paddingBottom = bottom;
    return this;
  }
}
