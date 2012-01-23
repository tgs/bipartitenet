package edu.iu.sci2.visualization.bipartitenet.algorithm;

import java.util.Dictionary;

import org.cishell.framework.CIShellContext;
import org.cishell.framework.algorithm.Algorithm;
import org.cishell.framework.algorithm.AlgorithmFactory;
import org.cishell.framework.data.Data;

public class BipartiteNetAlgorithmFactory implements AlgorithmFactory {

	@Override
	public Algorithm createAlgorithm(Data[] data,
			Dictionary<String, Object> parameters, CIShellContext ciShellContext) {
		return new BipartiteNetAlgorithm();
	}

}
