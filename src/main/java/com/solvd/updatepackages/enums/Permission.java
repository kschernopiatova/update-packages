package com.solvd.updatepackages.enums;

import java.util.Arrays;
import java.util.List;

public enum Permission {

    IMAGES("READ_MEDIA_IMAGES"), AUDIO("READ_MEDIA_AUDIO"), VIDEO("READ_MEDIA_VIDEO");

    private final String permission;

    Permission(String permission) {
        this.permission = permission;
    }

    public String getPermissionTag() {
        return "<uses-permission android:name=\"android.permission." + getPermission() + "\" /> \n";
    }

    public String getPermission() {
        return permission;
    }

    public static List<Permission> getPermissionsList() {
        return Arrays.stream(values()).toList();
    }
}
