package qvc;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Mykhaylo_Mikus on 4/17/2015 4:08 PM.
 */
public class ModuleFile {
    public String name;
    public Set<String> controls = new HashSet<>();

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("ModuleFile{");
        sb.append("name='").append(name).append('\'');
        sb.append(", controls=").append(controls);
        sb.append('}');
        return sb.toString();
    }
}
