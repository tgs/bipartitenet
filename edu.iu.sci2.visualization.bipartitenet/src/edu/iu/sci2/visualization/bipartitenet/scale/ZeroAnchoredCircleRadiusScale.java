package edu.iu.sci2.visualization.bipartitenet.scale;

public class ZeroAnchoredCircleRadiusScale extends AbstractZeroAnchoredScale<Double> {
	public ZeroAnchoredCircleRadiusScale(double maxRadius) {
		super(1, Math.PI * maxRadius * maxRadius);
	}

	@Override
	public Double apply(Double value) {
		return Math.sqrt(doApply(value) / Math.PI);
	}
}
