package datamining;

import java.util.concurrent.locks.Condition;

public class Similar {

	public static void testCondition(double[] x, double[] y) {
		if (x.length != y.length) {
			try {
				throw new Exception("length is not equal!");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.exit(1);
			}
		}
		if (IsAllZero(x) || IsAllZero(y)) {
			try {
				throw new Exception("x or y is all zero!");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.exit(1);
			}
		}
	}

	public static boolean IsAllZero(double[] x) {
		for (double num : x) {
			if (num != 0) {
				return false;
			}
		}
		return true;
	}

	public static double cosineSimilar(double[] x, double[] y) {
		double x_multiply_y_sum = 0.0d;
		double x_multiply_x_sum = 0.0d;
		double y_multiply_y_sum = 0.0d;

		testCondition(x, y);
		for (int i = 0; i < x.length; i++) {
			x_multiply_y_sum += x[i] * y[i];
			x_multiply_x_sum += x[i] * x[i];
			y_multiply_y_sum += y[i] * y[i];
		}
		double result = x_multiply_y_sum
				/ (Math.pow(x_multiply_x_sum, 0.5) * Math
						.sqrt(y_multiply_y_sum));
		return result;

	}

	public static double euclideanSimilar(double[] x, double[] y) {
		testCondition(x, y);
		double sum = 0.0d;
		for (int i = 0; i < x.length; i++) {
			sum += Math.pow((x[i] - y[i]), 2);
		}
		double result = Math.sqrt(sum);
		return result;
	}

	public static double MinkowskiSimilar(double[] x, double[] y, int r) {
		testCondition(x, y);
		double sum = 0.0d;
		for (int i = 0; i < x.length; i++) {
			sum += Math.pow((x[i] - y[i]), r);
		}
		double result = Math.pow(sum, 1.0 / r);
		return result;
	}

	public static boolean isZeroAndOne(double[] x) {
		for (double num : x) {
			if (num!=1.0d&&num!=0.0d) {
				return false;
			}
		}
		return true;
	}

	public static double SMCSimilar(double[] x, double[] y) {
		testCondition(x, y);
		double one_one = 0.0d;
		double one_zero = 0.0d;
		double zero_zero = 0.0d;
		double zero_one = 0.0d;
		if (!isZeroAndOne(x) || !isZeroAndOne(y)) {
			try {
				throw new Exception("not all zero and one!");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		for (int i = 0; i < x.length; i++) {
			if (x[i] == 0) {
				if (y[i] == 0) {
					zero_zero += 1;
				} else {
					zero_one += 1;
				}
			} else {
				if (y[i] == 0) {
					one_zero += 1;
				} else {
					one_one += 1;
				}
			}
		}
		double result = (one_one+zero_zero)/(one_one+zero_zero+one_zero+zero_one);
		return result;
	}
	public static double Jaccard(double[] x, double[] y) {
		testCondition(x, y);
		double one_one = 0.0d;
		double one_zero = 0.0d;
		double zero_zero = 0.0d;
		double zero_one = 0.0d;
		if (!isZeroAndOne(x) || !isZeroAndOne(y)) {
			try {
				throw new Exception("not all zero and one!");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		for (int i = 0; i < x.length; i++) {
			if (x[i] == 0) {
				if (y[i] == 0) {
					zero_zero += 1;
				} else {
					zero_one += 1;
				}
			} else {
				if (y[i] == 0) {
					one_zero += 1;
				} else {
					one_one += 1;
				}
			}
		}
		double result = (one_one)/(one_one+one_zero+zero_one);
		return result;
	}
	public static double getMean(double[] x){
		double  sum=0 ;
		for(double num:x){
			sum+=num;
		}
		double mean = sum/x.length;
		return mean;
		
	}
	public static double person_similar(double[] x ,double[] y){
		testCondition(x, y);
		double mean_x = getMean(x);
		double mean_y = getMean(y);
		double covariance = 0;
		double strandard_deviation_x = 0;
		double strandard_deviation_y = 0;
		for(int i =0 ; i<x.length;i++){
			covariance += (x[i]-mean_x)*(y[i]-mean_y);
			strandard_deviation_x += Math.pow((x[i]-mean_x), 2);
			strandard_deviation_y += Math.pow((y[i]-mean_y), 2);
		}
		double result = covariance/(Math.sqrt(strandard_deviation_x)*Math.sqrt(strandard_deviation_y));
		return result;
	}
	public static void main(String[] agr) {
		double[] x = {1,2 };
		double[] y = { 1 ,1};
		double result = Similar.person_similar(x, y);
		
		System.out.println(result);
		System.exit(1);
	}
}
