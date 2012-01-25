package edu.iu.sci2.visualization.bipartitenet.model;

import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.cishell.utilities.NumberUtilities;
import org.osgi.service.log.LogService;

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
		private boolean gotAnyLeftNodes = false;
		private boolean gotAnyRightNodes = false;

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
			NodeDestination dest;
			if (getTypeThatIsLeft().equalsIgnoreCase(type)) {
				dest = NodeDestination.LEFT;
				gotAnyLeftNodes = true;
			} else {
				dest = NodeDestination.RIGHT;
				gotAnyRightNodes = true;
			}
			
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
			
			if (something.getDestination() == NodeDestination.LEFT) {
				left = something;
				right = nodeById.get(node2);
			} else {
				left = nodeById.get(node2);
				right = something;
			}
			
			if (left.getDestination() == right.getDestination()) {
				log(LogService.LOG_WARNING, String.format("Graph is not properly bipartite: %s and %s are linked but are on the same side!",
						left, right));
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
			if (! gotAnyLeftNodes) {
				log(LogService.LOG_WARNING, "Supposedly bipartite graph has no left-hand nodes");
			}
			if (! gotAnyRightNodes) {
				log(LogService.LOG_WARNING, "Supposedly bipartite graph has no right-hand nodes");
			}
			return new BipartiteGraphDataModel(allNodes, edges, getNodeSizeCol());
		}

	}

	private final String nodeTypeCol;
	private final String nodeSizeCol;
	private final String typeThatIsLeft;
	LogService log = null;

	public NWBDataImporter(String nodeTypeCol, String typeThatIsLeft,
			String nodeSizeCol) {
		this.nodeTypeCol = nodeTypeCol;
		this.typeThatIsLeft = typeThatIsLeft;
		this.nodeSizeCol = nodeSizeCol;
	}
	
	public void log(int level, String message) {
		if (log != null) {
			log.log(level, message);
		}
	}

	public NWBDataImporter(String nodeTypeCol, String typeThatIsLeft,
			String nodeSizeCol, LogService log) {
		this.nodeTypeCol = nodeTypeCol;
		this.typeThatIsLeft = typeThatIsLeft;
		this.nodeSizeCol = nodeSizeCol;
		this.log = log;
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