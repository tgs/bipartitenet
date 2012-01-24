package edu.iu.sci2.visualization.bipartitenet.model;

import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.cishell.utilities.NumberUtilities;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import edu.iu.nwb.util.nwbfile.NWBFileParser;
import edu.iu.nwb.util.nwbfile.NWBFileParserHandler;
import edu.iu.nwb.util.nwbfile.ParsingException;

public class NWBDataImporter {

	private class ParserHandler implements NWBFileParserHandler {
		private Map<Integer, Node> nodeById = Maps.newHashMap();
		private List<Node> allNodes = Lists.newArrayList();
		private List<Edge> edges = Lists.newArrayList();

		@Override
		public void addComment(String comment) {
		}

		@Override
		public void addDirectedEdge(int sourceNode, int targetNode,
				Map<String, Object> attributes) {
			// we re-direct the edges so they're always left->right.
			addUndirectedEdge(sourceNode, targetNode, attributes);
		}

		@Override
		public void addNode(int id, String label, Map<String, Object> attributes) {
			
			
			Double weight = NumberUtilities.interpretObjectAsDouble(attributes.get(getNodeSizeCol()));
			String type = (String) attributes.get(getNodeTypeCol());
			NodeDestination dest = getTypeThatIsLeft().equalsIgnoreCase(type) ? NodeDestination.LEFT : NodeDestination.RIGHT;
			
			Node nodeObj = new Node(label,
					weight, dest);
			allNodes.add(nodeObj);
			nodeById.put(id, nodeObj);
		}

		@Override
		public void addUndirectedEdge(int node1, int node2,
				Map<String, Object> attributes) {
			Node left, right, something;
			something = nodeById.get(node1);
			// TODO check for consistency
			if (something.getDestination() == NodeDestination.LEFT) {
				left = something;
				right = nodeById.get(node2);
			} else {
				left = nodeById.get(node2);
				right = something;
			}
			
			edges.add(new Edge(left, right));
			
		}

		@Override
		public void finishedParsing() {

		}

		@Override
		public boolean haltParsingNow() {
			return false;
		}

		@Override
		public void setDirectedEdgeCount(int numberOfEdges) {
		}

		@Override
		public void setDirectedEdgeSchema(LinkedHashMap<String, String> schema) {
		}

		@Override
		public void setNodeCount(int numberOfNodes) {
		}

		@Override
		public void setNodeSchema(LinkedHashMap<String, String> schema) {
			// TODO Check schema for consistency with data types and stuff
		}

		@Override
		public void setUndirectedEdgeCount(int numberOfEdges) {
		}

		@Override
		public void setUndirectedEdgeSchema(LinkedHashMap<String, String> schema) {
		}

		public BipartiteGraphDataModel constructGraphDataModel() {
			return new BipartiteGraphDataModel(allNodes, edges);
		}

	}

	private final String nodeTypeCol;

	private final String nodeSizeCol;

	private final String typeThatIsLeft;

	public NWBDataImporter(String nodeTypeCol, String typeThatIsLeft,
			String nodeSizeCol) {
		this.nodeTypeCol = nodeTypeCol;
		this.typeThatIsLeft = typeThatIsLeft;
		this.nodeSizeCol = nodeSizeCol;
	}

	public BipartiteGraphDataModel constructModelFromFile(InputStream nwbData)
			throws IOException, ParsingException {
		ParserHandler handler = new ParserHandler();
		NWBFileParser parser = new NWBFileParser(nwbData);
		parser.parse(handler);
		return handler.constructGraphDataModel();
	}

	public String getNodeSizeCol() {
		return nodeSizeCol;
	}

	public String getNodeTypeCol() {
		return nodeTypeCol;
	}

	public String getTypeThatIsLeft() {
		return typeThatIsLeft;
	}
}