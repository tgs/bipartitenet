package edu.iu.sci2.visualization.bipartitenet.model;

import java.util.Collection;
import java.util.List;

import com.google.common.base.Objects;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.common.collect.Ordering;


public class BipartiteGraphDataModel {
	private final String nodeValueAttribute;
	private final String edgeValueAttribute;
	
	private final ImmutableList<Node> leftNodes;
	private final ImmutableList<Node> rightNodes;
	
	private final ImmutableList<Edge> edges;

	private static final Ordering<Node> orderingByRadius = new Ordering<Node>() {
		@Override
		public int compare(Node a, Node b) {
			// decreasing
			return - Double.compare(a.getValue(), b.getValue());
		}
	};


	public BipartiteGraphDataModel(Collection<Node> allNodes,
			Collection<Edge> edges, String nodeValueAttribute, String edgeValueAttribute) {
		this.edgeValueAttribute = edgeValueAttribute;
		List<Node> leftNodes = Lists.newArrayList(),
				rightNodes = Lists.newArrayList();
		for (Node n : allNodes) {
			if (n.getDestination() == NodeDestination.LEFT) {
				leftNodes.add(n);
			} else {
				rightNodes.add(n);
			}
		}
		
		this.leftNodes = ImmutableList.copyOf(orderingByRadius.sortedCopy(leftNodes));
		this.rightNodes = ImmutableList.copyOf(orderingByRadius.sortedCopy(rightNodes));
		this.edges = ImmutableList.copyOf(edges);
		this.nodeValueAttribute = nodeValueAttribute;
	}

	public ImmutableList<Edge> getEdges() {
		return edges;
	}

	public ImmutableList<Node> getLeftNodes() {
		return leftNodes;
	}

	public ImmutableList<Node> getRightNodes() {
		return rightNodes;
	}
	
	@Override
	public String toString() {
		return Objects.toStringHelper(this)
				.add("left", leftNodes)
				.add("right", rightNodes)
				.add("edges", edges)
				.toString();
	}

	public String getNodeValueAttribute() {
		return nodeValueAttribute;
	}

	public String getEdgeValueAttribute() {
		return edgeValueAttribute;
	}
	
	public boolean hasWeightedNodes() {
		return nodeValueAttribute != null;
	}
	
	public boolean hasWeightedEdges() {
		return edgeValueAttribute != null;
	}

}
