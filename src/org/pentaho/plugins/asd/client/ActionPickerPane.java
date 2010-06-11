package org.pentaho.plugins.asd.client;

import java.util.ArrayList;

import org.pentaho.plugins.asd.client.canvas.ActionSequenceCanvas;

import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.LayoutPanel;
import com.google.gwt.user.client.ui.StackLayoutPanel;
import com.google.gwt.user.client.ui.Tree;
import com.google.gwt.user.client.ui.TreeItem;

public class ActionPickerPane extends StackLayoutPanel {
  final Tree actionItemTree = new Tree();
  
  public ActionPickerPane(final ActionSequenceCanvas canvas) {
    super(Unit.CM);
    addToTree(TestData.getAvailableActions());
    
    actionItemTree.addSelectionHandler(new SelectionHandler<TreeItem>() {

      public void onSelection(SelectionEvent<TreeItem> event) {
        ActionItem actionItem = (ActionItem)event.getSelectedItem().getUserObject();
        canvas.drawAction(actionItem);
      }
      
    });

    add(actionItemTree, "Actions", 1);
    add(new LayoutPanel(), "Inputs", 1);
    add(new LayoutPanel(), "Outputs", 1);
    add(new LayoutPanel(), "Flow Controls", 1);
  }
  
  private void addToTree(ArrayList<ActionItem> actionItems) {
    for (ActionItem actionItem : actionItems) {
      HTML html = new HTML("<img src=\""+ actionItem.iconUrl +"\" width=\"32\" height=\"32\"><br> " + actionItem.name);
      TreeItem treeItem = new TreeItem(html);
      treeItem.setUserObject(actionItem);
      actionItemTree.addItem(treeItem);
    }
  }
  


}
