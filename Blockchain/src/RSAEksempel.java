import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import javax.crypto.Cipher;

public class RSAEksempel {
    public static void main(String [] args) throws Exception {
        // Get an instance of the RSA key generator
        KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");

        // Generate the keys
        KeyPair myPair = kpg.generateKeyPair();

        // Get an instance of the Cipher for RSA encryption/decryption
        Cipher desCipher = Cipher.getInstance("RSA/ECB/OAEPWithSHA-256AndMGF1Padding");

        // Initiate the Cipher, telling it that it is going to Encrypt, giving it the public key
        desCipher.init(Cipher.ENCRYPT_MODE, myPair.getPublic());
        // Create a secret message
        byte[] text = "No body can see me.".getBytes("UTF8");
        // Cipher it
        byte[] textEncrypted = desCipher.doFinal(text);
        // Write the message to an data file
        Path filePath = Paths.get("C:\\Journal\\RSAEncrypted.dat");
        Files.write(filePath, textEncrypted);

        // Reads the data file and saves it in fileContent.
        byte[] fileContents =  Files.readAllBytes(filePath);
        // Initiate the decipher
        desCipher.init(desCipher.DECRYPT_MODE, myPair.getPrivate());
        // Decipher the message
        byte[] textDecrypted = desCipher.doFinal(fileContents);
        // Converts the bytes to a String and prints it
        String aString = new String(textDecrypted);
        System.out.println(aString);
    }
}