package org.pentaho.plugins.asd.client.canvas;

import org.vaadin.gwtgraphics.client.Group;
import org.vaadin.gwtgraphics.client.shape.Circle;
import org.vaadin.gwtgraphics.client.shape.Path;
import org.vaadin.gwtgraphics.client.shape.path.LineTo;

public class DirectedEdge extends Group implements ActionNodeMoveListener, NodeAnchoredListener {

  private Path path = null;
  private ArrowHead arrowHead = null;

  private ActionNode sourceNode, sinkNode;
  
  private boolean processingEvent;

  private int toX, toY;

  private ActionNodeMovedEvent curMoveEvent;

  public DirectedEdge(ActionNode node) {
    super();
    this.sourceNode = node;
    path = new Path(sourceNode.getCenterX(), sourceNode.getCenterY());
    path.close();
    add(path);
    
    arrowHead = new ArrowHead(sourceNode.getX(), sourceNode.getY());
    add(arrowHead);
    
    pop(sourceNode);
    sourceNode.addMoveListener(this);
    sourceNode.addAnchoredListener(this);
  }

  public void sinkTo(ActionNode node) {
    this.sinkNode = node;
    redraw();
    sinkNode.addMoveListener(this);
    sinkNode.addAnchoredListener(this);
  }
  
  private void redraw() {
    Point sinkConn = sinkNode.getNearestConnector(getX(), getY());
    path.setStep(1, new LineTo(false, sinkConn.getX(), sinkConn.getY()));
    pop(sinkNode);
    
    Point sourceConn = sourceNode.getNearestConnector(sinkConn.getX(), sinkConn.getY());
    path.setX(sourceConn.getX());
    path.setY(sourceConn.getY());
    
    arrowHead.redrawOnLine(sourceConn.getX(), sourceConn.getY(), sinkConn.getX(), sinkConn.getY());
  }

  public void destroy() {
    if (sourceNode != null) {
      sourceNode.removeMoveListener(this);
      sourceNode.removeAnchoredListener(this);
    }
    if (sinkNode != null) {
      sinkNode.removeMoveListener(this);
      sinkNode.removeAnchoredListener(this);
    }
    remove(path);
  }

  public void pointTo(int x, int y) {
    toX = x;
    toY = y;
    path.setStep(1, new LineTo(false, x, y));
  }
  
  public int getX() {
    return path.getX();
  }
  public int getY() {
    return path.getY();
  }

  class ArrowHead extends Group {
    private Path p = null;
    private static final int magnitude = 8;
    int length = magnitude; //to arrow point
    int width = magnitude - 2; //across arrow sides
    //because this is an isosceles triangle, we know the centroid is at 1/3h
    int distanceTipToCentroid = (int) Math.round((0.66666*length));
    int distanceToRotationalCenter = (int) Math.round((0.5*length));
    
    public ArrowHead(int x, int y) {
      //draw triangle with path starting at centroid, so we rotate correctly
      p = new Path(x - distanceToRotationalCenter, y);
      p.lineRelativelyTo(distanceToRotationalCenter, 0);
      p.lineRelativelyTo(-length, width/2);
      p.lineRelativelyTo(0, -width);
      p.lineRelativelyTo(length, width/2);
      p.setFillColor("black");
      
      add(p);
    }
    
    public void setRotation(int degree) {
      p.setRotation(degree);
    }
    
    public void setX(int x) {
      p.setX(x);
    }
    public void setY(int y) {
      p.setY(y);
    }
    
    public void redrawOnLine(int x1, int y1, int x2, int y2) {
      double opposite = y2 - y1 * 1.0;
      double adjacent = x2 - x1 * 1.0;
      double angle = Math.toDegrees(Math.atan(opposite/adjacent));
      int degrees = (int)Math.round(angle);

      Point newCenter = Point.travelLine(x2, y2, x1, y1, distanceToRotationalCenter);
      setX(newCenter.getX());
      setY(newCenter.getY());
      
//      mark(x2, y2, "green");
//      mark(newCenter, "red");
      
      setRotation(degrees);
    }
    
    private void mark(Point p, String color) {
      mark(p.getX(), p.getY(), color);
    }
    private void mark(int x, int y, String color) {
      Circle marker2 = new Circle(x, y, 2);
      marker2.setStrokeColor(color);
      marker2.setFillColor(color);
      add(marker2);
    }

  }

  public void nodeMoved(ActionNodeMovedEvent event) {
    //using a 1-level deep stack to process events sanely and dump ones we can't process
    curMoveEvent = event;
    
    if(processingEvent) { 
      return;
    }
    
    synchronized (this) {
      processingEvent = true;
      ActionNodeMovedEvent ev = curMoveEvent;
      curMoveEvent = null;
      ActionNode node = ev.getSource();
      Point center = node.getCenter();
      if (node == sourceNode) {
        path.setX(center.getX());
        path.setY(center.getY());
      } else if (node == sinkNode) {
        path.setStep(1, new LineTo(false, center.getX(), center.getY()));
      }
      processingEvent = false;
    }
  }

  public void nodeAnchored(NodeAnchoredEvent event) {
    redraw();
  }

}
