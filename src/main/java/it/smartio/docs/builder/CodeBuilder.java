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

import it.smartio.docs.CodeNode;

/**
 * The {@link CodeBuilder} class.
 */
public class CodeBuilder extends ContentBuilder implements CodeNode {

  private boolean styled;
  private String  padding;
  private String  background;
  private String  textColor;
  private String  borderColor;
  private String  fontSize;

  /**
   * Constructs an instance of {@link CodeBuilder}.
   *
   * @param isSimple
   */
  public CodeBuilder(boolean styled) {
    this.styled = styled;
    this.fontSize = "11pt";
    this.padding = "2pt 4pt";
    this.background = "#eeeeee";
    this.textColor = null;
    this.borderColor = "#aaaaaa";
  }

  /**
   * Return <code>true</code> id the code is styled.
   */
  @Override
  public final boolean isStyled() {
    return this.styled;
  }

  public final void setStyled(boolean styled) {
    this.styled = styled;
  }

  /**
   * Gets the background color.
   */
  @Override
  public final String getFontSize() {
    return this.fontSize;
  }

  /**
   * Gets the background color.
   */
  @Override
  public final String getBackground() {
    return this.background;
  }

  /**
   * Gets the border color.
   */
  @Override
  public final String getTextColor() {
    return this.textColor;
  }

  /**
   * Gets the border color.
   */
  @Override
  public final String getBorderColor() {
    return this.borderColor;
  }

  @Override
  public String getPadding() {
    return this.padding;
  }

  /**
   * @param background
   */
  public final void setFontSize(String fontSize) {
    this.fontSize = fontSize;
  }

  /**
   * @param background
   */
  public final void setPadding(String padding) {
    this.padding = padding;
  }

  /**
   * @param background
   */
  public final void setBackground(String background) {
    this.background = background;
  }

  /**
   * @param borderColor
   */
  public final void setTextColor(String textColor) {
    this.textColor = textColor;
  }

  /**
   * @param borderColor
   */
  public final void setBorderColor(String borderColor) {
    this.borderColor = borderColor;
  }

  @Override
  public void addContent(String content) {
    addNode(new TextBuilder(content));
  }

  /**
   * Add a child {@link NodeBuilder}.
   *
   * @param node
   */
  public final <N extends NodeBuilder> N addNode(N node) {
    return add(node);
  }
}
