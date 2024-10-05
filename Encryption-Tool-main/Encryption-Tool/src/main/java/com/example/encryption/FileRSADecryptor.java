package main.java.com.example.encryption;

import javax.crypto.Cipher;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.security.PrivateKey;

public class FileRSADecryptor {

    private static final String ALGORITHM = "RSA";

    // Decrypt the file using the provided private key
    public void decryptFile(File inputFile, File outputFile, PrivateKey privateKey) throws Exception {
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, privateKey);

        try (FileInputStream inputStream = new FileInputStream(inputFile);
             FileOutputStream outputStream = new FileOutputStream(outputFile)) {
             
            // Read the encrypted file
            byte[] inputBytes = new byte[(int) inputFile.length()];
            inputStream.read(inputBytes);

            // Decrypt the data
            byte[] outputBytes = cipher.doFinal(inputBytes);

            // Write the decrypted data to the output file
            outputStream.write(outputBytes);
        } catch (Exception e) {
            throw new Exception("Error during RSA decryption: " + e.getMessage(), e);
        }
    }
}