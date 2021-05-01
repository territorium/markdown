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

package it.smartio.docs.fop.nodes;

import java.util.Map;

/**
 * The {@link FoUtil} is an utility to build {@link FoNode}.
 */
public class FoUtil {

  private static void writeAttr(StringBuffer buffer, String name, String value) {
    if (value != null) {
      buffer.append(" ");
      buffer.append(name);
      buffer.append("=\"");
      buffer.append(value);
      buffer.append("\"");
    }
  }

  private static void writeAttrs(StringBuffer buffer, Map<String, String> attrs) {
    for (Map.Entry<String, String> e : attrs.entrySet()) {
      FoUtil.writeAttr(buffer, e.getKey(), e.getValue());
    }
  }

  static void writeStart(StringBuffer buffer, String name, Map<String, String> attrs) {
    buffer.append("<");
    buffer.append(name);
    FoUtil.writeAttrs(buffer, attrs);
    buffer.append(">");
  }

  static void writeEnd(StringBuffer buffer, String name) {
    buffer.append("</");
    buffer.append(name);
    buffer.append(">");
  }

  static void writeEmpty(StringBuffer buffer, String name, Map<String, String> attrs) {
    buffer.append("<");
    buffer.append(name);
    FoUtil.writeAttrs(buffer, attrs);
    buffer.append("/>");
  }
}
