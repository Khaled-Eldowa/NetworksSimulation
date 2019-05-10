package randomGens;

import java.util.ArrayList;
import draw.Histogram;
import draw.StdDraw;

public class TestGenerator {
	
	public static void test(RandomGenerator rg)
	{
		double testMean = 0.0;
		double firstTest = rg.generate();
		double max = firstTest;
		double min = firstTest;
		double temp;
		int numberOfTests = 1000000;
		ArrayList<Double> generatedList = new ArrayList<>();
		generatedList.add(firstTest);
		for (int i = 0; i < (numberOfTests-1); ++i) {
			temp = rg.generate();
			testMean += temp;
			generatedList.add(temp);
			if(temp>max)
				max = temp;
			else if(temp<min)
				min = temp;
		}
		testMean /= numberOfTests;

		System.out.println(
				"Mean to be tested : " + Double.toString(rg.getMean()) + ", Measured Mean : " + Double.toString(testMean));
		generateHistogram(numberOfTests, generatedList, min, max);	
	}
	
	private static void generateHistogram(int sampleSize, ArrayList<Double> samples, double min, double max)
	{
		int histogramSize = (int)Math.sqrt(sampleSize);
		int[] histogram = new int[histogramSize];
		double histogramIntervalWidth = (max-min)/histogramSize;
		/*for(double value:samples)
		{
			if(value < max)
				histogram[(int)((value-min-Math.ulp(value))/histogramIntervalWidth)] += 1;
		}
		System.out.println("Histogram:");
		for(int i=0; i<histogramSize; i++)
		{
			//System.out.println("Interval " + i + ": " + histogram[i]);
			System.out.print("Interval " + i + ": ");
			double stars = histogram[i]/500.0;
			for(int j=0; j<stars; j++)
				System.out.print("*");
			System.out.println();
		}*/
		
		Histogram graphical = new Histogram(histogramSize);
		for (double value:samples) {
			if(value < max)
				graphical.addDataPoint((int)((value-min-Math.ulp(value))/histogramIntervalWidth));
		}
		StdDraw.setCanvasSize(500, 400);
        graphical.draw();
	}
}
