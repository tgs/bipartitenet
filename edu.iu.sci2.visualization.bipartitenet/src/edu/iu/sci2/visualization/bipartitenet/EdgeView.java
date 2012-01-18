package edu.iu.sci2.visualization.bipartitenet;

import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;

import edu.iu.sci2.visualization.bipartitenet.component.NodeView;
import edu.iu.sci2.visualization.bipartitenet.component.Paintable;

public class EdgeView implements Paintable {
	
	private final NodeView dest;
	private final NodeView src;

	public EdgeView(NodeView src, NodeView dest) {
		this.src = src;
		this.dest = dest;
	}

	@Override
	public void paint(Graphics2D g) {
		Line2D grossLine = new Line2D.Double(src.getNodeCenter(), dest.getNodeCenter());
		
		g.draw(grossLine);

	}


}
