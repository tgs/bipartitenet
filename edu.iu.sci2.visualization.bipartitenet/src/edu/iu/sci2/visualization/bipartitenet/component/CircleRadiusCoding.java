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

	public static CircleRadiusCoding createWithSlopeAndIntercept(double slope,
			double intercept) {
		return new CircleRadiusCoding(slope, intercept);
	}

	@Override
	public Double apply(Double value) {
		return Math.sqrt((value * slope + intercept) / Math.PI);
	}

}
