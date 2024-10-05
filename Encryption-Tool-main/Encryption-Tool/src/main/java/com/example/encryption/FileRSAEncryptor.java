package main.java.com.example.encryption;

import javax.crypto.Cipher;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
//import java.security.PrivateKey;
import java.security.PublicKey;

public class FileRSAEncryptor {

    private static final String ALGORITHM = "RSA";
    private static final int KEY_SIZE = 4096;

    // Generate an RSA key pair
    public KeyPair generateKeyPair() throws NoSuchAlgorithmException {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(ALGORITHM);
        keyPairGenerator.initialize(KEY_SIZE);
        return keyPairGenerator.generateKeyPair();
    }

    // Encrypt the file using the provided public key
    public void encryptFile(File inputFile, File outputFile, PublicKey publicKey) throws Exception {
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);

        try (FileInputStream inputStream = new FileInputStream(inputFile);
             FileOutputStream outputStream = new FileOutputStream(outputFile)) {
             
            // Read the input file
            byte[] inputBytes = new byte[(int) inputFile.length()];
            inputStream.read(inputBytes);

            // Encrypt the data
            byte[] outputBytes = cipher.doFinal(inputBytes);

            // Write the encrypted data to the output file
            outputStream.write(outputBytes);
        } catch (Exception e) {
            throw new Exception("Error during RSA encryption: " + e.getMessage(), e);
        }
    }
}