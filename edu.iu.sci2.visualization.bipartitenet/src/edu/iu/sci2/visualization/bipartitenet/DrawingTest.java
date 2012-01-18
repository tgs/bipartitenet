package edu.iu.sci2.visualization.bipartitenet;

import java.awt.geom.Line2D;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.swing.JFrame;

import org.apache.xmlgraphics.java2d.GraphicContext;
import org.apache.xmlgraphics.java2d.ps.EPSDocumentGraphics2D;

import com.google.common.collect.ImmutableList;

import edu.iu.sci2.visualization.bipartitenet.component.CanvasContainer;
import edu.iu.sci2.visualization.bipartitenet.model.Edge;
import edu.iu.sci2.visualization.bipartitenet.model.Node;

public class DrawingTest {

	private static BipartiteGraphRenderer createRenderer() {
		BipartiteGraphRenderer r = new BipartiteGraphRenderer(
				getTestDataModel(), new Line2D.Double(200, 10, 200, 500),
				new Line2D.Double(400, 10, 400, 500));
		return r;
	}

	private static BipartiteGraphDataModel getTestDataModel() {
		Node cheez = new Node(
				"Cheezburger", 5);
		Node llama = new Node("Llama", 2);
		Node cat = new Node("Cat", 4);
		return new BipartiteGraphDataModel(ImmutableList.of(cheez), 
				ImmutableList.of(llama, cat),
				ImmutableList.of(new Edge(cheez, llama), new Edge(cheez, cat)));
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
		CanvasContainer cc = new CanvasContainer();
		cc.add(createRenderer());
		f.getContentPane().add(cc);
		f.setVisible(true);
		
		OutputStream out = new FileOutputStream("BLAH.eps");
		EPSDocumentGraphics2D g2d = new EPSDocumentGraphics2D(false);
		g2d.setGraphicContext(new GraphicContext());
		g2d.setupDocument(out, 600, 600);
		createRenderer().paint(g2d);
		g2d.finish();
		out.close();
		
	}

}
