package Main;

import java.math.BigInteger;

public class VerifySignature {


    private final static String SIGNEDINPUTPATH = "Opdracht1/src/Main/Files/INPUT(SIGNEDBY";
    private final static String PUBLICKEYPATH = "Opdracht1/src/Main/Files/SignatureKey_public.txt";

    /*
    De derde applicatie leest file INPUT(SIGNEDBYLK).EXT en de publieke sleutel van de ondertekenaar en verifieert de
    handtekening. Het resultaat (wel of niet goedgekeurd) wordt gemeld. Als de handtekening klopt wordt bovendien de
    oorspronkelijke file INPUT.EXT gereconstrueerd.
     */
    public static void main(String[] args) {
        BigInteger publicKey = ReaderWriter.readKey(PUBLICKEYPATH);
        String signature = "Lk";

        new VerifySignature(publicKey, signature);

    }

    public VerifySignature(BigInteger publicKey, String signature){

        String[] data = getSavedInput(signature);
        if(verifySignature(data[0], data[1], signature, publicKey)){
            ReaderWriter.writeDecodedInput(data[2]);
            System.out.println("Message decoded to DecodedInput.txt");
        }
        else{
            System.out.println("Incorrect key or signature");
        }
    }

    private String[] getSavedInput(String signature){
        String signedInput = ReaderWriter.readInput(SIGNEDINPUTPATH + signature + ").txt");
        String[] data = new String[3];
        int endFirstRow = signedInput.indexOf("\n");
        int endSecondRow = signedInput.indexOf("\n", endFirstRow +2);
        data[0] = signedInput.substring(0, endFirstRow);
        data[1] = signedInput.substring(endFirstRow + 1, endSecondRow);
        data[2] = signedInput.substring(endSecondRow +1);
        return data;
    }

    private boolean verifySignature(String length, String encryptedSignature, String signature, BigInteger publicKey){
        if(Integer.parseInt(length) == encryptedSignature.length()){
            RSA rsa = new RSA();
            String decodedSignature = new String(rsa.decryptWithKey(new BigInteger(encryptedSignature), publicKey).toByteArray());
            return signature.equals(decodedSignature);
        }
        return false;
    }

}
