package com.reactor.core;

public class Parrot extends Bird{

    public Parrot(String id, String name, String parrotId) {
        super(id, name);
        this.parrotId = parrotId;
    }

    String parrotId;

}
