package edu.iu.sci2.visualization.bipartitenet;

import java.awt.Font;
import java.awt.Graphics2D;
import java.util.List;

import math.geom2d.Point2D;
import math.geom2d.line.LineSegment2D;

import com.google.common.collect.ImmutableMap;

import edu.iu.sci2.visualization.bipartitenet.component.CircleRadiusCoding;
import edu.iu.sci2.visualization.bipartitenet.component.CircleRadiusLegend;
import edu.iu.sci2.visualization.bipartitenet.component.Paintable;
import edu.iu.sci2.visualization.bipartitenet.component.PaintableContainer;
import edu.iu.sci2.visualization.bipartitenet.model.BipartiteGraphDataModel;
import edu.iu.sci2.visualization.bipartitenet.model.Node;

public class PageDirector implements Paintable {
	private PaintableContainer painter = new PaintableContainer();
	private BipartiteGraphDataModel dataModel;

	public static final int PAGE_HEIGHT = 800;
	public static final int PAGE_WIDTH = 800;
	// TODO: a better place for this to live?
	public static final int MAX_RADIUS = 15;

	private static final LineSegment2D LEFT_LINE = new LineSegment2D(300, 100,
			300, 500);
	private static final LineSegment2D RIGHT_LINE = new LineSegment2D(500, 100,
			500, 500);
	
	private static final Font TITLE_FONT = new Font("Dialog", Font.BOLD, 16);

	private static final Point2D CIRCLE_LEGEND_POSITION = new Point2D(250, 600);
	private static final Point2D LEFT_TITLE_POSITION = LEFT_LINE.getFirstPoint().translate(MAX_RADIUS, -50);
	private static final Point2D RIGHT_TITLE_POSITION = RIGHT_LINE.getFirstPoint().translate(- MAX_RADIUS, -50);
//	private final String leftSideType;
//	private final String rightSideType;

	public PageDirector(final BipartiteGraphDataModel dataModel, final String leftSideType, final String rightSideType) {
		this.dataModel = dataModel;
//		this.leftSideType = leftSideType;
//		this.rightSideType = rightSideType;

		CircleRadiusCoding coding = makeCircleCoding();
		ImmutableMap<Double, String> legendLabels = chooseLegendLabels();
		placeLegends(coding, legendLabels);

		BipartiteGraphRenderer renderer = new BipartiteGraphRenderer(dataModel,
				LEFT_LINE, RIGHT_LINE, coding);
		painter.add(renderer);
		
		painter.add(new RightAlignedLabel(LEFT_TITLE_POSITION, leftSideType, TITLE_FONT));
		painter.add(new Paintable() {
			@Override
			public void paint(Graphics2D g) {
				g.setFont(TITLE_FONT);
				g.drawString(rightSideType, (float) RIGHT_TITLE_POSITION.getX(), (float) RIGHT_TITLE_POSITION.getY());
			}
		});
	}

	private ImmutableMap<Double, String> chooseLegendLabels() {
		double max = getMaxNodeValue();
		double half = max / 2;
		// maybe use the decimal format choosing from geomaps?
		return ImmutableMap.of(0.0, "0", half, ""+half, max, ""+max);
	}

	private void placeLegends(CircleRadiusCoding coding,
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

	public CircleRadiusCoding makeCircleCoding() {
		double biggest = getMaxNodeValue();
		return CircleRadiusCoding.createZeroAnchoredScaledCoding(biggest,
				MAX_RADIUS);
	}

	private double getMaxNodeValue() {
		double biggest;
		List<Node> leftNodes = dataModel.getLeftNodes(), rightNodes = dataModel
				.getRightNodes();
		biggest = Math.max(leftNodes.get(0).getValue(), rightNodes.get(0)
				.getValue());
		return biggest;
	}

}
