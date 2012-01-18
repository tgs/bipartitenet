package edu.iu.sci2.visualization.bipartitenet.component;

import java.awt.Graphics2D;
import java.awt.geom.Point2D;

import edu.iu.sci2.visualization.bipartitenet.model.Node;

public abstract class NodeView implements Paintable {
	private Node node;
	private Point2D nodeCenter;
	private static final int NODE_TEXT_PADDING = 8;
	private static final int NODE_MAX_RADIUS = 15;

	protected NodeView(Node node, Point2D nodeCenter) {
		super();
		this.node = node;
		this.nodeCenter = nodeCenter;
	}
	
	public static NodeView create(Node node, Point2D nodeCenter) {
		return new RightNodeView(node, nodeCenter);
	}
	

	public int getCenterToTextDistance() {
		return getMaxRadius() + NODE_TEXT_PADDING;
	}

	public int getMaxRadius() {
		return NODE_MAX_RADIUS;
	}

	public Node getNode() {
		return node;
	}

	public Point2D getNodeCenter() {
		return (Point2D) nodeCenter.clone();
	}

	@Override
	public void paint(Graphics2D g) {
		g.drawOval((int) nodeCenter.getX(), (int) nodeCenter.getY(),
				(int) node.getValue(), (int) node.getValue());
		paintLabel(g);
	}

	public abstract void paintLabel(Graphics2D g);
}
