package ru.nsu.zhigalov.ris.db;

import generated.Node;

public class TestSubject {
    public final String name;
    public final Dao<Node> dao;

    public TestSubject(String name, Dao<Node> dao) {
        this.name = name;
        this.dao = dao;
    }
}
