package edu.iu.sci2.visualization.bipartitenet.scale;

import java.awt.Color;


public class ConstantColor implements Scale<Double, Color> {
	@Override
	public Color apply(Double arg0) {
		return Color.gray;
	}

	@Override
	public void train(Iterable<Double> trainingData) {
		// do nothing
	}

	@Override
	public void doneTraining() {
		// do nothing
	}

}
