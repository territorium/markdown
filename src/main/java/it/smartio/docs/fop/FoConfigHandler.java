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

package it.smartio.docs.fop;

import org.apache.fop.fonts.Font;

import java.io.File;
import java.net.URI;
import java.net.URL;
import java.util.Stack;

import it.smartio.docs.config.FoPage;
import it.smartio.docs.config.FoPageItem;
import it.smartio.docs.config.FoPageItemImage;
import it.smartio.docs.config.FoPageItemPanel;
import it.smartio.docs.config.FoPageItemText;
import it.smartio.docs.config.FoPageRegion;
import it.smartio.docs.config.FoPageStatic;
import it.smartio.docs.fop.builder.FoPageSequenceMaster;
import it.smartio.docs.fop.builder.FoRegion;
import it.smartio.docs.fop.builder.FoSimplePageMaster;
import it.smartio.docs.fop.builder.FoPageSequenceMaster.BlankOrNot;
import it.smartio.docs.fop.template.FoMargin;
import it.smartio.docs.util.DataUri;
import it.smartio.docs.util.StAX;
import it.smartio.docs.util.StAX.Attributes;

/**
 * The {@link FoConfigHandler} class.
 */
public class FoConfigHandler implements StAX.Handler {

  private final File     base;
  private final FoConfig config;

  private String         width;
  private String         height;


  private FoPageSequenceMaster    master;
  private FoSimplePageMaster      simple;

  private final Stack<FoPage>     pages     = new Stack<>();
  private final Stack<FoPageItem> items     = new Stack<>();
  private final Stack<String>     fontNames = new Stack<>();

  /**
   * Constructs an instance of {@link FoConfigHandler}.
   *
   * @param base
   * @param config
   */
  public FoConfigHandler(File base, FoConfig config) {
    this.base = base;
    this.config = config;
  }

  /**
   * Get {@link FoConfig}.
   */
  protected final FoConfig getConfig() {
    return config;
  }

