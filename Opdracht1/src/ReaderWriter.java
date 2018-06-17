import java.io.*;
import java.math.BigInteger;
import java.util.Properties;

public class ReaderWriter {

    private final static String KEY = "Opdracht1/src/SignatureKey";
    private final static String CONFIG = "Opdracht1/src/config.properties";
    private final static String MODULUSPROP = "modulus";
    private final static String DECODEDINPUT = "Opdracht1/src/DecodedInput.txt";

    public static void SaveModulusToProperties(BigInteger modulus) {
        Properties prop = new Properties();
        OutputStream output = null;

        try {
            output = new FileOutputStream(CONFIG);

            prop.setProperty(MODULUSPROP, modulus.toString());
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

    public static BigInteger ReadModulusFromProperties() {
        Properties prop = new Properties();
        InputStream input = null;

        try {
            input = new FileInputStream(CONFIG);
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
        return new BigInteger(prop.getProperty(MODULUSPROP));
    }

    public static BigInteger readKey(String path) {
        FileReader fileReader = null;
        BufferedReader bufferedReader = null;

        try {
            fileReader = new FileReader(path);
            bufferedReader = new BufferedReader(fileReader);
            return new BigInteger(bufferedReader.readLine());
        } catch (IOException e) {
            System.out.println("error");
        } finally {
            closeChannels(bufferedReader, fileReader);
        }
        return null;
    }

    public static String readInput(String path) {
        StringBuilder text = new StringBuilder();
        FileReader fileReader = null;
        BufferedReader bufferedReader = null;
        try {
            fileReader = new FileReader(path);
            bufferedReader = new BufferedReader(fileReader);
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                text.append(line + "\n");
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            closeChannels(bufferedReader, fileReader);
        }
        return text.toString();
    }

    public static void writeDecodedInput(String message) {
        String path = DECODEDINPUT;
        FileWriter fileWriter = null;
        BufferedWriter bufferedWriter = null;

        try {
            fileWriter = new FileWriter(path);
            bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write(message);

        } catch (IOException e) {
            System.out.println("error");
        } finally {
            closeChannels(bufferedWriter, fileWriter);
        }
    }

    public static void writeKeyToText(String publicKey, String privateKey) {

        String path = KEY;
        FileWriter fileWriter = null;
        BufferedWriter bufferedWriter = null;

        try {
            fileWriter = new FileWriter(path + "_public.txt");
            bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write(publicKey);

            bufferedWriter.close();
            fileWriter.close();

            fileWriter = new FileWriter(path + "_private.txt");
            bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write(privateKey);
        } catch (IOException e) {
            System.out.println("error");
        } finally {
            closeChannels(bufferedWriter, fileWriter);
        }
    }

    public static void writeInputToText(String name, String signature, String content) {

        String path = "Opdracht1/src/INPUT(SIGNEDBY" + name + ").txt";
        FileWriter fileWriter = null;
        BufferedWriter bufferedWriter = null;

        try {
            fileWriter = new FileWriter(path);
            bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write(signature.length() + "\n");
            bufferedWriter.write(signature + "\n");
            bufferedWriter.write(content + "\n");

        } catch (IOException e) {
            System.out.println("error");
        } finally {
            closeChannels(bufferedWriter, fileWriter);

        }
    }

    private static void closeChannels(Closeable buffer, Closeable file) {
        try {
            if (buffer != null)
                buffer.close();

            if (file != null)
                file.close();

        } catch (IOException ex) {

            ex.printStackTrace();

        }
    }
}
