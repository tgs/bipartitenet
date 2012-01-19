package edu.iu.sci2.visualization.bipartitenet;

import java.awt.Graphics2D;
import java.awt.font.LineMetrics;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;

public class LayoutUtils {

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
	
	public static float getFontCenterHeight(Graphics2D g) {
		 LineMetrics lm = g.getFont().getLineMetrics("Asdf", g.getFontRenderContext());
		 
		 return lm.getAscent() / 2;
	}

}
