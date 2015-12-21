package qvc;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Mykhaylo_Mikus on 4/30/2015 11:19 AM.
 */
public class TestFile {
    public String name;
    public Set<String> controls = new HashSet<>();
    public Set<String> modules = new HashSet<>();

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("TestFile{");
        sb.append("name='").append(name).append('\'');
        sb.append(", controls=").append(controls);
        sb.append(", modules=").append(modules);
        sb.append('}');
        return sb.toString();
    }
}
