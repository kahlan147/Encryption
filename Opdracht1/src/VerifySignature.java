import java.io.*;
import java.math.BigInteger;
import java.util.Properties;

public class VerifySignature {


    private final static String SIGNEDINPUTPATH = "Opdracht1/src/INPUT(SIGNEDBYLk).txt";
    private final static String PUBLICKEYPATH = "Opdracht1/src/SignatureKey_public.txt";

    /*
    De derde applicatie leest file INPUT(SIGNEDBYLK).EXT en de publieke sleutel van de ondertekenaar en verifieert de
    handtekening. Het resultaat (wel of niet goedgekeurd) wordt gemeld. Als de handtekening klopt wordt bovendien de
    oorspronkelijke file INPUT.EXT gereconstrueerd.
     */
    public static void main(String[] args) {
        BigInteger publicKey = ReaderWriter.readKey(PUBLICKEYPATH);
        String signedInput = ReaderWriter.readInput(SIGNEDINPUTPATH);
        String signature = "Lk";

        VerifySignature verifySignature = new VerifySignature(publicKey, signedInput, signature);

    }

    public VerifySignature(BigInteger publicKey, String signedInput, String signature){
        String[] data = new String[3];
        int endFirstRow = signedInput.indexOf("\n");
        int endSecondRow = signedInput.indexOf("\n", endFirstRow +2);
        data[0] = signedInput.substring(0, endFirstRow);
        data[1] = signedInput.substring(endFirstRow + 1, endSecondRow);
        data[2] = signedInput.substring(endSecondRow +1);

        if(Integer.parseInt(data[0]) == data[1].length()){
            RSA rsa = new RSA();
            String decodedSignature = new String(rsa.decryptWithKey(new BigInteger(data[1]), publicKey).toByteArray());
            if(signature.equals(decodedSignature)){
                System.out.println(data[2]);
            }
        }
    }

}
