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

package it.smartio.docs.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.function.Consumer;

import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;

/**
 * The {@link StAX} utility provides an XML parser for the {@link StAXHandler}.
 */
public abstract class StAX {

  /**
   * Create a {@link StAX} from the a resource.
   *
   * @param stream
   * @param handler
   */
  public static void parse(InputStream stream, Handler handler) throws IOException {
    try {
      XMLInputFactory xmlInputFactory = XMLInputFactory.newInstance();
      XMLEventReader reader = xmlInputFactory.createXMLEventReader(stream);

      StringBuffer buffer = null;
      while (reader.hasNext()) {
        XMLEvent event = reader.nextEvent();

        switch (event.getEventType()) {
          case XMLStreamConstants.START_ELEMENT:
            StartElement start = event.asStartElement();
            handler.handleEvent(start.getName().getLocalPart(), StAX.parseAttributes(start));
            buffer = new StringBuffer();
            break;

          case XMLStreamConstants.END_ELEMENT:
            EndElement end = event.asEndElement();
            handler.handleEvent(end.getName().getLocalPart(), buffer == null ? null : buffer.toString());
            buffer = null;
            break;

          case XMLStreamConstants.CDATA:
          case XMLStreamConstants.CHARACTERS:
            if (buffer != null) {
              buffer.append(event.asCharacters().getData());
            }
            break;

          default:
            break;
        }
      }
    } catch (XMLStreamException e) {
      throw new IOException(e);
    }
  }

  /**
   * Parses the attributes from {@link StartElement}.
   *
   * @param elem
   */
  private static Attributes parseAttributes(StartElement elem) {
    Map<String, String> attributes = new HashMap<>();
    Iterator<Attribute> iterator = elem.getAttributes();
    while (iterator.hasNext()) {
      QName name = iterator.next().getName();
      String value = elem.getAttributeByName(name).getValue();
      attributes.put(name.getLocalPart(), value);
    }
    return new Attributes(attributes);
  }

  /**
   * The {@link StAXHandler} class.
   */
  public interface Handler {

    void handleEvent(String name, Attributes attributes);

    void handleEvent(String name, String content);
  }

  public static class Attributes {

    private final Map<String, String> attrs;

    private Attributes(Map<String, String> attrs) {
      this.attrs = attrs;
    }

    public final boolean isSet(String name) {
      return this.attrs.containsKey(name);
    }

    public final String get(String name) {
      return this.attrs.get(name);
    }

    public final String get(String name, String value) {
      return isSet(name) ? this.attrs.get(name) : value;
    }

    public final boolean getBool(String name) {
      return Boolean.valueOf(get(name, "false"));
    }

    public final void onAttribute(String name, Consumer<String> consumer) {
      if (this.attrs.containsKey(name)) {
        consumer.accept(this.attrs.get(name));
      }
    }
  }
}
