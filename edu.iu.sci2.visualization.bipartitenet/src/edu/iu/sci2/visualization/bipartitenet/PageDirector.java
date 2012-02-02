package edu.iu.sci2.visualization.bipartitenet;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.util.List;

import math.geom2d.Point2D;
import math.geom2d.line.LineSegment2D;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Iterables;

import edu.iu.sci2.visualization.bipartitenet.component.CircleRadiusLegend;
import edu.iu.sci2.visualization.bipartitenet.component.EdgeWeightLegend;
import edu.iu.sci2.visualization.bipartitenet.component.Paintable;
import edu.iu.sci2.visualization.bipartitenet.component.PaintableContainer;
import edu.iu.sci2.visualization.bipartitenet.component.RightAlignedLabel;
import edu.iu.sci2.visualization.bipartitenet.model.BipartiteGraphDataModel;
import edu.iu.sci2.visualization.bipartitenet.model.Edge;
import edu.iu.sci2.visualization.bipartitenet.model.Node;
import edu.iu.sci2.visualization.bipartitenet.scale.BrightnessScale;
import edu.iu.sci2.visualization.bipartitenet.scale.ConstantCircleRadius;
import edu.iu.sci2.visualization.bipartitenet.scale.ConstantColor;
import edu.iu.sci2.visualization.bipartitenet.scale.Scale;
import edu.iu.sci2.visualization.bipartitenet.scale.ZeroAnchoredCircleRadiusScale;

public class PageDirector implements Paintable {
	private PaintableContainer painter = new PaintableContainer();
	private BipartiteGraphDataModel dataModel;

	public static final int PAGE_HEIGHT = 800;
	public static final int PAGE_WIDTH = 800;
	public static final int MAX_RADIUS = 15;

	private static final LineSegment2D LEFT_LINE = new LineSegment2D(300, 100,
			300, 500);
	private static final LineSegment2D RIGHT_LINE = new LineSegment2D(500, 100,
			500, 500);
	
	public static final Font BASIC_FONT = new Font("Arial", Font.PLAIN, 12);
	private static final Font TITLE_FONT = BASIC_FONT.deriveFont(Font.BOLD, 16);

	private static final Point2D CIRCLE_LEGEND_POSITION = new Point2D(250, 600);
	private static final Point2D LEFT_TITLE_POSITION = LEFT_LINE.getFirstPoint().translate(MAX_RADIUS, -50);
	private static final Point2D RIGHT_TITLE_POSITION = RIGHT_LINE.getFirstPoint().translate(- MAX_RADIUS, -50);
	private static final Point2D EDGE_LEGEND_POSITION = new Point2D(550, 600);

	public PageDirector(final BipartiteGraphDataModel dataModel, final String leftSideType, String leftSideTitle, final String rightSideType, final String rightSideTitle) {
		this.dataModel = dataModel;

		Scale<Double,Double> coding = makeCircleCoding();
		Scale<Double,Color> edgeCoding = makeColorCoding();
		
		if (dataModel.hasWeightedNodes()) {
			ImmutableMap<Double, String> legendLabels = chooseNodeLegendLabels();
			placeNodeLegend(coding, legendLabels);
		}

		if (dataModel.hasWeightedEdges()) {
			ImmutableList<Double> edgeLegendLabels = chooseEdgeLegendLabels(edgeCoding);
			placeEdgeLegend(edgeCoding, edgeLegendLabels);
		}
		
		BipartiteGraphRenderer renderer = new BipartiteGraphRenderer(dataModel,
				LEFT_LINE, RIGHT_LINE, coding, edgeCoding);
		painter.add(renderer);
		
		painter.add(new RightAlignedLabel(LEFT_TITLE_POSITION, leftSideTitle, TITLE_FONT));
		painter.add(new Paintable() {
			@Override
			public void paint(Graphics2D g) {
				g.setFont(TITLE_FONT);
				g.drawString(rightSideTitle, (float) RIGHT_TITLE_POSITION.getX(), (float) RIGHT_TITLE_POSITION.getY());
			}
		});
	}


	private void placeEdgeLegend(Scale<Double, Color> edgeCoding,
			ImmutableList<Double> edgeLegendLabels) {
		EdgeWeightLegend legend = new EdgeWeightLegend(EDGE_LEGEND_POSITION, 
				"Edge Weight: " + dataModel.getEdgeValueAttribute(),
				edgeCoding, edgeLegendLabels);
		painter.add(legend);
	}

	private Scale<Double,Color> makeColorCoding() {
		if (dataModel.hasWeightedEdges()) {
			Scale<Double,Color> colorScale = BrightnessScale.createWithDefaultColor();
			colorScale.train(Iterables.transform(dataModel.getEdges(), Edge.VALUE_GETTER));
			colorScale.doneTraining();
			return colorScale;
		} else {
			return new ConstantColor();
		}
	}

	private ImmutableMap<Double, String> chooseNodeLegendLabels() {
		double max = getMaxNodeValue();
		double half = max / 2;
		// maybe use the decimal format choosing from geomaps?
		return ImmutableMap.of(0.0, "0.0", half, ""+half, max, ""+max);
	}
	
	private ImmutableList<Double> chooseEdgeLegendLabels(Scale<Double,Color> edgeCoding) {
		return edgeCoding.getExtrema();
	}

	private void placeNodeLegend(Scale<Double,Double> coding,
			ImmutableMap<Double, String> labels) {
		CircleRadiusLegend legend = new CircleRadiusLegend(
				CIRCLE_LEGEND_POSITION, "Circle Area: "
						+ dataModel.getNodeValueAttribute(), coding, labels,
				MAX_RADIUS);
		painter.add(legend);
	}

	@Override
	public void paint(Graphics2D g) {
		painter.paint(g);
	}

	private Scale<Double, Double> makeCircleCoding() {
		if (dataModel.hasWeightedNodes()) {
			Scale<Double, Double> nodeScale = new ZeroAnchoredCircleRadiusScale(calculateMaxNodeRadius());
			nodeScale.train(Iterables.transform(dataModel.getLeftNodes(), Node.VALUE_GETTER));
			nodeScale.train(Iterables.transform(dataModel.getRightNodes(), Node.VALUE_GETTER));
			nodeScale.doneTraining();
			return nodeScale;
		} else {
			return new ConstantCircleRadius(calculateMaxNodeRadius());
		}
	}

	private double getMaxNodeValue() {
		double biggest;
		List<Node> leftNodes = dataModel.getLeftNodes(),
				rightNodes = dataModel.getRightNodes();
		
		biggest = Math.max(leftNodes.get(0).getValue(),
				rightNodes.get(0).getValue());
		return biggest;
	}
	
	private double calculateMaxNodeRadius() {
		int maxNodesOnOneSide = Math.max(dataModel.getLeftNodes().size(), dataModel.getRightNodes().size());
		return Math.min(MAX_RADIUS, LEFT_LINE.getLength() / maxNodesOnOneSide);
	}
}
