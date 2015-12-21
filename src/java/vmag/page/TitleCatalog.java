package vmag.page;

import vmag.action.UrlSourceGetter;
import vmag.model.ParentRub;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class TitleCatalog implements Serializable {

    public static final String START_PAGE = "http://vashmagazin.ua/";
    public List<ParentRub> parentRubList = new ArrayList<>();
    private Document doc;

    public TitleCatalog() throws IOException {
        doc = Jsoup.parse(UrlSourceGetter.getUrlSource(START_PAGE, "windows-1251"));
        initParentRubList();
    }


    @Override
    public String toString() {
        return "Catalog{" +
                "ParentRubList=" + parentRubList;
    }

    private void initParentRubList() throws IOException {
        Elements list = doc.getElementsByClass("mainMenuGroupContainer");
        for (int i = 0; i < list.size(); i++) {
            Element parentRubTitle = list.get(i).getElementsByClass("parent_rub_title").get(0);
            String href = parentRubTitle.attr("href");
            href = href.substring(1, href.length() - 1);
            String title = parentRubTitle.text();
            if (href.equals("nerukhomist")) {
                parentRubList.add(new ParentRub(href, title, list.get(i)));
            }
        }
    }
}
