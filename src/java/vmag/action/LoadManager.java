package vmag.action;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import vmag.Log;
import vmag.action.parser.Appender;
import vmag.model.Kvartyry;
import vmag.model.ParentRub;
import vmag.model.Rub;
import vmag.model.SubRub;
import vmag.page.TitleCatalog;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Mykhaylo_Mikus on 3/6/2015 5:03 PM.
 */
public class LoadManager {
    private Map<SubRub, List<Kvartyry>> map;

    public LoadManager() throws IOException, ClassNotFoundException {
        this.map = new Serializer().load();
    }

    public void update() throws IOException {
        Log.info();
        Log.info("Start update");
        TitleCatalog title = new TitleCatalog();
        ExecutorService executor = Executors.newCachedThreadPool();
        Long from = System.currentTimeMillis();
        for (ParentRub pr : title.parentRubList) {
            for (Rub r : pr.rubList) {
                for (SubRub sr : r.subRubList) {
                    if (map.size() == 0) {
                        //if map is clear then start first loading thread
                        executor.execute(new Appender(sr, map));
                    } else {
                        if (map.get(sr) == null) {
                            //if subRub not found in map then start new thread for loading this subRub
                            executor.execute(new Appender(sr, map));
                            break;
                        }
                    }
                }
            }
        }
        executor.shutdown();
        while (!executor.isTerminated()) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        Long to = System.currentTimeMillis();
        Log.info("Work time     = " + (to - from) / 1000 + " s");
        Log.info("RubCount      = " + map.size());
        Log.info("KvartyryCount = " + calculateSize(map));
        Log.info("End update");
    }

    private int calculateSize(Map<SubRub, List<Kvartyry>> map) {
        int res = 0;
        for (Map.Entry<SubRub, List<Kvartyry>> e : map.entrySet())
            res += e.getValue().size();
        return res;
    }

    public void save() throws IOException, InvalidFormatException {
        Log.info();
        Log.info("Start save");
        Serializer serializer = new Serializer(map);
        serializer.save();
        // serializer.saveSimpleList();
        serializer.saveSimpleListOfTels();
        serializer.saveAsXLSX();
        serializer.filter("filter.txt");
        serializer.saveFilterAsXLSX();
        Log.info("End save");
    }
}
