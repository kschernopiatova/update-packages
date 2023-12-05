package com.solvd.updatepackages;

public enum Packages {

    IMAGE("android/media/Image", "com/solvd/mock/Image"),
    IMAGE_READER("android/media/ImageReader", "com/solvd/mock/ImageReader"),
    BYTE_BUFFER("java/nio/ByteBuffer", "com/solvd/mock/ByteBuffer");

    private String originalPackage;
    private String mockPackage;

    private Packages(String originalPackage, String mockPackage) {
        this.originalPackage = originalPackage;
        this.mockPackage = mockPackage;
    }

    String getOriginalPackage() {
        return originalPackage;
    }

    String getMockPackage() {
        return mockPackage;
    }
}
