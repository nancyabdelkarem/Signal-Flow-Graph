package model;

public class test {

	public static void main(String[] args) {
		Insertion n = new Insertion();

	
	n.setNumOfNodes(9);
		//n.fillSGF(0, 2, 1);
		n.fillSGF(0, 1, 1);
		//n.fillSGF(1, 3, 2);
		n.fillSGF(1,2 , 2);
		
	//	n.fillSGF(1, 4, 1);
		//n.fillSGF(2, 7, 3);
		//n.fillSGF(3, 3, 3);
		n.fillSGF(2, 3, -1);
		n.fillSGF(3, 4, -1);
		n.fillSGF(3, 6, 1);
		//n.fillSGF(4,4, 5);
		n.fillSGF(4,5, 2);
		//n.fillSGF(4, 3, -1);
		n.fillSGF(5, 4, 1);
		//n.fillSGF(6, 5, -1);
		n.fillSGF(5, 6, -1);
		n.fillSGF(7, 1, 2);
		n.fillSGF(6, 7, -1);
		n.fillSGF(6, 2, -1);
		n.fillSGF(7, 8, -1);
		n.fillSGF(7, 5, -1);
		n.fillSGF(5, 7, -1);
		//n.getForwardPath();
		/*
		for(int i = 0;i<n.forwardPaths.get(0).size();i++){
			System.out.println(n.forwardPaths.get(0).get(i));
		}
		System.out.println("====================");
		for(int i = 0;i<n.forwardPaths.get(1).size();i++){
			System.out.println(n.forwardPaths.get(1).get(i));
		}
		System.out.println("====================");
		System.out.println("====================");
		for(int i = 0;i<n.forwardPaths.get(2).size();i++){
			System.out.println(n.forwardPaths.get(2).get(i));
		}
	System.out.println(n.forwardPaths.size());
	System.out.println("====================");
	System.out.println(n.fpGain.get(0));
	System.out.println("====================");
	System.out.println(n.fpGain.get(1));*/
		Loops l=new Loops(n);
		//n.getLoops();
		l.getLoops();
	
	l.cycles();
	
//	Calculations c=new Calculations(l);
//	c.nonTouchingGains();
//	c.combinationGainsSum();
//	c.printCombination();
	}
	
}
