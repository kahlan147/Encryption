import java.io.*;
import java.math.BigInteger;

public class ReadFile {
    /*
    De tweede applicatie leest een file die een private sleutel bevat en een andere file (die we even INPUT.EXT zullen
    noemen).   Verder wordt de naam van de ondertekenaar (bijv. Lk) opgegeven. Als output wordt een file met de naam
    INPUT(SIGNEDBYLK).EXT geproduceerd met de volgende inhoud: de lengte (in bytes) van de digitale handtekening, de
    digitale handtekening (gemaakt met het algoritme 'SHA1withRSA') en de inhoud van file INPUT.EXT.
     */

    private final static String INPUTPATH = "Opdracht1/src/Input.txt";
    private final static String PRIVATEKEYPATH = "Opdracht1/src/SignatureKey_private.txt";

    public static void main(String[] args){
        String name = "Lk";
        ReadFile readFile = new ReadFile();
        BigInteger privateKey = readFile.readKey();
        if(privateKey == null){return;}
        String content = readFile.readInput();
        String signature = readFile.encryptText(name, privateKey);
        readFile.writeInputToText(name, signature, content);
    }

    private String encryptText(String content, BigInteger key){
        RSA rsa = new RSA();
        return rsa.encryptWithKey(new BigInteger(content.getBytes()), key).toString();
    }

    private String readInput(){
        StringBuilder text = new StringBuilder();
        try (FileReader instream = new FileReader(INPUTPATH);
             BufferedReader buffer = new BufferedReader(instream)) {
            long length = 0;
            String line;
            while ((line = buffer.readLine()) != null) {
                text.append(line + "\n");
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return text.toString();
    }

    private void writeInputToText(String name, String signature, String content) {

        String path = "Opdracht1/src/INPUT(SIGNEDBY" + name + ").txt";
        FileWriter fileWriter = null;
        BufferedWriter bufferedWriter = null;

        try {
            fileWriter = new FileWriter(path);
            bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write(signature.length() + "\n");
            bufferedWriter.write(signature + "\n");
            bufferedWriter.write(content + "\n");

        }
        catch (IOException e) {
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

    private BigInteger readKey(){
        String path = PRIVATEKEYPATH;
        FileReader fileReader = null;
        BufferedReader bufferedReader = null;

        try{
            fileReader = new FileReader(path);
            bufferedReader = new BufferedReader(fileReader);
            return new BigInteger(bufferedReader.readLine());
        }
        catch(IOException e) {
            System.out.println("error");
        }
        return null;
    }
}
