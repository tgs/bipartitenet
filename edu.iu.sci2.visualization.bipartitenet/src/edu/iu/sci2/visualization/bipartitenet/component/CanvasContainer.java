package edu.iu.sci2.visualization.bipartitenet.component;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class CanvasContainer extends Canvas {
	private PaintableContainer container = new PaintableContainer();

	public void add(Paintable child) {
		container.add(child);
	}

	public boolean remove(Paintable child) {
		return container.remove(child);
	}

	@Override
	public void paint(Graphics g) {
		container.paint((Graphics2D) g);
	}

}
