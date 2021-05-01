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

package it.smartio.docs.fop.config;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import it.smartio.docs.fop.nodes.FoBlockContainer;
import it.smartio.docs.fop.nodes.FoBlockContainer.Position;
import it.smartio.docs.fop.nodes.FoStaticContent;
import it.smartio.docs.util.DataUri;

/**
 * The {@link UIPanelStatic} class.
 */
class UIPanelStatic implements UIContainer {

  private String                   top;
  private String                   left;
  private String                   right;
  private String                   bottom;

  private String                   color;
  private String                   background;

  private String                   fontSize;
  private String                   fontStyle;
  private String                   fontWeight;
  private String                   textAlign;
  private String                   lineHeight;

  private final List<UIRenderable> items = new ArrayList<>();

  /**
   * Constructs an instance of {@link UIPanelStatic}.
   *
   */
  public UIPanelStatic() {
    this.top = "0";
    this.left = "0";
    this.right = "0";
    this.bottom = "0";
    this.textAlign = "left";
    this.lineHeight = "1.5em";
  }

  @Override
  public final void addItem(UIRenderable child) {
    this.items.add(child);
  }

  @Override
  public final UIContainer addContainer() {
    UIPanel container = new UIPanel();
    this.items.add(container);
    return container;
  }

  @Override
  public final void setTop(String top) {
    this.top = top;
  }

  @Override
  public final void setLeft(String left) {
    this.left = left;
  }

  @Override
  public final void setRight(String right) {
    this.right = right;
  }

  @Override
  public final void setBottom(String bottom) {
    this.bottom = bottom;
  }

  @Override
  public final void setColor(String color) {
    this.color = color;
  }

  @Override
  public final void setBackground(String background) {
    this.background = background;
  }

  @Override
  public final void setFontSize(String fontSize) {
    this.fontSize = fontSize;
  }

  @Override
  public final void setFontStyle(String fontStyle) {
    this.fontStyle = fontStyle;
  }

  @Override
  public final void setFontWeight(String fontWeight) {
    this.fontWeight = fontWeight;
  }

  @Override
  public final void setTextAlign(String textAlign) {
    this.textAlign = textAlign;
  }

  @Override
  public final void setLineHeight(String lineHeight) {
    this.lineHeight = lineHeight;
  }

  public void render(FoStaticContent node, Properties properties) {
    FoBlockContainer container = node.blockContainer(Position.Absolute);
    container.setPosition(this.left, this.right, this.top, this.bottom);

    container.setColor(this.color);
    if (this.background != null) {
      if (this.background.startsWith("#")) {
        container.setBackgroundColor(this.background);
      } else {
        container.setBackground(DataUri.loadImage(this.background), "no-repeat");
      }
    }
    container.setFontSize(this.fontSize);
    container.setFontStyle(this.fontStyle);
    container.setFontWeight(this.fontWeight);
    container.setTextAlign(this.textAlign);
    container.setLineHeight(this.lineHeight);

    this.items.forEach(c -> c.render(container.addBlock(), properties));
  }
}