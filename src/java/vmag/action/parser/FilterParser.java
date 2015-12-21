package vmag.action.parser;

import vmag.model.Filter;

import java.io.IOException;
import java.util.Arrays;

/**
 * Created by Mykhaylo_Mikus on 3/12/2015 6:38 PM.
 */
public class FilterParser {
    public Filter getFilter(String fn) throws IOException {
        return new Filter(Arrays.asList(TextFile.readFile(fn).split(System.lineSeparator())));
    }
}
