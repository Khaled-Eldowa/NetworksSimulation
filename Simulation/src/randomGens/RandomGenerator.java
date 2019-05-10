package randomGens;

public abstract class RandomGenerator {
	private double mean;
	private double variance;
	public double getMean() {
		return mean;
	}
	public void setMean(double mean) {
		this.mean = mean;
	}
	public double getVariance() {
		return variance;
	}
	public void setVariance(double variance) {
		this.variance = variance;
	}
	public abstract double generate();
	
}
