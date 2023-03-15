package com.example.warehouse.model;

public class APIResponse {
    private boolean error;
    private String message;
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    private APIModels data;

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public APIModels getData() {
        return data;
    }

    public void setData(APIModels data) {
        this.data = data;
    }
}
