package edu.iu.sci2.visualization.bipartitenet.scale;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;


public class BasicZeroAnchoredScale implements Scale<Double, Double> {
	private final double minResult;
	private final double maxResult;
	
	private double slope;
	private double intercept;
	private boolean doneTraining = false;
	private final Range<Double> dataRange = Range.create();
	
	public BasicZeroAnchoredScale(double minResult, double maxResult) {
		this.minResult = minResult;
		this.maxResult = maxResult;
	}
	
	@Override
	public void train(Iterable<Double> trainingData) {
		Preconditions.checkState(! doneTraining, "Tried to add more training data after done training!");
		dataRange.considerAll(trainingData);
		this.slope = (maxResult - minResult) / dataRange.getMax();
		this.intercept = minResult;
	}
	
	@Override
	public void doneTraining() {
		this.doneTraining = true;
	}

	@Override
	public Double apply(Double value) {
		return value * slope + intercept;
	}

	@Override
	public ImmutableList<Double> getExtrema() {
		return ImmutableList.of(dataRange.getMin(), dataRange.getMax());
	}
}