package edu.iu.sci2.visualization.bipartitenet;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.font.GlyphVector;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.Path2D;
import java.awt.geom.Rectangle2D;
import java.util.LinkedHashMap;

import math.geom2d.Point2D;
import math.geom2d.line.LineSegment2D;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;

import edu.iu.sci2.visualization.bipartitenet.component.CircleRadiusCoding;
import edu.iu.sci2.visualization.bipartitenet.component.CircleRadiusLegend;
import edu.iu.sci2.visualization.bipartitenet.component.EdgeView;
import edu.iu.sci2.visualization.bipartitenet.component.NodeView;
import edu.iu.sci2.visualization.bipartitenet.component.Paintable;
import edu.iu.sci2.visualization.bipartitenet.component.PaintableContainer;
import edu.iu.sci2.visualization.bipartitenet.model.BipartiteGraphDataModel;
import edu.iu.sci2.visualization.bipartitenet.model.Edge;
import edu.iu.sci2.visualization.bipartitenet.model.Node;
import edu.iu.sci2.visualization.bipartitenet.model.NodeDestination;

public class BipartiteGraphRenderer implements Paintable {
	
	private final BipartiteGraphDataModel data;
	private ImmutableMap<Node, NodeView> nodeToNodeView;
	
	private PaintableContainer painter = new PaintableContainer();

	private final LineSegment2D leftLine;

	private final LineSegment2D rightLine;
	private CircleRadiusCoding nodeRadiusCoding;

	public BipartiteGraphRenderer(BipartiteGraphDataModel skel, LineSegment2D leftLine, LineSegment2D rightLine, CircleRadiusCoding nodeRadiusCoding) {
		this.data = skel;
		this.leftLine = leftLine;
		this.rightLine = rightLine;
		this.nodeRadiusCoding = nodeRadiusCoding;
		
		nodeToNodeView = ImmutableMap.copyOf(placeNodes());
		placeEdges();
		
		
	}
	
	
//	private void doTestStuff(Graphics2D g) {
//		System.err.println(g.getFont().getFontName());
//		GlyphVector vec = g.getFont().createGlyphVector(g.getFontRenderContext(), "I am a banana!");
//		Shape s = vec.getOutline();
//		Path2D.Double path = new Path2D.Double(s);
//		path.append(new Path2D.Double(new Rectangle2D.Double(10, 10, 20, 20)).getPathIterator(null), false);
//		path.transform(AffineTransform.getTranslateInstance(10, 15));
//		g.draw(path);
//	}

	private void placeEdges() {
		for (Edge e : data.getEdges()) {
			EdgeView ev = new EdgeView(nodeToNodeView.get(e.getLeftNode()),
					nodeToNodeView.get(e.getRightNode()));
			
			painter.add(ev);
		}
	}

	private LinkedHashMap<Node, NodeView> placeNodes() {
		LinkedHashMap<Node,NodeView> nodeViews = Maps.newLinkedHashMap();
		nodeViews.putAll(placeNodesOnLine(data.getRightNodes(), getRightLine(), NodeDestination.RIGHT));
		nodeViews.putAll(placeNodesOnLine(data.getLeftNodes(), getLeftLine(), NodeDestination.LEFT));
		
		for (NodeView nv : nodeViews.values()) {
			painter.add(nv);
		}
		
		return nodeViews;
	}

	private LinkedHashMap<Node, NodeView> placeNodesOnLine(ImmutableList<Node> nodes,
			LineSegment2D centerLine, NodeDestination painter) {
		LinkedHashMap<Node,NodeView> nodeViews = Maps.newLinkedHashMap();
		int numNodes = nodes.size();
		double denominator = Math.max(1, numNodes - 1); // don't divide by 0!
		
		for (int i = 0; i < numNodes; i++) {
			Point2D centerPoint = centerLine.getPoint(i / denominator);
			NodeView view = new NodeView(nodes.get(i), centerPoint, painter, nodeRadiusCoding);
			nodeViews.put(nodes.get(i), view);
		}
		return nodeViews;
	}

	public LineSegment2D getLeftLine() {
		return leftLine;
	}

	public LineSegment2D getRightLine() {
		return rightLine;
	}

	public void paint(Graphics2D g) {
		g.setColor(Color.black);
		painter.paint(g);
		
//		doTestStuff(g);
	}
	
	
}
