package edu.iu.sci2.visualization.bipartitenet.scale;

import java.awt.Color;

import com.google.common.collect.ImmutableList;

public class BrightnessScale implements Scale<Double,Color> {
	private static final float DEFAULT_HUE = 0; 
	private static final float DEFAULT_SATURATION = 0;

	private final BasicZeroAnchoredScale valueScale; 
	private final float hue;
	private final float saturation; 

	private BrightnessScale(float hue, float saturation) {
		this.valueScale = new BasicZeroAnchoredScale(0.1, 1);
		this.hue = hue;
		this.saturation = saturation;
	}
	
	public static BrightnessScale createWithHS(float hue, float saturation) {
		return new BrightnessScale(hue, saturation);
	}
	
	public static BrightnessScale createWithDefaultColor() {
		return new BrightnessScale(DEFAULT_HUE, DEFAULT_SATURATION);
	}
	
	@Override
	public Color apply(Double value) {
		return Color.getHSBColor(hue, saturation, 1 - valueScale.apply(value).floatValue());
	}
	
	public void train(Iterable<Double> trainingData) {
		valueScale.train(trainingData);
	}
	
	public void doneTraining() {
		valueScale.doneTraining();
	}
	
	public ImmutableList<Double> getExtrema() {
		return valueScale.getExtrema();
	}
}
