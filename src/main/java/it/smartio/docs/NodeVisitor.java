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

package it.smartio.docs;

/**
 * The {@link NodeVisitor} class.
 */
public interface NodeVisitor<D> {

  default void visit(Chapter node, D data) {}

  default void visit(Paragraph node, D data) {}

  default void visit(Inline node, D data) {}

  default void visit(Text node, D data) {}

  default void visit(Link node, D data) {}

  default void visit(Image node, D data) {}

  default void visit(List node, D data) {}

  default void visit(Table node, D data) {}

  default void visit(CodeNode node, D data) {}

  default void visit(Message node, D data) {}
}
