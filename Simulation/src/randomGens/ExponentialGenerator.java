package randomGens;

public class ExponentialGenerator extends RandomGenerator{

	public ExponentialGenerator(double mean) {
		this.setMean(mean);
		this.setVariance(mean*mean);
	}
	
	public double generate()
	{
		return -Math.log(UniformGenerator.generate())*this.getMean();
	}
}
