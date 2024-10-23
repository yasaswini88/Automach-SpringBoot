package com.example.Automach.DTO;

public class RawMaterialCountDTO {
    private String rawMaterialName;
    private int count;

    public RawMaterialCountDTO(String rawMaterialName, int count) {
        this.rawMaterialName = rawMaterialName;
        this.count = count;
    }

    // Getters and setters
    public String getRawMaterialName() {
        return rawMaterialName;
    }

    public void setRawMaterialName(String rawMaterialName) {
        this.rawMaterialName = rawMaterialName;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
