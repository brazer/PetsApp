package com.example.myapplication.domain;

public enum PetType {
    CAT(0, "cat"), DOG(1, "dog");

    private int id;
    private String name;

    PetType(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

}
