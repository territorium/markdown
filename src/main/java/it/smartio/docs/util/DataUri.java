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

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Base64;

/**
 * The {@link DataUri} class.
 */
public abstract class DataUri {

  private static final String EMPTY =
      "data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAAEAAAABCAQAAAC1HAwCAAAAC0lEQVR42mNkYAAAAAYAAjCB0C8AAAAASUVORK5CYII=";

  /**
   * Avoid an instance of {@link DataUri}.
   */
  private DataUri() {}

  /**
   * Get the {@link InputStream}.
   * 
   * @param uri
   */
  private static final InputStream getInputStream(String uri) throws FileNotFoundException {
    return new File(uri).exists() ? new FileInputStream(uri) : DataUri.class.getResourceAsStream(uri);
  }

  /**
   * Converts the path to an URI.
   *
   * @param base
   * @param path
   */
  public static InputStream toInputStream(File base, String path) throws IOException {
    if (path.startsWith(":")) {
      return DataUri.class.getResourceAsStream("/" + path.substring(1));
    }
    return new FileInputStream(path.startsWith("/") ? new File(path) : new File(base, path));
  }

  /**
   * Converts the path to an URI.
   *
   * @param base
   * @param path
   */
  public static URI toURI(File base, String path) {
    if (path.startsWith(":")) {
      try {
        return DataUri.class.getResource("/" + path.substring(1)).toURI();
      } catch (URISyntaxException e) {
        e.printStackTrace();
      }
      // String uri = String.format(
      // "jar:file:/home/brigl/.gradle/caches/jars-8/8c8e4a53f085475a7fa0b3a4cced5e89/markdown-1.0.0.jar!/%s",
      // path.substring(1));
      // return URI.create(uri);
    }
    return path.startsWith("/") ? new File(path).toURI() : new File(base, path).toURI();
  }

  /**
   * Load an image resource from {@link ClassLoader} as DataURL.
   *
   * @param resource
   */
  public static final String loadImage(String resource) {
    String contentType = resource.toLowerCase().endsWith(".png") ? "png" : "jpeg";
    try (InputStream stream = getInputStream(resource)) {
      byte[] bytes = stream.readAllBytes();
      String base64 = Base64.getEncoder().encodeToString(bytes);
      return String.format("data:image/%s;base64,%s", contentType, base64);
    } catch (IOException e) {}
    return DataUri.EMPTY;
  }

  /**
   * Load an image resource from {@link ClassLoader} as DataURL.
   *
   * @param uri
   */
  public static final String loadImage(URI uri) {
    String contentType = uri.toString().toLowerCase().endsWith(".png") ? "png" : "jpeg";
    try (InputStream stream = uri.toURL().openStream()) {
      byte[] bytes = stream.readAllBytes();
      String base64 = Base64.getEncoder().encodeToString(bytes);
      return String.format("data:image/%s;base64,%s", contentType, base64);
    } catch (IOException e) {}
    return DataUri.EMPTY;
  }
}
