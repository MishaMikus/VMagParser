package qvc;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Set;

/**
 * Created by Mykhaylo_Mikus on 4/17/2015 10:18 AM.
 */
public class POContrl {
    public String name;
    public String fileName;
    public Set<String> modules = new HashSet<>();
    public Set<String> tests = new HashSet<>();
    public LinkedHashMap<String, ArrayList<String>> locatorMap = new LinkedHashMap<String, ArrayList<String>>() {
        {
            put("US_ANDROID", new ArrayList<String>());
        }
        {
            put("UK_ANDROID", new ArrayList<String>());
        }
        {
            put("DE_ANDROID", new ArrayList<String>());
        }
        {
            put("US_IPAD", new ArrayList<String>());
        }
        {
            put("UK_IPAD", new ArrayList<String>());
        }
        {
            put("DE_IPAD", new ArrayList<String>());
        }
        {
            put("US_IPHONE", new ArrayList<String>());
        }
        {
            put("UK_IPHONE", new ArrayList<String>());
        }
        {
            put("DE_IPHONE", new ArrayList<String>());
        }
    };
    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("POContrl{");
        sb.append("name='").append(name).append('\'');
        sb.append(" fileName='").append(fileName).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
