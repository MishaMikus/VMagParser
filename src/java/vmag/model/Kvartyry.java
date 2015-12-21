package vmag.model;

import java.io.Serializable;
import java.util.List;

public class Kvartyry implements Serializable {
    private static final String level = "\t\t\t\t";

    private String text;

    private List<String> tels;
    private String chastynaKvartyry;
    private String formulaZyizdRozyizd;
    private Integer count;
    private String stina;
    private String poverh;
    private String ploshcha;
    private String hrn;
    private String usd;
    private String usdm;
    private String title;

    @Override
    public String toString() {
        return level + "Kvartyry{" +
                "\ttext='" + text + '\'' +
                "\ttels=" + tels +
                "\tchastynaKvartyry='" + chastynaKvartyry + '\'' +
                "\tformulaZyizdRozyizd='" + formulaZyizdRozyizd + '\'' +
                "\tcount=" + count +
                "\tstina='" + stina + '\'' +
                "\tpoverh='" + poverh + '\'' +
                "\tploshcha='" + ploshcha + '\'' +
                "\thrn='" + hrn + '\'' +
                "\tusd='" + usd + '\'' +
                "\tusdm='" + usdm + '\'' +
                "\ttitle='" + title + '\'' +
                "\t}";
    }

    public Kvartyry(String text, List<String> tels, String chastynaKvartyry, String formulaZyizdRozyizd, Integer count, String stina, String poverh, String ploshcha, String hrn, String usd, String usdm, String title) {
        this.text = text;
        this.tels = tels;
        this.chastynaKvartyry = chastynaKvartyry;
        this.formulaZyizdRozyizd = formulaZyizdRozyizd;
        this.count = count;
        this.stina = stina;
        this.poverh = poverh;
        this.ploshcha = ploshcha;
        this.hrn = hrn;
        this.usd = usd;
        this.usdm = usdm;
        this.title = title;
    }

    public List<String> getTels() {
        return tels;
    }

    public static String getLevel() {
        return level;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setTels(List<String> tels) {
        this.tels = tels;
    }

    public String getChastynaKvartyry() {
        return chastynaKvartyry;
    }

    public void setChastynaKvartyry(String chastynaKvartyry) {
        this.chastynaKvartyry = chastynaKvartyry;
    }

    public String getFormulaZyizdRozyizd() {
        return formulaZyizdRozyizd;
    }

    public void setFormulaZyizdRozyizd(String formulaZyizdRozyizd) {
        this.formulaZyizdRozyizd = formulaZyizdRozyizd;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public String getStina() {
        return stina;
    }

    public void setStina(String stina) {
        this.stina = stina;
    }

    public String getPoverh() {
        return poverh;
    }

    public void setPoverh(String poverh) {
        this.poverh = poverh;
    }

    public String getPloshcha() {
        return ploshcha;
    }

    public void setPloshcha(String ploshcha) {
        this.ploshcha = ploshcha;
    }

    public String getHrn() {
        return hrn;
    }

    public void setHrn(String hrn) {
        this.hrn = hrn;
    }

    public String getUsd() {
        return usd;
    }

    public void setUsd(String usd) {
        this.usd = usd;
    }

    public String getUsdm() {
        return usdm;
    }

    public void setUsdm(String usdm) {
        this.usdm = usdm;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
