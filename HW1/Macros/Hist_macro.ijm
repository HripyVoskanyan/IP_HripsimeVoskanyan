
subDirs = newArray("hue", "sat", "val");
baseDir = "C:/AUA/Image Processing/HW1/";  

setBatchMode(true);

for(s = 0; s< subDirs.length; s++){
	dir = baseDir + subDirs[s] + "/";  // Construct the path to the subdirectory
            fileList = getFileList(dir);

	for (i = 0; i < fileList.length; i++) {
	if (endsWith(fileList[i], ".png")) {

	open(dir + fileList[i]);


            run("8-bit");


            getHistogram(values, counts, 256);


            f  = File.open(dir + fileList[i].replace(".png", "_histogram.csv"));

            for (j = 0; j < counts.length; j++) {
        		print(f, counts[j]);
            }
            File.close(f);
}
}
}
setBatchMode(false);
