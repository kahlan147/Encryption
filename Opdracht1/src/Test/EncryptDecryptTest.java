package Test;

import Main.CreateKey;
import Main.SignFile;
import Main.ReaderWriter;
import Main.VerifySignature;
import org.junit.Test;

/**
 * Created by Niels Verheijen on 17/06/2018.
 */
public class EncryptDecryptTest {

    @Test
    public void test() {
 //Does not work due to path errors?
        String signature = "polkadot";
        CreateKey createKey = new CreateKey(1024);
        SignFile signFile = new SignFile(signature);
        VerifySignature verifySignature = new VerifySignature(ReaderWriter.readKey("Opdracht1/src/Main/Files/SignatureKey_public.txt"), signature);
    }
}