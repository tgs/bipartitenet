package edu.iu.sci2.visualization.bipartitenet;

public class CircleAreaCoding {
	private final double factor;
	
	private CircleAreaCoding(double factor) {
		this.factor = factor;
	}
	
	public double getAreaForValue(double value) {
		return factor * value;
	}
	
	public double getRadiusForValue(double value) {
		return Math.sqrt(getAreaForValue(value) / Math.PI);
	}
}
