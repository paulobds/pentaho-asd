package org.pentaho.plugins.asd.client.canvas;

import org.pentaho.plugins.asd.client.ActionItem;
import org.pentaho.plugins.asd.client.ActionSequenceView;
import org.pentaho.plugins.asd.client.StepEditorDialog;
import org.vaadin.gwtgraphics.client.DrawingArea;

import com.google.gwt.event.dom.client.DoubleClickEvent;
import com.google.gwt.event.dom.client.DoubleClickHandler;
import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.event.dom.client.MouseDownHandler;
import com.google.gwt.event.dom.client.MouseMoveEvent;
import com.google.gwt.event.dom.client.MouseMoveHandler;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.event.dom.client.MouseUpEvent;
import com.google.gwt.event.dom.client.MouseUpHandler;
import com.google.gwt.event.logical.shared.CloseEvent;
import com.google.gwt.event.logical.shared.CloseHandler;
import com.google.gwt.user.client.ui.PopupPanel;

public class ActionSequenceCanvas extends DrawingArea {

  private ActionNode selectedNode = null;

  private ActionNode nodeUnderMouse = null;

  private DirectedEdge curEdge = null;

  public ActionSequenceCanvas(int width, int height) {
    super(width, height);
  }

  public void drawAction(ActionItem actionItem) {
    final ActionNode node = new ActionNode(actionItem);
    add(node);

    ActionNodeHandler nodeHandler = new ActionNodeHandler();
    ActionCanvasHandler canvasHandler = new ActionCanvasHandler();

    node.addMouseDownHandler(nodeHandler);
    node.addMouseUpHandler(nodeHandler);
    node.addMouseOverHandler(nodeHandler);
    node.addMouseOutHandler(nodeHandler);
    node.addDoubleClickHandler(nodeHandler);
    addMouseMoveHandler(canvasHandler);
    addMouseUpHandler(canvasHandler);

    setSelectedNode(node);
  }

  private void setSelectedNode(ActionNode node) {
    if (selectedNode != node) {
      node.setSelected(true);
      if (selectedNode != null) {
        selectedNode.setSelected(false);
      }
      selectedNode = node;
    }
  }

  private ActionNode getNodeAt(int x, int y) {
    return nodeUnderMouse;
  }

  class ActionCanvasHandler implements MouseMoveHandler, MouseUpHandler {
    public void onMouseMove(MouseMoveEvent event) {
      if (!selectedNode.isAnchored()) {
        selectedNode.centerAt(event.getX(), event.getY());
      }
      if (curEdge != null) {
        curEdge.pointTo(event.getX(), event.getY());
      }
    }

    public void onMouseUp(MouseUpEvent event) {
      ActionNode sinkNode = getNodeAt(event.getX(), event.getY());

      if (curEdge != null) {
        if (sinkNode == null) {
          curEdge.destroy();
          remove(curEdge);
          curEdge = null;
        } else {
          curEdge.sinkTo(sinkNode);
          curEdge = null;
        }
      }
    }
  }

  class ActionNodeHandler implements MouseDownHandler, MouseUpHandler, MouseOutHandler, MouseOverHandler,
      DoubleClickHandler {
    public void onMouseDown(MouseDownEvent event) {
      event.preventDefault(); //suppress FF image drag, for instance
      ActionNode ai = (ActionNode) event.getSource();

      if (event.isShiftKeyDown()) {
        curEdge = new DirectedEdge(ai);
        add(curEdge);
      } else {
        ai.setAnchored(false);
      }

      setSelectedNode(ai);
    }

    public void onMouseUp(MouseUpEvent event) {
      ((ActionNode) event.getSource()).setAnchored(true);
    }

    public void onMouseOver(MouseOverEvent event) {
      ActionNode node = (ActionNode) event.getSource();
      node.setHighlighted(true);
      nodeUnderMouse = node;
    }

    public void onMouseOut(MouseOutEvent event) {
      ActionNode node = (ActionNode) event.getSource();
      node.setHighlighted(false);
      nodeUnderMouse = null;
    }

    public void onDoubleClick(DoubleClickEvent event) {
      ActionNode node = (ActionNode) event.getSource();
      final StepEditorDialog dialogBox = new StepEditorDialog(node.getActionItem());
      dialogBox.center();
      dialogBox.setPopupPosition(event.getNativeEvent().getClientX(), event.getNativeEvent().getClientY());
      dialogBox.show();
      
      dialogBox.addCloseHandler(new CloseHandler<PopupPanel>() {
        public void onClose(CloseEvent<PopupPanel> event) {
          ActionSequenceView.setActionSequece(dialogBox.getActionSequence());
        }
      });
    }
  }
}
