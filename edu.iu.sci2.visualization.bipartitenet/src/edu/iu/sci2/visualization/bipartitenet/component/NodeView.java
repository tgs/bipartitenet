package edu.iu.sci2.visualization.bipartitenet.component;

import java.awt.Graphics2D;
import java.awt.font.TextLayout;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

import edu.iu.sci2.visualization.bipartitenet.LayoutUtils;
import edu.iu.sci2.visualization.bipartitenet.component.NodeView.LabelPainter;
import edu.iu.sci2.visualization.bipartitenet.model.Node;

public class NodeView implements Paintable {
	public enum LabelPainter {
		LEFT {

			@Override
			void paintLabel(NodeView nv, Graphics2D g) {
				// TODO Auto-generated method stub
				TextLayout tl = new TextLayout(nv.getNode().getLabel(),
						g.getFont(), g.getFontRenderContext());
				Rectangle2D textBounds = tl.getBounds();
				int x = (int) (nv.getNodeCenter().getX()
						- nv.getCenterToTextDistance() - textBounds.getWidth());

				g.drawString(
						nv.getNode().getLabel(),
						x,
						(int) nv.getNodeCenter().getY()
								+ LayoutUtils.getFontCenterHeight(g));
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
		};

		abstract void paintLabel(NodeView nv, Graphics2D g);

	}

	private final Node node;
	private final Point2D nodeCenter;
	private LabelPainter labelPainter;
	private static final int NODE_TEXT_PADDING = 8;
	private static final int NODE_MAX_RADIUS = 15;

	public NodeView(Node node, Point2D nodeCenter, LabelPainter painter) {
		super();
		this.node = node;
		this.nodeCenter = nodeCenter;
		this.labelPainter = painter;
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
		labelPainter.paintLabel(this, g);
	}

}
