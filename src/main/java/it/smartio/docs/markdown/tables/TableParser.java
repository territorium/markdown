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

package it.smartio.docs.markdown.tables;

import java.util.ArrayList;
import java.util.List;

import org.commonmark.node.Block;
import org.commonmark.node.Node;
import org.commonmark.parser.InlineParser;
import org.commonmark.parser.SourceLine;
import org.commonmark.parser.SourceLines;
import org.commonmark.parser.block.AbstractBlockParser;
import org.commonmark.parser.block.AbstractBlockParserFactory;
import org.commonmark.parser.block.BlockContinue;
import org.commonmark.parser.block.BlockStart;
import org.commonmark.parser.block.MatchedBlockParser;
import org.commonmark.parser.block.ParserState;

class TableParser extends AbstractBlockParser {

	private final Table block = new Table();
	private final List<CharSequence> bodyLines = new ArrayList<>();
	private final List<Integer> columnWidth;
	private final List<TableCell.Alignment> columnAlign;
	private final List<String> headerCells;

	private boolean nextIsSeparatorLine = true;

	private TableParser(List<Integer> columnWidth, List<TableCell.Alignment> columnAlign, List<String> headerCells) {
		this.columnWidth = columnWidth;
		this.columnAlign = columnAlign;
		this.headerCells = headerCells;
	}

	@Override
	public boolean canHaveLazyContinuationLines() {
		return true;
	}

	@Override
	public Block getBlock() {
		return this.block;
	}

	@Override
	public BlockContinue tryContinue(ParserState state) {
		if (state.getLine().toString().contains("|")) {
			return BlockContinue.atIndex(state.getIndex());
		} else {
			return BlockContinue.none();
		}
	}

	/*
	 * @see
	 * org.commonmark.parser.block.AbstractBlockParser#addLine(org.commonmark.parser
	 * .SourceLine)
	 */
	@Override
	public void addLine(SourceLine line) {
		if (this.nextIsSeparatorLine) {
			this.nextIsSeparatorLine = false;
		} else {
			this.bodyLines.add(line.getContent());
		}
	}

	@Override
	public void parseInlines(InlineParser inlineParser) {
		int headerColumns = this.headerCells.size();

		Node head = new TableHead();
		this.block.appendChild(head);

		TableRow headerRow = new TableRow();
		head.appendChild(headerRow);
		for (int i = 0; i < headerColumns; i++) {
			String cell = this.headerCells.get(i);
			TableCell tableCell = parseCell(cell, i, inlineParser);
			tableCell.setHeader(true);
			headerRow.appendChild(tableCell);
		}

		Node body = null;
		for (CharSequence rowLine : this.bodyLines) {
			List<String> cells = TableParser.split(rowLine);
			TableRow row = new TableRow();

			// Body can not have more columns than head
			for (int i = 0; i < headerColumns; i++) {
				String cell = i < cells.size() ? cells.get(i) : "";
				TableCell tableCell = parseCell(cell, i, inlineParser);
				row.appendChild(tableCell);
			}

			if (body == null) {
				// It's valid to have a table without body. In that case, don't add an empty
				// TableBody node.
				body = new TableBody();
				this.block.appendChild(body);
			}
			body.appendChild(row);
		}
	}

	private TableCell parseCell(String cell, int column, InlineParser inlineParser) {
		TableCell tableCell = new TableCell();

		if (column < this.columnWidth.size()) {
			tableCell.setWidth(this.columnWidth.get(column));
		}
		if (column < this.columnAlign.size()) {
			tableCell.setAlignment(this.columnAlign.get(column));
		}

		SourceLines lines = new SourceLines();
		lines.addLine(SourceLine.of(cell.trim(), null));
		inlineParser.parse(lines, tableCell);

		return tableCell;
	}

