import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigInteger;


/*
De eerste applicatie genereert een publieke sleutel van 1024 bits volgens het RSA-algoritme en de bijbehorende
private sleutel en schrijft beide sleutels in afzonderlijke files weg. Je kunt hier een variant van het  programma
RSATest (listing 20 in [1]) voor gebruiken.
 */
public class CreateKey {

    public static void main(String[] args) {
        new CreateKey(1024);
    }

    public CreateKey(int bits){
        RSA key = new RSA(bits);
        System.out.println("\n------------------------------------- \nThe keys are: ");
        System.out.println(key);
        System.out.println("------------------------------------- \n");
        writeKeyToText(key.publicKey.toString(), key.privateKey.toString());

        RSA polka = new RSA(bits);
         String s = "test";
         byte[] bytes = s.getBytes();
         BigInteger message = new BigInteger(bytes);

        BigInteger encrypt = polka.encryptWithKey(message, key.publicKey);
        BigInteger decrypt = polka.decryptWithKey(encrypt, key.privateKey);
        System.out.println("message   = " + message);
        System.out.println("encrypted = " + encrypt);
        System.out.println("decrypted = " + decrypt);

        System.out.println("test = " + new String(decrypt.toByteArray()));
    }

    private void writeKeyToText(String publicKey, String privateKey){

        String path = "Opdracht1/src/SignatureKey";
        FileWriter fileWriter = null;
        BufferedWriter bufferedWriter = null;

        try {
            fileWriter = new FileWriter(path + "_public.txt");
            bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write(publicKey);

            bufferedWriter.close();
            fileWriter.close();

            fileWriter = new FileWriter(path + "_private.txt");
            bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write(privateKey);
        }
        catch(IOException e) {
            System.out.println("error");
        }
        finally {

            try {

                if (bufferedWriter != null)
                    bufferedWriter.close();

                if (fileWriter != null)
                    fileWriter.close();

            } catch (IOException ex) {

                ex.printStackTrace();

            }
        }


    }
}
