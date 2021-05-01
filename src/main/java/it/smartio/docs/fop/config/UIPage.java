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

import it.smartio.docs.fop.Fo;
import it.smartio.docs.fop.nodes.FoPageSequence;
import it.smartio.docs.fop.nodes.FoRegion;
import it.smartio.docs.fop.nodes.FoSimplePageMaster;

/**
 * The {@link UIPage} defines a page of the template.
 */
class UIPage implements UIItem {

  private final String             name;
  private final FoSimplePageMaster simple;
  private final FoRegion           content;
  private final List<UIPageRegion> regions = new ArrayList<>();

  /**
   * Constructs an instance of {@link UIPage}.
   *
   * @param name
   */
  public UIPage(String name) {
    this.name = name;
    this.simple = new FoSimplePageMaster(name);
    this.content = this.simple.setBodyRegion(Fo.PAGE_CONTENT);
  }

  /**
   * Gets the name.
   */
  public final String getName() {
    return this.name;
  }

  /**
   * Gets the {@link FoSimplePageMaster}.
   */
  public final FoSimplePageMaster getSimplePage() {
    return this.simple;
  }

  /**
   * Set a custom page size.
   *
   * @param width
   * @param height
   */
  public void setPageSize(String width, String height) {
    this.simple.setPageSize(width, height);
  }

  /**
   * Set the {@link UIPage} top padding.
   *
   * @param value
   */
  public final void setPaddingTop(String value) {
    this.content.setMarginTop(value);
  }

  /**
   * Set the {@link UIPage} left padding.
   *
   * @param value
   */
  public final void setPaddingLeft(String value) {
    this.content.setMarginLeft(value);
  }

  /**
   * Set the {@link UIPage} right padding.
   *
   * @param value
   */
  public final void setPaddingRight(String value) {
    this.content.setMarginRight(value);
  }

  /**
   * Set the {@link UIPage} bottom padding.
   *
   * @param value
   */
  public final void setPaddingBottom(String value) {
    this.content.setMarginBottom(value);
  }

  /**
   * Set the columns of the page.
   *
   * @param count
   * @param gap
   */
  public void setColumns(String count, String gap) {
    this.content.setColumns(count, gap);
  }

  /**
   * Add a {@link UIPageRegion}.
   *
   * @param name
   */
  public UIPageRegion addTop(String name) {
    if (name == null) {
      name = String.format("region-top-%s", getName());
    }

    UIPageRegion region = new UIPageRegion(name, this.simple.addRegionBefore(name));
    this.regions.add(region);
    return region;
  }

  /**
   * Add a {@link UIPageRegion}.
   *
   * @param name
   */
  public UIPageRegion addLeft(String name) {
    if (name == null) {
      name = String.format("region-left-%s", getName());
    }

    UIPageRegion region = new UIPageRegion(name, this.simple.addRegionStart(name));
    this.regions.add(region);
    return region;
  }

  /**
   * Add a {@link UIPageRegion}.
   *
   * @param name
   */
  public UIPageRegion addRight(String name) {
    if (name == null) {
      name = String.format("region-right-%s", getName());
    }

    UIPageRegion region = new UIPageRegion(name, this.simple.addRegionEnd(name));
    this.regions.add(region);
    return region;
  }

  /**
   * Add a {@link UIPageRegion}.
   *
   * @param name
   */
  public UIPageRegion addBottom(String name) {
    if (name == null) {
      name = String.format("region-bottom-%s", getName());
    }

    UIPageRegion region = new UIPageRegion(name, this.simple.addRegionAfter(name));
    this.regions.add(region);
    return region;
  }

  /**
   * Renders the regions of the page.
   *
   * @param page
   * @param properties
   */
  public void render(FoPageSequence sequence, Properties properties) {
    this.regions.forEach(r -> r.render(sequence, properties));
  }
}
