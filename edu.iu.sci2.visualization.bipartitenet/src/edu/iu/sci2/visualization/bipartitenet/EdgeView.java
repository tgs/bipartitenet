package edu.iu.sci2.visualization.bipartitenet;

import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.Line2D;

import math.geom2d.line.LineSegment2D;
import edu.iu.sci2.visualization.bipartitenet.component.NodeView;
import edu.iu.sci2.visualization.bipartitenet.component.Paintable;

public class EdgeView implements Paintable {
	
	private static final double NODE_EDGE_SPACE = 4;
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
		grossLine.getSubCurve(tStart, 1 - tEnd).draw(g);

	}

	private double getRadius(NodeView n) {
		return n.getNode().getValue();
	}

}
