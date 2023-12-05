package com.solvd.updatepackages;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.List;

public class FileUtil {

    public static Collection<File> collectSmaliFiles() {
        Path currentRelativePath = Paths.get("");
        String s = currentRelativePath.toAbsolutePath().toString();
        return FileUtils.listFiles(new File(s), new String[] {"smali"}, true);
    }

    public static void updateFiles(Collection<File> files) {
        for (File file : files) {
            String fileContext;
            try {
                fileContext = FileUtils.readFileToString(file, "UTF-8");
                fileContext = replacePackages(fileContext);
                FileUtils.write(file, fileContext, "UTF-8");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        updateManifest();
    }

    private static String replacePackages(String fileContext) {
        List<Package> packages = Package.getPackageList();
        for (Package pack : packages) {
            fileContext = fileContext.replaceAll(pack.getOriginalPackage(), pack.getMockPackage());
        }
        return fileContext;
    }

    private static void updateManifest() {
        File manifest = getManifestFile();
        checkPermissions(manifest);
    }

    private static File getManifestFile() {
        Path currentRelativePath = Paths.get("").toAbsolutePath();
        String s = currentRelativePath.toString();
        Collection<File> foundFiles;
        foundFiles = FileUtils.listFiles(new File(s), new String[] {"xml"}, false);
        while (findManifest(foundFiles) == null) {
            currentRelativePath = currentRelativePath.getParent();
            s = currentRelativePath.toAbsolutePath().toString();
            foundFiles = FileUtils.listFiles(new File(s), new String[] {"xml"}, false);
        }
        return findManifest(foundFiles);
    }

    private static File findManifest(Collection<File> foundFiles) {
        for (File file : foundFiles) {
            if (file.getName().equalsIgnoreCase("AndroidManifest.xml"))
                return file;
        }
        return null;
    }

    private static void checkPermissions(File file) {
        String fileContext;
        try {
            fileContext = FileUtils.readFileToString(file, "UTF-8");
            List<Permission> permissions = Permission.getPermissionsList();
            for (Permission permission : permissions) {
                if (!fileContext.contains(permission.getPermission())) {
                    addPermissionTag(file, permission);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void addPermissionTag(File file, Permission permission) {
        String fileContext;
        try {
            fileContext = FileUtils.readFileToString(file, "UTF-8");
            int index = StringUtils.indexOf(fileContext, "<app");
            fileContext = fileContext.substring(0, index) + permission.getPermissionTag()
                    + fileContext.substring(index);
            FileUtils.write(file, fileContext, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
