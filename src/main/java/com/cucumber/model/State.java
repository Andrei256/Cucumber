package com.cucumber.model;

public enum State {

    CANCELED("Отменен"), COMPLETED("Выполнен"), ACTIVE("Ожидается доставка");

    private String name;

    State(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
