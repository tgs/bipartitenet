package edu.iu.sci2.visualization.bipartitenet;

import java.util.Collection;
import java.util.Map;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Ordering;

import edu.iu.sci2.visualization.bipartitenet.model.Edge;
import edu.iu.sci2.visualization.bipartitenet.model.Node;

public class BipartiteGraphDataModel {
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

	public BipartiteGraphDataModel(Collection<Node> leftNodes, 
			Collection<Node> rightNodes, 
			Collection<Edge> edges) {
		super();
		this.leftNodes = ImmutableList.copyOf(orderingByRadius.sortedCopy(leftNodes));
		this.rightNodes = ImmutableList.copyOf(orderingByRadius.sortedCopy(rightNodes));
		this.edges = ImmutableList.copyOf(edges);
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
}
