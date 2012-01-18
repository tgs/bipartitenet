package edu.iu.sci2.visualization.bipartitenet;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.font.LineMetrics;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;

import com.google.common.collect.ImmutableList;

import edu.iu.sci2.visualization.bipartitenet.component.Paintable;
import edu.iu.sci2.visualization.bipartitenet.model.Node;

public class BipartiteGraphRenderer implements Paintable {
	private BipartiteGraphDataModel data;
//	private Map<Node, Point2D> nodePosition;

	private final Line2D leftLine = new Line2D.Double();

	private final Line2D rightLine = new Line2D.Double();

	private static final int NODE_TEXT_PADDING = 8;
	private static final int NODE_MAX_RADIUS = 15;
	
	
	/**
	 * Treats {@code vector} as a directed line segment, and computes the point
	 * that is, for example, halfway along the line.
	 * 
	 * @param vector
	 * @param proportion
	 *            a number between 0 and 1, the fraction of the distance along
	 *            the vector to travel.
	 * @return
	 */
	public static Point2D getPointAlongLine(Line2D vector, double proportion) {
		if (proportion < 0 || proportion > 1) {
			throw new IllegalArgumentException(
					"Proportion must be between 0 and 1");
		}
	
		return new Point2D.Double(vector.getX1() + proportion * (vector.getX2() - vector.getX1()), 
				vector.getY1() + proportion * (vector.getY2() - vector.getY1()));
	}

	public BipartiteGraphRenderer(BipartiteGraphDataModel skel, Line2D leftLine, Line2D rightLine) {
		this.data = skel;
		this.leftLine.setLine(leftLine);
		this.rightLine.setLine(rightLine);
	}
	public int getCenterToTextDistance() {
		return getMaxRadius() + NODE_TEXT_PADDING;
	}
	
	public float getFontCenterHeight(Graphics2D g) {
		 LineMetrics lm = g.getFont().getLineMetrics("Asdf", g.getFontRenderContext());
		 
		 return lm.getAscent() / 2;
	}

	public Line2D getLeftLine() {
		return (Line2D) leftLine.clone();
	}

	public int getMaxRadius() {
		return NODE_MAX_RADIUS;
	}
	
	public Line2D getRightLine() {
		return (Line2D) rightLine.clone();
	}

	public void paint(Graphics2D g) {
		g.setColor(Color.black);
		System.out.println(g.getClipBounds());
		
		renderNodesAndLabels(g, data.getLeftNodes(), getLeftLine());
		renderNodesAndLabels(g, data.getRightNodes(), getRightLine());

	}
	
	
	private void renderNodesAndLabels(Graphics2D g, ImmutableList<Node> nodes, Line2D line) {
		for (int i = 0; i < nodes.size(); i++) {
			Node n = nodes.get(i);
			Point2D nodeCenter = getPointAlongLine(line, ((double) i) / nodes.size());
			g.drawOval((int) nodeCenter.getX(), (int) nodeCenter.getY(), (int) n.getValue(), (int) n.getValue());
			System.out.println(getFontCenterHeight(g));
			g.drawString(n.getLabel(), (int) nodeCenter.getX() + getCenterToTextDistance(),
					(int) nodeCenter.getY() + getFontCenterHeight(g));
		}
//		g.drawString()
	}
	
}
