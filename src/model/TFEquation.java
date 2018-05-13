package model;

import java.util.ArrayList;

public class TFEquation {

	Loops l;
	ArrayList<Integer> gainsSum = new ArrayList<Integer>();
	ArrayList<Integer> pathDelta = new ArrayList<Integer>();
	double delta = 1;
	Insertion n;
	double transferFunction;

	public TFEquation(Loops l, Insertion n) {
		this.l = l;
		this.n = n;
	}

	public int sumAllLoops() {
		int sum = 0;
		for (int i = 0; i < l.lpGain.size(); i++) {
			sum += l.lpGain.get(i);
		}
		return sum;
	}

	public void nonTouchingGains() {
		for (int i = 0; i < l.nonTouchingLoops.size(); i++) {
			int gain = 1;
			for (int j = 0; j < l.nonTouchingLoops.get(i).size(); j++) {
				gain *= getLoopGain(l.nonTouchingLoops.get(i).get(j));
			}
			l.nonTouchingGains.add(gain);

		}
	}

	public int getLoopGain(ArrayList<Integer> loop) {
		int i = l.loops.indexOf(loop);
		return l.lpGain.get(i);
	}

	public void combinationGainsSum() {
		int i = 2;
		int sum = 0;
		if (l.nonTouchingLoops.size() == 1) {
			gainsSum.add(l.nonTouchingGains.get(0));
			return;
		}
		for (int j = 0; j < l.nonTouchingLoops.size() - 1; j++) {
			if (i == l.nonTouchingLoops.get(j).size()) {
				sum += l.nonTouchingGains.get(j);
				if (i != l.nonTouchingLoops.get(j + 1).size()) {
					gainsSum.add(sum);
					sum = 0;
					i++;
					if (j + 1 == l.nonTouchingLoops.size() - 1) {
						gainsSum.add(l.nonTouchingGains.get(l.nonTouchingLoops.size() - 1));
					}
				} else if (j + 1 == l.nonTouchingLoops.size() - 1) {

					sum += l.nonTouchingGains.get(j + 1);
					gainsSum.add(sum);
				}
			}
		}

	}

	public void calculateDelta() {
		if (!l.loops.isEmpty()) {
			delta -= sumAllLoops();
		}
		if (!l.nonTouchingGains.isEmpty()) {
			for (int i = 0; i < gainsSum.size(); i++) {
				if (i % 2 == 0) {
					delta += gainsSum.get(i);
				} else {
					delta -= gainsSum.get(i);
				}
			}
		}
	}

	public void calculatePathDelta() {
		for (int i = 0; i < n.getForwardPaths().size(); i++) {
			ArrayList<ArrayList<Integer>> pathLoopsNonTouching = new ArrayList<ArrayList<Integer>>();
			int pdelta = 1;
			for (int j = 0; j < l.loops.size(); j++) {
				if (l.twoNonTouching(n.getForwardPaths().get(i), l.loops.get(j))) {
					pdelta -= l.lpGain.get(j);
					pathLoopsNonTouching.add(l.loops.get(j));
				}
			}
			for (int j = 0; j < l.nonTouchingLoops.size(); j++) {
				boolean loopsMatched = true;
				for (int k = 0; k < l.nonTouchingLoops.get(j).size(); k++) {
					if (!l.loopExists(l.nonTouchingLoops.get(j).get(k), pathLoopsNonTouching)) {
						loopsMatched = false;
					}
				}
				if (loopsMatched) {
					if (l.nonTouchingLoops.get(j).size() % 2 == 0) {
						pdelta += l.nonTouchingGains.get(j);
					} else {
						pdelta -= l.nonTouchingGains.get(j);
					}
				}
			}
			pathDelta.add(pdelta);
		}
	}

	public void calculateTF() {
		double numerator = 0;
		for (int i = 0; i < n.fpGain.size(); i++) {
			numerator += (pathDelta.get(i) * n.fpGain.get(i));
		}
		transferFunction = numerator / delta;
	}

	public double getTransferFunction() {
		return transferFunction;
	}

	public ArrayList<Integer> getPathDelta() {
		return pathDelta;
	}

	public double getDelta() {
		return delta;
	}

	public void printCombination() {
		for (int i = 0; i < gainsSum.size(); i++) {
			System.out.println(gainsSum.get(i) + " for" + i);
		}
	}

}
