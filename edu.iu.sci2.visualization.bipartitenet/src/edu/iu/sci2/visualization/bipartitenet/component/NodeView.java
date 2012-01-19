package edu.iu.sci2.visualization.bipartitenet.component;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.font.TextLayout;
import java.awt.geom.Rectangle2D;

import math.geom2d.Point2D;
import math.geom2d.conic.Circle2D;
import edu.iu.sci2.visualization.bipartitenet.LayoutUtils;
import edu.iu.sci2.visualization.bipartitenet.model.Node;

public class NodeView implements Paintable {
	public enum Section {
		LEFT {

			@Override
			void paintLabel(NodeView nv, Graphics2D g) {
				TextLayout tl = new TextLayout(nv.getNode().getLabel(),
						g.getFont(), g.getFontRenderContext());
				Rectangle2D textBounds = tl.getBounds();
				double x = nv.getNodeCenter().getX()
						- nv.getCenterToTextDistance() - textBounds.getWidth();
				
				tl.draw(g, (float) x, (float) (nv.getNodeCenter().getY()
								+ LayoutUtils.getFontCenterHeight(g)));
			}

			@Override
			Color getFillColor() {
				return Color.pink;
			}

		},
		RIGHT {
			@Override
			void paintLabel(NodeView nv, Graphics2D g) {
				g.drawString(
						nv.getNode().getLabel(),
						(int) nv.getNodeCenter().getX()
								+ nv.getCenterToTextDistance(),
						(int) nv.getNodeCenter().getY()
								+ LayoutUtils.getFontCenterHeight(g));
			}

			@Override
			Color getFillColor() {
				return Color.orange;
			}
		};

		abstract void paintLabel(NodeView nv, Graphics2D g);
		abstract Color getFillColor();

	}

	private final Node node;
	private final Point2D nodeCenter;
	private final Section leftRightDifference;
	private final CircleRadiusCoding coding;
	private static final int NODE_TEXT_PADDING = 8;
	private static final int NODE_MAX_RADIUS = 15;

	public NodeView(Node node, Point2D nodeCenter, Section painter, CircleRadiusCoding coding) {
		super();
		this.node = node;
		this.nodeCenter = nodeCenter;
		this.leftRightDifference = painter;
		this.coding = coding;
	}

	public int getCenterToTextDistance() {
		return getMaxRadius() + NODE_TEXT_PADDING;
	}

	public int getMaxRadius() {
		return NODE_MAX_RADIUS;
	}

	private Node getNode() {
		return node;
	}

	public Point2D getNodeCenter() {
		return nodeCenter;
	}
	
	public double getRadius() {
		return coding.apply(node.getValue());
	}

	@Override
	public void paint(Graphics2D g) {
		Circle2D circle = new Circle2D(nodeCenter.getX(), nodeCenter.getY(), getRadius());
		leftRightDifference.paintLabel(this, g);
		g.setColor(leftRightDifference.getFillColor());
		circle.fill(g);
		g.setColor(Color.black);
		circle.draw(g);
	}

}
