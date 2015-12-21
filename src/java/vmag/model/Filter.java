package vmag.model;

import java.util.List;

/**
 * Created by Mykhaylo_Mikus on 3/12/2015
 */
public class Filter {
    private List<String> tels;

    public Filter(List<String> tels) {
        this.tels = tels;
    }

    public List<String> getTels() {
        return tels;
    }

    public void setTels(List<String> tels) {
        this.tels = tels;
    }
}
