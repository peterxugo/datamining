package datamining;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class KNN {
	double[][] predict;
	double[][] trainData;
	int[] trainy;
	int k;

	KNN(double[][] predict, double[][] trainData, int[] trainy, int k) {
		this.predict = predict;
		this.trainData = trainData;
		if(k>trainy.length){
			try {
				throw new Exception("k is bigger than train's length!");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.exit(0);
		}
		this.k = k;
		this.trainy = trainy;
	}
	public int[]  excute(){
		return getDistance();
	}
	private int[] getDistance() {
		int[] classresult = new int[predict.length];
		int x = 0;
		for (double[] item : predict) {
			HashMap<Integer, Double> result = new HashMap<Integer, Double>();
			for (int i = 0; i < trainData.length; i++) {
				double value = Similar.cosineSimilar(item, trainData[i]);
				result.put(i, value);
			}
			ArrayList<Entry<Integer, Double>> sortresult = mapSort(result);
			HashMap<Integer, Double> classmap = new HashMap<Integer, Double>();
			for (int i = 0; i < k; i++) {
				Entry<Integer, Double> map = sortresult.get(i);
				int index = map.getKey();
				int classname = trainy[index];
				double value = map.getValue();
				if (classmap.containsKey(classname)) {
					classmap.put(classname, classmap.get(index) + value);
				} else {
					classmap.put(classname, value);
				}
			}
			ArrayList<Entry<Integer, Double>> classmapsort = mapSort(classmap);
			classresult[x] = classmapsort.get(0).getKey();
			x++;
		}
		return classresult;
	}

	public static ArrayList<Entry<Integer, Double>> mapSort(
			HashMap<Integer, Double> map) {
		ArrayList<Entry<Integer, Double>> l = new ArrayList<Entry<Integer, Double>>(
				map.entrySet());
		Collections.sort(l, new Comparator<Map.Entry<Integer, Double>>() {
			public int compare(Map.Entry<Integer, Double> o1,
					Map.Entry<Integer, Double> o2) {
				double a = o2.getValue();
				double b = o1.getValue();
				double c = a - b;
				if (c > 0) {
					return 1;
				} else {
					return -1;
				}
			}
		});
		return l;
	}
}
