package com.dhiway.Utilities;

import java.io.File;
import java.io.IOException;

import com.google.common.io.Files;

public class Screenshot {

    // Method to create a folder and save the screenshot
    public static void saveScreenshot(File screen, String destinationPath) {
        File destinationFile = new File(destinationPath);
        File destinationFolder = destinationFile.getParentFile(); // Get the folder path

        // Check if the folder exists, if not, create it
        if (!destinationFolder.exists()) {
            destinationFolder.mkdirs(); // Create the folder(s)
        }

        try {
            Files.copy(screen, destinationFile); // Copy the file to the destination
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
