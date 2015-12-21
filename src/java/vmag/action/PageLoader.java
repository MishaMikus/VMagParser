package vmag.action;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import vmag.Log;
import vmag.model.Kvartyry;
import vmag.model.SubRub;
import vmag.page.TitleCatalog;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PageLoader {

    private static final String ALL = "ogolosh-avto-sp";
    private static final String POPYT = "ogo_0_0_table";
    private static final String VMAG_ENCODING = "windows-1251";

    private String startUrl;
    private Document startDoc;
    private Integer actualCount;

    public PageLoader(SubRub sr) throws IOException {
        this.startUrl = sr.getUrl();
        this.startDoc = Jsoup.parse(UrlSourceGetter.getUrlSource(sr.getUrl(), VMAG_ENCODING));
        this.actualCount = parseActualCount();
    }

    private Integer parseActualCount() {
        Integer res = 0;
        try {
            Elements elements = startDoc.getElementsByAttributeValueContaining("style", "../img/new/list.gif");
            if (elements.size() > 0) {
                res = Integer.parseInt(elements.get(0).getElementsByTag("b").get(0).text());
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.info("parseActualCount Error");
        }
        return res;
    }

    public List<Kvartyry> download() throws IOException {
        List<Kvartyry> res = Collections.synchronizedList(new ArrayList<Kvartyry>());
        List<String> urlList = getUrlList();
        res.addAll(addToElList(Jsoup.parse(UrlSourceGetter.getUrlSource(startUrl, VMAG_ENCODING))));
        for (String urlLocal : urlList) {
            res.addAll(addToElList(Jsoup.parse(UrlSourceGetter.getUrlSource(urlLocal, VMAG_ENCODING))));
        }
        if (res.size() < actualCount) {
            Log.info("Download : " + res.size());
            Log.info("Want to be downloaded : " + actualCount);
        }
        return res;
    }

    //Pagination
    private List<String> getUrlList() {
        List<String> res = new ArrayList<>();
        Elements tmp = startDoc.getElementsByClass("page");//Pagination Buttons (without default)
        String prefix = "";
        int maxPage = 0;
        for (int i = 0; i < tmp.size(); i++) {
            String[] s = tmp.get(i).getElementsByTag("a").attr("href").split("&page=");
            if (prefix.equals("") || prefix.equals("&page=")) {
                prefix = s[0] + "&page=";
            }
            int page = 0;
            try {
                page = Integer.parseInt(s[1]);
            } catch (Exception e) {
            }
            maxPage = (page > maxPage ? page : maxPage);
        }
        for (int i = 1; i <= maxPage; i++) {
            res.add(TitleCatalog.START_PAGE + prefix + i);
        }
        if (res.size() > 1) {
            res.remove(0);
        }
        Log.info(startDoc.title() + ":" + (res.size() > 0 ? res : "[current page source]"));
        return res;
    }


    private List<Kvartyry> addToElList(Document document) {
        List<Kvartyry> res = new ArrayList<>();
        Elements allElements = getElTable(document);
        if (allElements.size() > 0) {
            Elements trCollections = allElements.get(0).getElementsByTag("tr");
            for (int i = 0; i < trCollections.size(); i++) {
                String className = trCollections.get(i).attr("class");
                if (className.equals("border_bo")) {
                    res.add(element(i, trCollections).getRes());
                }
            }
        } else {
            Log.info("Err URI : " + document.baseUri());
            Log.info("Document " + document.title() + " have no records");
        }
        return res;
    }

    private Elements getElTable(Document document) {
        Elements res = document.getElementsByClass(ALL);
        if (res.size() == 0) {
            res = document.getElementsByClass(POPYT);
        }
        return res;
    }

    private ElementContainer element(int i, Elements tmp) {
        List<Element> container = new ArrayList<>();
        int j = i - 1;
        while ((j > 0) && (!tmp.get(j).attr("class").equals("border_bo"))) {
            container.add(tmp.get(j--));
        }
        return new ElementContainer(container);
    }
}
