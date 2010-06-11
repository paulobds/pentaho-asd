package org.pentaho.plugins.asd.client;

import org.pentaho.actionsequence.model.ActionInputConstant;
import org.pentaho.actionsequence.model.ActionSequenceDocument;
import org.pentaho.actionsequence.model.action.HelloWorldAction;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class StepEditorDialog extends DialogBox {
  
  TextArea textArea = new TextArea();
  
  public StepEditorDialog(ActionItem actionItem) {
    setText("Edit Step \""+actionItem.name+"\"");

    VerticalPanel dialogContents = new VerticalPanel();
    dialogContents.setSpacing(4);
    setWidget(dialogContents);
    
    textArea.setVisibleLines(5);
    
    dialogContents.add(new HTML("<br><br>Message:"));
    dialogContents.add(textArea);
    dialogContents.add(createOkCancelPanel());
  }

  private Widget createOkCancelPanel() {
    HorizontalPanel panel = new HorizontalPanel();
    panel.setHorizontalAlignment(HorizontalPanel.ALIGN_RIGHT);
    
    Button okButton = new Button("Ok", new ClickHandler() {
      public void onClick(ClickEvent event) {
        hide();
      }
    });

    panel.add(okButton);

    Button cancelButton = new Button("Cancel", new ClickHandler() {
      public void onClick(ClickEvent event) {
        hide();
      }
    });
    panel.add(cancelButton);
    
    return panel;
  }
  
  public ActionSequenceDocument getActionSequence() {
    ActionSequenceDocument actionSequenceDocument = new ActionSequenceDocument();    
    HelloWorldAction helloWorldAction= new HelloWorldAction();
    helloWorldAction.setQuote(new ActionInputConstant(textArea.getText()));
    actionSequenceDocument.getRootLoop().add(helloWorldAction);
    return actionSequenceDocument;
  }

}
