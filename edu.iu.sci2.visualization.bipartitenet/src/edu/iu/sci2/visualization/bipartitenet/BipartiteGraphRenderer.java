package edu.iu.sci2.visualization.bipartitenet;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.util.Map;

import com.google.common.collect.ImmutableList;

import edu.iu.sci2.visualization.bipartitenet.component.Paintable;
import edu.iu.sci2.visualization.bipartitenet.model.Node;

public class BipartiteGraphRenderer implements Paintable {
	private BipartiteGraphDataModel data;
	private BipartiteGraphLayoutModel layout;
//	private Map<Node, Point2D> nodePosition;

	public BipartiteGraphRenderer(BipartiteGraphDataModel skel, BipartiteGraphLayoutModel layout) {
		this.data = skel;
		this.layout = layout;
	}

	public void paint(Graphics2D g) {
		g.setColor(Color.black);
		System.out.println(g.getClipBounds());
		
		renderNodesAndLabels(g, data.getLeftNodes(), layout.getLeftLine());
		renderNodesAndLabels(g, data.getRightNodes(), layout.getRightLine());

	}

	private void renderNodesAndLabels(Graphics2D g, ImmutableList<Node> nodes, Line2D line) {
		for (int i = 0; i < nodes.size(); i++) {
			Node n = nodes.get(i);
			Point2D nodeCenter = BipartiteGraphLayoutModel.getPointAlongLine(line, ((double) i) / nodes.size());
			g.drawOval((int) nodeCenter.getX(), (int) nodeCenter.getY(), (int) n.getValue(), (int) n.getValue());
			System.out.println(layout.getFontCenterHeight(g));
			g.drawString(n.getLabel(), (int) nodeCenter.getX() + layout.getCenterToTextDistance(),
					(int) nodeCenter.getY() + layout.getFontCenterHeight(g));
		}
//		g.drawString()
	}
}
