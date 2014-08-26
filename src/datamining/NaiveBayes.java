package datamining;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

public class NaiveBayes {
	HashMap<Integer, HashMap<Integer, Double>>[] class_conditional;
	HashMap<Integer, Double> PriorProbability;
	int[][] trainx;
	int[] trainy;

	@SuppressWarnings("unchecked")
	NaiveBayes(int[][] trainx, int[] trainy) {
		this.trainx = trainx;
		this.trainy = trainy;
		this.class_conditional = new HashMap[this.trainx[0].length];
		this.PriorProbability = new HashMap<Integer, Double>();
	}

	public void getFrequency() {
		for (int item : trainy) {
			if (this.PriorProbability.containsKey(item)) {
				this.PriorProbability.put(item,
						this.PriorProbability.get(item) + 1);
			} else {
				this.PriorProbability.put(item, 1.0d);
			}
		}

		for (int i = 0; i < this.trainx[0].length; i++) {
			HashMap<Integer, HashMap<Integer, Double>> map = new HashMap<Integer, HashMap<Integer, Double>>();

			this.class_conditional[i] = map;
			for (int j = 0; j < this.trainx.length; j++) {
				int num = this.trainy[j];
				int x = this.trainx[i][j];
				if (map.containsKey(num)) {
					HashMap<Integer, Double> sonmap = map.get(num);
					if (sonmap.containsKey(x)) {
						sonmap.put(x, sonmap.get(x) + 1);
					} else {
						sonmap.put(x, 1.0);
					}
				} else {
					HashMap<Integer, Double> sonmap = new HashMap<Integer, Double>();
					sonmap.put(x, 1.0);
					map.put(num, sonmap);
				}

			}
		}
		normalizationMap(this.PriorProbability);
		for (HashMap<Integer, HashMap<Integer, Double>> item : this.class_conditional) {
			for (int key : item.keySet()) {
				normalizationMap(item.get(key));
			}
		}
	}

	private void normalizationMap(HashMap<Integer, Double> map) {
		double sum = 0;
		for (Double item : map.values()) {
			sum += item;
		}
		for (int item : map.keySet()) {
			map.put(item, map.get(item) / sum);
		}
	}

	private double getClassConditional(int[] x, int y) {
		double posteriorprobability = 1;
		for (int i = 0; i < x.length; i++) {
			HashMap<Integer, HashMap<Integer, Double>> map = this.class_conditional[i];
			try{
				posteriorprobability = posteriorprobability * map.get(y).get(x[i]);
			}
			catch(NullPointerException e){
				e.printStackTrace();
				posteriorprobability = posteriorprobability * 1;
			}
			
		}
		return posteriorprobability;
	}

	public double getPOsteriorProbability(int[] x, int y) {
		double posteriorprobability;
		double classconditional = getClassConditional(x, y);
		Double priorprobability = this.PriorProbability.get(y);
		posteriorprobability = classconditional * priorprobability;
		return posteriorprobability;
	}
	private void soomthData(){
		
	}
}