  @Override
  public void handleEvent(String name, Attributes attrs) {
    switch (name) {
      case "template":
        this.width = attrs.get("width");
        this.height = attrs.get("height");
        break;

      case "page":
        String attrName = attrs.get("name");
        this.simple = getConfig().addSimplePage(attrName);
        this.simple.setPageSize(this.width, this.height);

        this.pages.push(new FoPage(attrName, getConfig()));
        getConfig().add(attrName, this.pages.peek());

        FoConfigHandler.parseMargins(this.simple, attrs);
        break;

      case "region-body":
        FoRegion region = this.simple.setBodyRegion("region-body");
        if (attrs.isSet("column-count")) {
          region.setColumns(attrs.get("column-count"), attrs.get("column-gap"));
        }
        FoConfigHandler.parseMargins(region, attrs);
        break;

      case "region-north":
        attrName =
            attrs.isSet("name") ? attrs.get("name") : String.format("region-north-%s", this.simple.getPageName());
        region = this.simple.addRegionBefore(attrName);
        region.setExtent(attrs.get("extent")).setPrecedence(attrs.get("precedence"))
            .setDisplayAlign(attrs.get("align"));

        this.pages.push(new FoPageRegion(attrName, getConfig()));
        getConfig().add(attrName, this.pages.peek());
        getConfig().get(this.simple.getPageName()).addPage(attrName);
        if (attrs.isSet("background")) {
          String background = attrs.get("background");
          if (background.startsWith("#")) {
            ((FoPageRegion) this.pages.peek()).setBackground(background);
          } else {
            ((FoPageRegion) this.pages.peek()).setBackground(DataUri.toURI(base, background));
          }
        }
        break;

      case "region-south":
        attrName =
            attrs.isSet("name") ? attrs.get("name") : String.format("region-south-%s", this.simple.getPageName());
        region = this.simple.addRegionAfter(attrName);
        region.setExtent(attrs.get("extent")).setPrecedence(attrs.get("precedence"));
        region.setDisplayAlign(attrs.get("align"));

        this.pages.push(new FoPageRegion(attrName, getConfig()));
        getConfig().add(attrName, this.pages.peek());
        getConfig().get(this.simple.getPageName()).addPage(attrName);
        if (attrs.isSet("background")) {
          String background = attrs.get("background");
          if (background.startsWith("#")) {
            ((FoPageRegion) this.pages.peek()).setBackground(background);
          } else {
            ((FoPageRegion) this.pages.peek()).setBackground(DataUri.toURI(base, background));
          }
        }
        break;

      case "region-east":
        attrName = attrs.isSet("name") ? attrs.get("name") : String.format("region-east-%s", this.simple.getPageName());
        region = this.simple.addRegionStart(attrName);
        region.setExtent(attrs.get("extent")).setPrecedence(attrs.get("precedence"));
        region.setBorderWidth(attrs.get("border")).setPadding(attrs.get("padding"));
        region.setReferenceOrientation(attrs.get("orientation"));

        this.pages.push(new FoPageRegion(attrName, getConfig()));
        getConfig().add(attrName, this.pages.peek());
        getConfig().get(this.simple.getPageName()).addPage(attrName);
        if (attrs.isSet("background")) {
          String background = attrs.get("background");
          if (background.startsWith("#")) {
            ((FoPageRegion) this.pages.peek()).setBackground(background);
          } else {
            ((FoPageRegion) this.pages.peek()).setBackground(DataUri.toURI(base, background));
          }
        }
        break;

      case "region-west":
        attrName = attrs.isSet("name") ? attrs.get("name") : String.format("region-west-%s", this.simple.getPageName());
        region = this.simple.addRegionEnd(attrName);
        region.setExtent(attrs.get("extent")).setPrecedence(attrs.get("precedence"));
        region.setBorderWidth(attrs.get("border")).setPadding(attrs.get("padding"));
        region.setReferenceOrientation(attrs.get("orientation"));

        this.pages.push(new FoPageRegion(attrName, getConfig()));
        getConfig().add(attrName, this.pages.peek());
        getConfig().get(this.simple.getPageName()).addPage(attrName);
        if (attrs.isSet("background")) {
          String background = attrs.get("background");
          if (background.startsWith("#")) {
            ((FoPageRegion) this.pages.peek()).setBackground(background);
          } else {
            ((FoPageRegion) this.pages.peek()).setBackground(DataUri.toURI(base, background));
          }
        }
        break;

      case "page-sequence":
        attrName = attrs.get("name");
        this.master = getConfig().addMasterPage(attrName);

        getConfig().add(attrName, new FoPage(attrName, getConfig()));
        break;

      case "page-reference":
        attrName = attrs.get("name");
        getConfig().get(this.master.getPageName()).addPage(attrName);
        if ("blank".equalsIgnoreCase(attrs.get("pagination"))) {
          this.master.addBlank(attrName, BlankOrNot.Blank);
        } else {
          this.master.addPage(attrName, attrs.get("position"), attrs.get("pagination"));
        }
        break;

      case "static":
        FoPageStatic page = new FoPageStatic();
        attrs.onAttribute("top", v -> page.setTop(v));
        attrs.onAttribute("left", v -> page.setLeft(v));
        attrs.onAttribute("right", v -> page.setRight(v));
        attrs.onAttribute("bottom", v -> page.setBottom(v));
        attrs.onAttribute("background", v -> page.setBackground(v));

        attrs.onAttribute("color", v -> page.setColor(v));
        attrs.onAttribute("font-size", v -> page.setFontSize(v));
        attrs.onAttribute("font-style", v -> page.setFontStyle(v));
        attrs.onAttribute("font-weight", v -> page.setFontWeight(v));
        attrs.onAttribute("text-align", v -> page.setTextAlign(v));;
        attrs.onAttribute("line-height", v -> page.setLineHeight(v));

        ((FoPageRegion) this.pages.peek()).addStatic(page);
        break;

      case "image":
        URI uri = DataUri.toURI(base, attrs.get("uri"));
        FoPageItemImage image = new FoPageItemImage(uri);
        if (this.items.isEmpty()) {
          ((FoPageRegion) this.pages.peek()).getStatic().addItem(image);
        } else {
          ((FoPageItemPanel) this.items.peek()).addItem(image);
        }
        this.items.add(image);
        break;

      case "text":
        FoPageItemText text = new FoPageItemText();
        attrs.onAttribute("color", v -> text.setColor(v));
        attrs.onAttribute("font-size", v -> text.setFontSize(v));
        attrs.onAttribute("font-style", v -> text.setFontStyle(v));
        attrs.onAttribute("font-weight", v -> text.setFontWeight(v));
        attrs.onAttribute("text-align", v -> text.setTextAlign(v));;
        attrs.onAttribute("line-height", v -> text.setLineHeight(v));

        attrs.onAttribute("top", v -> text.setTop(v));
        attrs.onAttribute("left", v -> text.setLeft(v));
        attrs.onAttribute("right", v -> text.setRight(v));
        attrs.onAttribute("bottom", v -> text.setBottom(v));

        if (this.items.isEmpty()) {
          ((FoPageRegion) this.pages.peek()).getStatic().addItem(text);
        } else {
          ((FoPageItemPanel) this.items.peek()).addItem(text);
        }
        this.items.add(text);
        break;

      case "panel":
        FoPageItemPanel panel = new FoPageItemPanel();
        attrs.onAttribute("color", v -> panel.setColor(v));
        attrs.onAttribute("font-size", v -> panel.setFontSize(v));
        attrs.onAttribute("font-style", v -> panel.setFontStyle(v));
        attrs.onAttribute("font-weight", v -> panel.setFontWeight(v));
        attrs.onAttribute("text-align", v -> panel.setTextAlign(v));;
        attrs.onAttribute("line-height", v -> panel.setLineHeight(v));

        attrs.onAttribute("span", v -> panel.setSpan(v));
        attrs.onAttribute("top", v -> panel.setTop(v));
        attrs.onAttribute("left", v -> panel.setLeft(v));
        attrs.onAttribute("right", v -> panel.setRight(v));
        attrs.onAttribute("bottom", v -> panel.setBottom(v));

        if (this.items.isEmpty()) {
          ((FoPageRegion) this.pages.peek()).getStatic().addItem(panel);
        } else {
          ((FoPageItemPanel) this.items.peek()).addItem(panel);
        }
        this.items.add(panel);
        break;

      case "font":
        fontNames.add(attrs.get("name"));
        break;

      case "font-metric":
        URI fontURI = DataUri.toURI(base, attrs.get("file"));
        int weight = Boolean.valueOf(attrs.get("bold")) ? Font.WEIGHT_BOLD : Font.WEIGHT_NORMAL;
        String style = Boolean.valueOf(attrs.get("italic")) ? Font.STYLE_ITALIC : Font.STYLE_NORMAL;
        getConfig().addFontInfo(FoFont.createEmbed(fontNames.peek(), style, weight, fontURI.toString()));
        break;


    }
  }

