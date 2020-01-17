package src;

import src.mandelbrot.ComplexNumber;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;

public class MandelbrotGUI implements ActionListener {
	private int width;
    private int height;
    private ComplexNumber center;
    private double scale;
    private JFrame frame;
    private JPanel imagePanel;
    private JPanel accessPanel;
    private JLabel mandelbrotLabel;
    private JButton zoomInButton;
    private JButton zoomOutButton;
    private JLabel scaleLabel;
	
	public MandelbrotGUI(int width, int height, double scale) {
		this.width = width;
		this.height = height;
		this.scale = scale;
		center = new ComplexNumber(0.360240443437614, -0.6413130610648);

        frame = new JFrame();
        imagePanel = new JPanel();
        imagePanel.setBounds(0, 0, width-200, height);
        accessPanel = new JPanel();
        accessPanel.setLayout(new BoxLayout(accessPanel, BoxLayout.PAGE_AXIS));
        accessPanel.setBounds(width-200, 0, width, height);
        
        mandelbrotLabel = new JLabel();
        imagePanel.add(mandelbrotLabel);
        
        zoomInButton = new JButton("Zoom in");
        zoomInButton.setActionCommand("Zoom 0.5");
        zoomInButton.addActionListener(this);		
        zoomInButton.setVisible(true);
        accessPanel.add(zoomInButton);
        
        zoomOutButton = new JButton("Zoom out");
        zoomOutButton.setActionCommand("Zoom 2");
        zoomOutButton.addActionListener(this);
        zoomOutButton.setVisible(true);		
        accessPanel.add(zoomOutButton);
        
        scaleLabel = new JLabel(Double.toString(scale));
        accessPanel.add(scaleLabel);
        
        frame.add(imagePanel);
        frame.add(accessPanel);
        
        frame.setSize(width, height);
        frame.setLayout(null);  
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        redrawImage();
	}

	public void redrawImage() {
		MandelbrotImageGenerator m = new MandelbrotImageGenerator(width, height, center, scale, 255);
		m.generateImage();
	    m.generatePalette();
	    m.applyPalette();
	    
	    BufferedImage img = getImageFromImageData(m.getImageData(), width, height);
	    mandelbrotLabel.setIcon(new ImageIcon(img));
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().startsWith("Zoom")) {
			double multiplier = Double.parseDouble(e.getActionCommand().split("\\s+")[1]);
			scale *= multiplier;
			scaleLabel.setText(Double.toString(scale));
			redrawImage();
		}
	}

    private static BufferedImage getImageFromImageData(int[][][] imageData, int width, int height) {
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);
        WritableRaster raster = image.getRaster();
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                raster.setPixel(x, y, imageData[x][y]);
            }
        }
        return image;
    }

    public static void main(String[] args) {
        new MandelbrotGUI(1920/2, 1080/2, 4);
    }
}
