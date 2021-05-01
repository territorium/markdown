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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import it.smartio.docs.Node;

/**
 * The {@link NodeBuilder} class defines a generic node for a {@link Node} structure.
 */
public abstract class NodeBuilder implements Node {

  private final List<Node> nodes = new ArrayList<>();

  /**
   * Returns an iterator over the child {@link Node}'s.
   */
  @Override
  public final Iterator<Node> iterator() {
    return this.nodes.iterator();
  }

  /**
   * Iterates over the child nodes.
   */
  @Override
  public final List<Node> nodes() {
    return this.nodes;
  }

  /**
   * Add a child {@link NodeBuilder}.
   *
   * @param node
   */
  protected final <N extends NodeBuilder> N add(N node) {
    this.nodes.add(node);
    return node;
  }
}
