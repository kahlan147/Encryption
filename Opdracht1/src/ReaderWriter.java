import java.io.*;
import java.math.BigInteger;
import java.util.Properties;

/**
 * Created by Niels Verheijen on 17/06/2018.
 */
public class ReaderWriter {

    private final static String path = "Opdracht1/src/config.properties";
    private final static String modulusProp = "modulus";

    public static void SaveModulusToProperties(BigInteger modulus) {
        Properties prop = new Properties();
        OutputStream output = null;

        try {
            output = new FileOutputStream(path);

            prop.setProperty(modulusProp, modulus.toString());
            prop.store(output, null);

        } catch (IOException io) {
            io.printStackTrace();
        } finally {
            if (output != null) {
                try {
                    output.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
    }

    public static BigInteger ReadModulusFromProperties(){
        Properties prop = new Properties();
        InputStream input = null;

        try {
            input = new FileInputStream(path);
            prop.load(input);


        } catch (IOException io) {
            io.printStackTrace();
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
        return new BigInteger(prop.getProperty(modulusProp));
    }
}
