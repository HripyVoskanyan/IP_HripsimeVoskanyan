import ij.*;
import ij.process.*;
import ij.gui.*;
import ij.plugin.filter.*;

public class Extract_Face_Pixels implements PlugInFilter {
    ImagePlus imp;

    public int setup(String args, ImagePlus imp) {
        this.imp = imp;
        return DOES_ALL; // This flag supports all image types.
    }

    public void run(ImageProcessor ip) {
        // Convert to RGB if not already in RGB
        if (ip instanceof ColorProcessor) {
            // Already RGB, no conversion needed
        } else {
            imp.setProcessor(imp.getTitle(), ip.convertToRGB());
            ip = imp.getProcessor();
        }

        int width = ip.getWidth();
        int height = ip.getHeight();

        // Set specific numerical thresholds
        int thresholdLow = 100;  // also works with 50-100 for the first couple of images
        int thresholdHigh = 200; 

        // Process image pixels
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int[] rgb = ip.getPixel(x, y, null);
                int value = rgb[0]; // Assuming R=G=B in grayscale converted images

                if (value >= thresholdLow && value <= thresholdHigh) {
                    ip.putPixel(x, y, new int[]{255, 255, 255}); // Set to white
                } else {
                    ip.putPixel(x, y, new int[]{0, 0, 0}); // Set to black
                }
            }
        }

        imp.updateAndDraw(); // Update the image display
    }
}
