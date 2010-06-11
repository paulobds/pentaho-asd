package org.pentaho.plugins.asd.client;

import org.pentaho.plugins.asd.client.canvas.ActionSequenceCanvas;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.ServiceDefTarget;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.TabLayoutPanel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.Widget;

public class ActionSequenceEditorPane extends TabLayoutPanel {
  ActionSequenceCanvas asEditorCanvas; 
  TextArea textArea = new TextArea();
  ActionSequenceServiceAsync actionSequenceService = GWT.create(ActionSequenceService.class);

  public ActionSequenceEditorPane(double barHeight, Unit barUnit) {
    super(barHeight, barUnit);
    asEditorCanvas = new ActionSequenceCanvas(1200, 1200);
    ScrollPanel asEditorScrollPanel = new ScrollPanel(asEditorCanvas);
    add(asEditorScrollPanel, "Canvas");
    add(createXmlEditorPanel(), "XML");
    
    addSelectionHandler(new SelectionHandler<Integer>() {

      public void onSelection(SelectionEvent<Integer> event) {
        if(1 == event.getSelectedItem()) {
          actionSequenceService.getXmlForActionSequence(ActionSequenceView.getActionSequece(), new AsyncCallback<String>() {
            
            public void onSuccess(String xml) {
              textArea.setText(xml);
            }
            
            public void onFailure(Throwable caught) {
              Window.alert(caught.getMessage());
              caught.printStackTrace();
            }
          });
        }
      }
    });
  }
  
  public ActionSequenceCanvas getCanvas() {
    return asEditorCanvas;
  }
  
  private Widget createXmlEditorPanel() {
    textArea.setReadOnly(true);
    textArea.setSize("100%", "100%");
    
    ServiceDefTarget endpoint = (ServiceDefTarget) actionSequenceService;
    endpoint.setServiceEntryPoint(ActionSequenceView.getBaseUrl());
    
    return textArea;
  }

}
