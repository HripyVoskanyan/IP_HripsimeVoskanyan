# Homework 1

* Task 1: Run gray_dupes macro to create the hue/val/sat folders with their corresponding pictures.
* Task 2: Run Hist_macro to create the histogram data CSV files further to be integrated into one Excel. Then run with the right parameters (pay attention to comments) Write_into_csv.py to write this data automatically into Hist_HSV.xlsx
* Task 3: Run Find_Threshold.py to approximately find the minimum and maximum thresholds and write them into Hist_HSV.xlsx.
* Task 4: Created the SV Plugin to create Saturation*Brightness pictures. Then, run the convert_to_sv macro to automatically create all the pictures and save them in the sv folder. Then run Write_into_csv.py and Find_Threshold.py with the right parameters to fill in the excel file.
* Task 5. Created the Extract_Face_Pixels plugin and experimented with different thresholds, mainly using 50-100 and 100-200. 50-100 worked only for the first images of hue folder, that is why I automated again with save_faces macro with constant 100-200 thresholds.


