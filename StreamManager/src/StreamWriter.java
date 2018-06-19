import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;


public class StreamWriter {

    private BufferedWriter bufferedWriter = null;
    private FileWriter fileWriter = null;

    public StreamWriter(){

    }

    public void WriteToTxt(String path, String content){
        try {
            fileWriter = new FileWriter(path);
            bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write(content);
        }
        catch(IOException e) {
            System.out.println("error");
        }
    }
}
