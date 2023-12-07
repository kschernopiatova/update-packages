package com.solvd.updatepackages.util;

import com.solvd.updatepackages.enums.Package;
import com.solvd.updatepackages.enums.Permission;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.List;

public class FileUtil {

    private static final String UTF_8 = "UTF-8";

    public static void mockFiles() {
        Collection<File> files = collectSmaliFiles();
        for (File file : files) {
            String fileContext;
            try {
                fileContext = FileUtils.readFileToString(file, UTF_8);
                fileContext = replacePackages(fileContext);
                FileUtils.write(file, fileContext, UTF_8);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        updateManifest();
    }

    private static Collection<File> collectSmaliFiles() {
        Path currentRelativePath = Paths.get("");
        String s = currentRelativePath.toAbsolutePath().toString();
        return FileUtils.listFiles(new File(s), new String[] { "smali" }, true);
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
        String path = currentRelativePath.toString();
        Collection<File> foundFiles = FileUtils.listFiles(new File(path), new String[] { "xml" }, false);
        while (findManifest(foundFiles) == null) {
            currentRelativePath = currentRelativePath.getParent();
            path = currentRelativePath.toAbsolutePath().toString();
            foundFiles = FileUtils.listFiles(new File(path), new String[] { "xml" }, false);
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
            fileContext = FileUtils.readFileToString(file, UTF_8);
            List<Permission> permissions = Permission.getPermissionsList();
            for (Permission permission : permissions) {
                if (!fileContext.contains(permission.getPermission())) {
                    fileContext = addPermissionTag(fileContext, permission);
                    FileUtils.write(file, fileContext, UTF_8);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String addPermissionTag(String fileContext, Permission permission) {
        int index = StringUtils.indexOf(fileContext, "<app");
        fileContext = fileContext.substring(0, index) + permission.getPermissionTag()
                + fileContext.substring(index);
        return fileContext;
    }
}
