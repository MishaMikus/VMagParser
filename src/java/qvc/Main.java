package qvc; /**
 * Created by Mykhaylo_Mikus on 4/17/2015 9:58 AM.
 */

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {
    final static String DIR = "\\\\EPUALVIW0637.kyiv.epam.com\\QVCMobile_draft\\pageObjects";
    final static String DIR_MODULE = "\\\\EPUALVIW0637.kyiv.epam.com\\QVCMobile_draft\\module";
    final static String DIR_TESTS = "\\\\EPUALVIW0637.kyiv.epam.com\\QVCMobile_draft\\tests\\Smoke";
    final static String RESULT_FILE_NAME = "\\\\EPUALVIW0637.kyiv.epam.com\\QVCMobile_draft\\Tagging\\Map.xlsx";

    public static void main(String[] arg) throws IOException, InvalidFormatException {
        Book.saveSumMap(RESULT_FILE_NAME, getControlList(getFiles(DIR), getFiles(DIR_MODULE), getFiles(DIR_TESTS)));
    }

    private static List<POContrl> getControlList(File[] filesPO, File[] filesMod, File[] filesTest) throws IOException, InvalidFormatException {
        List<POContrl> res = new ArrayList<>();
        for (File f : filesPO) {
            res.addAll(Book.parse(f));
        }

        List<ModuleFile> modList = getModuleList(filesMod);
        for (POContrl control : res) {
            setModule(control, modList);
        }

        List<TestFile> testList = getTestList(filesTest);
        for (POContrl control : res) {
            setTests(control, testList);
        }

        return res;
    }

    private static void setTests(POContrl control, List<TestFile> testList) {
        for (TestFile tf : testList) {

            if (tf.controls.contains(control.name)) {
                control.tests.add(tf.name);
            }

            for (String module : control.modules) {
                String rMod = module.split("\\.")[0];
                if (tf.modules.contains(rMod)) {
                    control.tests.add(tf.name);
                }
            }
        }
    }

    private static List<TestFile> getTestList(File[] filesTest) throws IOException, InvalidFormatException {
        List<TestFile> res = new ArrayList<>();
        for (File f : filesTest) {
            res.addAll(Book.parseTest(f));
        }
        return res;
    }

    private static List<ModuleFile> getModuleList(File[] filesMod) throws IOException, InvalidFormatException {
        List<ModuleFile> res = new ArrayList<>();
        for (File f : filesMod) {
            res.addAll(Book.parseModule(f));
        }
        return res;
    }

    private static void setModule(POContrl control, List<ModuleFile> modList) {
        for (ModuleFile mf : modList) {
            if (mf.controls.contains(control.name)) {
                control.modules.add(mf.name);
            }
        }
    }

    private static File[] getFiles(String dir) {
        return new File(dir).listFiles(new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                String fn = pathname.getName();
                return fn.endsWith(".xlsx") && (!fn.startsWith("~$"));
            }
        });
    }
}
