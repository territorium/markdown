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

package it.smartio.docs.fop.nodes.set;

/**
 * The {@link FoFont} class.
 */
public interface FoFont<F extends FoFont<?>> extends Fo {

  @SuppressWarnings("unchecked")
  default F setColor(String color) {
    set("color", color);
    return (F) this;
  }

  @SuppressWarnings("unchecked")
  default F setFontFamily(String family) {
    set("font-family", family);
    return (F) this;
  }

  @SuppressWarnings("unchecked")
  default F setFontWeight(String weight) {
    set("font-weight", weight);
    return (F) this;
  }

  @SuppressWarnings("unchecked")
  default F setFontStyle(String style) {
    set("font-style", style);
    return (F) this;
  }

  @SuppressWarnings("unchecked")
  default F setFontSize(String size) {
    set("font-size", size);
    return (F) this;
  }

  @SuppressWarnings("unchecked")
  default F setLineHeight(String height) {
    set("line-height", height);
    return (F) this;
  }

  @SuppressWarnings("unchecked")
  default F setTextAlign(String align) {
    set("text-align", align);
    return (F) this;
  }

  @SuppressWarnings("unchecked")
  default F setTextDecoration(String value) {
    set("text-decoration", value);
    return (F) this;
  }
}
