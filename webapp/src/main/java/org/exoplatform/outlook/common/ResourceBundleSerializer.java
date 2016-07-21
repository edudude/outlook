
/*
 * Copyright (C) 2003-2016 eXo Platform SAS.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */
package org.exoplatform.outlook.common;

import juzu.RequestScoped;

import java.util.ResourceBundle;

/**
 * Created by The eXo Platform SAS
 * 
 * @author <a href="mailto:pnedonosko@exoplatform.com">Peter Nedonosko</a>
 * @version $Id: ResourceBundleSerializer.java 00000 Jul 8, 2016 pnedonosko $
 * 
 */
@RequestScoped
public class ResourceBundleSerializer {

  /**
   * 
   */
  public ResourceBundleSerializer() {
  }

  public String toJSON(ResourceBundle bundle) {
    StringBuilder str = new StringBuilder();
    str.append("{\"locale\":\"");
    str.append(bundle.getLocale().getLanguage());
    String country = bundle.getLocale().getCountry();
    if (country != null && country.length() > 0) {
      str.append('_');
      str.append(country);
    }
    str.append("\",");
    str.append("\"messages\":{");
    boolean addComma = false;
    for (String k : bundle.keySet()) {
      if (k.startsWith("Outlook.messages")) {
        if (addComma) {
          str.append(',');
        }
        str.append('"');
        str.append(k);
        str.append("\":\"");
        str.append(bundle.getString(k));
        str.append('"');
        addComma = true;
      }
    }
    str.append("}}");
    return str.toString();
  }

}