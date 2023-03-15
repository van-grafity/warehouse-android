package com.example.warehouse.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class APIModels {
    private User user;
    @SerializedName("material")
    private ArrayList<Material> materials;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public ArrayList<Material> getMaterials() {
        return materials;
    }

    public void setMaterials(ArrayList<Material> materials) {
        this.materials = materials;
    }
}
