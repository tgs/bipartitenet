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
import edu.iu.sci2.visualization.bipartitenet.PageDirector;
import edu.iu.sci2.visualization.bipartitenet.component.CanvasContainer;
import edu.iu.sci2.visualization.bipartitenet.model.BipartiteGraphDataModel;
import edu.iu.sci2.visualization.bipartitenet.model.NWBDataImporter;

public class BipartiteNetAlgorithm implements Algorithm {

	private final NWBDataImporter importer;
	private final File nwbFile;

	public BipartiteNetAlgorithm(File nwbFile, String nodeSizeColumn,
			String leftSideType) {
		importer = new NWBDataImporter("bipartitetype",
				leftSideType, nodeSizeColumn);
		this.nwbFile = nwbFile;
		
	}

	@Override
	public Data[] execute() throws AlgorithmExecutionException {
		BipartiteGraphDataModel model;
		try {
			model = importer
					.constructModelFromFile(new FileInputStream(nwbFile));
		} catch (FileNotFoundException e) {
			throw new AlgorithmExecutionException(e);
		} catch (IOException e) {
			throw new AlgorithmExecutionException(e);
		} catch (ParsingException e) {
			throw new AlgorithmExecutionException(e);
		}
		
		JFrame f = new JFrame("Bipartite Network Graph");
		f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		f.setSize(600, 600);
		CanvasContainer cc = new CanvasContainer();
		PageDirector r = new PageDirector(model);
		cc.add(r);
		f.getContentPane().add(cc);
		f.setVisible(true);
		return new Data[] {};
	}

}
