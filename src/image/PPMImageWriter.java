package src.image;

import java.io.IOException;

public class PPMImageWriter extends ImageWriter {
    private int width;
    private int height;
    private int colorDepth;
    private int sampleSize;

    public PPMImageWriter(int cdepth) {
        colorDepth = cdepth;
        if (colorDepth >= 256) {
            sampleSize = 2;
        }
        else {
            sampleSize = 1;
        }
    }

    public void write() throws IOException {
        if (file != null) {
            writeHeader();
            writeData();
        }
        else {
            throw new IOException("Cannot write to null file");
        }
    }

    public void setData(int[][][] data) {
        if (data[0][0].length != 3) {
            System.err.println("Expected three values per pixel");
        }
        else {
            this.width = data.length;
            this.height = data[0].length;
            this.byteData = flattenIntDataToByteData(data);
        }
    }

    private void writeHeader() throws IOException {
        String header = "P6\n" + width + " " + height + "\n" + colorDepth + "\n";
        file.write(header.getBytes());
    }

    private void writeData() throws IOException {
        System.out.println(byteData.length);
        file.write(byteData);
    }

    private byte[] flattenIntDataToByteData(int[][][] data) {
        byte[] dataBytes = new byte[width * height * 3 * sampleSize];
        int i = 0;
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                for (int k = 0; k < 3; k++) {
                    if (sampleSize == 2) {
                        dataBytes[i] = (byte) (data[x][y][k] >> 8);
                        i++;
                    }
                    dataBytes[i] = (byte) data[x][y][k];
                    i++;
                }
            }
        }
        return dataBytes;
    }
}
