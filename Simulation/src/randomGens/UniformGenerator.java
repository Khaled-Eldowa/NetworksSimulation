package randomGens;

public class UniformGenerator{

	public static double generate() {
		double r;
		do {
			r = Math.random();
		} while (r == 0.0 || r == 1.0);
		return r;
	}

}
