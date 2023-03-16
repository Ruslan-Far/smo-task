public class Main {

	public static void main(String[] args) {
		double lambda;
		double mu;
		int n;
		int m;
		int tobs;
		int tsmena;
		double ro;
		double p0;
		double potkaz;
		double pobs;
		double A;
		double nzan;
		double Loh;
		double toh;
		double z;
		double tsmo;

		n = 3;
		m = 2;
		lambda = 6;
		tobs = 5;
		tsmena = 12;

		mu = getMu(tsmena, tobs);
		ro = getRo(n, lambda, mu);
		System.out.println("Интенсивность потока обслуживания mu = " + mu + " авт/дн");
		System.out.println("Интенсивность загрузки фасовщиков ro = " + ro);
		while (true) {
			p0 = getP0(n, ro, m);
			potkaz = getPotkaz(n, n + m, ro, p0);
			pobs = 1 - potkaz;
			System.out.println("Емкость подсобных помещений m = " + m);
			System.out.println("Вероятность простоя фасовщиков при отсутствии машин p0 = " + p0);
			System.out.println("Вероятность отказа в обслуживании potkaz = " + potkaz);
			System.out.println("Вероятность обслуживания pobs = " + pobs);
			if (pobs >= 0.96)
				break;
			m++;
			System.out.println("\n\n\n");
		}
		A = getA(pobs, lambda);
		nzan = getNzan(A, mu);
		Loh = getLoh(n, ro, p0, m);
		toh = getToh(Loh, lambda);
		z = getZ(Loh, nzan);
		tsmo = getTsmo(z, lambda);
		System.out.println("\nАбсолютная пропускная способность A = " + A + " авт/дн");
		System.out.println("\nСреднее число занятых обслуживанием каналов nzan = " + nzan);
		System.out.println("\nСреднее число заявок в очереди Loh = " + Loh);
		System.out.println("\nСреднее время ожидания обслуживания toh = " + toh);
		System.out.println("\nСреднее число машин в магазине z = " + z + " авт");
		System.out.println("\nСреднее время пребывания машины в магазине tsmo = " + tsmo + " дн");
	}

	private static double getMu(int tsmena, int tobs) {
		return (double) tsmena / tobs;
	}

	private static double getRo(int n, double lambda, double mu) {
		return lambda / (n * mu);
	}

	private static double getP0(int n, double ro, int m) {
		double sum;
		double mult;
		double mult2;

		sum = 0;
		for (int k = 0; k <= n; k++) {
			sum += Math.pow(n, k) * Math.pow(ro, k) / fact(k);
		}
		mult = Math.pow(n, n) * Math.pow(ro, n + 1) / fact(n);
		mult2 = (1 - Math.pow(ro, m)) / (1 - ro);
		return 1 / (sum + mult * mult2);
	}

	private static double getPotkaz(int n, int k, double ro, double p0) {
		return p0 * Math.pow(ro, k) * Math.pow(n, n) / fact(n);
	}

	private static int fact(int n) {
		int r = 1;
		for (int i = 2; i <= n; ++i)
			r *= i;
		return r;
	}

	private static double getA(double pobs, double lambda) {
		return pobs * lambda;
	}

	private static double getNzan(double A, double mu) {
		return A / mu;
	}

	private static double getLoh(int n, double ro, double p0, int m) {
		double mult;
		double mult2;

		mult = Math.pow(n, n) * Math.pow(ro, n + 1) / fact(n);
		mult2 = (1 - Math.pow(ro, m) * (m + 1 - m * ro)) * p0 / Math.pow(1 - ro, 2);
		return mult * mult2;
	}

	private static double getToh(double Loh, double lambda) {
		return Loh / lambda;
	}

	private static double getZ(double Loh, double nzan) {
		return Loh + nzan;
	}

	private static double getTsmo(double z, double lambda) {
		return z / lambda;
	}
}
