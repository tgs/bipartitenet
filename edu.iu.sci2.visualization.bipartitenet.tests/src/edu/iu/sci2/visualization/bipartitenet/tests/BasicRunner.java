package edu.iu.sci2.visualization.bipartitenet.tests;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

import edu.iu.nwb.util.nwbfile.ParsingException;
import edu.iu.sci2.visualization.bipartitenet.PageDirector;
import edu.iu.sci2.visualization.bipartitenet.component.CanvasContainer;
import edu.iu.sci2.visualization.bipartitenet.model.BipartiteGraphDataModel;
import edu.iu.sci2.visualization.bipartitenet.model.NWBDataImporter;

public class BasicRunner {

	/**
	 * @param args
	 * @throws ParsingException 
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException, ParsingException {
		NWBDataImporter importer = new NWBDataImporter("bipartitetype", "Who", "Desirability");
		BipartiteGraphDataModel model = importer.constructModelFromFile(BasicRunner.class.getResourceAsStream("test-network.nwb"));
		
		renderOnScreen(model);
		renderToPNG(model);
	}

	private static void renderToPNG(BipartiteGraphDataModel model) throws IOException {
		BufferedImage img = new BufferedImage(PageDirector.PAGE_WIDTH, PageDirector.PAGE_HEIGHT, BufferedImage.TYPE_INT_RGB);
		Graphics2D g = img.createGraphics();
		g.setPaint(Color.white);
		g.fillRect(0, 0, img.getWidth(), img.getHeight());
		g.setPaint(Color.black);
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		PageDirector r = new PageDirector(model, "Who", "What");
		r.paint(g);
		ImageIO.write(img, "PNG", new File("BLAH.png"));
	}
	
	private static void renderOnScreen(BipartiteGraphDataModel model) {
		JFrame f = new JFrame("Application Review");
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setSize(PageDirector.PAGE_WIDTH, PageDirector.PAGE_HEIGHT);
		CanvasContainer cc = new CanvasContainer();
		PageDirector r = new PageDirector(model, "Who", "What");
		cc.add(r);
		f.getContentPane().add(cc);
		f.setVisible(true);
	}

	/*
 		OutputStream out = new FileOutputStream("BLAH.eps");
		EPSDocumentGraphics2D g2d = new EPSDocumentGraphics2D(false);
		g2d.setGraphicContext(new GraphicContext());
		g2d.setupDocument(out, 600, 600);
		createRenderer().paint(g2d);
		g2d.finish();
		out.close();
	 */
}
