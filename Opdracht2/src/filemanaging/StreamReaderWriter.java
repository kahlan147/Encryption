package filemanaging;

import java.io.*;


public class StreamReaderWriter
{
    public void WriteToFile(File path, byte[] encrypted)
    {
        try (RandomAccessFile raf = new RandomAccessFile(path, "rw"))
        {
            raf.write(encrypted);
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public byte[] ReadFromFile(File path)
    {
        try (RandomAccessFile raf = new RandomAccessFile(path, "r"))
        {
            byte[] byteArray = new byte[(int)raf.length()];
            raf.read(byteArray);
            return byteArray;
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return null;
    }
}
