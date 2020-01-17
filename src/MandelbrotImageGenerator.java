package src;

import src.image.ImageWriter;
import src.image.PPMImageWriter;
import src.mandelbrot.ComplexNumber;
import src.mandelbrot.Mandelbrot;

import java.io.IOException;

public class MandelbrotImageGenerator {
    private int width;
    private int height;

    private double leftBound, lowerBound;
    private double rightBound, upperBound;

    private Mandelbrot mandelbrot;
    private int colorDepth;
    private int maxIterations;

    private ImageWriter writer;
    private int[][] palette;
    private int[][] paletteIndices;
    private int[][][] imageData;

    public MandelbrotImageGenerator(int width, int height,
                                    double leftBound, double lowerBound, double rightBound, double upperBound,
                                    int colorDepth) {
        this.width = width;
        this.height = height;
        this.leftBound = leftBound;
        this.lowerBound = lowerBound;
        this.rightBound = rightBound;
        this.upperBound = upperBound;
        this.colorDepth = colorDepth;
        this.maxIterations = colorDepth;

        mandelbrot = new Mandelbrot(width, height, leftBound, lowerBound, rightBound, upperBound,
                maxIterations - 1);
        
        palette = new int[colorDepth][3];
        paletteIndices = new int[width][height];
        imageData = new int[width][height][3];
        writer = new PPMImageWriter(colorDepth);
    }

    public MandelbrotImageGenerator(int width, int height, ComplexNumber center, double scale, int colorDepth) {
        /*
            Sets x bounds to [-scale + center.real, scale + center.real]
            Sets y bound to values that will not distort the image
        */
        double leftBound = scale * -1 + center.real();
        double rightBound = scale * 1 + center.real();
        double upperBound =  (rightBound - leftBound)/2 * height/width + center.imaginary();
        double lowerBound = -(rightBound - leftBound)/2 * height/width + center.imaginary();

        this.width = width;
        this.height = height;
        this.leftBound = leftBound;
        this.lowerBound = lowerBound;
        this.rightBound = rightBound;
        this.upperBound = upperBound;
        this.colorDepth = colorDepth;
        this.maxIterations = colorDepth;
        mandelbrot = new Mandelbrot(width, height, leftBound, lowerBound, rightBound, upperBound,
                maxIterations - 1);

        palette = new int[colorDepth][3];
        paletteIndices = new int[width][height];
        imageData = new int[width][height][3];
        writer = new PPMImageWriter(colorDepth);
    }

    // methods
    public void generateImage() {
        mandelbrot.countAllIterations();
    }
    
    public void generatePalette() {
    	for (int i = 0; i < colorDepth; i++) {
    		palette[i] = new int[] {i, i, i};
		}
    }
    
    public void applyPalette() {
        paletteIndices = mandelbrot.getIterationCounts();
        //generateHistogramPaletteIndices();
        
    	for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
            	imageData[x][y] = palette[ paletteIndices[x][y] ];
        	}
    	}
    	
    }

    public void writeImage() {
        try {
            writer.open("test.ppm");
            writer.setData(imageData);
            writer.write();
            writer.close();
        }
        catch (IOException e) {
            System.err.println("error while opening/writing to/closing image file");
            System.exit(1);
        }
    }
    
    // histogram coloring method
    private void generateHistogramPaletteIndices() {
        int total = 0;
        int[] numIterationsPerPixel = new int[maxIterations];
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                int i = mandelbrot.getIterationCounts()[x][y];
                numIterationsPerPixel[i]++;
                total++;
            }
        } 

        double[][] hue = new double[width][height];
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                int iteration = mandelbrot.getIterationCounts()[x][y];
                for (int i = 0; i < iteration; i++) {
                    hue[x][y] += (double) numIterationsPerPixel[i] / total;
                }
            }
        }

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                paletteIndices[x][y] = (int) (colorDepth * hue[x][y]);
            }
        }
    }

    public int[][][] getImageData() {
        return imageData;
    }

    public static void main(String[] args) {
        MandelbrotImageGenerator gen = new MandelbrotImageGenerator(192, 108,
                new ComplexNumber(-0.751095959125087, -0.116817186889238),
                1.0/50, 512);
        gen.generateImage();
        gen.generatePalette();
        gen.applyPalette();
        gen.writeImage();
    }
}
