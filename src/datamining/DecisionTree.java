package datamining;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

public class DecisionTree {
	int[] splitindex;
	double[][] trainx;
	double[] trainy;
	HashMap<Integer, HashMap> tree;
	ArrayList<Integer> index;

	public DecisionTree(double[][] trainx, double[] trainy) {
		this.splitindex = new int[trainy.length];
		this.trainx = trainx;
		this.trainy = trainy;
		this.index = new ArrayList<Integer>();
		this.tree = new HashMap<Integer, HashMap>();
		for (int i = 0; i < this.trainx[0].length; i++) {
			this.index.add(i);
		}
	}

	public static HashMap<Double, Integer> getFrequency(double[] x) {
		HashMap<Double, Integer> xfrequency = new HashMap<Double, Integer>();
		for (double num : x) {
			if (xfrequency.containsKey(num)) {
				xfrequency.put(num, xfrequency.get(num) + 1);
			} else {
				xfrequency.put(num, 1);
			}
		}
		return xfrequency;
	}

	public static double gini(double[] x) {
		HashMap<Double, Integer> xfrequency = getFrequency(x);
		double gini = 0;
		for (double num : xfrequency.values()) {
			num = num / x.length;
			gini += Math.pow(num, 2);
		}
		gini = 1 - gini;
		return gini;
	}

	public static double entropy(double[] x) {
		HashMap<Double, Integer> xfrequency = getFrequency(x);
		double entropy = 0;
		for (double num : xfrequency.values()) {
			num = num / x.length;
			entropy += (num * Math.log(num) / Math.log(2));
		}
		entropy = -entropy;
		return entropy;
	}

	public static double classificationerror(double[] x) {
		double error = 0;
		HashMap<Double, Integer> xfrequency = getFrequency(x);
		for (double num : xfrequency.values()) {
			num = num / x.length;
			if (num > error) {
				error = num;
			}
		}
		error = 1 - error;
		return error;
	}

	private double informationGain(double[] Parent, double[]... sons) {
		int sumn = 0;
		int sumsonentropy = 0;
		int n = Parent.length;
		for (double[] nums : sons) {
			sumn += nums.length;
		}
		if (sumn != n) {
			try {
				throw new Exception("sons's length is not equal to parent's!");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		double entropyparent = entropy(Parent);

		for (double[] nums : sons) {
			sumsonentropy += nums.length / n * entropy(nums);
		}
		double result = entropyparent - sumsonentropy;
		return result;

	}

	private double getSonsEntropy(Collection<ArrayList<Double>> values) {
		
		int sumsonentropy = 0;
		int n = 0;
		for(ArrayList<Double> value:values){
			n+=value.size();
		}
		
		
		for(ArrayList<Double> value:values){
			double[] arrayvalue = new double[value.size()]; 
			int i =0;
			for(double num:value){
				arrayvalue[i] =  num;
				i++;
			}
			
			sumsonentropy += value.size() / n * entropy(arrayvalue);
		}
		return sumsonentropy;
	}
	
	
	private double getSonsEntropy(double[][] sons) {
		int sumsonentropy = 0;
		int n = sons.length * sons[0].length;
		for (double[] nums : sons) {
			sumsonentropy += nums.length / n * entropy(nums);
		}
		return sumsonentropy;
	}

	public static int getMin(ArrayList<Double> sonsentropies) {
		double min = sonsentropies.get(0);
		int index = 0;
		for (int i = 0; i < sonsentropies.size(); i++) {
			if (sonsentropies.get(i) < min) {
				min = sonsentropies.get(i);
				index = i;
			}
		}
		return index;
	}

	private int findBestSplit(double[][] x, double[] y) {
		int chosedIndex = 0;
		ArrayList<Double> sonsentropies = new ArrayList<Double>();
//		System.out.println(this.index.size());
		for (int num : this.index) {
			HashMap<Integer, ArrayList<Double>> split = new HashMap<Integer, ArrayList<Double>>();
			for (int i = 0; i < y.length; i++) {
				int xnum = (int) x[i][num];
				if (split.containsKey(xnum)) {
					split.get(xnum).add(y[i]);
				} else {
					ArrayList<Double> list = new ArrayList<Double>();
					list.add(y[i]);
					split.put(xnum, list);
				}
			}
			Collection<ArrayList<Double>> values = split.values();
			double sonsentropy = getSonsEntropy(values);
			sonsentropies.add(sonsentropy);
		}
		int chosed = getMin(sonsentropies);
		chosedIndex = this.index.get(chosed);
		this.index.remove(chosed);
		return chosedIndex;
	}

	public void buildTree(HashMap<Integer, HashMap> tree, double[][] x,
			double[] y) {
		int chosedIndex = findBestSplit(x, y);
		HashMap<Integer, HashMap> son = new HashMap<Integer, HashMap>();
		tree.put(chosedIndex, son);
		HashMap<Integer, ArrayList<Integer>> split = new HashMap<Integer, ArrayList<Integer>>();
		for (int i = 0; i < y.length; i++) {
			int xnum = (int) x[i][chosedIndex];
			if (split.containsKey(xnum)) {
				split.get(xnum).add(i);
			} else {
				ArrayList<Integer> newindex = new ArrayList<Integer>();
				newindex.add(i);
				split.put(xnum, newindex);
			}
		}
		for (int mykey : split.keySet()) {
			ArrayList<Integer> indexlist = split.get(mykey);
//			System.out.println(split);
			double[][] newx = new double[indexlist.size()][x[0].length];
			double[] newy = new double[indexlist.size()];
			int i = 0;
			for (int index : indexlist) {
				newx[i] = x[index];
				newy[i] = y[index];
				i++;
			}
			
			if (isSame(newy)) {
				tree.get(chosedIndex).put(newx[0][chosedIndex], newy[0]);
			} else {
				HashMap<Integer, HashMap> grandson = new HashMap<Integer, HashMap>();
				tree.get(chosedIndex).put(newx[0][chosedIndex], grandson);
				buildTree(grandson, newx, newy);
			}
		}
	}

	public static boolean isSame(double[] x) {
//		System.out.println(x.length);
		double sum = x[0];
		for (double num : x) {
			if (num != sum) {
				return false;
			}
		}
		return true;
	}

	public static void main(String[] agr) {

		double[][] x = { { 1, 1, 2, 1, 1, 1 }, { 1, 0, 0, 1, 1, 1 },{ 0, 1, 0, 1, 1, 1 }, { 1, 1, 0, 1, 1, 1 } };
		double[] y = { 1, 1,0,1 };
		DecisionTree test = new DecisionTree(x, y);
		test.buildTree(test.tree, x, y);
		System.out.println(test.tree);
	}
}
