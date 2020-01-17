package src.mandelbrot;

public class Mandelbrot {
    private double leftBound, lowerBound;
    private double rightBound, upperBound;
    private int maxIterations;

    private int height;
    private int width;
    private int[][] iterationCounts;
    
    public Mandelbrot(int width, int height, double leftBound, double lowerBound, double rightBound, double upperBound,
                      int maxIterations) {
        this.leftBound = leftBound;
        this.lowerBound = lowerBound;
        this.rightBound = rightBound;
        this.upperBound = upperBound;
        this.width = width;
        this.height = height;
        this.maxIterations = maxIterations;

        iterationCounts = new int[width][height];
    }

    private int countIterations(ComplexNumber z, ComplexNumber c) {
        int iterations = 0;
        while (z.real() * z.real() + z.imaginary() * z.imaginary() <= 2*2 && iterations < maxIterations) {
            z.mandelbrotIteration(c);
            iterations++;
        }
        
        return iterations;
    }

    public void countAllIterations() {
        double xIncrement = (rightBound - leftBound) / width;
        double yIncrement = (upperBound - lowerBound) / height;

        for (int xIdx = 0; xIdx < width; xIdx++) {
            for (int yIdx = 0; yIdx < height; yIdx++) {
                double x = leftBound + xIdx * xIncrement;
                double y = upperBound - yIdx * yIncrement;
                ComplexNumber c = new ComplexNumber(x, y);

                iterationCounts[xIdx][yIdx] = countIterations(new ComplexNumber(0, 0), c);
            }
        }
    }

    public int[][] getIterationCounts() {
        return iterationCounts;
    }
}
