package src.image;

import java.io.FileOutputStream;
import java.io.IOException;

public abstract class ImageWriter {
    protected FileOutputStream file;
    protected byte[] byteData;

    public void open(String filename) throws IOException {
        file = new FileOutputStream(filename);
    }

    public void close() throws IOException {
        if (file != null) {
            file.close();
        }
        else {
            throw new IOException("Cannot close null file");
        }
    }

    public abstract void write() throws IOException;

    public abstract void setData(int[][][] imageData);
}
