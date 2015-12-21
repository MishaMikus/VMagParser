package vmag.model;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ParentRub implements Serializable {
    public List<Rub> rubList = new ArrayList<>();
    String url;
    String title;

    public ParentRub(String url, String title, Element el) throws IOException {
        super();
        this.url = url;
        this.title = title;
        initList(el);
    }

    private void initList(Element el) throws IOException {
        Elements tmp = el.getElementsByClass("submenuhandler");
        for (int i = 0; i < tmp.size(); i++) {
            Element a = tmp.get(i).getElementsByClass("kat").get(0);
            rubList.add(new Rub(a.attr("href"), a.text(), tmp.get(i)));
        }
    }

    @Override
    public String toString() {
        return "\n\tParentRub{" +
                ", title='" + title + '\'' +
                ", list=" + rubList +
                '}';
    }
}
