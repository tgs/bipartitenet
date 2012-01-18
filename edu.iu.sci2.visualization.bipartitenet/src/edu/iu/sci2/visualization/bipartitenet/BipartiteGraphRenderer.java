package edu.iu.sci2.visualization.bipartitenet;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.util.LinkedHashMap;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;

import edu.iu.sci2.visualization.bipartitenet.component.NodeView;
import edu.iu.sci2.visualization.bipartitenet.component.Paintable;
import edu.iu.sci2.visualization.bipartitenet.component.PaintableContainer;
import edu.iu.sci2.visualization.bipartitenet.model.Edge;
import edu.iu.sci2.visualization.bipartitenet.model.Node;

public class BipartiteGraphRenderer implements Paintable {
	private final BipartiteGraphDataModel data;
	private ImmutableMap<Node, NodeView> nodeToNodeView;
	
	private PaintableContainer painter = new PaintableContainer();

	private final Line2D leftLine = new Line2D.Double();

	private final Line2D rightLine = new Line2D.Double();

	public BipartiteGraphRenderer(BipartiteGraphDataModel skel, Line2D leftLine, Line2D rightLine) {
		this.data = skel;
		this.leftLine.setLine(leftLine);
		this.rightLine.setLine(rightLine);
		
		nodeToNodeView = ImmutableMap.copyOf(placeNodes());
		placeEdges();
	}
	
	private void placeEdges() {
		for (Edge e : data.getEdges()) {
			EdgeView ev = new EdgeView(nodeToNodeView.get(e.getLeftNode()),
					nodeToNodeView.get(e.getRightNode()));
			
			painter.add(ev);
		}
	}

	private LinkedHashMap<Node, NodeView> placeNodes() {
		LinkedHashMap<Node,NodeView> nodeViews = Maps.newLinkedHashMap();
		nodeViews.putAll(placeNodesOnLine(data.getRightNodes(), getRightLine(), NodeView.LabelPainter.RIGHT));
		nodeViews.putAll(placeNodesOnLine(data.getLeftNodes(), getLeftLine(), NodeView.LabelPainter.LEFT));
		
		for (NodeView nv : nodeViews.values()) {
			painter.add(nv);
		}
		
		return nodeViews;
	}

	private LinkedHashMap<Node, NodeView> placeNodesOnLine(ImmutableList<Node> nodes,
			Line2D centerLine, NodeView.LabelPainter painter) {
		LinkedHashMap<Node,NodeView> nodeViews = Maps.newLinkedHashMap();
		int numNodes = nodes.size();
		double denominator = Math.max(1, numNodes - 1); // don't divide by 0!
		
		for (int i = 0; i < numNodes; i++) {
			Point2D centerPoint = LayoutUtils.getPointAlongLine(centerLine, i / denominator);
			NodeView view = new NodeView(nodes.get(i), centerPoint, painter);
			nodeViews.put(nodes.get(i), view);
		}
		return nodeViews;
	}

	public Line2D getLeftLine() {
		return (Line2D) leftLine.clone();
	}

	public Line2D getRightLine() {
		return (Line2D) rightLine.clone();
	}

	public void paint(Graphics2D g) {
		g.setColor(Color.black);
		System.out.println(g.getClipBounds());
		painter.paint(g);
	}
	
	
}
