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

package it.smartio.docs.markdown.images;

import java.util.Map;

import org.commonmark.node.AbstractVisitor;
import org.commonmark.node.CustomNode;
import org.commonmark.node.Image;
import org.commonmark.node.Node;
import org.commonmark.renderer.html.AttributeProvider;

class ImageProvider implements AttributeProvider {

  private ImageProvider() {}

  public static ImageProvider create() {
    return new ImageProvider();
  }

  @Override
  public void setAttributes(Node node, String tagName, final Map<String, String> attributes) {
    if (node instanceof Image) {
      node.accept(new AbstractVisitor() {

        @Override
        public void visit(CustomNode node) {
          if (node instanceof ImageAttributes) {
            ImageAttributes imageAttributes = (ImageAttributes) node;
            for (Map.Entry<String, String> entry : imageAttributes.getAttributes().entrySet()) {
              attributes.put(entry.getKey(), entry.getValue());
            }
            // Now that we have used the image attributes we remove the node.
            imageAttributes.unlink();
          }
        }
      });
    }
  }
}
