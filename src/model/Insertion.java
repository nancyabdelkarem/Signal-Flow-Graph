package model;

import java.util.ArrayList;
import java.util.Stack;

public class Insertion {

	int numOfNodes;
	int[][] SGF;
	private ArrayList<ArrayList<Integer>> forwardPaths = new ArrayList<ArrayList<Integer>>();
	ArrayList<ArrayList<Integer>> loops = new ArrayList<ArrayList<Integer>>();
	ArrayList<Integer> fpGain = new ArrayList<Integer>();

	public int[][] getSGF() {
		return SGF;
	}

	public int getNumOfNodes() {
		return numOfNodes;
	}

	public void setNumOfNodes(int numOfNodes) {

		this.numOfNodes = numOfNodes;
		SGF = new int[numOfNodes][numOfNodes];

	}

	public void fillSGF(int from, int to, int gain) {

		System.out.println("from :" + from);
		System.out.println("to:" + to);
		System.out.println("gain :" + gain);
		SGF[from][to] = gain;

	}

	public void getForwardPath() {
		Stack<Integer> track = new Stack<Integer>();
		ArrayList<Integer> path = new ArrayList<Integer>();
		int fGain = 1;
		boolean flag = false;
		int row = 0;
		track.push(0);
		path.add(0);
		for (int i = 0; i < numOfNodes; i++) {
			while (SGF[row][i] != 0 && !path.contains(i)) {
				fGain = fGain * SGF[row][i];
				track.push(i);
				path.add(i);
				row = i;

				if (row == numOfNodes - 1) {
					ArrayList<Integer> list = new ArrayList<Integer>();
					int fGains = fGain;
					for (int g = 0; g < path.size(); g++) {
						list.add(path.get(g));
					}

					getForwardPaths().add(list);
					fpGain.add(fGains);
					flag = true;
					break;
				}
				i = 0;
			}
			if (flag == true) {
				break;
			}
		}

		flag = false;
		while (!track.isEmpty()) {
			int column = track.pop();
			path.remove(path.size() - 1);
			if (track.isEmpty()) {
				break;
			}
			row = track.peek();
			fGain = fGain / SGF[row][column];
			for (int k = column + 1; k < numOfNodes; k++) {
				while (SGF[row][k] != 0 && !path.contains(k)) {
					fGain = fGain * SGF[row][k];
					track.push(k);
					path.add(k);
					row = k;
					if (row == numOfNodes - 1) {
						ArrayList<Integer> list = new ArrayList<Integer>();
						int fGains = fGain;
						for (int g = 0; g < path.size(); g++) {
							list.add(path.get(g));
						}
						getForwardPaths().add(list);
						fpGain.add(fGains);
						flag = true;
						break;
					}
				}
				if (flag == true) {
					break;
				}

			}
			flag = false;

		}

	}

	public ArrayList<ArrayList<Integer>> getForwardPaths() {
		return forwardPaths;
	}

}
