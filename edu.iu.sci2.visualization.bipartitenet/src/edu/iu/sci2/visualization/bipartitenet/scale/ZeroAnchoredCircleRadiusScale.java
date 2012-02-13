package edu.iu.sci2.visualization.bipartitenet.scale;

import com.google.common.collect.ImmutableList;

public class ZeroAnchoredCircleRadiusScale implements Scale<Double,Double> {
	private final BasicZeroAnchoredScale areaScale;
	public ZeroAnchoredCircleRadiusScale(double maxRadius) {
		// TODO explain or make constant "1"
		areaScale = new BasicZeroAnchoredScale(1, Math.PI * maxRadius * maxRadius);
	}

	@Override
	public void train(Iterable<Double> trainingData) {
		areaScale.train(trainingData);
	}

	public void doneTraining() {
		areaScale.doneTraining();
	}

	public Double apply(Double value) {
		// TODO negatives?
		return Math.sqrt(areaScale.apply(value) / Math.PI);
	}

	public ImmutableList<Double> getExtrema() {
		return areaScale.getExtrema();
	}
}
