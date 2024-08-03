# Homework 3

* Task 1: By Comparing the results of my manual and automated selections, we can see that automated selections, despite being different, are quite on point and very good ovals for face.
* Algorithm:
* * Image Loading - we load the images one by one and convert them to 8-bit grayscale.
  * Preprocessing - then we apply Gaussian blurring to reduce noise
  * Thresholding - we apply thresholding to create a binary mask
  * Inversion - after that, we invert the binary mask of the previous step to improve the component detection
  * ROI Detection - in this step, we detect regions of interest using ThresholdToSelection PlugIn
  * Output - then we identify the largest region area and calculate the oval dimensions, after which it gets saved .csv file to be copied to Excel.
 
* Bonus Task: I adjusted the thresholds to 20 - 60 to get the regions for the new darker images better. 
