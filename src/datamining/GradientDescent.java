package datamining;

public class GradientDescent {
	double[][] trainset;
	double[] thet;
	double learnrate;
	double[][] trainxs;
	double[] trainys;

	GradientDescent(double[][] trainset, double learnrate) {
		this.learnrate = learnrate;
		this.trainset = trainset;
		int m = this.trainset.length;
		int n = this.trainset[0].length;
		this.thet = new double[n];
		this.trainxs = new double[m][n];
		this.trainys = new double[m];
		for (int i = 0; i < n; i++) {
			this.thet[i] = (double) 0.0;
		}
		for (int i1 = 0; i1 < m; i1++) {
			this.trainxs[i1][0] = 1;
			for (int j = 1; j < n; j++) {
				this.trainxs[i1][j] = this.trainset[i1][j - 1];
			}
			this.trainys[i1] = this.trainset[i1][n - 1];
		}
		//
		// for (int i = 0; i < m; i++) {
		// for (int j = 0; j < n; j++) {
		// System.out.print(this.trainxs[i][j] + "\t");
		// }
		// System.out.println(this.trainys[i]);
		// }
	}

	private double function(double[] trainx) {
		double f = 0;
		for (int i = 0; i < this.thet.length; i++) {
			f += this.thet[i] * trainx[i];

		}
		return f;
	}

	private double lostFunction() {
		double lost = 0;
		for (int i = 0; i < this.trainset.length; i++) {
			lost += Math.pow((this.trainys[i] - function(this.trainxs[i])), 2);
		}
		lost = lost / (2 * this.trainys.length);
		return lost;
	}

	private double[] getDiff(double detalx) {
		double[] diff = new double[this.thet.length];

		double beforelost = lostFunction();
		for (int i = 0; i < this.thet.length; i++) {
			this.thet[i] -= detalx;
			double afterlost = lostFunction();
			diff[i] = (beforelost - afterlost) / detalx;
			this.thet[i] += detalx;
		}

		for (double a : diff) {
			// System.out.println(a);
		}
		return diff;

	}

	private void updateThet(double[] diff) {
		for (int i = 0; i < this.thet.length; i++) {
			this.thet[i] -= this.learnrate * diff[i];
		}
	}

	public static void main(String[] a) {
		double[][] trainset = new double[500][2];
		for (int i = 0; i < 500; i++) {
			trainset[i][0] = i;
			trainset[i][1] = (double) (10 * i);
		}

		GradientDescent test = new GradientDescent(trainset,
				(double) 0.000000001);

		while (test.lostFunction() > 0.001) {
			double[] diff = test.getDiff((double) 0.001);
			test.updateThet(diff);

		}
		System.out.println(test.lostFunction() + "\t" + test.thet[1]);
		for (double th : test.thet) {
			System.out.println(th);
		}

	}
}