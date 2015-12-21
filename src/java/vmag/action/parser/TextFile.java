package vmag.action.parser;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by Mykhaylo_Mikus on 3/11/2015 5:58 PM.
 */
public class TextFile {
     public static String readFile(File f) throws IOException {
        String content;
        FileReader reader = new FileReader(f);
        char[] chars = new char[(int) f.length()];
        reader.read(chars);
        content = new String(chars);
        reader.close();
        return content;
    }

    public static void saveFile(File f, String content) throws IOException {
        FileWriter fw = new FileWriter(f);
        fw.write(content);
        fw.close();
    }

    public static String readFile(String fn) throws IOException {
        return readFile(new File(fn));
    }
}
