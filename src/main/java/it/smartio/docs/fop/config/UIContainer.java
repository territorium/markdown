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


/**
 * The {@link UIContainer} defines a generic interface for all template items.
 */
public interface UIContainer extends UIItem {

  void addItem(UIRenderable item);

  UIContainer addContainer();

  void setTop(String top);

  void setLeft(String left);

  void setRight(String right);

  void setBottom(String bottom);

  void setColor(String color);

  void setBackground(String background);

  void setFontSize(String fontSize);

  void setFontStyle(String fontStyle);

  void setFontWeight(String fontWeight);

  void setTextAlign(String textAlign);

  void setLineHeight(String lineHeight);
}
