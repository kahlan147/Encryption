import java.io.*;
import java.math.BigInteger;

public class ReadFile {
    /*
    De tweede applicatie leest een file die een private sleutel bevat en een andere file (die we even INPUT.EXT zullen
    noemen).   Verder wordt de naam van de ondertekenaar (bijv. Lk) opgegeven. Als output wordt een file met de naam
    INPUT(SIGNEDBYLK).EXT geproduceerd met de volgende inhoud: de lengte (in bytes) van de digitale handtekening, de
    digitale handtekening (gemaakt met het algoritme 'SHA1withRSA') en de inhoud van file INPUT.EXT.
     */

    public static void main(String[] args){
        String name = "Lk";
        ReadFile readFile = new ReadFile();
        readFile.readInput();
    }

    private void writeInputToText(String name, String content) {

        String path = "Opdracht1/src/INPUT(SIGNEDBY" + name + ").txt";
        FileWriter fileWriter = null;
        BufferedWriter bufferedWriter = null;

        try {
            fileWriter = new FileWriter(path);
            bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write(content);

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

    private void readInput(){
        String path = "Opdracht1/src/SignatureKey_private.txt";
        FileReader fileReader = null;
        BufferedReader bufferedReader = null;

        try{
            fileReader = new FileReader(path);
            bufferedReader = new BufferedReader(fileReader);
            System.out.println(bufferedReader.readLine());
        }
        catch(IOException e) {
            System.out.println("error");
        }
    }
}
