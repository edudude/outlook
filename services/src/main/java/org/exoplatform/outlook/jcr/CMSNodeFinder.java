/*
 * Copyright (C) 2003-2016 eXo Platform SAS.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */

package org.exoplatform.outlook.jcr;

import org.exoplatform.ecm.utils.text.Text;
import org.exoplatform.services.cms.impl.Utils;
import org.exoplatform.services.cms.link.LinkManager;
import org.exoplatform.services.cms.link.impl.NodeFinderImpl;
import org.exoplatform.services.jcr.RepositoryService;
import org.exoplatform.services.jcr.ext.app.SessionProviderService;
import org.exoplatform.services.jcr.ext.common.SessionProvider;
import org.exoplatform.services.jcr.ext.hierarchy.NodeHierarchyCreator;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.jcr.Item;
import javax.jcr.ItemNotFoundException;
import javax.jcr.Node;
import javax.jcr.NodeIterator;
import javax.jcr.PathNotFoundException;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.query.Query;
import javax.jcr.query.QueryManager;
import javax.jcr.query.QueryResult;

/**
 * Node finder based on original implementation from ECMS.<br>
 * 
 * Created by The eXo Platform SAS.
 * 
 * @author <a href="mailto:pnedonosko@exoplatform.com">Peter Nedonosko</a>
 * @version $Id: CMSNodeFinder.java 00000 Feb 26, 2013 pnedonosko $
 * 
 */
public class CMSNodeFinder extends NodeFinderImpl implements NodeFinder {

  protected final SessionProviderService sessionProviderService;

  protected final NodeHierarchyCreator   hierarchyCreator;

  public CMSNodeFinder(RepositoryService repositoryService,
                       LinkManager linkManager,
                       SessionProviderService sessionProviderService,
                       NodeHierarchyCreator hierarchyCreator) {
    super(repositoryService, linkManager);
    this.sessionProviderService = sessionProviderService;
    this.hierarchyCreator = hierarchyCreator;
  }

  /**
   * {@inheritDoc}
   */
  public String cleanName(String name) {
    // Align name to ECMS conventions
    // we keep using the dot character as separator between name and extension for backward compatibility
    int extIndex = name.lastIndexOf('.');
    StringBuilder jcrName = new StringBuilder();
    if (extIndex >= 0 && extIndex < name.length() - 1) {
      jcrName.append(Text.escapeIllegalJcrChars(Utils.cleanString(name.substring(0, extIndex))));
      String extName = Text.escapeIllegalJcrChars(Utils.cleanString(name.substring(extIndex + 1)));
      jcrName.append('.').append(extName).toString();
    } else {
      jcrName.append(Text.escapeIllegalJcrChars(Utils.cleanString(name)));
    }
    return jcrName.toString();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Item findItem(Session session, String absPath) throws PathNotFoundException, RepositoryException {
    return getItem(session, absPath, true);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Collection<Node> findLinked(Session session, String uuid) throws PathNotFoundException, RepositoryException {
    Set<Node> res = new LinkedHashSet<Node>();
    try {
      QueryManager qm = session.getWorkspace().getQueryManager();
      Query q = qm.createQuery("SELECT * FROM exo:symlink WHERE exo:uuid='" + uuid + "'", Query.SQL);
      QueryResult qr = q.execute();
      for (NodeIterator niter = qr.getNodes(); niter.hasNext();) {
        res.add(niter.nextNode());
      }
    } catch (ItemNotFoundException e) {
      // nothing
    }
    return res;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Node getUserNode(String userName) throws Exception {
    SessionProvider sessionProvider = sessionProviderService.getSessionProvider(null);
    Node userNode = hierarchyCreator.getUserNode(sessionProvider, userName);
    return userNode;
  }

}
