package Main;


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
        ReaderWriter.writeKeyToText(key.publicKey.toString(), key.privateKey.toString());

        ReaderWriter.SaveModulusToProperties(key.modulus);
    }
}
