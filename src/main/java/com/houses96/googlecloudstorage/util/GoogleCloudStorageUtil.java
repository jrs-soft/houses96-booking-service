package com.houses96.googlecloudstorage.util;

public class GoogleCloudStorageUtil {

    /**
     * Extracts the file name from the Google Cloud Storage URL.
     *
     * @param url the full Google Cloud Storage URL
     * @return the extracted file name, or null if the URL is invalid
     */
    public static String extractFileNameFromUrl(String url) {
        if (url == null || url.isEmpty()) {
            return null; // handle null or empty URL case
        }

        // Split the URL by '/' and return the last part (file name)
        String[] parts = url.split("/");

        // Check if the URL contains at least the bucket and file name
        if (parts.length < 5) {
            return null; // invalid URL format
        }

        return parts[parts.length - 1]; // the file name is the last part of the URL
    }

    public static void main(String[] args) {
        String url = "https://storage.googleapis.com/my-bucket/my-file.jpg";
        String fileName = extractFileNameFromUrl(url);

        System.out.println("Extracted file name: " + fileName); // Output: my-file.jpg
    }

}
