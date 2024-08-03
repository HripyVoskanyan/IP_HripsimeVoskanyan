import ij.IJ;
import ij.ImagePlus;
import ij.plugin.PlugIn;
import ij.process.ImageProcessor;
import ij.plugin.filter.GaussianBlur;
import ij.plugin.filter.ThresholdToSelection;
import ij.plugin.filter.ParticleAnalyzer;
import ij.measure.Measurements;
import ij.measure.ResultsTable;
import ij.gui.Roi;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Auto_Oval_Selection_All implements PlugIn {

    @Override
    public void run(String arg) {
        String directoryPath = "C:/AUA/Image Processing/HW3/dataset";  
        File dir = new File(directoryPath);
        if (!dir.exists() || !dir.isDirectory()) {
            IJ.error("Directory not found: " + directoryPath);
            return;
        }

        
        String csvFilePath = "C:/AUA/Image Processing/HW3/results.csv";  
        try (FileWriter csvWriter = new FileWriter(csvFilePath)) {
            csvWriter.append("Image,X,Y,Width,Height\n");

            // Process each image in the directory seperately
            for (File file : dir.listFiles()) {
                if (file.isFile() && file.getName().endsWith(".jpg")) {
                    processImage(file, csvWriter);
                }
            }

            IJ.log("Results saved to " + csvFilePath);
        } catch (IOException e) {
            IJ.error("Error saving results to CSV file: " + e.getMessage());
        }
    }

    private void processImage(File file, FileWriter csvWriter) throws IOException {
      
        ImagePlus image = IJ.openImage(file.getAbsolutePath());
        if (image == null) {
            IJ.log("Failed to open image: " + file.getAbsolutePath());
            return;
        }
        ImageProcessor ip = image.getProcessor();

        // Preprocessing: Convert to grayscale and apply Gaussian blur
        ip = ip.convertToByte(true);
        GaussianBlur blur = new GaussianBlur();
        blur.blurGaussian(ip, 2);

        // Apply adaptive threshold to create binary mask
        ip.setAutoThreshold("Default", true, ImageProcessor.NO_LUT_UPDATE);
        IJ.run(image, "Convert to Mask", "");

        // Invert the binary mask
        IJ.run(image, "Invert", "");

        // Getting the ROI of the thresholded image
        ThresholdToSelection tts = new ThresholdToSelection();
        Roi roi = tts.convert(ip);
        if (roi == null) {
            IJ.log("No components found in image: " + file.getName());
            return;
        }

        // Set the ROI and measure it
        image.setRoi(roi);
        ResultsTable rt = new ResultsTable();
        ParticleAnalyzer pa = new ParticleAnalyzer(ParticleAnalyzer.EXCLUDE_EDGE_PARTICLES + ParticleAnalyzer.SHOW_RESULTS,
                Measurements.AREA + Measurements.CENTROID + Measurements.RECT, rt, 0, Double.MAX_VALUE);
        pa.analyze(image, ip);

        // Find the largest component
        double largestArea = 0;
        int largestIndex = -1;
        for (int i = 0; i < rt.getCounter(); i++) {
            double area = rt.getValue("Area", i);
            if (area > largestArea) {
                largestArea = area;
                largestIndex = i;
            }
        }

        if (largestIndex == -1) {
            IJ.log("No components found in image: " + file.getName());
            return;
        }

        // Get the bounding box of the largest component
        double x = rt.getValue("X", largestIndex);
        double y = rt.getValue("Y", largestIndex);
        double width = rt.getValue("Width", largestIndex);
        double height = rt.getValue("Height", largestIndex);

        // Save the parameters of the oval selection to compare with manual one
        csvWriter.append(file.getName()).append(",")
                .append(String.format("%.2f", x)).append(",")
                .append(String.format("%.2f", y)).append(",")
                .append(String.format("%.2f", width)).append(",")
                .append(String.format("%.2f", height)).append("\n");

        IJ.log("Processed image: " + file.getName());
    }

    public static void main(String[] args) {
        IJ.runPlugIn(Auto_Oval_Selection_All.class.getName(), "");
    }
}
