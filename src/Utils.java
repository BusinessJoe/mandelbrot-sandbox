package src;

public class Utils {
	private static int[] HSVtoRGB(double[] hsv) {
        /*
        0 <= hue <= 360
        0 <= saturation <= 1
        0 <= value <= 1
        */
        double h = hsv[0];
        double s = hsv[1];
        double v = hsv[2];

        double c = v * s;
        double x = c * (1 - Math.abs(((h / 60) % 2) - 1));
        double m = v - c;

        double _r, _g, _b;
        if (h < 60) {
            _r = c;
            _g = x;
            _b = 0;
        }
        else if (h < 120) {
            _r = x;
            _g = c;
            _b = 0;
        }
        else if (h < 180) {
            _r = 0;
            _g = c;
            _b = x;
        }
        else if (h < 240) {
            _r = 0;
            _g = x;
            _b = c;
        }
        else if (h < 300) {
            _r = x;
            _g = 0;
            _b = c;
        }
        else {
            _r = c;
            _g = 0;
            _b = x;
        }

        int r, g, b;
        r = (int) (255 * (_r + m));
        g = (int) (255 * (_g + m));
        b = (int) (255 * (_b + m));

        return new int[]{r, g, b};
    }
    
    private static int[][] makeColorArray(int numColors) {
        int[][] colors = new int[numColors][3];

        for (int i = 0; i < numColors; i++) {
            int[] c = HSVtoRGB(new double[] {(360.0/numColors*i + 240) % 360, 1, 1});
            colors[i] = c;
        }

        return colors;
    }
}
