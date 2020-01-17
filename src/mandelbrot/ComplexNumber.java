package src.mandelbrot;

public class ComplexNumber {
    private double real;
    private double imaginary;

    public ComplexNumber(double r, double i) {
        real = r;
        imaginary = i;
    }

    public void set(ComplexNumber z) {
        real = z.real;
        imaginary = z.imaginary;
    }

    public void add(ComplexNumber z) {
        set(add(this, z));
    }

    public void subtract(ComplexNumber z) {
        set(subtract(this, z));
    }

    public void multiply(ComplexNumber z) {
        set(multiply(this, z));
    }

    public void mandelbrotIteration(ComplexNumber c) {
        set(mandelbrotIteration(this, c));
    }

    public double real() {
        return real;
    }

    public double imaginary() {
        return imaginary;
    }

    public static ComplexNumber add(ComplexNumber z1, ComplexNumber z2) {
        return new ComplexNumber(z1.real + z2.real, z1.imaginary + z2.imaginary);
    }

    public static ComplexNumber subtract(ComplexNumber z1, ComplexNumber z2) {
        return new ComplexNumber(z1.real - z2.real, z1.imaginary - z2.imaginary);
    }

    public static ComplexNumber multiply(ComplexNumber z1, ComplexNumber z2) {
        double _real = z1.real * z2.real - z1.imaginary * z2.imaginary;
        double _imaginary = z1.real * z2.imaginary + z2.real * z1.imaginary; 
        return new ComplexNumber(_real, _imaginary);
    }

    public static ComplexNumber mandelbrotIteration(ComplexNumber z, ComplexNumber c) {
        double _real = z.real * z.real - z.imaginary * z.imaginary + c.real;
        double _imaginary = 2 * z.real * z.imaginary + c.imaginary;
        return new ComplexNumber(_real, _imaginary);
    }

    public String toString() {
        return real + " " + imaginary;
    }
}
