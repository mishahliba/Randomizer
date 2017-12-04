package com.mentoring.model;

/**
 * Class represents entity with which user can interact.
 */
public class Person {

    private String name;
    private Boolean isWinner = false;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getWinner() {
        return isWinner;
    }

    public void setWinner(Boolean winner) {
        isWinner = winner;
    }
}
