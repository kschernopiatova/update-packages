package com.solvd.updatepackages;

import java.util.Arrays;
import java.util.List;

public enum Package {

    IMAGE("android/media/Image", "com/solvd/mock/Image"),
    IMAGE_READER("android/media/ImageReader", "com/solvd/mock/ImageReader"),
    BYTE_BUFFER("java/nio/ByteBuffer", "com/solvd/mock/ByteBuffer");

    private final String originalPackage;
    private final String mockPackage;

    Package(String originalPackage, String mockPackage) {
        this.originalPackage = originalPackage;
        this.mockPackage = mockPackage;
    }

    String getOriginalPackage() {
        return originalPackage;
    }

    String getMockPackage() {
        return mockPackage;
    }

    public static List<Package> getPackageList() {
        return Arrays.stream(values()).toList();
    }
}
