package com.elypia.webhooker.test.impl;

public class Streamer {

    private int id;
    private String description;
    private boolean isLive;

    public Streamer() {
        // Do nothing
    }

    public Streamer(int id, String description, boolean isLive) {
        this.id = id;
        this.description = description;
        this.isLive = isLive;
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

    public boolean isLive() {
        return isLive;
    }

    public void setIsLive(boolean isLive) {
        this.isLive = isLive;
    }
}