  @Override
  public void handleEvent(String name, String content) {
    switch (name) {
      case "page":
      case "region-north":
      case "region-south":
      case "region-east":
      case "region-west":
      case "page-reference":
        break;

      case "text":
        ((FoPageItemText) this.items.peek()).setText(content);
      case "image":
      case "panel":
        this.items.pop();
        break;

      case "template":
        if (!fontNames.contains(Fo.FONT_SYMBOLS)) {
          String ttf = FoFont.class.getResource("/fonts/MaterialIcons-Regular.ttf").toString();
          URL codepoints = FoFont.class.getResource("/fonts/MaterialIcons-Regular.ttf.codepoints");
          FoFont.registerSymbols(Fo.FONT_SYMBOLS, codepoints);
          getConfig().addFontInfo(FoFont.createEmbed(Fo.FONT_SYMBOLS, Font.STYLE_NORMAL, Font.WEIGHT_NORMAL, ttf));
        }
        if (!fontNames.contains(Fo.FONT_CODE)) {
          String url = FoFont.class.getResource("/fonts/UbuntuMono-Regular.ttf").toString();
          getConfig().addFontInfo(FoFont.createEmbed(Fo.FONT_CODE, Font.STYLE_NORMAL, Font.WEIGHT_NORMAL, url));

          url = FoFont.class.getResource("/fonts/UbuntuMono-Bold.ttf").toString();
          getConfig().addFontInfo(FoFont.createEmbed(Fo.FONT_CODE, Font.STYLE_NORMAL, Font.WEIGHT_BOLD, url));

          url = FoFont.class.getResource("/fonts/UbuntuMono-Italic.ttf").toString();
          getConfig().addFontInfo(FoFont.createEmbed(Fo.FONT_CODE, Font.STYLE_ITALIC, Font.WEIGHT_NORMAL, url));

          url = FoFont.class.getResource("/fonts/UbuntuMono-BoldItalic.ttf").toString();
          getConfig().addFontInfo(FoFont.createEmbed(Fo.FONT_CODE, Font.STYLE_ITALIC, Font.WEIGHT_BOLD, url));
        }
        if (!fontNames.contains(Fo.FONT_TEXT)) {
          String url = FoFont.class.getResource("/fonts/RobotoCondensed-Regular.ttf").toString();
          getConfig().addFontInfo(FoFont.createEmbed(Fo.FONT_TEXT, Font.STYLE_NORMAL, Font.WEIGHT_NORMAL, url));

          url = FoFont.class.getResource("/fonts/RobotoCondensed-Bold.ttf").toString();
          getConfig().addFontInfo(FoFont.createEmbed(Fo.FONT_TEXT, Font.STYLE_NORMAL, Font.WEIGHT_BOLD, url));

          url = FoFont.class.getResource("/fonts/RobotoCondensed-Italic.ttf").toString();
          getConfig().addFontInfo(FoFont.createEmbed(Fo.FONT_TEXT, Font.STYLE_ITALIC, Font.WEIGHT_NORMAL, url));

          url = FoFont.class.getResource("/fonts/RobotoCondensed-BoldItalic.ttf").toString();
          getConfig().addFontInfo(FoFont.createEmbed(Fo.FONT_TEXT, Font.STYLE_ITALIC, Font.WEIGHT_BOLD, url));
        }
        break;

      default:
        break;
    }
  }

  /**
   * Parses the {@link FoMargin}.
   *
   * @param elem
   * @param attrs
   */
  private static void parseMargins(FoMargin<?> elem, Attributes attrs) {
    attrs.onAttribute("top", v -> elem.setMarginTop(v));
    attrs.onAttribute("left", v -> elem.setMarginLeft(v));
    attrs.onAttribute("right", v -> elem.setMarginRight(v));
    attrs.onAttribute("bottom", v -> elem.setMarginBottom(v));
  }
}
