// Define directories
baseDir = "C:/AUA/Image Processing/HW1/";
inputDir = baseDir + "hue/";
outputDir = baseDir + "face/";

// Ensure the output directory exists
File.makeDirectory(outputDir);

// Get a list of all files in the input directory
listOfFiles = getFileList(inputDir);

// Enable batch mode for faster processing
setBatchMode(true);

// Process each file
for (i = 0; i < listOfFiles.length; i++) {
    fileName = listOfFiles[i];
    if (endsWith(fileName, ".png")) { // Check if the file is a PNG image
        // Open the image
        open(inputDir + fileName);

        // Run the plugin
        run("Extract Face Pixels");

        // Save the processed image in the output directory
        saveAs("PNG", outputDir + "processed_" + fileName + "1");

        // Close the image
        close();
    }
}

// Disable batch mode
setBatchMode(false);

// Display completion message
print("All images processed and saved in the 'face' folder.");
