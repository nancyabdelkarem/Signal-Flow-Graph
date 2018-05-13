package view;

import java.awt.Color;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.view.mxGraph;

import model.Insertion;
import model.Loops;
import model.TFEquation;

public class View extends JFrame {
	protected static mxGraph graph = new mxGraph();
	protected static HashMap m = new HashMap();
	private mxGraphComponent graphComponent;
	protected JTextField text;
	private JButton buttonAdd;
	private JButton buttonFin;
	private JButton addEdge;
	private JButton calculate;
	private Object cell;
	private Insertion n = new Insertion();
	Loops l;
	TFEquation transferFun;
	public int numOfNodes;
	private JFrame cal = new JFrame("Calculations");
	private JLabel fp;
	private JLabel lp;
	private JLabel nonlp;
	private JLabel trFun;
	private JLabel delta;
	private JTextArea fptxt;
	private JTextArea lptxt;
	private JTextArea nonlptxt;
	private JTextArea deltatxt;
	private JTextField trFuntxt;

	public static HashMap getM() {
		return m;
	}

	public static mxGraph getGraph() {
		return graph;
	}

	public View() {
		super("JGraph - Signal Flow Graph");
		initGUI();
	}

	private void initGUI() {
		setSize(700, 500);
		setLocationRelativeTo(null);

		graphComponent = new mxGraphComponent(graph);
		graphComponent.setConnectable(false);
		graphComponent.setPreferredSize(new Dimension(670, 380));
		graphComponent.getViewport().setBackground(Color.white);
		getContentPane().add(graphComponent);

		text = new JTextField();
		getContentPane().add(text);
		text.setPreferredSize(new Dimension(250, 21));
		setLayout(new FlowLayout(FlowLayout.LEFT));

		buttonAdd = new JButton("NUM Of Nodes");
		getContentPane().add(buttonAdd);
		buttonAdd.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				AddNodes add = new AddNodes(text.getText());
				numOfNodes = Integer.parseInt(text.getText());
				n.setNumOfNodes(numOfNodes);
				;
				text.setText("");
			}
		});

		addEdge = new JButton("Add Edge");
		getContentPane().add(addEdge);
		addEdge.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				Edges edge = new Edges(n);

			}
		});

		buttonFin = new JButton("Draw");
		getContentPane().add(buttonFin);
		buttonFin.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				graph.getModel().endUpdate();

			}
		});
		calculate = new JButton("Calculate");
		getContentPane().add(calculate);
		calculate.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				listingFrame();

			}
		});

		graphComponent.getGraphControl().addMouseListener(new MouseAdapter() {

			public void mouseReleased(MouseEvent e) {
				cell = graphComponent.getCellAt(e.getX(), e.getY());
			}
		});
	}

	void listingFrame() {

		cal.setVisible(true);
		cal.setSize(760, 700);
		cal.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JPanel panel = new JPanel();
		panel.setLayout(null);
		cal.add(panel);
		fp = new JLabel("Forward Paths");
		fp.setBounds(10, 5, 100, 20);
		panel.add(fp);
		fptxt = new JTextArea();
		fptxt.setBounds(135, 5, 300, 100);
		panel.add(fptxt);
		lp = new JLabel("Loops");
		lp.setBounds(10, 110, 80, 20);
		panel.add(lp);
		lptxt = new JTextArea();
		lptxt.setBounds(135, 110, 300, 100);
		panel.add(lptxt);
		nonlp = new JLabel("Non Touching Loops");
		nonlp.setBounds(10, 210, 130, 20);
		panel.add(nonlp);
		nonlptxt = new JTextArea();
		nonlptxt.setBounds(135, 215, 300, 170);
		panel.add(nonlptxt);
		delta = new JLabel("Delta");
		delta.setBounds(10, 400, 100, 20);
		panel.add(delta);
		deltatxt = new JTextArea();
		deltatxt.setBounds(135, 400, 300, 100);
		panel.add(deltatxt);

		trFun = new JLabel("Overall Transfer Function");
		trFun.setBounds(20, 520, 200, 20);
		panel.add(trFun);
		trFuntxt = new JTextField();
		trFuntxt.setBounds(210, 510, 200, 50);
		panel.add(trFuntxt);

		listingOutput();

	}

	void listingOutput() {
		n.getForwardPath();
		l = new Loops(n);
		transferFun = new TFEquation(l, n);
		l.getLoops();
		l.cycles();
		l.getloopGain();
		transferFun.nonTouchingGains();
		transferFun.combinationGainsSum();
		transferFun.calculateDelta();
		transferFun.calculatePathDelta();
		transferFun.calculateTF();

		String forwardPaths = "";

		for (int i = 0; i < n.getForwardPaths().size(); i++) {

			for (int j = 0; j < n.getForwardPaths().get(i).size(); j++) {
				forwardPaths += (new StringBuilder()).append(n.getForwardPaths().get(i).get(j));

			}

			forwardPaths += System.lineSeparator();

		}
		fptxt.setText(forwardPaths);
		String loops = "";
		for (int i = 0; i < l.getLoop().size(); i++) {
			for (int j = 0; j < l.getLoop().get(i).size(); j++) {
				loops += (new StringBuilder()).append(l.getLoop().get(i).get(j));
			}

			loops += System.lineSeparator();
		}
		lptxt.setText(loops);
		String nonTouchingLoop = "";
		for (int i = 0; i < l.getNonTouchingLoops().size(); i++) {
			for (int j = 0; j < l.getNonTouchingLoops().get(i).size(); j++) {
				for (int k = 0; k < l.getNonTouchingLoops().get(i).get(j).size(); k++) {
					nonTouchingLoop += (new StringBuilder()).append(l.getNonTouchingLoops().get(i).get(j).get(k));

				}
				nonTouchingLoop += ("  ");
			}
			nonTouchingLoop += System.lineSeparator();
		}

		nonlptxt.setText(nonTouchingLoop);

		String pathDelta = "";
		pathDelta = "overal all delta ->" + Double.toString(transferFun.getDelta());
		pathDelta += System.lineSeparator();
		for (int i = 0; i < transferFun.getPathDelta().size(); i++) {
			pathDelta += "delta" + Integer.toString(i + 1) + "->" + Double.toString(transferFun.getPathDelta().get(i));
			pathDelta += System.lineSeparator();
		}
		deltatxt.setText(pathDelta);
		trFuntxt.setText(Double.toString(transferFun.getTransferFunction()));
	}

}