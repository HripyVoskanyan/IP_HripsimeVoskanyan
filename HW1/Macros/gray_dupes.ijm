dir = getDirectory("Choose a Source Directory");
list = getFileList(dir);
setBatchMode(true);

outputDir = "C:/AUA/Image Processing/HW1/";

// Define the array of specific filenames
specificFiles = newArray(
    "105-11.jpg", "106-11.jpg", "107-11.jpg", "108-11.jpg", "109-11.jpg", "110-11.jpg", "111-11.jpg", "112-11.jpg",
    "105-13.jpg", "106-13.jpg", "107-13.jpg", "108-13.jpg", "109-13.jpg", "110-13.jpg", "111-13.jpg", "112-13.jpg",
    "105-14.jpg", "106-14.jpg", "107-14.jpg", "108-14.jpg", "109-14.jpg", "110-14.jpg", "111-14.jpg", "112-14.jpg"
);

for (i = 0; i < list.length; i++) {
    if (isInArray(specificFiles, list[i])) {  // Use the custom function
        showProgress(i+1, list.length);
        open(dir + list[i]);
        run("HSB Stack");

        stack = getImageID();

        // Save Hue as PNG with '0' digit
        selectImage(stack);
        setSlice(1);
        saveAs("PNG", outputDir + "hue/" + list[i] + "_0.png");

        // Save Saturation as PNG with '1' digit
        selectImage(stack);
        setSlice(2);
        saveAs("PNG", outputDir + "sat/" + list[i] + "_1.png");

        // Save Brightness as PNG with '2' digit
        selectImage(stack);
        setSlice(3);
        saveAs("PNG", outputDir + "val/" + list[i] + "_2.png");

        close();
    }
}
setBatchMode(false);

// Custom function to check if an item is in the array
function isInArray(arr, item) {
    for (j = 0; j < arr.length; j++) {
        if (arr[j] == item) {
            return true;
        }
    }
    return false;
}
