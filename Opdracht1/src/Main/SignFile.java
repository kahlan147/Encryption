package Main;

import java.math.BigInteger;

public class SignFile {
    /*
    De tweede applicatie leest een file die een private sleutel bevat en een andere file (die we even INPUT.EXT zullen
    noemen).   Verder wordt de naam van de ondertekenaar (bijv. Lk) opgegeven. Als output wordt een file met de naam
    INPUT(SIGNEDBYLK).EXT geproduceerd met de volgende inhoud: de lengte (in bytes) van de digitale handtekening, de
    digitale handtekening (gemaakt met het algoritme 'SHA1withRSA') en de inhoud van file INPUT.EXT.
     */

    private final static String INPUTPATH = "Opdracht1/src/Main/Files/Input.txt";
    private final static String PRIVATEKEYPATH = "Opdracht1/src/Main/Files/SignatureKey_private.txt";

    public static void main(String[] args){
        String signature = "test";
        new SignFile(signature);
    }

    public SignFile(String signature){
        BigInteger privateKey = ReaderWriter.readKey(PRIVATEKEYPATH);
        if(privateKey == null){return;}
        String content = ReaderWriter.readInput(INPUTPATH);
        String encryptedSignature = encryptSignature(signature, privateKey);
        ReaderWriter.writeInputToText(signature, encryptedSignature, content);
        System.out.println("Message encrypted and stored");
    }

    //Encrypt the given signature
    private String encryptSignature(String signature, BigInteger key){
        RSA rsa = new RSA();
        return rsa.encryptWithKey(new BigInteger(signature.getBytes()), key).toString();
    }




}
