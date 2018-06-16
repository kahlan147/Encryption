import java.math.BigInteger;
import java.security.SecureRandom;

/**
 * Created by Niels Verheijen on 16/06/2018.
 */
public class CreateKey {

    private final static SecureRandom random = new SecureRandom();

    /*
    De eerste applicatie genereert een publieke sleutel van 1024 bits volgens het RSA-algoritme en de bijbehorende
    private sleutel en schrijft beide sleutels in afzonderlijke files weg. Je kunt hier een variant van het  programma
    RSATest (listing 20 in [1]) voor gebruiken.
     */

    public static void main(String[] args){
        int N = 938;
        RSA key = new RSA(N);

        System.out.println(key);

        // create random message, encrypt and decrypt
        BigInteger message = new BigInteger(N-1, random);

        //// create message by converting string to integer
        // String s = "test";
        // byte[] bytes = s.getBytes();
        // BigInteger message = new BigInteger(bytes);

        BigInteger encrypt = key.encrypt(message);
        BigInteger decrypt = key.decrypt(encrypt);
        System.out.println("message   = " + message);
        System.out.println("encrypted = " + encrypt);
        System.out.println("decrypted = " + decrypt);
    }
}
