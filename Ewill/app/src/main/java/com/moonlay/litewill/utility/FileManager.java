package com.moonlay.litewill.utility;

import com.microsoft.azure.storage.CloudStorageAccount;
import com.microsoft.azure.storage.Constants;
import com.microsoft.azure.storage.blob.CloudBlobClient;
import com.microsoft.azure.storage.blob.CloudBlobContainer;
import com.microsoft.azure.storage.blob.CloudBlockBlob;

import java.io.InputStream;
import java.security.SecureRandom;

public class FileManager {

    private static CloudBlobContainer getContainer() throws Exception {
        CloudStorageAccount storageAccount = CloudStorageAccount
                .parse(com.moonlay.litewill.config.Constants.STORAGE_CONNECTION_STRING);
        CloudBlobClient blobClient = storageAccount.createCloudBlobClient();
        CloudBlobContainer container = blobClient.getContainerReference("pictures");

        return container;
    }

    public static String UploadImage(InputStream image, int imageLength) throws Exception {
        CloudBlobContainer container = getContainer();

        container.createIfNotExists();

        String imageName = "Android-" + randomString(10) + ".jpg";

        CloudBlockBlob imageBlob = container.getBlockBlobReference(imageName);
        /*imageBlob.getProperties().setContentType("application/jpg");*/
        imageBlob.getProperties().setContentType("image/jpeg");
        imageBlob.upload(image, imageLength);

        return imageName;
    }

    static final String validChars = "abcdefghijklmnopqrstuvwxyz";
    static SecureRandom rnd = new SecureRandom();

    static String randomString(int len) {
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++)
            sb.append(validChars.charAt(rnd.nextInt(validChars.length())));
        return sb.toString();
    }
}
