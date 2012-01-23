package edu.iu.sci2.visualization.bipartitenet.algorithm;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.JFrame;

import math.geom2d.line.LineSegment2D;

import org.cishell.framework.algorithm.Algorithm;
import org.cishell.framework.algorithm.AlgorithmExecutionException;
import org.cishell.framework.data.Data;

import edu.iu.nwb.util.nwbfile.ParsingException;
import edu.iu.sci2.visualization.bipartitenet.BipartiteGraphRenderer;
import edu.iu.sci2.visualization.bipartitenet.component.CanvasContainer;
import edu.iu.sci2.visualization.bipartitenet.model.BipartiteGraphDataModel;
import edu.iu.sci2.visualization.bipartitenet.model.NWBDataImporter;

public class BipartiteNetAlgorithm implements Algorithm {

	public BipartiteNetAlgorithm(File nwbFile, String nodeSizeColumn,
			String leftSideType) throws FileNotFoundException, IOException,
			ParsingException {
		NWBDataImporter importer = new NWBDataImporter("bipartitetype",
				leftSideType, nodeSizeColumn);
		BipartiteGraphDataModel model = importer
				.constructModelFromFile(new FileInputStream(nwbFile));
		
		// XXX this should be in .execute() below!
		
		JFrame f = new JFrame("Bipartite Network Graph");
		f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		f.setSize(600, 600);
		CanvasContainer cc = new CanvasContainer();
		BipartiteGraphRenderer r = new BipartiteGraphRenderer(
				model, new LineSegment2D(200, 100, 200, 500),
				new LineSegment2D(400, 100, 400, 500));
		cc.add(r);
		f.getContentPane().add(cc);
		f.setVisible(true);
	}

	@Override
	public Data[] execute() throws AlgorithmExecutionException {
		return new Data[] {};
	}

}
