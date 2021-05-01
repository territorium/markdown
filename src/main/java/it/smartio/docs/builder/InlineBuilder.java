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

import it.smartio.docs.Inline;

/**
 * The {@link InlineBuilder} class.
 */
public class InlineBuilder extends ContentBuilder implements Inline {

  private String  radius;
  private String  color;
  private String  background;

  private String  paddingTop;
  private String  paddingLeft;
  private String  paddingRight;
  private String  paddingBottom;

  private boolean isBold;
  private boolean isItalic;
  private boolean isOverline;
  private boolean isUnderline;
  private boolean isStrikethrough;
  private boolean isFootnote;

  @Override
  public final String getRadius() {
    return this.radius;
  }

  @Override
  public final String getColor() {
    return this.color;
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

  public final InlineBuilder setRadius(String radius) {
    this.radius = radius;
    return this;
  }

  public final InlineBuilder setColor(String color) {
    this.color = color;
    return this;
  }

  public final InlineBuilder setBackground(String background) {
    this.background = background;
    return this;
  }

  public final InlineBuilder setPadding(String horizontal, String vertical) {
    return setPadding(horizontal, horizontal, vertical, vertical);
  }

  public final InlineBuilder setPadding(String left, String right, String top, String bottom) {
    this.paddingTop = top;
    this.paddingLeft = left;
    this.paddingRight = right;
    this.paddingBottom = bottom;
    return this;
  }

  @Override
  public final boolean isBold() {
    return this.isBold;
  }

  public final InlineBuilder setBold() {
    this.isBold = true;
    return this;
  }

  @Override
  public final boolean isItalic() {
    return this.isItalic;
  }

  public final InlineBuilder setItalic() {
    this.isItalic = true;
    return this;
  }

  @Override
  public final boolean isUnderline() {
    return this.isUnderline;
  }

  public final InlineBuilder setUnderline() {
    this.isUnderline = true;
    return this;
  }

  @Override
  public final boolean isOverline() {
    return this.isOverline;
  }

  public final InlineBuilder setOverline() {
    this.isOverline = true;
    return this;
  }

  @Override
  public final boolean isStrikethrough() {
    return this.isStrikethrough;
  }

  public final InlineBuilder setStrikethrough() {
    this.isStrikethrough = true;
    return this;
  }

  @Override
  public final boolean isFootnote() {
    return this.isFootnote;
  }

  public final void setFootnote() {
    this.isFootnote = true;
  }

}
