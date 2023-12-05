package com.solvd.updatepackages;

import java.io.File;
import java.util.Collection;

public class Main {
    public static void main(String[] args) {
        Collection<File> files = FileUtil.collectSmaliFiles();
        FileUtil.updateFiles(files);
    }
}
