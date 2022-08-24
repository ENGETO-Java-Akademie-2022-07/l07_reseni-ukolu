package com.engeto.assetregister;

public class Asset {
    private static int nextId = 1;
    private int id;
    private String description;

    public Asset(int id, String description) {
        this.id = id;
        this.description = description;
    }

    public static int getNextId() {
        return nextId;
    }

    public static void setNextId(int nextId) {
        Asset.nextId = nextId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Asset{" +
                "id=" + id +
                ", description='" + description + '\'' +
                '}';
    }
}
