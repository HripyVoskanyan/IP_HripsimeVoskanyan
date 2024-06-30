import ij.*;
import ij.process.*;
import ij.plugin.filter.*;
import java.awt.Color;  // Import statement for java.awt.Color

public class SV_Plugin implements PlugInFilter {
    ImagePlus imp;

    public int setup(String arg, ImagePlus imp) {
        this.imp = imp;
        return DOES_RGB;  // This ensures the plugin only processes RGB images.
    }

    public void run(ImageProcessor ip) {
        int width = ip.getWidth();
        int height = ip.getHeight();

        // New image to store the product of saturation and brightness
        ImageProcessor prodIp = new FloatProcessor(width, height);

        // Process each pixel
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int pixel = ip.getPixel(x, y);
                float[] hsb = Color.RGBtoHSB((pixel & 0xff0000) >> 16, (pixel & 0xff00) >> 8, pixel & 0xff, null);

                float saturation = hsb[1];
                float brightness = hsb[2];
                float product = saturation * brightness;

                prodIp.putPixelValue(x, y, product * 255);  // Scale to 0-255 range
            }
        }

        new ImagePlus("Saturation-Brightness Product", prodIp).show();
    }
}
