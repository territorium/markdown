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

/**
 * The {@link FoPageSequenceMaster} class.
 */
public class FoPageSequenceMaster extends FoNode {

  private final String name;
  private final FoNode master;

  public enum BlankOrNot {

    Blank("blank"),
    NotBlank("not-blank "),
    Any("any");

    public final String value;

    BlankOrNot(String value) {
      this.value = value;
    }
  }

  public enum OddOrEven {

    Odd("odd"),
    Even("even"),
    Any("any");

    public final String value;

    OddOrEven(String value) {
      this.value = value;
    }
  }

  public enum Position {

    First("first"),
    Last("last"),
    Rest("rest"),
    Any("any"),
    Only("only");

    public final String value;

    Position(String value) {
      this.value = value;
    }
  }

  /**
   * Constructs an instance of {@link FoPageSequenceMaster}.
   *
   * @param name
   */
  public FoPageSequenceMaster(String name) {
    super("fo:page-sequence-master");
    this.name = name;
    set("master-name", name);
    this.master = FoNode.create("fo:repeatable-page-master-alternatives");
    super.addNode(this.master);
  }

  public final String getPageName() {
    return this.name;
  }

  public FoNode addBlank(String reference, BlankOrNot blankOrNot) {
    FoNode builder = FoNode.create("fo:conditional-page-master-reference");
    builder.set("master-reference", reference);
    builder.set("blank-or-not-blank", blankOrNot.value);
    setNode(builder);
    return this;
  }

  public FoNode addPosition(String reference, Position position) {
    FoNode builder = FoNode.create("fo:conditional-page-master-reference");
    builder.set("master-reference", reference);
    builder.set("page-position", position.value);
    setNode(builder);
    return this;
  }

  public FoNode addOddOrEven(String reference, OddOrEven oddOrEven) {
    FoNode builder = FoNode.create("fo:conditional-page-master-reference");
    builder.set("master-reference", reference);
    builder.set("odd-or-even", oddOrEven.value);
    setNode(builder);
    return this;
  }

  public FoNode addPage(String name, Position position, OddOrEven oddOrEven) {
    return addPage(name, position.value, oddOrEven.value);
  }

  public FoNode addPage(String name, String position, String oddOrEven) {
    FoNode builder = FoNode.create("fo:conditional-page-master-reference");
    builder.set("master-reference", name);
    builder.set("page-position", position);
    builder.set("odd-or-even", oddOrEven);
    setNode(builder);
    return this;
  }

  public FoNode addBlankPage(FoSimplePageMaster page) {
    FoNode builder = FoNode.create("fo:conditional-page-master-reference");
    builder.set("master-reference", page.getPageName());
    builder.set("blank-or-not-blank", BlankOrNot.Blank.value);
    setNode(builder);
    return this;
  }

  /**
   * Add a new child {@link FoNode}.
   *
   * @param node
   */
  public FoPageSequenceMaster setNode(FoNode node) {
    this.master.addNode(node);
    return this;
  }
}
