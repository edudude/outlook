
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
package org.exoplatform.outlook;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

import javax.jcr.Node;

/**
 * Office user API.
 * 
 * Created by The eXo Platform SAS
 * 
 * @author <a href="mailto:pnedonosko@exoplatform.com">Peter Nedonosko</a>
 * @version $Id: OutlookEmail.java 00000 JUn 14, 2016 pnedonosko $
 * 
 */
public class OutlookMessage {

  protected final OutlookUser  user;

  protected final OutlookEmail from;

  protected List<OutlookEmail> to = new ArrayList<OutlookEmail>();

  protected String             id;

  protected String             subject;

  protected String             body;

  protected Calendar           created;

  protected Calendar           modified;

  protected Node               fileNode;

  protected OutlookMessage(OutlookUser user) {
    this.user = user;
    this.from = user;
  }

  protected OutlookMessage(OutlookUser user, OutlookEmail from) {
    this.user = user;
    this.from = from;
  }

  /**
   * @param subject
   */
  protected void setSubject(String subject) {
    this.subject = subject;
  }

  /**
   * @param body
   */
  protected void setBody(String body) {
    this.body = body;
  }

  /**
   * @param id the id to set
   */
  protected void setId(String id) {
    this.id = id;
  }

  /**
   * @param to the to to set
   */
  protected void setTo(List<OutlookEmail> to) {
    this.to = to;
  }

  /**
   * @param created the created to set
   */
  protected void setCreated(Calendar created) {
    this.created = created;
  }

  /**
   * @param modified the modified to set
   */
  protected void setModified(Calendar modified) {
    this.modified = modified;
  }

  /**
   * @return the fileNode
   */
  protected Node getFileNode() {
    return fileNode;
  }

  /**
   * @param fileNode the fileNode to set
   */
  protected void setFileNode(Node fileNode) {
    this.fileNode = fileNode;
  }

  /**
   * @return the user
   */
  public OutlookUser getUser() {
    return user;
  }

  /**
   * @return the from
   */
  public OutlookEmail getFrom() {
    return from;
  }

  /**
   * @return the to
   */
  public List<OutlookEmail> getTo() {
    return Collections.unmodifiableList(to);
  }

  /**
   * @return the id
   */
  public String getId() {
    return id;
  }

  /**
   * @return the subject
   */
  public String getSubject() {
    return subject;
  }

  /**
   * @return the body
   */
  public String getBody() {
    return body;
  }

  /**
   * @return the created
   */
  public Calendar getCreated() {
    return created;
  }

  /**
   * @return the modified
   */
  public Calendar getModified() {
    return modified;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String toString() {
    StringBuilder str = new StringBuilder("From ");
    str.append(user.toString());
    str.append(" at ");
    str.append(modified.getTime());
    return str.toString();
  }

}
