package edu.iu.sci2.visualization.bipartitenet;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;

import javax.swing.JFrame;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

import edu.iu.sci2.visualization.bipartitenet.model.Node;

public class DrawingTest extends Canvas {

	@Override
	public void paint(Graphics g) {

		BipartiteGraphRenderer r = new BipartiteGraphRenderer(
				getTestSkeleton(), new Line2D.Double(200, 10, 200, 500),
				new Line2D.Double(400, 10, 400, 500));
		r.paint((Graphics2D) g.create(10, 10, 590, 590));

	}

	private BipartiteGraphDataModel getTestSkeleton() {
		return new BipartiteGraphDataModel(ImmutableList.of(new Node(
				"Cheezburger", 5)), ImmutableList.of(new Node("Llama", 2),
				new Node("Cat", 4)), ImmutableMap.<Node, Node> of());
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		JFrame f = new JFrame("boogers");
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setSize(600, 600);
		f.getContentPane().add(new DrawingTest());
		f.setVisible(true);
	}

}
