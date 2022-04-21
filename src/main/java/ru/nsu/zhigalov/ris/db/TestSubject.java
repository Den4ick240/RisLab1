package ru.nsu.zhigalov.ris.db;

import generated.Node;

import java.util.function.Function;

public class TestSubject {
    public final String name;
    public final Dao<Node> dao;
    public final Runnable finalizer;


    public TestSubject(String name, Dao<Node> dao) {
        this(name, dao, () -> {});
    }

    public TestSubject(String name, Dao<Node> dao, Runnable finalizer) {
        this.name = name;
        this.dao = dao;
        this.finalizer = finalizer;
    }
}
