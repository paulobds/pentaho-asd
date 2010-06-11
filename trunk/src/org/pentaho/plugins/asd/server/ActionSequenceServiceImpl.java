/*
 * This program is free software; you can redistribute it and/or modify it under the 
 * terms of the GNU Lesser General Public License, version 2.1 as published by the Free Software 
 * Foundation.
 *
 * You should have received a copy of the GNU Lesser General Public License along with this 
 * program; if not, you can obtain a copy at http://www.gnu.org/licenses/old-licenses/lgpl-2.1.html 
 * or from the Free Software Foundation, Inc., 
 * 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; 
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU Lesser General Public License for more details.
 *
 * Copyright 2009 Pentaho Corporation.  All rights reserved.
 *
 * Created May 20, 2009
 * @author Aaron Phillips
 */

package org.pentaho.plugins.asd.server;

import org.pentaho.actionsequence.model.ActionInputConstant;
import org.pentaho.actionsequence.model.ActionSequenceDocument;
import org.pentaho.actionsequence.model.action.HelloWorldAction;
import org.pentaho.plugins.asd.client.ActionSequenceService;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

@SuppressWarnings("serial")
public class ActionSequenceServiceImpl extends RemoteServiceServlet implements ActionSequenceService {

  public String getXmlForActionSequence(ActionSequenceDocument doc) {
    org.pentaho.actionsequence.dom.ActionSequenceDocument xmlDoc = new org.pentaho.actionsequence.dom.ActionSequenceDocument();
    org.pentaho.actionsequence.dom.actions.HelloWorldAction xmlHelloWorld = new org.pentaho.actionsequence.dom.actions.HelloWorldAction();
    xmlDoc.getRootLoop().add(xmlHelloWorld);

    HelloWorldAction helloWorld = (HelloWorldAction) doc.getExecutableChildren().get(0);
    String message = ((ActionInputConstant) helloWorld.getQuote()).getStringValue();
    xmlHelloWorld.setQuote(new org.pentaho.actionsequence.dom.ActionInputConstant(message, null));
    return xmlDoc.toString();
  }
}