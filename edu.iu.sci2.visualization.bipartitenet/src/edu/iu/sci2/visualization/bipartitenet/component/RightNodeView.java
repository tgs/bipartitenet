package edu.iu.sci2.visualization.bipartitenet.component;

import java.awt.Graphics2D;
import java.awt.geom.Point2D;

import edu.iu.sci2.visualization.bipartitenet.model.Node;

public class RightNodeView extends AbstractNodeView {

	public RightNodeView(Node node, Point2D nodeCenter) {
		super(node, nodeCenter);
	}

	@Override
	public void paintLabel(Graphics2D g) {
		g.drawString(getNode().getLabel(), (int) getNodeCenter().getX() + getCenterToTextDistance(),
				(int) getNodeCenter().getY() + getFontCenterHeight(g));
	}

}
