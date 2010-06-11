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

package org.pentaho.plugins.asd.client;

import org.pentaho.actionsequence.model.ActionSequenceDocument;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.RootLayoutPanel;
import com.google.gwt.user.client.ui.SplitLayoutPanel;

public class ActionSequenceView implements EntryPoint {
  
  private static ActionSequenceDocument actionSequece;

  public static ActionSequenceDocument getActionSequece() {
    return actionSequece;
  }

  public static void setActionSequece(ActionSequenceDocument as) {
    actionSequece = as;
  }

  public void onModuleLoad() {
    ActionSequenceEditorPane editorPane = new ActionSequenceEditorPane(1, Unit.CM);

    SplitLayoutPanel splitPanel = new SplitLayoutPanel();
    splitPanel.addWest(new ActionPickerPane(editorPane.getCanvas()), 175);
    splitPanel.addSouth(new HTML("log"), 200);
    splitPanel.add(editorPane);
    RootLayoutPanel.get().add(splitPanel);
  }

  /** 
   * This hackery has to be done so we get to the /pentaho context where our 
   * gecho servlet lives (/pentaho/gecho/service).  If we don't parse out the plugin-related
   * parts of the module url,  the GWT client code will wrongly POST to /pentaho/content/gecho-res/gecho/service.
   * 
   * @return the true URL to the rpc service
   */
  public static String getBaseUrl() {
    String moduleUrl = GWT.getModuleBaseURL();
    
    //
    //Set the base url appropriately based on the context in which we are running this client
    //
    if(moduleUrl.indexOf("content") > -1) {
      //we are running the client in the context of a BI Server plugin, so 
      //point the request to the GWT rpc proxy servlet
      String baseUrl = moduleUrl.substring(0, moduleUrl.indexOf("content"));
      //NOTE: the dispatch URL ("gechoService") must match the bean id for 
      //this service object in your plugin.xml.  "gwtrpc" is the servlet 
      //that handles plugin gwt rpc requests in the BI Server.
      return  baseUrl + "gwtrpc/actionSequenceService";
    }
    //we are running this client in hosted mode, so point to the servlet 
    //defined in war/WEB-INF/web.xml
    return moduleUrl + "service";
  }
}
