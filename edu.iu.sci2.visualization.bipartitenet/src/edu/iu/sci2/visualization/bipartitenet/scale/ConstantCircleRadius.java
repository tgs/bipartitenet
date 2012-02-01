package edu.iu.sci2.visualization.bipartitenet.scale;


public class ConstantCircleRadius implements Scale<Double, Double> {
	private Double radius;

	public ConstantCircleRadius(double radius) {
		this.radius = radius;
	}

	@Override
	public Double apply(Double arg0) {
		return radius;
	}

	@Override
	public void train(Iterable<Double> trainingData) {
		// Do nothing
	}

	@Override
	public void doneTraining() {
		// Do nothing
	}
}
