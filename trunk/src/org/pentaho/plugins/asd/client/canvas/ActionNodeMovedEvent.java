package org.pentaho.plugins.asd.client.canvas;

public class ActionNodeMovedEvent {
  private int x, y;
  private ActionNode source;
  
  /**
   * @param toX the resulting center X coordinate
   * @param toY the resulting center Y coordinate
   * @param source the {@link ActionNode} that moved
   */
  public ActionNodeMovedEvent(int toX, int toY, ActionNode source) {
    super();
    this.x = toX;
    this.y = toY;
    this.source = source;
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
