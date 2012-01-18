package edu.iu.sci2.visualization.bipartitenet;
import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.swing.JFrame;

import org.apache.xmlgraphics.java2d.GraphicContext;
import org.apache.xmlgraphics.java2d.ps.EPSDocumentGraphics2D;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

import edu.iu.sci2.visualization.bipartitenet.model.Node;

public class DrawingTest extends Canvas {

	@Override
	public void paint(Graphics g) {

		BipartiteGraphRenderer r = createRenderer();
		r.paint((Graphics2D) g.create(10, 10, 590, 590));

	}

	private BipartiteGraphRenderer createRenderer() {
		BipartiteGraphRenderer r = new BipartiteGraphRenderer(
				getTestSkeleton(), new BipartiteGraphLayoutModel(
						new Line2D.Double(200, 10, 200, 500),
						new Line2D.Double(400, 10, 400, 500)));
		return r;
	}

	private BipartiteGraphDataModel getTestSkeleton() {
		return new BipartiteGraphDataModel(ImmutableList.of(new Node(
				"Cheezburger", 5)), ImmutableList.of(new Node("Llama", 2),
				new Node("Cat", 4)), ImmutableMap.<Node, Node> of());
	}

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		JFrame f = new JFrame("boogers");
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setSize(600, 600);
		f.getContentPane().add(new DrawingTest());
		f.setVisible(true);
		
		OutputStream out = new FileOutputStream("BLAH.eps");
		EPSDocumentGraphics2D g2d = new EPSDocumentGraphics2D(false);
		g2d.setGraphicContext(new GraphicContext());
		g2d.setupDocument(out, 600, 600);
		new DrawingTest().createRenderer().paint(g2d);
		g2d.finish();
		out.close();
		
	}

}
