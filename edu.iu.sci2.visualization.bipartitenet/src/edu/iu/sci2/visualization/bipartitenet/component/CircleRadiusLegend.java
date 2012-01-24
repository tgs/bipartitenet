package edu.iu.sci2.visualization.bipartitenet.component;

import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.font.GlyphVector;
import java.awt.geom.Rectangle2D;
import java.util.Map;

import math.geom2d.Point2D;
import math.geom2d.conic.Circle2D;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Ordering;

public class CircleRadiusLegend implements Paintable {
	private final CircleRadiusCoding coding;
	private final ImmutableMap<Double, String> labeledValues;
	private final Point2D topCenter;
	private final String title;
	private final double maxRadius;
	
	private static final Font TITLE_FONT = new Font("Dialog", Font.BOLD, 14);
	private static final Font LEGEND_FONT = new Font("Dialog", Font.PLAIN, 8);
	
	private static final int LABEL_X_OFFSET = 5; // from outer edge of circles to the labels
	private static final int LEGEND_Y_OFFSET = 20; // from top of label to top of circles
	
	
	public CircleRadiusLegend(Point2D topCenter, String title,
			CircleRadiusCoding coding,
			ImmutableMap<Double, String> labeledValues, double maxRadius) {
		this.topCenter = topCenter;
		this.title = title;
		this.coding = coding;
		this.labeledValues = labeledValues;
		this.maxRadius = maxRadius;
	}


	@Override
	public void paint(Graphics2D g) {
		paintLabel(g);
		paintLegend(g);
	}

	private void paintLabel(Graphics2D g) {
		GlyphVector titleGV = TITLE_FONT.createGlyphVector(g.getFontRenderContext(), title);
		Rectangle2D bounds = titleGV.getVisualBounds();
		float x = (float) (topCenter.getX() - bounds.getCenterX());
		float y = (float) (topCenter.getY() - bounds.getY());
		g.drawGlyphVector(titleGV, x, y);
	}

	private void paintLegend(Graphics2D g) {
		Point2D legendTopCenter = topCenter.translate(0, LEGEND_Y_OFFSET);
		for (Map.Entry<Double, String> labeledValue : labeledValues.entrySet()) {
			double radius = coding.apply(labeledValue.getKey());
			// circle center
			double circleX = legendTopCenter.getX() - maxRadius,
					circleY = legendTopCenter.getY() + 2 * maxRadius - radius;
			new Circle2D(circleX, circleY, radius).draw(g);
			
			// label position
			double labelX = legendTopCenter.getX() + LABEL_X_OFFSET,
					labelY = circleY - radius;
			g.drawString(labeledValue.getValue(), (float) labelX, (float) labelY);
			
		}
	}



}
