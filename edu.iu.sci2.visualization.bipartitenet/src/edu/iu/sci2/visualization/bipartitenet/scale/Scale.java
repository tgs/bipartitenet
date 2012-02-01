package edu.iu.sci2.visualization.bipartitenet.scale;
import com.google.common.base.Function;


public interface Scale<I,O> extends Function<I,O> {
	void train(Iterable<I> trainingData);
	void doneTraining();
}
