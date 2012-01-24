package edu.iu.sci2.visualization.bipartitenet.model;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.font.LineMetrics;
import java.awt.font.TextLayout;
import java.awt.geom.Rectangle2D;

import edu.iu.sci2.visualization.bipartitenet.component.NodeView;

public enum NodeDestination {
	LEFT {

		@Override
		public
		void paintLabel(NodeView nv, Graphics2D g) {
			TextLayout tl = new TextLayout(nv.getLabel(),
					g.getFont(), g.getFontRenderContext());
			Rectangle2D textBounds = tl.getBounds();
			double x = nv.getNodeCenter().getX()
					- nv.getCenterToTextDistance() - textBounds.getWidth();
			
			tl.draw(g, (float) x, (float) (nv.getNodeCenter().getY()
							+ getFontCenterHeight(g)));
		}

		@Override
		public
		Color getFillColor() {
			return Color.pink;
		}

	},
	RIGHT {
		@Override
		public
		void paintLabel(NodeView nv, Graphics2D g) {
			g.drawString(
					nv.getLabel(),
					(int) nv.getNodeCenter().getX()
							+ nv.getCenterToTextDistance(),
					(int) nv.getNodeCenter().getY()
							+ getFontCenterHeight(g));
		}

		@Override
		public
		Color getFillColor() {
			return Color.orange;
		}
	};

	public abstract void paintLabel(NodeView nv, Graphics2D g);
	public abstract Color getFillColor();
	
	private static float getFontCenterHeight(Graphics2D g) {
		 LineMetrics lm = g.getFont().getLineMetrics("Asdf", g.getFontRenderContext());
		 
		 return lm.getAscent() / 2;
	}
}
