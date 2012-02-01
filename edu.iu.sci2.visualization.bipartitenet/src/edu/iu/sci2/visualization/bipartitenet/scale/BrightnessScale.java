package edu.iu.sci2.visualization.bipartitenet.scale;

import java.awt.Color;

public class BrightnessScale extends AbstractZeroAnchoredScale<Color> {
	private static final float DEFAULT_HUE = 0; 
	private static final float DEFAULT_SATURATION = 0;
	private final float hue;
	private final float saturation; 

	private BrightnessScale(float hue, float saturation) {
		super(0.1, 1);
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
		return Color.getHSBColor(hue, saturation, 1 - (float) doApply(value));
	}
}
