package vmag.action.parser;

import vmag.Log;
import vmag.action.PageLoader;
import vmag.model.SubRub;
import vmag.model.Kvartyry;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class Appender implements Runnable {
    private final SubRub sr;
    private final Map<SubRub, List<Kvartyry>> map;

    public Appender(SubRub sr, Map<SubRub, List<Kvartyry>> map) {
        this.sr = sr;
        this.map = map;
    }

    public void run() {
        try {
            List<Kvartyry> plList = new PageLoader(sr).download();
            log(plList);
            map.put(sr, plList);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private synchronized void log(List<Kvartyry> plList) {
        Log.info(plList.size() + "\tfrom\t" + sr.getUrl());
    }
}