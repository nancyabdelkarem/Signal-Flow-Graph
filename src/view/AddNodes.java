package view;

import java.util.Random;

import com.mxgraph.view.mxGraph;

import model.Insertion;

public class AddNodes extends View {

	public AddNodes(String nodes) {
		int x = 20;
		int y = 100;
		Random rand = new Random();
		this.getGraph().getModel().beginUpdate();
		Object parent = this.getGraph().getDefaultParent();
		int numOfNodes = Integer.parseInt(nodes);

		for (int i = 0; i < numOfNodes; i++) {

			Object v1 = this.getGraph().insertVertex(parent, null, String.valueOf(i), x, y, 30, 30,
					"fillColor=pink;shape=ellipse;perimeter=ellipsePerimeter");
			x = x + (600 / numOfNodes);
			y = y + 10;
			this.getM().put(String.valueOf(i), v1);
		}

	}

}