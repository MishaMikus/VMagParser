package vmag.page;

import vmag.Log;
import vmag.action.UrlSourceGetter;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Mykhaylo_Mikus on 3/10/2015 10:06 AM.
 */
public class DatePO {
    private static String actualDate;

    private static final String DEFAULT_DATA_FORMAT = "dd.MM.YYYY";
    private static final String VMAG_ENCODING = "windows-1251";

    public static String getActualDate() {
        if (actualDate == null) {
            actualDate = initActualDate();
            return actualDate;
        } else return actualDate;
    }

    private static String initActualDate() {
        Document doc = null;
        try {
            doc = Jsoup.parse(UrlSourceGetter.getUrlSource("http://vashmagazin.ua/nerukhomist/", VMAG_ENCODING));
        } catch (IOException e) {
            e.printStackTrace();
        }
        String res;
        try {
            res = doc.getElementById("nomer_view").getElementsByTag("option").get(0).text();
            Pattern p = Pattern.compile("\\d\\d.\\d\\d.\\d\\d");
            Matcher m = p.matcher(res);
            if (m.find()) {
                res = m.group(0);
            }
        } catch (Exception e) {
            SimpleDateFormat sdf = new SimpleDateFormat(DEFAULT_DATA_FORMAT);
            res = sdf.format(new GregorianCalendar().getTime());
        }
        Log.info("initActualDate()=" + res);
        return res;
    }
}
