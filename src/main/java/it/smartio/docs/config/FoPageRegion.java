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

package it.smartio.docs.config;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import it.smartio.docs.fop.FoConfig;
import it.smartio.docs.fop.builder.FoBlockContainer;
import it.smartio.docs.fop.builder.FoPageSequence;
import it.smartio.docs.fop.builder.FoStaticContent;
import it.smartio.docs.fop.builder.FoBlockContainer.Position;
import it.smartio.docs.util.DataUri;

/**
 * The {@link FoPageRegion} class.
 */
public class FoPageRegion extends FoPage {

  private URI                      uri;
  private String                   background;
  private final List<FoPageStatic> children = new ArrayList<>();

  /**
   * Constructs an instance of {@link FoPageRegion}.
   *
   * @param name
   * @param data
   */
  public FoPageRegion(String name, FoConfig data) {
    super(name, data);
  }

  public final FoPageStatic getStatic() {
    return this.children.isEmpty() ? null : this.children.get(this.children.size() - 1);
  }

  public final void addStatic(FoPageStatic child) {
    this.children.add(child);
  }

  public final void setBackground(URI uri) {
    this.uri = uri;
  }

  public final void setBackground(String background) {
    this.background = background;
  }

  @Override
  public void render(FoPageSequence page, Properties properties) {
    FoStaticContent content = new FoStaticContent(getName());

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
      page.addNode(content);
    }
  }
}