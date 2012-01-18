package edu.iu.sci2.visualization.bipartitenet;

import java.awt.Graphics2D;
import java.awt.font.LineMetrics;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;

public class BipartiteGraphLayoutModel {
	private final Line2D leftLine = new Line2D.Double();
	private final Line2D rightLine = new Line2D.Double();

	private static final int NODE_TEXT_PADDING = 8;
	private static final int NODE_MAX_RADIUS = 15;
	
	public BipartiteGraphLayoutModel(Line2D leftLine, Line2D rightLine) {
		leftLine.setLine(leftLine);
		rightLine.setLine(rightLine);
	}

	public Line2D getLeftLine() {
		return (Line2D) leftLine.clone();
	}

	public Line2D getRightLine() {
		return (Line2D) rightLine.clone();
	}

	public int getMaxRadius() {
		return NODE_MAX_RADIUS;
	}
	
	public int getCenterToTextDistance() {
		return getMaxRadius() + NODE_TEXT_PADDING;
	}

	public float getFontCenterHeight(Graphics2D g) {
		 LineMetrics lm = g.getFont().getLineMetrics("Asdf", g.getFontRenderContext());
		 
		 return lm.getAscent() / 2;
	}
	
	
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
	
	
}
