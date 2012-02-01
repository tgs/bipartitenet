package edu.iu.sci2.visualization.bipartitenet.model;

import com.google.common.base.Function;
import com.google.common.base.Objects;

public class Edge {
	public static final Function<Edge,Double> VALUE_GETTER = new Function<Edge,Double>(){
		@Override
		public Double apply(Edge it) {
			return it.getDataValue();
		}
	};
	private final Node leftNode;
	private final Node rightNode;
	private final double dataValue;
	public Edge(Node leftNode, Node rightNode, double dataValue) {
		super();
		this.leftNode = leftNode;
		this.rightNode = rightNode;
		this.dataValue = dataValue;
	}
	public double getDataValue() {
		return dataValue;
	}
	public Node getLeftNode() {
		return leftNode;
	}
	public Node getRightNode() {
		return rightNode;
	}
	
	@Override
	public String toString() {
		return Objects.toStringHelper(this)
				.addValue(leftNode.getLabel())
				.addValue(rightNode.getLabel())
				.add("value", dataValue)
				.toString();
	}
	
}
