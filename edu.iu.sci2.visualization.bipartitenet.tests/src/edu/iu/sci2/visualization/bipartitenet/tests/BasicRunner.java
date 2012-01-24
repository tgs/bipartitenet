package edu.iu.sci2.visualization.bipartitenet.tests;

import java.io.IOException;

import javax.swing.JFrame;

import math.geom2d.line.LineSegment2D;

import edu.iu.nwb.util.nwbfile.ParsingException;
import edu.iu.sci2.visualization.bipartitenet.BipartiteGraphRenderer;
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
		NWBDataImporter importer = new NWBDataImporter("bipartitetype", "Who", "totaldesirability");
		BipartiteGraphDataModel model = importer.constructModelFromFile(BasicRunner.class.getResourceAsStream("test-network.nwb"));
		
		JFrame f = new JFrame("Application Review");
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setSize(800, 800);
		CanvasContainer cc = new CanvasContainer();
		BipartiteGraphRenderer r = new BipartiteGraphRenderer(
				model, new LineSegment2D(300, 100, 300, 500),
				new LineSegment2D(500, 100, 500, 500));
		cc.add(r);
		f.getContentPane().add(cc);
		f.setVisible(true);
	}

}
