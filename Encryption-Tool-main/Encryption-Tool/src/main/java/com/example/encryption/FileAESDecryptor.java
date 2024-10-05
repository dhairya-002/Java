package main.java.com.example.encryption;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

public class FileAESDecryptor {

    private static final String ALGORITHM = "AES";

    public void decryptFile(File inputFile, File outputFile, SecretKey secretKey) throws Exception {
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, secretKey);

        try (FileInputStream inputStream = new FileInputStream(inputFile);
             FileOutputStream outputStream = new FileOutputStream(outputFile)) {
             
            byte[] buffer = new byte[4096]; 
            int bytesRead;
            // Read and decrypt the data in chunks
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                byte[] outputBytes = cipher.update(buffer, 0, bytesRead);
                if (outputBytes != null) {
                    outputStream.write(outputBytes);
                }
            }
            // Finalize decryption
            byte[] outputBytes = cipher.doFinal();
            if (outputBytes != null) {
                outputStream.write(outputBytes);
            }
        } catch (Exception e) {
            throw new Exception("Error during file decryption: " + e.getMessage(), e);
        }
    }
}