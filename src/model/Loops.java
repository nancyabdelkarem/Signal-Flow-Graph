package model;

import java.util.ArrayList;

public class Loops {
	int[][] SGF;
	ArrayList<ArrayList<Integer>> loops = new ArrayList<ArrayList<Integer>>();
	ArrayList<Integer> onStack = new ArrayList<Integer>();
	int numOfNodes;
	ArrayList<Integer> lpGain = new ArrayList<Integer>();
	ArrayList<ArrayList<ArrayList<Integer>>> nonTouchingLoops = new ArrayList<ArrayList<ArrayList<Integer>>>();
	ArrayList<Integer> nonTouchingGains = new ArrayList<Integer>();
	Insertion n;

	public Loops(Insertion n) {
		this.n = n;
		this.numOfNodes = n.getNumOfNodes();
		this.SGF = n.getSGF();
	}

	public ArrayList<ArrayList<Integer>> getLoop() {
		return loops;
	}

	public ArrayList<Integer> getLpGain() {
		return lpGain;
	}

	public ArrayList<ArrayList<ArrayList<Integer>>> getNonTouchingLoops() {
		return nonTouchingLoops;
	}

	public void getLoops() {
		int row = 0;
		while (row < numOfNodes) {
			for (int i = 0; i < numOfNodes; i++) {
				if (SGF[row][i] != 0) {
					onStack.add(i);
					findCycles(SGF[i]);
					onStack.clear();
				}
			}
			row++;
		}

	}

	void findCycles(int[] row) {
		for (int i = 0; i < numOfNodes; i++) {
			if (row[i] != 0 && onStack.get(0) == i) {
				onStack.add(i);
				if (!loops.contains(onStack)) {
					ArrayList<Integer> list = new ArrayList<Integer>();
					for (int k = 0; k < onStack.size(); k++) {

						list.add(onStack.get(k));
					}
					loops.add(list);
				}
				onStack.remove(onStack.size() - 1);
			} else if (row[i] != 0 && i >= onStack.get(0) && !onStack.contains(i)) {
				onStack.add(i);
				findCycles(SGF[i]);
				onStack.remove(onStack.size() - 1);
			}

		}
	}

	public void getloopGain() {

		for (int i = 0; i < loops.size(); i++) {
			int gain = 1;
			for (int k = 0; k < loops.get(i).size() - 1; k++) {
				gain = SGF[loops.get(i).get(k)][loops.get(i).get(k + 1)] * gain;
			}

			lpGain.add(gain);

		}
	}

	public void cycles() {
		int n = 2;
		ArrayList<ArrayList<ArrayList<Integer>>> output = findNonTouchingLoops(n);
		while (output.size() > 0) {
			n++;
			for (int i = 0; i < output.size(); i++) {
				nonTouchingLoops.add(output.get(i));
			}
			output = findNonTouchingLoops(n);

		}
		removeDuplicates();
	}

	public ArrayList<ArrayList<ArrayList<Integer>>> findNonTouchingLoops(int n) {
		ArrayList<ArrayList<ArrayList<Integer>>> output = new ArrayList<ArrayList<ArrayList<Integer>>>();
		if (n == 2) {
			ArrayList<ArrayList<ArrayList<Integer>>> twoNonTouching = get2NonTouching();

			return twoNonTouching;
		} else {

			ArrayList<ArrayList<ArrayList<Integer>>> lowerLevelLoops = findNonTouchingLoops(n - 1);
			for (int i = 0; i < lowerLevelLoops.size(); i++) {
				for (int j = 0; j < loops.size(); j++) {
					boolean nonTouching = true;
					for (int k = 0; k < lowerLevelLoops.get(i).size(); k++) {
						if (!twoNonTouching(loops.get(j), lowerLevelLoops.get(i).get(k))) {
							nonTouching = false;
							break;
						}
					}
					if (nonTouching) {
						ArrayList<ArrayList<Integer>> sublist = new ArrayList<ArrayList<Integer>>();
						sublist.addAll(lowerLevelLoops.get(i));
						sublist.add(loops.get(j));
						output.add(sublist);
					}
				}
			}
			return output;

		}

	}

	public boolean twoNonTouching(ArrayList<Integer> loop1, ArrayList<Integer> loop2) {
		for (int i = 0; i < loop2.size(); i++) {
			if (loop1.contains(loop2.get(i))) {
				return false;
			}
		}
		return true;
	}

	public ArrayList<ArrayList<ArrayList<Integer>>> get2NonTouching() {
		ArrayList<ArrayList<ArrayList<Integer>>> twoNonTouching = new ArrayList<ArrayList<ArrayList<Integer>>>();
		for (int i = 0; i < loops.size() - 1; i++) {
			for (int j = i + 1; j < loops.size(); j++) {
				if (twoNonTouching(loops.get(i), loops.get(j))) {
					ArrayList<ArrayList<Integer>> temp = new ArrayList<ArrayList<Integer>>();
					temp.add(loops.get(i));
					temp.add(loops.get(j));
					twoNonTouching.add(temp);
				}
			}
		}
		return twoNonTouching;
	}

	public void removeDuplicates() {
		for (int i = 0; i < nonTouchingLoops.size() - 1; i++) {
			for (int j = i + 1; j < nonTouchingLoops.size(); j++) {
				boolean listMatch = true;
				if (nonTouchingLoops.get(i).size() == nonTouchingLoops.get(j).size()) {
					for (int k = 0; k < nonTouchingLoops.get(i).size(); k++) {
						if (!loopExists(nonTouchingLoops.get(i).get(k), nonTouchingLoops.get(j))) {
							listMatch = false;
						}
					}
					if (listMatch) {
						nonTouchingLoops.remove(j);
						j--;
					}
				}
			}
		}
	}

	public boolean loopExists(ArrayList<Integer> loop, ArrayList<ArrayList<Integer>> list) {
		for (int i = 0; i < list.size(); i++) {
			if (loop == list.get(i)) {
				return true;
			}
		}
		return false;
	}

	public void print() {
		for (int i = 0; i < loops.size(); i++) {
			for (int j = 0; j < loops.get(i).size(); j++) {
				System.out.print(loops.get(i).get(j));
			}
			System.out.println("======================");
		}
	}

	public void gprint() {
		for (int i = 0; i < lpGain.size(); i++) {
			System.out.println(lpGain.get(i));
		}
	}

	void printNonTouching() {
		for (int i = 0; i < nonTouchingLoops.size(); i++) {
			for (int j = 0; j < nonTouchingLoops.get(i).size(); j++) {
				for (int k = 0; k < nonTouchingLoops.get(i).get(j).size(); k++) {
					System.out.print(nonTouchingLoops.get(i).get(j).get(k));

				}
				System.out.print("  ");
			}
			System.out.println();
		}
	}
}
