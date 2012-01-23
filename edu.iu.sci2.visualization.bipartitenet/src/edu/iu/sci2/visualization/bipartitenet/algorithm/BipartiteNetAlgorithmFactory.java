package edu.iu.sci2.visualization.bipartitenet.algorithm;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
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
import org.osgi.service.metatype.ObjectClassDefinition;

import com.google.common.collect.Lists;

import edu.iu.nwb.util.nwbfile.NWBFileParser;
import edu.iu.nwb.util.nwbfile.NWBFileUtilities;
import edu.iu.nwb.util.nwbfile.ParsingException;

public class BipartiteNetAlgorithmFactory implements AlgorithmFactory,
		ParameterMutator {
	private static final String LEFT_SIDE_TYPE_ID = "leftSideType";
	private static final String NODE_SIZE_COLUMN_ID = "nodeSizeColumn";

	@Override
	public Algorithm createAlgorithm(Data[] data,
			Dictionary<String, Object> parameters, CIShellContext ciShellContext) {
		try {
			return new BipartiteNetAlgorithm(getNWBFile(data),
					(String) parameters.get(NODE_SIZE_COLUMN_ID),
					(String) parameters.get(LEFT_SIDE_TYPE_ID));
		} catch (FileNotFoundException e) {
			throw new AlgorithmCreationFailedException(e);
		} catch (IOException e) {
			throw new AlgorithmCreationFailedException(e);
		} catch (ParsingException e) {
			throw new AlgorithmCreationFailedException(e);
		}
	}

	@Override
	public ObjectClassDefinition mutateParameters(Data[] data,
			ObjectClassDefinition oldParameters) {
		NWBFileExaminer examiner = new NWBFileExaminer();
		File nwbFile = getNWBFile(data);

		try {
			new NWBFileParser(nwbFile).parse(examiner);
		} catch (ParsingException e) {
			throw new AlgorithmCreationFailedException(e);
		} catch (IOException e) {
			throw new AlgorithmCreationFailedException(e);
		}

		LinkedHashMap<String, String> nodeSchema = examiner.getNodeSchema();
		List<String> numericColumns = 
				NWBFileUtilities.findNumericAttributes(
						NWBFileUtilities.removeRequiredNodeProps(nodeSchema));

		DropdownMutator mutator = new DropdownMutator();

		mutator.add(LEFT_SIDE_TYPE_ID, examiner.getBipartiteTypes());
		mutator.add(NODE_SIZE_COLUMN_ID, numericColumns);

		return mutator.mutate(oldParameters);
	}

	private File getNWBFile(Data[] data) {
		return (File) data[0].getData();
	}

}
