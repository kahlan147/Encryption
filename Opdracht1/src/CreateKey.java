import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;


/*
De eerste applicatie genereert een publieke sleutel van 1024 bits volgens het RSA-algoritme en de bijbehorende
private sleutel en schrijft beide sleutels in afzonderlijke files weg. Je kunt hier een variant van het  programma
RSATest (listing 20 in [1]) voor gebruiken.
 */
public class CreateKey {

    public static void main(String[] args) {
        int N = 1024;
        RSA key = new RSA(N);

        System.out.println(key);

        new CreateKey().writeKeyToText("test", key.publicKey.toString(), key.privateKey.toString());


    }

    private void writeKeyToText(String path, String publicKey, String privateKey){

        FileWriter fileWriter = null;
        BufferedWriter bufferedWriter = null;

        try {
            fileWriter = new FileWriter(path + "_public.txt");
            bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write(publicKey);

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
