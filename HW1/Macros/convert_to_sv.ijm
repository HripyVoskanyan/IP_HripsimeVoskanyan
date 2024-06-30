
inputDir = "C:/AUA/Image Processing/HW1/dataset/";
outputDir = "C:/AUA/Image Processing/HW1/sv/";


File.makeDirectory(outputDir);

// Get a list of all files in the input directory
listOfFiles = getFileList(inputDir);


setBatchMode(true);

// Process each file in the directory
for (i = 0; i < listOfFiles.length; i++) {

    imagePath = inputDir + listOfFiles[i];
    

    if (endsWith(imagePath, ".jpg") || endsWith(imagePath, ".png") || endsWith(imagePath, ".tif")) {
        open(imagePath);
        // added this plugin with this name to PLugin/Filters
        run("SV Plugin");
        
        processedImage = getTitle();

        outputPath = outputDir + "SV_" + listOfFiles[i];
        
        saveAs("png", outputPath);
        print("Processed and saved: " + outputPath);
        
        close();
    }
}

// Disable batch mode to restore normal operations
setBatchMode(false);

print("All images processed and saved successfully.");
