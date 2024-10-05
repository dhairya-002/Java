package main.java.com.example.cli;

import javax.crypto.SecretKey;
import main.java.com.example.encryption.FileAESEncryptor;
import main.java.com.example.encryption.EncryptionUtils;
import main.java.com.example.encryption.FileAESDecryptor;
import main.java.com.example.encryption.FileRSAEncryptor;
import main.java.com.example.encryption.FileRSADecryptor;
import java.io.File;
import java.util.Scanner;
import java.security.KeyPair;
import java.security.PublicKey;
import java.security.PrivateKey;


public class CommandLineInterface {
    private static final Scanner scanner = new Scanner(System.in);
    private final FileAESEncryptor fileEncryptor = new FileAESEncryptor();
    private final FileAESDecryptor fileDecryptor = new FileAESDecryptor();

    public void start() {
        System.out.println("Welcome to the File Encryption Tool");
        System.out.println("Choose an option: ");
        System.out.println("1. Encrypt a file");
        System.out.println("2. Decrypt a file");
        System.out.print("Enter your choice: ");
        
        int choice = getUserChoice();

        switch (choice) {
            case 1:
                chooseAlgorithmAndEncrypt();
                break;
            case 2:
                chooseAlgorithmAndDecrypt();
                break;
            default:
                System.out.println("Invalid choice. Please try again.");
        }
    }

    private int getUserChoice() {
        while (true) {
            try {
                int choice = scanner.nextInt();
                scanner.nextLine(); 
                return choice;
            } catch (Exception e) {
                System.out.println("Invalid input. Please enter a number.");
                scanner.nextLine(); 
            }
        }
    }

    private void chooseAlgorithmAndEncrypt() {
        System.out.println("Choose an Algorithm:");
        System.out.println("1. AES");
        System.out.println("2. RSA");
        System.out.print("Enter your choice: ");
        
        int algorithmChoice = getUserChoice();
        switch (algorithmChoice) {
            case 1:
                encryptFileWithAES();
                break;
            case 2:
                encryptFileWithRSA();
                break;
            default:
                System.out.println("Invalid choice. Please try again.");
        }
    }

    private void chooseAlgorithmAndDecrypt() {
        System.out.println("Choose an Algorithm:");
        System.out.println("1. AES");
        System.out.println("2. RSA");
        System.out.print("Enter your choice: ");
        
        int algorithmChoice = getUserChoice();
        switch (algorithmChoice) {
            case 1:
                decryptFileWithAES();
                break;
            case 2:
                decryptFileWithRSA();
                break;
            default:
                System.out.println("Invalid choice. Please try again.");
        }
    }

    private void encryptFileWithAES() {
        try {
            System.out.print("Enter the path of the file to encrypt: ");
            String inputPath = scanner.nextLine();
            File inputFile = new File(inputPath);

            if (!inputFile.exists() || !inputFile.isFile()) {
                System.err.println("Input file does not exist or is not a valid file.");
                return;
            }

            System.out.print("Enter the path for the encrypted output file: ");
            String outputPath = scanner.nextLine();
            File outputFile = new File(outputPath);

            // Use the FileEncryptor instance to generate the key and encrypt the file
            SecretKey secretKey = fileEncryptor.generateKey();
            fileEncryptor.encryptFile(inputFile, outputFile, secretKey);
            
            String encodedKey = EncryptionUtils.encodeKey(secretKey);
            System.out.println("File encrypted successfully!");
            System.out.println("Secret Key (store this securely): " + encodedKey);
        } catch (Exception e) {
            System.err.println("Error during encryption: " + e.getMessage());
        }
    }

    private void decryptFileWithAES() {
        try {
            System.out.print("Enter the path of the file to decrypt: ");
            String inputPath = scanner.nextLine();
            File inputFile = new File(inputPath);

            if (!inputFile.exists() || !inputFile.isFile()) {
                System.err.println("Input file does not exist or is not a valid file.");
                return;
            }

            System.out.print("Enter the path for the decrypted output file: ");
            String outputPath = scanner.nextLine();
            File outputFile = new File(outputPath);

            System.out.print("Enter the secret key: ");
            String keyString = scanner.nextLine();
            SecretKey secretKey = EncryptionUtils.decodeKey(keyString);

            fileDecryptor.decryptFile(inputFile, outputFile, secretKey);
            System.out.println("File decrypted successfully!");
        } catch (Exception e) {
            System.err.println("Error during decryption: " + e.getMessage());
        }
    }

    private void encryptFileWithRSA() {
        try {
            System.out.print("Enter the path of the file to encrypt: ");
            String inputPath = scanner.nextLine();
            File inputFile = new File(inputPath);

            if (!inputFile.exists() || !inputFile.isFile()) {
                System.err.println("Input file does not exist or is not a valid file.");
                return;
            }

            System.out.print("Enter the path for the encrypted output file: ");
            String outputPath = scanner.nextLine();
            File outputFile = new File(outputPath);

            if(!outputFile.getParentFile().exists() || !outputFile.getParentFile().isDirectory()){
                System.err.println("Ouput file path is not a valid directory");
                return;
            }

            // Generate RSA key pair
            FileRSAEncryptor rsaEncryptor = new FileRSAEncryptor();
            KeyPair keyPair = rsaEncryptor.generateKeyPair();
            PublicKey publicKey = keyPair.getPublic();

            // Encrypt the file
            rsaEncryptor.encryptFile(inputFile, outputFile, publicKey);
            String encodedPublicKey = EncryptionUtils.encodePublicKey(publicKey);
            String encodedPrivateKey = EncryptionUtils.encodePrivateKey(keyPair.getPrivate());
            System.out.println("File encrypted successfully!");
            System.out.println("Public Key (store this securely): " + encodedPublicKey);
            System.out.println("Private Key (store this securely): " + encodedPrivateKey);
        } catch (Exception e) {
            System.err.println("Error during encryption: " + e.getMessage());
        }
    }

    private void decryptFileWithRSA() {
        try {
            System.out.print("Enter the path of the file to decrypt: ");
            String inputPath = scanner.nextLine();
            File inputFile = new File(inputPath);

            if (!inputFile.exists() || !inputFile.isFile()) {
                System.err.println("Input file does not exist or is not a valid file.");
                return;
            }

            System.out.print("Enter the path for the decrypted output file: ");
            String outputPath = scanner.nextLine();
            File outputFile = new File(outputPath);

            if(!outputFile.getParentFile().exists() || !outputFile.getParentFile().isDirectory()){
                System.err.println("Ouput file path is not a vaild directory");
                return;
            }

            System.out.print("Enter the private key: ");
            String keyString = scanner.nextLine();
            PrivateKey privateKey = EncryptionUtils.decodePrivateKey(keyString);

            FileRSADecryptor rsaDecryptor = new FileRSADecryptor();
            rsaDecryptor.decryptFile(inputFile, outputFile, privateKey);
            System.out.println("File decrypted successfully!");
        } catch (Exception e) {
            System.err.println("Error during decryption: " + e.getMessage());
        }
    }
}