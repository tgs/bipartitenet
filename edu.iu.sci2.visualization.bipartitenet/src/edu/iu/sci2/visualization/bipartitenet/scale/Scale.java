package edu.iu.sci2.visualization.bipartitenet.scale;
import com.google.common.base.Function;
import com.google.common.collect.ImmutableList;


public interface Scale<I,O> extends Function<I,O> {
	ImmutableList<I> getExtrema();
	void train(Iterable<I> trainingData);
	void doneTraining();
}
