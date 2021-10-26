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

package it.smartio.docs.fop;

import it.smartio.docs.Chapter;
import it.smartio.docs.NodeVisitor;
import it.smartio.docs.fop.config.FoContext;
import it.smartio.docs.fop.nodes.FoBookmark;
import it.smartio.docs.util.PageUtil;

/**
 * The {@link FoRendererBookmark} class.
 */
class FoRendererBookmark implements NodeVisitor<FoBookmark> {

  private final FoContext config;

  /**
   * Constructs an instance of {@link FoRendererBookmark}.
   *
   * @param config
   */
  public FoRendererBookmark(FoContext config) {
    this.config = config;
  }

  /**
   * Renders a {@link Chapter} node.
   *
   * @param node
   * @param data
   */
  @Override
  public final void visit(Chapter node, FoBookmark data) {
    FoBookmark bookmark =
        (node.getLevel() > 1) ? data.addBookmark(node.getId()) : this.config.addBookmark(node.getId());
    bookmark.setTitle(PageUtil.encode(node.getTitle().trim()));
    node.forEach(n -> n.accept(this, bookmark));
  }
}
