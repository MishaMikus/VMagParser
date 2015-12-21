package vmag.action;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import vmag.Log;
import vmag.action.parser.FilterParser;
import vmag.action.xlsx.BookCreator;
import vmag.model.Filter;
import vmag.model.Kvartyry;
import vmag.model.SubRub;
import vmag.page.DatePO;

import java.io.*;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class Serializer {

    public static final String FILENAME = currentDataDir() + "catalog";
    private Map<SubRub, List<Kvartyry>> map;
    private Map<SubRub, List<Kvartyry>> filteredMap = new HashMap<>();

    public Serializer(Map<SubRub, List<Kvartyry>> map) {
        this.map = map;
    }

    public Serializer() {

    }

    private static String currentDataDir() {
        String res;
        String sep = System.getProperty("file.separator");
        String curDataDir = "out" + sep + "data" + sep + DatePO.getActualDate();

        File dir = new File(curDataDir);
        if (dir.exists())
            res = curDataDir + sep;
        else {
            if (dir.mkdir()) {
                res = curDataDir + sep;
            } else res = "";
        }
        Log.info("currentDataDir()=" + res);
        return res;
    }

    public static final String OUTPUT_ENCODING = "UTF-8";

    public Map<SubRub, List<Kvartyry>> load() throws IOException, ClassNotFoundException {
        Log.info();
        Log.info("Start load");
        Log.info("try load from '" + FILENAME + "'");
        Map<SubRub, List<Kvartyry>> res = new ConcurrentHashMap<>();
        try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILENAME));
            res = (ConcurrentHashMap<SubRub, List<Kvartyry>>) ois.readObject();
            Log.info("load from '" + FILENAME + "'");
            return res;
        } catch (Exception e) {
            Log.info("'" + FILENAME + "' loading Failed");
        }
        Log.info("End load");
        return res;
    }

    public void save() throws IOException {
        Log.info("try save to '" + FILENAME + "'");
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILENAME));
        oos.writeObject(map);
        oos.close();
        Log.info("save to '" + FILENAME + "' success");
    }

    public void saveSimpleList() throws IOException {
        saveText(FILENAME + " " + DatePO.getActualDate() + ".txt", transformToSimpleList(map));
    }

    private String transformToSimpleList(Map<SubRub, List<Kvartyry>> map) {
        String res = "";
        for (Map.Entry<SubRub, List<Kvartyry>> e : map.entrySet()) {
            SubRub sr = e.getKey();
            for (Kvartyry k : e.getValue()) {
                res += DatePO.getActualDate() + "\t" + sr.toString() + k.toString() + System.lineSeparator();
            }
        }
        return res;
    }

    public void saveSimpleListOfTels() throws IOException {
        String res = "";
        Set<String> tels = new HashSet<>();
        for (Map.Entry<SubRub, List<Kvartyry>> e : map.entrySet()) {
            for (Kvartyry k : e.getValue()) {
                tels.addAll(k.getTels());
            }
        }
        for (String t : tels) {
            res += t + System.lineSeparator();
        }
        String fn = FILENAME + "(Tel List)" + DatePO.getActualDate() + ".txt";
        saveText(fn, res);
    }

    private void saveText(String fn, String res) throws IOException {
        Log.info("try save to '" + fn + "'");
        Writer writer = new OutputStreamWriter(new FileOutputStream(fn), OUTPUT_ENCODING);
        BufferedWriter fout = new BufferedWriter(writer);
        fout.write(res);
        fout.close();
        Log.info("save to '" + fn + "'");
    }

    public void saveAsXLSX() throws IOException, InvalidFormatException {
        new BookCreator(map).save(FILENAME + DatePO.getActualDate() + ".xlsx");
    }

    public void saveFilterAsXLSX() throws IOException, InvalidFormatException {
        new BookCreator(filteredMap).save(FILENAME + DatePO.getActualDate() + "_filtered.xlsx");
    }

    public void filter(String fn) throws IOException {
        Log.info();
        Log.info("Start filter");
        Filter filter = new FilterParser().getFilter(fn);
        List<String> telList = filter.getTels();
        for (Map.Entry<SubRub, List<Kvartyry>> e : map.entrySet()) {
            List<Kvartyry> fList = new ArrayList<>();
            for (Kvartyry k : e.getValue()) {
                boolean match = false;
                for (String tel : k.getTels()) {
                    match = (telList.contains(tel));
                }
                if (!match) {
                    fList.add(k);
                }
            }
            if (fList.size() > 0) {
                filteredMap.put(e.getKey(), fList);
            }
        }
        Log.info("End filter");
    }
}
