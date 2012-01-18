package edu.iu.sci2.visualization.bipartitenet.model;

public class Edge {
	private final Node leftNode;
	private final Node rightNode;
	public Edge(Node leftNode, Node rightNode) {
		super();
		this.leftNode = leftNode;
		this.rightNode = rightNode;
	}
	public Node getLeftNode() {
		return leftNode;
	}
	public Node getRightNode() {
		return rightNode;
	}
	
}
