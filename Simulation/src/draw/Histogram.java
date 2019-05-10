package draw;

public class Histogram {
    private final double[] freq;   // freq[i] = # occurences of value i
    private double max;            // max frequency of any value

    // Create a new histogram. 
    public Histogram(int n) {
        freq = new double[n];
    }

    // Add one occurrence of the value i. 
    public void addDataPoint(int i) {
        freq[i]++; 
        if (freq[i] > max) max = freq[i]; 
    } 

    // draw (and scale) the histogram.
    public void draw() {
        StdDraw.setYscale(-1, max + 1);  // to leave a little border
        StdStats.plotBars(freq);
    }
 
} 