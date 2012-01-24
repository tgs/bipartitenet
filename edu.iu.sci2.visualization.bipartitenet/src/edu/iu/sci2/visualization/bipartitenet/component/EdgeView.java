package edu.iu.sci2.visualization.bipartitenet.component;

import java.awt.Color;
import java.awt.Graphics2D;

import math.geom2d.AffineTransform2D;
import math.geom2d.Point2D;
import math.geom2d.line.AbstractLine2D;
import math.geom2d.line.LineSegment2D;
import math.geom2d.polygon.SimplePolygon2D;

public class EdgeView implements Paintable {
	
	private static final double NODE_EDGE_SPACE = 4;
	private static final double ARROW_HEAD_SIDE_LENGTH = 6;
	private final NodeView dest;
	private final NodeView src;

	public EdgeView(NodeView src, NodeView dest) {
		this.src = src;
		this.dest = dest;
	}

	@Override
	public void paint(Graphics2D gIn) {
		Graphics2D g = (Graphics2D) gIn.create();
		g.setColor(Color.gray);
		LineSegment2D grossLine = new LineSegment2D(src.getNodeCenter(), dest.getNodeCenter());
		double tStart = (src.getRadius() + NODE_EDGE_SPACE) / grossLine.getLength(),
				tEnd = (dest.getRadius() + NODE_EDGE_SPACE) / grossLine.getLength();
		AbstractLine2D fineLine = grossLine.getSubCurve(tStart, 1 - tEnd);
		
		fineLine.draw(g);

		drawArrowHead(fineLine, g);
		
	}

	private void drawArrowHead(AbstractLine2D line, Graphics2D g) {
		Point2D end = line.getLastPoint();
		Point2D colinearPoint = line.getPoint(1 - (ARROW_HEAD_SIDE_LENGTH / line.getLength()));
		Point2D cwPoint = colinearPoint.transform(AffineTransform2D.createRotation(end, Math.PI / 6));
		Point2D ccwPoint = colinearPoint.transform(AffineTransform2D.createRotation(end, -Math.PI / 6));
		SimplePolygon2D arrowHead = new SimplePolygon2D(end, cwPoint, ccwPoint);
		arrowHead.draw(g);
		arrowHead.fill(g);
	}

}
