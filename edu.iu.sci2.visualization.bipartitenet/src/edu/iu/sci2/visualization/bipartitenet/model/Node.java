package edu.iu.sci2.visualization.bipartitenet.model;

import com.google.common.base.Objects;

public class Node {
	private final double radius;

	private final String label;

	public Node(String label, double radius) {
		super();
		if (label == null) {
			throw new NullPointerException("Label must not be null");
		}
		this.label = label;
		this.radius = radius;
	}

	public String getLabel() {
		return label;
	}

	public double getValue() {
		return radius;
	}

	@Override
	public String toString() {
		return Objects.toStringHelper(this)
				.add("label", label)
				.add("radius", radius)
				.toString();
	}
}
