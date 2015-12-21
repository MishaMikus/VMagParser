package vmag.model;

import vmag.page.TitleCatalog;

import java.io.IOException;
import java.io.Serializable;

public class SubRub implements Serializable {
    private String url;
    private String title;
    public SubRub(String url, String title) throws IOException {
        this.url = TitleCatalog.START_PAGE + url;
        this.title = title;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SubRub)) return false;
        SubRub subRub = (SubRub) o;
        return url.equals(subRub.url);
    }

    @Override
    public int hashCode() {
        return url.hashCode();
    }

    @Override
    public String toString() {
        return "\turl='" + url + '\'' +
                "\ttitle='" + title + '\'' +
                "\t}";
    }

    public String getUrl() {
        return url;
    }

    public String getTitle() {
        return title;
    }
}
