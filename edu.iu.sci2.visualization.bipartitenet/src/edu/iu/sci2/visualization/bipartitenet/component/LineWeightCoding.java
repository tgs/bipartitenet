package edu.iu.sci2.visualization.bipartitenet.component;

import java.awt.Color;
import java.util.Collection;

import com.google.common.base.Function;

import edu.iu.sci2.visualization.bipartitenet.model.Edge;

public class LineWeightCoding implements Function<Double, Color> {
	private static final float[] DEFAULT_COLOR_HSB = new float[] { 0, 0, 1 }; 
	private static final double MAX_BRIGHTNESS = 1;
	private static final double MIN_BRIGHTNESS = 0.1;
	private final double slope;
	private final double intercept;
	
	private LineWeightCoding(double slope, double intercept) {
		this.slope = slope;
		this.intercept = intercept;
	}

	public static LineWeightCoding createFromEdges(Collection<Edge> edges) {
		if (edges.isEmpty()) {
			// XXX: is this the right thing to do?
			throw new IllegalArgumentException("Must have at least one edge...");
		}

		// XXX: what about negative values?  error, warning, or just be wrong?
		double max = Double.NEGATIVE_INFINITY;
		for (Edge e : edges) {
			System.out.println(e);
			double v = e.getDataValue();
			max = Math.max(max, v);
		}
		
		return createZeroAnchoredScaledCoding(max);
	}
	public static LineWeightCoding createZeroAnchoredScaledCoding(double max) {
		return createAutoScaledCoding(0, max);
	}
	
	public static LineWeightCoding createAutoScaledCoding(double min, double max) {
		if (Math.abs(min - max) < 0.00000001) {
			// if min == max, just make lines some color and don't worry...
			return createWithSlopeAndIntercept(0, MAX_BRIGHTNESS / 2);
		}
		double slope = (MAX_BRIGHTNESS - MIN_BRIGHTNESS) / (max - min);
		double intercept = (- min) * slope + MIN_BRIGHTNESS;
		
		return createWithSlopeAndIntercept(slope, intercept);
	}


	private static LineWeightCoding createWithSlopeAndIntercept(double slope,
			double intercept) {
		return new LineWeightCoding(slope, intercept);
	}

	@Override
	public Color apply(Double arg0) {
		float h = DEFAULT_COLOR_HSB[0],
				s = DEFAULT_COLOR_HSB[1],
				b = DEFAULT_COLOR_HSB[2] * (1 - (float) (slope * arg0 + intercept));
		return Color.getHSBColor(h, s, b);
	}
}
