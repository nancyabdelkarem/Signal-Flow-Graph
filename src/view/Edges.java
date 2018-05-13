package view;

import java.util.HashMap;

import javax.swing.JOptionPane;

import com.mxgraph.view.mxGraph;

import model.Insertion;

public class Edges extends View {
	public Edges(Insertion n) {

		Object parent = this.getGraph().getDefaultParent();
		String nodeFrom = JOptionPane.showInputDialog("add edge from :");
		Object v1 = this.getM().get(nodeFrom);
		String nodeTo = JOptionPane.showInputDialog("to :");
		Object v2 = this.getM().get(nodeTo);
		String gain = JOptionPane.showInputDialog("enter gain :");
		this.getGraph().insertEdge(parent, null, gain, v1, v2);
		int from = Integer.parseInt(nodeFrom);
		int to = Integer.parseInt(nodeTo);
		int edgeGain = Integer.parseInt(gain);
		System.out.println(n.getNumOfNodes());
		n.fillSGF(from, to, edgeGain);

	}

}
