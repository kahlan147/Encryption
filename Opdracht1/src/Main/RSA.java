package Main;

import java.math.BigInteger;
import java.security.SecureRandom;

// https://introcs.cs.princeton.edu/java/99crypto/RSA.java.html
public class RSA {
    private final static BigInteger one      = new BigInteger("1");
    private final static SecureRandom random = new SecureRandom();

    public BigInteger privateKey;
    public BigInteger publicKey;
    public BigInteger modulus;

    // generate an N-bit (roughly) public and private key
    public RSA(int N) {
        BigInteger p = BigInteger.probablePrime(N/2, random);
        BigInteger q = BigInteger.probablePrime(N/2, random);
        BigInteger phi = (p.subtract(one)).multiply(q.subtract(one));

        modulus    = p.multiply(q);
        publicKey  = new BigInteger("65537");     // common value in practice = 2^16 + 1
        privateKey = publicKey.modInverse(phi);
    }

    public RSA(){
        this.modulus = ReaderWriter.ReadModulusFromProperties();
    }

    /*
    //Example
    private BigInteger encrypt(BigInteger message) {
        return message.modPow(publicKey, modulus);
    }
    //example
    private BigInteger decrypt(BigInteger encrypted) {
        return encrypted.modPow(privateKey, modulus);
    }
*/
    public BigInteger encryptWithKey(BigInteger message, BigInteger encryptionKey) {
        return message.modPow(encryptionKey, modulus);
    }

    public BigInteger decryptWithKey(BigInteger encrypted, BigInteger decryptionKey) {
        return encrypted.modPow(decryptionKey, modulus);
    }

    public String toString() {
        String s = "";
        s += "public  = " + publicKey  + "\n";
        s += "private = " + privateKey + "\n";
        s += "modulus = " + modulus;
        return s;
    }

    //args[0] gives error,
    /*public static void main(String[] args) {
        int N = Integer.parseInt(args[0]);
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
    }*/
}
