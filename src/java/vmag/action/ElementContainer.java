package vmag.action;

import vmag.Log;
import vmag.model.Kvartyry;
import org.jsoup.nodes.Element;

import java.util.ArrayList;
import java.util.List;

public class ElementContainer {
    private List<Element> container;
    private Kvartyry res;

    public ElementContainer(List<Element> container) {//sieve for announcement witch incapsulate in TR list(1~6)
        this.container = container;
        try {
            initRes();
        } catch (Exception e) {
            try {
                intResPopyt();
            } catch (Exception e1) {
                try {
                    initChastynaKvartyry();
                } catch (Exception e2) {
                    try {
                        initZyizdRozyizd();
                    } catch (Exception e3) {
                        e3.printStackTrace();
                        Log.info(container);
                        System.exit(0);
                    }
                }
            }
        }
    }

    private void initChastynaKvartyry() {//'http://vashmagazin.ua//nerukhomist/kvartyry/prodaty'
        String text = container.get(1).getElementsByTag("div").get(0).text();
        List<String> tels = getTels(container.get(2));
        String chastynaKvartyry = container.get(3).getElementsByTag("span").get(1).text();
        String formulaZyizdRozyizd = "";
        Integer count = 0;
        String stina = container.get(3).getElementsByTag("span").get(2).text();
        String poverh = container.get(3).getElementsByTag("td").get(1).text();
        String ploshcha = container.get(3).getElementsByTag("td").get(2).text();
        String hrn = container.get(3).getElementsByTag("td").get(3).getElementsByTag("nobr").get(0).text();
        String usd = container.get(3).getElementsByTag("td").get(3).getElementsByTag("nobr").get(1).text();
        String usdm = container.get(3).getElementsByTag("td").get(3).getElementsByTag("nobr").get(2).text();
        String title = container.get(4).getElementsByTag("td").get(1).text();
        res = new Kvartyry(text, tels, chastynaKvartyry, formulaZyizdRozyizd, count, stina, poverh, ploshcha, hrn, usd, usdm, title);
    }

    private void intResPopyt() {//'http://vashmagazin.ua//nerukhomist/kvartyry/prodaty'
        String text = container.get(1).getElementsByTag("td").get(1).text();
        List<String> tels = getTels(container.get(0));
        String chastynaKvartyry = "";
        String formulaZyizdRozyizd = "";
        Integer count = 0;
        String stina = "";
        String poverh = "";
        String ploshcha = "";
        String hrn = "";
        String usd = "";
        String usdm = "";
        String title = "";
        res = new Kvartyry(text, tels, chastynaKvartyry, formulaZyizdRozyizd, count, stina, poverh, ploshcha, hrn, usd, usdm, title);
    }

    private void initRes() {//'http://vashmagazin.ua//nerukhomist/kvartyry/..' except '../prodaty'
        String text = container.get(1).getElementsByTag("div").get(0).text();
        List<String> tels = getTels(container.get(2));
        String chastynaKvartyry = "";
        String formulaZyizdRozyizd = "";
        Integer count = Integer.parseInt(container.get(3).getElementsByTag("span").get(1).text());
        String stina = container.get(3).getElementsByTag("span").get(2).text();
        String poverh = container.get(3).getElementsByTag("td").get(1).text();
        String ploshcha = container.get(3).getElementsByTag("td").get(2).text();
        String hrn = container.get(3).getElementsByTag("td").get(3).getElementsByTag("nobr").get(0).text();
        String usd = container.get(3).getElementsByTag("td").get(3).getElementsByTag("nobr").get(1).text();
        String usdm = container.get(3).getElementsByTag("td").get(3).getElementsByTag("nobr").get(2).text();
        String title = container.get(4).getElementsByTag("td").get(1).text();
        res = new Kvartyry(text, tels, chastynaKvartyry, formulaZyizdRozyizd, count, stina, poverh, ploshcha, hrn, usd, usdm, title);
    }


    private void initZyizdRozyizd() {//'http://vashmagazin.ua//nerukhomist/obmin-nerukhomosti/zyizd-rozyizd/'
        String text = container.get(1).getElementsByTag("td").get(0).text();
        List<String> tels = getTels(container.get(2));
        String chastynaKvartyry = "";
        Integer count = 0;
        String formulaZyizdRozyizd = container.get(3).getElementsByTag("span").get(1).text();
        String stina = container.get(3).getElementsByTag("span").get(2).text();
        String poverh = container.get(3).getElementsByTag("td").get(1).text();
        String ploshcha = container.get(3).getElementsByTag("td").get(2).text();
        String hrn = "";
        String usd = "";
        String usdm = "";
        String title = container.get(4).getElementsByTag("td").get(1).text();
        res = new Kvartyry(text, tels, chastynaKvartyry, formulaZyizdRozyizd, count, stina, poverh, ploshcha, hrn, usd, usdm, title);

    }


    private List<String> getTels(Element telContainer) {
        List<String> res = new ArrayList<>();
        for (Element e : telContainer.getElementsByClass("ner-auto-tel")) {
            res.add(e.getElementsByTag("nobr").text());
        }
        return res;
    }

    public Kvartyry getRes() {
//        String text="";
//        List<String> tels=null;
//        String chastynaKvartyry="";
//        String formulaZyizdRozyizd="";
//        Integer count=0;
//        String stina="";
//        String poverh="";
//        String ploshcha="";
//        String hrn="";
//        String usd="";
//        String usdm="";
//        String title="";
//        res = new Kvartyry(text,tels, chastynaKvartyry, formulaZyizdRozyizd, count, stina, poverh, ploshcha, hrn, usd, usdm, title);
        return res;
    }







}
