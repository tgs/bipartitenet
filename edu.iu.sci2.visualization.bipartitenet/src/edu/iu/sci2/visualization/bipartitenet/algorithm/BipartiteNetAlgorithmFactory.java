package edu.iu.sci2.visualization.bipartitenet.algorithm;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.LinkedHashMap;
import java.util.List;

import org.cishell.framework.CIShellContext;
import org.cishell.framework.algorithm.Algorithm;
import org.cishell.framework.algorithm.AlgorithmCreationFailedException;
import org.cishell.framework.algorithm.AlgorithmFactory;
import org.cishell.framework.algorithm.ParameterMutator;
import org.cishell.framework.data.Data;
import org.cishell.utilities.mutateParameter.dropdown.DropdownMutator;
import org.osgi.service.log.LogService;
import org.osgi.service.metatype.ObjectClassDefinition;

import com.google.common.collect.Lists;

import edu.iu.nwb.util.nwbfile.NWBFileParser;
import edu.iu.nwb.util.nwbfile.NWBFileUtilities;
import edu.iu.nwb.util.nwbfile.ParsingException;

public class BipartiteNetAlgorithmFactory implements AlgorithmFactory, ParameterMutator {
	private static final String NO_EDGE_WEIGHT_OPTION = "No edge weight";
	private static final String NO_NODE_WEIGHT_OPTION = "No node weight";
	private static final String LEFT_SIDE_TYPE_ID = "leftSideType";
	private static final String NODE_SIZE_COLUMN_ID = "nodeSizeColumn";
	private static final String EDGE_WEIGHT_COLUMN_ID = "edgeWeightColumn";
	private static final String LEFT_COLUMN_TITLE_ID = "leftColumnTitle";
	private static final String RIGHT_COLUMN_TITLE_ID = "rightColumnTitle";
	
	private NWBFileExaminer examiner;
	
	@Override
	public Algorithm createAlgorithm(Data[] data,
			Dictionary<String, Object> parameters, CIShellContext ciShellContext) {
		LogService log = (LogService) ciShellContext.getService(LogService.class.getName());
		
		List<String> types = new ArrayList<String>(examiner.getBipartiteTypes());
		String leftSideType = (String) parameters.get(LEFT_SIDE_TYPE_ID);
		types.remove(leftSideType);
		String rightSideType = types.get(0);
		
		String leftSideTitle = (String) parameters.get(LEFT_COLUMN_TITLE_ID);
		leftSideTitle = leftSideTitle.trim().isEmpty() ? leftSideType : leftSideTitle;
		String rightSideTitle = (String) parameters.get(RIGHT_COLUMN_TITLE_ID);
		rightSideTitle = rightSideTitle.trim().isEmpty() ? rightSideType : rightSideTitle;
		
		String nodeWeightColumn = (String) parameters.get(NODE_SIZE_COLUMN_ID);
		if (NO_NODE_WEIGHT_OPTION.equals(nodeWeightColumn)) {
			nodeWeightColumn = null;
		}
		
		String edgeWeightColumn = (String) parameters.get(EDGE_WEIGHT_COLUMN_ID);
		if (NO_EDGE_WEIGHT_OPTION.equals(edgeWeightColumn)) {
			edgeWeightColumn = null;
		}
		
		return new BipartiteNetAlgorithm(data[0], getNWBFile(data),
				nodeWeightColumn, edgeWeightColumn,
				leftSideType, leftSideTitle, rightSideType, rightSideTitle, log);
	}

	@Override
	public ObjectClassDefinition mutateParameters(Data[] data,
			ObjectClassDefinition oldParameters) {
		examiner = new NWBFileExaminer();
		File nwbFile = getNWBFile(data);

		try {
			new NWBFileParser(nwbFile).parse(examiner);
		} catch (ParsingException e) {
			throw new AlgorithmCreationFailedException(e);
		} catch (IOException e) {
			throw new AlgorithmCreationFailedException(e);
		}

		LinkedHashMap<String, String> nodeSchema = examiner.getNodeSchema();
		List<String> nodeNumericColumns = Lists.newArrayList(
				NWBFileUtilities.findNumericAttributes(
						NWBFileUtilities.removeRequiredNodeProps(nodeSchema)));
		nodeNumericColumns.add(0, NO_NODE_WEIGHT_OPTION);

		List<String> edgeNumericColumns = Lists.newArrayList(
				NWBFileUtilities.findNumericAttributes(
						NWBFileUtilities.removeRequiredEdgeProps(
								NWBFileUtilities.getConsistentEdgeAttributes(examiner))));
		edgeNumericColumns.add(0, NO_EDGE_WEIGHT_OPTION);
		
		DropdownMutator mutator = new DropdownMutator();

		mutator.add(LEFT_SIDE_TYPE_ID, examiner.getBipartiteTypes());
		mutator.add(NODE_SIZE_COLUMN_ID, nodeNumericColumns);
		mutator.add(EDGE_WEIGHT_COLUMN_ID, edgeNumericColumns);
		

		return mutator.mutate(oldParameters);
	}

	private File getNWBFile(Data[] data) {
		return (File) data[0].getData();
	}

}
