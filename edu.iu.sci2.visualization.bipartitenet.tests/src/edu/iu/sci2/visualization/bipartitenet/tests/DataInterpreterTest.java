package edu.iu.sci2.visualization.bipartitenet.tests;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import edu.iu.nwb.util.nwbfile.ParsingException;
import edu.iu.sci2.visualization.bipartitenet.model.BipartiteGraphDataModel;
import edu.iu.sci2.visualization.bipartitenet.model.NWBDataImporter;

public class DataInterpreterTest {
	private NWBDataImporter importer = new NWBDataImporter("bipartitetype", "Who", "totaldesirability");
	private BipartiteGraphDataModel model;
	
	@Before
	public void constructModel() throws IOException, ParsingException {
		model = importer.constructModelFromFile(this.getClass().getResourceAsStream("test-network.nwb"));
	}
	
	@Test
	public void testModel() {
		assertNotNull(model);
		System.out.println(model.toString());
	}
}
