package com.example.mad_finalgame;

public class Player {
    private long id;
    private String name;
    private int score;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    // Will be used by the ArrayAdapter in the ListView
    @Override
    public String toString() {
        return name + ": " + score;
    }
}