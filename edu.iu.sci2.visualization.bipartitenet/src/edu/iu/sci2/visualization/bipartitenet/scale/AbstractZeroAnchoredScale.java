package edu.iu.sci2.visualization.bipartitenet.scale;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;


public abstract class AbstractZeroAnchoredScale<T> implements Scale<Double, T> {
	private boolean doneTraining = false;
	private final Range<Double> dataRange = Range.create();

	protected final double maxArea;
	protected double slope;
	protected double intercept;
	private final double minArea;
	public AbstractZeroAnchoredScale(double minResult, double maxResult) {
		minArea = minResult;
		maxArea = maxResult;
	}
	protected double doApply(double value) {
		return value * slope + intercept;
	}

	@Override
	public void doneTraining() {
		this.doneTraining = true;
	}

	@Override
	public ImmutableList<Double> getExtrema() {
		return ImmutableList.of(dataRange.getMin(), dataRange.getMax());
	}

	@Override
	public void train(Iterable<Double> trainingData) {
		Preconditions.checkState(! doneTraining, "Tried to add more training data after done training!");
		dataRange.considerAll(trainingData);
		this.slope = (maxArea - minArea) / dataRange.getMax();
		this.intercept = minArea;
	}
}