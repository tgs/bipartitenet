package edu.iu.sci2.visualization.bipartitenet.component;

import com.google.common.base.Function;

/**
 * Encodes a value into a circle radius, such that the circle's area will
 * be proportional to the given value.
 * @author thgsmith
 *
 */

public class CircleRadiusCoding implements Function<Double, Double> {
	private final double slope;
	private final double intercept;
	
	private CircleRadiusCoding(double slope, double intercept) {
		this.slope = slope;
		this.intercept = intercept;
	}

	public static CircleRadiusCoding createAreaIdentityCoding() {
		return createWithSlopeAndIntercept(1, 0);
	}
	
	public static CircleRadiusCoding createZeroAnchoredScaledCoding(double max, double maxRadius) {
		return createAutoScaledCoding(0, max, maxRadius);
	}
	
	public static CircleRadiusCoding createAutoScaledCoding(double min, double max, double maxRadius) {
		if (Math.abs(min - max) < 0.00000001) {
			// if min == max, just make the dots some size and don't worry.
			return createWithSlopeAndIntercept(0, maxRadius / 2);
		}
		double maxArea = Math.PI * maxRadius * maxRadius;
		double minArea = 1;
		double slope = (maxArea - minArea) / (max - min);
		double intercept = (- min) * slope + minArea;
		
		return new CircleRadiusCoding(slope, intercept);
	}

	public static CircleRadiusCoding createWithSlopeAndIntercept(double slope,
			double intercept) {
		return new CircleRadiusCoding(slope, intercept);
	}

	@Override
	public Double apply(Double value) {
		return Math.sqrt((value * slope + intercept) / Math.PI);
	}

}
