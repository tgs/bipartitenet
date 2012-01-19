package edu.iu.sci2.visualization.bipartitenet;

import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.Line2D;

import math.geom2d.AffineTransform2D;
import math.geom2d.Point2D;
import math.geom2d.line.AbstractLine2D;
import math.geom2d.line.LineSegment2D;
import math.geom2d.polygon.Polygon2DUtils;
import math.geom2d.polygon.SimplePolygon2D;
import edu.iu.sci2.visualization.bipartitenet.component.NodeView;
import edu.iu.sci2.visualization.bipartitenet.component.Paintable;

public class EdgeView implements Paintable {
	
	private static final double NODE_EDGE_SPACE = 4;
	private static final double ARROW_SIDE_LENGTH = 6;
	private final NodeView dest;
	private final NodeView src;

	public EdgeView(NodeView src, NodeView dest) {
		this.src = src;
		this.dest = dest;
	}

	@Override
	public void paint(Graphics2D g) {
		LineSegment2D grossLine = new LineSegment2D(src.getNodeCenter(), dest.getNodeCenter());
		double tStart = (getRadius(src) + NODE_EDGE_SPACE) / grossLine.getLength(),
				tEnd = (getRadius(dest) + NODE_EDGE_SPACE) / grossLine.getLength();
		AbstractLine2D fineLine = grossLine.getSubCurve(tStart, 1 - tEnd);
		
		fineLine.draw(g);

		drawArrowHead(fineLine, g);
		
	}

	private void drawArrowHead(AbstractLine2D line, Graphics2D g) {
		Point2D end = line.getLastPoint();
		Point2D colinearPoint = line.getPoint(1 - (ARROW_SIDE_LENGTH / line.getLength()));
		Point2D cwPoint = colinearPoint.transform(AffineTransform2D.createRotation(end, Math.PI / 6));
		Point2D ccwPoint = colinearPoint.transform(AffineTransform2D.createRotation(end, -Math.PI / 6));
		SimplePolygon2D arrowHead = new SimplePolygon2D(end, cwPoint, ccwPoint);
		arrowHead.draw(g);
		arrowHead.fill(g);
	}

	private double getRadius(NodeView n) {
		return n.getNode().getValue();
	}

}
