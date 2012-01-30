package edu.iu.sci2.visualization.bipartitenet.component;

import java.awt.Color;
import java.awt.color.ColorSpace;

import com.google.common.base.Function;

public class LineWeightCoding implements Function<Double, Color> {
	private static final float[] DEFAULT_COLOR_HSB = new float[] { 0, 1, 0 }; 
	private static final double MAX_SATURATION = 255;
	private static final double MIN_SATURATION = 40;
	private final double slope;
	private final double intercept;
	
	private LineWeightCoding(double slope, double intercept) {
		this.slope = slope;
		this.intercept = intercept;
	}

	public static LineWeightCoding createZeroAnchoredScaledCoding(double max) {
		return createAutoScaledCoding(0, max);
	}
	
	public static LineWeightCoding createAutoScaledCoding(double min, double max) {
		if (Math.abs(min - max) < 0.00000001) {
			// if min == max, just make lines some color and don't worry...
			return createWithSlopeAndIntercept(0, MAX_SATURATION / 2);
		}
		double slope = (MAX_SATURATION - MIN_SATURATION) / (max - min);
		double intercept = (- min) * slope + MIN_SATURATION;
		
		return createWithSlopeAndIntercept(slope, intercept);
	}


	private static LineWeightCoding createWithSlopeAndIntercept(double slope,
			double intercept) {
		return new LineWeightCoding(slope, intercept);
	}

	@Override
	public Color apply(Double arg0) {
		float h = DEFAULT_COLOR_HSB[0],
				s = DEFAULT_COLOR_HSB[1] * (float) (slope * arg0 + intercept),
				b = DEFAULT_COLOR_HSB[2];
		return Color.getHSBColor(h, s, b);
	}
}