	private static List<String> split(CharSequence input) {
		String line = input.toString().trim();
		if (line.startsWith("|")) {
			line = line.substring(1);
		}
		List<String> cells = new ArrayList<>();
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < line.length(); i++) {
			char c = line.charAt(i);
			switch (c) {
			case '\\':
				if (((i + 1) < line.length()) && (line.charAt(i + 1) == '|')) {
					// Pipe is special for table parsing. An escaped pipe doesn't result in a new
					// cell, but
					// is
					// passed down to inline parsing as an unescaped pipe. Note that that applies
					// even for
					// the `\|`
					// in an input like `\\|` - in other words, table parsing doesn't support
					// escaping
					// backslashes.
					sb.append('|');
					i++;
				} else {
					// Preserve backslash before other characters or at end of line.
					sb.append('\\');
				}
				break;
			case '|':
				cells.add(sb.toString());
				sb.setLength(0);
				break;
			default:
				sb.append(c);
			}
		}
		if (sb.length() > 0) {
			cells.add(sb.toString());
		}
		return cells;
	}

	// Examples of valid separators:
	//
	// |-
	// -|
	// |-|
	// -|-
	// |-|-|
	// --- | ---
	private static void parseSeparator(CharSequence s, List<Integer> width, List<TableCell.Alignment> align) {
		int pipes = 0;
		boolean valid = false;
		int i = 0;
		int w = 0;
		while (i < s.length()) {
			char c = s.charAt(i);
			switch (c) {
			case '|':
				i++;
				pipes++;
				if (pipes > 1) {
					// More than one adjacent pipe not allowed
					return;
				}
				// Need at lest one pipe, even for a one column table
				valid = true;
				break;
			case '-':
			case ':':
				w = 0;
				if ((pipes == 0) && !align.isEmpty()) {
					// Need a pipe after the first column (first column doesn't need to start with
					// one)
					return;
				}
				boolean left = false;
				boolean right = false;
				if (c == ':') {
					left = true;
					i++;
					w++;
				}
				boolean haveDash = false;
				while ((i < s.length()) && (s.charAt(i) == '-')) {
					i++;
					w++;
					haveDash = true;
				}
				if (!haveDash) {
					// Need at least one dash
					return;
				}
				if ((i < s.length()) && (s.charAt(i) == ':')) {
					right = true;
					i++;
					w++;
				}
				width.add(w);
				align.add(TableParser.getAlignment(left, right));
				// Next, need another pipe
				pipes = 0;
				break;
			case ' ':
			case '\t':
				// White space is allowed between pipes and columns
				i++;
				break;
			default:
				// Any other character is invalid
				return;
			}
		}
		if (!valid) {
		}
	}

	private static TableCell.Alignment getAlignment(boolean left, boolean right) {
		if (left && right) {
			return TableCell.Alignment.CENTER;
		} else if (left) {
			return TableCell.Alignment.LEFT;
		} else if (right) {
			return TableCell.Alignment.RIGHT;
		} else {
			return null;
		}
	}

	public static class Factory extends AbstractBlockParserFactory {

		@Override
		public BlockStart tryStart(ParserState state, MatchedBlockParser matchedBlockParser) {
			CharSequence line = state.getLine().getContent();
			SourceLines paragraph = matchedBlockParser.getParagraphLines();
			if ((paragraph != null) && paragraph.getContent().contains("|") && !paragraph.toString().contains("\n")) {
				CharSequence separatorLine = line.subSequence(state.getIndex(), line.length());
				List<Integer> width = new ArrayList<>();
				List<TableCell.Alignment> align = new ArrayList<>();
				TableParser.parseSeparator(separatorLine, width, align);
				if ((align != null) && !align.isEmpty()) {
					List<String> headerCells = TableParser.split(paragraph.getContent());
					if (align.size() >= headerCells.size()) {
						return BlockStart.of(new TableParser(width, align, headerCells)).atIndex(state.getIndex())
								.replaceActiveBlockParser();
					}
				}
			}
			return BlockStart.none();
		}
	}

}
