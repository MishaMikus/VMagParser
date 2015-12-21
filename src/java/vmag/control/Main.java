package vmag.control;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import vmag.Log;
import vmag.action.LoadManager;
import vmag.action.Serializer;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;
import java.io.IOException;

/**
 * Created by Mykhaylo_Mikus on 3/12/2015
 */

public class Main {

    public static void main(String[] args) throws IOException, ParserConfigurationException, XPathExpressionException, ClassNotFoundException, InvalidFormatException {
        LoadManager loadAction = new LoadManager();
        loadAction.update();
        loadAction.save();
        Log.saveToFile(Serializer.FILENAME+".log");
    }
}
