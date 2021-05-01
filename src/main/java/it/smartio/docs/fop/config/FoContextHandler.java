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

import java.net.URI;
import java.util.Stack;

import it.smartio.docs.util.DataUri;
import it.smartio.docs.util.StAX;
import it.smartio.docs.util.StAX.Attributes;

/**
 * The {@link FoContextHandler} class.
 */
class FoContextHandler implements StAX.Handler {

  private final UITemplate    template;
  private final Stack<UIFont> fonts = new Stack<>();
  private final Stack<UIItem> items = new Stack<>();

  /**
   * Constructs an instance of {@link FoContextHandler}.
   *
   * @param template
   */
  public FoContextHandler(UITemplate template) {
    this.template = template;
  }

  /**
   * Get {@link UITemplate}.
   */
  protected final UITemplate getTemplate() {
    return this.template;
  }

  /**
   * Get top {@link UIItem}.
   *
   * @param clazz
   */
  @SuppressWarnings("unchecked")
  protected final <I extends UIItem> I getUIItem(Class<I> clazz) {
    return (I) this.items.peek();
  }


  @Override
  public void handleEvent(String name, Attributes attrs) {
    switch (name) {
      case "template":
        getTemplate().setSize(attrs.get("width"), attrs.get("height"));
        break;

      case "font":
        this.fonts.add(getTemplate().addFontName(attrs.get("name")));
        break;

      case "font-metric":
        URI uri = DataUri.toURI(getTemplate().getWorkingDir(), attrs.get("file"));
        this.fonts.peek().addMetric(uri, attrs.getBool("bold"), attrs.getBool("italic"));
        break;

      case "page":
        UIPage page = getTemplate().addPage(attrs.get("name"));
        page.setPageSize(attrs.get("width", getTemplate().getWidth()), attrs.get("height", getTemplate().getHeight()));
        this.items.push(page);

        if (attrs.isSet("column-count")) {
          page.setColumns(attrs.get("column-count"), attrs.get("column-gap"));
        }

        attrs.onAttribute("padding-top", p -> page.setPaddingTop(p));
        attrs.onAttribute("padding-left", p -> page.setPaddingLeft(p));
        attrs.onAttribute("padding-right", p -> page.setPaddingRight(p));
        attrs.onAttribute("padding-bottom", p -> page.setPaddingBottom(p));
        attrs.onAttribute("padding", p -> {
          String[] padding = p.split(",");
          if (padding.length == 1) {
            padding = new String[] { padding[0], padding[0], padding[0], padding[0] };
          } else if (padding.length == 2) {
            padding = new String[] { padding[0], padding[1], padding[0], padding[1] };
          }

          page.setPaddingTop(padding[0]);
          page.setPaddingLeft(padding[3]);
          page.setPaddingRight(padding[1]);
          page.setPaddingBottom(padding[2]);
        });

        break;

      case "page-top":
        addPageRegion(attrs, getUIItem(UIPage.class).addTop(attrs.get("name")));
        break;

      case "page-left":
        // region.getRegion().setReferenceOrientation(attrs.get("orientation"));
        addPageRegion(attrs, getUIItem(UIPage.class).addLeft(attrs.get("name")));
        break;

      case "page-right":
        // region.getRegion().setReferenceOrientation(attrs.get("orientation"));
        addPageRegion(attrs, getUIItem(UIPage.class).addRight(attrs.get("name")));
        break;

      case "page-bottom":
        addPageRegion(attrs, getUIItem(UIPage.class).addBottom(attrs.get("name")));
        break;

      case "panel":
        UIContainer container = getUIItem(UIContainer.class).addContainer();
        this.items.push(container);

        attrs.onAttribute("top", v -> container.setTop(v));
        attrs.onAttribute("left", v -> container.setLeft(v));
        attrs.onAttribute("right", v -> container.setRight(v));
        attrs.onAttribute("bottom", v -> container.setBottom(v));

        attrs.onAttribute("color", v -> container.setColor(v));
        attrs.onAttribute("background", v -> container.setBackground(v));

        attrs.onAttribute("font-size", v -> container.setFontSize(v));
        attrs.onAttribute("font-style", v -> container.setFontStyle(v));
        attrs.onAttribute("font-weight", v -> container.setFontWeight(v));
        attrs.onAttribute("text-align", v -> container.setTextAlign(v));;
        attrs.onAttribute("line-height", v -> container.setLineHeight(v));
        break;

      case "image":
        uri = DataUri.toURI(getTemplate().getWorkingDir(), attrs.get("uri"));
        UIImage image = new UIImage(uri);
        getUIItem(UIContainer.class).addItem(image);
        this.items.add(image);
        break;

      case "text":
        UIText text = new UIText();
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

        getUIItem(UIContainer.class).addItem(text);
        this.items.add(text);
        break;
    }
  }

  @Override
  public void handleEvent(String name, String content) {
    switch (name) {
      case "font":
        this.fonts.pop();
        break;

      case "page":
      case "page-top":
      case "page-left":
      case "page-right":
      case "page-bottom":
      case "panel":
      case "image":
        this.items.pop();
        break;

      case "text":
        getUIItem(UIText.class).setText(content);
        this.items.pop();
        break;

      default:
        break;
    }
  }

  /**
   * Adds a {@link UIPageRegion}.
   *
   * @param attrs
   */
  protected void addPageRegion(Attributes attrs, UIPageRegion region) {
    this.items.push(region);
    region.getRegion().setExtent(attrs.get("extent"));
    if (attrs.isSet("background")) {
      String background = attrs.get("background");
      if (background.startsWith("#")) {
        region.setBackground(background);
      } else {
        region.setBackground(DataUri.toURI(getTemplate().getWorkingDir(), background));
      }
    }
  }
}
