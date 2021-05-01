/**
 * Copyright (C) 2016 Matthieu Brouillard [http://oss.brouillard.fr/jgitver]
 * (matthieu@brouillard.fr)
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */

package it.smartio.docs.markdown.alerts;

import java.util.regex.Matcher;

import org.commonmark.node.Block;
import org.commonmark.parser.block.AbstractBlockParser;
import org.commonmark.parser.block.AbstractBlockParserFactory;
import org.commonmark.parser.block.BlockContinue;
import org.commonmark.parser.block.BlockStart;
import org.commonmark.parser.block.MatchedBlockParser;
import org.commonmark.parser.block.ParserState;

public class AlertBlockParser extends AbstractBlockParser {

  private final AlertBlock block;
  private boolean          multiline;

  public AlertBlockParser(Alert type, boolean multiline) {
    this.block = new AlertBlock(type);
    this.multiline = multiline;
  }

  @Override
  public boolean isContainer() {
    return true;
  }

  @Override
  public boolean canContain(Block block) {
    return (block != null) && !AlertBlock.class.isAssignableFrom(block.getClass());
  }

  @Override
  public AlertBlock getBlock() {
    return this.block;
  }

  @Override
  public void closeBlock() {
    // TODO Auto-generated method stub
    super.closeBlock();
  }

  @Override
  public BlockContinue tryContinue(ParserState state) {
    CharSequence content = state.getLine().getContent();
    CharSequence line = content.subSequence(state.getColumn() + state.getIndent(), content.length());

    if (!this.multiline) {
      return BlockContinue.finished();
    }

    if ((line.length() > 1) && (line.charAt(0) == '!') && (line.charAt(1) == '!')) {
      this.multiline = false;
      return BlockContinue.finished();
    }

    return BlockContinue.atColumn(state.getColumn() + state.getIndent());
  }

  /**
   * The {@link Factory} class to create an {@link AlertBlockParser}.
   */
  public static class Factory extends AbstractBlockParserFactory {

    @Override
    public BlockStart tryStart(ParserState state, MatchedBlockParser matchedBlockParser) {
      CharSequence content = state.getLine().getContent();
      CharSequence line = content.subSequence(state.getColumn(), content.length());

      Matcher matcher = Alert.matcher(line);
      if (matcher.matches()) {
        boolean isMultiline = "!".equals(matcher.group(2));
        AlertBlockParser parser = new AlertBlockParser(Alert.of(matcher.group(3)), isMultiline);
        return BlockStart.of(parser).atColumn(state.getColumn() + state.getIndent() + matcher.end(1));
      }
      return BlockStart.none();
    }
  }
}
