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

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import it.smartio.docs.fop.nodes.FoBlockContainer;
import it.smartio.docs.fop.nodes.FoBlockContainer.Position;
import it.smartio.docs.fop.nodes.FoPageSequence;
import it.smartio.docs.fop.nodes.FoRegion;
import it.smartio.docs.fop.nodes.FoStaticContent;
import it.smartio.docs.util.DataUri;

/**
 * The {@link UIPageRegion} defines a single region of the page.
 */
class UIPageRegion implements UIContainer {

  private final String         name;
  private final FoRegion       region;

  private URI                  uri;
  private String               background;
  private final List<UIPanelStatic> children = new ArrayList<>();

  /**
   * Constructs an instance of {@link UIPageRegion}.
   *
   * @param name
   * @param region
   */
  public UIPageRegion(String name, FoRegion region) {
    this.name = name;
    this.region = region;
  }

  /**
   * Gets the {@link FoRegion}.
   */
  public final FoRegion getRegion() {
    return this.region;
  }

  @Override
  public final void addItem(UIRenderable child) {
    throw new UnsupportedOperationException();
  }

  @Override
  public final UIContainer addContainer() {
    UIPanelStatic container = new UIPanelStatic();
    this.children.add(container);
    return container;
  }

  public final void setBackground(URI uri) {
    this.uri = uri;
  }

  @Override
  public final void setBackground(String background) {
    this.background = background;
  }

  @Override
  public void setTop(String top) {
    throw new UnsupportedOperationException();
  }

  @Override
  public void setLeft(String left) {
    throw new UnsupportedOperationException();
  }

  @Override
  public void setRight(String right) {
    throw new UnsupportedOperationException();
  }

  @Override
  public void setBottom(String bottom) {
    throw new UnsupportedOperationException();
  }

  @Override
  public void setColor(String color) {
    throw new UnsupportedOperationException();
  }

  @Override
  public void setFontSize(String fontSize) {
    throw new UnsupportedOperationException();
  }

  @Override
  public void setFontStyle(String fontStyle) {
    throw new UnsupportedOperationException();
  }

  @Override
  public void setFontWeight(String fontWeight) {
    throw new UnsupportedOperationException();
  }

  @Override
  public void setTextAlign(String textAlign) {
    throw new UnsupportedOperationException();
  }

  @Override
  public void setLineHeight(String lineHeight) {
    throw new UnsupportedOperationException();
  }

  public void render(FoPageSequence node, Properties properties) {
    FoStaticContent content = new FoStaticContent(this.name);

    if (this.uri != null) {
      FoBlockContainer container = content.blockContainer(Position.Absolute);
      container.setPosition("0", "0", "0", "0");
      container.setBackground(DataUri.loadImage(this.uri), "no-repeat");
      container.addBlock();
    } else if (this.background != null) {
      FoBlockContainer container = content.blockContainer(Position.Absolute);
      container.setPosition("0", "0", "0", "0");
      if (this.background.startsWith("#")) {
        container.setBackgroundColor(this.background);
      } else {
        container.setBackground(DataUri.loadImage(this.background), "no-repeat");
      }
      container.addBlock();
    }

    this.children.forEach(c -> c.render(content, properties));

    if (content.hasChildren()) {
      node.addNode(content);
    }
  }
}