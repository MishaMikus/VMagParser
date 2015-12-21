package vmag.action;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class UrlSourceGetter {
    public static String getUrlSource(String url,String encoding) throws IOException {
        URL tmpURL = new URL(url);
        URLConnection tmpURLc = tmpURL.openConnection();
        BufferedReader in= new BufferedReader(new InputStreamReader(tmpURLc.getInputStream(), encoding));
        String inputLine=in.readLine();
        StringBuilder a = new StringBuilder();
        while (inputLine!=null) {
            a.append(inputLine);
            inputLine=in.readLine();
        }
        in.close();
        return a.toString();
    }
}
