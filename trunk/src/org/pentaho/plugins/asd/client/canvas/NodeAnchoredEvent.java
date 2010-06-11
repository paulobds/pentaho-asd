package org.pentaho.plugins.asd.client.canvas;

public class NodeAnchoredEvent {
  
  private int x, y;
  private ActionNode source;
  
  public NodeAnchoredEvent(int x, int y, ActionNode source) {
    super();
    this.source = source;
    this.x = x;
    this.y = y;
  }

  public int getX() {
    return x;
  }

  public int getY() {
    return y;
  }

  public ActionNode getSource() {
    return source;
  }


}
