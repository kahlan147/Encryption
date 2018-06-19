package Test;

import Main.CreateKey;
import Main.ReadFile;
import Main.ReaderWriter;
import Main.VerifySignature;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Niels Verheijen on 17/06/2018.
 */
public class EncryptDecryptTest {

    @Test
    public void test() {
 //Does not work due to path errors?
        String signature = "polkadot";
        CreateKey createKey = new CreateKey(1024);
        ReadFile readFile = new ReadFile(signature);
        VerifySignature verifySignature = new VerifySignature(ReaderWriter.readKey("Opdracht1/src/Main/Files/SignatureKey_public.txt"), signature);
    }
}