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

package it.smartio.docs.codeblock;

import it.smartio.docs.builder.CodeParser;
import it.smartio.docs.builder.CodeParserDefault;
import it.smartio.docs.builder.SectionBuilder;

/**
 * The {@link CodeFactory} implements a tokenizer for a specific language.
 */
public class CodeFactory {

  /**
   * Creates a {@link CodeFactory} for a specific language.
   *
   * @param name
   * @param section
   */
  public static CodeParser of(String name, SectionBuilder builder) {
    switch (name.toLowerCase()) {
      case "ini":
        return new CodeParserIni(builder);
      case "conf":
        return new CodeParserConf(builder);
      case "yaml":
        return new CodeParserYaml(builder);
      case "shell":
        return new CodeParserShell(builder);

      case "meta":
        return new CodeParserMeta(builder);
      case "java":
        return new CodeParserJava(builder);
      case "c":
      case "cpp":
      case "c++":
        return new CodeParserCpp(builder);
      case "oql":
      case "sql":
        return new CodeParserSql(builder);

      case "xml":
        return new CodeParserXml(builder);
      case "json":
        return new CodeParserJson(builder);

      case "api":
        return new CodeParserApi(builder);

      default:
        return new CodeParserDefault(builder);
    }
  }
}
