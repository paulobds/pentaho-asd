package org.pentaho.plugins.asd.client.canvas;

import com.google.gwt.user.client.Window;

public class Point {
  private int x, y;

  public Point(int x, int y) {
    super();
    this.x = x;
    this.y = y;
  }

  public void setX(int x) {
    this.x = x;
  }

  public void setY(int y) {
    this.y = y;
  }

  public int getX() {
    return x;
  }

  public int getY() {
    return y;
  }

  public double distanceSqTo(Point p) {
    return distanceSq(x, y, p.getX(), p.getY());
  }

  public static double distanceSq(double x1, double y1, double x2, double y2) {
    x1 -= x2;
    y1 -= y2;
    return (x1 * x1 + y1 * y1);
  }

  public static double distance(double x1, double y1, double x2, double y2) {
    x1 -= x2;
    y1 -= y2;
    return Math.sqrt(x1 * x1 + y1 * y1);
  }

  /**
   * Return the coordinate on the line defined by x1,y1 to x2,y2, having traveled a distance
   * of <code>distance</code> units from the first point.
   * @param x2
   * @param y2
   * @param x1
   * @param y1
   * @param distance
   * @return
   */
  public static Point travelLine(int x1, int y1, int x2, int y2, int distance) {
//    k = (the distance you want) / (distance from A to B),
    double proportionalDistance = distance / distance(x1, y1, x2, y2);
//    Window.alert("distance="+distance);
//    Window.alert("proportional distance="+Double.toString(proportionalDistance));
    int newX = (int)Math.round(x1 + proportionalDistance*(x2 - x1));
    int newY = (int)Math.round(y1 + proportionalDistance*(y2 - y1));
    return new Point(newX, newY);
  }
}
