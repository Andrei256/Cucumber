package com.cucumber.model;

public enum Category {

    LAPTOP("Ноутбук"), PHONE("Телефон"), TABLET("Планшет");

    private String name;

    Category(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
