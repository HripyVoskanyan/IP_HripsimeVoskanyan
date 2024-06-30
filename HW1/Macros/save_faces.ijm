// Define directories
baseDir = "C:/AUA/Image Processing/HW1/";
inputDir = baseDir + "hue/";
outputDir = baseDir + "face/";

File.makeDirectory(outputDir);

listOfFiles = getFileList(inputDir);

setBatchMode(true);

for (i = 0; i < listOfFiles.length; i++) {
    fileName = listOfFiles[i];
    if (endsWith(fileName, ".png")) { // Check if the file is a PNG image
        // Open the image
        open(inputDir + fileName);

        // Run the plugin, added this plugin in the Plugin/Filters
        run("Extract Face Pixels");


        saveAs("PNG", outputDir + "processed_" + fileName + "1");

        // Close the image
        close();
    }
}

setBatchMode(false);

print("All images processed and saved in the 'face' folder.");
