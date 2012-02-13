package edu.iu.sci2.visualization.bipartitenet.scale;

import java.awt.Color;

import com.google.common.collect.ImmutableList;

public class ColorIntensityScale implements Scale<Double,Color> {
	private static final float DEFAULT_HUE = 0; 
	private static final float DEFAULT_SATURATION = 0;

	private final BasicZeroAnchoredScale brightnessScale; 
	private final float hue;
	private final float saturation; 

	private ColorIntensityScale(float hue, float saturation) {
		// TODO Explain .1 or .9 or whatever
		this.brightnessScale = new BasicZeroAnchoredScale(0.1, 1);
		this.hue = hue;
		this.saturation = saturation;
	}
	
	public static ColorIntensityScale createWithHS(float hue, float saturation) {
		return new ColorIntensityScale(hue, saturation);
	}
	
	public static ColorIntensityScale createWithDefaultColor() {
		return new ColorIntensityScale(DEFAULT_HUE, DEFAULT_SATURATION);
	}
	
	@Override
	public Color apply(Double value) {
		// TODO Explain 1-
		return Color.getHSBColor(hue, saturation, 1 - brightnessScale.apply(value).floatValue());
	}
	
	public void train(Iterable<Double> trainingData) {
		brightnessScale.train(trainingData);
	}
	
	public void doneTraining() {
		brightnessScale.doneTraining();
	}
	
	public ImmutableList<Double> getExtrema() {
		return brightnessScale.getExtrema();
	}
}
