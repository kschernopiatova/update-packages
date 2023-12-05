package com.solvd.updatepackages;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class FileUtil {

    public static Collection<File> collectSmaliFiles() {
        Path currentRelativePath = Paths.get("");
        String s = currentRelativePath.toAbsolutePath().toString();
        System.out.println("Current absolute path is: " + s);
        return FileUtils.listFiles(new File(s), new String[] {"smali"}, true);
    }

    public static void updateFiles(Collection<File> files) {
        for (File file : files) {
            String fileContext = null;
            try {
                fileContext = FileUtils.readFileToString(file, "UTF-8");
                fileContext = replacePackages(fileContext);
                FileUtils.write(file, fileContext, "UTF-8");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static String replacePackages(String fileContext) {
        List<Packages> packages = Arrays.stream(Packages.values()).toList();
        for (Packages pack : packages) {
            fileContext = fileContext.replaceAll(pack.getOriginalPackage(), pack.getMockPackage());
        }
        return fileContext;
    }
}
