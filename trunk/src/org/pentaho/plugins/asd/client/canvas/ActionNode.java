package org.pentaho.plugins.asd.client.canvas;

import java.util.HashSet;
import java.util.Set;

import org.pentaho.plugins.asd.client.ActionItem;
import org.vaadin.gwtgraphics.client.Group;
import org.vaadin.gwtgraphics.client.Image;
import org.vaadin.gwtgraphics.client.shape.Rectangle;

public class ActionNode extends Group {
  private static double OPACITY_HIGHLIGHTED = 0.5;

  private static double OPACITY_SELECTED = 0.65;

  private static double OPACITY_DEFAULT = 0.1;

  private boolean anchored = true;

  private ActionItem actionItem;

  private Set<ActionNodeMoveListener> moveListeners = new HashSet<ActionNodeMoveListener>();
  private Set<NodeAnchoredListener> anchoredListeners = new HashSet<NodeAnchoredListener>();

  private Point[] connectors = new Point[4];

  private Image image;

  private boolean selected = false;

  private Rectangle highlightRect;

  public ActionNode(ActionItem item) {
    this.actionItem = item;
    image = new Image(0, 0, 32, 32, item.iconUrl);
    add(image);
    highlightRect = new Rectangle(image.getX(), image.getY(), 32, 32);
    highlightRect.setFillOpacity(0);
    highlightRect.setStrokeOpacity(OPACITY_DEFAULT);
    add(highlightRect);
    centerAt(50, 50);
    setAnchored(true);
  }

  public ActionItem getActionItem() {
    return actionItem;
  }

  public void setHighlighted(boolean b) {
    if (selected)
      return;
    if (b) {
      highlightRect.setStrokeOpacity(OPACITY_HIGHLIGHTED);
    } else {
      highlightRect.setStrokeOpacity(OPACITY_DEFAULT);
    }
  }

  public void setSelected(boolean b) {
    selected = b;
    if (b) {
      highlightRect.setStrokeOpacity(OPACITY_SELECTED);
      highlightRect.setStrokeWidth(2);
    } else {
      highlightRect.setStrokeOpacity(OPACITY_DEFAULT);
      highlightRect.setStrokeWidth(1);
    }
  }

  public int getX() {
    return highlightRect.getX();
  }

  public int getY() {
    return highlightRect.getY();
  }

  public int getCenterX() {
    return highlightRect.getX() + 16;
  }

  public int getCenterY() {
    return highlightRect.getY() + 16;
  }

  public Point getCenter() {
    return new Point(getCenterX(), getCenterY());
  }

  public boolean isAnchored() {
    return anchored;
  }

  public void setAnchored(boolean anchored) {
    this.anchored = anchored;
    if (anchored) {
      int cX = getCenterX();
      int cY = getCenterY();
      //order T-R-B-L
      connectors[0] = new Point(cX, cY + 16);
      connectors[1] = new Point(cX + 16, cY);
      connectors[2] = new Point(cX, cY - 16);
      connectors[3] = new Point(cX - 16, cY);
      
      for (NodeAnchoredListener l : anchoredListeners) {
        l.nodeAnchored(new NodeAnchoredEvent(cX, cY, this));
      }
    }
  }

  public void centerAt(int x, int y) {
    int xCoord = x - 16;
    int yCoord = y - 16;
    highlightRect.setX(xCoord);
    image.setX(xCoord);
    highlightRect.setY(yCoord);
    image.setY(yCoord);

    for (ActionNodeMoveListener moveListener : moveListeners) {
      moveListener.nodeMoved(new ActionNodeMovedEvent(x, y, this));
    }
  }

  public void addMoveListener(ActionNodeMoveListener listener) {
    moveListeners.add(listener);
  }

  public void removeMoveListener(ActionNodeMoveListener listener) {
    moveListeners.remove(listener);
  }
  
  public void addAnchoredListener(NodeAnchoredListener listener) {
    anchoredListeners.add(listener);
  }
  
  public void removeAnchoredListener(NodeAnchoredListener listener) {
    anchoredListeners.remove(listener);
  }

  public Point getNearestConnector(int x, int y) {

    Point p = new Point(x, y);
    double leastDistance = Double.MAX_VALUE;
    Point nearest = null;
    for (Point conn : connectors) {

      double dist = p.distanceSqTo(conn);
      if (dist < leastDistance) {
        leastDistance = dist;
        nearest = conn;
      }
    }
    return nearest;
  }

}
