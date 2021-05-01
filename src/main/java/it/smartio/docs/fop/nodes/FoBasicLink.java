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

import java.util.regex.Pattern;

import it.smartio.docs.fop.nodes.set.FoFont;

/**
 * The {@link FoBasicLink} class.
 */
public class FoBasicLink extends FoNode implements FoFont<FoBasicLink> {

  private static final Pattern EXTERNAL = Pattern.compile("^[a-zA-Z]+:.+");

  /**
   * Constructs an instance of {@link FoBasicLink}.
   *
   * @param url
   */
  public FoBasicLink(String url) {
    super("fo:basic-link");
    if (FoBasicLink.EXTERNAL.matcher(url).find()) {
      set("external-destination", url);
    } else {
      set("internal-destination", url);
    }
  }
}
