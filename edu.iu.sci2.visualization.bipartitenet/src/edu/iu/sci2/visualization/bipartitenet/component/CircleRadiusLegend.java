package edu.iu.sci2.visualization.bipartitenet.component;

import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.font.GlyphVector;
import java.awt.geom.Rectangle2D;
import java.util.Map;

import math.geom2d.Point2D;
import math.geom2d.conic.Circle2D;
import math.geom2d.line.LineSegment2D;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSortedMap;

import edu.iu.sci2.visualization.bipartitenet.PageDirector;

public class CircleRadiusLegend implements Paintable {
	private final CircleRadiusCoding coding;
	private final ImmutableMap<Double, String> labeledValues;
	private final Point2D topCenter;
	private final String title;
	private final double maxRadius;
	
	private static final Font TITLE_FONT = PageDirector.BASIC_FONT.deriveFont(Font.BOLD, 14);
	private static final Font LEGEND_FONT = PageDirector.BASIC_FONT.deriveFont(Font.PLAIN, 10);
	
	private static final int LABEL_X_OFFSET = 5; // from outer edge of circles to the labels
	private static final int LEGEND_Y_OFFSET = 25; // from top of label to top of circles
	
	
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
		paintCircles(g);
		paintDataLabels(g);
	}

	private void paintLabel(Graphics2D g) {
		GlyphVector titleGV = TITLE_FONT.createGlyphVector(g.getFontRenderContext(), title);
		Rectangle2D bounds = titleGV.getVisualBounds();
		float x = (float) (topCenter.getX() - bounds.getCenterX());
		float y = (float) (topCenter.getY() - bounds.getY());
		g.drawGlyphVector(titleGV, x, y);
	}

	private void paintCircles(Graphics2D g) {
		Point2D legendTopCenter = topCenter.translate(0, LEGEND_Y_OFFSET);
		for (Map.Entry<Double, String> labeledValue : labeledValues.entrySet()) {
			double radius = coding.apply(labeledValue.getKey());
			// circle center
			double circleX = legendTopCenter.getX() - maxRadius,
					circleY = legendTopCenter.getY() + 2 * maxRadius - radius;
			new Circle2D(circleX, circleY, radius).draw(g);
		}
	}
	
	private void paintDataLabels(Graphics2D g) {
		Point2D labelsTop = topCenter.translate(LABEL_X_OFFSET, LEGEND_Y_OFFSET + 8);
		LineSegment2D labelLine = new LineSegment2D(labelsTop, labelsTop.translate(0, 2 * maxRadius)); // the line "points" downward
		
		ImmutableSortedMap<Double, String> m = ImmutableSortedMap.copyOf(labeledValues);
		ImmutableList<String> labels = ImmutableList.copyOf(m.values()).reverse(); // in descending order
		
		
		int numLabels = labels.size();
		double denominator = Math.max(1, numLabels);
	
		g.setFont(LEGEND_FONT);
		for (int i = 0; i < numLabels; i++) {
			Point2D labelPoint = labelLine.getPoint(i / denominator);
			g.drawString(labels.get(i), (float) labelPoint.getX(), (float) labelPoint.getY());
		}
			// label position
//			double labelX = legendTopCenter.getX() + LABEL_X_OFFSET,
//					labelY = circleY - radius;
//			g.drawString(labeledValue.getValue(), (float) labelX, (float) labelY);
//		}
		
//		int numNodes = nodes.size();
//		double denominator = Math.max(1, numNodes - 1); // don't divide by 0!
//		
//		for (int i = 0; i < numNodes; i++) {
//			Point2D centerPoint = centerLine.getPoint(i / denominator);
//			NodeView view = new NodeView(nodes.get(i), centerPoint, painter, nodeRadiusCoding);
//			nodeViews.put(nodes.get(i), view);
//		}
		
	}



}
