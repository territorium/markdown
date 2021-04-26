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

import org.apache.fop.apps.FOPException;
import org.apache.fop.apps.FOUserAgent;
import org.apache.fop.apps.io.InternalResourceResolver;
import org.apache.fop.fonts.EmbedFontInfo;
import org.apache.fop.fonts.FontCollection;
import org.apache.fop.render.intermediate.IFContext;
import org.apache.fop.render.intermediate.IFDocumentHandler;
import org.apache.fop.render.intermediate.IFDocumentHandlerConfigurator;
import org.apache.fop.render.pdf.PDFDocumentHandler;
import org.apache.fop.render.pdf.PDFDocumentHandlerMaker;
import org.apache.fop.render.pdf.PDFRendererConfigurator;
import org.apache.fop.render.pdf.PDFRendererConfig.PDFRendererConfigParser;

import java.util.List;

/**
 * The {@link PDFRenderer} implemens an {@link PDFDocumentHandlerMaker} with custom
 * {@link EmbedFontInfo}.
 */
public class PDFRenderer extends PDFDocumentHandlerMaker {

  private final List<EmbedFontInfo> embedFonts;

  /**
   * Constructs an instance of {@link PDFRenderer}.
   *
   * @param embedFonts
   */
  public PDFRenderer(List<EmbedFontInfo> embedFonts) {
    this.embedFonts = embedFonts;
  }

  /**
   * Creates an {@link IFDocumentHandler}.
   * 
   * @param context
   */
  @Override
  public IFDocumentHandler makeIFDocumentHandler(IFContext context) {
    PDFDocumentHandler handler = new PDFDocumentHandler(context) {

      @Override
      public IFDocumentHandlerConfigurator getConfigurator() {
        return new CustomPDFRendererConfigurator(getUserAgent());
      }
    };
    FOUserAgent userAgent = context.getUserAgent();
    if (userAgent.isAccessibilityEnabled()) {
      userAgent.setStructureTreeEventHandler(handler.getStructureTreeEventHandler());
    }
    return handler;
  }

  /**
   * The {@link CustomPDFRendererConfigurator} class.
   */
  private class CustomPDFRendererConfigurator extends PDFRendererConfigurator {

    /**
     * Constructs an instance of {@link CustomPDFRendererConfigurator}.
     *
     * @param userAgent
     */
    public CustomPDFRendererConfigurator(FOUserAgent userAgent) {
      super(userAgent, new PDFRendererConfigParser());
    }

    @Override
    protected final FontCollection getCustomFontCollection(InternalResourceResolver resolver, String mimeType)
        throws FOPException {
      return createCollectionFromFontList(resolver, embedFonts);
    }
  }
}
