import ij.IJ;
import ij.ImagePlus;
import ij.plugin.PlugIn;
import ij.process.ImageProcessor;
import java.awt.Color;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.TreeSet;
import java.util.HashMap;
import java.util.Map;

public class FaceColor_Analysis implements PlugIn {

    @Override
    public void run(String arg) {
        String folderPath = IJ.getDirectory("Choose the face images folder");

        // Collect unique face-specific colors
        TreeSet<Integer> uniqueColors = new TreeSet<>();

        File folder = new File(folderPath);
        for (File file : folder.listFiles()) {
            if (file.isFile() && file.getName().endsWith(".png")) {
                ImagePlus image = IJ.openImage(file.getAbsolutePath());
                ImageProcessor processor = image.getProcessor();

                int width = processor.getWidth();
                int height = processor.getHeight();

                for (int y = 0; y < height; y++) {
                    for (int x = 0; x < width; x++) {
                        int pixel = processor.getPixel(x, y);
                        Color color = new Color(pixel);

                        if (color.getRed() != 0 || color.getGreen() != 0 || color.getBlue() != 0) {
                            uniqueColors.add(pixel);
                        }
                    }
                }
            }
        }


        int[] count = new int[256];
        int[] min = new int[256];
        int[] max = new int[256];
        double[] mean = new double[256];
        double[] mean2 = new double[256];

        for (int i = 0; i < 256; i++) {
            min[i] = Integer.MAX_VALUE;
            max[i] = Integer.MIN_VALUE;
        }

        Map<Integer, Integer> sum = new HashMap<>();
        Map<Integer, Integer> sum2 = new HashMap<>();

        // Calculate the metrics
        for (int colorInt : uniqueColors) {
            Color color = new Color(colorInt);
            int R = color.getRed();
            int G = color.getGreen();
            int B = color.getBlue();
            int averageRB = (R + B) / 2;

            count[G]++;
            min[G] = Math.min(min[G], averageRB);
            max[G] = Math.max(max[G], averageRB);
            sum.put(G, sum.getOrDefault(G, 0) + averageRB);
            sum2.put(G, sum2.getOrDefault(G, 0) + averageRB * averageRB);
        }

        for (int i = 0; i < 256; i++) {
            if (count[i] > 0) {
                mean[i] = sum.get(i) / (double) count[i];
                mean2[i] = sum2.get(i) / (double) count[i];
            } else {
                min[i] = 0;
                max[i] = 0;
                mean[i] = 0.0;
                mean2[i] = 0.0;
            }
        }

        // Save the results to a CSV file to easily copy them to our designated file
        String csvFilePath = "C:\\AUA\\Image Processing\\HW2\\face_color_analysis.csv";
        try (FileWriter csvWriter = new FileWriter(csvFilePath)) {
            csvWriter.append("Green,Count,Min,Max,Mean,Mean2\n");
            for (int i = 0; i < 256; i++) {
                csvWriter.append(i + "," + count[i] + "," + min[i] + "," + max[i] + "," + mean[i] + "," + mean2[i] + "\n");
            }
            IJ.log("Results saved to " + csvFilePath);
        } catch (IOException e) {
            IJ.error("Error saving results to CSV file: " + e.getMessage());
        }
    }
}
