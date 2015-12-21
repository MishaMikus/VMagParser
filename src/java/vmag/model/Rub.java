package vmag.model;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Rub implements Serializable {
	public List<SubRub> subRubList =new ArrayList<>();
	String url;
    String title;
	public Rub(String url,String title,Element el) throws IOException {
		super();
		this.url = url;
		this.title = title;
        initSubRubList(el);
    }

    private void initSubRubList(Element el) throws IOException {
        Elements tmp=el.getElementsByTag("ul").get(0).getElementsByClass("kat");
        for (int i = 0; i < tmp.size(); i++) {
        Element a = tmp.get(i);
        subRubList.add(new SubRub(a.attr("href"),a.text()));}
    }

    @Override
    public String toString() {
        return "\n\t\tRub{" +
             //   "url='" + url + '\'' +
                ", title='" + title + '\'' +
                ", subRubList=" + subRubList +
                '}';
    }

}
