package com.example.warehouse.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Material implements Parcelable {
    private int id;
    private String name;
    private Type type;
    @SerializedName("type_id")
    private int typeId;
    @SerializedName("part_number")
    private String partNumber;
    private Condition condition;
    @SerializedName("condition_id")
    private int conditionId;
    private int quantity;
    private double price;
    private String color;
    private String comment;
    @SerializedName("created_at")
    private String createdAt;
    @SerializedName("deleted_at")
    private String deletedAt;

    public Material(String name, int typeId, String partNumber, int conditionId, int quantity, double price, String color, String comment) {
        this.name = name;
        this.typeId = typeId;
        this.partNumber = partNumber;
        this.conditionId = conditionId;
        this.quantity = quantity;
        this.price = price;
        this.color = color;
        this.comment = comment;
    }

    protected Material(Parcel in) {
        id = in.readInt();
        name = in.readString();
        typeId = in.readInt();
        type = in.readParcelable(getClass().getClassLoader());
        partNumber = in.readString();
        conditionId = in.readInt();
        condition = in.readParcelable(getClass().getClassLoader());
        quantity = in.readInt();
        price = in.readDouble();
        color = in.readString();
        comment = in.readString();
        createdAt = in.readString();
        deletedAt = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeInt(typeId);
        dest.writeParcelable(type, flags);
        dest.writeString(partNumber);
        dest.writeInt(conditionId);
        dest.writeParcelable(condition, flags);
        dest.writeInt(quantity);
        dest.writeDouble(price);
        dest.writeString(color);
        dest.writeString(comment);
        dest.writeString(createdAt);
        dest.writeString(deletedAt);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Material> CREATOR = new Creator<Material>() {
        @Override
        public Material createFromParcel(Parcel in) {
            return new Material(in);
        }

        @Override
        public Material[] newArray(int size) {
            return new Material[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public String getPartNumber() {
        return partNumber;
    }

    public void setPartNumber(String partNumber) {
        this.partNumber = partNumber;
    }

    public Condition getCondition() {
        return condition;
    }

    public void setCondition(Condition condition) {
        this.condition = condition;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(String deletedAt) {
        this.deletedAt = deletedAt;
    }
}
